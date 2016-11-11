package com.koch.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.LockModeType;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.koch.dao.MemberDao;
import com.koch.dao.TaskInfoDao;
import com.koch.entity.Member;
import com.koch.entity.Task;
import com.koch.entity.TaskInfo;
import com.koch.service.TaskInfoService;

@Service
public class TaskInfoServiceImpl extends BaseServiceImpl<TaskInfo> implements TaskInfoService{
	
	@Resource
	private TaskInfoDao taskInfoDao;
	@Resource
	private MemberDao memberDao;
	
	@Resource
	public void setBaseDao(TaskInfoDao taskInfoDao) {
		super.setBaseDao(taskInfoDao);
	}
	
	@Transactional(readOnly=true)
	public List<TaskInfo> findList(Date beginDate,Date endDate,Task task,Member member){
		return taskInfoDao.findList(beginDate, endDate, task, member);
	}
	
	@Transactional
	public void taskScan(Task task,Member member){
		memberDao.lock(member, LockModeType.PESSIMISTIC_WRITE);
		member.setScore(member.getScore() + task.getScore());
		memberDao.update(member);
		
		TaskInfo taskInfo = new TaskInfo();
		taskInfo.setMember(member);
		taskInfo.setTask(task);
		taskInfoDao.save(taskInfo);
	}
}
