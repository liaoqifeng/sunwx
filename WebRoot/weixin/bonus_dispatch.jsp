<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.gson.oauth.Oauth"%>
<%@page import="com.koch.util.JsonUtil"%>
<%@page import="com.koch.service.MemberService"%>
<%@page import="com.koch.util.SpringUtil"%>
<%@page import="com.koch.entity.Member"%>
<%@page import="com.gson.util.HttpKit"%>
<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@page import="com.gson.util.WechatUtil"%><html>
<% request.setAttribute("path",request.getContextPath()); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
	String code = request.getParameter("code");
	Oauth oauth = new Oauth();
	String xml = oauth.getToken(code);
	Map<String, Object> map = JsonUtil.toHashMap(xml);
	String openId = (String) map.get("openid");
	String at = (String) map.get("access_token");
	String xml2 = oauth.getUserInfo(openId, at);
	Map<String, Object> map2 = JsonUtil.toHashMap(xml2);
	String name = (String) map2.get("nickname");
	Boolean auth = true;
	
	String jsonStr = HttpKit.get("https://api.weixin.qq.com/cgi-bin/user/info?access_token="+WechatUtil.getAccessToken()+"&openid="+openId+"&lang=zh_CN", null);
	Map<String, Object> jsonMap = JSONObject.parseObject(jsonStr);
	if(jsonMap.get("subscribe").toString().equals("0")){
		auth = false;
	}
	MemberService memberService = SpringUtil.getBean("memberServiceImpl", MemberService.class);
	Member member = memberService.get("wechatCode",openId);
	if(member == null){
		auth = false;
	}
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script type="text/javascript">
    	window.location.href="http://p38.e75.cn/?openid=<%=openId%>&nickname=<%=name%>&auth=<%=auth%>&access_token=<%=at%>";
    </script>
</head>
<body>
</body>
</html>
