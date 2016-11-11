package com.koch.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.koch.bean.Pager;
import com.koch.dao.ExchangeInfoDao;
import com.koch.entity.Brand;
import com.koch.entity.ExchangeInfo;
import com.koch.entity.Product;
import com.koch.entity.ProductCategory;

@Repository("exchangeInfoDao")
public class ExchangeInfoDaoImpl extends BaseDaoImpl<ExchangeInfo> implements ExchangeInfoDao{
	
	public Pager<ExchangeInfo> findByPager(String realName,String phone,Boolean isCollect,Pager<ExchangeInfo> pager) {
		CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(ExchangeInfo.class);
		Root root = criteriaQuery.from(ExchangeInfo.class);
		criteriaQuery.select(root);
		Predicate predicate = criteriaBuilder.conjunction();
		if(StringUtils.isNotEmpty(realName))
			predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("member").get("realname"), realName));
		if(StringUtils.isNotEmpty(phone))
			predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("member").get("phone"), phone));
		if(isCollect != null)
			predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("isCollect"), isCollect));
		criteriaQuery.where(predicate);
		return super.findByPager(criteriaQuery, pager);
	}
}
