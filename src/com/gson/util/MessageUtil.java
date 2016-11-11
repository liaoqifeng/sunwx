package com.gson.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.gson.bean.Articles;
import com.gson.oauth.Message;
import com.koch.entity.Article;
import com.koch.util.CommonUtil;

public class MessageUtil {
	
	public static String REGEDIT_SUCCESS = "恭喜您注册成为阳光汇会员，我们将为您提供优质的服务。";

	public static String initRegeditMessage() {
		String url = WechatUtil.getAuthUrl(WechatUtil.getAuthUrl(PropertiesUtil.getProperty(PropertiesUtil.BASE_URL)
				+ "/sunwx/weixin/login.jsp"));
		return "终于等到你啦!感谢您关注六里阳光,马上<a href='" + url + "'>注册</a>,领取你的\"阳光见面礼\",【回复\"活动\"告诉你三大福利怎么拿哟!】";
	}

	public static List<Articles> initArticleMessage(List<Article> ns) {
		List<Articles> alist = new ArrayList<Articles>();
		for (int i = 0; i < ns.size(); i++) {
			Article n = ns.get(i);
			Articles a1 = new Articles();
			a1.setDescription(n.getShowText());
			a1.setTitle(n.getArticleTitle());
			a1.setPicUrl(n.getImage());
			if(StringUtils.isEmpty(n.getUrl())){
				n.setUrl(PropertiesUtil.getProperty(PropertiesUtil.BASE_URL)+"/sunwx/article/view/"+n.getId()+".shtml");
			}
			a1.setUrl(n.getUrl());
			alist.add(a1);
		}
		return alist;
	}

	public static void sendText(String text) {
		
	}
	
	public static void sendText(String openId,String text) {
		try {
			Message message = new Message();
			message.sendText(WechatUtil.getAccessToken(), openId, text);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public static void sendNews(List<Articles> articles) {
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
