package com.koch.dao;

import com.koch.bean.Pager;
import com.koch.entity.ExchangeInfo;

public interface ExchangeInfoDao extends BaseDao<ExchangeInfo>{
	public Pager<ExchangeInfo> findByPager(String realName,String phone,Boolean isCollect,Pager<ExchangeInfo> pager);
	
}
