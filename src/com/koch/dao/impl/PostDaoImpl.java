package com.koch.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.koch.bean.Pager;
import com.koch.bean.Pager.OrderType;
import com.koch.dao.PostDao;
import com.koch.entity.Faction;
import com.koch.entity.Member;
import com.koch.entity.Post;
import com.koch.entity.PostSector;
import com.koch.util.PostConstant;

@Repository("postDao")
public class PostDaoImpl extends BaseDaoImpl<Post> implements PostDao {

	public Pager<Post> findList(Integer postSectorId, Integer factionId, Integer memberId,Integer postType, Integer minRow) {
		Pager<Post> pager = new Pager<Post>();
//		pager.setPageSize(10);
		pager.setRows(10);
//		pager.setPage(page);
		pager.setPageNumber(1);
		CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Post> criteriaQuery = criteriaBuilder.createQuery(Post.class);
		Root root = criteriaQuery.from(Post.class);
		criteriaQuery.select(root);
		Predicate predicate = criteriaBuilder.conjunction();
		predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("status"), PostConstant.PostStatus.active) );
		
		if (postSectorId != null && postSectorId != -1) {
			PostSector ps = new PostSector();
			ps.setId(postSectorId);
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("postSector"), ps) );
		}	
		if (factionId != null && factionId != -1) {
			Faction fa = new Faction();
			fa.setId(factionId);
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.join("member").join("faction"), fa) );
		}
		
		if (postType != null && postType != -1) {
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("type"), postType) );
		}
		
		if (memberId != null && memberId != -1) {
			Member m = new Member();
			m.setId(memberId);
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("member"), m) );
		}
		
		if (minRow != null && minRow != -1) {
			predicate = criteriaBuilder.and(predicate,criteriaBuilder.lessThan(root.get("id"), minRow) );
		}
		criteriaQuery.where(predicate);
		
		pager.setOrderBy("id");
		pager.setOrderType(OrderType.desc);
	
		Pager<Post> result = findByPager(criteriaQuery, pager);
		return result;
	}

}
