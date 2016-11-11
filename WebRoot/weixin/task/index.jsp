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
	<title>超级任务</title>
    <link rel="stylesheet" type="text/css" href="${path}/weixin/common/css/mui.min.css">
    <style type="text/css">
    .mui-table-view-cell:after{left:0px;}
    .wx-clear{clear:both;}
    .wx-clear-btop:after{height: 0px;}
    .wx-clear-bdown:before{height: 0px;}
    .wx-clear-p{padding: 0px;}
    .wx-clear-m{margin: 0px;}
    .wx-padding{padding: 10px;}
    .wx-highlight-color{color: #ff4800;}
    .wx-margin-r{margin-right: 10px;}
    .wx-margin-l{margin-left: 10px;}
    .wx-icon{vertical-align: middle;width:40px;height:40px;margin-right:10px;}
    .wx-coin{vertical-align: middle;width:16px;height:16px;}
    </style>
</head>
<body>
	<ul class="mui-table-view">
		<c:if test="${tasks != null}">
		<c:forEach var="row" items="${tasks}">
		<c:if test="${row.id==3}">
		<li class="mui-table-view-cell mui-media">
			<a href="${path }/weixin/member/task/${row.action}">
				<img alt="${row.title }" src="${row.image }" class="wx-icon mui-pull-left"/>
				<div class="mui-media-body mui-pull-left">${row.title }<div><h5 style="display: inline;">奖励：<label class="wx-highlight-color">+${row.score }</label></h5>&nbsp;&nbsp;<img src="${path }/weixin/common/images/Icon_coin.png" class="wx-coin"/></div></div>
				<button class="mui-btn mui-btn-negative mui-pull-right" onclick="return false;">${row.isComplete?'已完成':'未完成' }</button>
			</a>
		</li>
		</c:if>
		</c:forEach>
		</c:if>
	</ul>
</body>
</html>
