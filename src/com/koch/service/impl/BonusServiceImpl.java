package com.koch.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gson.util.MoneyUtils;
import com.koch.dao.BonusDao;
import com.koch.dao.BonusInfoDao;
import com.koch.entity.Bonus;
import com.koch.entity.BonusInfo;
import com.koch.entity.Member;
import com.koch.service.BonusService;

@Service
public class BonusServiceImpl extends BaseServiceImpl<Bonus> implements BonusService{

	@Resource
	public BonusDao bonusDao;
	@Resource
	public BonusInfoDao bonusInfoDao;
	
	@Resource
	public void setBaseDao(BonusDao bonusDao) {
		super.setBaseDao(bonusDao);
	}
	
	@Transactional(readOnly=true)
	public Bonus findActiveBonus(){
		return bonusDao.findActiveBonus();
	}
	
	@Transactional
	public void sendBonus(Member member, Bonus bonus, Double amount){
		/*BonusInfo b = new BonusInfo();
		String no = MoneyUtils.getOrderNo();
		b.setMchBillno(no);
		b.setSendListid(member.getWechatCode());
		b.setSendTime(sendTime)*/
	}
}
