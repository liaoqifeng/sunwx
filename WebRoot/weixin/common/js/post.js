function comment(id) {
	$("#commentPostId").val(id);
	$("#cover").css("display", "block");
	$("#commentForm").slideDown("fast");
}
function cancelComment() {
	$("#commentText").val("");
	$("#cover").css("display", "none");
	$("#commentForm").slideUp("fast");
}
function submitComment() {

	var id = $("#commentPostId").val();
	var commentText = $("#commentText").val();
	if (commentText == "") {
		alert("评论内容不能为空");
	} else {
		$.ajax({
			url : "comment.shtml",// 跳转到 action
			type : 'post',
			cache : false,
			dataType : 'json',
			data : {
				postId : id,
				content : commentText,
				needResult : false
			},
			success : function(data) {
				if (data.type == "success") {
					var o = eval('(' + data.content + ')');
					$("#comment_" + o.postId).text(o.count);
					cancelComment();
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
function praise(id) {
	$.ajax({
		url : "praise.shtml",// 跳转到 action
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
				initPraises(!o.result , o.postId ) ;
			} else {
				alert(data.content);
			}
		},
		error : function() {
			
		}
	});

}

function prePic(path, paths) {
	var ps = paths.split(",");

	wx.previewImage({
		current :  path, // 当前显示图片的http链接
		urls : ps
	// 需要预览的图片http链接列表
	});
	
	event.stopPropagation();
}

function selectMember(id) {
	location.href = "main.shtml?type=2&memberId=" + id;
	event.stopPropagation();
}