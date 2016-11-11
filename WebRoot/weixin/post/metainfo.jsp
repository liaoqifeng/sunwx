<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	//response.setHeader("Cache-Control", "no-store");
	//response.setDateHeader("Expires", 0);
	//response.setHeader("Pragma", "no-cache");
	request.setAttribute("path", request.getContextPath());
	request.setAttribute("root","http://"+ request.getServerName());
	System.out.print("http://"+ request.getServerName());
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


<!--
<script type="text/javascript" src="${path }/back/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${path }/common/js/jquery.json-2.4.js"></script>
<script type="text/javascript" src="${path }/common/js/jquery.timer.js"></script>
<script type="text/javascript" src="${path }/common/js/jquery.tools.js"></script>
<script type="text/javascript" src="${path }/back/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${path }/common/js/jquery.ajaxfileupload.js" charset="utf-8"></script>
<script type="text/javascript" src="${path }/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${path }/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${path }/back/js/jquery.common.js"></script>
  -->
</script>