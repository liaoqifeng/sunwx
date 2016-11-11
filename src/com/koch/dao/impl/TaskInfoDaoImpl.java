package com.koch.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.koch.dao.TaskInfoDao;
import com.koch.entity.Member;
import com.koch.entity.Product;
import com.koch.entity.Task;
import com.koch.entity.TaskInfo;

@Repository("taskInfoDao")
public class TaskInfoDaoImpl extends BaseDaoImpl<TaskInfo> implements TaskInfoDao{
	
	public List<TaskInfo> findList(Date beginDate,Date endDate,Task task,Member member){
		CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<TaskInfo> criteriaQuery = criteriaBuilder.createQuery(TaskInfo.class);
		Root root = criteriaQuery.from(TaskInfo.class);
		criteriaQuery.select(root);
		Predicate predicate = criteriaBuilder.conjunction();
		if (beginDate != null) {
			predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("createDate"), beginDate));
		}
		if (endDate != null) {
			predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("createDate"), endDate));
		}
		predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("task"), task));
		predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("member"), member));
		criteriaQuery.where(predicate);
		return this.entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT).getResultList();
	}
}
