package com.koch.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.koch.dao.DugCoinInfoDao;
import com.koch.entity.DugCoin;
import com.koch.entity.DugCoinInfo;
import com.koch.entity.Member;
import com.koch.service.DugCoinInfoService;

@Service
public class DugCoinInfoServiceImpl extends BaseServiceImpl<DugCoinInfo> implements DugCoinInfoService{

	@Resource
	public DugCoinInfoDao dugCoinInfoDao;
	
	@Resource
	public void setBaseDao(DugCoinInfoDao dugCoinInfoDao) {
		super.setBaseDao(dugCoinInfoDao);
	}
	
	@Transactional(readOnly=true)
	public List<DugCoinInfo> findList(DugCoin dugCoin,Member member){
		return dugCoinInfoDao.findList(dugCoin, member);
	}
}
