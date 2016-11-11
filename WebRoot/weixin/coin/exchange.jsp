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
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" /> 
	<meta http-equiv="Pragma" content="no-cache" /> 
	<meta http-equiv="Expires" content="0" />
	<title>礼品兑换</title>
    <link rel="stylesheet" type="text/css" href="${path }/weixin/common/css/mui.min.css">
    <link rel="stylesheet" type="text/css" href="${path }/weixin/common/css/zepto.alert.css">
    <style type="text/css">
    .mui-table-view-cell:after{left:0px;}
    .wx-clear{clear:both;}
    .wx-clear-btop:after{height: 0px;}
    .wx-clear-bdown:before{height: 0px;}
    .wx-clear-p{padding: 0px;}
    .wx-clear-m{margin: 0px;}
    .wx-padding{padding: 10px;}
    .wx-highlight-color{color: #ff4800;}
    .wx-line{width:100%;height:10px;background-color: #efefef;border-top:1px solid #dddddd;border-bottom:1px solid #dddddd;}
    .wx-title{font-size: 15px;font-weight: bold;color:#000000;padding-bottom: 2px;}
    .wx-introduction p{line-height: 1px;}
    .wx-list-img{width: 60px;height: 60px;max-width: 60px;margin-right: 10px;}
    .wx-swrap{display: inline-block;font-size: 12px;line-height: 1;position: absolute;top: 20%;right: 0px;}
    </style>
</head>
<body>
	<div id="slider" class="mui-slider">
		<div class="mui-slider-group">
			<c:if test="${fn:length(product.productImages) > 0}">
			<c:forEach var="row" items="${product.productImages}">
			<div class="mui-slider-item"><img src="${row.source }"></div>
			</c:forEach>
			</c:if>
		</div>
		<div class="mui-slider-indicator">
			<c:if test="${fn:length(product.productImages) > 0}">
			<c:forEach var="row" items="${product.productImages}" varStatus="i">
			<div class="mui-indicator ${i.index==0?'mui-active':'' }"></div>
			</c:forEach>
			</c:if>
		</div>
	</div>
	<h4 id="texta" class="wx-highlight-color wx-padding" style="margin:auto;">${product.fullName }</h4>
	<div class="wx-introduction wx-padding" style="width:100%;">${product.introduction }</div>
	<div class="wx-line"></div>
	<button id="okBtn" class="mui-btn mui-btn-negative mui-btn-block">确认兑换</button>
	
</body>
<script src="${path }/weixin/common/js/zepto.min.js" ></script>
<script src="${path }/weixin/common/js/zepto.touch.js" ></script>
<script src="${path }/weixin/common/js/slider-app.js" ></script>
<script src="${path }/weixin/common/js/zepto.alert.js" ></script>
<script>
$("#slider").slider({duration:6000});
var $okBtn = $("#okBtn").click(function(){
	$.dialog({
		content : '是否确定兑换?',title : 'ok',width:250,
		ok : function() {
			$.ajax({
				url: "${path}/weixin/member/coin/doExchange.shtml?"+Math.random(),data:{receiverId:1,productId:"${product.id}"},type: "POST",dataType: "json",cache: false,
				beforeSend:function(){$okBtn.prop("disabled",true);},
				success: function(data) {
					if(data.type=="success"){
						$.dialog({content : "礼物已兑换成功,请查收",title : 'ok',width:250,ok : function() {}});
					}else{
						$.dialog({content : data.content,title : 'ok',width:250,ok : function() {}});
					}
				},
				complete:function(){$okBtn.prop("disabled",false);},
				error:function(event, request, settings){alert("${fn:call('Common.net.networkNotAllowed')}");}
			});
		},
		cancel:function(){}
	});
});
$(".wx-swipe-btn").click(function(){
	var $this = $(this);
	$.dialog({
		content : '是否确定删除?',title : 'ok',width:250,
		ok : function() {
			$.ajax({
				url: "${path}/weixin/member/coin/removeAddress.shtml?"+Math.random(),data:{receiverId:$this.data("id")},type: "POST",dataType: "json",cache: false,
				beforeSend:function(){$this.prop("disabled",true);},
				success: function(data) {
					$.dialog({content : data.content,title : 'ok',width:250,ok : function() {}});
					if(data.type=="success"){$this.closest("li").remove();}
				},
				complete:function(){$this.prop("disabled",false);},
				error:function(event, request, settings){alert("${fn:call('Common.net.networkNotAllowed')}");}
			});
		},
		cancel:function(){}
	});
});
</script>
</html>
