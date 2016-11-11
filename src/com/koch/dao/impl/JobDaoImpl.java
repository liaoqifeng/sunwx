package com.koch.dao.impl;

import org.springframework.stereotype.Repository;

import com.koch.dao.JobDao;
import com.koch.entity.Job;

@Repository("jobDao")
public class JobDaoImpl extends BaseDaoImpl<Job> implements JobDao{
	
}
