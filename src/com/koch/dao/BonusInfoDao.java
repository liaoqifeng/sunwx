package com.koch.dao;

import java.util.List;

import com.koch.entity.Bonus;
import com.koch.entity.BonusInfo;
import com.koch.entity.Member;

public interface BonusInfoDao extends BaseDao<BonusInfo>{
	public List<BonusInfo> findBonusInfo(Member member, Bonus bonus);
}
