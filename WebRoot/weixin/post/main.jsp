<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>天地汇</title>
<%@ include file="/weixin/post/metainfo.jsp"%>
<link rel="stylesheet" type="text/css" href="${path }/weixin/common/css/navigation.css" />
<link rel="stylesheet" type="text/css" href="${path }/weixin/common/css/iscroll.css" />



<script type="text/javascript" src="${path }/weixin/common/js/ejs.js"></script>
<script type="text/javascript" src="${path }/weixin/common/js/iscroll.js"></script>
<script type="text/javascript" src="${path }/weixin/common/js/post.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		// hover property will help us set the events for mouse enter and mouse leave
		$('.navigation').children().click(nav);
		$('#nav4_masklayer').click(nav);

		function nav() {
			var th = this;
			var mask = $("#nav4_masklayer");
			var obj = mask[0];
			$('.navigation').children().each(function() {
				if (th != this) {
					$('ul', this).fadeOut();
				}
			});

			//not(this).fadeOut();

			if (this == obj) {
				mask.removeClass("on");
				return;
			}
			//Fade in the navigation submenu
			$('ul', this).fadeToggle(); // fadeIn will show the sub cat menu
			mask.toggleClass("on");
		}
	});
</script>
<style type="text/css">
.toolbar {
	position: relative;
	padding: 5px 0px;
	height: 30px;
	-webkit-box-shadow: 0 0 0px rgba(0, 0, 0, 1);
	box-shadow: 0 0 0px rgba(0, 0, 0, 1);
	border-bottom: 1px solid #ccc;
	border-top: 1px solid #ccc;
}

