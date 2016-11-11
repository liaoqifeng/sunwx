<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>注册</title>

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

	<ul class="mui-table-view" style="margin-top: 15px;">
		<li class="mui-table-view-cell mui-hidden">cared
			<div id="M_Toggle" class="mui-switch mui-active">
				<div class="mui-switch-handle"></div>
			</div>
		</li>
		<c:forEach var="row" items="${factions}" varStatus="i2">
			<li class="mui-table-view-cell mui-media"><a href="javascript:select('${row.id }')" class="mui-navigate-right"> <img
					class="mui-media-object mui-pull-left" src="${row.imagePath}">
					<div class="mui-media-body" style="line-height: 40px;">${row.name }
							<span style="color: gray;font-weight: normal;">${row.neighborhoodName }</span></div></a></li>
		</c:forEach>
	</ul>


</body>
<script type="text/javascript">
	function select(id){
		window.location.href="${path}/weixin/regedit2.shtml?id="+id;
	}
</script>

</html>