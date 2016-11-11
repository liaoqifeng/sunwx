package com.koch.service;

import com.koch.entity.Bonus;


public interface BonusService extends BaseService<Bonus>{
	public Bonus findActiveBonus();
}
