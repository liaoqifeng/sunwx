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

.okbtn {
	margin: 10px;
	margin-top: 20px;
}

#imageDiv {
	background-color: #fff;
	border-bottom: 1px solid #dddddd;
}

#selectDiv {
	width: 120px;
	float: right;
	height: 40px;
	margin: 10px 0;
	border: 1px solid #dddddd;
	border-radius: 5px;
	font-size: 12px;
}

.msSelect {
	height: 30px;
	background: rgba(0, 0, 0, 0);
	border: 1px solid #dddddd;
	vertical-align: middle;
	padding: 0px 10px;
	text-align: center;
}

input[type="text"] {
	height: 40px;
}

#title {
	border: 0px;
	border-bottom: 1px solid #dddddd;
	height: 40px;
}

.postPic {
	width: 80px;
	height: 80px;
	padding-left: 10px;
	padding-right: 0px;
	padding-top: 5px;
	padding-bottom: 5px;
}
</style>



</head>
<body>
	<form action="${path}/weixin/post/addSuccess.shtml" id="form" method="post">
		<div>
			<input type="text" placeholder="帖子标题" style="margin: 0px;" name="title" id="title">
		</div>
		<div>
			<textarea rows="10" placeholder="帖子正文" style="margin: 0px;border: 0px;" id="content"
				name="content"></textarea>
		</div>
		<div id="picDiv" style="background-color: #fff;"></div>
		<div class="mui-bar mui-bar-nav toolbar" style="height: 60px;">
			<a class="mui-icon mui-pull-left"><img src="${path }/weixin/common/images/selectImage.png"
				onclick="openImg()" /></a>
			<div id="selectDiv">
				<select id="sector" name="sector" style="height: 50px;">
					<c:forEach var="row" items="${postSectors}" varStatus="i2">
						<option value="${row.id}">${row.name}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<input type="hidden" name="pics" id="pics" />
	</form>
	<div class="okbtn">
		<button class="mui-btn mui-btn-positive" style="width: 100%;height: 44px;font-size: 18px;"
			onclick="submitForm()">发 布</button>
	</div>


</body>
<script type="text/javascript">
	wx.config({
		debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		appId : "${appId}", // 必填，公众号的唯一标识
		timestamp : "${timestamp}", // 必填，生成签名的时间戳
		nonceStr : "${nonceStr}", // 必填，生成签名的随机串
		signature : "${signature}",
		jsApiList : [ 'chooseImage', 'uploadImage', 'hideOptionMenu' ]
	// 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
	/*
	wx.ready(function() {
		wx.checkJsApi({
		    jsApiList: ['chooseImage', 'uploadImage','hideOptionMenu'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
		    success: function(res) {
		    	alert(res)
		    }
		});
		wx.hideOptionMenu();
		// config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
	});
	 */
	var images = {
		localId : [],
		serverId : [],
		tmp : []
	};
	function openImg() {
		wx.chooseImage({
			success : function(res) {
				images.tmp = res.localIds;
				for ( var int = 0; int < images.tmp.length; int++) {

				}
				//alert('已选择 ' + res.localIds.length + ' 张图片');
				uploadImg();
			}
		});
	};

	function addImg(i) {
		var d = $("<div>", {
			"class" : "picContainer",
			"code" : images.tmp[i],
			"id" : "picDiv_" + i
		}).appendTo("#picDiv");
		var d1 = $("<div>", {
			"class" : "picDummy"
		}).appendTo(d);
		var d2 = $("<div>", {
			"class" : "picContent"
		}).appendTo(d);

		var img = $("<img>", {
			"src" : images.tmp[i],
			"class" : "pic",
		}).appendTo(d2);

		//img.attr("code", images.tmp[i]);

		d.click(function() {
			for ( var int = 0; int < images.serverId.length; int++) {
				var id = images.localId[int];
				if (id == $(this).attr("code")) {
					images.serverId.splice(int, 1);
					break;
				}
			}
			$(this).remove();
		});

		//$("#picDiv").append(img);
	}

	function addImg1(i) {
		var img = $("<img></img>").attr("src", images.tmp[i]);
		img.addClass("postPic");
		img.attr("code", images.tmp[i]);

		img.click(function() {
			for ( var int = 0; int < images.serverId.length; int++) {
				var id = images.localId[int];
				if (id == $(this).attr("code")) {
					images.serverId.splice(int, 1);
					break;
				}
			}
			$(this).remove();
		});

		$("#picDiv").append(img);
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

	$('#sector').mobiscroll().select(msSetting.select);

	var flag = true;
	function submitForm() {
		if (flag) {
			var title = $("#title").val();
			var sector = $("#sector  option:selected").attr("value");
			var content = $("#content").val();
			if (title == "") {
				alert("请输入帖子标题");
			} else if (content == "" && images.serverId.length == 0) {
				alert("请输入帖子正文或上传图片");
			} else if (sector == -1) {
				alert("请选择发布类型");
			} else {
				if (images.serverId.length > 0) {
					var s = images.serverId.join(';');

					$("#pics").val(s);
				}
				$("#form").submit();
				flag = false;
			}
		}
	}
</script>

</html>