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
	<title>我的礼品</title>
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
		<div class="box"><a href="${path }/weixin/member/coin/gift.shtml"><img src="${path }/weixin/common/images/Icon_gift.png" class="wx-header-img"/><h5>&nbsp;全部礼品</h5></a></div>
	</div>
	<ul class="mui-table-view mui-grid-view" style="margin:0px;padding:0px;">
		<c:if test="${gifts == null || fn:length(gifts)<=0}">
		<li class="mui-table-view-cell mui-media" style="text-align: center;width:100%;"><h5>您还没得到任何礼物,加油吧!</h5></li>
		</c:if>
		<c:if test="${gifts != null && fn:length(gifts)>0}">
		<c:forEach var="row" items="${gifts}">
		<li class="mui-table-view-cell mui-media mui-col-xs-6 mui-col-sm-4 wx-bb" style="margin:0;padding-bottom:5px;padding-right:5px;">
			<a href="#">
				<img class="ui-media-object" src="${row.productImage }" style="width:100%;height:100%;">
				<div class="mui-media-body">${row.productName }</div>
				<p><h5>兑换时间:<fmt:formatDate value="${row.createDate}" pattern="yyyy-MM-dd"/></h5></p>
				<p class='mui-ellipsis wx-highlight-color'><c:if test="${!row.isCollect}"><span class="mui-badge mui-badge-danger">未领取</span></c:if><c:if test="${row.isCollect}"><span class="mui-badge mui-badge-success">已领取</span></c:if>&nbsp;<fmt:formatNumber value="${row.score }" pattern="#,#00"/>&nbsp;<img src="${path }/weixin/common/images/Icon_coin.png" class="wx-header-img"/></p>
			</a>
		</li>
		</c:forEach>
		</c:if>
	</ul>
</body>
</html>
