<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>新增帮派</title>
	<%@ include file="/back/common/metainfo.jsp" %>
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
			$editForm.validate({
				rules: {
					"name": {
						required: true,
						maxlength: 255
					},
					"neighborhoodName": {
						required: true,
						maxlength: 255
					},
					"orderList": { integer:true }
				}
			});
			$("#browserButton").browser({callback:function(data){
				$("#image").val(data.content);
			}})
			$saveButton.click(function(){
				$editForm.submit();
			});
		});
	</script>
</head>
<body>
	<div id="mainBox" class="main-box">
		<div id="mainPanel" class="easyui-panel" iconCls="icon-add" title="&nbsp;&nbsp;新  增  帮  派">
			<form id="editForm" action="${path }/back/faction/add.shtml" method="post">
		    	<table class="table-edit" align="center" cellpadding="0" cellspacing="0">
		      		<tbody>
		      			<tr>
		      				<td width="30%" align="right"><font color="red">*&nbsp;&nbsp;</font>帮派名称：&nbsp;&nbsp;</td>
		      				<td width="70%" align="left"><input type="text" name="name" class="text"/></td>
		      			</tr>
		      			<tr>
		      				<td width="30%" align="right"><font color="red">*&nbsp;&nbsp;</font>小区名称：&nbsp;&nbsp;</td>
		      				<td width="70%" align="left"><input type="text" name="neighborhoodName" class="text"/></td>
		      			</tr>
		      			<tr>
		    			<td height="30" align="right">&nbsp;图片：&nbsp;&nbsp;</td>
			    			<td align="left">
			    				<input type="text" id="image" name="imagePath" class="text"/>
			    				<input type="button" id="browserButton" class="button" value="选择文件.." />
			    			</td>
			    		</tr>
		      			<tr>
		      				<td width="30%" align="right">公告：&nbsp;&nbsp;</td>
		      				<td width="70%" align="left">
		      					<textarea rows="8" cols="50" name="notice"></textarea>
		      				</td>
		      			</tr>
		      			<tr>
		      				<td width="30%" align="right">简介：&nbsp;&nbsp;</td>
		      				<td width="70%" align="left"><textarea rows="8" cols="50" name="describe"></textarea></td>
		      			</tr>
		      			<tr>
		      				<td align="right">排序：&nbsp;&nbsp;</td>
		      				<td align="left"><input type="text" name="index" class="text short"/></td>
		      			</tr>
		      			<tr>
		      				<td height="60"></td>
		      				<td align="left">
		      					<a href="#" id="saveButton" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保      存</a>
		      					<a href="#" button="back" class="easyui-linkbutton" data-options="iconCls:'icon-back'">返      回</a>
		      				</td>
		      			</tr>
		      		</tbody>
		    	</table>
		    </form>
		</div>
	</div>
</body>
</html>