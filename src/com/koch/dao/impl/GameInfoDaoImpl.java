package com.koch.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.koch.dao.GameInfoDao;
import com.koch.entity.Game;
import com.koch.entity.GameInfo;
import com.koch.entity.Member;

@Repository("gameInfoDao")
public class GameInfoDaoImpl extends BaseDaoImpl<GameInfo> implements GameInfoDao{
	
	public List<GameInfo> findList(Date beginDate,Date endDate,Game game,Member member){
		CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<GameInfo> criteriaQuery = criteriaBuilder.createQuery(GameInfo.class);
		Root root = criteriaQuery.from(GameInfo.class);
		criteriaQuery.select(root);
		Predicate predicate = criteriaBuilder.conjunction();
		if (beginDate != null) {
			predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("createDate"), beginDate));
		}
		if (endDate != null) {
			predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("createDate"), endDate));
		}
		predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("game"), game));
		predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("member"), member));
		criteriaQuery.where(predicate);
		return this.entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT).getResultList();
	}
}
