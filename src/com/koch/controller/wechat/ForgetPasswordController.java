package com.koch.controller.wechat;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gson.oauth.Oauth;
import com.gson.util.WechatUtil;
import com.koch.base.BaseController;
import com.koch.bean.WeChatMessage;
import com.koch.entity.Faction;
import com.koch.entity.Job;
import com.koch.entity.Member;
import com.koch.entity.Member.MemberStatus;

import com.koch.service.FactionService;

import com.koch.service.AreaService;

import com.koch.service.MemberService;
import com.koch.util.CookieUtils;
import com.koch.util.GlobalConstant;
import com.koch.util.JavaMD5;
import com.koch.util.JsonUtil;
import com.koch.util.MessageUtil;
import com.koch.util.PropertiesUtil;
import com.koch.util.SmsUtil;
import com.sun.mail.handlers.image_gif;

@Controller("wcForgetPasswordController")
@RequestMapping(value = "weixin/forget")
public class ForgetPasswordController extends BaseController {

	@Value("${image.project}")
	private String imageProject;

	@Value("${system.project}")
	private String project;

	@Resource
	private MemberService memberService;


	@RequestMapping(value = "sendCode")
	@ResponseBody
	public WeChatMessage sendCode(String mobile, HttpServletRequest request) {
		String identifyingCode = getIdentifyingCode();
		System.out.println("identifyingCode==" + identifyingCode);
		request.getSession().setAttribute("forget_identifyingCode", identifyingCode);
		request.getSession().setAttribute("forget_mobile", mobile);

		String context = PropertiesUtil.getProperty("SMS_FORGET");
		context = context.replaceAll("!code!", identifyingCode);

		int r = SmsUtil.SMSsend(context, mobile);
		if (r > 0) {
			return WeChatMessage.success("发送成功");
		} else {
			return WeChatMessage.warn("手机号不正确，获取验证码失败");
		}
	}

	@RequestMapping(value = "checkCode")
	@ResponseBody
	public WeChatMessage checkCode(String mobile, String code, String password,String password1, HttpServletRequest request) {
		getIdentifyingCode();
		String codeTmp = (String) request.getSession().getAttribute("forget_identifyingCode");
		String mobileTmp = (String) request.getSession().getAttribute("forget_mobile");
		if(!password.equals(password1)){
			return WeChatMessage.warn("两次密码输入不一致");
		}
		if (!mobile.equals(mobileTmp)) {
			return WeChatMessage.warn("手机号码与发送验证码手机不一致");
		} else if (!code.equals(codeTmp)) {
			return WeChatMessage.warn("验证码错误");
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("phone", mobile);
			map.put("status", MemberStatus.active);
			Member m = memberService.get(map);
			if (m == null) {
				return WeChatMessage.warn("该手机号还未注册");

			} else {
				m.setPassword(JavaMD5.getMD5ofStr(password));
				memberService.update(m);
				return WeChatMessage.success("");
			}
		}

	}


	public static String getIdentifyingCode() {
		Integer i = new Double(Math.random() * 9000 + 1000).intValue();
		String s = i.toString();
		return s;
	}

}
