<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>天地汇</title>
<%@ include file="/weixin/post/metainfo.jsp"%>

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
</style>



</head>
<body>
	<form action="${path}/weixin/post/commentSuccess.shtml" id="form" method="post">
		<div>
			<textarea rows="10" placeholder="评论内容，长度在100字之内" style="margin: 0px;border: 0px;" id="content"
				name="content"></textarea>
		</div>
		<input type="hidden" name="postId" value="${postId}"/>
	</form>
	<div class="okbtn">
		<button class="mui-btn mui-btn-positive" style="width: 100%;height: 44px;" onclick="submitForm()">评论</button>
	</div>


</body>
<script type="text/javascript">
	function submitForm() {
		var content = $("#content").val();
		 if (content == "") {
			alert("请输入评论内容");
		} else if (content.length>100) {
			alert("评论内容，长度在100字之内,目前" + content.length + "字");
		} else {
			$("#form").submit();
		}
	}
</script>

</html>