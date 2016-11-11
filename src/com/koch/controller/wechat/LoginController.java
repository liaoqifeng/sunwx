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

@Controller("wcLoginController")
@RequestMapping(value = "weixin")
public class LoginController extends BaseController {

	@Value("${image.project}")
	private String imageProject;

	@Value("${system.project}")
	private String project;

	@Resource
	private MemberService memberService;

	@Resource
	private FactionService factionService;

	@Resource
	private AreaService areaService;

	@RequestMapping(value = { "/login" }, method = { RequestMethod.POST })
	@ResponseBody
	public WeChatMessage login(String username, String password, String url, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", username);
		map.put("status", MemberStatus.active);
		Member member = memberService.get(map);
		if (member == null) {
			return WeChatMessage.error("用户名不存在");
		}
		if (!member.getPassword().equals(JavaMD5.getMD5ofStr(password))) {
			return WeChatMessage.error("密码不正确");
		}

		String openId = (String) request.getSession().getAttribute(GlobalConstant.MEMBER_SESSION_OPENID);
		if (openId != null) {
			member.setWechatCode(openId);
			memberService.save(member);
		}

		request.getSession().invalidate();
		request.getSession().setAttribute(GlobalConstant.MEMBER_SESSION_USER, member);

		return WeChatMessage.success("登录成功");
	}

	@RequestMapping(value = { "/regedit1" })
	public String regedit1(ModelMap modelMap) {
		List<Faction> factions = factionService.getAll();
		modelMap.put("factions", factions);
		return "/weixin/member/regedit1";

	}

	@RequestMapping(value = { "/regedit2" })
	public String regedit2(Integer id, ModelMap modelMap, HttpServletRequest request) {
		Member member = new Member();
		Faction faction = new Faction();
		faction.setId(id);
		member.setFaction(faction);
		request.getSession().setAttribute("regedit_member", member);
		String regeditUrl = WechatUtil.getAuthUserInfoUrl();

		modelMap.put("regeditUrl", regeditUrl);
		return "/weixin/member/regedit2";

	}

	@RequestMapping(value = "sendCode")
	@ResponseBody
	public WeChatMessage sendCode(String mobile, HttpServletRequest request) {
		String identifyingCode = getIdentifyingCode();
		System.out.println("identifyingCode==" + identifyingCode);
		request.getSession().setAttribute("regedit_identifyingCode", identifyingCode);
		request.getSession().setAttribute("regedit_mobile", mobile);

		String context = PropertiesUtil.getProperty(PropertiesUtil.SMS_CONTEXT);
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
	public WeChatMessage checkCode(String mobile, String code, String password, HttpServletRequest request) {
		getIdentifyingCode();
		String codeTmp = (String) request.getSession().getAttribute("regedit_identifyingCode");
		String mobileTmp = (String) request.getSession().getAttribute("regedit_mobile");
		if (false){//(!mobile.equals(mobileTmp)) {
			return WeChatMessage.warn("手机号码与发送验证码手机不通");
		} else if(false){//else if (!code.equals(codeTmp)) {
			return WeChatMessage.warn("验证码错误");
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("phone", mobile);
			map.put("status", MemberStatus.active);
			Member m = memberService.get(map);
			if (m != null) {
				return WeChatMessage.warn("该手机号已经注册");

			} else {
				Member member = (Member) request.getSession().getAttribute("regedit_member");
				member.setPhone(mobile);
				member.setUsername(mobile);
				member.setPassword(JavaMD5.getMD5ofStr(password));
				// request.getSession().setAttribute("regedit_identifyingCode_tmp",
				// code);
				// request.getSession().setAttribute("regedit_mobile_tmp",
				// mobile);
				// request.getSession().setAttribute("regedit_password_tmp",
				// password);
				request.getSession().setAttribute("regedit_member", member);
				return WeChatMessage.success("");
			}
		}

	}

	@RequestMapping(value = { "/regedit3" })
	public String regedit3(HttpServletRequest request, ModelMap modelMap) {
		try {
			Member member = (Member) request.getSession().getAttribute("regedit_member");
			String code = request.getParameter("code");
			System.out.println("code=" + code);
			String openId = null;
			if (code != null) {
				// String tmp = keyMap.get(code);
				// if (tmp == null) {
				Oauth oauth = new Oauth();
				String xml = oauth.getToken(code);
				Map<String, Object> map = JsonUtil.toHashMap(xml);
				System.out.println(xml);
				System.out.println(request.getParameter("state1"));
				if (map.get("openid") != null) {
					openId = (String) map.get("openid");
					String at = (String) map.get("access_token");
					String xml2 = oauth.getUserInfo(openId, at);
					// System.out.println(xml2);
					Map<String, Object> map2 = JsonUtil.toHashMap(xml2);
					String name = (String) map2.get("nickname");
					String head = (String) map2.get("headimgurl");
					System.out.println("name : " + name);
					System.out.println("head : " + head);

					member.setRealname(name);
					member.setWechatCode(openId);
					member.setCreateDate(new Date());
					member.setModifyDate(new Date());
					member.setScore(100);
					member.setStatus(MemberStatus.active);
					Job job = new Job();
					job.setId(1);
					member.setJob(job);
					if (head != null && head.trim().length() > 0) {
						String dir = request.getServletContext().getRealPath("upload/head");
						dir = dir.replaceAll(project, imageProject);
						File dirf = new File(dir);
						if (!dirf.exists()) {
							dirf.mkdirs();
						}
						String file = dir + File.separator + openId;
						WechatUtil.saveImageToDisk(head, file, false);
						member.setProfileImage(PropertiesUtil.getProperty(PropertiesUtil.BASE_URL) +"/"+imageProject+ "/upload/head/"
								+ openId);
					} else {
						
						member.setProfileImage(PropertiesUtil.getProperty(PropertiesUtil.BASE_URL)+
								request.getContextPath()+"/weixin/common/images/default_profile.jpg");
					}

					Integer r = memberService.save(member);
					if (r > 0) {
						request.getSession().setAttribute(GlobalConstant.MEMBER_SESSION_USER, member);
						MessageUtil.sendText(MessageUtil.REGEDIT_SUCCESS);
						return "/weixin/wxclose";
					} else {
						return "/weixin/error";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/weixin/error";
	}

	public static String getIdentifyingCode() {
		Integer i = new Double(Math.random() * 9000 + 1000).intValue();
		String s = i.toString();
		return s;
	}

}
