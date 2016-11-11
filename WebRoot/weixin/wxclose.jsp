<%@page import="com.gson.util.ConfKit"%>
<%@page import="com.gson.util.WechatUtil"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + path + "/";
	String code = request.getParameter("code");
	String url = (basePath + "wxclose.jsp");

	System.out.println(url);
	String signature = WechatUtil.getSignature(url);
	System.out.println(signature);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title></title>
<meta charset="UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">


<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
</style>
</head>

<body>

</body>
<script type="text/javascript">
	wx.config({
		debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		appId : '<%=ConfKit.get("AppId")%>', // 必填，公众号的唯一标识
		timestamp : <%=WechatUtil.TIMESTAMP%>, // 必填，生成签名的时间戳
		nonceStr : '<%=WechatUtil.NONCESTR%>', // 必填，生成签名的随机串
		signature : '<%=signature%>',
		jsApiList : [ 'closeWindow' ]
	// 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
	wx.ready(function() {
		wx.closeWindow();
		// config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
	});
</script>
</html>

