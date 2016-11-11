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
	<title>水果机 </title>
	<link rel="stylesheet" href="${path }/weixin/common/css/mui.min.css" />
	<link rel="stylesheet" type="text/css" href="${path }/weixin/common/css/zepto.alert.css">
	<script type="text/javascript" src="${path }/weixin/common/js/jquery-2.1.1.min.js"></script>
	<script type="text/javascript" src="${path }/weixin/common/js/jquery.slotmachine.min.js"></script>
	<script type="text/javascript" src="${path }/weixin/common/js/zepto.alert.js" ></script>
	<script type="text/javascript">if(!isWeiXin()){window.location.href="${path}/weixin/wxbrowser.jsp";}</script>
	<style>
		.wx-solt-h{width: 320px;height: 252px;background: url(${path }/weixin/common/images/slot_header.png);margin: auto;position: relative;}
		.wx-solt-b{width: 320px;min-height: 150px;background: #f5a439;margin: auto;border-left: 1px solid #d0771d;border-right: 1px solid #d0771d;border-bottom: 1px solid #d0771d;}
		.wx-solt-tip{width: 98%;min-height: 100px;font-size:13px;padding:10px; word-wrap:break-word;background-color: #ffedaf; border-radius: 5px;margin: auto;margin-bottom: 20px;}
		.wx-score{width: 250px;height: 30px;line-height:30px;position: absolute;left:40px;top:54px;text-align: center;font-size: 14px;}
		.lotteryBox{width: 282px;height: 106px;position: absolute;top: 109px;left:20px;border-radius: 5px;border: 5px solid #bd0100;}
		.slotMachine{width: 33.333333%;border-left: 5px solid #bd0100;height: 95px;line-height: 95px;padding: 0px;overflow: hidden;text-align: center;background-color: #ffffff;float: left;}
		.slotMachine img{width:100%;height:95px;}
		.machineResult{color:#fff;text-align:center;font-weight: 900;}
		.noBorder{border:none !important;}
		.slotMachine .slot{height:100%;background-position-x: 55%;background-repeat: no-repeat;}
	</style>
</head>
<body style="background: #F4B54C;">
	<div class="wx-solt-h">
		<div class="wx-score">我的金币<font color="#FB6832">${member.score }</font>个</div>
		<div class="lotteryBox">
			<div id="machine1" class="slotMachine noBorder">
				<c:if test="${game.gameItems != null}">
				<c:forEach var="row" items="${game.gameItems}">
				<div class="slot"><img src="${row.image }"/></div>
				</c:forEach>
				</c:if>
			</div>
			<div id="machine2" class="slotMachine">
				<c:if test="${game.gameItems != null}">
				<c:forEach var="row" items="${game.gameItems}">
				<div class="slot"><img src="${row.image }"/></div>
				</c:forEach>
				</c:if>
			</div>
			<div id="machine3" class="slotMachine">
				<c:if test="${game.gameItems != null}">
				<c:forEach var="row" items="${game.gameItems}">
				<div class="slot"><img src="${row.image }"/></div>
				</c:forEach>
				</c:if>
			</div>
		</div>
	</div>
	<div class="wx-solt-b">
		<button id="slotMachineButtonShuffle" class="mui-btn mui-btn-negative mui-btn-block">开始抽奖</button>
		<button id="slotMachineButtonStop" class="mui-btn mui-btn-primary mui-btn-block" style="display: none;">停止抽奖</button>
		<div class="wx-solt-tip"><h4 style="font-size: 15px;">规则说明：</h4>${game.rules }</div>
	</div>
</body>
<script>
$(document).ready(function(){
		var machine1 = $("#machine1").slotMachine({active	: 0,delay	: 500});
		var machine2 = $("#machine2").slotMachine({active	: 0,delay	: 500});
		var machine3 = $("#machine3").slotMachine({active	: 0,delay	: 500});
		var $shuffle=$("#slotMachineButtonShuffle");
		var $stop=$("#slotMachineButtonStop");
		var started = false;
		var hortationItem=null;
		var result=null;
		$shuffle.click(function(){
			var $this = $(this);
			if(!started){
				started = !started;
				$.ajax({
					url: "${path}/weixin/member/game/lottery.shtml?"+Math.random(),type: "POST",dataType: "json",cache: false,
					beforeSend:function(){},
					data:{id:${game.id}},
					success: function(data) {
						if(data.type == "success"){
							hortationItem = $.parseJSON(data.content);
							result = $.parseJSON(hortationItem.result);
							machine1.shuffle();
							machine2.shuffle();
							machine3.shuffle();
							$this.hide();
							$stop.show();
						}else{$.dialog({content : data.content,title : 'ok',width:250,ok : function() {}});}
					},
					complete:function(){},
					error:function(event, request, settings){alert("${fn:call('Common.net.networkNotAllowed')}");}
				});
			}
		});
		$stop.click(function(){
			if(started){
				setTimeout(function(){
					machine1.stop(false,hortationItem.indexs[0]);
				},1000);
				setTimeout(function(){
					machine2.stop(false,hortationItem.indexs[1]);
				},2000);
				setTimeout(function(){
					machine3.stop(false,hortationItem.indexs[2]);
				},3000);
				setTimeout(function(){
					started = !started;
					$stop.hide();
					$shuffle.show();
					$.dialog({content : hortationItem.message,title : 'ok',width:250,ok : function() {}});
				},4000);
			}
		});
	});
</script>
</html>