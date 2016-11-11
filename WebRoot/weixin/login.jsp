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
    <title>登录</title>
    <link rel="stylesheet" type="text/css" href="${path }/weixin/common/css/mui.min.css">
    <link rel="stylesheet" type="text/css" href="${path }/weixin/common/css/zepto.alert.css">
    <style type="text/css">
    .mui-table-view-cell:after{left:0px;}
    .wx-clear{clear:both;}
    .wx-clear-p{padding: 0px;}
    .wx-clear-m{margin: 0px;}
    .wx-text{height: 25px;line-height: 25px;}
    .wx-hr{height: 10px;width: 100%; border: 10px;background: #F2F2F2;padding: 0px;margin: 0px;}
    .logo{width: 150px;height: 150px;border-radius:50%; margin-left: auto;margin-right: auto;background: #ffffff;margin-top: 20px;text-align: center;}
    </style>
</head>
<body>
	<ul class="mui-table-view">
		<li class="mui-table-view-cell mui-media" style="background: #15B9D5;height: 200px;">
			<div class="logo">
				<h5 style="padding-top: 35px;font-weight: bold;"><font>SUN -</font> <font color="#23BCA7">LIGHT</font></h5> 
				<h4 style="margin-top:20px;color: #23BCA7;"><b>阳光城外高桥</b></h4>
				<h6 style="margin-top:20px;"><b>商业微信服务</b></h6>
			</div>
		</li>
		<li class="mui-table-view-cell mui-media" style="height: 50px;">
			<span class="mui-icon mui-icon-person mui-pull-left" style="margin-top: 2px;"></span>
			<div class="mui-media-body"><input type="text" id="username" placeholder="用户名" class="wx-text" style="height: 30px;border: 0px;"/></div>
		</li>
		<li class="mui-table-view-cell mui-media" style="height: 50px;">
			<span class="mui-icon mui-icon-locked mui-pull-left" style="margin-top: 2px;"></span>
			<div class="mui-media-body"><input type="password" id="password" placeholder="密码" class="wx-text" style="height: 30px;border: 0px;"/></div>
		</li>
	</ul>
	<hr class="wx-hr"/>
	<button id="loginBtn" class="mui-btn mui-btn-negative mui-btn-block">登&nbsp;&nbsp;&nbsp;&nbsp;录</button>
	<div class="wx-clear"></div>
 	<a href="forget.jsp" class="mui-pull-left" style="margin-left: 5px;color:gray;"><h4>重置密码</h4></a>
	<a href="regedit1.shtml" class="mui-pull-right" style="margin-right: 5px;color:gray;"><h4>注册>></h4>&nbsp;&nbsp;&nbsp;</a>&nbsp;
</body>
<script src="${path }/weixin/common/js/zepto.min.js" ></script>
<script src="${path }/weixin/common/js/zepto.alert.js" ></script>
<script>
	var $loginBtn = $("#loginBtn");var $username = $("#username");var $password = $("#password");
	$loginBtn.click(function(){
		if($.trim($username.val())==""){$.dialog({content :"请输入用户名",title : 'ok',width:250,ok : function() {}}); return false;}
		if($.trim($password.val())==""){$.dialog({content :"请输入密码",title : 'ok',width:250,ok : function() {}}); return false;}
		 $.ajax({
			url: "${path}/weixin/login.shtml",type: "POST",dataType: "json",data:{username:$username.val(),password:$password.val()},cache: false,
			beforeSend:function(){$loginBtn.prop("disabled",true);$loginBtn.html("正在登录,请稍等...");},
			success: function(data) {
				if(data.type == "success"){
					var redirectUrl = "${param.redirectUrl}";
					if(redirectUrl == "") redirectUrl = '${path}/weixin/member/coin/index.shtml';
					window.location.href = redirectUrl;
				}else{
					alert(data.content);$loginBtn.prop("disabled",false);$loginBtn.html("登&nbsp;&nbsp;&nbsp;&nbsp;录");
				}
			},
			complete:function(){},
			error:function(event, request, settings){alert("${fn:call('Common.net.networkNotAllowed')}");$loginBtn.prop("disabled",false);$loginBtn.html("登&nbsp;&nbsp;&nbsp;&nbsp;录");}
		});
	 });
</script>
</html>
