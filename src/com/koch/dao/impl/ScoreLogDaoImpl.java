package com.koch.dao.impl;

import org.springframework.stereotype.Repository;

import com.koch.dao.PostJoinDao;
import com.koch.dao.ScoreLogDao;
import com.koch.entity.PostJoin;
import com.koch.entity.ScoreLog;

@Repository("scoreLogDao")
public class ScoreLogDaoImpl extends BaseDaoImpl<ScoreLog> implements ScoreLogDao {


}
