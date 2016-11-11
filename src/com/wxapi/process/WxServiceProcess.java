package com.wxapi.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.wxapi.vo.Article;
import com.wxapi.vo.MsgRequest;
import com.wxapi.vo.MsgResponseNews;
import com.wxapi.vo.MsgResponseText;

/**
 * 微信 服务处理，消息转换等
 * 
 */

public class WxServiceProcess {
	
	//获取 MsgResponseText 对象
	public static MsgResponseText getMsgResponseText(MsgRequest msgRequest,Object msgText){
		if(msgText != null){
			MsgResponseText reponseText = new MsgResponseText();
			reponseText.setToUserName(msgRequest.getFromUserName());
			reponseText.setFromUserName(msgRequest.getToUserName());
			reponseText.setMsgType(MsgType.Text.toString());
			reponseText.setCreateTime(new Date().getTime());
			reponseText.setContent("");
			return reponseText;
		}else{
			return null;
		}
	}
	
	//获取 MsgResponseNews 对象
	public static MsgResponseNews getMsgResponseNews(MsgRequest msgRequest,List msgNews){
		if(msgNews != null && msgNews.size() > 0){
			MsgResponseNews responseNews = new MsgResponseNews();
			responseNews.setToUserName(msgRequest.getFromUserName());
			responseNews.setFromUserName(msgRequest.getToUserName());
			responseNews.setMsgType(MsgType.News.toString());
			responseNews.setCreateTime(new Date().getTime());
			responseNews.setArticleCount(msgNews.size());
			List<Article> articles = new ArrayList<Article>(msgNews.size());
			
			responseNews.setArticles(articles);
			return responseNews;
		}else{
			return null;
		}
	}
	
	//发布菜单
	public static JSONObject publishMenus(String menus,String appId,String appSecret){
		String token = WxApi.getToken(appId,appSecret).getAccessToken();
		String url = WxApi.getMenuCreateUrl(token);
		return WxApi.httpsRequest(url, HttpMethod.POST, menus);
	}
	
	//删除菜单
	public static JSONObject deleteMenu(String appId, String appSecret){
		String token = WxApi.getToken(appId,appSecret).getAccessToken();
		String url = WxApi.getMenuDeleteUrl(token);
		return WxApi.httpsRequest(url, HttpMethod.POST, null);
	}

	
	//上传图文消息
	public static JSONObject uploadNews(List msgNewsList,String appId,String appSecret){
		JSONObject rstObj = new JSONObject();
		try{
			JSONArray jsonArr = new JSONArray();
			
			JSONObject postObj = new JSONObject();
			postObj.put("articles", jsonArr);
			
			String token = WxApi.getTokenUrl(appId, appSecret);
			rstObj = WxApi.httpsRequest(WxApi.getUploadNewsUrl(token), HttpMethod.POST, postObj.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		return rstObj;
	}
	
	//群发接口
	public static JSONObject sendAll(String mediaId,String msgType,String appId,String appSecret){
		JSONObject postObj = new JSONObject();
		JSONObject filter = new JSONObject();
		filter.put("is_to_all", true);
		postObj.put("filter", filter);
		JSONObject mpnews = new JSONObject();
		mpnews.put("media_id", mediaId);
		postObj.put("mpnews", mpnews);
		postObj.put("msgtype", msgType);
		String token = WxApi.getTokenUrl(appId, appSecret);
		return WxApi.httpsRequest(WxApi.getUploadNewsUrl(token), HttpMethod.POST, postObj.toString());
	}
	
}



