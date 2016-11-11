package com.koch.service;

import java.util.Date;
import java.util.List;

import com.koch.entity.Game;
import com.koch.entity.GameInfo;
import com.koch.entity.Member;


public interface GameInfoService extends BaseService<GameInfo>{
	public List<GameInfo> findList(Date beginDate,Date endDate,Game game,Member member);
}
