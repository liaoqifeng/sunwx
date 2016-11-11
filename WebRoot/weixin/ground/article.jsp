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
    <title>崇明原生态大米</title>
    <link rel="stylesheet" type="text/css" href="../common/css/mui.min.css">
    <style type="text/css">
    .mui-table-view:after{height: 0px;}
    .mui-table-view-cell:after{left:0px;height: 0px;}
    .mui-table-view-cell img{width: 100%;}
    </style>
</head>
<body background="#F2F2F2">
	<img alt="崇明原生态大米" src="${path}/weixin/common/images/yuanshengtai.jpg" style="width:100%;">
</body>
</html>
