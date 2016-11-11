package com.koch.service.impl;

import javax.annotation.Resource;
import javax.persistence.LockModeType;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.koch.dao.DugCoinDao;
import com.koch.dao.MemberDao;
import com.koch.entity.DugCoin;
import com.koch.entity.Member;
import com.koch.service.DugCoinService;

@Service
public class DugCoinServiceImpl extends BaseServiceImpl<DugCoin> implements DugCoinService{

	@Resource
	private DugCoinDao dugCoinDao;
	@Resource
	private MemberDao memberDao;
	
	@Resource
	public void setBaseDao(DugCoinDao dugCoinDao) {
		super.setBaseDao(dugCoinDao);
	}
	
	@Transactional
	public void dug(Member member,Integer score){
		this.memberDao.lock(member, LockModeType.PESSIMISTIC_WRITE);
		member.setScore(member.getScore() + score);
		this.memberDao.update(member);
	}
}
