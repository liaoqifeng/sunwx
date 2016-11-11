package com.koch.dao.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.koch.dao.PostCommentDao;
import com.koch.entity.PostComment;
import com.koch.util.PostConstant;

@Repository("postCommentDao")
public class PostCommentDaoImpl extends BaseDaoImpl<PostComment> implements PostCommentDao {

	public List getCount(List<Integer> postIds) {
		if (postIds == null)
			return Collections.emptyList();

		StringBuffer sql = new StringBuffer();
		sql.append("select postComment.postId  as postId , count(*) as  num from PostComment postComment where  postComment.postId in (");
		// for (int i = 0; i < postIds.size(); i++) {
		// sql.append(":postId" + i + ",");
		// }
		// if (postIds.size() > 0) {
		// sql.deleteCharAt(sql.length() - 1);
		// }
		sql.append(":postIds ");
		sql.append(") and postComment.status = (:status) group by postComment.postId ");
		Query query = entityManager.createQuery(sql.toString());
		query.setParameter("status", PostConstant.PostStatus.active);
		query.setParameter("postIds", postIds);
		// for (int i = 0; i < postIds.size(); i++) {
		// query.setParameter("postId" + i, postIds.get(i));
		// }
		return query.getResultList();
	}

	public List getBadgeCounts(List<Integer> postIds) {
		if (postIds == null)
			return Collections.emptyList();

		StringBuffer sql = new StringBuffer();
		sql.append("select postComment.postId  as postId , SUM(badge) as  num from PostComment postComment where  postComment.postId in (");
		// for (int i = 0; i < postIds.size(); i++) {
		// sql.append(":postId" + i + ",");
		// }
		// if (postIds.size() > 0) {
		// sql.deleteCharAt(sql.length() - 1);
		// }
		sql.append(":postIds ");
		sql.append(") and postComment.status = (:status) group by postComment.postId ");
		Query query = entityManager.createQuery(sql.toString());
		query.setParameter("status", PostConstant.PostStatus.active);
		query.setParameter("postIds", postIds);
		// for (int i = 0; i < postIds.size(); i++) {
		// query.setParameter("postId" + i, postIds.get(i));
		// }
		return query.getResultList();
	}

	public void updateBadeg(Integer memberId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" update PostComment set badge=0 where postId in  ");
		sql.append(" (SELECT id FROM Post where memberId =" + memberId + ")  ");
		Query query = entityManager.createQuery(sql.toString());
		query.executeUpdate();
		//

	}

	public void updateBadegByPost(Integer postId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" update PostComment set badge=0 where postId =  ");
		sql.append("  (:postId) ");
		Query query = entityManager.createQuery(sql.toString());
		query.setParameter("postId", postId);
		query.executeUpdate();
		//

	}

	@Override
	public Long getBadgeCount(Integer memberId) {
		StringBuffer sql = new StringBuffer();

		sql.append("  select SUM(badge) from PostComment  where postId in  ");
		sql.append(" (SELECT id FROM Post where memberId = (:memberId)  and status = (:status))  ");
		Query query = entityManager.createQuery(sql.toString());
		query.setParameter("status", PostConstant.PostStatus.active);
		query.setParameter("memberId", memberId);
		List<Long> l = query.getResultList();
		if (l.size() > 0 && l.get(0) != null)
			return l.get(0);
		else
			return 0l;
	}

	@Override
	public void updateBadeg(Integer memberId, Integer id) {
		StringBuffer sql = new StringBuffer();
		sql.append(" update PostComment set badge=0 where postId in  ");
		sql.append(" (SELECT id FROM Post where memberId =" + memberId + ")  and id <= " + id);
		Query query = entityManager.createQuery(sql.toString());
		query.executeUpdate();

	}

	@Override
	public Integer getMaxId(Integer memberId) {
		StringBuffer sql = new StringBuffer();

		sql.append("  select max(id) from PostComment  where postId in  ");
		sql.append(" (SELECT id FROM Post where memberId = (:memberId)  and status = (:status))  ");
		Query query = entityManager.createQuery(sql.toString());
		query.setParameter("status", PostConstant.PostStatus.active);
		query.setParameter("memberId", memberId);
		List<Integer> l = query.getResultList();
		if (l.size() > 0 && l.get(0) != null)
			return l.get(0);
		else
			return 0;
	}

}
