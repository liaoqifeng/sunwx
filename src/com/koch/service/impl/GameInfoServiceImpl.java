package com.koch.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.koch.dao.GameInfoDao;
import com.koch.dao.MemberDao;
import com.koch.entity.Game;
import com.koch.entity.GameInfo;
import com.koch.entity.Member;
import com.koch.service.GameInfoService;

@Service
public class GameInfoServiceImpl extends BaseServiceImpl<GameInfo> implements GameInfoService{
	
	@Resource
	private GameInfoDao gameInfoDao;
	@Resource
	private MemberDao memberDao;
	
	@Resource
	public void setBaseDao(GameInfoDao gameInfoDao) {
		super.setBaseDao(gameInfoDao);
	}
	
	@Transactional(readOnly=true)
	public List<GameInfo> findList(Date beginDate,Date endDate,Game game,Member member){
		return gameInfoDao.findList(beginDate, endDate, game, member);
	}
	
}
