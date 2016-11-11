<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0">
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
		<title>微信支付页面</title>
	</head>
	<body>
	公众号：${params.appId}<br/>
	时间戳：${params.timeStamp}<br/>
	随机串：${params.nonceStr}<br/>
	扩展包：${packageStr}<br/>
	微信签名：${params.sign}<br/>
	
	<button id="getBrandWCPayRequest">支付测试</button>
	</body>
	<script type="text/javascript">
	//当微信内置浏览器完成内部初始化后会触发WeixinJSBridgeReady事件。
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
		alert("init");
		//公众号支付
		document.getElementById("getBrandWCPayRequest").addEventListener("click",function(){
			WeixinJSBridge.invoke('getBrandWCPayRequest',{
				"appId" : "${params.appId}", //公众号名称，由商户传入
				"timeStamp" : "${params.timeStamp}", //时间戳
				"nonceStr" : "${params.nonceStr}", //随机串
				"package" : "${packageStr}", //扩展包
				"signType" : "MD5", //微信签名方式:
				"paySign" : "${params.sign}" ////微信签名
			},function(res){
				// if(res.err_msg == get_brand_wcpay_request:ok ) {
				alert( res.err_msg ); // alert("OK");
				// }
				// 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回ok，但并不保证它绝对可靠。
				//因此微信团队建议，当收到ok返回时，向商户后台询问是否收到交易成功的通知，若收到通知，前端展示交易成功的界面；若此时未收到通知，商户后台主动调用查询订单接口，查询订单的当前状态，并反馈给前	展示相应的界面。
			});
		});
		
		WeixinJSBridge.log('yo~ ready.');
	}, false);
	</script>
</html>