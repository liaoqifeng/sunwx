package com.koch.controller.wechat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.koch.base.BaseController;
import com.koch.bean.WeChatMessage;
import com.koch.entity.Member;
import com.koch.entity.Task;
import com.koch.entity.TaskInfo;
import com.koch.service.AreaService;
import com.koch.service.MemberService;
import com.koch.service.TaskInfoService;
import com.koch.service.TaskService;
import com.koch.util.CookieUtils;
import com.koch.util.GlobalConstant;

@Controller("wcCommonController")
@RequestMapping(value="weixin/common")
public class CommonController extends BaseController{
	@Resource
	private AreaService areaService;
	@Resource
	private MemberService memberService;
	@Resource
	private TaskService taskService;
	@Resource
	private TaskInfoService taskInfoService;
	
	@RequestMapping(value = { "/initAreaSelect" },method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String initAreaSelect(HttpServletRequest request,ModelMap model) {
		return "{\"data\":" + areaService.getTreeJson() + "}";
	}
	
	@RequestMapping(value = { "/taskScan" },method={RequestMethod.GET,RequestMethod.POST})
	public String taskScan(Integer userId, Integer taskId, ModelMap model){
		Task task = taskService.get(taskId);
		Calendar c = Calendar.getInstance();
		Date beginDate = null, endDate = null;
		Integer hour = c.get(Calendar.HOUR_OF_DAY);
		if(task.getBeginTime() != null){
			if(hour < task.getBeginTime()){
				model.addAttribute("message",WeChatMessage.error("任务失败,请查看任务规则说明"));
				return "/weixin/ground/index";
			}
			c.set(Calendar.HOUR_OF_DAY, task.getBeginTime());
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			beginDate = c.getTime();
		}
		if(task.getEndTime() != null){
			if(hour > task.getEndTime()){
				model.addAttribute("message",WeChatMessage.error("任务失败,请查看任务规则说明"));
				return "/weixin/ground/index";
			}
			c.set(Calendar.HOUR_OF_DAY, task.getEndTime());
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			endDate = c.getTime();
		}
		Member member = memberService.get(userId);
		List<TaskInfo> taskInfos = taskInfoService.findList(beginDate, endDate, task, member);
		if(task.getCount() != null){
			if(taskInfos.size() >= task.getCount()){
				model.addAttribute("message",WeChatMessage.error("任务失败,请查看任务规则说明"));
				return "/weixin/ground/index";
			}
		}
		taskInfoService.taskScan(task, member);
		return "/weixin/ground/index";
	}
	
	@RequestMapping(value = { "/doTask" },method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public WeChatMessage doTask(Integer memberId, Integer taskId){
		Task task = taskService.get(taskId);
		Calendar c = Calendar.getInstance();
		Date beginDate = null, endDate = null;
		Integer hour = c.get(Calendar.HOUR_OF_DAY);
		if(task.getBeginTime() != null){
			if(hour < task.getBeginTime()){
				return WeChatMessage.error("任务失败,请查看任务规则说明");
			}
			c.set(Calendar.HOUR_OF_DAY, task.getBeginTime());
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			beginDate = c.getTime();
		}
		if(task.getEndTime() != null){
			if(hour > task.getEndTime()){
				return WeChatMessage.error("任务失败,请查看任务规则说明");
			}
			c.set(Calendar.HOUR_OF_DAY, task.getEndTime());
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			endDate = c.getTime();
		}
		Member member = memberService.get(memberId);
		List<TaskInfo> taskInfos = taskInfoService.findList(beginDate, endDate, task, member);
		if(task.getCount() != null){
			if(taskInfos.size() >= task.getCount()){
				return WeChatMessage.error("任务失败,请查看任务规则说明");
			}
		}
		taskInfoService.taskScan(task, member);
		return WeChatMessage.success("任务完成");
	}
	
	@RequestMapping(value = { "/findScore" },method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Integer findScore(){
		Member member = memberService.get(memberService.getCurrent().getId());
		return member.getScore();
	}
}
