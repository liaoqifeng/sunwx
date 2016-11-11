<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<% request.setAttribute("path",request.getContextPath()); %>
<% response.setStatus(HttpServletResponse.SC_OK);%>  
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width,user-scalable=no,initial-scale=1,maximum-scale=1" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" /> 
	<meta http-equiv="Pragma" content="no-cache" /> 
	<meta http-equiv="Expires" content="0" />
	<title>资源不存在</title>
	<link rel="stylesheet" type="text/css" href="${path }/weixin/common/css/mui.min.css">
</head>
<body style="padding-top:50px;text-align: center;"><img src="${path }/weixin/common/images/404.jpg" style="width:100%;"/><a href="${path }/weixin/member/coin/index.shtml" class="mui-btn mui-btn-negative">返回首页</a></body>
</html>