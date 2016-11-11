package com.koch.service;

import java.util.List;

import com.koch.bean.WeChatMessage;
import com.koch.entity.PostJoin;
import com.koch.entity.ScoreLog;

public interface ScoreLogService extends BaseService<ScoreLog> {

	public Integer updateScore(ScoreLog scoreLog);

}
