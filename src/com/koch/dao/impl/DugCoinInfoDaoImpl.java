package com.koch.dao.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.koch.dao.DugCoinInfoDao;
import com.koch.entity.DugCoin;
import com.koch.entity.DugCoinInfo;
import com.koch.entity.Member;
import com.koch.entity.Task;
import com.koch.entity.TaskInfo;
import com.koch.util.DateUtil;

@Repository("dugCoinInfoDao")
public class DugCoinInfoDaoImpl extends BaseDaoImpl<DugCoinInfo> implements DugCoinInfoDao{
	public List<DugCoinInfo> findList(DugCoin dugCoin,Member member){
		CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<DugCoinInfo> criteriaQuery = criteriaBuilder.createQuery(DugCoinInfo.class);
		Root root = criteriaQuery.from(DugCoinInfo.class);
		criteriaQuery.select(root);
		Predicate predicate = criteriaBuilder.conjunction();
		Calendar c = Calendar.getInstance();
		c.setTime(dugCoin.getCurrentPoint());
		c.add(Calendar.MINUTE, dugCoin.getLastTime());
		Date endDate = c.getTime();
		predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("createDate"), dugCoin.getCurrentPoint()));
		predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("createDate"), endDate));
		predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("member"), member));
		criteriaQuery.where(predicate);
		return this.entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT).getResultList();
	}
}
