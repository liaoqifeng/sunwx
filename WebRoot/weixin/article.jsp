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
    <title>${article.title }</title>
    <link rel="stylesheet" type="text/css" href="${path }/weixin/common/css/mui.min.css">
    <style type="text/css">
    .mui-table-view:after{height:0;}
    .mui-table-view-cell:after{height:0;padding:0;}
    .mui-table-view-cell p{font-size:16px;}
    .wx-detail{height:20px;line-height:20px;padding:0;text-align: center;}
    .wx-content img{width:100%;}
    </style>
</head>

<body>
	<ul class="mui-table-view">
		<li class="mui-table-view-cell">${article.articleTitle }</li>
		<li class="mui-table-view-cell wx-detail"><h5><fmt:formatDate value="${article.createDate}" pattern="yyyy-MM-dd HH:mm"/>&nbsp;&nbsp;作者：${article.author }</h5></li>
		<li class="mui-table-view-cell" style="padding:0;"><div class="bdsharebuttonbox" style="width:190px;margin:auto;"><a class="bds_sqq" title="分享到QQ好友" href="#" data-cmd="sqq"></a><a class="bds_qzone" title="分享到QQ空间" href="#" data-cmd="qzone"></a><a class="bds_tsina" title="分享到新浪微博" href="#" data-cmd="tsina"></a><a class="bds_tqq" title="分享到腾讯微博" href="#" data-cmd="tqq"></a><a class="bds_weixin" title="分享到微信" href="#" data-cmd="weixin"></a><a class="bds_tqf" title="分享到腾讯朋友" href="#" data-cmd="tqf"></a></div></li>
		<li class="mui-table-view-cell wx-content">${article.content }</li>
	</ul>
<script>window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"${article.articleTitle }","bdMini":"1","bdMiniList":false,"bdPic":"${article.image }","bdStyle":"0","bdSize":"24"},"share":{}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>
</body>
</html>
