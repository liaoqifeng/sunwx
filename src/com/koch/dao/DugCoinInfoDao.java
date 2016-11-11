package com.koch.dao;

import java.util.List;

import com.koch.entity.DugCoin;
import com.koch.entity.DugCoinInfo;
import com.koch.entity.Member;

public interface DugCoinInfoDao extends BaseDao<DugCoinInfo>{
	public List<DugCoinInfo> findList(DugCoin dugCoin,Member member);
}
