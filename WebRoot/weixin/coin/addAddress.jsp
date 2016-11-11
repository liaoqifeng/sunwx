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
    <title>新增地址</title>
    <link rel="stylesheet" type="text/css" href="${path }/weixin/common/css/mui.min.css">
    <link rel="stylesheet" type="text/css" href="${path }/weixin/common/css/zepto.loading.css">
    <link rel="stylesheet" type="text/css" href="${path }/weixin/common/css/alert.css">
    <link rel="stylesheet" type="text/css" href="${path }/weixin/common/plugin/select_area/mobile-select-area.min.css">
    <style type="text/css">
    .mui-table-view-cell:after{left:0px;}
    .wx-clear{clear:both;}
    .wx-clear-p{padding: 0px;}
    .wx-clear-m{margin: 0px;}
    .wx-text{height: 25px;line-height: 25px;}
    .wx-hr{height: 10px;width: 100%; border: 10px;background: #F2F2F2;padding: 0px;margin: 0px;}
    </style>
</head>
<body>
	<ul class="mui-table-view">
		<li class="mui-table-view-cell mui-media" style="height: 50px;">
			<span class="mui-pull-left" style="margin-top: 4px;">收货人:</span>
			<div class="mui-media-body"><input type="text" id="receiver" class="wx-text" maxlength="50" style="height: 30px;border: 0px;"/></div>
		</li>
		<li class="mui-table-view-cell mui-media" style="height: 50px;">
			<span class="mui-pull-left" style="margin-top: 4px;">联系电话:</span>
			<div class="mui-media-body"><input type="text" id="phone" class="wx-text" maxlength="20" style="height: 30px;border: 0px;"/></div>
		</li>
		<li class="mui-table-view-cell mui-media" style="height: 50px;">
			<span class="mui-pull-left" style="margin-top: 4px;">所在地区:</span>
			<div class="mui-media-body"><input type="text" id="txt_area" maxlength="100" class="wx-text" style="height: 30px;border: 0px;"/><input type="hidden" id="hd_area" value=""/></div>
		</li>
		<li class="mui-table-view-cell mui-media" style="height: 50px;">
			<span class="mui-pull-left" style="margin-top: 4px;">详细地址:</span>
			<div class="mui-media-body"><input type="text" id="address" class="wx-text" maxlength="255" style="height: 30px;border: 0px;"/></div>
		</li>
	</ul>
	<hr class="wx-hr"/>
	<button id="okBtn" class="mui-btn mui-btn-negative mui-btn-block">确&nbsp;&nbsp;&nbsp;&nbsp;定</button>
</body>
<script type="text/javascript" src="${path }/weixin/common/plugin/select_area/zepto.min.js"></script>
<script type="text/javascript" src="${path }/weixin/common/js/zepto.loading.js"></script>
<script type="text/javascript" src="${path }/weixin/common/plugin/select_area/dialog.min.js"></script>
<script type="text/javascript" src="${path }/weixin/common/plugin/select_area/mobile-select-area.min.js"></script>
<script>
var selectArea = new MobileSelectArea();
var $loading = null;
$.ajax({
	url: "${path}/weixin/common/initAreaSelect.shtml",type: "POST",dataType: "json",cache: true,
	beforeSend:function(){$loading = $('body').loading()},
	success: function(data) {
		selectArea.init({trigger:'#txt_area',value:$('#hd_area').val(),data:data.data});
	},
	complete:function(){$loading.removeLoading();},
	error:function(event, request, settings){alert("${fn:call('Common.net.networkNotAllowed')}");}
});
var $hd_area=$('#hd_area');var $receiver=$("#receiver");var $phone=$("#phone");var $address=$("#address");
var $okBtn = $("#okBtn").click(function(){
	var receiver=$receiver.val(),phone=$phone.val(),address=$address.val(),area=$hd_area.val();;
	if(isEmpty(receiver) || isEmpty(phone) || isEmpty(address) || isEmpty(area)){wxAlert("提示","请填写完整");return false;}
	if(!isMobileOrTel(phone)){wxAlert("提示","请正确填写联系电话");return false;}
	var as=area.split(",");
	if(as[2]=="0"){area = as[1];}else if(as[1]=="0"){area = as[0];}else{area = as[2];}
	$.ajax({
		url: "${path}/weixin/member/coin/addAddress.shtml?"+Math.random(),data:{receiver:receiver,phone:phone,address:address,areaId:area},type: "POST",dataType: "json",cache: false,
		beforeSend:function(){$okBtn.prop("disabled",true);},
		success: function(data) {
			if(data.type=="success"){
				window.location.href="${path}/weixin/member/coin/exchange.shtml?id=${param.id}";
			}else{
				wxAlert("提示",data.content);
			}
		},
		complete:function(){$okBtn.prop("disabled",false);},
		error:function(event, request, settings){alert("${fn:call('Common.net.networkNotAllowed')}");}
	});
});
</script>
</html>
