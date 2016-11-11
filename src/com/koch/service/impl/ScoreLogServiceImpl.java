package com.koch.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.koch.dao.MemberDao;
import com.koch.dao.ScoreLogDao;
import com.koch.entity.Member;
import com.koch.entity.ScoreLog;
import com.koch.service.ScoreLogService;

@Service
public class ScoreLogServiceImpl extends BaseServiceImpl<ScoreLog> implements ScoreLogService {
	@Resource
	private ScoreLogDao scoreLogDao;

	@Resource
	private MemberDao memberDao;

	@Resource
	public void setBaseDao(ScoreLogDao scoreLogDao) {
		super.setBaseDao(scoreLogDao);
	}

	@Override
	@Transactional
	public Integer updateScore(ScoreLog scoreLog) {
		Member member = memberDao.get(scoreLog.getMember().getId());
		Integer score = member.getScore() + scoreLog.getScore();
		member.setScore(score);
		memberDao.update(member);
		Integer code = scoreLogDao.save(scoreLog);
		return code;
	}

}
