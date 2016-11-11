package com.koch.service;

import java.util.Date;
import java.util.List;

import com.koch.entity.Member;
import com.koch.entity.Task;
import com.koch.entity.TaskInfo;


public interface TaskInfoService extends BaseService<TaskInfo>{
	public List<TaskInfo> findList(Date beginDate,Date endDate,Task task,Member member);
	public void taskScan(Task task,Member member);
}
