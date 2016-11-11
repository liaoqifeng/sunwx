package com.koch.service;

import java.util.List;

import com.koch.entity.Bonus;
import com.koch.entity.BonusInfo;
import com.koch.entity.Member;


public interface BonusInfoService extends BaseService<BonusInfo>{
	public List<BonusInfo> findBonusInfo(Member member, Bonus bonus);
}
