<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>定义任务规则</title>
	<%@ include file="/back/common/metainfo.jsp" %>
	<script type="text/javascript" src="${path }/back/js/jquery.tmpl.js"></script>
	<link rel="stylesheet" href="${path }/common/kindeditor/themes/default/default.css" />
	<script type="text/javascript" src="${path }/common/kindeditor/kindeditor-min.js" charset="utf-8"></script>
	<script type="text/javascript" src="${path }/common/kindeditor/lang/zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript">
		$().ready(function(){
			var $mainBox = $("#mainBox");
			var $mainPanel = $("#mainPanel");
			var $editForm = $("#editForm");
			var $tab = $("#tab");
			var $setHortationTab = $("#setHortationTab");
			var $saveButton = $("#saveButton");
			var gameItemIndex = ${fn:length(game.gameItems)};
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
			if ($.tools != null) {
				$tab.tabs("table.tabContent", {
					tabs: "input"
				});
			}
			$("input.tip").tooltip({       
				position: "center right",
				offset: [0, 10],
				opacity: 0.7
			});
			$("#browserButton").browser({callback:function(data){
				$("#image").val(data.content);
			}})
			$("#imgUrlBrowserButton").browser({callback:function(data){
				$("#imgUrl").val(data.content);
			}})
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
				"不允许小于开始时间点"
			);
			$editForm.validate({
				rules: {
					title: "required",
					endTime: { compare: "#beginTime" },
					score:{ digits: true },
					count:{ digits: true }
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
		<div id="mainPanel" class="easyui-panel" iconCls="icon-add" title="&nbsp;&nbsp;定义任务规则">
			<form id="editForm" action="${path }/back/task/edit.shtml" method="post">
				<input type="hidden" name="id" value="${task.id }"/>
				<input type="hidden" name="type" value="${task.type }"/>
				<input type="hidden" name="cycle" value="${task.cycle }"/>
				<input type="hidden" name="action" value="${task.action }"/>
				<input type="hidden" name="createDate" value="${task.createDate }"/>
		    	<ul id="tab" class="tab">
					<li><input value="基本信息" type="button" /></li>
					<li><input value="任务说明" type="button" /></li>
				</ul>
				<table width="100%" class="input tabContent table">
					<tr>
		    			<td width="30%" height="30" align="right"><font color="red">*</font>&nbsp;名称：&nbsp;&nbsp;</td>
		    			<td width="70%" align="left"><input type="text" name="title" class="text" value="${task.title }"/></td>
		    		</tr>
		    		<tr>
		    			<td height="30" align="right">&nbsp;图片：&nbsp;&nbsp;</td>
		    			<td align="left">
		    				<input type="text" id="image" name="image" value="${task.image }" class="text"/>
		    				<input type="button" id="browserButton" class="button" value="选择文件.." />
		    			</td>
		    		</tr>
		    		<c:if test="${task.type=='forward'}">
		    		<tr>
		    			<td width="30%" height="30" align="right">分享链接：&nbsp;&nbsp;</td>
		    			<td width="70%" align="left"><input type="text" name="url" class="text" value="${task.url }"/></td>
		    		</tr>
		    		<tr>
		    			<td width="30%" height="30" align="right">分享内容：&nbsp;&nbsp;</td>
		    			<td width="70%" align="left"><textarea name="content" rows="5" cols="50">${task.content }</textarea></td>
		    		</tr>
		    		<tr>
		    			<td height="30" align="right">&nbsp;分享图片：&nbsp;&nbsp;</td>
		    			<td align="left">
		    				<input type="text" id="imgUrl" name="imgUrl" value="${task.imgUrl }" class="text"/>
		    				<input type="button" id="imgUrlBrowserButton" class="button" value="选择文件.." />
		    			</td>
		    		</tr>
		    		</c:if>
		    		<tr style="display: none;">
		    			<td height="30" align="right">&nbsp;周期：&nbsp;&nbsp;</td>
		    			<td align="left">
							<select name="cycle">
		    				<c:forEach var="row" items="${cycles}">
		    				<option value="${row }"><fmt:message key="TaskCycle.${row}"/></option>
		    				</c:forEach>
		    				</select>
						</td>
		    		</tr>
		    		<tr>
		    			<td height="30" align="right">&nbsp;开始时间点：&nbsp;&nbsp;</td>
		    			<td align="left">
		    				<select id="beginTime" name="beginTime">
		    					<option value="">无限制</option>
		    					<c:forEach var="row" begin="0" end="23" step="1">
		    					<option value="${row }" ${task.beginTime==row?'selected="selected"':'' }>${row }</option>
		    					</c:forEach>
		    				</select>
		    				&nbsp;&nbsp;&nbsp;&nbsp;结束时间点：
		    				<select name="endTime">
		    					<option value="">无限制</option>
		    					<c:forEach var="row" begin="0" end="23" step="1">
		    					<option value="${row }" ${task.endTime==row?'selected="selected"':'' }>${row }</option>
		    					</c:forEach>
		    				</select>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td height="30" align="right">&nbsp;消耗金币：&nbsp;&nbsp;</td>
		    			<td align="left"><input type="text" name="score" value="${task.score }" class="text tip" title="留空为无消耗" maxlength="20"/>&nbsp;/&nbsp;次</td>
		    		</tr>
		    		<tr>
		    			<td height="30" align="right">&nbsp;限制次数：&nbsp;&nbsp;</td>
		    			<td align="left"><input type="text" name="count" value="${task.count }" class="text tip" title="留空为无限制" maxlength="20"/>&nbsp;/&nbsp;天</td>
		    		</tr>
		    		<tr>
		    			<td height="30" align="right">&nbsp;任务规则：&nbsp;&nbsp;</td>
		    			<td align="left"><textarea name="rules" rows="5" cols="50">${task.rules }</textarea></td>
		    		</tr>
		    		<tr>
		    			<td height="30" align="right">&nbsp;任务要求：&nbsp;&nbsp;</td>
		    			<td align="left"><textarea name="require" rows="5" cols="50">${task.require }</textarea></td>
		    		</tr>
		    		<tr>
		    			<td height="30" align="right">&nbsp;排序：&nbsp;&nbsp;</td>
		    			<td align="left"><input type="text" name="orderList" class="text short" value="${task.orderList }"/></td>
		    		</tr>
				</table>
	   			<table width="100%" class="input tabContent table">
	   				<tr>
		    			<td><textarea name="description" style="width:1000px;height:350px;">${task.description }</textarea></td>
		    		</tr>
	    		</table>
		    	<table width="100%" align="center" cellpadding="0" cellspacing="0">
		      		<tr>
	      				<td width="60%" align="center" height="50">
	      					<a href="#" id="saveButton" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保      存</a>
	      					<a href="#" button="back" class="easyui-linkbutton" data-options="iconCls:'icon-back'">返      回</a>
	      				</td>
	      				<td width="40%"></td>
	      			</tr>
		    	</table>
		    </form>
		</div>
	</div>
	<script type="text/javascript">
		KindEditor.ready(function(K) {
			var editor1 = K.create('textarea[name="description"]', {
				uploadJson : '${path }/back/file/upload.shtml',
				fileManagerJson : '${path }/back/file/browser.shtml',
				allowFileManager : true,
				afterCreate : function() {},
				afterBlur: function(){this.sync();}
			});
		});
	</script>
</body>
</html>
