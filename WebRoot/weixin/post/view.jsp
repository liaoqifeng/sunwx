<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>天地汇</title>
<%@ include file="/weixin/post/metainfo.jsp"%>
<link rel="stylesheet" type="text/css" href="${path }/weixin/common/css/zepto.alert.css">
	<script type="text/javascript" src="${path }/weixin/common/js/post.js"></script>
	<script type="text/javascript" src="${path }/weixin/common/js/ejs.js"></script>
	<script type="text/javascript" src="${path }/weixin/common/js/iscroll.js"></script>
	<script type="text/javascript" src="${path }/weixin/common/js/swipeButton-app.js"></script>
	<script type="text/javascript" src="${path }/weixin/common/js/zepto.alert.js"></script>
	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<style type="text/css">
* {
	margin: 0px;
	padding: 0px;
}

.postToolbar {
	height: 40px;
	line-height: 40px;
	vertical-align: middle;
	padding: 0 15px;
}

.viewToolbar {
	box-sizing: border-box;
	-moz-box-sizing: border-box; /* Firefox */
	-webkit-box-sizing: border-box;
	bottom: 0px;
} /* Safari */
.viewToolbarA {
	border-left: 1px solid #dddddd;
}

.okbtn {
	margin: 10px;
	margin-top: 50px;
}

#imageDiv {
	background-color: #fff;
	border-bottom: 1px solid #dddddd;
}

#selectDiv {
	width: 100px;
	float: right;
	height: 30px;
	margin-top: 5px;
	border: 1px solid #dddddd;
	border-radius: 5px;
}

#sector {
	height: 30px;
	background: rgba(0, 0, 0, 0);
	border: 1px solid #dddddd;
	vertical-align: middle;
	padding: 0px 10px;
	text-align: center;
}

#sector option {
	text-indent: 20px;
	height: 50px;
	border: 1px solid #dddddd;
}

.entry-trangleBorder {
	position: absolute;
	margin-top: -11px;
	width: 0;
	height: 0;
	border-right: 10px solid transparent;
	border-left: 10px solid transparent;
	border-bottom: 10px solid #dddddd;
	z-index: 200;
}

.entry-trangle {
	position: absolute;
	margin-top: -10px;
	width: 0;
	height: 0;
	border-right: 10px solid transparent;
	border-left: 10px solid transparent;
	border-bottom: 10px solid #fff;
	z-index: 200;
}

#praiseContentDiv {
	padding: 0px 0px;
}

.praiseImage {
	width: 30px;
	height: 30px;
	padding: 0px;
	box-sizing: border-box;
	-moz-box-sizing: border-box; /* Firefox */
	-webkit-box-sizing: border-box; /* Safari */
	display: inline-block;
	float: left;
	margin: 10px 5px;
	border-radius: 3px;
}

.picContainer {
	width: 30%;
	position: relative;
	display: inline-block;
	margin: 1%;
}

.picDummy {
	padding-bottom: 100%;
}

.picContent {
	position: absolute;
	left: 0;
	right: 0;
	top: 0;
	bottom: 0;
}

.pic {
	width: 100%;
	height: 100%;
}

.postPic {
	float: left;
	width: 80px;
	height: 80px;
	border: 1px solid #ccc;
	margin-right: 5px;
	margin-bottom: 5px;
}

.activityContent {
	
}

.activityContentDiv {
	
}

.activityImgDiv {
	width: 120px;
	height: 120px;
	padding: 10px;
	float: right;
}

.activityImage {
	float: right;
	width: 90px;
	margin: 5px;
}

.lineleft {
	float: left;
	width: 30px;
	padding: 12px 5px;
}

.lineright {
	float: right;
	width: 30px;
	padding: 12px 5px;
}

.linecenter {
	margin: 0 30px;
}

.commentContent {
	color: #313131;
	font-size: 14px;
	line-height: 20px;
}

.commentTitle {
	color: #313131;
	font-size: 16px;
	line-height: 20px;
}

.commentDate {
	color: #a0a0a0;
	font-size: 10px;
	line-height: 15px;
}

.postContent {
	color: #313131;
	font-size: 14px;
	line-height: 20px;
}

