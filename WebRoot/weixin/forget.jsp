<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>重置密码</title>

<%
	request.setAttribute("path", request.getContextPath());
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
			<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
				<link rel="stylesheet" type="text/css" href="${path }/weixin/common/css/mui.css" />

				<script type="text/javascript" src="${path }/common/js/jquery-1.11.0.min.js"></script>
				<style type="text/css">
.img {
	width: 40px;
	height: 40px;
}
</style>
</head>
<body>
		<ul class="mui-table-view">
			<li class="mui-table-view-cell mui-media" style="height: 50px;"><span class="mui-pull-left"
				style="margin-top: 5px;">手机号:</span>
				<div class="mui-media-body">
					<input type="text" class="wx-text" style="height: 30px;border: 0px;" name="mobile" id="mobile" placeholder="请输入手机号"/>
				</div></li>
			<li class="mui-table-view-cell mui-media" style="height: 50px;"><span class="mui-pull-left"
				style="margin-top: 5px;">验证码:</span>
				<div class="mui-media-body">
					<input type="text" placeholder="请输入验证码" class="wx-text" style="height: 30px;border: 0px;"
						maxlength="9" id="code" name="code" />
				</div>
				<button class="mui-btn" onclick="return sendCode()" id="codeButton">获取验证码</button></li>
			<li class="mui-table-view-cell mui-media" style="height: 50px;"><span class="mui-pull-left"
				style="margin-top: 5px;">密&nbsp;&nbsp;&nbsp;&nbsp;码:</span>
				<div class="mui-media-body">
					<input type="password" placeholder="设定你用于登录的密码" class="wx-text" style="height: 30px;border: 0px;"
						name="password" id="password" />
				</div></li>
			<li class="mui-table-view-cell mui-media" style="height: 50px;"><span class="mui-pull-left"
				style="margin-top: 5px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
				<div class="mui-media-body">
					<input type="password" placeholder="再次输入密码" class="wx-text" style="height: 30px;border: 0px;"
						name="password1" id="password1" />
				</div></li>
		</ul>
	<div id="message" style="text-align: center;margin-top: 10px;">&nbsp;</div>
	<button class="mui-btn mui-btn-negative mui-btn-block" style="margin: 20px 0px;"
		onclick="submit();">重&nbsp;&nbsp;&nbsp;&nbsp;置</button>



</body>
<script type="text/javascript">
	function submit() {
		var mobile = $("#mobile").val();
		var code = $("#code").val();
		var password = $("#password").val();
		var password1 = $("#password1").val();
		if (mobile == "") {
			alert("请输入手机号");
		} else if (password == "") {
			alert("请输入密码");
		} else if (password.length <6) {
			alert("密码长度必须大于等于6位");
		}else if (password1 == "") {
			alert("请输入密码");
		}else if (password1.length <6) {
			alert("密码长度必须大于等于6位");
		}else if (password1 != password) {
			alert("两次密码输入不一致");
		}else if (code == "") {
			alert("请输入验证码");
		} else {
			$.ajax({
				url : "${path}/weixin/forget/checkCode.shtml",// 跳转到 action
				type : 'post',
				cache : false,
				dataType : 'json',
				data : {
					mobile : mobile,
					code : code,
					password : password,
					password1 : password1
				},
				success : function(data) {
					if (data.type == "success") {
						alert("密码重置成功,请重新登录");
						window.location.href="${path}/weixin/login.jsp";
					} else {
						alert(data.content);
					}
				},
				error : function() {
				
				}
			});

		}

	}

	var getCodeFlag = true;
	var interval;
	function hasGetCode() {
		getCodeFlag = false;
		// view("修改成功！");    
		var time = 60;
		$("#codeButton").text("等待  " + time);
		interval = setInterval(function() {
			time--;
			$("#codeButton").text("等待  " + time);
			if (time == 0) {
				clearInterval(interval);
				$("#codeButton").text("获取验证码 ");
				getCodeFlag = true;
			}
		}, 1000);
	}

	function sendCode() {
		if (getCodeFlag) {
			hasGetCode();
			var mobile = $("#mobile").val();
			if (mobile == "") {
				alert("请输入手机号");
				clearInterval(interval);
				$("#codeButton").text("获取验证码");
				getCodeFlag = true;
				return false;
			}
			$.ajax({
				url : "${path}/weixin/forget/sendCode.shtml",// 跳转到 action
				type : 'post',
				cache : false,
				dataType : 'json',
				data : {
					mobile : mobile
				},
				success : function(data) {
					if (data.type == "success") {
						$("#message").text(data.content);
					} else {
						$("#message").text(data.content);
						clearInterval(interval);
						$("#codeButton").text("获取验证码");
						getCodeFlag = true;
					}
				},
				error : function() {
					
				}
			});
		}
		return false;
	}
</script>

</html>