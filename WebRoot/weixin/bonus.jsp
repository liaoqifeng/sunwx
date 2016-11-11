<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.gson.util.PropertiesUtil"%>
<%@page import="com.gson.util.ConfKit"%><html>
<% request.setAttribute("path",request.getContextPath()); %>
<%
	String baseurl = PropertiesUtil.getProperty(PropertiesUtil.BASE_URL) + "/sunwx/weixin/bonus_dispatch.jsp";
	String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + ConfKit.get("AppId")
			+ "&redirect_uri=" + baseurl + "&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect";
	response.sendRedirect(url);
%>

