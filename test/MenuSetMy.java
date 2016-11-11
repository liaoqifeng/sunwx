import java.util.ArrayList;
import java.util.List;

import com.gson.WeChat;
import com.gson.oauth.Menu;
import com.gson.oauth.Oauth;
import com.gson.util.ConfKit;
import com.gson.util.PropertiesUtil;

public class MenuSetMy {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		 createMenu();

	}

	public static void createMenu() throws Exception {
		WeChat w = new WeChat();
		String at = w.getAccessToken();
		String baseurl1 = PropertiesUtil.getProperty(PropertiesUtil.BASE_URL) + "/sunny/payment/init.do";
		String baseurl2 = PropertiesUtil.getProperty(PropertiesUtil.BASE_URL) + "/sunny/user/userinfo.do";
		String baseurl3 = PropertiesUtil.getProperty(PropertiesUtil.BASE_URL) + "/sunny/info.jsp";
		String baseurl4 = PropertiesUtil.getProperty(PropertiesUtil.BASE_URL) + "/sunwx/weixin/post/main.shtml";
		String baseurl5 = PropertiesUtil.getProperty(PropertiesUtil.BASE_URL) + "/sunwx/wc/regedit1.shtml";
		Menu m = new Menu();
		Oauth o = new Oauth();
		String s11 = getClickMenu("小区公告", "xqgg");
		String s12 = getClickMenu("房屋报修", "wybx");
		List<String> sl1 = new ArrayList<String>();
		sl1.add(s11);
		sl1.add(s12);
		String s1 = getSubMenu("服务", sl1);
		String s2 = getUrlMenu("缴费", baseurl1, true);

		String s31 = getUrlMenu("我的阳光汇", baseurl2, true);
		String s32 = getUrlMenu("关于阳光汇", baseurl3, false);
		String s33 = getUrlMenu("关于阳光汇", baseurl4, true);
		String s34 = getUrlMenu("关于阳光汇", baseurl5, true);
		List<String> sl3 = new ArrayList<String>();
		sl3.add(s31);
		sl3.add(s32);
		sl3.add(s33);
		sl3.add(s34);
		String s3 = getSubMenu("我", sl3);
		String params = " {\"button\":[" + s1 + "," + s2 + "," + s3 + "]}";
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
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + ConfKit.get("AppId")
				+ "&redirect_uri=" + baseurl + "&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect";
		return url;
	}

}
