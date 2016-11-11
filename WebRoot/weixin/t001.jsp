<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.gson.util.PropertiesUtil"%>
<%@page import="com.gson.util.ConfKit"%>
<%@page import="com.koch.service.MemberService"%>
<%@page import="com.koch.util.SpringUtil"%>
<%@page import="com.koch.entity.Member"%>
<%@page import="com.gson.util.WechatUtil"%>
<%@page import="com.koch.util.JsonUtil"%>
<html>
<%
	String openid = request.getParameter("openid");
	MemberService memberService = SpringUtil.getBean("memberServiceImpl", MemberService.class);
	Member member = memberService.get("wechatCode",openid);
	Map map = new HashMap();
	if(member == null){
		map.put("auth",false);
	}else{
		map.put("auth",true);
		map.put("access_token",WechatUtil.getAccessToken());
	}
		
	response.setContentType("text/html;charset=UTF-8");
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
	response.getWriter().write(JsonUtil.toJson(map));
	response.getWriter().flush();
%>

