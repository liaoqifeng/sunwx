package com.koch.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.koch.bean.Filter;
import com.koch.bean.Filter.Operator;
import com.koch.bean.OrderBy;
import com.koch.bean.OrderBy.OrderType;
import com.koch.bean.WeChatMessage;
import com.koch.dao.PostJoinDao;
import com.koch.dao.PostPraiseDao;
import com.koch.entity.Member;
import com.koch.entity.PostJoin;
import com.koch.entity.PostPraise;
import com.koch.service.PostJoinService;
import com.koch.util.PostConstant;

@Service
public class PostJoinServiceImpl extends BaseServiceImpl<PostJoin> implements PostJoinService {
	@Resource
	private PostJoinDao postJoinDao;

	@Resource
	public void setBaseDao(PostJoinDao postJoinDao) {
		super.setBaseDao(postJoinDao);
	}

	@Override
	@Transactional
	public WeChatMessage doJoin(Integer memberId, Integer postId) {
		// Map<String ,Object> map = new HashMap<String ,Object>();
		// map.put("postId", postId);
		Member member = new Member();
		member.setId(memberId);
		// map.put("member", member);

		Filter filter = new Filter();
		filter.setProperty("postId");
		filter.setValue(postId);
		filter.setOperator(Operator.eq);

		Filter filter2 = new Filter();
		filter2.setProperty("member");
		filter2.setValue(member);
		filter2.setOperator(Operator.eq);

		Filter filter3 = new Filter();
		filter3.setProperty("status");
		filter3.setValue(PostConstant.PostStatus.active);
		filter3.setOperator(Operator.eq);

		Filter[] filters1 = { filter, filter2, filter3 };

		long rr = getBaseDao().count(filters1);
		// PostPraise postPraise = getBaseDao().get(map);

		if (rr == 0) {
			PostJoin pp = new PostJoin();
			pp.setMember(member);
			pp.setPostId(postId);
			pp.setCreateDate(new Date());
			pp.setModifyDate(new Date());
			pp.setStatus(PostConstant.PostStatus.active);
			Integer r = getBaseDao().save(pp);
			System.out.println(r);
			if (r > 0) {

				Filter[] filters2 = { filter, filter3 };
				long c = getBaseDao().count(filters2);
				String content = "{\"postId\":\"" + postId + "\",\"count\":\"" + c + "\"}";
				return WeChatMessage.success(content);
			} else {
				return WeChatMessage.error("参加活动失败");
			}
		} else {
			return WeChatMessage.warn("您已经参加了次活动");
		}
	}

	// @Override
	// public Map<Integer, Integer> getPraiseCount(List<Integer> postIds) {
	// List l = postPraiseDao.getCount(postIds);
	// Map<Integer, Integer> r = new HashMap<Integer, Integer>();
	// for (int i = 0; i < postIds.size(); i++) {
	// r.put(postIds.get(i), 0);
	// }
	//
	// for (int i = 0; i < l.size(); i++) {
	// Map<String, Integer> m = (Map<String, Integer>) l.get(i);
	// r.put(m.get("postId"), m.get("num"));
	// }
	//
	// System.out.println(r);
	// return r;
	// }

	@Override
	public List<PostJoin> getJoins(Integer postId) {
		OrderBy order = new OrderBy();
		order.setOrderBy("createDate");
		order.setOrderType(OrderType.desc);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("postId", postId);
		map.put("status", PostConstant.PostStatus.active);
		return getBaseDao().getList(map, order);
		// return getBaseDao().getList("postId", postId, order);
	}

	@Override
	@Transactional
	public WeChatMessage cancelJoin(Integer memberId, Integer postId) {
		Member member = new Member();
		member.setId(memberId);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("postId", postId);
		map.put("member", member);
		map.put("status", PostConstant.PostStatus.active);
		// PostPraise postPraise = getBaseDao().get(map);
		PostJoin postJoin = getBaseDao().get(map);
		if (postJoin != null) {
			postJoin.setStatus(PostConstant.PostStatus.disable);
			postJoin.setModifyDate(new Date());
			getBaseDao().update(postJoin);
			Filter filter = new Filter();
			filter.setProperty("postId");
			filter.setValue(postId);
			filter.setOperator(Operator.eq);

			Filter filter3 = new Filter();
			filter3.setProperty("status");
			filter3.setValue(PostConstant.PostStatus.active);
			filter3.setOperator(Operator.eq);

			Filter[] filters2 = { filter, filter3 };
			long c = getBaseDao().count(filters2);
			String content = "{\"postId\":\"" + postId + "\",\"count\":\"" + c + "\"}";
			return WeChatMessage.success(content);

		} else {
			return WeChatMessage.warn("您已经取消参加了次活动");
		}
	}

	@Override
	public boolean isJoin(Integer memberId, Integer postId) {
		Member member = new Member();
		member.setId(memberId);
		// map.put("member", member);

		Filter filter = new Filter();
		filter.setProperty("postId");
		filter.setValue(postId);
		filter.setOperator(Operator.eq);

		Filter filter2 = new Filter();
		filter2.setProperty("member");
		filter2.setValue(member);
		filter2.setOperator(Operator.eq);

		Filter filter3 = new Filter();
		filter3.setProperty("status");
		filter3.setValue(PostConstant.PostStatus.active);
		filter3.setOperator(Operator.eq);

		Filter[] filters1 = { filter, filter2, filter3 };

		long rr = getBaseDao().count(filters1);
		// PostPraise postPraise = getBaseDao().get(map);

		if (rr == 0) {
			return false;
		} else {
			return true;
		}
	}
}
