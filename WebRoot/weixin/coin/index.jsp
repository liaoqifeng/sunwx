<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<% request.setAttribute("path",request.getContextPath()); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0">
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <title>挖金币</title>
    <link rel="stylesheet" type="text/css" href="${path}/weixin/common/css/mui.min.css">
    <link rel="stylesheet" type="text/css" href="${path }/weixin/common/css/zepto.alert.css">
    <style type="text/css">
    .mui-table-view-cell:after{left:0;height:0;}
    .wx-clear{clear:both;}
    .wx-clear-btop:after{height: 0;}
    .wx-clear-bdown:before{height: 0;}
    .wx-clear-p{padding: 0px;}
    .wx-padding{padding: 10px;}
    .wx-highlight-color{color: #ff4800;}
    .wx-line{width:100%;height:10px;background-color: #efefef;border-top:1px solid #dddddd;border-bottom:1px solid #dddddd;}
    .wx-header{width:100%;height:130px;background-color: #ff4800;position: relative;z-index: 10000;text-align: center;}
    .wx-profile{width:100%;height:135px;background:url(${path}/weixin/common/images/coin-headbg.png) no-repeat;; background-position:50% 50%;text-align: center;position: absolute;z-index:1;bottom: -16px;}
    .wx-head-tip{position: absolute;left:10px;top:5px;z-index:2;}
    .wx-head-woyao{position: absolute;width:120px;height:29px;z-index:3;margin-top:80px;right:55%;}
    .wx-head-score{position:absolute;margin-top:80px;right:10px;z-index:4;color:#fff;}
    .wx-obh{ display: inline-block;vertical-align: middle;border-radius: 50%;width: 80px;height: 80px;border: 5px solid #ffb428;margin:auto;margin-top:48px;}
	.wx-obh img{border-radius: 50%; width: 70px;height: 70px;border: none;}
    .wx-dig{width:100%;position: relative;padding-top:20px;}
    .wx-dig-left{width:60%;height:50%;position: absolute;}
    .wx-dig-left .button{height:30px;border:none;background-color: #ff4800;border-radius: 50px;color:#fff;}
    .wx-zjb img{width: 70%;border-radius:50%;}
    .wx-shop{background: #ffffff;}
    .wx-shop img{width: 100%;}
    .wx-highlight {color:#D84C29;}
    .wx-mask { position: fixed;_position: absolute;padding:0px;left: 0;top: 0;width: 100%;height: 100%;z-index:999999999;background-color:rgba(0,0,0,0.7);}
    .wx-shop .wx-header-img{vertical-align: middle;width:16px;height:16px;}
    .time-item{text-align: center;}
    .time-item strong{background:#ff4800;color:#fff;font-size:14px;font-family:Arial;padding:0 5px;border-radius:5px;box-shadow:1px 1px 3px rgba(0,0,0,0.2);}
	#day_show{float:left;line-height:49px;color:#c71c60;font-size:32px;font-family:Arial, Helvetica, sans-serif;display:none;}
    .wx-bonus-s{position: relative;width:280px;min-height:150px;background-color: #fecd32;border-radius:10px;margin-top:150px;margin-left:auto;margin-right:auto;padding-top:40px;text-align:center;}
    .wx-bonus-s .success{position:absolute;width:120px;height:83px;right:0px;top:-60px;background:url("${path}/weixin/common/images/bonus_sucess.png") no-repeat;}
    .wx-bonus-s .fail{position:absolute;width:90px;height:73px;right:20px;top:-40px;background:url("${path}/weixin/common/images/bonus_fail.png") no-repeat;}
    .wx-bonus-s .content{width:90%;min-height:50px;margin-left:auto;margin-right:auto;text-align:center;}
    .wx-bonus-s .btn{width:150px;height:35px;background-color: #ee3614;border: 0;border-radius:50px;color:#fff;}
    </style>
</head>
<body>
	<div class=" wx-header">
		<div class="wx-head-tip">
			<img src="${path}/weixin/common/images/icon_accou.png" class="mui-pull-left" style="width:20px;height:20px;margin-top:4px;margin-right:10px;">
			<span style="color: #fff;"><h4 class="mui-pull-left"/>${member.realname }</h4></span>
		</div>
		<div class="wx-profile">
			<c:if test="${empty member.profileImage}"><div class="wx-obh"><img src="${path}/weixin/common/images/default_profile.jpg" /></div></c:if>
			<c:if test="${!empty member.profileImage}"><div class="wx-obh"><img src="${member.profileImage}" /></div></c:if>
		</div>
		<img src="${path}/weixin/common/images/woyao.png" class="wx-head-woyao"/>
		<h4 class="wx-head-score"><label id="scoreLabel"><fmt:formatNumber value="${member.score }" pattern="#,##0"/></label>&nbsp;<font style="font-size: 14px;">金币</font></h4>
	</div>
	<div id="digCoinBtn" class="wx-dig" style="${!empty bonus?'display:none;':''}">
		<div class="wx-dig-left">
			<c:if test="${!dubMap.isOver}">
				<div id="wajinbiTimer" style="width:80%;text-align: center;">
					<h5 style="color: #00b1cd;">挖金币倒计时</h5>
					<div class="time-item">
						<span id="day_show">0天</span>
						<strong id="hour_show"><s id="h"></s>0时</strong>
						<strong id="minute_show"><s></s>00分</strong>
						<strong id="second_show"><s></s>00秒</strong>
					</div>
				</div>
				<div id="wajinbiTip" style="width:80%;text-align: center;display: none;">
					<h5 style="color: #00b1cd;">快挖吧,时间不多了</h5>
					<div class="time-item">
						<strong id="hour_dug"><s id="h"></s>0时</strong>
						<strong id="minute_dug"><s></s>00分</strong>
						<strong id="second_dug"><s></s>00秒</strong>
					</div>
					<button id="clickBtn" class="button" style="vertical-align: middle;">点击挖金币</button>
				</div>
			</c:if>
			<c:if test="${dubMap.isOver}">
				<h5 id="wajinbiOver" style="text-align: center;width:70%;color: #00b1cd;padding-top:10%;">挖金币活动结束</h5>
			</c:if>
		</div>
		<img id="wajinbiLoadImg" src="${path}/weixin/common/images/wajingbi.png" style="width: 100%;"/>
		<img id="wajinbiImg" src="${path}/weixin/common/images/wajinbi.gif" style="width: 100%;display: none;"/>
	</div>
	<c:if test="${!empty bonus}">
	<div id="bonusBtn" class="wx-dig" style="padding-top:0;background-color: #ffeb8c;">
		<img id="bonusImg" src="${path}/weixin/common/images/bonus_bg.jpg" style="width: 100%;">
	</div>
	</c:if>
	<div class="wx-line"></div>
	<ul class="mui-table-view">
		<li class="mui-table-view-cell wx-clear-p">
			<div class="wx-zjb">
				<ul class="mui-table-view mui-grid-view mui-grid-4 wx-clear-bdown wx-clear-btop">
					<li class="mui-table-view-cell mui-media mui-col-xs-3">
						<a href="${path }/weixin/member/task/index.shtml">
							<img src="${path}/weixin/common/images/task_icon.jpg" />
							<div class="mui-media-body">超级任务</div>
						</a>
					</li>
					<li class="mui-table-view-cell mui-media mui-col-xs-3">
						<a href="${path }/weixin/member/game/sudoku.shtml">
							<img src="${path}/weixin/common/images/sudoku_icon.jpg" />
							<div class="mui-media-body">幸运大抽奖</div>
						</a>
					</li>
					<li class="mui-table-view-cell mui-media mui-col-xs-3">
						<a href="${path }/weixin/member/game/rotate.shtml">
							<img src="${path}/weixin/common/images/rotate_icon.jpg" />
							<div class="mui-media-body">幸运转盘</div>
						</a>
					</li>
					<li class="mui-table-view-cell mui-media mui-col-xs-3">
						<a href="${path }/weixin/member/game/guaguale.shtml">
							<img src="${path}/weixin/common/images/guaguale_icon.jpg" />
							<div class="mui-media-body">刮刮乐</div>
						</a>
					</li>
				</ul>
			</div>
		</li>
		<li class="wx-line"></li>
		<li class="mui-table-view-cell wx-clear-p">
			<h5 class="mui-pull-left" style="padding: 10px 0px 0px 10px;">金币超市</h5>
			<h5 class="mui-pull-right" style="padding: 10px 10px 0px 10px;"><a href="${path }/weixin/member/coin/gift.shtml" class="wx-highlight">更多礼品》</a></h5>
			<div class="wx-shop wx-clear">
				<ul class="mui-table-view mui-grid-view mui-grid-3 wx-clear-bdown wx-clear-btop">
					<c:if test="${products != null}">
					<c:forEach var="row" items="${products}">
					<li class="mui-table-view-cell mui-media mui-col-xs-4">
						<a href="${path }/weixin/member/coin/exchange.shtml?id=${row.id}">
							<img src="${row.showImg }" />
							<h4 class="mui-media-body">${row.name }</h4>
							<h5><font class="wx-highlight-color"><fmt:formatNumber value="${row.coinPrice }" pattern="#,#00"/></font>&nbsp;<img src="${path }/weixin/common/images/Icon_coin.png" class="wx-header-img"/></h5>
						</a>
					</li>
					</c:forEach>
					</c:if>
				</ul>
			</div>
		</li>
	</ul>
	<div id="wx-mask" class="wx-mask" style="display: none;">
		<img alt="" src="${path}/weixin/common/images/dug_success.png" style="width:100%;" />
		<div style="width:100%;position: relative;"><img alt="" src="${path}/weixin/common/images/dug_success_tip.png" style="width:100%;position: absolute;" /><h4 style="position: absolute;left:10px;right:10px;text-align: center;padding-top:7%;color: #fff;">成功挖到了<b class="wx-highlight-color score">0</b>金币</h4></div>
	</div>
	<script src="${path }/weixin/common/js/zepto.min.js" ></script>
	<script src="${path }/weixin/common/js/zepto.alert.js" ></script>
	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script>
	var $wajinbiLoadImg = $("#wajinbiLoadImg");
	var $wajinbiImg = $("#wajinbiImg");
	var $wajinbiTimer = $("#wajinbiTimer");
	var $wajinbiTip = $("#wajinbiTip");
	var $scoreLabel = $("#scoreLabel");
	$.ajax({
		url: "${path}/weixin/common/findScore.shtml?"+Math.random(),type: "GET",dataType: "json",cache: false,
		success: function(data) {$scoreLabel.text(data);}
	});
	var dugTime=${dugCoin.lastTime}*60;
	var gapTime=0;
	var oldTime=sessionStorage.getItem("oldTime");
	if(oldTime){gapTime=Math.floor((new Date().getTime()-oldTime)/1000); }else{sessionStorage.setItem("oldTime",new Date().getTime());}
	function setDugTimer(timerInter){
		window.clearInterval(timerInter);
		$wajinbiTimer.hide();
		$wajinbiTip.show();
		dugTimer(dugTime);
	}
	function timer(intDiff){
		var timerInter = window.setInterval(function(){
		var day=0, hour=0, minute=0, second=0;		
		if(intDiff > 0){
			day = Math.floor(intDiff / (60 * 60 * 24));
			hour = Math.floor(intDiff / (60 * 60)) - (day * 24);
			minute = Math.floor(intDiff / 60) - (day * 24 * 60) - (hour * 60);
			second = Math.floor(intDiff) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
		}else{
			setDugTimer(timerInter);
		}
		if (minute <= 9) minute = '0' + minute;
		if (second <= 9) second = '0' + second;
		$('#day_show').html(day+"天");
		$('#hour_show').html('<s id="h"></s>'+hour+'时');
		$('#minute_show').html('<s></s>'+minute+'分');
		$('#second_show').html('<s></s>'+second+'秒');
		intDiff--;
		}, 1000);
	}
	function dugTimer(intDiff){
		var timerInter = window.setInterval(function(){
		var day=0, hour=0, minute=0, second=0;		
		if(intDiff > 0){
			day = Math.floor(intDiff / (60 * 60 * 24));
			hour = Math.floor(intDiff / (60 * 60)) - (day * 24);
			minute = Math.floor(intDiff / 60) - (day * 24 * 60) - (hour * 60);
			second = Math.floor(intDiff) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
		}else{
			window.clearInterval(timerInter);
			window.location.reload();
		}
		if (minute <= 9) minute = '0' + minute;
		if (second <= 9) second = '0' + second;
		$('#hour_dug').html('<s id="h"></s>'+hour+'时');
		$('#minute_dug').html('<s></s>'+minute+'分');
		$('#second_dug').html('<s></s>'+second+'秒');
		intDiff--;
		}, 1000);
	}
	<c:if test="${dubMap.isOver}">$wajinbiTip.hide();$wajinbiTimer.hide();</c:if>
	<c:if test="${!dubMap.isOver && dubMap.isHaveDug}">dugTimer(Math.abs(${dubMap.second})-gapTime);$wajinbiTip.show();$wajinbiTimer.hide();</c:if>
	<c:if test="${!dubMap.isOver && !dubMap.isHaveDug}">timer(${dubMap.second}-gapTime);$wajinbiTip.hide();$wajinbiTimer.show();</c:if>
	$("#clickBtn").click(function(){
		$wajinbiLoadImg.hide();
		$wajinbiImg.show();
		$wajinbiTip.hide();
		$.ajax({
			url: "${path}/weixin/member/coin/dug.shtml",type: "GET",dataType: "json",cache: false,
			success: function(data) {
				if(data.type == "success"){
					setTimeout(function(){
						$wajinbiImg.hide();
						$wajinbiLoadImg.show();
						$wajinbiTimer.hide();
						$wajinbiTip.show();
						$("#wx-mask").show().find(".score").text(data.content);
						setTimeout(function(){$("#wx-mask").hide(); $scoreLabel.text(parseInt($scoreLabel.text())+parseInt(data.content));},3000);
					},3000);
				}else{
					$wajinbiImg.hide();
					$wajinbiLoadImg.show();
					$wajinbiTip.show();
					$.dialog({content :data.content,title : 'ok',width:250,ok : function() {}});
				}
			},
			complete:function(){}, error:function(event, request, settings){alert("${fn:call('Common.net.networkNotAllowed')}");}
		});
	});
	wx.config({
		debug : false, appId : '${params.appId}',timestamp : '${params.timestamp}',nonceStr : '${params.nonceStr}',signature : '${params.signature}',jsApiList : [ 'closeWindow' ]
	});
	$("#bonusBtn").click(function(){
		var $this = $(this);
		$.ajax({
			url: "${path}/weixin/member/coin/sendBonus.shtml",type: "POST",dataType: "json",cache: false,
			success: function(data) {
				if(data.type == "success"){
					var html='<div class="wx-mask"><div class="wx-bonus-s"><div class="success"></div>'+
					'<div class="content">'+
						'<h4 style="color: #d94902;">'+data.content+'</h4>'+
						'<p style="color: #a5672c;font-size: 13px;">稍后您会收到公众号的领取通知</p>'+
					'</div><button class="btn makeBonusBtn" onclick="wx.closeWindow();">去拆红包</button></div></div>';
					$("body").append(html);
					$this.remove();$("#digCoinBtn").show();
				}else{
					$.dialog({content :data.content,title : 'ok',width:250,ok : function() {}});
				}
			},
			complete:function(){}, error:function(event, request, settings){alert("${fn:call('Common.net.networkNotAllowed')}");}
		});
	});
	</script>
</body>
</html>
