package com.koch.util;

import java.util.ArrayList;
import java.util.List;

import com.gson.bean.Articles;
import com.gson.oauth.Message;
import com.gson.util.WechatUtil;

public class MessageUtil {

	public static String REGEDIT_SUCCESS = "恭喜您注册成为阳光汇会员，我们将为您提供优质的服务。";

	public static String initRegeditMessage() {
		String url = WechatUtil.getAuthUrl(WechatUtil.getAuthUrl(PropertiesUtil.getProperty(PropertiesUtil.BASE_URL)
				+ "/sunny/user/userinfo.do"));
		return "欢迎使用阳光城-阳光汇微信服务号，请点击注册\n<a href='" + url + "'>注册地址</a>";
	}

	// public static List<Articles> initNoticeMessage(List<Notice> ns) {
	// List<Articles> alist = new ArrayList<Articles>();
	// for (int i = 0; i < ns.size(); i++) {
	// Notice n = ns.get(i);
	// Articles a1 = new Articles();
	// a1.setDescription(n.getBody());
	// a1.setTitle(n.getTitle());
	// String imgs = n.getImages();
	// if (imgs != null && imgs.trim().length() > 0) {
	// String img = imgs.split(";")[0];
	// a1.setPicUrl(PropertiesUtil.getProperty(PropertiesUtil.BASE_URL) +
	// "/sunny/"
	// + CommonUtil.getPicturePath(img, PropertiesUtil.NOTICE_PIC_DIR));
	// }
	// String url = n.getUrl();
	// if (url == null || url.trim().length() == 0) {
	// url = PropertiesUtil.getProperty(PropertiesUtil.BASE_URL) +
	// "/sunny/notice/show.do?id=" + n.getId();
	// }
	// a1.setUrl(url);
	// alist.add(a1);
	// }
	// return alist;
	// }

	// public static List<Articles> initRepairMessage(List<Notice> ns) {
	// List<Articles> alist = initNoticeMessage(ns);
	// // Articles a1 = new Articles();
	// // a1.setDescription("您現在正在使用房屋报修服务");
	// // a1.setTitle("您現在正在使用房屋报修服务");
	// // a1.setPicUrl(PropertiesUtil.getProperty(PropertiesUtil.BASE_URL) +
	// "/sunny/images/bg.png");
	// // // a1.setUrl("");
	// // alist.add(a1);
	// Articles a2 = new Articles();
	// a2.setDescription("asdsa");
	// a2.setTitle("添加报修信息");
	// a2.setPicUrl(PropertiesUtil.getProperty(PropertiesUtil.BASE_URL) +
	// "/sunny/images/icon1.png");
	// a2.setUrl(WechatUtil.getAuthUrl(PropertiesUtil.getProperty(PropertiesUtil.BASE_URL)
	// + "/sunny/repair/add.do"));
	// alist.add(a2);
	// Articles a3 = new Articles();
	// a3.setDescription("asdas");
	// a3.setTitle("查看报修信息");
	// a3.setPicUrl(PropertiesUtil.getProperty(PropertiesUtil.BASE_URL) +
	// "/sunny/images/icon2.png");
	// a3.setUrl(WechatUtil.getAuthUrl(PropertiesUtil.getProperty(PropertiesUtil.BASE_URL)
	// + "/sunny/repair/list.do"));
	// alist.add(a3);
	// return alist;
	// }

	// public static List<Articles> initRepairMessage() {
	// List<Articles> alist = new ArrayList<Articles>();
	// Articles a1 = new Articles();
	// a1.setDescription("您現在正在使用房屋报修服务");
	// a1.setTitle("您現在正在使用房屋报修服务");
	// a1.setPicUrl(PropertiesUtil.getProperty(PropertiesUtil.BASE_URL) +
	// "/sunny/images/bg.png");
	// // a1.setUrl("");
	// alist.add(a1);
	// Articles a2 = new Articles();
	// a2.setDescription("asdsa");
	// a2.setTitle("添加报修信息");
	// a2.setPicUrl(PropertiesUtil.getProperty(PropertiesUtil.BASE_URL) +
	// "/sunny/images/icon1.png");
	// a2.setUrl(WechatUtil.getAuthUrl(PropertiesUtil.getProperty(PropertiesUtil.BASE_URL)
	// + "/sunny/repair/add.do"));
	// alist.add(a2);
	// Articles a3 = new Articles();
	// a3.setDescription("asdas");
	// a3.setTitle("查看报修信息");
	// a3.setPicUrl(PropertiesUtil.getProperty(PropertiesUtil.BASE_URL) +
	// "/sunny/images/icon2.png");
	// a3.setUrl(WechatUtil.getAuthUrl(PropertiesUtil.getProperty(PropertiesUtil.BASE_URL)
	// + "/sunny/repair/list.do"));
	// alist.add(a3);
	// return alist;
	// }

//	public static List<Articles> initWelcomeMessage() {
//		List<Articles> alist = new ArrayList<Articles>();
//		Articles a1 = new Articles();
//		a1.setDescription("");
//		a1.setTitle("欢迎使用阳光汇微信服务");
//		a1.setPicUrl(PropertiesUtil.getProperty(PropertiesUtil.BASE_URL) + "/sunny/images/bg.png");
//		// a1.setUrl("http://yjmjimmy.vicp.net");
//		alist.add(a1);
//		Articles a2 = new Articles();
//		a2.setDescription("asdsa");
//		a2.setTitle("阳光城新闻一");
//		a2.setPicUrl(PropertiesUtil.getProperty(PropertiesUtil.BASE_URL) + "/sunny/images/bg.png");
//		// a2.setUrl("http://yjmjimmy.vicp.net");
//		alist.add(a2);
//		Articles a3 = new Articles();
//		a3.setDescription("asdas");
//		a3.setTitle("阳光城新闻二");
//		a3.setPicUrl(PropertiesUtil.getProperty(PropertiesUtil.BASE_URL) + "/sunny/images/bg.png");
//		// a3.setUrl("http://yjmjimmy.vicp.net");
//		alist.add(a3);
//		return alist;
//	}

//	public static List<Articles> initRepairSuccessMessage(String id) {
//		Articles n = new Articles();
//		List<Articles> as = new ArrayList<Articles>();
//		n.setDescription("你的报修已经成功\n我们会尽快联系您，并及时为您处理\n点击全文，查看报修详情");
//		n.setTitle("报修成功");
//		n.setUrl(WechatUtil.getAuthUrl(PropertiesUtil.getProperty(PropertiesUtil.BASE_URL)
//				+ "/sunny/repair/show.do?id=" + id));
//		as.add(n);
//		return as;
//	}
//
//	public static String initPaySuccessMessage(String amount) {
//		return "您已缴费" + amount + "元。谢谢您的配合。我们会持续为您提供优质服务。";
//	}

	public static void sendText(String text) {
		try {
			Message message = new Message();
			message.sendText(WechatUtil.getAccessToken(), WechatUtil.getCurrent().getWechatCode(), text);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public static void sendNews(List<Articles> articles) {
		try {
			Message message = new Message();
			message.sendNews(WechatUtil.getAccessToken(), WechatUtil.getCurrent().getWechatCode(), articles);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
