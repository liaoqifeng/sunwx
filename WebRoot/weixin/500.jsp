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
	<title>系统出错了</title>
	<link rel="stylesheet" type="text/css" href="${path }/weixin/common/css/mui.min.css">
</head>
<body style="padding-top:50px;text-align: center;">
	<img src="${path }/weixin/common/images/500.jpg" style="width:100%;"/>
	<a href="${path }/weixin/member/coin/index.shtml" class="mui-btn mui-btn-negative">返回首页</a>
	<h5 style="padding:20px 10px 0px; 0px;text-align: left;">如果在浏览本站时,多次出现些错误,请联系管理员     管理员QQ:3278186579</h5>
</body>
</html>