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
	<title>金币超市</title>
    <link rel="stylesheet" type="text/css" href="${path }/weixin/common/css/mui.min.css">
    <style type="text/css">
    .mui-table-view:after{height:0px;}
    .mui-table-view-cell:after{left:0px;  position: absolute;}
    .wx-clear{clear:both;}
    .wx-clear-p{padding: 0px;}
    .wx-clear-m{margin: 0px;}
    .wx-padding{padding: 10px;}
    .wx-highlight-color{color: #ff4800;}
    .wx-line{width:100%;height:10px;background-color: #efefef;border-top:1px solid #dddddd;border-bottom:1px solid #dddddd;}
    .wx-header{width:100%;height:50px;line-height:50px;}
    .wx-header .box{width:50%;float: left;text-align: center;}
    .wx-header-img{vertical-align: middle;width:16px;height:16px;}
    .wx-header h5{height:20px;line-height:25px;display: inline;}
    .wx-title{width:100%;height:40px;line-height:40px;padding:8px 10px;}
    .wx-bb{border-right:1px solid #dddddd;border-bottom:1px solid #dddddd;}
    </style>
</head>
<body>
	<div class="wx-header">
		<div class="box"><img src="${path }/weixin/common/images/Icon_coin.png" class="wx-header-img"/><h5>&nbsp;金币<font class="wx-highlight-color"><fmt:formatNumber value="${member.score }" pattern="#,##0"/></font></h5></div>
		<div class="box"><a href="${path }/weixin/member/coin/myGift.shtml"><img src="${path }/weixin/common/images/Icon_gift.png" class="wx-header-img"/><h5>&nbsp;我的礼品</h5></a></div>
	</div>
	<div class="wx-line"></div>
	<div class="wx-title"><h5>全部礼品</h5></div>
	<ul class="mui-table-view mui-grid-view" style="margin:0px;padding:0px;">
		<c:if test="${products != null}">
		<c:forEach var="row" items="${products}" varStatus="i">
		<li class="mui-table-view-cell mui-media mui-col-xs-6 mui-col-sm-4 wx-bb" style="margin:0;padding-bottom:5px;padding-right:5px;">
			<a href="${path }/weixin/member/coin/exchange.shtml?id=${row.id}">
				<img class="ui-media-object" src="${row.showImg }" style="width:100%;height:100%;">
				<div class="mui-media-body">${row.name }</div>
				<p class='mui-ellipsis wx-highlight-color'><fmt:formatNumber value="${row.coinPrice }" pattern="#,##0"/>&nbsp;<img src="${path }/weixin/common/images/Icon_coin.png" class="wx-header-img"/></p>
			</a>
		</li>
		</c:forEach>
		</c:if>
	</ul>
</body>
</html>
