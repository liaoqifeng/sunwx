<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>天地汇</title>
<%@ include file="/weixin/post/metainfo.jsp"%>
<script type="text/javascript" src="../common/js/jq.mobi.min.js"></script>
<script src="../common/js/jquery.mobile-1.2.0.min.js"></script>
<script src="../common/js/mobiscroll.jqmobi.js" type="text/javascript"></script>
<script src="../common/js/mobiscroll.core.js" type="text/javascript"></script>
<script src="../common/js/mobiscroll.datetime.js" type="text/javascript"></script>
<script src="../common/js/mobiscroll.android-ics.js" type="text/javascript"></script>
<script src="../common/js/i18n/mobiscroll.i18n.zh.js" type="text/javascript"></script>
<script src="../common/js/mobiscroll.select.js" type="text/javascript"></script>
<script src="../common/js/mobiscrollSetting.js" type="text/javascript"></script>
<link href="../common/css/mobiscroll.core.css" rel="stylesheet" type="text/css" />
<link href="../common/css/mobiscroll.android-ics.css" rel="stylesheet" type="text/css" />
<link href="../common/css/mobiscroll.animation.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<style type="text/css">
input[type="text"] {
	border: 0px;
	border-bottom: 1px solid #dddddd;
	margin: 0px;
}

.toolbar {
	position: relative;
	display: block;
	height: 40px;
	line-height: 40px;
	vertical-align: middle;
}

.postPic {
	width: 100px;
	height: 100px;
	border: 1px solid #ccc;
	margin-right: 5px;
	margin-bottom: 5px;
}

.okbtn {
	margin: 10px;
	margin-top: 20px;
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

.ul {
	margin: 0;
	padding: 0;
	list-style: none;
	z-index: 200;
	width: 100%;
	height: 80px;
}

.li1 {
	float: left;
	width: 80px;
	background-color: #fff;
	height: 80px;
	border-bottom: 1px solid #dddddd;
	border-right: 1px solid #dddddd;
	padding: 5px;
}

.li2 {
	margin-left: 80px;
}

.pic {
	width: 70px;
	height: 70px;
}
</style>



</head>
<body>
	<form action="${path}/weixin/post/addActivitySuccess.shtml" id="form" method="post">
		<div>
			<input type="text" placeholder="活动名称" name="title" id="title">
		</div>
		<div>
			<ul class="ul">
				<li class="li1" id="picDiv" onclick="openImg()"><img
					src="${path }/weixin/common/images/selectImage.png" style="margin: 14px;" /></li>
				<li class="li2"><input type="text" placeholder="开始时间" name="beginTime" id="beginDate">
						<input type="text" placeholder="结束时间" name="endTime" id="endDate"></li>
			</ul>
		</div>
		<div>
			<input type="text" placeholder="活动地点" name="area" id="area">
		</div>
		<div>
			<textarea rows="5" placeholder="活动内容" style="margin: 0px;border: 0px;" id="content"
				name="content"></textarea>
		</div>
		<div id="imageDiv"></div>
		<!-- 
		<div class="mui-bar mui-bar-nav toolbar" style="height: 60px;">
			<a class="mui-icon mui-pull-left"><img src="${path }/weixin/common/images/selectImage.png"
				onclick="openImg()" /></a> 
		</div>-->
		<input type="hidden" name="pics" id="pics" />
	</form>
	<div class="okbtn">
		<button class="mui-btn mui-btn-positive" style="width: 100%;height: 44px;font-size: 18px;"
			onclick="submitForm()">发 布</button>
	</div>


</body>
<script type="text/javascript">
	$('#beginDate').mobiscroll().datetime(msSetting.datetime);
	$('#endDate').mobiscroll().datetime(msSetting.datetime);

	wx.config({
		debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		appId : "${appId}", // 必填，公众号的唯一标识
		timestamp : "${timestamp}", // 必填，生成签名的时间戳
		nonceStr : "${nonceStr}", // 必填，生成签名的随机串
		signature : "${signature}",
		jsApiList : [ 'chooseImage', 'uploadImage', 'hideOptionMenu' ]
	// 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});

	wx.ready(function() {
		wx.hideOptionMenu();
		// config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
	});

	var images = {
		localId : [],
		serverId : [],
		tmp : []
	};
	function openImg() {
		wx.chooseImage({
			success : function(res) {
				images.tmp = res.localIds;
				if (res.localIds.length != 1) {
					alert("只能选择一张图片");
				} else {
					for ( var int = 0; int < images.tmp.length; int++) {

					}
					images.localId.length = 0;
					images.serverId.length = 0;
					//alert('已选择 ' + res.localIds.length + ' 张图片');
					uploadImg();
				}
			}
		});
	};

	function addImg(i) {
		var img = $("<img></img>").attr("src", images.tmp[i]);
		img.addClass("pic");
		img.attr("code", images.tmp[i]);

		$("#picDiv").html(img);
	}

	function uploadImg() {
		if (images.tmp.length == 0) {
			//alert('请先使用 chooseImage 接口选择图片');
			return;
		}
		isUploading = true;
		var i = 0, length = images.tmp.length;
		//images.serverId = [];
		function upload() {
			wx.uploadImage({
				localId : images.tmp[i],
				success : function(res) {
					addImg(i);
					images.localId.push(images.tmp[i]);
					i++;
					//	alert('已上传：' + i + '/' + length + "  " + res.serverId);
					images.serverId.push(res.serverId);
					if (i < length) {
						setTimeout(function() {
							upload();
						}, 100);
					} else {
						for ( var int = 0; int < images.tmp.length; int++) {

						}
						isUploading = false;
					}
				},
				fail : function(res) {
					alert("上传失败，请稍后重试");
					isUploading = false;
				}
			});
		}
		upload();
	};

	var flag = true;

	function submitForm() {
		if (flag) {
			var title = $("#title").val();
			var beginDate = $("#beginDate").val();
			var endDate = $("#endDate").val();
			var area = $("#area").val();
			if (title == "") {
				alert("请输入活动名称");
			} else if (beginDate == "") {
				alert("请输入开始时间");
			} else {
				if (endDate != "") {
					beginDate = beginDate.trim().replace(/-/g, "/") + ":00";
					endDate = endDate.trim().replace(/-/g, "/") + ":00";
					var bd = new Date(beginDate);
					var ed = new Date(endDate);
					if (ed.getTime() <= bd.getTime()) {
						alert("结束时间必须大于开始时间");
						return;
					}
				}
				if (area == "") {
					alert("请输入活动地点");
				}
				//else if (content.length<50||content.length>100) {
				//	alert("活动内容，长度在50-100字之间,目前" + content.length + "字");
				//} 
				else {
					if (images.serverId.length > 0) {
						var s = images.serverId.join(';');

						$("#pics").val(s);
					}
					$("#form").submit();
					flag = false;
				}
			}
		}
	}
</script>

</html>