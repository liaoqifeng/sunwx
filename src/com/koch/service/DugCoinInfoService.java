package com.koch.service;

import java.util.List;

import com.koch.entity.DugCoin;
import com.koch.entity.DugCoinInfo;
import com.koch.entity.Member;


public interface DugCoinInfoService extends BaseService<DugCoinInfo>{
	public List<DugCoinInfo> findList(DugCoin dugCoin,Member member);
}
