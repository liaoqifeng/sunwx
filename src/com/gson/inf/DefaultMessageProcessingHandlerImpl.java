/**
 * 微信公众平台开发模式(JAVA) SDK
 * (c) 2012-2013 ____′↘夏悸 <wmails@126.cn>, MIT Licensed
 * http://www.jeasyuicn.com/wechat
 */
package com.gson.inf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.gson.bean.Article;
import com.gson.bean.Articles;
import com.gson.bean.InMessage;
import com.gson.bean.NewsOutMessage;
import com.gson.bean.OutMessage;
import com.gson.util.ContantValue;
import com.gson.util.MessageUtil;
import com.koch.bean.Filter;
import com.koch.bean.Pager;
import com.koch.bean.Filter.Operator;
import com.koch.bean.Pager.OrderType;
import com.koch.entity.ArticleCategory;
import com.koch.service.ArticleCategoryService;
import com.koch.service.ArticleService;
import com.koch.util.SpringUtil;
@Component
public class DefaultMessageProcessingHandlerImpl implements MessageProcessingHandler {

	public static Map<String, String> reSchedulingCache = new HashMap<String, String>();
	
	private OutMessage outMessage;

	@Override
	public void allType(InMessage msg) {
		
	}

	@Override
	public void textTypeMsg(InMessage msg) {
		if(reSchedulingCache.get(msg.getMsgId().toString()) == null){
			reSchedulingCache.put(msg.getMsgId().toString(), msg.getMsgId().toString());
			//String m = "您好，我们的自动回复功能还在开发中，敬请期待，如有任何建议或者意见，请随时联系我们的客服QQ：3278186579，我们会尽快给您回复！";
			//MessageUtil.sendText(msg.getFromUserName(), m);
			
			Integer articleCategoryId = 3;
			ArticleService articleService = SpringUtil.getBean("articleServiceImpl", ArticleService.class);
			ArticleCategoryService articleCategoryService = SpringUtil.getBean("articleCategoryServiceImpl", ArticleCategoryService.class);
			ArticleCategory articleCategory = articleCategoryService.get(articleCategoryId);
			Pager<com.koch.entity.Article> pager = new Pager<com.koch.entity.Article>();
			pager.setRows(4);
			pager.setOrderBy("createDate");
			pager.setOrderType(OrderType.desc);
			pager.getFilters().add(new Filter("articleCategory", Operator.eq, articleCategory));
			pager.getFilters().add(new Filter("isPublish", Operator.eq, true));
	    	pager = articleService.findByPage(pager);
	    	if(pager.getList() != null && pager.getList().size() > 0){
	    		List<Articles> alist = MessageUtil.initArticleMessage(pager.getList());
		    	NewsOutMessage out = new NewsOutMessage();
				out.setArticles(alist);
				setOutMessage(out);
	    	}
			
		}else{
			MessageUtil.sendText(msg.getFromUserName(), "");
			reSchedulingCache.remove(msg.getMsgId().toString());
		}
	}

	@Override
	public void locationTypeMsg(InMessage msg) {
	}

	@Override
	public void imageTypeMsg(InMessage msg) {
	}

	@Override
	public void videoTypeMsg(InMessage msg) {
	}

	@Override
	public void voiceTypeMsg(InMessage msg) {
	}

	@Override
	public void linkTypeMsg(InMessage msg) {
	}

	@Override
	public void verifyTypeMsg(InMessage msg) {
	}

	@Override
	public void eventTypeMsg(InMessage msg) {
		if(msg.getEvent().equals(ContantValue.EVENT_SUBSCRIBE)){
			if(reSchedulingCache.get(msg.getFromUserName()+msg.getCreateTime()) == null){
				reSchedulingCache.put(msg.getFromUserName()+msg.getCreateTime(), msg.getFromUserName()+msg.getCreateTime());
				MessageUtil.sendText(msg.getFromUserName(), MessageUtil.initRegeditMessage());
			}else{
				MessageUtil.sendText(msg.getFromUserName(), "");
				reSchedulingCache.remove(msg.getFromUserName()+msg.getCreateTime());
			}
		} else if (msg.getEvent().equals(ContantValue.EVENT_CLICK)) {
			if(msg.getEventKey().indexOf(ContantValue.MENU_ARTICLE) > -1){
				Integer articleCategoryId = Integer.valueOf(msg.getEventKey().split("_")[1]);
				ArticleService articleService = SpringUtil.getBean("articleServiceImpl", ArticleService.class);
				ArticleCategoryService articleCategoryService = SpringUtil.getBean("articleCategoryServiceImpl", ArticleCategoryService.class);
				ArticleCategory articleCategory = articleCategoryService.get(articleCategoryId);
				Pager<com.koch.entity.Article> pager = new Pager<com.koch.entity.Article>();
				pager.setRows(4);
				pager.setOrderBy("createDate");
				pager.setOrderType(OrderType.desc);
				pager.getFilters().add(new Filter("articleCategory", Operator.eq, articleCategory));
				pager.getFilters().add(new Filter("isPublish", Operator.eq, true));
		    	pager = articleService.findByPage(pager);
		    	if(pager.getList() != null && pager.getList().size() > 0){
		    		List<Articles> alist = MessageUtil.initArticleMessage(pager.getList());
			    	NewsOutMessage out = new NewsOutMessage();
					out.setArticles(alist);
					setOutMessage(out);
		    	}
			}
		}
	}

	@Override
	public void setOutMessage(OutMessage outMessage) {
		this.outMessage = outMessage;
	}

	@Override
	public void afterProcess(InMessage inMessage, OutMessage outMessage) {
	}

	@Override
	public OutMessage getOutMessage() {
		return outMessage;
	}
}
