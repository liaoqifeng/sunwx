package com.koch.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.koch.dao.AutoReplyDao;
import com.koch.entity.AutoReply;
import com.koch.service.AutoReplyService;

@Service
public class AutoReplyServiceImpl extends BaseServiceImpl<AutoReply> implements AutoReplyService{
	@Resource
	private AutoReplyDao autoReplyDao;
	@Resource
	public void setBaseDao(AutoReplyDao autoReplyDao) {
		super.setBaseDao(autoReplyDao);
	}
	
}
