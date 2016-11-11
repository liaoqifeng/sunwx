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
import com.koch.dao.PostDao;
import com.koch.dao.PostPraiseDao;
import com.koch.entity.Member;
import com.koch.entity.Post;
import com.koch.entity.PostJoin;
import com.koch.entity.PostPraise;
import com.koch.service.PostPraiseService;
import com.koch.util.PostConstant;

@Service
public class PostPraiseServiceImpl extends BaseServiceImpl<PostPraise> implements PostPraiseService {
	@Resource
	private PostPraiseDao postPraiseDao;
	
	@Resource
	private PostDao postDao;

	@Resource
	public void setBaseDao(PostPraiseDao postPraiseDao) {
		super.setBaseDao(postPraiseDao);
	}

	@Override
	@Transactional
	public WeChatMessage doPraise(Integer memberId, Integer postId) {
		// Map<String ,Object> map = new HashMap<String ,Object>();
		// map.put("postId", postId);
		Member member = new Member();
		member.setId(memberId);
		// map.put("member", member);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("postId", postId);
		map.put("member", member);
		map.put("status", PostConstant.PostStatus.active);
		// PostPraise postPraise = getBaseDao().get(map);
		PostPraise postPraise = getBaseDao().get(map);

		// Filter filter = new Filter();
		// filter.setProperty("postId");
		// filter.setValue(postId);
		// filter.setOperator(Operator.eq);
		//
		// Filter filter2 = new Filter();
		// filter2.setProperty("member");
		// filter2.setValue(member);
		// filter2.setOperator(Operator.eq);
		//
		// Filter filter3 = new Filter();
		// filter3.setProperty("status");
		// filter3.setValue(PostConstant.PostStatus.active);
		// filter3.setOperator(Operator.eq);
		//
		// Filter[] filters1 = { filter, filter2, filter3 };

		// long rr = getBaseDao().count(filters1);
		// PostPraise postPraise = getBaseDao().get(map);

		if (postPraise == null) {
			PostPraise pp = new PostPraise();
			pp.setMember(member);
			pp.setPostId(postId);
			pp.setCreateDate(new Date());
			pp.setModifyDate(new Date());
			pp.setStatus(PostConstant.PostStatus.active);
			Post post = postDao.get(postId);
			if (post.getMember().getId().equals(memberId)) {
				pp.setBadge(0);
			}
			Integer r = getBaseDao().save(pp);
			System.out.println(r);
			if (r > 0) {
				long c = getPraiseCount(postId);
				String content = "{\"postId\":\"" + postId + "\",\"count\":\"" + c + "\" ,\"result\":" + true + "  }";
				return WeChatMessage.success(content);
			} else {
				return WeChatMessage.error("点赞失败");
			}
		} else {
			postPraise.setModifyDate(new Date());
			postPraise.setStatus(PostConstant.PostStatus.disable);
			postPraise = this.update(postPraise);
			getBaseDao().flush();
			long c = getPraiseCount(postId);

			String content = "{\"postId\":\"" + postId + "\",\"count\":\"" + c + "\" ,\"result\":" + false + "  }";
			return WeChatMessage.success(content);

			// return WeChatMessage.warn("您已经点过赞了");
		}
	}

	@Transactional(readOnly = true)
	private long getPraiseCount(Integer postId) {
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
		return c;
	}

	@Override
	public Map<Integer, Integer> getPraiseCount(List<Integer> postIds) {
		List l = postPraiseDao.getCount(postIds);
		Map<Integer, Integer> r = new HashMap<Integer, Integer>();
		for (int i = 0; i < postIds.size(); i++) {
			r.put(postIds.get(i), 0);
		}

		for (int i = 0; i < l.size(); i++) {
			Map<String, Integer> m = (Map<String, Integer>) l.get(i);
			r.put(m.get("postId"), m.get("num"));
		}

		System.out.println(r);
		return r;

		// Filter filter = new Filter();
		// filter.setProperty("postId");
		// filter.setValue(postId);
		// filter.setOperator(Operator.in);
		//
		//
		//
		// Filter filter3 = new Filter();
		// filter3.setProperty("status");
		// filter3.setValue(PostConstant.PostStatus.active);
		// filter3.setOperator(Operator.eq);
		//
		// Filter[] filters1 = { filter, filter3 };
		//
		// long rr = getBaseDao().count(filters1);
		// // PostPraise postPraise = getBaseDao().get(map);
		//
		// if (rr == 0) {
		// PostPraise pp = new PostPraise();
		// pp.setMember(member);
		// pp.setPostId(postId);
		// pp.setCreateDate(new Date());
		// pp.setModifyDate(new Date());
		// pp.setStatus(PostConstant.PostStatus.active);
		// Integer r = getBaseDao().save(pp);
		// System.out.println(r);
		// if (r > 0) {
		//
		// Filter[] filters2 = { filter ,filter3};
		// long c = getBaseDao().count(filters2);
		// String content = "{\"postId\":\"" + postId + "\",\"count\":\"" + c +
		// "\"}";
		// return WeChatMessage.success(content);
		// } else {
		// return WeChatMessage.error("点赞失败");
		// }
		// } else {
		// return WeChatMessage.warn("您已经点过赞了");
		// }
	}

	@Override
	public List<PostPraise> getPraises(Integer postId) {
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
	public WeChatMessage cancelPraise(Integer memberId, Integer postId) {
		Member member = new Member();
		member.setId(memberId);
		// map.put("member", member);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("postId", postId);
		map.put("member", member);
		map.put("status", PostConstant.PostStatus.active);
		// PostPraise postPraise = getBaseDao().get(map);
		PostPraise postPraise = getBaseDao().get(map);
		// PostPraise postPraise = getBaseDao().get(map);

		if (postPraise != null) {
			postPraise.setModifyDate(new Date());
			postPraise.setStatus(PostConstant.PostStatus.disable);
			getBaseDao().save(postPraise);

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

			Filter[] filters2 = { filter, filter3 };
			long c = getBaseDao().count(filters2);
			String content = "{\"postId\":\"" + postId + "\",\"count\":\"" + c + "\"}";
			return WeChatMessage.success(content);

		} else {
			return WeChatMessage.warn("您已经取消点赞了");
		}
	}

	@Override
	@Transactional
	public boolean isPraise(Integer memberId, Integer postId) {
		Member member = new Member();
		member.setId(memberId);

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

		if (rr == 0) {
			return false;
		} else {
			return true;
		}
	}
}
