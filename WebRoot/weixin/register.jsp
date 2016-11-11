<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<% request.setAttribute("path",request.getContextPath()); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0">
    <meta charset="utf-8">
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" /> 
	<meta http-equiv="Pragma" content="no-cache" /> 
	<meta http-equiv="Expires" content="0" />
    <title>注册</title>
    <link rel="stylesheet" type="text/css" href="${path }/weixin/common/css/mui.min.css">
    <style type="text/css">
    .mui-table-view-cell:after{left:0px;}
    .wx-clear{clear:both;}
    .wx-clear-p{padding: 0px;}
    .wx-clear-m{margin: 0px;}
    .wx-text{height: 25px;line-height: 25px;}
    .wx-hr{height: 10px;width: 100%; border: 10px;background: #F2F2F2;padding: 0px;margin: 0px;}
    .logo{
    	width: 150px;height: 150px;border-radius:50%; margin-left: auto;margin-right: auto;background: #ffffff;margin-top: 20px;text-align: center;
    }
    </style>
</head>
<body>
	<ul class="mui-table-view">
		<li class="mui-table-view-cell mui-media" style="background: #15B9D5;height: 200px;">
			<div class="logo">
				<h5 style="padding-top: 35px;font-weight: bold;"><font>SUN -</font> <font color="#23BCA7">LIGHT</font></h5> 
				<h4 style="margin-top:20px;color: #23BCA7;"><b>阳光城外高桥</b></h4>
				<h6 style="margin-top:20px;"><b>商业微信服务</b></h6>
			</div>
		</li>
		<li class="mui-table-view-cell mui-media" style="height: 50px;">
			<span class="mui-pull-left" style="margin-top: 5px;">手机号:</span>
			<div class="mui-media-body"><input type="text" class="wx-text" style="height: 30px;border: 0px;" maxlength="11"/></div>
		</li>
		<li class="mui-table-view-cell mui-media" style="height: 50px;">
			<span class="mui-pull-left" style="margin-top: 5px;">验证码:</span>
			<div class="mui-media-body"><input type="text" placeholder="请输入验证码" class="wx-text" style="height: 30px;border: 0px;" maxlength="9"/></div>
			<button class="mui-btn">发送</button>
		</li>
		<li class="mui-table-view-cell mui-media" style="height: 50px;">
			<span class="mui-pull-left" style="margin-top: 5px;">密&nbsp;&nbsp;&nbsp;&nbsp;码:</span>
			<div class="mui-media-body"><input type="text" placeholder="设定你用于登录的密码" class="wx-text" style="height: 30px;border: 0px;"/></div>
		</li>
	</ul>
	<hr class="wx-hr"/>
	<button class="mui-btn mui-btn-negative mui-btn-block">注&nbsp;&nbsp;&nbsp;&nbsp;册</button>
</body>
</html>
