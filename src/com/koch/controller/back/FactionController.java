package com.koch.controller.back;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
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
import com.koch.bean.Pager.OrderType;
import com.koch.entity.Faction;
import com.koch.entity.Question;
import com.koch.service.FactionService;
import com.koch.service.QuestionService;
import com.koch.util.JsonUtil;
/**
 * 帮派控制器
 * @author koch
 * @date  2015-06-12
 */
@Controller
@RequestMapping(value="back/faction")
public class FactionController extends BaseController{
	@Resource
	private FactionService factionService;
	
    @RequestMapping(value="list")
	public String list(){
		return "/back/member/faction_list";
    }
    
    @RequestMapping(value="list/pager")
    @ResponseBody
	public String pager(String name,Pager pager){
    	pager.setOrderBy("index");
    	pager.setOrderType(OrderType.asc);
    	pager = factionService.findByPage(pager);
    	String result = "[]";
    	if(pager.getList() != null && pager.getList().size()>0){
    		result = JsonUtil.toJsonIgnoreProperties(new CustomerData(pager.getList(), pager.getTotalCount()), "faction", new String[]{});
    	}
    	return result;
    }
    
	@RequestMapping(value="view")
    public String view(){
    	return "/back/member/faction_add";
    }
    
	@RequestMapping(value="add",method={RequestMethod.POST})
    public String add(Faction faction,RedirectAttributes redirectAttributes){
		factionService.save(faction);
    	setRedirectAttributes(redirectAttributes, "Common.save.success");
    	return "redirect:/back/faction/list.shtml";
    }
    
    @RequestMapping(value="edit/{id}")
	public String get(@PathVariable Integer id,ModelMap modelMap){
    	Faction faction = factionService.get(id);
    	modelMap.addAttribute("faction",faction);
    	return "/back/member/faction_edit";
    }
    
	@RequestMapping(value="edit",method={RequestMethod.POST})
    public String edit(Faction faction,RedirectAttributes redirectAttributes){
		factionService.update(faction);
    	setRedirectAttributes(redirectAttributes, "Common.update.success");
    	return "redirect:/back/faction/list.shtml";
    }
	
	@RequestMapping(value="delete",method={RequestMethod.POST})
	@ResponseBody
    public JsonMessage delete(Integer [] id){
		if (id != null) {
			this.factionService.delete(id);
		}
		return delete_success;
    }
}
