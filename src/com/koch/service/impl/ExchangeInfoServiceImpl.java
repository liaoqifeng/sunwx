package com.koch.service.impl;

import javax.annotation.Resource;
import javax.persistence.LockModeType;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.koch.bean.Pager;
import com.koch.dao.ExchangeInfoDao;
import com.koch.dao.MemberDao;
import com.koch.dao.ProductDao;
import com.koch.dao.TaskDao;
import com.koch.entity.ExchangeInfo;
import com.koch.entity.Member;
import com.koch.entity.Product;
import com.koch.entity.Receiver;
import com.koch.service.ExchangeInfoService;

@Service
public class ExchangeInfoServiceImpl extends BaseServiceImpl<ExchangeInfo> implements ExchangeInfoService{

	@Resource
	private MemberDao memberDao;
	@Resource
	private ProductDao productDao;
	
	@Resource
	private ExchangeInfoDao exchangeInfoDao;
	@Resource
	public void setBaseDao(ExchangeInfoDao exchangeInfoDao) {
		super.setBaseDao(exchangeInfoDao);
	}
	
	@Transactional
	public void exchange(Product product,Receiver receiver,Member member){
		memberDao.lock(member, LockModeType.PESSIMISTIC_WRITE);
		member.setScore(member.getScore() - product.getCoinPrice().intValue());
		memberDao.update(member);
		
		productDao.lock(product, LockModeType.PESSIMISTIC_WRITE);
		product.setStock(product.getStock() - 1);
		productDao.update(product);
		
		ExchangeInfo e = new ExchangeInfo();
		e.setProduct(product);
		e.setMember(member);
		e.setReceiver(receiver);
		exchangeInfoDao.save(e);
	}
	
	public Pager<ExchangeInfo> findByPager(String realName,String phone,Boolean isCollect,Pager<ExchangeInfo> pager){
		return this.exchangeInfoDao.findByPager(realName, phone, isCollect, pager);
	}
}
