<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>天地汇</title>
<%@ include file="/weixin/post/metainfo.jsp"%>
<script type="text/javascript" src="${path }/weixin/common/js/post.js"></script>
<script type="text/javascript" src="${path }/weixin/common/js/ejs.js"></script>
<script type="text/javascript" src="${path }/weixin/common/js/iscroll.js"></script>
<style type="text/css">
.factionDescribe {
	color: #313131;
	font-size: 14px;
	line-height: 25px;
}

.factionTitle {
	color: #313131;
	font-size: 24px;
	line-height: 30px;
	font-weight: bold;
}

.scoreTitle {
	color: #a0a0a0;
	font-size: 14px;
	line-height: 25px;
}

.scoreNumber {
	color: red;
	font-size: 24px;
	line-height: 30px;
	font-weight: bold;
}

.memberName {
	color: #313131;
	font-size: 18px;
	line-height: 40px;
	vertical-align: middle;
}
</style>


<script type="text/javascript">
	
</script>
</head>
<body>
	<ul class="mui-table-view">
		<li class="mui-table-view-cell mui-hidden">cared
			<div id="M_Toggle" class="mui-switch mui-active">
				<div class="mui-switch-handle"></div>
			</div>
		</li>
		<li class="mui-table-view-cell mui-media">
			<div style="min-height: 110px;">
				<img class="mui-pull-left" style="height: 100px;width: 100px;border-radius: 5px;"
					src="${faction.imagePath }">
					<div style="margin-left: 120px;">
						<span class="factionTitle">${faction.name }</span><span class="factionDescribe">${faction.describe
							}</span> <br />
						<c:if test="${faction.calculate ==0 }">
							<span class="scoreTitle">当前积分：</span>
							<span class="scoreNumber"><fmt:formatNumber type="number" pattern="###,###,###,###"
									value="${score}" /></span>&nbsp;<img src="../common/images/coin.png" alt="" />
						</c:if>
					</div>
			</div>
			<div style="border-top: 1px solid #dddddd;padding-top: 10px;">
				<div style="float:left;width:  90px;">
					<img src="../common/images/gonggao.png"
						style="width: 12px;height: 16px;vertical-align: middle;"></img>&nbsp;<span
						style="color: #313131;font-size: 16px;vertical-align: middle;">社区公告</span>
				</div>
				<div style="margin-left:90px; ">
					<marquee style="width:100%;" scrollamount="2" direction="left" align="middle">
					<p>${faction.notice }</p>
					</marquee>
				</div>
			</div>
		</li>
	</ul>
	<ul class="mui-table-view">
		<c:forEach var="row" items="${members }">
			<c:if test="${row.type==0 }">
				<li class="mui-table-view-divider" id="div_${row.name }">${row.value }</li>
			</c:if>
			<c:if test="${row.type==1 }">
				<li class="mui-table-view-cell mui-media" onclick="goUser('${row.value.id }')"><img
					class="mui-media-object mui-pull-left" style="border-radius: 5px;"
					src="${row.value.profileImage }"><span class="memberName">${row.value.realname }</span></li>
			</c:if>
		</c:forEach>
	</ul>


	<div style="position:fixed; top: 200px;right: 0px;">
		<c:forEach var="row" items="${members }">
			<c:if test="${row.type==0 }">
				<div onclick="click_scroll('div_${row.name }');"
					style="width: 15px;text-align: center;height: 15px;font-size: 14px;">${row.value }</div>
			</c:if>
		</c:forEach>

	</div>
</body>
<script type="text/javascript">
	function goUser(id) {
		window.location.href = "${path}/weixin/post/main.shtml?type=2&memberId="
				+ id;
	}

	function click_scroll(name) {
		var scroll_offset = $("#" + name).offset(); //得到pos这个div层的offset，包含两个值，top和left
		//alert(scroll_offset.top);
		$("body,html").animate({
			scrollTop : scroll_offset.top
		//让body的scrollTop等于pos的top，就实现了滚动
		}, 0);
		event.stopPropagation();
	}
	function initComments() {
		$.ajax({
			url : "${path}/weixin/post/getComments.shtml",// 跳转到 action
			type : 'post',
			cache : false,
			dataType : 'json',
			data : {
				postId : "${post.id}"
			},
			success : function(data) {
				$("#comment_${post.id}").text(data.length);
				if (data.length > 0) {
					var d = {
						list : data,
						path : "${path}"
					};
					var html = new EJS({
						url : "${path}/weixin/post/comment.ejs"
					}).render(d);
					$("#commentUl").html(html);
				}
			},
			error : function() {
				alert("系统异常");
			}
		});
	}

	function initPraises() {
		$.ajax({
			url : "${path}/weixin/post/getPraises.shtml",// 跳转到 action
			type : 'post',
			cache : false,
			dataType : 'json',
			data : {
				postId : "${post.id}"
			},
			success : function(data) {
				$("#praise_${post.id}").text(data.length);
				if (data.length > 0) {
					var d = {
						list : data,
						path : "${path}"
					};
					var html = new EJS({
						url : "${path}/weixin/post/praise.ejs"
					}).render(d);
					$("#praiseContentDiv").html(html);
				}
			},
			error : function() {
				alert("系统异常");
			}
		});
	}
</script>

</html>