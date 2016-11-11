package com.koch.controller.back;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.koch.base.BaseController;
import com.koch.bean.CustomerData;
import com.koch.bean.Filter;
import com.koch.bean.JsonMessage;
import com.koch.bean.Message;
import com.koch.bean.OrderBy;
import com.koch.bean.Pager;
import com.koch.bean.Filter.Operator;
import com.koch.entity.Article;
import com.koch.entity.ArticleCategory;
import com.koch.entity.Brand;
import com.koch.entity.ExchangeInfo;
import com.koch.entity.ProductCategory;
import com.koch.entity.Tag;
import com.koch.entity.Tag.Type;
import com.koch.service.ArticleCategoryService;
import com.koch.service.ArticleService;
import com.koch.service.ExchangeInfoService;
import com.koch.service.TagService;
import com.koch.util.JsonUtil;
import com.koch.util.GlobalConstant.BooleanType;
/**
 * 礼品兑换管理控制器
 * @author koch
 * @date  2015-05-17
 */
@Controller
@RequestMapping(value="back/exchangeInfo")
public class ExchangeInfoController extends BaseController{
	
	@Resource
	private ExchangeInfoService exchangeInfoService;
	
    @RequestMapping(value="list")
	public String list(ModelMap model){
		return "/back/content/exchange_list";
    }
    
    @RequestMapping(value="list/pager")
    @ResponseBody
   	public String pager(String realName,String phone,Boolean isCollect,Pager<ExchangeInfo> pager){
    	pager = exchangeInfoService.findByPager(realName, phone, isCollect, pager);
    	String result = "[]";
    	if(pager.getList() != null && pager.getList().size()>0){
    		CustomerData data = new CustomerData(pager.getList(), pager.getTotalCount());
			Map<String,String[]> filterMap = new HashMap<String, String[]>();
			filterMap.put("exchangeInfo", new String[]{"id","member","product","productName","score","isCollect","createDate","modifyDate"});
			filterMap.put("member", new String[]{"username","realname","phone"});
			filterMap.put("product", new String[]{"number"});
			result = JsonUtil.toJsonIncludeProperties(data, filterMap);
    	}
    	return result;
    }
    
    @RequestMapping(value="collect")
    @ResponseBody
    public JsonMessage collect(Integer id){
    	ExchangeInfo exchangeInfo = exchangeInfoService.get(id);
    	if(exchangeInfo == null)
    		return save_error;
    	exchangeInfo.setIsCollect(true);
    	exchangeInfoService.update(exchangeInfo);
    	return save_success;
    }
}
