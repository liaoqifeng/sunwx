package com.koch.controller.wechat;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.koch.base.BaseController;
import com.koch.service.ArticleService;
/**
 * 文章管理控制器
 * @author koch
 * @date  2015-06-13
 */
@Controller("wcArticleController")
@RequestMapping(value="article")
public class ArticleController extends BaseController{
	@Resource
	private ArticleService articleService;
 
    
    @RequestMapping(value="view/{id}",method={RequestMethod.GET})
	public String get(@PathVariable Integer id,ModelMap model){
    	model.addAttribute("article", articleService.get(id));
    	return "/weixin/article";
    }
    
   
}
