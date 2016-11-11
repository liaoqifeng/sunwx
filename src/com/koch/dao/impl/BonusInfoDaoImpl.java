package com.koch.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.koch.dao.BonusInfoDao;
import com.koch.entity.Bonus;
import com.koch.entity.BonusInfo;
import com.koch.entity.Member;

@Repository("bonusInfoDao")
public class BonusInfoDaoImpl extends BaseDaoImpl<BonusInfo> implements BonusInfoDao{
	public List<BonusInfo> findBonusInfo(Member member, Bonus bonus){
		String hql = "from BonusInfo as model where model.member=? and model.bonus=?";
		List<BonusInfo> list = entityManager.createQuery(hql).setParameter(1, member).setParameter(2, bonus).getResultList();
		return list;
	}
}