.postMemberName {
	color: #a0a0a0;
	font-size: 18px;
	line-height: 20px;
}

.postDate {
	color: #f4d000;
	font-size: 10px;
	line-height: 20px;
}

.activityContent {
	color: #a0a0a0;
	font-size: 14px;
	line-height: 25px;
	vertical-align: middle;
}

.activityTitle {
	color: #313131;
	font-size: 18px;
	line-height: 25px;
}

.commentPic {
	width: 30px;
	height: 30px;
	line-height: 30px;
	border-radius: 3px;
}

.sector {
	background-color: red;
	color: #fff;
	font-size: 10px;
	padding: 0px 5px;
}

.commentPraiseDiv {
	display: table-cell;
	width: 1em;
	height: 1em;
	font-size: 144px;
	width: 144px;
	height: 100%;
	font-size: 118px;
	border: 1px solid #beceeb;
	text-align: center;
	vertical-align: middle;
}

.commentPraisePic {
	position: absolute;
	top: 50%;
	right: 12px;
	margin-top: -12px;
}

#mcover {
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background: rgba(0, 0, 0, 0.7);
	display: none;
	z-index: 20000;
}

#mcover img {
	position: fixed;
	right: 18px;
	top: 5px;
	width: 260px !important;
	height: 180px !important;
	z-index: 20001;
}
</style>


	<script type="text/javascript">
		var praiseScroll;
		var joinScroll;
		var mainScroll;
		function initJoinScroll() {
			if (joinScroll != null) {
				joinScroll.destroy();
			}
			joinScroll = new iScroll('joinWrapper', {
				hScrollbar : false,
				vScroll : false,
				onScrollEnd : setJoinScroll
			});

		}

		function loaded() {
			mainScroll = new iScroll('mainWrapper');

		}

		function initPraiseScroll() {
			if (praiseScroll != null) {
				praiseScroll.destroy();
			}
			praiseScroll = new iScroll('praiseWrapper', {
				hScrollbar : false,
				vScroll : false,
				onScrollEnd : setPraiseScroll
			});
		}

		function setPraiseScroll() {
			if (praiseScroll.x == 0) {
				$("#praisePre").css("display", "none");
			} else {
				$("#praisePre").css("display", "block");
			}
			if (praiseScroll.maxScrollX > 0
					|| praiseScroll.x == praiseScroll.maxScrollX) {
				$("#praiseNext").css("display", "none");
			} else {
				$("#praiseNext").css("display", "block");
			}
		}

		function setJoinScroll() {
			if (joinScroll.x == 0) {
				$("#joinPre").css("display", "none");
			} else {
				$("#joinPre").css("display", "block");
			}
			if (joinScroll.maxScrollX > 0
					|| joinScroll.x == joinScroll.maxScrollX) {
				$("#joinNext").css("display", "none");
			} else {
				$("#joinNext").css("display", "block");
			}
		}

		document.addEventListener('DOMContentLoaded', function() {
			setTimeout(loaded, 200);
		}, false);
		//document.addEventListener('DOMContentLoaded', loaded, false);
	</script>
