<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<% request.setAttribute("path",request.getContextPath()); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width,user-scalable=no,initial-scale=1,maximum-scale=1" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>幸运转盘 </title>
	<link rel="stylesheet" type="text/css" href="${path }/weixin/common/css/zepto.alert.css">
	<script src="${path}/weixin/common/js/jquery.1.11.0.min.js"></script>
	<script type="text/javascript">if(!isWeiXin()){window.location.href="${path}/weixin/wxbrowser.jsp";}</script>
	<style>
	*{padding:0; margin:0;}
	body{background: #D91B35;width:100%;}
	.lotteryMain{ width:280px;height:280px;position: relative;margin-left:auto;margin-right:auto;margin-top:20px;text-align: ce}
	.lotteryBg{width: 100%; position:absolute; overflow:hidden;z-index:1;}
	#run{width: 50px; left:115px; top:105px; position:absolute; z-index:2; transform:rotate(0deg); -ms-transform:rotate(0deg); }
	#btn_run{ width:42px; left:119px; top:120px; border:none; outline:none; position:absolute; z-index:2;cursor:pointer;}
	.wx-solt-tip{ margin:20px 10px 10px 10px;min-height: 100px;padding:10px;font-size:13px; word-wrap:break-word;background-color: #ffedaf; border-radius: 5px;clear: both;}
	</style>
</head>

<body style="background: #D91B35;">
<div class="lotteryMain">
    <img id="lotteryBg" src="${game.image}" class="lotteryBg" style="display: none;"/>
    <img id="run" src="${path}/weixin/common/images/start.png" style="-webkit-transform: rotate(0deg);display: none;">
    <img id="btn_run" src="${path}/weixin/common/images/btn_start.png" style="display: none;">
    <h4 id="load" style="position: absolute;top:105px;width:100%;text-align: center;">加载中...</h4>
</div>
<div class="wx-solt-tip"><h4 style="font-size: 15px;">规则说明：</h4>${game.description }</div>
<script type="text/javascript" src="${path }/weixin/common/js/zepto.alert.js" ></script>
<script src="${path}/weixin/common/js/Rotate.js"></script>
<script>
var $lotteryBg=$("#lotteryBg");
var $btn_run=$("#btn_run");
var $run=$("#run");
$lotteryBg.one('load', function() {$("img").show(); $("#load").remove(); }).each(function() { if(this.complete) $(this).load(); });
$btn_run.click(function(){$("#btn_run").attr('disabled',true).css("cursor","default");lottery();});
function lottery(){ 
	var hortationItem = null;
	$.ajax({
		url: "${path}/weixin/member/game/lottery.shtml?"+Math.random(),type: "POST",dataType: "json",cache: false,
		beforeSend:function(){},
		data:{id:${game.id}},
		success: function(data) {
			if(data.type == "success"){
				hortationItem = $.parseJSON(data.content);
				var result = $.parseJSON(hortationItem.result);
				$("#run").rotate({ 
					duration:5000, //转动时间 
					angle: 0, //默认角度
					animateTo:360*6+parseFloat(hortationItem.angle), //转动角度 
					easing: $.easing.easeOutSine, 
					callback: function(){ 
						$.dialog({content : hortationItem.message,title : 'ok',width:250,ok : function() {}});
						$("#btn_run").attr('disabled',false).css("cursor","pointer"); 
					} 
				});
			}else{$.dialog({content : data.content,title : 'ok',width:250,ok : function() {}});}
		},
		complete:function(){},
		error:function(event, request, settings){alert("${fn:call('Common.net.networkNotAllowed')}");}
	});
};
</script>  
</body></html>