.postPic {
	float: left;
	width: 80%;
	border: 1px solid #ccc;
	margin-right: 5px;
	margin-bottom: 5px;
	height: 80px;
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

.activityContentDiv {
	
}

.activityImgDiv {
	float: right;
	width: 120px;
	height: 120px;
	padding: 10px;
}

.activityImage {
	width: 90px;
	float: right;
	margin: 5px;
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

.memberName {
	color: #007aff;
	font-size: 20px;
	line-height: 25px;
}

.memberScore {
	color: #313131;
	font-size: 14px;
	line-height: 20px;
}

.memberFaction {
	color: #313131;
	font-size: 14px;
	line-height: 20px;
}

.sector {
	background-color: red;
	color: #fff;
	font-size: 10px;
	padding: 0px 5px;
}

.pmTitle {
	color: #313131;
	font-size: 20px;
	text-align: right;
	line-height: 35px;
}

.pmScore {
	color: red;
	font-size: 16px;
	text-align: right;
	line-height: 28px;
}

.mui-table-view {
	background-color: #efeff4;
}

.mui-table-view-cell {
	background-color: #fff;
}

.mui-table-view-cell::after {
	left: 0;
}

.tdhdPm {
	margin-right: 10%;
	width: 33%;
}

.tdhdHead {
	width: 55%;
	border-radius: 5px;
}

.tdhdIndex {
	font-size: 14px;
	color: #313131;
}

.tdhdTitle {
	font-size: 18px;
	color: #cdbe1f;
	padding: 2px 0;
}

.tdhdContent {
	font-size: 14px;
	color: #313131;
}
</style>



</head>
<body>
	<div id="nav4_masklayer" class="masklayer_div">&nbsp;</div>
	<div class="nagidiv">
		<ul class="navigation">
			<li><a href="#" style="border-left: 0px;"><c:out value="${factions[selectFactionId]}" /><p  class="mui-icon mui-icon-arrowdown" style="font-size: 18px;"></p></a>
				<ul>
					<c:forEach var="row" items="${factions}" varStatus="i2">
						<li><a href="javascript:selectFaction('${row.key}')"><span>${row.value}</span></a></li>
					</c:forEach>

				</ul>
				<div class="clear"></div></li>
			<li><a href="#" ><c:out value="${postSectors[selectPostSectorId]}" /><p  class="mui-icon mui-icon-arrowdown" style="font-size: 18px;"></p> </a>
				<ul>
					<c:forEach var="row" items="${postSectors}" varStatus="i2">
						<li><a href="javascript:selectPostSector('${row.key}')"><span>${row.value}</span></a></li>
					</c:forEach>
				</ul>
				<div class="clear"></div></li>
			<li><a href="#" >发布<p  class="mui-icon mui-icon-arrowdown" style="font-size: 18px;"></p></a>
				<ul>
					<li><a href='javascript:window.location.href="${path }/weixin/post/main.shtml?type=2"'><c:if
								test="${badgeCount>0}">
								<span style="color: red;">●</span>
							</c:if><span>我的天地汇</span></a></li>
					<li><a href='javascript:window.location.href="${path }/weixin/post/add.shtml"'><img
							src="../common/images/tiezi.png" alt=""
							style="width: 20px;height: 20px;vertical-align: middle;margin-bottom: 3px;" />&nbsp;<span>发布帖子</span></a></li>
					<li><a href='javascript:window.location.href="${path }/weixin/post/addActivity.shtml"'><img
							src="../common/images/huodong.png" alt=""
							style="width: 20px;height: 20px;vertical-align: middle;margin-bottom: 3px;" />&nbsp;<span
							style="">发布活动</span></a></li>
				</ul>
				<div class="clear"></div></li>
		</ul>

		<div class="clear" style="clear: both;"></div>

	</div>
	<div id="wrapper" style="margin-top: 10px;">
		<div id="scroller">
			<div id="pullDown">
				<span class="pullDownIcon"></span><span class="pullDownLabel">下拉刷新...</span>
			</div>
			<div id="content">
				<ul class="mui-table-view" id="memberDiv" style="">
					<li class="mui-table-view-cell mui-hidden">
						<div id="M_Toggle" class="mui-switch mui-active">
							<div class="mui-switch-handle"></div>
						</div>
					</li>
					<c:if test="${type == 2 }">
						<li class="mui-table-view-cell" style="margin-top: 15px;"><img
							style="height: 100px;width:100px;border-radius: 5px;" class="mui-pull-left"
							src="${member.profileImage }"></img>
							<div style="margin-left: 110px;">
								<p class="memberName">${member.realname}</p>
								<p class="memberScore">积分：${member.score}</p>
								<p class="memberScore">职位：${member.job.name}</p>
								<p class="memberFaction">
									<a class="memberFaction"
										href="${path }/weixin/post/faction.shtml?factionId=${member.faction.id}">帮派：${member.faction.name}</a>
								</p>
							</div></li>
					</c:if>


					<c:if test="${!empty  scoreList}">
						<li class="mui-table-view-cell" style="padding:11px 0px;margin-top: 15px;" id="tdhdDiv">
							<div style="padding: 0 15px 11px 15px;">
								<p class="tdhdIndex">${postNotice.number}</p>
								<p class="tdhdTitle" onclick="goNotice('${postNotice.id}')">${postNotice.title}</p>
								<p class="tdhdContent">${postNotice.content}</p>
							</div>

							<ul style="list-style: none;width: 100%;padding: 0px;">
								<li
									style="width: 33.33%;float: left;border-right: 1px solid #dddddd;padding: 0px 15px 0px 15px;"><c:if
										test="${scoreList.size()>0 }">
										<div>
											<img src="../common/images/no1.png" class="tdhdPm" /><img src="${scoreList[0][3] }"
												class="tdhdHead" onclick="goFaction('${scoreList[0][1] }')" />
										</div>
										<div>
											<p class="pmTitle">${scoreList[0][2] }</p>
											<p class="pmScore">
												<fmt:formatNumber type="number" pattern="###,###,###,###" value="${scoreList[0][0]}" />
												<img src="../common/images/coin.png" />
											</p>
										</div>
									</c:if></li>
								<li
									style="width: 33.33%;float: left;border-right: 1px solid #dddddd;padding: 0px 15px 0px 15px;"><c:if
										test="${scoreList.size()>1 }">
										<div>
											<img src="../common/images/no2.png" class="tdhdPm" /><img src="${scoreList[1][3] }"
												class="tdhdHead" onclick="goFaction('${scoreList[1][1] }')" />
										</div>
										<div>
											<p class="pmTitle">${scoreList[1][2] }</p>
											<p class="pmScore">
												<fmt:formatNumber type="number" pattern="###,###,###,###" value="${scoreList[1][0]}" />
												<img src="../common/images/coin.png" />
											</p>
										</div>
									</c:if></li>
								<li style="width: 33.33%;float: left;padding: 0px 15px 0px 15px;"><c:if
										test="${scoreList.size()>2 }">
										<div>
											<img src="../common/images/no3.png" class="tdhdPm" /><img src="${scoreList[2][3] }"
												class="tdhdHead" onclick="goFaction('${scoreList[2][1] }')" />
										</div>
										<div>
											<p class="pmTitle">${scoreList[2][2] }</p>
											<p class="pmScore">
												<fmt:formatNumber type="number" pattern="###,###,###,###" value="${scoreList[2][0]}" />
												<img src="../common/images/coin.png" />
											</p>
										</div>
									</c:if></li>
							</ul>

						</li>
					</c:if>


					<c:forEach var="post" items="${posts}" varStatus="i">
						<li class="mui-table-view-cell mui-media" style="margin-top: 15px;padding: 0px;"><div
								style="padding: 10px;">
								<img class="mui-media-object mui-pull-left" src="${post.member.profileImage }"
									style="border-radius: 5px;" onclick="selectMember('${post.member.id}')"></img>
								<div style="margin-left: 50px;" onclick="select('${post.id}')">
									<span class="postMemberName">${post.member.realname}</span>&nbsp;
									<c:if test="${post.badge>0}">
										<span style="color: red;">●</span>
									</c:if>
									<span class="sector" style="float: right;"> <c:if test="${post.type  =='1'}">
										${post.postSectorName}
									</c:if> <c:if test="${post.type  !='1'}">
										活动
									</c:if>
									</span>
									<p class="postDate">${post.showDate}</p>
									<c:if test="${post.type  =='1'}">
										<p class="activityTitle" >${post.title}</p>
										<p class="postContent">${post.content}</p>
										<c:forEach var="image" items="${post.postImages}" varStatus="i2">
											<div class='picContainer'>
												<div class='picDummy'></div>
												<div class='picContent'>
													<img class="pic" src="${image.name }"
														onclick="prePic('${image.name }' , '${post.postImagesStr }')"></img>
												</div>
											</div>
											<!--  	<img class="postPic" src="${path }${image.name }"
												onclick="prePic('${path }${image.name }' , '${post.postImagesStr }')"></img>-->
										</c:forEach>
										<div style="clear: both;"></div>

									</c:if>
									<c:if test="${post.type  =='2'}">
										<c:if test="${post.postImages.size()>0}">
											<img src="${post.postImages[0].name }" class="activityImage"
												onclick="prePic('${post.postImages[0].name }' , '${post.postImagesStr }')" />
										</c:if>
										<div class="activityContentDiv">
											<p class="activityTitle">${post.title }</p>
											<p class="activityContent">
												<img src="../common/images/pin.png" style="vertical-align: middle;" />&nbsp;<span
													style="vertical-align: middle;">${post.area}</span>
											</p>
											<p class="activityContent">
												<img src="../common/images/clock.png" style="vertical-align: middle;" />&nbsp;<span
													style="vertical-align: middle;">${post.beginDateStr}</span>
											</p>

											<p class="activityContent">
												<img src="../common/images/info.png" style="vertical-align: middle;" />&nbsp;<span
													style="vertical-align: middle;"><c:if test="${post.content ==null}">无活动说明</c:if>
													<c:if test="${post.content !=null}">${post.content }</c:if></span>
											</p>
										</div>

									</c:if>
								</div>
							</div> <nav class="mui-bar  mui-bar-tab toolbar" style="margin-top:0px;"> <a
								class="mui-tab-item" href="javascript:comment('${post.id }')"
								style="border-right: 1px solid #ccc;height: 22px;line-height: 22px;vertical-align: middle;padding: 0px;margin: 0px;color: #a0a0a0;"><img
								style="vertical-align: middle;" src="../common/images/pinlun.png" />&nbsp;<span
								style="vertical-align: middle;">评论</span>&nbsp;<span id="comment_${post.id }"
								style="vertical-align: middle;color: #a0a0a0;">${post.postCommentCount}</span></a> <a
								class="mui-tab-item" style="height: 22px" href="javascript:praise('${post.id }')"><img
								style="vertical-align: middle;" src="../common/images/like${post.praised }.png"
								id="praisePic_${post.id }" />&nbsp;<span id="praise_${post.id }"
								style="vertical-align: middle;color: #a0a0a0;">${post.postPraiseCount}</span></a></nav></li>
					</c:forEach>
				</ul>

			</div>

			<div id="pullUp">
				<span class="pullUpIcon"></span><span class="pullUpLabel">上拉加载更多...</span>
			</div>
		</div>
	</div>
	<input type="hidden" id="sectorId" value="${selectPostSectorId}" />
	<input type="hidden" id="factionId" value="${selectFactionId}" />
	<input type="hidden" id="type" value="${type}" />
	<input type="hidden" id="memberId" value="${memberId}" />
	<input type="hidden" id="minRow" value="${minRow}" />
	<%@ include file="/weixin/post/commentDiv.jsp"%>


	<!--  
	<table>
		<c:forEach var="row" items="${posts}" varStatus="i">
			<tr class="item">
				<td align="left">${post.title }</td>
				<td align="left">${post.content }</td>
				 
			
			</tr>
		</c:forEach>
	</table>
	-->
</body>
<script type="text/javascript">
	wx.config({
		debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		appId : "${appId}", // 必填，公众号的唯一标识
		timestamp : "${timestamp}", // 必填，生成签名的时间戳
		nonceStr : "${nonceStr}", // 必填，生成签名的随机串
		signature : "${signature}",
		jsApiList : [ 'hideOptionMenu' ]
	// 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
	/*
	wx.checkJsApi({
	    jsApiList: ['hideOptionMenu'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
	    success: function(res) {
	    }
	});
	 */
	wx.ready(function() {
		wx.hideOptionMenu();
		// config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
	});

	var minRow = $("#minRow").val();
	
	function goNotice(id){
		window.location.href = "${path }/weixin/post/notice.shtml?id=" + id;
	}

	function close() {
		//	alert(${badgeCount})
		if ("${removeBadge}" == "true") {
			$.ajax({
				url : "${path}/weixin/post/removeBadge.shtml",// 跳转到 action    
				type : 'post',
				cache : false,
				dataType : 'json',

				success : function(data) {

				},
				error : function() {
					// view("异常！");  

				}
			});
		}
	};

	//document.body.onbeforeunload = close;

	function select(id) {
		window.location.href = "${path }/weixin/post/view.shtml?postId=" + id;
	}

	function initComments() {
	}
	function initPraises(flag, id) {
		$("#praisePic_" + id).attr("src",
				"../common/images/like" + !flag + ".png");
	}

	function selectFaction(id) {
		var sid = $("#sectorId").val();
		window.location.href = "${path}/weixin/post/main.shtml?sectorId=" + sid
				+ "&factionId=" + id;
	}

	function selectPostSector(id) {
		var sid = $("#factionId").val();
		window.location.href = "${path}/weixin/post/main.shtml?sectorId=" + id
				+ "&factionId=" + sid;
	}

	var myScroll, pullDownEl, pullDownOffset, pullUpEl, pullUpOffset, generatedCount = 0;

	/**
	 * 下拉刷新 （自定义实现此方法）
	 * myScroll.refresh();		// 数据加载完成后，调用界面更新方法
	 */
	function pullDownAction() {
		window.location.reload();
	}

	/**
	 * 滚动翻页 （自定义实现此方法）
	 * myScroll.refresh();		// 数据加载完成后，调用界面更新方法
	 */
	function pullUpAction() {
		Ajax(minRow);
	}

	/**
	 * 初始化iScroll控件
	 */
	function loaded() {

		pullDownEl = document.getElementById('pullDown');
		pullDownOffset = pullDownEl.offsetHeight;
		pullUpEl = document.getElementById('pullUp');
		pullUpOffset = pullUpEl.offsetHeight;
		$("#content").css("min-height", ($(window).height() - 40) + "px");

		myScroll = new iScroll(
				'wrapper',
				{
					scrollbarClass : 'myScrollbar', /* 重要样式 */
					useTransition : false, /* 此属性不知用意，本人从true改为false */
					topOffset : pullDownOffset,
					onRefresh : function() {
						if (pullDownEl.className.match('loading')) {
							pullDownEl.className = '';
							pullDownEl.querySelector('.pullDownLabel').innerHTML = '下拉刷新...';
						} else if (pullUpEl.className.match('loading')) {
							pullUpEl.className = '';
							pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉加载更多...';
						}
					},
					onScrollMove : function() {
						if (this.y > 5 && !pullDownEl.className.match('flip')) {
							pullDownEl.className = 'flip';
							pullDownEl.querySelector('.pullDownLabel').innerHTML = '松手开始更新...';
							this.minScrollY = 0;
						} else if (this.y < 5
								&& pullDownEl.className.match('flip')) {
							pullDownEl.className = '';
							pullDownEl.querySelector('.pullDownLabel').innerHTML = '下拉刷新...';
							this.minScrollY = -pullDownOffset;
						} else if (this.y < (this.maxScrollY - 5)
								&& !pullUpEl.className.match('flip')) {
							pullUpEl.className = 'flip';
							pullUpEl.querySelector('.pullUpLabel').innerHTML = '松手开始更新...';
							this.maxScrollY = this.maxScrollY;
						} else if (this.y > (this.maxScrollY + 5)
								&& pullUpEl.className.match('flip')) {
							pullUpEl.className = '';
							pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉加载更多...';
							this.maxScrollY = pullUpOffset;
						}
					},
					onScrollEnd : function() {
						var leng2 = $("#pullUp").position().top
								- document.getElementById("wrapper").clientHeight;
						//alert(myScroll.maxScrollY + "  " + leng2);
						if (myScroll.maxScrollY > -(leng2 + 50)) {
							myScroll.maxScrollY = -(leng2 + 50);
						}
						if (pullDownEl.className.match('flip')) {
							pullDownEl.className = 'loading';
							pullDownEl.querySelector('.pullDownLabel').innerHTML = '加载中...';
							pullDownAction(); // Execute custom function (ajax call?)
						} else if (pullUpEl.className.match('flip')) {
							pullUpEl.className = 'loading';
							pullUpEl.querySelector('.pullUpLabel').innerHTML = '加载中...';
							pullUpAction(); // Execute custom function (ajax call?)
						} else if (!pullUpEl.className.match('flip')) {
							if (this.maxScrollY < 0
									&& this.y < this.maxScrollY + 50) {
								//this.scrollTo(this.x, this.maxScrollY + 50,500);
								hiddenBar();
							}
						}

					}
				});

		//alert(leng);

		/*
		alert(($("#memberDiv").children().first().next()
				.position().top +"    " +$("#memberDiv").children().first().next()
				.outerHeight(true))
				+ "   "
				+ myScroll.maxScrollY
				+ "   "
				+ document.getElementById("wrapper").clientHeight+"    "+($(window).height() - 40));
		 */

		setTimeout(function() {
			document.getElementById('wrapper').style.left = '0';

		}, 100);
	}

	function goFaction(id) {
		window.location.href = " ${path }/weixin/post/faction.shtml?factionId="
				+ id;
		event.stopPropagation();
	}

	function hiddenBar() {
		//	var last = $("#memberDiv").children().last();
		//	var leng = last.position().top + last.outerHeight(true) + 15 + 50
		//			- document.getElementById("wrapper").clientHeight;

		var leng2 = $("#pullUp").position().top
				- document.getElementById("wrapper").clientHeight;
		//alert(myScroll.maxScrollY + "  " + leng2);
		if (myScroll.maxScrollY > -(leng2 + 50)) {
			myScroll.maxScrollY = -(leng2 + 50);
		}
		if (leng2 > 0) {
			myScroll.scrollTo(myScroll.x, -leng2, 500);
		}

	}

	function Ajax(p) { // ajax后台交互
		var sid = $("#sectorId").val();
		var fid = $("#factionId").val();
		var type = $("#type").val();
		var mid = $("#memberId").val();
		$.ajax({
			url : "${path}/weixin/post/getData.shtml",// 跳转到 action    
			type : 'post',
			cache : false,
			dataType : 'json',
			data : {
				type : type,
				minRow : p,
				sectorId : sid,
				factionId : fid,
				memberId : mid
			},
			success : function(data) {
				if (data.list.length) { // 如果后台传过来有数据执行如下操作 ， 没有就执行else 告诉用户没有更多内容呢
					minRow = data.list[data.list.length - 1].id;
					var d = {
						path : "${path}",
						list : data.list
					};
					var html = new EJS({
						url : "${path}/weixin/post/post.ejs"
					}).render(d);
					$("#memberDiv").append(html);
					//minRow = data.l
					myScroll.refresh();
					var leng2 = $("#pullUp").position().top
							- document.getElementById("wrapper").clientHeight;
					//alert(myScroll.maxScrollY + "  " + leng2);
					if (myScroll.maxScrollY > -(leng2 + 50)) {
						myScroll.maxScrollY = -(leng2 + 50);
					}
				} else {
					$('.pullUpLabel').html('亲，没有更多内容了！');
					setTimeout(function() {
						hiddenBar();
					}, 1000)

					//document.getElementById('pullUp').className = 'loading';

				}

			},
			error : function() {
				// view("异常！");  

			}
		});

	}

	//初始化绑定iScroll控件 
	document.addEventListener('touchmove', function(e) {
		e.preventDefault();
	}, false);
	document.addEventListener('DOMContentLoaded', loaded, false);
</script>
</html>