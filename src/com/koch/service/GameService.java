package com.koch.service;

import java.util.List;

import com.koch.entity.Game;
import com.koch.entity.GameInfo;
import com.koch.entity.GameItem;
import com.koch.entity.Member;


public interface GameService extends BaseService<Game>{
	public GameItem lottery(Game game, Member member, List<GameInfo> infos);
}