</head>
<body>
	<div
		style="overflow: auto;bottom: <c:if test="${!isLogin}">50px;</c:if><c:if test="${isLogin}">0px;</c:if>position: absolute;top:0px;right:0px;left:0px;"
		id="mainWrapper">
		<div>
			<div>
				<ul class="mui-table-view">
					<li class="mui-table-view-cell mui-hidden">
						<div id="M_Toggle" class="mui-switch mui-active">
							<div class="mui-switch-handle"></div>
						</div>
					</li>
					<li class="mui-table-view-cell mui-media"><img class="mui-media-object mui-pull-left"
						style="border-radius: 5px;" src="${post.member.profileImage }"
						onclick="selectMember('${post.member.id}')"></img>
						<div style="margin-left: 50px;">
							<span class="postMemberName">${post.member.realname}</span> <span class="sector"
								style="float: right;"> <c:if test="${post.type  =='1'}">
										${post.postSector.name}
									</c:if> <c:if test="${post.type  !='1'}">
										活动
									</c:if>
							</span>
							<p class="postDate">${post.showDate}</p>
							<c:if test="${post.type  =='1'}">
								<p class="activityTitle">${post.title }</p>
								<p class="postContent">${post.content}</p>
								<c:forEach var="image" items="${post.postImages}" varStatus="i2">
									<div class='picContainer'>
										<div class='picDummy'></div>
										<div class='picContent'>
											<img class="pic" src="${image.name }"
												onclick="prePic('${image.name }' , '${post.postImagesStr }')"></img>
										</div>
									</div>
									<!-- 
									<img class="postPic" src="${path }${image.name }"
										onclick="prePic('${path }${image.name }' , '${post.postImagesStr }')"></img> -->
								</c:forEach>


							</c:if>
							<c:if test="${post.type  =='2'}">


								<c:if test="${post.postImages.size()>0}">
									<img src="${post.postImages[0].name }" class="activityImage"
										onclick="prePic('${post.postImages[0].name }' , '${post.postImagesStr }')" />
								</c:if>


								<div class="activityContentDiv">
									<p class="activityTitle">${post.title }</p>
									<p class="activityContent">
										<img src="../common/images/pin.png" style="vertical-align: middle;" />&nbsp;${post.area }
									</p>
									<p class="activityContent">
										<img src="../common/images/clock.png" style="vertical-align: middle;" />&nbsp;${post.beginDateStr
										}
									</p>
									<p class="activityContent">
										<img src="../common/images/info.png" style="vertical-align: middle;" />&nbsp;
										<c:if test="${post.content ==null}">无活动说明</c:if>
										<c:if test="${post.content !=null}">${post.content }</c:if>
									</p>
								</div>




							</c:if>
						</div></li>
					<c:if test="${post.type  =='2'}">
						<li class="mui-table-view-cell mui-media" style="color: #313131;font-size: 16px;">已报名参加(<span
							id="join_${post.id }">0</span>)
							<div id="joinContentDiv"></div>
						</li>
					</c:if>
			</div>



			<div class="postToolbar">
				<a href="javascript:changeComment()" id="commentTool" style="color: #000;">评论&nbsp;&nbsp;<span
					id="comment_${post.id }">0</span></a> <a id="praiseTool" style="color: gray;float: right;"
					href="javascript:changePraise()">赞 &nbsp;&nbsp;<span id="praise_${post.id }">0</span></a>
			</div>
			<div style="border-top: 1px solid #dddddd;display: none;" id="commentDiv">

				<div class="entry-trangleBorder" style="margin-left: 40px;"></div>
				<div class="entry-trangle" style="margin-left: 40px;"></div>
				<ul class="mui-table-view" id="commentUl">
					<li class="mui-table-view-cell mui-hidden">cared
						<div id="M_Toggle" class="mui-switch mui-active">
							<div class="mui-switch-handle"></div>
						</div>
					</li>


					<li class="mui-table-view-cell mui-media">&nbsp;</li>


				</ul>
			</div>
			<div style="border-top: 1px solid #dddddd; display:none ;background-color: #fff;height: 50px;"
				id="praiseDiv">

				<div class="entry-trangleBorder" style="right:30px;"></div>
				<div class="entry-trangle" style="right: 30px;"></div>
				<div id="praiseContentDiv">&nbsp;</div>
			</div>
		</div>
	</div>
	<c:if test="${!isLogin}">
		<nav class="mui-bar mui-bar-tab viewToolbar"> <a href="javascript:zf()"
			class="mui-tab-item viewToolbarA"> <span class="mui-tab-label">转发</span>
		</a> <a class="mui-tab-item viewToolbarA"
			style="height: 30px;<c:if test="${!isComment}">display: none;</c:if>"
			href="javascript:comment('${post.id}')"> <span class="mui-tab-label">评论</span>
		</a> <a class="mui-tab-item viewToolbarA" href="javascript:praise('${post.id}')"
			style="height: 30px;<c:if test="${!isPraise}">display: none;</c:if>" id="praiseA"> <span
			class="mui-tab-label">赞</span>
		</a> <a class="mui-tab-item viewToolbarA" href="javascript:praise('${post.id}')"
			style="height: 30px;	 <c:if test="${!isNotPraise}">display: none;</c:if>" id="unPraiseA"> <span
			class="mui-tab-label">取消赞</span>
		</a> <a class="mui-tab-item viewToolbarA" href="javascript:join('${post.id}')"
			style="height: 30px; <c:if test="${!isJoin}">display: none;</c:if>" id="joinA"> <span
			class="mui-tab-label">报名</span>
		</a> <a class="mui-tab-item viewToolbarA" href="javascript:unJoin('${post.id}')"
			style="height: 30px;display: none;" id="unJoinA"> <span class="mui-tab-label">不参加</span>
		</a> <c:if test="${isDelete}">
			<a class="mui-tab-item viewToolbarA" href="javascript:deletePost('${post.id}')"
				style="height: 30px;"> <span class="mui-tab-label">删除</span>
			</a>
		</c:if> <!-- <a class="mui-tab-item viewToolbarA" href="javascript:login('${post.id}')" style="height: 30px;">
			<span class="mui-tab-label">登录</span>
		</a>  --></nav>
	</c:if>
	<%@ include file="/weixin/post/commentDiv.jsp"%>
	<div style="z-index: 280;position: fixed;right: 0px;left: 0px;top: 0px;bottom: 0px;display: none;"
		id="cover" onclick="cancelComment()">&nbsp;</div>
	<div class="mui-input-group"
		style="position: fixed;bottom:0px;z-index: 300;width: 100%;display:none;" id="replyForm">
		<textarea rows="5" placeholder="评论内容" style="border-bottom: 1px solid #dddddd;" id="replyText"></textarea>
		<div class="mui-button-row">
			<button class="mui-btn mui-btn-primary" onclick="submitReply();" style="width: 45%">确认</button>
			&nbsp;&nbsp;
			<button class="mui-btn mui-btn-primary" style="width: 45%" onclick="cancelReply()">取消</button>
		</div>
		<input type="hidden" id="replyPostId" />
	</div>


	<div id="mcover" style="display: none;" onclick="weChat()">
		<img src="${path}/weixin/common/images/share_div.png" />
	</div>
