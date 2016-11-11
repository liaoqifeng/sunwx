package com.koch.controller.wechat;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gson.WeChat;
import com.gson.oauth.Pay;
import com.gson.util.ConfKit;
import com.gson.util.MoneyUtils;
import com.gson.util.PropertiesUtil;
import com.gson.util.WeChatPayUtils;
import com.koch.base.BaseController;
import com.koch.util.XmlConverUtil;

@Controller("weChatPayController")
@RequestMapping(value="O2O/wechat")
public class WeChatPayController extends BaseController{
	
	@RequestMapping(value = { "pay" },method={RequestMethod.POST})
	public String pay(HttpServletRequest req,ModelMap model) throws Exception {
		// 判断是否微信环境, 5.0 之后的支持微信支付
		boolean isweixin = WeChat.isWeiXin(req);
		if (isweixin) {
			String nonceStr = WeChatPayUtils.buildRandom();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("appid", "wx0daa0d7188858bb0");
			map.put("mch_id", "1251637201");
			map.put("openid", "ouI-8voT5s51r-sjOG2f1wVn1TNs");
			map.put("nonce_str", nonceStr);
			map.put("body", new String("测试商品".getBytes(), "ISO8859-1") );
			map.put("total_fee", 1000);
			map.put("trade_type", "JSAPI");
			map.put("out_trade_no", WeChatPayUtils.getOrderNo());
			map.put("notify_url", "http://koch.tunnel.mobi/sunwx/O2O/wechat/notify.shtml");
			map.put("spbill_create_ip", WeChatPayUtils.getIpAddr(req));
			map.put("trade_type", "JSAPI");
			map.put("sign", WeChatPayUtils.createSign(map));
			
			String result = WeChatPayUtils.doSend(WeChatPayUtils.url, WeChatPayUtils.createXML(map));
			Map resultMap = XmlConverUtil.xmltoMap(result);
			
			map = new HashMap<String, Object>();
			map.put("appId", "wx0daa0d7188858bb0");
			map.put("nonceStr", nonceStr);
			map.put("package", String.format("prepay_id=%s",resultMap.get("prepay_id")));
			map.put("signType", "MD5");
			map.put("timeStamp", System.currentTimeMillis());
			map.put("sign", WeChatPayUtils.createSign(map));
			
			model.addAttribute("packageStr", map.get("package"));
			model.addAttribute("params", map);
			return "/weixin/pay";
		} else {
			return "/weixin/pay";
		}
	}
	
	@RequestMapping(value = { "/notify" },method={RequestMethod.GET})
	public String payNotify(){
		return "";
	}
	
}
