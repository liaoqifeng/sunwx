<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>收款信息</title>
	<%@ include file="/back/common/metainfo.jsp" %>
	<script type="text/javascript">
		$().ready(function(){
			var $mainBox = $("#mainBox");
			var $mainPanel = $("#mainPanel");
			var $editForm = $("#editForm");
			var $tab = $("#tab");
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
		});
	</script>
</head>
<body>
	<div id="mainBox" class="main-box">
		<div id="mainPanel" class="easyui-panel" iconCls="icon-search" title="&nbsp;&nbsp;收 款  信  息">
			<table width="100%" class="input tabContent table">
				<tr>
	    			<td width="30%" height="30" align="right">&nbsp;编号：&nbsp;&nbsp;</td>
	    			<td width="70%" align="left">${paymentInfo.number }</td>
	    		</tr>
	    		<tr>
	    			<td height="30" align="right">&nbsp;订单：&nbsp;&nbsp;</td>
	    			<td align="left">${paymentInfo.order.number }</td>
	    		</tr>
	    		<tr>
	    			<td height="30" align="right">&nbsp;类型：&nbsp;&nbsp;</td>
	    			<td align="left">${paymentInfo.typeName }</td>
	    		</tr>
	    		<tr>
	    			<td height="30" align="right">&nbsp;支付方式：&nbsp;&nbsp;</td>
	    			<td align="left">${paymentInfo.paymentwayName }</td>
	    		</tr>
	    		<tr>
	    			<td height="30" align="right">&nbsp;支付状态：&nbsp;&nbsp;</td>
	    			<td align="left">${paymentInfo.statusName }</td>
	    		</tr>
	    		<tr>
	    			<td height="30" align="right">&nbsp;会员：&nbsp;&nbsp;</td>
	    			<td align="left">${paymentInfo.member.username }</td>
	    		</tr>
	    		<tr>
	    			<td height="30" align="right">&nbsp;付款金额：&nbsp;&nbsp;</td>
	    			<td align="left">${fn:currency(paymentInfo.paidAmount,true) }</td>
	    		</tr>
	    		<tr>
	    			<td height="30" align="right">&nbsp;付款银行：&nbsp;&nbsp;</td>
	    			<td align="left">${paymentInfo.bank }</td>
	    		</tr>
	    		<tr>
	    			<td height="30" align="right">&nbsp;银行帐号：&nbsp;&nbsp;</td>
	    			<td align="left">${paymentInfo.account }</td>
	    		</tr>
	    		<tr>
	    			<td height="30" align="right">&nbsp;付款人：&nbsp;&nbsp;</td>
	    			<td align="left">${paymentInfo.payer }</td>
	    		</tr>
	    		<tr>
	    			<td height="30" align="right">&nbsp;备注：&nbsp;&nbsp;</td>
	    			<td align="left">${paymentInfo.remark }</td>
	    		</tr>
	    		<tr>
	    			<td height="30" align="right">&nbsp;操作人：&nbsp;&nbsp;</td>
	    			<td align="left">${paymentInfo.operator }</td>
	    		</tr>
	    		<tr>
	    			<td height="30" align="right">&nbsp;收款日期：&nbsp;&nbsp;</td>
	    			<td align="left">${paymentInfo.status=='success' ? paymentInfo.modifyDate : '-' }</td>
	    		</tr>
	    		<tr>
	    			<td height="30" align="right">&nbsp;创建日期：&nbsp;&nbsp;</td>
	    			<td align="left">${paymentInfo.createDate }</td>
	    		</tr>
			</table>
	    	<table width="100%" align="center" cellpadding="0" cellspacing="0">
	      		<tr>
      				<td width="60%" align="center" height="50">
      					<a href="#" button="back" class="easyui-linkbutton" data-options="iconCls:'icon-back'">返      回</a>
      				</td>
      				<td width="40%"></td>
      			</tr>
	    	</table>
		</div>
	</div>
</body>
</html>