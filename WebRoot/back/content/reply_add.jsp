<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>新增回复</title>
	<%@ include file="/back/common/metainfo.jsp" %>
	<link rel="stylesheet" href="${path }/common/kindeditor/themes/default/default.css" />
	<script type="text/javascript" src="${path }/common/kindeditor/kindeditor-min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${path }/common/kindeditor/lang/zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript">
		$().ready(function(){
			var $mainBox = $("#mainBox");
			var $mainPanel = $("#mainPanel");
			var $editForm = $("#editForm");
			var $saveButton = $("#saveButton");
			$mainPanel.panel({
				width:$mainBox.width(),
				height:$mainBox.height(),
				tools:[{
					iconCls:'icon-reload',
					handler:function(){window.location.reload();}
				}]
			});
			$(window).resize(function() { 
				var width= $mainBox.width(); 
				var height=  $mainBox.height();
				$mainPanel.panel('resize', { width : width,height:height });
			});
			$("#browserButton").browser({callback:function(data){
				$("#showImgBtn").val(data.content);
			}});
			$editForm.validate({
				rules: {
					"articleTitle": {
						required: true,
						maxlength: 200
					},
					"articleCategoryId": {
						required: true
					}
				}
			});
			$saveButton.click(function(){
				$editForm.submit();
			});
		});
	</script>
</head>
<body>
	<div id="mainBox" class="main-box">
		<div id="mainPanel" class="easyui-panel" iconCls="icon-add" title="&nbsp;&nbsp;新  增  回  复">
			<form id="editForm" action="${path }/back/autoReply/add.shtml" method="post">
		    	<table class="table-edit" width="100%" align="center" cellpadding="0" cellspacing="0">
		      		<tbody>
		      			<tr>
		      				<td align="right">关键字：&nbsp;&nbsp;</td>
		      				<td align="left">
		      					<input type="text" name="keywords" class="text long"/>多个关键字用,隔开
		      				</td>
		      			</tr>
		      			<tr>
		      				<td align="right"><fmt:message key='Common.label.tag'/>：&nbsp;&nbsp;</td>
		      				<td align="left">
		      					<textarea name="content" style="width:90%;height:400px;"></textarea>
		      				</td>
		      			</tr>
		      			<tr>
		      				<td height="60"></td>
		      				<td align="left">
		      					<a href="#" id="saveButton" class="easyui-linkbutton" data-options="iconCls:'icon-save'"><fmt:message key='Common.button.save'/></a>
		      					&nbsp;&nbsp;<a href="#" button="back" class="easyui-linkbutton" data-options="iconCls:'icon-back'"><fmt:message key='Common.button.return'/></a>
		      				</td>
		      			</tr>
		      		</tbody>
		    	</table>
		    </form>
		</div>
	</div>
</body>
<script type="text/javascript">
	KindEditor.ready(function(K) {
		var editor1 = K.create('textarea[name="content"]', {
			uploadJson : '${path }/back/file/upload.shtml',
			fileManagerJson : '${path }/back/file/browser.shtml',
			allowFileManager : true,
			afterCreate : function() {},
			afterBlur: function(){this.sync();}
		});
	});
</script>
</html>