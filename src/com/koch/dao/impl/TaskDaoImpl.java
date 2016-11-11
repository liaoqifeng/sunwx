package com.koch.dao.impl;

import org.springframework.stereotype.Repository;

import com.koch.dao.TaskDao;
import com.koch.entity.Task;

@Repository("taskDao")
public class TaskDaoImpl extends BaseDaoImpl<Task> implements TaskDao{
	
	
}
