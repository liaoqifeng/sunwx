package com.gson;
import java.util.ArrayList;
import java.util.List;
import com.gson.WeChat;
import com.gson.oauth.Menu;
import com.gson.oauth.Oauth;
import com.gson.util.PropertiesUtil;

public class MenuSet {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			MenuSet.createMenu();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Boolean createMenu() throws Exception {
		WeChat w = new WeChat();
		String at = w.getAccessToken();
		

		Menu m = new Menu();
		String s3 = getUrlMenu("挖金币", PropertiesUtil.getProperty(PropertiesUtil.BASE_URL)+"/sunwx/weixin/member/coin/index.shtml", true);
		String s4 = getUrlMenu("天地汇", PropertiesUtil.getProperty(PropertiesUtil.BASE_URL)+"/sunwx/weixin/post/main.shtml", true);
		
		//String s50 = getUrlMenu("获奖名单（最终期）", "http://mp.weixin.qq.com/s?__biz=MzIwMDAyODYwMA==&mid=209671096&idx=1&sn=ae0504e08c67faa478c584445fc98019#rd", false);
		//String s51 = getUrlMenu("现金红包", PropertiesUtil.getProperty(PropertiesUtil.BASE_URL)+"/sunwx/weixin/bonus.jsp", true);
		//String s52 = getUrlMenu("邀请活动", PropertiesUtil.getProperty(PropertiesUtil.BASE_URL)+"/sunwx/weixin/events.jsp", true);
		String s53 = getUrlMenu("天地认证", PropertiesUtil.getProperty(PropertiesUtil.BASE_URL)+"/sunwx/weixin/ground/index.jsp", false);
		
		List<String> sl5 = new ArrayList<String>();
		//sl5.add(s50);
		//sl5.add(s51);
		//sl5.add(s52);
		sl5.add(s53);
		//String s5 = getSubMenu("天地互动", sl5);
		String s5 = getUrlMenu("一键WiFi", PropertiesUtil.getProperty(PropertiesUtil.BASE_URL)+"/sunwx/weixin/wifi.jsp", true);
		String params = " {\"button\":[" + s3 + "," + s4 + "," + s5 +"]}";
		System.out.println(params);
		Boolean rs = m.createMenu(at, params);
		System.out.println(rs);
		return rs;
	}
	
	
	public static Boolean createMenu(String url) throws Exception {
		WeChat w = new WeChat();
		String at = w.getAccessToken();
		

		Menu m = new Menu();
		String s3 = getUrlMenu("挖金币", url, false);
		String s4 = getUrlMenu("天地汇", url, false);
		
		//String s50 = getUrlMenu("获奖名单（最终期）", "http://mp.weixin.qq.com/s?__biz=MzIwMDAyODYwMA==&mid=209671096&idx=1&sn=ae0504e08c67faa478c584445fc98019#rd", false);
		//String s51 = getUrlMenu("现金红包", PropertiesUtil.getProperty(PropertiesUtil.BASE_URL)+"/sunwx/weixin/bonus.jsp", true);
		//String s52 = getUrlMenu("邀请活动", PropertiesUtil.getProperty(PropertiesUtil.BASE_URL)+"/sunwx/weixin/events.jsp", true);
		String s53 = getUrlMenu("天地认证", url, false);
		
		List<String> sl5 = new ArrayList<String>();
		//sl5.add(s50);
		//sl5.add(s51);
		//sl5.add(s52);
		sl5.add(s53);
		String s5 = getSubMenu("天地互动", sl5);
		
		String params = " {\"button\":[" + s3 + "," + s4 + "," + s5 +"]}";
		System.out.println(params);
		Boolean rs = m.createMenu(at, params);
		System.out.println(rs);
		return rs;
	}

	private static String getClickMenu(String name, String key) {
		String u = " {\"type\":\"click\",\"name\":\"" + name + "\",\"key\":\"" + key + "\"}";
		return u;
	}

	private static String getUrlMenu(String name, String url, boolean isAuth) {
		if (isAuth) {
			url = getUrl(url);
		}
		String u = " {\"type\":\"view\",\"name\":\"" + name + "\",\"url\":\"" + url + "\"}";
		return u;
	}

	private static String getSubMenu(String name, List<String> ls) {
		String u = "{\"name\":\"" + name + "\", \"sub_button\":[ 	";

		String su = "";
		for (int i = 0; i < ls.size(); i++) {
			String s = ls.get(i);
			su += "," + s;
		}
		su = su.substring(1);
		u += su + "]}";
		return u;
	}

	private static String getUrl(String baseurl) {
		// baseurl = URLEncoder.encode(baseurl);
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+PropertiesUtil.getProperty("AppId")+"&redirect_uri="
				+ baseurl + "&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect";
		return url;
	}

}
