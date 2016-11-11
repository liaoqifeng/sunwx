package com.koch.service.impl;

import java.util.ArrayList;
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
import com.koch.dao.MemberDao;
import com.koch.dao.PostCommentDao;
import com.koch.dao.PostCommentPraiseDao;
import com.koch.dao.PostDao;
import com.koch.dao.PostScoreDao;
import com.koch.entity.Member;
import com.koch.entity.Post;
import com.koch.entity.PostComment;
import com.koch.entity.PostScore;
import com.koch.service.PostCommentService;
import com.koch.util.PostConstant;
import com.koch.util.PostConstant.PostStatus;
import com.koch.util.PropertiesUtil;

@Service
public class PostCommentServiceImpl extends BaseServiceImpl<PostComment> implements PostCommentService {

	@Resource
	private PostScoreDao postScoreDao;

	@Resource
	private PostDao postDao;

	@Resource
	private PostCommentPraiseDao postCommentPraiseDao;

	@Resource
	private PostCommentDao postCommentDao;

	@Resource
	private MemberDao memberDao;

	@Override
	@Transactional
	public WeChatMessage doComment(Integer memberId, Integer postId, String content) {
		return doComment(memberId, postId, null, content);
	}
	
	@Override
	@Transactional
	public WeChatMessage doComment(Integer memberId, Integer postId,Integer replyCommentId, String content) {
		// Map<String ,Object> map = new HashMap<String ,Object>();
		// map.put("postId", postId);
		Member member = new Member();
		member.setId(memberId);
		// map.put("member", member);

		PostComment postComment = new PostComment();
		postComment.setPostId(postId);
		postComment.setMember(member);
		postComment.setContent(content);
		postComment.setCreateDate(new Date());
		postComment.setModifyDate(new Date());
		postComment.setStatus(PostConstant.PostStatus.active);
		Post post = postDao.get(postId);
		if (post.getMember().getId().equals(memberId)) {
			postComment.setBadge(0);
		}
		
		if (replyCommentId != null){
			PostComment replyPost = get(replyCommentId);
			postComment.setReplyComment(replyPost);
			postComment.setReplyMember(replyPost.getMember());
		}
		Integer r = getBaseDao().save(postComment);
		if (r > 0) {
			Filter filter = new Filter();
			filter.setProperty("postId");
			filter.setValue(postId);
			filter.setOperator(Operator.eq);
			Filter filter2 = new Filter();
			filter2.setProperty("status");
			filter2.setValue(PostConstant.PostStatus.active);
			filter2.setOperator(Operator.eq);

			Filter[] filters2 = { filter, filter2 };
			long c = getBaseDao().count(filters2);
			String contents = "{\"postId\":\"" + postId + "\",\"count\":\"" + c + "\"}";

			int add = Integer.parseInt(PropertiesUtil.getProperty(PropertiesUtil.POST_COMMENT_SCORE));
			Member m = memberDao.get(postComment.getMember().getId());
			m.setScore(m.getScore() + add);
			memberDao.save(m);
			
			
			
//			PostScore postScore = postScoreDao.getPostScore(postComment.getMember());
//			int cj = Integer.parseInt(PropertiesUtil.getProperty(PropertiesUtil.POST_ALL_SCORE)) - postScore.getScore();
//			int add = Integer.parseInt(PropertiesUtil.getProperty(PropertiesUtil.POST_COMMENT_SCORE));
//			if (add > cj) {
//				add = cj;
//			}
//			if (add > 0) {
//				Member m = memberDao.get(postComment.getMember().getId());
//				m.setScore(m.getScore() + add);
//				postScore.setScore(postScore.getScore() + add);
//				memberDao.save(m);
//				postScoreDao.save(postScore);
//			}

			return WeChatMessage.success(contents);
		} else {
			return WeChatMessage.error("评论失败");
		}

	}

	@Override
	public List<PostComment> getComments(Integer postId, Member currentMember) {
		OrderBy order = new OrderBy();
		order.setOrderBy("createDate");
		order.setOrderType(OrderType.desc);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("postId", postId);
		map.put("status", PostConstant.PostStatus.active);
		List<PostComment> r = getBaseDao().getList(map, order);

		List<Integer> postIds = new ArrayList<Integer>();
		if (r != null && r.size() > 0) {
			for (int i = 0; i < r.size(); i++) {
				PostComment p = r.get(i);
				postIds.add(p.getId());

			}

			List<Integer> l3 = postCommentPraiseDao.getPraises(postIds, currentMember);

			for (int i = 0; i < r.size(); i++) {
				PostComment p = r.get(i);

				if (l3.contains(p.getId())) {
					p.setPraised(true);
				}
			}
		}

		return r;
	}

	@Resource
	public void setBaseDao(PostCommentDao postCommentDao) {
		super.setBaseDao(postCommentDao);
	}

	@Override
	@Transactional
	public WeChatMessage cancelComment(Integer commentId) {
		PostComment comment = get(commentId);
		if (comment != null) {
			comment.setStatus(PostStatus.disable);
			comment.setModifyDate(new Date());
			getBaseDao().update(comment);
			return WeChatMessage.success("");
		} else {
			return WeChatMessage.error("删除回帖失败");
		}
	}

	@Override
	@Transactional
	public void updateBadeg(Integer memberId) {
		postCommentDao.updateBadeg(memberId);

	}

}
