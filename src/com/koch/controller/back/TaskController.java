package com.koch.controller.back;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.koch.base.BaseController;
import com.koch.bean.CustomerData;
import com.koch.bean.Pager;
import com.koch.entity.Task;
import com.koch.entity.Task.TaskCycle;
import com.koch.service.TaskService;
import com.koch.util.JsonUtil;
/**
 * 任务管理控制器
 * @author koch
 * @date  2015-06-7
 */
@Controller
@RequestMapping(value="back/task")
public class TaskController extends BaseController{
	
	@Resource
	private TaskService taskService;
	
    @RequestMapping(value="list")
	public String list(ModelMap model){
		return "/back/content/task_list";
    }
    
    @RequestMapping(value="list/pager")
    @ResponseBody
   	public String pager(Pager<Task> pager){
    	pager = taskService.findByPage(pager);
    	String result = "[]";
    	if(pager.getList() != null && pager.getList().size()>0){
    		result = JsonUtil.toJson(new CustomerData(pager.getList(), pager.getTotalCount()));
    	}
    	return result;
    }
    
    @RequestMapping(value="edit/{id}",method={RequestMethod.GET})
	public String get(@PathVariable Integer id,ModelMap model){
    	model.addAttribute("task", taskService.get(id));
    	model.addAttribute("cycles", TaskCycle.values());
    	return "/back/content/task_edit";
    }
    
    @RequestMapping(value="edit",method={RequestMethod.POST})
    public String edit(Task task,RedirectAttributes redirectAttributes) {
    	if(task == null){
			return "redirect:/back/task/list.shtml";
    	}
    	taskService.update(task);
    	setRedirectAttributes(redirectAttributes, "Common.update.success");
    	return "redirect:/back/task/list.shtml";
    }

}
