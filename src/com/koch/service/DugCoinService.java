package com.koch.service;

import com.koch.entity.DugCoin;
import com.koch.entity.Member;


public interface DugCoinService extends BaseService<DugCoin>{
	public void dug(Member member,Integer score);
}
