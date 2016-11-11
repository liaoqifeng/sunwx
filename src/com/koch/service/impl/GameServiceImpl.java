package com.koch.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.LockModeType;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.koch.dao.GameDao;
import com.koch.dao.GameInfoDao;
import com.koch.dao.GameItemDao;
import com.koch.dao.MemberDao;
import com.koch.entity.Game;
import com.koch.entity.GameInfo;
import com.koch.entity.GameItem;
import com.koch.entity.LotteryGift;
import com.koch.entity.Member;
import com.koch.entity.GameItem.HortationType;
import com.koch.service.GameService;
import com.koch.util.LotteryUtil;

@Service
public class GameServiceImpl extends BaseServiceImpl<Game> implements GameService{

	@Resource
	public GameDao gameDao;
	@Resource
	private GameItemDao gameItemDao;
	@Resource
	private GameInfoDao gameInfoDao;
	@Resource
	private MemberDao memberDao;
	
	@Resource
	public void setBaseDao(GameDao gameDao) {
		super.setBaseDao(gameDao);
	}
	
	@Transactional
	public GameItem lottery(Game game, Member m, List<GameInfo> infos){
		//Member m = this.memberDao.get(member.getId());
		this.memberDao.lock(m, LockModeType.PESSIMISTIC_WRITE);
		
		List<GameItem> gameItems = game.getGameItems();
		List<Double> orignalRates = new ArrayList<Double>(gameItems.size());
		for (GameItem gameItem : gameItems) {
			double probability = gameItem.getProbability();
			if (probability < 0) {
				probability = 0;
			}
			if (gameItem.getCount() != null) {// 限数量抽奖
				Integer winningCount = gameItem.getWinningCount() == null ? 0 : gameItem.getWinningCount();
				if (winningCount >= gameItem.getCount() && ((gameItem.getHortationType() == HortationType.virtual && gameItem.getScore().intValue() > 0) || gameItem.getHortationType() == HortationType.physical)) {// 如果数量已经被抽完,则把概率设为0
					probability = 0;
				}
			}
			orignalRates.add(probability);
		}
		int index = LotteryUtil.lottery(orignalRates);
		GameItem item = gameItems.get(index);
		gameItemDao.lock(item, LockModeType.PESSIMISTIC_WRITE);
		if(item.getHortationType() == HortationType.virtual){
			item.setWinningCount((item.getWinningCount() == null ? 0 : item.getWinningCount()) + (item.getScore() == null ? 0 : item.getScore()));
		}else{
			item.setWinningCount((item.getWinningCount() == null ? 0 : item.getWinningCount()) + 1);
		}
		this.gameItemDao.update(item);
		
		GameInfo gameInfo = new GameInfo();
		gameInfo.setGame(game);
		gameInfo.setMember(m);
		gameInfo.setHortationIndex(item.getHortationIndex());
		gameInfo.setHortationTitle(item.getTitle());
		this.gameInfoDao.save(gameInfo);
		
		if(item.getHortationType() == HortationType.virtual){
			m.setScore(m.getScore() + (item.getScore() == null ? 0 : item.getScore()));
		}
		m.setScore(m.getScore() - game.getScore(infos.size()));
		this.memberDao.update(m);
		
		return item;
	}
}
