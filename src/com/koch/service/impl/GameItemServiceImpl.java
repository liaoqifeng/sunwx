package com.koch.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.koch.dao.GameItemDao;
import com.koch.entity.GameItem;
import com.koch.service.GameItemService;

@Service
public class GameItemServiceImpl extends BaseServiceImpl<GameItem> implements GameItemService{

	@Resource
	public GameItemDao gameItemDao;
	
	@Resource
	public void setBaseDao(GameItemDao gameItemDao) {
		super.setBaseDao(gameItemDao);
	}
	
}