</body>
<script type="text/javascript" src="${path }/weixin/common/js/zepto.min.js"></script>
<script type="text/javascript" src="${path }/weixin/common/js/swipeButton-app.js"></script>
<script type="text/javascript">
	wx.config({
		debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		appId : "${appId}", // 必填，公众号的唯一标识
		timestamp : "${timestamp}", // 必填，生成签名的时间戳
		nonceStr : "${nonceStr}", // 必填，生成签名的随机串
		signature : "${signature}",
		jsApiList : [ 'onMenuShareAppMessage', 'onMenuShareTimeline',
				'previewImage', 'showAllNonBaseMenuItem' ]
	// 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});

	function reply(id, replyPostId) {
		if ("${isLogin}" == "true") {

		} else {
			$("#commentPostId").val(id);
			$("#replyPostId").val(replyPostId);
			$("#cover").css("display", "block");
			$("#replyForm").slideDown("fast");
			$("#replyText").attr("placeholder",
					"回复 " + $("#commentMember_" + replyPostId).text());
		}
	}

	function cancelReply() {
		$("#replyText").val("");
		$("#cover").css("display", "none");
		$("#replyForm").slideUp("fast");
	}
	function submitReply() {
		var id = $("#commentPostId").val();
		var replyPostId = $("#replyPostId").val();
		var commentText = $("#replyText").val();
		if (commentText == "") {
			alert("评论内容不能为空");
		} else {
			$.ajax({
				url : "reply.shtml",// 跳转到 action
				type : 'post',
				cache : false,
				dataType : 'json',
				data : {
					postId : id,
					replyCommentId : replyPostId,
					content : commentText,
					needResult : false
				},
				success : function(data) {
					if (data.type == "success") {
						var o = eval('(' + data.content + ')');
						$("#comment_" + o.postId).text(o.count);
						cancelReply();
						initComments();
					} else {
						alert(data.content);
					}
				},
				error : function() {

				}
			});
		}
	}

	function zf() {
		$("#mcover").css("display", "block");
	}

	function weChat() {
		$("#mcover").css("display", "none");
	}

	function login(id) {
		window.location.href = "${redirectUrl}";

		//window.location.href = "${shareUrl}";
	}

	/*
	wx.checkJsApi({
	jsApiList: ['onMenuShareAppMessage'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
	success: function(res) {
	 	alert(res);
	}
	});
	 */

	var hasComment = false;
	var hasPraise = false;
	wx.ready(function() {
		wx.showAllNonBaseMenuItem();
		wx.onMenuShareAppMessage({
			title : '天地汇', // 分享标题
			desc : '${post.title}', // 分享描述
			link : '${shareUrl}', // 分享链接
			imgUrl : '', // 分享图标
			type : '', // 分享类型,music、video或link，不填默认为link
			dataUrl : '', // 如果type是music或video，则要提供数据链接，默认为空
			success : function() {
			},
			cancel : function() {
				// 用户取消分享后执行的回调函数
			}
		});

		wx.onMenuShareTimeline({
			title : '天地汇', // 分享标题
			link : '${shareUrl}', // 分享链接
			imgUrl : '', // 分享图标
			success : function() {
				// 用户确认分享后执行的回调函数
			},
			cancel : function() {
				// 用户取消分享后执行的回调函数
			}
		});
		// config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
	});

	function deletePostSubmit(id) {
		window.location.href = "${path}/weixin/post/deletePost.shtml?postId="
				+ id;
	}
	function unJoin(id) {
		$.ajax({
			url : "cancelJoin.shtml",// 跳转到 action
			type : 'post',
			cache : false,
			dataType : 'json',
			data : {
				postId : id
			},
			success : function(data) {
				if (data.type == "success") {
					var o = eval('(' + data.content + ')');
					$("#join_" + o.postId).text(o.count);
					initJoins(true);
				} else {
					alert(data.content);
				}
			},
			error : function() {

			}
		});
	}

	function unPraise(id) {
		$.ajax({
			url : "cancelPraise.shtml",// 跳转到 action
			type : 'post',
			cache : false,
			dataType : 'json',
			data : {
				postId : id
			},
			success : function(data) {
				if (data.type == "success") {
					var o = eval('(' + data.content + ')');
					$("#praise_" + o.postId).text(o.count);
					initPraises(true);

				} else {
					alert(data.content);
				}
			},
			error : function() {

			}
		});
	}

	function join(id) {
		$.ajax({
			url : "join.shtml",// 跳转到 action
			type : 'post',
			cache : false,
			dataType : 'json',
			data : {
				postId : id
			},
			success : function(data) {
				if (data.type == "success") {
					var o = eval('(' + data.content + ')');
					$("#join_" + o.postId).text(o.count);
					initJoins(false);
				} else {
					alert(data.content);
				}
			},
			error : function() {

			}
		});
	}

	function initJoins(flag) {
		$.ajax({
			url : "${path}/weixin/post/getJoins.shtml",// 跳转到 action
			type : 'post',
			cache : false,
			dataType : 'json',
			data : {
				postId : "${post.id}"
			},
			success : function(data) {
				$("#join_${post.id}").text(data.length);
				if (data.length > 0) {
					var d = {
						list : data,
						path : "${path}"
					};
					var html = new EJS({
						url : "${path}/weixin/post/join.ejs"
					}).render(d);
					$("#joinContentDiv").html(html);
					initJoinScroll();
					joinScroll.refresh();
					setJoinScroll();

				} else {
					$("#joinContentDiv").html("");
				}
				setJoinButton(flag)
			},
			error : function() {

			}
		});
	}
	function setJoinButton(flag) {
		if (flag == undefined) {

		} else if (flag) {
			$("#joinA").css("display", "");
			$("#unJoinA").css("display", "none");
		} else {
			$("#joinA").css("display", "none");
			$("#unJoinA").css("display", "none");
			//	$("#unJoinA").css("display", "");
		}
	}

	function setPraiseButton(flag) {
		if (flag == undefined) {

		} else if (flag) {
			$("#praiseA").css("display", "table-cell");
			$("#unPraiseA").css("display", "none");
		} else {
			$("#praiseA").css("display", "none");
			$("#unPraiseA").css("display", "table-cell");
		}
	}

	function initComments() {
		if (type == 1) {
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
						hasComment = true;
					} else {
						hasComment = false;
						$("#commentDiv").css("display", "none");
					}
					change();

					var d = {
						list : data,
						path : "${path}"
					};
					var html = new EJS({
						url : "${path}/weixin/post/comment.ejs"
					}).render(d);
					//	alert(html)
					$("#commentUl").html(html);
					for ( var int = 0; int < data.length; int++) {
						var r = data[int];
						if (r.belong) {
							$("#commentLi_" + r.id).swipeButton({
								callback : function(obj) {
									deleteComment(r.id);
								}
							});

						}
					}
					if (mainScroll != null) {
						mainScroll.refresh();
					}

				},
				error : function() {

				}
			});
		}
	}

	function initPraises(flag) {
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
					//alert(html)
					initPraiseScroll();
					hasPraise = true;
				} else {
					$("#praiseContentDiv").html("&nbsp;");
					$("#praiseDiv").css("display", "none");
					hasPraise = false;
				}
				setPraiseButton(flag);
				if (praiseScroll != null) {
					praiseScroll.refresh();
					setPraiseScroll();
				}
				change();
			},
			error : function() {

			}
		});
	}

	function commentPraise(id) {
		if ("${isLogin}" == "true") {
			alert("只有系统用户可以点赞");
		} else {
			$.ajax({
				url : "commentPraise.shtml",// 跳转到 action
				type : 'post',
				cache : false,
				dataType : 'json',
				data : {
					postCommentId : id
				},
				success : function(data) {
					if (data.type == "success") {
						var o = eval('(' + data.content + ')');
						$("#commentPraisePic_" + o.postCommentId).attr("src",
								"../common/images/like" + o.result + ".png");
					} else {
						alert(data.content);
					}
				},
				error : function() {

				}
			});
		}
	}

	var type = 1;

	function change() {
		if (type == 1) {
			$("#praiseDiv").css("display", "none");
			if (hasComment) {
				$("#commentDiv").css("display", "");
			}
			$("#praiseTool").css("color", "gray");
			$("#commentTool").css("color", "#313131");
		} else {
			if (hasPraise) {
				$("#praiseDiv").css("display", "");
			}
			$("#commentDiv").css("display", "none");
			$("#praiseTool").css("color", "#313131");
			$("#commentTool").css("color", "gray");
			praiseScroll.refresh();
			setPraiseScroll();
		}
	}

	function changeComment() {

		type = 1;
		initComments();
		change();
		/*
		$("#praiseDiv").css("display", "none");
		if (hasComment) {
			$("#commentDiv").css("display", "");
		}
		$("#praiseTool").css("color", "gray");
		$("#commentTool").css("color", "#313131");
		 */
	}

	function changePraise() {
		type = 2;
		change();
		/*
		if (hasPraise) {
			$("#praiseDiv").css("display", "");
		}
		$("#commentDiv").css("display", "none");
		$("#praiseTool").css("color", "#313131");
		$("#commentTool").css("color", "gray");
		praiseScroll.refresh();
		setPraiseScroll();
		 */
	}

	function init() {
		initComments();
		initPraises();
		if ("${post.type}" == 2) {
			initJoins();
		}
	}
	function deletePost(id) {
		$.dialog({
			content : "确认删除？",
			title : '删除',
			width : 250,
			okText : '删除',
			cancelText : '取消',
			ok : function() {
				click = false;
				deletePostSubmit(id);
			},
			cancel : function() {
			}
		});

	}

	function deleteComment(id) {

		$.ajax({
			url : "${path}/weixin/post/cancelCommont.shtml",// 跳转到 action
			type : 'post',
			cache : false,
			dataType : 'json',
			data : {
				commentId : id
			},
			success : function(data) {
				if (data.type == "success") {
					initComments();
				} else {
					alert(data.content);
				}

			},
			error : function() {

			}
		});
	}
	init();
</script>

</html>