package com.koch.dao.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.koch.dao.PostPraiseDao;
import com.koch.entity.Member;
import com.koch.entity.PostPraise;
import com.koch.util.PostConstant;

@Repository("postPraiseDao")
public class PostPraiseDaoImpl extends BaseDaoImpl<PostPraise> implements PostPraiseDao {

	public List getCount(List<Integer> postIds) {
		if (postIds == null)
			return Collections.emptyList();

		StringBuffer sql = new StringBuffer();
		sql.append("select postPraise.postId  as postId , count(*) as  num from PostPraise postPraise where  postPraise.postId in (");
		// for (int i = 0; i < postIds.size(); i++) {
		// sql.append(":postId" + i + ",");
		// }
		// if (postIds.size() > 0) {
		// sql.deleteCharAt(sql.length() - 1);
		// }
		sql.append(":postIds ");
		sql.append(") and postPraise.status = (:status) group by postPraise.postId ");
		Query query = entityManager.createQuery(sql.toString());
		query.setParameter("status", PostConstant.PostStatus.active);
		query.setParameter("postIds", postIds);
		// for (int i = 0; i < postIds.size(); i++) {
		// query.setParameter("postId" + i, postIds.get(i));
		// }
		return query.getResultList();
	}

	@Override
	public List<Integer> getPraises(List<Integer> postIds, Member member) {
		if (postIds == null)
			return Collections.emptyList();

		StringBuffer sql = new StringBuffer();
		sql.append("select postPraise.postId  as postId from PostPraise postPraise where  postPraise.postId in (");
		// for (int i = 0; i < postIds.size(); i++) {
		// sql.append(":postId" + i + ",");
		// }
		// if (postIds.size() > 0) {
		// sql.deleteCharAt(sql.length() - 1);
		// }
		sql.append(":postIds ");
		sql.append(") and postPraise.status = (:status) and  postPraise.member = (:member)");
		Query query = entityManager.createQuery(sql.toString());
		query.setParameter("status", PostConstant.PostStatus.active);
		query.setParameter("member", member);
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
		sql.append("select postPraise.postId  as postId , SUM(badge) as  num from PostPraise postPraise where  postPraise.postId in (");
		// for (int i = 0; i < postIds.size(); i++) {
		// sql.append(":postId" + i + ",");
		// }
		// if (postIds.size() > 0) {
		// sql.deleteCharAt(sql.length() - 1);
		// }
		sql.append(":postIds ");
		sql.append(") and postPraise.status = (:status) group by postPraise.postId ");
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
		sql.append(" update PostPraise set badge=0 where postId in  ");
		sql.append(" (SELECT id FROM Post where memberId =" + memberId + ")  ");
		Query query = entityManager.createQuery(sql.toString());
		query.executeUpdate();
		//

	}
	
	public void updateBadegByPost(Integer postId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" update PostPraise set badge=0 where postId =  ");
		sql.append("  (:postId)  ");
		Query query = entityManager.createQuery(sql.toString());
		query.setParameter("postId", postId);
		query.executeUpdate();
		//

	}

	public Long getBadgeCount(Integer memberId) {
		StringBuffer sql = new StringBuffer();

		sql.append("  select SUM(badge) from PostPraise  where postId in  ");
		sql.append(" (SELECT id FROM Post where memberId = (:memberId)  and status = (:status))  ");
		Query query = entityManager.createQuery(sql.toString());
		query.setParameter("status", PostConstant.PostStatus.active);
		query.setParameter("memberId", memberId);
		List<Long> l = query.getResultList();
		if (l.size() > 0 && l.get(0)!= null)
			return l.get(0);
		else
			return 0l;
	}
	
	@Override
	public void updateBadeg(Integer memberId, Integer id) {
		StringBuffer sql = new StringBuffer();
		sql.append(" update PostPraise set badge=0 where postId in  ");
		sql.append(" (SELECT id FROM Post where memberId =" + memberId + ")  and id <= " + id);
		Query query = entityManager.createQuery(sql.toString());
		query.executeUpdate();

	}

	@Override
	public Integer getMaxId(Integer memberId) {
		StringBuffer sql = new StringBuffer();

		sql.append("  select max(id) from PostPraise  where postId in  ");
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

	/*
	 * public List getCount(List<Integer> postIds) { if (postIds == null) return
	 * Collections.emptyList();
	 * 
	 * StringBuffer sql = new StringBuffer(); sql.append(
	 * "select postPraise.postId  as postId , count(*) as  num from wechat.dbo.post_praise postPraise where  postPraise.postId in ("
	 * ); // for (int i = 0; i < postIds.size(); i++) { // sql.append(":postId"
	 * + i + ","); // } // if (postIds.size() > 0) { //
	 * sql.deleteCharAt(sql.length() - 1); // } sql.append(":postIds ");
	 * sql.append
	 * (") and postPraise.status = (:status) group by postPraise.postId ");
	 * Query query = entityManager.createNativeQuery(sql.toString());
	 * query.unwrap
	 * (SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
	 * query.setParameter("status", PostConstant.PostStatus.active.ordinal());
	 * query.setParameter("postIds" , postIds); // for (int i = 0; i <
	 * postIds.size(); i++) { // query.setParameter("postId" + i,
	 * postIds.get(i)); // } return query.getResultList(); }
	 */
}
