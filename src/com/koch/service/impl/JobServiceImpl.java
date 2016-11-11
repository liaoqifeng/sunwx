package com.koch.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.koch.dao.JobDao;
import com.koch.entity.Job;
import com.koch.service.JobService;

@Service
public class JobServiceImpl extends BaseServiceImpl<Job> implements JobService{
	

	@Resource
	public JobDao jobDao;
	
	@Resource
	public void setBaseDao(JobDao jobDao) {
		super.setBaseDao(jobDao);
	}
	
}
