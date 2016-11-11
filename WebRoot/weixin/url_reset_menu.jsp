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
    <title></title>
    <link rel="stylesheet" type="text/css" href="${path }/weixin/common/css/mui.min.css">
    <link rel="stylesheet" type="text/css" href="${path }/weixin/common/css/zepto.alert.css">
    <style type="text/css">
    .mui-table-view-cell:after{left:0px;}
    .wx-clear{clear:both;}
    .wx-clear-p{padding: 0px;}
    .wx-clear-m{margin: 0px;}
    .wx-text{height: 25px;line-height: 25px;}
    .wx-hr{height: 10px;width: 100%; border: 10px;background: #F2F2F2;padding: 0px;margin: 0px;}
    .logo{width: 150px;height: 150px;border-radius:50%; margin-left: auto;margin-right: auto;background: #ffffff;margin-top: 20px;text-align: center;}
    </style>
</head>
<body>
	<form action="url_reset_menu_dispatch.jsp" method="post">
		<input type="text" name="url" style="height: 30px;width:400px;"/>
		<input type="submit" value="重置"/>
	</form>
	
</body>
</html>
