package com.koch.interceptor;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.gson.oauth.Oauth;
import com.koch.entity.Member;
import com.koch.entity.Member.MemberStatus;
import com.koch.service.MemberService;
import com.koch.util.GlobalConstant;
import com.koch.util.JsonUtil;

@Repository
public class WechatLoginInterceptor implements HandlerInterceptor {

	public static final String loginPage = "/weixin/login.jsp";
	private static final String loginUrl = "/weixin/login.shtml";
	private static final String logoutUrl = "/weixin/logout.shtml";
	private static final String mainUrl = "/weixin/member/coin/index.shtml";

	private static final String viewPostPage = "/weixin/post/view.shtml";
	private static final String ajaxPostPage = "/weixin/post/get";

	@Resource
	private MemberService memberService;

	@Value("${url.charset}")
	private String charset;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Member member = memberService.getCurrent();
		HttpSession session = request.getSession();
		String uri = request.getRequestURI();
		member = memberService.getCurrent();
		if (member != null) {
			return true;
		}

		String code = request.getParameter("code");
		String state1 = request.getParameter("state1");
		System.out.println("code=" + code + " " + state1);
		String openId = null;
		if (code != null) {
			// String tmp = keyMap.get(code);
			// if (tmp == null) {
			Oauth oauth = new Oauth();
			String xml = oauth.getToken(code);
			Map<String, Object> map = JsonUtil.toHashMap(xml);
			if (map.get("openid") != null) {
				openId = (String) map.get("openid");
				session.setAttribute(GlobalConstant.MEMBER_SESSION_OPENID, openId);
				// keyMap.put(code, openId);
				// request.getSession().setAttribute(ContantValue.SESSION_OPENID,
				// openId);
			}
			// } else {
			// openId = tmp;
			// HttpSession hs = request.getSession();
			// hs.setAttribute(ContantValue.SESSION_OPENID, openId);
			// session.put(ContantValue.SESSION_OPENID, openId);
			// }
		}
		// hs.setAttribute(ContantValue.SESSION_OPENID, openId);
		// String op = (String) session.get(ContantValue.SESSION_OPENID);
		String op = (String) session.getAttribute(GlobalConstant.MEMBER_SESSION_OPENID);

		// if (openId == null) {
		// openId = ContantValue.test_openid;
		// System.out.println("===================test_user==================");
		// }
		// session.put(ContantValue.SESSION_OPENID, openId);

		System.out.println("openId=" + op);

		if (openId != null) {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("wechatCode", openId);
			map.put("status", MemberStatus.active);
			member = memberService.get(map);
			if (member != null ) {
				session.setAttribute(GlobalConstant.MEMBER_SESSION_USER, member);
				return true;
			}
		}

		if (uri.indexOf(loginUrl) > 0 || uri.indexOf(logoutUrl) > 0 || uri.indexOf(viewPostPage) > 0
				|| uri.indexOf(ajaxPostPage) > 0) {
			return true;
		}

		String header = request.getHeader("X-Requested-With");
		if (header != null && (header.equalsIgnoreCase("XMLHttpRequest"))) {
			response.addHeader("loginStatus", "accessDenied");
			response.sendError(403);
			return false;
		}
		if (request.getMethod().equalsIgnoreCase("GET")) {
			String redirectUrl = request.getQueryString() != null ? request.getRequestURI() + "?"
					+ request.getQueryString() : request.getRequestURI();
			redirectUrl = "?" + "redirectUrl" + "=" + URLEncoder.encode(redirectUrl, this.charset);
			response.sendRedirect(request.getContextPath() + this.loginPage + redirectUrl);
		} else {
			String redirectUrl = "?" + "redirectUrl" + "=" + URLEncoder.encode(mainUrl, this.charset);
			response.sendRedirect(request.getContextPath() + this.loginPage + redirectUrl);
		}
		return false;
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	public static void main(String[] args) {
		// String uri = "/cms/back/order/list.shtml";
		// String [] items = uri.split("/");
	}
}
