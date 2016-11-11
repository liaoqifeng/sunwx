<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<% request.setAttribute("path",request.getContextPath()); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
	<title>设置挖金币参数</title>
	<link rel="stylesheet" type="text/css" href="${path }/back/css/easyui.css"/>
	<link rel="stylesheet" type="text/css" href="${path }/back/css/icon.css"/>
	<link rel="stylesheet" type="text/css" href="${path }/back/css/style.css"/>
	<script type="text/javascript" src="${path }/common/js/jquery-1.11.0.min.js"></script>
	<script type="text/javascript" src="${path }/back/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${path }/common/js/jquery.json-2.4.js"></script>
	<script type="text/javascript" src="${path }/common/js/jquery.validate.js"></script>
	<script type="text/javascript" src="${path }/common/js/jquery.validate.methods.js"></script>
	<script type="text/javascript" src="${path }/back/js/jquery.common.js"></script>
	<script type="text/javascript">
		$().ready(function(){
			${fn:message(message)}
			var $mainBox = $("#mainBox");
			var $mainPanel = $("#mainPanel");
			var $editForm = $("#editForm");
			var $saveButton = $("#saveButton");
			var $tab = $("#tab");
			var $minScore = $("#minScore");
			var $maxScore = $("#maxScore");
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
			$("#addButton").click(function(){
				var tr = '<tr><td width="20%" align="right">时间点：&nbsp;&nbsp;</td>'+
  				'<td width="80%" align="left"><input class="easyui-timespinner" data-options="showSeconds:true" style="width:100px;">&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" class="delete">[删除]</a></td></tr>';
				$tab.find("tbody").append(tr);
				$.parser.parse();
			});
			$tab.on("click",".delete",function(){
				$(this).closest("tr").remove();
			});
			$.validator.addMethod("compare", 
				function(value, element, param) {
					var parameterValue = $(param).val();
					if ($.trim(parameterValue) == "" || $.trim(value) == "") {
						return true;
					}
					try {
						return parseFloat(parameterValue) <= parseFloat(value);
					} catch(e) {
						return false;
					}
				},
				"不允许小于最小值"
			);
			$editForm.validate({
				rules: {
					"minScore": {
						required: true,
						digits: true
					},
					"maxScore": {
						required: true,
						digits: true,
						compare: "#minScore"
					}
				},
				submitHandler: function(form) {
					form.submit();
				}
			});
			$("#saveButton").click(function(){
				var timers = new Array();
				$tab.find("tbody tr").each(function(){
					var $input = $(this).find("input");
					timers.push($input.timespinner("getHours")+":"+$input.timespinner("getMinutes")+":"+$input.timespinner("getSeconds"));
				});
				if(timers == null || timers.length <=0){
					$.messager.alert('提示','最少设置一个时间点');
					return false;
				}
				$("#timers").val($.toJSON(timers));
				$editForm.submit();
			});
		});
	</script>
</head>
<body>
	<div id="mainBox" class="main-box">
		<div id="mainPanel" class="easyui-panel" iconCls="icon-add" title="&nbsp;&nbsp;设置挖金币参数">
			<form id="editForm" action="${path }/back/dugCoin/edit.shtml" method="post">
				<input type="hidden" name="id" value="${dugCoin.id }"/>
				<input type="hidden" id="timers" name="timers"/>
				<input type="hidden" name="createDate" value="${dugCoin.createDate }"/>
		    	<table id="tab" class="table-edit" width="100%" align="center" cellpadding="0" cellspacing="0">
		    		<thead>
		    			<tr>
		      				<td width="20%" align="right"><font color="red">*&nbsp;&nbsp;</font>金币区间：&nbsp;&nbsp;</td>
		      				<td width="80%" align="left">
		      					<input type="text" id="minScore" name="minScore" value="${dugCoin.minScore }" class="text short"/>&nbsp;-
		      					<input type="text" id="maxScore" name="maxScore" value="${dugCoin.maxScore }" class="text short"/>
		      				</td>
		      			</tr>
		      			<tr>
		      				<td width="20%" align="right"><font color="red">*&nbsp;&nbsp;</font>持续时间：&nbsp;&nbsp;</td>
		      				<td width="80%" align="left"><input type="text" name="lastTime" value="${dugCoin.lastTime }" class="text"/>分钟</td>
		      			</tr>
		      			<tr>
		      				<td width="20%" align="right"></td>
		      				<td width="80%" align="left"><a href="#" id="addButton" class="easyui-linkbutton" data-options="iconCls:'icon-add'">新增</a></td>
		      			</tr>
		    		</thead>
		      		<tbody>
		      			<c:forEach var="row" items="${dugCoin.timers}" varStatus="i">
		      			<tr>
		      				<td width="20%" align="right">时间点：&nbsp;&nbsp;</td>
		      				<td width="80%" align="left"><input class="easyui-timespinner" value="${row }" data-options="showSeconds:true" style="width:100px;">&nbsp;&nbsp;&nbsp;&nbsp;<c:if test="${i.index != 0}"><a href="javascript:void(0);" class="delete">[删除]</a></c:if></td>
		      			</tr>
		      			</c:forEach>
		      		</tbody>
		      		<tfoot>
		      			<tr>
		      				<td width="20%" align="right"></td>
		      				<td width="80%" align="left">
								<a href="#" id="saveButton" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>
							</td>
		      			</tr>
		      		</tfoot>
		    	</table>
		    </form>
		</div>
	</div>
</body>
</html>