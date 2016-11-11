<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<% request.setAttribute("path",request.getContextPath()); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
<meta name="viewport" content="width=device-width,user-scalable=no,initial-scale=1,maximum-scale=1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>幸运大抽奖</title>
<script src="${path}/weixin/common/js/jquery.1.11.0.min.js"></script>
<script type="text/javascript">//if(!isWeiXin()){window.location.href="${path}/weixin/wxbrowser.jsp";}</script>
<style type="text/css">
*{padding: 0px;margin: 0px;}
.top{width: 100%;}
#lottery{width: 100%;text-align: center;}
#lottery .lottery-wrap{width:90%;border:4px solid #faa018;margin-left: auto;margin-right: auto;}
#lottery table{width: 100%;background-color: #ffffff;border: 5px solid #a46611;}
#lottery p{width: 90%;margin-left: auto;margin-right: auto;text-align: left;padding-top: 10px;font-size: 14px;color: #fff;}
#lottery h5{padding:10px;color: #fafafa;}
#lottery table td{width: 80px;height: 80px;text-align: center;vertical-align: middle;font-size: 24px;color: #666666;border: 2px solid #a46611;}
#lottery table td img{width: 100%;}
#lottery table td a{display: block;text-decoration: none;}
#lottery table td.active{background-color: #FFFF66;border:2px dashed #4CD964;width: 76px;height: 76px;}
.my_coin{background: #383838;opacity:0.8;border: 1px solid #fafafa;width: 200px;height: 40px;line-height:40px;margin:10px auto 10px auto; border-radius: 4px;text-align: center;color: #fafafa;}
.lottery-unit{position: relative;}
.lottery-unit .title{position: absolute;width:100%;height:25px;line-height:25px;z-index: 1000;bottom:0px;left:0px;background-color: rgba(152, 152, 152, 0.6);font-size: 14px;white-space:nowrap;overflow:hidden; text-overflow:ellipsis;}
</style>
<link rel="stylesheet" type="text/css" href="${path }/weixin/common/css/zepto.alert.css">
</head>
<body style="background:#ab2522;">
<img src="${path}/weixin/common/images/sudoku_hb.jpg" class="top" />
<div id="lottery">
	<c:set value="${game.gameItems}" var="gameItems"></c:set>
	<div class="lottery-wrap">
	<table cellspacing="0">
		<tr>
			<td class="lottery-unit lottery-unit-0"><img src="${gameItems[0].image }"/><div class="title">${gameItems[0].title }</div></td>
			<td class="lottery-unit lottery-unit-1"><img src="${gameItems[1].image }"/><div class="title">${gameItems[1].title }</div></td>
			<td class="lottery-unit lottery-unit-2"><img src="${gameItems[2].image }"/><div class="title">${gameItems[2].title }</div></td>
			<td class="lottery-unit lottery-unit-3"><img src="${gameItems[3].image }"/><div class="title">${gameItems[3].title }</div></td>
		</tr>
		<tr>
			<td class="lottery-unit lottery-unit-9"><img src="${gameItems[9].image }"/><div class="title">${gameItems[9].title }</div></td>
			<td id="startBtn"colspan="2" style="background-color: #a46611;"><img src="${path }/weixin/common/images/sudoku_start.png" style="width:100%;height:100%"/></td>
			<td class="lottery-unit lottery-unit-4"><img src="${gameItems[4].image }"/><div class="title">${gameItems[4].title }</div></td>
		</tr>
		<tr>
			<td class="lottery-unit lottery-unit-8"><img src="${gameItems[8].image }"/><div class="title">${gameItems[8].title }</div></td>
			<td class="lottery-unit lottery-unit-7"><img src="${gameItems[7].image }"/><div class="title">${gameItems[7].title }</div></td>
			<td class="lottery-unit lottery-unit-6"><img src="${gameItems[6].image }"/><div class="title">${gameItems[6].title }</div></td>
			<td class="lottery-unit lottery-unit-5"><img src="${gameItems[5].image }"/><div class="title">${gameItems[5].title }</div></td>
		</tr>
	</table>
	</div>
	<div class="my_coin"><img src="${path}/weixin/common/images/default-coin.png" style="width: 26px;height: 26px;vertical-align: middle;">&nbsp;我的金币<font color="#FB6832" id="userScore">${member.score }</font>个</div>
	<p>${game.description }</p>
	<h5>声明:本活动及提供礼品与腾讯公司无关</h5>
</div>
<script type="text/javascript" src="${path }/weixin/common/js/zepto.alert.js" ></script>
<script type="text/javascript">
var lottery={
	index:0,	//当前转动到哪个位置
	count:0,	//总共有多少个位置
	timer:0,	//setTimeout的ID，用clearTimeout清除
	speed:200,	//初始转动速度
	times:0,	//转动次数
	cycle:50,	//转动基本次数：即至少需要转动多少次再进入抽奖环节
	prize:-1,	//中奖位置
	init:function(id){
		if ($("#"+id).find(".lottery-unit").length>0) {
			$lottery = $("#"+id);
			$units = $lottery.find(".lottery-unit");
			this.obj = $lottery;
			this.count = $units.length;
			$lottery.find(".lottery-unit-"+this.index).addClass("active");
		};
	},
	roll:function(){
		var index = this.index;
		var count = this.count;
		var lottery = this.obj;
		$(lottery).find(".lottery-unit-"+index).removeClass("active");
		index += 1;
		if (index>count-1) {
			index = 0;
		};
		$(lottery).find(".lottery-unit-"+index).addClass("active");
		this.index=index;
		return false;
	},
	stop:function(index){
		this.prize=index;
		return false;
	}
};
var hortationItem = null;
function roll(){
	lottery.times += 1;
	lottery.roll();
	if (lottery.times > lottery.cycle+10 && lottery.prize==lottery.index) {
		clearTimeout(lottery.timer);
		lottery.prize=-1;
		lottery.times=0;
		click=false;
		$("#userScore").text(hortationItem.score);
		$.dialog({content : hortationItem.message,title : 'ok',width:250,ok : function() {}});
	}else{
		if (lottery.times<lottery.cycle) {
			lottery.speed -= 10;
		}else if(lottery.times==lottery.cycle) {
			var item = $.parseJSON(hortationItem.result);
			lottery.prize = item.hortationIndex;
		}else{
			if (lottery.times > lottery.cycle+10 && ((lottery.prize==0 && lottery.index==7) || lottery.prize==lottery.index+1)) {
				lottery.speed += 110;
			}else{
				lottery.speed += 20;
			}
		}
		if (lottery.speed<40) {
			lottery.speed=40;
		};
		lottery.timer = setTimeout(roll,lottery.speed);
	}
	return false;
}
var click=false;
window.onload=function(){
	lottery.init('lottery');
	$("#startBtn").click(function(){
		if (click) {return false;}
		else{
			$.ajax({
				url: "${path}/weixin/member/game/lottery.shtml?"+Math.random(),type: "POST",dataType: "json",cache: false,
				beforeSend:function(){},
				data:{id:${game.id}},
				success: function(data) {
					if(data.type == "success"){
						hortationItem = $.parseJSON(data.content);lottery.speed=100;roll();click=true;return false;
					}else{$.dialog({content : data.content,title : 'ok',width:250,ok : function() {}});}
				},
				complete:function(){},
				error:function(event, request, settings){alert("${fn:call('Common.net.networkNotAllowed')}");}
			});
		}
	});
};
</script>
</body>
</html>