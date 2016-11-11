package com.koch.dao;

import com.koch.entity.Bonus;

public interface BonusDao extends BaseDao<Bonus>{
	public Bonus findActiveBonus();
}
