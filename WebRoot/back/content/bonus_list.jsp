<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>红包列表</title>
	<%@ include file="/back/common/metainfo.jsp" %>
	<script type="text/javascript">
		$().ready(function(){
			var $mainTable = $("#main-table");
			var $toolBox = $("#toolBox");
			var $mainBox = $("#mainBox");
			var $number = $("#number");
			var $isPublish = $("#isPublish");
			var $searchButton = $("#searchButton");
			$mainTable.datagrid({
				url:'${path }/back/bonus/list/pager.shtml',
				idField:'id',
				treeField:'name',
				toolbar:$toolBox,
				pagination:true,
				singleSelect:true,
				width:'auto',
				height:$mainBox.height(),
				columns:[[
					{title:'编号',field:'id',align:'center',width:'50'},
					{title:'商户名称',field:'sendName',align:'center',width:'100'},
					{title:'活动名称',field:'activeName',align:'center',width:'100'},
					{title:'开始时间',field:'beginDate',align:'center',width:'100'},
					{title:'结束时间',field:'endDate',align:'center',width:'100'},
					{title:'红包总金额',field:'totalAmount',align:'center',width:'100'},
					{title:'最小红包金额 ',field:'minAmount',align:'center',width:'100'},
					{title:'最大红包金额 ',field:'maxAmount',align:'center',width:'100'},
					{title:'发放总人数',field:'count',align:'center',width:'100'},
					{title:'是否发布',field:'isPublish',align:'center',width:'60',
						formatter: function(value,row,index){
							if(value)
								return "<font color='green'>${fn:call('Common.yes')}</font>";
							else
								return "<font color='red'>${fn:call('Common.no')}</font>";
						}
					},
					{title:'创建日期',field:'createDate',align:'center',width:'100'},
					{title:'操作',field:'operator',align:'center',width:'100',
						formatter: function(value,row,index){
							var html = '<a href="${path}/back/deliverInfo/view.shtml?id='+row.id+'" style=" text-decoration:none;">[查看]</a>&nbsp;<a href="${path}/back/bonus/edit/'+row.id+'.shtml" style=" text-decoration:none;">[编辑]</a>';
	                   		return html;
						}
					}
				]],
				pageNumber:1,
				pageSize:20,
				loadMsg:"正在加载,请等待..."
			});
			$(window).resize(function() { 
				var width= $mainBox.width(); 
				var height=  $mainBox.height();
				$mainTable.datagrid('resize', { width : width,height:height }); 
			});
			$mainTable.datagrid('getPager').pagination({  
		        pageSize: 20,
		        pageList: [10,20,30,50,100], 
		        beforePageText: '第',
		        afterPageText: '页    共 {pages} 页',  
		        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
		    }); 
		    
			var reload = function(){
				var isPublish = $isPublish.val();
				$mainTable.datagrid('reload',{
					isPublish: isPublish,
					pageNumber:1
				});
			};
			$searchButton.click(function(){
				reload();
			});
		});
	</script>
</head>
<body>
	<div id="mainBox" class="main-box" style="width: 100%;">
		<table title="" id="main-table"></table>
	</div>
	<div id="toolBox">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<td height="50">
					<a href="${path }/back/bonus/view.shtml" class="easyui-linkbutton" iconCls="icon-add" plain="true"><fmt:message key="Common.button.add"/></a>
					<a href="#" button="reload" class="easyui-linkbutton" iconCls="icon-reload" plain="true"><fmt:message key="Common.button.refresh"/></a>
				</td>
				<td align="right">
					是否发布：<select id="isPublish" name="isPublish" class="select">
								<option value="">--${fn:call("Common.message.select")}--</option>
								<option value="true">${fn:call("Common.yes")}</option>
								<option value="false">${fn:call("Common.no")}</option>
							  </select>
					<a href="#" id="searchButton" class="easyui-linkbutton" iconCls="icon-search">${fn:call("Common.button.search")}</a>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>