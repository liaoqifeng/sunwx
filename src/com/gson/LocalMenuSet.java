package com.gson;
import java.util.ArrayList;
import java.util.List;

import com.gson.WeChat;
import com.gson.oauth.Menu;
import com.gson.oauth.Oauth;
import com.gson.util.PropertiesUtil;

public class LocalMenuSet {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			LocalMenuSet.createMenu();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void createMenu() throws Exception {
		WeChat w = new WeChat();
		String at = w.getAccessToken();
		
		
		Menu m = new Menu();
		String s30 = getClickMenu("阳光动态", "article_1");
		String s31 = getUrlMenu("挖金币", PropertiesUtil.getProperty(PropertiesUtil.BASE_URL)+"/sunwx/weixin/member/coin/index.shtml", false);
		//String s32 = getUrlMenu("金币超市", PropertiesUtil.getProperty(PropertiesUtil.BASE_URL)+"/sunwx/weixin/member/coin/gift.shtml", false);
		//String s33 = getUrlMenu("超级任务", PropertiesUtil.getProperty(PropertiesUtil.BASE_URL)+"/sunwx/weixin/member/task/index.shtml", false);
		//String s34 = getUrlMenu("水果机", PropertiesUtil.getProperty(PropertiesUtil.BASE_URL)+"/sunwx/weixin/member/game/slot.shtml", false);
		String s34 = getUrlMenu("支付测试", PropertiesUtil.getProperty(PropertiesUtil.BASE_URL)+"/sunwx/weixin/payhome.jsp", false);
		String s35 = getUrlMenu("mui示例", PropertiesUtil.getProperty(PropertiesUtil.BASE_URL)+"/sunwx/weixin/mui/list.html", false);
		List<String> sl3 = new ArrayList<String>();
		sl3.add(s30);
		sl3.add(s31);
		//sl3.add(s32);
		//sl3.add(s33);
		sl3.add(s34);
		sl3.add(s35);
		String s3 = getSubMenu("挖金币", sl3);
		
		String s4 = getUrlMenu("天地汇", PropertiesUtil.getProperty(PropertiesUtil.BASE_URL)+"/sunwx/weixin/post/main.shtml", false);
		//String s5 = getUrlMenu("天地认证", PropertiesUtil.getProperty(PropertiesUtil.BASE_URL)+"/sunwx/weixin/ground/index.jsp", false);
		String s5 = getUrlMenu("天地认证", PropertiesUtil.getProperty(PropertiesUtil.BASE_URL)+"/sunwx/weixin/ground/index.jsp", false);
		String params = " {\"button\":[" + s3 + "," + s4 + "," + s5 +"]}";
		System.out.println(params);
		System.out.println(m.createMenu(at, params));
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
