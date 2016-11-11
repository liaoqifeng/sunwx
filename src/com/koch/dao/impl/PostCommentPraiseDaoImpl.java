package com.koch.dao.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.koch.dao.PostCommentPraiseDao;
import com.koch.entity.Member;
import com.koch.entity.PostCommentPraise;
import com.koch.util.PostConstant;

@Repository("postCommentPraiseDao")
public class PostCommentPraiseDaoImpl extends BaseDaoImpl<PostCommentPraise> implements PostCommentPraiseDao {

	public List getCount(List<Integer> postIds) {
		if (postIds == null)
			return Collections.emptyList();

		StringBuffer sql = new StringBuffer();
		sql.append("select postPraise.postCommentId  as postId , count(*) as  num from PostCommentPraise postPraise where  postPraise.postCommentId in (");
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
		sql.append("select postPraise.postCommentId  as postId from PostCommentPraise postPraise where  postPraise.postCommentId in (");
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
