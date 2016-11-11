<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>新增红包</title>
	<%@ include file="/back/common/metainfo.jsp" %>
	<script type="text/javascript" src="${path }/back/js/datePicker/WdatePicker.js" charset="utf-8"></script>
	<script type="text/javascript">
		$().ready(function(){
			var $mainBox = $("#mainBox");
			var $mainPanel = $("#mainPanel");
			var $editForm = $("#editForm");
			var $tab = $("#tab");
			var $setHortationTab = $("#setHortationTab");
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
			$.validator.addMethod("compareDate", 
				function(value, element, param) {
					var startDate = $(param).val(); 
		            var date1 = new Date(Date.parse(startDate.replace("-", "/"))); 
		            var date2 = new Date(Date.parse(value.replace("-", "/"))); 
		            return date1 < date2;
				},
				"结束时间必须大于开始时间"
			);
			$.validator.addMethod("compareMin", 
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
				"不允许小于最小红包金额"
			);
			$.validator.addMethod("compareMax", 
				function(value, element, param) {
					var parameterValue = $(param).val();
					if ($.trim(parameterValue) == "" || $.trim(value) == "") {
						return true;
					}
					try {
						return parseFloat(parameterValue) >= parseFloat(value);
					} catch(e) {
						return false;
					}
				},
				"不允许大于红包总金额"
			);
			$editForm.validate({
				rules: {
					nickName: { required: true },
					sendName: { required: true },
					beginDate: { required: true },
					endDate: { required: true,compareDate:"#beginDate" },
					totalAmount:{
						required: true,
						min: 0,
						decimal:{
							length:18,
							scale:2
						}
					},
					minAmount:{
						required: true,
						min: 0,
						decimal:{
							length:18,
							scale:2
						}
					},
					maxAmount:{
						required: true,
						min: 0,
						decimal:{
							length:18,
							scale:2
						},
						compareMin: "#minAmount",
						compareMax: "#totalAmount"
					},
					count:{ required: true,digits: true,min: 1 }
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
		<div id="mainPanel" class="easyui-panel" iconCls="icon-add" title="&nbsp;&nbsp;新增红包">
			<form id="editForm" action="${path }/back/bonus/add.shtml" method="post">
				<table width="100%" class="input tabContent table">
					<tr>
		    			<td width="30%" height="30" align="right"><font color="red">*</font>&nbsp;提供方名称：&nbsp;&nbsp;</td>
		    			<td width="70%" align="left"><input type="text" name="nickName" class="text" value=""/></td>
		    		</tr>
		    		<tr>
		    			<td width="30%" height="30" align="right"><font color="red">*</font>&nbsp;商户名称：&nbsp;&nbsp;</td>
		    			<td width="70%" align="left"><input type="text" name="sendName" class="text" value=""/></td>
		    		</tr>
		    		<tr>
		    			<td width="30%" height="30" align="right">&nbsp;商户logo：&nbsp;&nbsp;</td>
		    			<td align="left">
		    				<input type="text" id="image" name="logo" value="" class="text"/>
		    				<input type="button" id="browserButton" class="button" value="选择文件.." />
		    			</td>
		    		</tr>
		    		<tr>
		    			<td height="30" align="right"><font color="red">*</font>&nbsp;开始时间：&nbsp;&nbsp;</td>
		    			<td align="left"><input type="text" id="beginDate" name="beginDate" class="text Wdate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss', maxDate: '#F{$dp.$D(\'endDate\')}'});"/></td>
		    		</tr>
		    		<tr>
		    			<td height="30" align="right"><font color="red">*</font>&nbsp;结束时间：&nbsp;&nbsp;</td>
		    			<td align="left"><input type="text" id="endDate" name="endDate" class="text Wdate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss', minDate: '#F{$dp.$D(\'beginDate\')}'});"/></td>
		    		</tr>
		    		<tr>
		    			<td width="30%" height="30" align="right"><font color="red">*</font>&nbsp;红包总金额：&nbsp;&nbsp;</td>
		    			<td width="70%" align="left"><input type="text" id="totalAmount" name="totalAmount" class="text short tip" title="单位：元" value=""/></td>
		    		</tr>
		    		<tr>
		    			<td width="30%" height="30" align="right"><font color="red">*</font>&nbsp;最小红包金额 ：&nbsp;&nbsp;</td>
		    			<td width="70%" align="left"><input type="text" id="minAmount" name="minAmount" class="text short tip" title="单位：元" value=""/></td>
		    		</tr>
		    		<tr>
		    			<td width="30%" height="30" align="right"><font color="red">*</font>&nbsp;最大红包金额 ：&nbsp;&nbsp;</td>
		    			<td width="70%" align="left"><input type="text" name="maxAmount" class="text short tip" title="单位：元" value=""/></td>
		    		</tr>
		    		<tr>
		    			<td width="30%" height="30" align="right"><font color="red">*</font>&nbsp;发放总人数 ：&nbsp;&nbsp;</td>
		    			<td width="70%" align="left"><input type="text" name="count" class="text short" value=""/></td>
		    		</tr>
		    		<tr>
		    			<td height="30" align="right">&nbsp;活动名称：&nbsp;&nbsp;</td>
		    			<td align="left"><input type="text" name="activeName" value="" class="text"/>&nbsp;&nbsp;</td>
		    		</tr>
		    		<tr>
		    			<td height="30" align="right">&nbsp;红包祝福语：&nbsp;&nbsp;</td>
		    			<td align="left"><input type="text" name="wishing" value="" class="text"/>&nbsp;&nbsp;</td>
		    		</tr>
		    		<tr>
		    			<td height="30" align="right">&nbsp;备注：&nbsp;&nbsp;</td>
		    			<td align="left"><input type="text" name="remark" value="" class="text long"/>&nbsp;&nbsp;</td>
		    		</tr>
		    		<tr>
		    			<td height="30" align="right">&nbsp;是否发布：&nbsp;&nbsp;</td>
		    			<td align="left"><input type="checkbox" name="isPublish" value="true"/></td>
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
	
</body>
</html>
