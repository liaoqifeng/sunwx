<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.gson.oauth.Oauth"%>
<%@page import="com.koch.util.JsonUtil"%>
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
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script type="text/javascript">
    	window.location.href="http://p34.e75.cn/?openid=<%=openId%>&nickname=<%=name%>";
    </script>
</head>
<body>
</body>
</html>
