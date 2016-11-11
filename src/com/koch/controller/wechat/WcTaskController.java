package com.koch.controller.wechat;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gson.util.AesException;
import com.gson.util.ConfKit;
import com.gson.util.WechatUtil;
import com.koch.base.BaseController;
import com.koch.bean.OrderBy;
import com.koch.bean.WeChatMessage;
import com.koch.entity.Member;
import com.koch.entity.Task;
import com.koch.entity.TaskInfo;
import com.koch.service.MemberService;
import com.koch.service.TaskInfoService;
import com.koch.service.TaskService;
import com.koch.util.QrcodeUtils;

@Controller("scTaskCoinController")
@RequestMapping(value="weixin/member/task")
public class WcTaskController extends BaseController{
	private static final Integer TASK_SCAN_ID = 1;
	private static final Integer TASK_SHARE_ID = 2;
	private static final Integer TASK_SEND_ID = 3;
	
	@Resource
	private MemberService memberService;
	@Resource
	private TaskService taskService;
	@Resource
	private TaskInfoService taskInfoService;
	
	@RequestMapping(value = { "/index" }, method = { RequestMethod.GET })
	public String index(HttpServletRequest request,ModelMap model) {
		List<Task> tasks = taskService.getAll(new OrderBy("orderList"));
		for(Task task : tasks){
			if(task.getCount() == null){//不限次数任务
				task.setIsComplete(false);
			}else{
				Calendar c = Calendar.getInstance();
				Date beginDate = null,endDate = null;
				if(task.getBeginTime() != null){
					c.set(Calendar.HOUR_OF_DAY, task.getBeginTime());
					c.set(Calendar.MINUTE, 0);
					c.set(Calendar.SECOND, 0);
					beginDate = c.getTime();
				}
				if(task.getEndTime() != null){
					c.set(Calendar.HOUR_OF_DAY, task.getEndTime());
					c.set(Calendar.MINUTE, 0);
					c.set(Calendar.SECOND, 0);
					endDate = c.getTime();
				}
				Member member = memberService.getCurrent();
				List<TaskInfo> taskInfos = taskInfoService.findList(beginDate, endDate, task, member);
				if(taskInfos.size() >= task.getCount()){
					task.setIsComplete(true);
				}
			}
		}
		model.addAttribute("tasks", tasks);
		return "/weixin/task/index";
	}
	
	@RequestMapping(value = { "/scan" }, method = { RequestMethod.GET })
	public String scan(HttpServletRequest request,ModelMap model) throws AesException{
		Task task = taskService.get(TASK_SCAN_ID);
		model.addAttribute("task", task);
		return "/weixin/task/scan";
	}
	
	@RequestMapping(value = { "/gen" })
	public String scan(Integer taskId,HttpServletRequest request,HttpServletResponse response){
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + path + "/";
		String url = (basePath + "weixin/common/taskScan.shtml?userId=" + memberService.getCurrent().getId() + "&taskId="+taskId);
		QrcodeUtils.encoderQRCoder(url, response);
		return null;
	}
	
	@RequestMapping(value = { "/share" }, method = { RequestMethod.GET })
	public String share(HttpServletRequest request,ModelMap model) throws AesException{
		Task task = taskService.get(TASK_SHARE_ID);
		model.addAttribute("task", task);
		
		String basePath = request.getScheme() + "://" + request.getServerName() + request.getContextPath() + "/";
		String url = (basePath + "weixin/member/task/share.shtml");
		String signature = WechatUtil.getSignature(url);
		Map<String, String> params = new HashMap<String, String>();
		params.put("appId", ConfKit.get("AppId"));
		params.put("timestamp", WechatUtil.TIMESTAMP);
		params.put("nonceStr", WechatUtil.NONCESTR);
		params.put("signature", signature);
		model.addAttribute("params", params);
		return "/weixin/task/share";
	}
	
	@RequestMapping(value = { "/topic" }, method = { RequestMethod.GET })
	public String send(HttpServletRequest request,ModelMap model) throws AesException{
		Task task = taskService.get(TASK_SEND_ID);
		model.addAttribute("task", task);
		return "/weixin/task/topic";
	}
	
	@RequestMapping(value = { "/complete" }, method = { RequestMethod.POST })
	@ResponseBody
	public WeChatMessage complete(Integer taskId){
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
		Member member = memberService.get(memberService.getCurrent().getId());
		List<TaskInfo> taskInfos = taskInfoService.findList(beginDate, endDate, task, member);
		if(task.getCount() != null){
			if(taskInfos.size() >= task.getCount()){
				return WeChatMessage.error("任务失败,请查看任务规则说明");
			}
		}
		taskInfoService.taskScan(task, member);
		return WeChatMessage.success("任务完成");
	}
}
