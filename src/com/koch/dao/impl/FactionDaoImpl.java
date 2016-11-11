package com.koch.dao.impl;

import org.springframework.stereotype.Repository;

import com.koch.dao.FactionDao;
import com.koch.entity.Faction;

@Repository("factionDao")
public class FactionDaoImpl extends BaseDaoImpl<Faction> implements FactionDao{
	
}
