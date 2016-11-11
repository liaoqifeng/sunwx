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
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>按图索骥</title>
    <link rel="stylesheet" type="text/css" href="${path}/weixin/common/css/mui.min.css">
    <style type="text/css">
    .mui-table-view-cell:after{left:0px;}
    .mui-table-view:after{height:0px;}
    .wx-clear{clear:both;}
    .wx-clear-btop:after{height: 0px;}
    .wx-clear-bdown:before{height: 0px;}
    .wx-clear-p{padding: 0px;}
    .wx-clear-m{margin: 0px;}
    .wx-padding{padding: 10px;}
    .wx-highlight-color{color: #ff4800;}
    .wx-center{text-align: center;}
    .wx-margin-r{margin-right: 10px;}
    .wx-margin-l{margin-left: 10px;}
    </style>
</head>
<body>
	<div><img src="${path}/weixin/common/images/antusuoji.jpg" style="width:100%;"/></div>
	<h4 class="wx-center wx-highlight-color">任务介绍</h4>
	<ul class="mui-table-view">
		<li class="mui-table-view-cell mui-media">
			<h5>金币奖励：＋${task.score }</h5>
			<h5>任务规则：${task.rules }</h5>
			<h5>任务要求：${task.require }</h5>
		</li>
		<li class="mui-table-view-cell mui-media">
			<h4 class="wx-center wx-highlight-color">任务说明</h4>
			<p>${task.description }</p>
			<div style="text-align: center;"><br />
				<img src="${path}/weixin/member/task/gen.shtml?taskId=${task.id}"/>
				<h5>扫描二维码</h5>
			</div>
		</li>
	</ul>
</body>
</html>
