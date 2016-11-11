package com.koch.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.koch.dao.BonusDao;
import com.koch.entity.Bonus;

@Repository("bonusDao")
public class BonusDaoImpl extends BaseDaoImpl<Bonus> implements BonusDao{
	public Bonus findActiveBonus(){
		Calendar c = Calendar.getInstance();
		Date date = c.getTime();
		CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Bonus> criteriaQuery = criteriaBuilder.createQuery(Bonus.class);
		Root root = criteriaQuery.from(Bonus.class);
		criteriaQuery.select(root);
		Predicate predicate = criteriaBuilder.conjunction();
		predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("beginDate"),date));
		predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"),date));
		predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("isPublish"), true));
		criteriaQuery.where(predicate);
		List<Bonus> s = this.entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT).getResultList();
		if(CollectionUtils.isNotEmpty(s)){
			return s.get(0);
		}
		return null;
	}
}
