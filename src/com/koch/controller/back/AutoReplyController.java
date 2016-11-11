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
import com.koch.bean.Filter;
import com.koch.bean.JsonMessage;
import com.koch.bean.Pager;
import com.koch.bean.Filter.Operator;
import com.koch.entity.AutoReply;
import com.koch.service.AutoReplyService;
import com.koch.util.JsonUtil;
/**
 * 自动回复管理控制器
 * @author koch
 * @date  2015-08-26
 */
@Controller
@RequestMapping(value="back/autoReply")
public class AutoReplyController extends BaseController{
	@Resource
	private AutoReplyService autoReplyService;
	
	
    @RequestMapping(value="list")
	public String list(ModelMap model){
		return "/back/content/reply_list";
    }
    
    @RequestMapping(value="list/pager")
    @ResponseBody
   	public String pager(String keyword,Pager<AutoReply> pager){
    	if(keyword != null){
    		pager.getFilters().add(new Filter("keyword", Operator.like, keyword));
    	}
    	pager = autoReplyService.findByPage(pager);
    	String result = "[]";
    	if(pager.getList() != null && pager.getList().size()>0){
    		CustomerData data = new CustomerData(pager.getList(), pager.getTotalCount());
			result = JsonUtil.toJson(data);
    	}
    	return result;
    }
    
    @RequestMapping(value="view",method={RequestMethod.GET})
    public String view(ModelMap model){
    	return "/back/content/reply_add";
    }
    
    @RequestMapping(value="add",method={RequestMethod.POST})
    public String add(AutoReply autoReply, RedirectAttributes redirectAttributes){
    	autoReplyService.save(autoReply);
    	setRedirectAttributes(redirectAttributes, "Common.save.success");
    	return "redirect:/back/autoReply/list.shtml";
    }
    
    @RequestMapping(value="edit/{id}",method={RequestMethod.GET})
	public String get(@PathVariable Integer id,ModelMap model){
    	model.addAttribute("reply", autoReplyService.get(id));
    	return "/back/content/reply_edit";
    }
    
    @RequestMapping(value="edit",method={RequestMethod.POST})
	public String edit(AutoReply autoReply,RedirectAttributes redirectAttributes){
    	autoReplyService.update(autoReply);
    	setRedirectAttributes(redirectAttributes, "Common.update.success");
    	return "redirect:/back/autoReply/list.shtml";
    }
    
    @RequestMapping(value="delete",method={RequestMethod.POST})
    @ResponseBody
    public JsonMessage delete(Integer [] id){
    	autoReplyService.delete(id);
    	return delete_success;
    }
}
