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
	<title>分享好友</title>
    <link rel="stylesheet" type="text/css" href="${path}/weixin/common/css/mui.min.css">
    <link rel="stylesheet" type="text/css" href="${path }/weixin/common/css/zepto.alert.css">
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
    .wx-share-img{width: 60px;height: 60px;margin-right: 5px;}
     #mcover {position: fixed;top: 0;left: 0;width: 100%;height: 100%;background: rgba(0, 0, 0, 0.7);display: none;z-index: 20000;}
	 #mcover img {position: fixed;right: 18px;top: 5px;width: 260px!important;height: 180px!important;z-index: 20001;}
    
    </style>
</head>
<body>
	<div><img src="${path}/weixin/common/images/friend.jpg" style="width:100%;"/></div>
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
			<div style="text-align: center;" ><br />
				<img src="${path}/weixin/common/images/share_qq.png" id="shareQQbtn" class="wx-share-img"/>
				<img src="${path}/weixin/common/images/share_wx.png" class="wx-share-img"/>
				<img src="${path}/weixin/common/images/share_wxq.png" class="wx-share-img"/>
				<img src="${path}/weixin/common/images/share_qwb.png" class="wx-share-img"/>
			</div>
		</li>
	</ul>
	<div id="mcover" style="display: block;" onclick="weChat()">
		<img src="${path}/weixin/common/images/share_div.png"/>
	</div>
</body>
<script src="${path }/weixin/common/js/zepto.min.js" ></script>
<script type="text/javascript" src="${path }/weixin/common/js/zepto.alert.js" ></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
	wx.config({
		debug : false, 
		appId : '${params.appId}',
		timestamp : '${params.timestamp}',
		nonceStr : '${params.nonceStr}',
		signature : '${params.signature}',
		jsApiList: [
            'checkJsApi',
            'onMenuShareTimeline',
            'onMenuShareAppMessage',
            'onMenuShareQQ',
            'onMenuShareWeibo'
        ]
	});
	function weChat(){ $("#mcover").css("display","none"); }
	var complete=function(){
		$.ajax({
			url: "${path}/weixin/member/task/complete.shtml",type: "POST",data:{taskId:${task.id}},dataType: "json",cache: false,
			beforeSend:function(){},
			success: function(data) {
				$.dialog({content : "分享成功"+(data.type=="success"?",任务完成":""),title : 'ok',width:250,ok : function() {}});
			},
			complete:function(){},
			error:function(event, request, settings){alert("${fn:call('Common.net.networkNotAllowed')}");}
		});
	}
	
	wx.ready(function(){
		wx.checkJsApi({
		 jsApiList: ['onMenuShareTimeline'],
		 success: function(res) {/*alert(JSON.stringify(res));*/}
		});
		wx.onMenuShareTimeline({
		    title: '${task.content}', // 分享标题
		    link: '${task.url}', // 分享链接
		    imgUrl: '${task.imgUrl}', // 分享图标
		    success: function () { complete();}, //成功回调
		    cancel: function () { } //失败回调
		});
		wx.onMenuShareAppMessage({
		    title: '${task.content}', // 分享标题
		    desc: '', // 分享描述
		    link: '${task.url}', // 分享链接
		    imgUrl: '${task.imgUrl}', // 分享图标
		    type: '', // 分享类型,music、video或link，不填默认为link
		    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
		    success: function () { complete();},
		    cancel: function () { }
		});
		wx.onMenuShareQQ({
		    title: '${task.content}', // 分享标题
		    desc: '', // 分享描述
		    link: '${task.url}', // 分享链接
		    imgUrl: '${task.imgUrl}', // 分享图标
		    success: function () { complete();},
		    cancel: function () { }
		});
		wx.onMenuShareWeibo({
		    title: '${task.content}', // 分享标题
		    desc: '', // 分享描述
		    link: '${task.url}', // 分享链接
		    imgUrl: '${task.imgUrl}', // 分享图标
		    success: function () {  complete();},
		    cancel: function () {}
		});
						
    });
	
</script>
</html>
