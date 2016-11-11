package com.koch.controller.back;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.annotation.Resource;

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
import com.koch.bean.Pager;
import com.koch.bean.Filter.Operator;
import com.koch.entity.Bonus;
import com.koch.entity.Brand;
import com.koch.entity.Brand.BrandType;
import com.koch.service.BonusService;
import com.koch.util.DateUtil;
import com.koch.util.JsonUtil;
/**
 * 红包管理控制器
 * @author koch
 * @date  2015-07-23
 */
@Controller
@RequestMapping(value="back/bonus")
public class BonusController extends BaseController{
	@Resource
	private BonusService bonusService;
	
	@RequestMapping(value="list")
	public String list(){
		return "/back/content/bonus_list";
    }
	
	@RequestMapping(value="list/pager")
	@ResponseBody
	public String pager(Boolean isPublish,Pager<Bonus> pager){
		if(isPublish != null){
			pager.getFilters().add(new Filter("isPublish", Operator.eq, isPublish));
		}
		pager = bonusService.findByPage(pager);
    	String result = "[]";
    	if(pager.getList() != null && pager.getList().size()>0){
    		result = JsonUtil.toJson(new CustomerData(pager.getList(), pager.getTotalCount()),"yyyy-MM-dd HH:mm:ss");
    	}
    	return result;
    }
	
	@RequestMapping(value="view")
	public String view(Integer id,ModelMap modelMap){
		return "/back/content/bonus_add";
	}
	
	@RequestMapping(value="add")
    public String add(Bonus bonus,RedirectAttributes redirectAttributes){
		bonusService.save(bonus);
    	setRedirectAttributes(redirectAttributes, "Common.save.success");
    	return "redirect:/back/bonus/list.shtml";
    }
	
	@RequestMapping(value="edit/{id}",method={RequestMethod.GET})
	public String get(@PathVariable Integer id, ModelMap model){
		model.addAttribute("bonus", bonusService.get(id));
		return "/back/content/bonus_edit";
	}
	
	@RequestMapping(value="edit")
	public String edit(Bonus bonus,RedirectAttributes redirectAttributes){
		Bonus old = bonusService.get(bonus.getId());
		BeanUtils.copyProperties(bonus, old, new String[]{"id","createDate","modifyDate","bonusInfos"});
		bonusService.update(old);
    	setRedirectAttributes(redirectAttributes, "Common.update.success");
    	return "redirect:/back/bonus/list.shtml";
    }
}
