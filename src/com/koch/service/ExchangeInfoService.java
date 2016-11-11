package com.koch.service;

import com.koch.bean.Pager;
import com.koch.entity.ExchangeInfo;
import com.koch.entity.Member;
import com.koch.entity.Product;
import com.koch.entity.Receiver;


public interface ExchangeInfoService extends BaseService<ExchangeInfo>{
	public void exchange(Product product,Receiver receiver,Member member);
	public Pager<ExchangeInfo> findByPager(String realName,String phone,Boolean isCollect,Pager<ExchangeInfo> pager);
}
