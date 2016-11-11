package com.koch.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import antlr.collections.List;

import com.koch.dao.TaskDao;
import com.koch.entity.Member;
import com.koch.entity.Task;
import com.koch.service.TaskService;

@Service
public class TaskServiceImpl extends BaseServiceImpl<Task> implements TaskService{
	
	@Resource
	public TaskDao taskDao;
	
	@Resource
	public void setBaseDao(TaskDao taskDao) {
		super.setBaseDao(taskDao);
	}
	
	
}
