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
import com.koch.bean.JsonMessage;
import com.koch.bean.Pager;
import com.koch.bean.Pager.OrderType;
import com.koch.entity.Job;
import com.koch.entity.PostSector;
import com.koch.service.PostSectorService;
import com.koch.util.JsonUtil;
/**
 * 职位控制器
 * @author koch
 * @date  2015-06-12
 */
@Controller
@RequestMapping(value="back/sector")
public class SectorController extends BaseController{
	@Resource
	private PostSectorService postSectorService;
	
    @RequestMapping(value="list")
	public String list(){
		return "/back/member/sector_list";
    }
    
    @RequestMapping(value="list/pager")
    @ResponseBody
	public String pager(String name,Pager pager){
    	pager.setOrderBy("index");
    	pager.setOrderType(OrderType.asc);
    	pager = postSectorService.findByPage(pager);
    	String result = "[]";
    	if(pager.getList() != null && pager.getList().size()>0){
    		result = JsonUtil.toJsonIgnoreProperties(new CustomerData(pager.getList(), pager.getTotalCount()), "post_sector", new String[]{});
    	}
    	return result;
    }
    
	@RequestMapping(value="view")
    public String view(){
    	return "/back/member/sector_add";
    }
    
	@RequestMapping(value="add",method={RequestMethod.POST})
    public String add(PostSector sector,RedirectAttributes redirectAttributes){
		postSectorService.save(sector);
    	setRedirectAttributes(redirectAttributes, "Common.save.success");
    	return "redirect:/back/sector/list.shtml";
    }
    
    @RequestMapping(value="edit/{id}")
	public String get(@PathVariable Integer id,ModelMap modelMap){
    	PostSector sector = postSectorService.get(id);
    	modelMap.addAttribute("sector",sector);
    	return "/back/member/sector_edit";
    }
    
	@RequestMapping(value="edit",method={RequestMethod.POST})
    public String edit(PostSector sector,RedirectAttributes redirectAttributes){
		postSectorService.update(sector);
    	setRedirectAttributes(redirectAttributes, "Common.update.success");
    	return "redirect:/back/sector/list.shtml";
    }
	
	@RequestMapping(value="delete",method={RequestMethod.POST})
	@ResponseBody
    public JsonMessage delete(Integer [] id){
		if (id != null) {
			this.postSectorService.delete(id);
		}
		return delete_success;
    }
}
