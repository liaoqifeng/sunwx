<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>积分维护</title>
<%@ include file="/back/common/metainfo.jsp"%>
<style type="text/css">
ul {
	list-style: none;
}

li {
	height: 20px;
	line-height: 20px;
}
</style>
<script type="text/javascript">
	$().ready(function() {
		var $mainBox = $("#mainBox");
		var $mainPanel = $("#mainPanel");
		var $editForm = $("#editForm");
		var $saveButton = $("#saveButton");
		$mainPanel.panel({
			width : $mainBox.width(),
			height : $mainBox.height(),
			tools : [ {
				iconCls : 'icon-reload',
				handler : function() {
					window.location.reload();
				}
			} ]
		});
		$(window).resize(function() {
			var width = $mainBox.width();
			var height = $mainBox.height();
			$mainPanel.panel('resize', {
				width : width,
				height : height
			});
		});

		$.validator.addMethod("compare", function(value, element, param) {
			var parameterValue = new Number($(param).val());
			try {
				var a = (parameterValue + new Number(value));
				return a >= 0;
			} catch (e) {
				return false;
			}
		}, "扣除积分不能大于当前积分");
		$editForm.validate({
			rules : {
				"member.id" : {
					required : true,
					maxlength : 255
				},
				"score" : {
					required : true,
					integer : true,
					compare : "#currentScore"
				}
			}
		});

		$("#browserButton").browser({
			callback : function(data) {
				$("#image").val(data.content);
			}
		})
		$saveButton.click(function() {
			$editForm.submit();
		});

	});

	function findUsers() {
		var name = $("#name").val();
		if (name == "") {
			alert("请输入查询的会员名称")
		} else {
			$.ajax({
				url : "${path}/back/scoreLog/findUsers.shtml",// 跳转到 action    
				type : 'post',
				cache : false,
				dataType : 'json',
				data : {
					realname : name
				},
				success : function(data) {
					$("#users").empty();
					for ( var int = 0; int < data.length; int++) {
						var li = $("<li>", {

						}).appendTo("#users");
						var radio = $("<input>", {
							"name" : "user",
							"type" : "radio",
							"value" : data[int].id,
							"userName" : data[int].realname,
							"score" : data[int].score

						}).appendTo(li);
						var span = $("<span>").appendTo(li);
						span.text(data[int].realname);

						radio.click(function() {
							$("#realname").val($(this).attr("userName"));
							$("#memberId").val($(this).attr("value"));
							$("#currentScore").val($(this).attr("score"));
						});
					}

				},
				error : function() {
					// view("异常！");  

				}
			});

		}

	}
</script>
</head>
<body>
	<div id="mainBox" class="main-box">
		<div id="mainPanel" class="easyui-panel" iconCls="icon-add" title="&nbsp;&nbsp;积分维护">
			<form id="editForm" action="${path }/back/scoreLog/add.shtml" method="post">
				<table class="table-edit" align="center" cellpadding="0" cellspacing="0" width="500px;">
					<tbody>
						<tr>
							<td width="20%" align="right"><font color="red">&nbsp;&nbsp;</font>会员查询：&nbsp;&nbsp;</td>
							<td width="80%" align="left"><input type="text" class="text " id="name" />&nbsp;&nbsp;&nbsp;&nbsp;<a
								href="javascript:findUsers()" id="searchButton" class="easyui-linkbutton"
								iconCls="icon-search">搜索</a>
								<ul id="users">

								</ul></td>
						</tr>
						<tr>
							<td align="right">会员名称：&nbsp;&nbsp;</td>
							<td align="left"><input type="text" id="realname" class="text " readonly="readonly" /><input
								type="hidden" name="member.id" id="memberId" />&nbsp;&nbsp;&nbsp;&nbsp;请在会员查询中搜索后选择</td>
						</tr>
						<tr>
							<td align="right">当前积分：&nbsp;&nbsp;</td>
							<td align="left"><input type="text" id="currentScore" class="text " readonly="readonly" /></td>
						</tr>

						<tr>
							<td align="right">新增积分：&nbsp;&nbsp;</td>
							<td align="left"><input type="text" name="score" class="text " />&nbsp;&nbsp;&nbsp;&nbsp;扣除积分请填负数</td>
						</tr>
						<tr>
							<td align="right">备注：&nbsp;&nbsp;</td>
							<td align="left"><input type="text" name="remark" class="text" /></td>
						</tr>
						<tr>
							<td height="60"></td>
							<td align="left"><a href="#" id="saveButton" class="easyui-linkbutton"
								data-options="iconCls:'icon-save'">保 存</a> <a href="${path }/back/sector/list.shtml"
								button="back" class="easyui-linkbutton" data-options="iconCls:'icon-back'">返 回</a></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
</body>
</html>