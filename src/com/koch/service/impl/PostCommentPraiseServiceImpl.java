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
import com.koch.dao.PostCommentPraiseDao;
import com.koch.dao.PostPraiseDao;
import com.koch.entity.Member;
import com.koch.entity.PostCommentPraise;
import com.koch.entity.PostPraise;
import com.koch.service.PostCommentPraiseService;
import com.koch.util.PostConstant;

@Service
public class PostCommentPraiseServiceImpl extends BaseServiceImpl<PostCommentPraise> implements
		PostCommentPraiseService {
	@Resource
	private PostCommentPraiseDao postCommentPraiseDao;

	@Resource
	public void setBaseDao(PostCommentPraiseDao postCommentPraiseDao) {
		super.setBaseDao(postCommentPraiseDao);
	}

	@Override
	@Transactional
	public WeChatMessage doPraise(Member member, Integer postCommentId) {
		// Map<String ,Object> map = new HashMap<String ,Object>();
		// map.put("postId", postId);

		// map.put("member", member);
		if (member == null) {
			return WeChatMessage.error("只有系统用户可以点赞");
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("postCommentId", postCommentId);
			map.put("member", member);
			map.put("status", PostConstant.PostStatus.active);
			// PostPraise postPraise = getBaseDao().get(map);
			PostCommentPraise postPraise = getBaseDao().get(map);

			if (postPraise == null) {
				PostCommentPraise pp = new PostCommentPraise();
				pp.setMember(member);
				pp.setPostCommentId(postCommentId);
				pp.setCreateDate(new Date());
				pp.setModifyDate(new Date());
				pp.setStatus(PostConstant.PostStatus.active);
				Integer r = getBaseDao().save(pp);
				System.out.println(r);
				if (r > 0) {
					long c = getPraiseCount(postCommentId);
					String content = "{\"postCommentId\":\"" + postCommentId + "\",\"count\":\"" + c
							+ "\" ,\"result\":" + true + "  }";
					return WeChatMessage.success(content);
				} else {
					return WeChatMessage.error("点赞失败");
				}
			} else {
				postPraise.setModifyDate(new Date());
				postPraise.setStatus(PostConstant.PostStatus.disable);
				postPraise = this.update(postPraise);
				getBaseDao().flush();
				long c = getPraiseCount(postCommentId);

				String content = "{\"postCommentId\":\"" + postCommentId + "\",\"count\":\"" + c + "\" ,\"result\":"
						+ false + "  }";
				return WeChatMessage.success(content);

				// return WeChatMessage.warn("您已经点过赞了");
			}
		}
	}

	@Transactional(readOnly = true)
	private long getPraiseCount(Integer postId) {
		Filter filter = new Filter();
		filter.setProperty("postCommentId");
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

}
