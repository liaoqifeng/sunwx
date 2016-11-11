package com.koch.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.persistence.LockModeType;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.koch.bean.OrderBy;
import com.koch.bean.Pager;
import com.koch.dao.DepositInfoDao;
import com.koch.dao.FactionDao;
import com.koch.dao.MemberDao;
import com.koch.entity.Admin;
import com.koch.entity.DepositInfo;
import com.koch.entity.DepositInfo.DepositType;
import com.koch.entity.Faction;
import com.koch.entity.Grade;
import com.koch.entity.Member;
import com.koch.entity.Member.MemberStatus;
import com.koch.service.MemberService;
import com.koch.util.GlobalConstant;
import com.koch.util.PingyinUtil;
import com.koch.util.PostConstant;

@Service
public class MemberServiceImpl extends BaseServiceImpl<Member> implements MemberService {
	@Resource
	private MemberDao memberDao;
	@Resource
	private DepositInfoDao depositInfoDao;

	@Resource
	private FactionDao factionDao;

	@Resource
	public void setBaseDao(MemberDao memberDao) {
		super.setBaseDao(memberDao);
	}

	public Pager<Member> findByPager(Grade grade, String name, MemberStatus status, Pager<Member> pager) {
		return memberDao.findByPager(grade, name, status, pager);
	}

	public BigDecimal getDeposit(Integer id) {
		return this.memberDao.getDeposit(id);
	}

	@Transactional(readOnly = true)
	public boolean usernameExists(String username) {
		return this.memberDao.usernameExists(username);
	}

	@Transactional(readOnly = true)
	public boolean emailExists(String email) {
		return this.memberDao.emailExists(email);
	}

	@Transactional(readOnly = true)
	public boolean emailUnique(String previousEmail, String currentEmail) {
		if (StringUtils.equalsIgnoreCase(previousEmail, currentEmail))
			return true;
		return !this.memberDao.emailExists(currentEmail);
	}

	@Transactional(readOnly = true)
	public Member findByUsername(String username) {
		return this.memberDao.findByUsername(username);
	}

	@Transactional(readOnly = true)
	public List<Member> findListByEmail(String email) {
		return this.memberDao.findListByEmail(email);
	}
	
	@Transactional(readOnly = true)
	public List<Member> findListByRealName(String realname) {
		return this.memberDao.findListByRealName(realname);
	}

	@Transactional
	public void save(Member member, Admin operator) {
		Assert.notNull(member);
		this.memberDao.save(member);
		if (member.getDeposit().compareTo(new BigDecimal(0)) > 0) {
			DepositInfo info = new DepositInfo();
			info.setType(operator != null ? DepositType.adminRecharge : DepositType.memberRecharge);
			info.setCredit(member.getDeposit());
			info.setDebit(new BigDecimal(0));
			info.setDeposit(member.getDeposit());
			info.setOperator(operator != null ? operator.getUsername() : null);
			info.setMember(member);
			this.depositInfoDao.save(info);
		}
	}

	@Transactional
	public void update(Member member, BigDecimal modifyDeposit, String modifyDepositRemark, Integer modifyScore,
			Admin operator) {
		Assert.notNull(member);

		this.memberDao.lock(member, LockModeType.PESSIMISTIC_WRITE);
		if (modifyScore != null && modifyScore.intValue() > 0 && (member.getScore() + modifyScore) >= 0) {
			member.setScore(member.getScore() + modifyScore);
		}

		if (modifyDeposit != null && modifyDeposit.compareTo(new BigDecimal(0)) != 0
				&& member.getDeposit().add(modifyDeposit).compareTo(new BigDecimal(0)) >= 0) {
			member.setDeposit(member.getDeposit().add(modifyDeposit));

			// 如果修改帐户余额为正数,则认定为帐户充值.如查为负数,则认定为帐户扣款
			DepositInfo info = new DepositInfo();
			if (modifyDeposit.compareTo(new BigDecimal(0)) > 0) {
				info.setType(DepositType.adminRecharge);
				info.setCredit(modifyDeposit);
				info.setDebit(new BigDecimal(0));
			} else {
				info.setType(DepositType.adminChargeback);
				info.setCredit(new BigDecimal(0));
				info.setDebit(modifyDeposit);
			}
			info.setDeposit(member.getDeposit());
			info.setOperator(operator != null ? operator.getUsername() : "");
			info.setRemark(modifyDepositRemark);
			info.setMember(member);

			this.depositInfoDao.save(info);
		}

		this.memberDao.update(member);
	}

	@Transactional(readOnly = true)
	public Member getCurrent() {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		if (requestAttributes != null) {
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
			Member member = (Member) request.getSession().getAttribute(GlobalConstant.MEMBER_SESSION_USER);
			if (member != null) {
				return member;// this.memberDao.get(1);
			}
		}
		return null;// this.memberDao.get(1);
	}

	@Transactional(readOnly = true)
	public String getCurrentUsername() {
		return getCurrent().getUsername();
	}

	@Override
	public Long getScoreByFaction(Integer factionId) {
		return memberDao.getScoreByFaction(factionId);
	}

	@Override
	public List getScoreGroupByFaction() {
		List list = memberDao.getScoreGroupByFaction();
		return list;
		// List<Faction> all = factionDao.getAll(new OrderBy("index"));
		// Map<Integer, Faction> r = new LinkedHashMap<Integer, Faction>();
		// for (int i = 0; i < all.size(); i++) {
		// Faction p = all.get(i);
		// r.put(p.getId(), p);
		// }
		// List result = new ArrayList();
		// // // int length = list.size();
		// // // if (lengt)
		// for (int i = 0; i < list.size(); i++) {
		// Object[] obs = (Object[]) list.get(i);
		// List l = new ArrayList();
		// l.add(obs[0]);
		// l.add(obs[1]);
		// l.add(r.get(obs[1]).getName());
		// l.add(r.get(obs[1]).getImagePath());
		// result.add(l);
		// }
		// return result;
	}

	@Override
	public List<Map<String, Object>> findListByFaction(Integer factionId) {
		List<Member> ms = memberDao.findListByFaction(factionId);
		Map<String, List<Member>> map = new TreeMap<String, List<Member>>();
		for (int i = 0; i < ms.size(); i++) {
			Member member = ms.get(i);
			String py = PingyinUtil.getFirstLetter(member.getRealname());
			List<Member> l = map.get(py);
			if (l == null) {
				l = new ArrayList<Member>();
				map.put(py, l);
			}
			l.add(member);
		}
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Iterator<String> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			String py = iterator.next();
			Map<String, Object> r1 = new HashMap<String, Object>();
			if ("！".equals(py)) {
				r1.put("value", "#");
				r1.put("name", "0");
			} else {
				r1.put("value", py);
				r1.put("name", py);
			}

			r1.put("type", 0);
			result.add(r1);
			List<Member> l = map.get(py);
			for (int i = 0; i < l.size(); i++) {
				Member member = l.get(i);
				Map<String, Object> r2 = new HashMap<String, Object>();
				r2.put("value", member);
				r2.put("type", 1);
				result.add(r2);
			}
		}
		return result;

	}
}
