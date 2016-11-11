package com.koch.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.koch.dao.BonusInfoDao;
import com.koch.entity.Bonus;
import com.koch.entity.BonusInfo;
import com.koch.entity.Member;
import com.koch.service.BonusInfoService;

@Service
public class BonusInfoServiceImpl extends BaseServiceImpl<BonusInfo> implements BonusInfoService{

	@Resource
	public BonusInfoDao bonusInfoDao;
	
	@Resource
	public void setBaseDao(BonusInfoDao bonusInfoDao) {
		super.setBaseDao(bonusInfoDao);
	}
	
	public List<BonusInfo> findBonusInfo(Member member, Bonus bonus){
		return this.bonusInfoDao.findBonusInfo(member, bonus);
	}
}
