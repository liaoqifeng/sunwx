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
    <title>天地认证</title>
    <style type="text/css">
    *{padding:0px;margin:0px;}
    </style>
</head>
<body style="background: #efefef;">
	<img src="${path}/weixin/common/images/tiandirenz-head.jpg" style="width:100%;margin:0px;padding:0px;">
	<a href="#"><img src="${path}/weixin/common/images/product1.jpg" style="width:100%;margin:-5px auto;padding:0px;"></a>
</body>
</html>
