<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
request.setAttribute("path",request.getContextPath());
%>
<!DOCTYPE HTML>
<html>
<head>
<title></title>
<meta charset="UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">


<style type="text/css">
</style>
</head>

<body>
<form id="payForm" action="${path}/O2O/wechat/pay.shtml" method="post">
	<input type="submit" value="去支付"/>
</form>
</body>
</html>

