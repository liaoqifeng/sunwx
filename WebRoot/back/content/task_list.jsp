<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>任务列表</title>
	<%@ include file="/back/common/metainfo.jsp" %>
	<script type="text/javascript">
		function doEdit(id){
			window.location.href='${path}/back/task/edit/'+id+'.shtml';
		}
		$().ready(function(){
			var $mainTable = $("#main-table");
			var $toolBox = $("#toolBox");
			var $mainBox = $("#mainBox");
			var $memberTable = $("#memberTable");
			var $memberWindow = $("#memberWindow");
			var $searchButton = $("#searchButton");
			var $completeButton = $("#completeButton");
			var $phone = $("#phone");
			var $realname = $("#realname");
			$mainTable.datagrid({
				url:'${path }/back/task/list/pager.shtml',
				idField:'id',
				toolbar:$toolBox,
				pagination:true,
				width:'auto',
				height:$mainBox.height(),
				columns:[[
					{title:'${fn:call("Common.label.title")}',field:'title',align:'center',width:'200'},
					{title:'消耗金币',field:'score',align:'center',width:'150',
						formatter: function(value,row,index){return value==null?"无限制":value+"/次";}
					},
					{title:'限制次数',field:'count',align:'center',width:'150',
						formatter: function(value,row,index){return value==null?"无限制":value+"/天";}
					},
					{title:'开始时间点',field:'beginTime',align:'center',width:'150',
						formatter: function(value,row,index){return value==null?"无限制":"每天"+value+"点";}
					},
					{title:'结束时间点',field:'endTime',align:'center',width:'150',
						formatter: function(value,row,index){return value==null?"无限制":"每天"+value+"点";}
					},
					{title:'${fn:call("Common.operator")}',field:'operator',align:'center',width:'200',
						formatter: function(value,row,index){
							var html = '<a href="#" onclick="doEdit(' + row.id + ')" style=" text-decoration:none;">[${fn:call("Common.edit")}]</a>';
							html += '&nbsp;&nbsp;<a href="#" class="taskComplete" data-id="'+row.id+'" style="text-decoration:none;">[设置任务完成]</a>';
	                   		return html;
						}
					}
				]],
				pageNumber:1,
				pageSize:20,
				loadMsg:"${fn:call('Common.message.loading')}",
				onLoadSuccess:function(){
					$mainBox.find("a.taskComplete").unbind("click").bind("click",function(){
						var $this = $(this);
						$memberWindow.window('open');
						$memberTable.data("taskId",$this.data("id"));
					});
				}
			});
			$(window).resize(function() { 
				var width= $mainBox.width(); 
				var height=  $mainBox.height();
				$mainTable.datagrid('resize', { width : width,height:height }); 
			});
			$mainTable.datagrid('getPager').pagination({  
		        pageSize: 20,
		        pageList: [10,20,30,50,100], 
		        beforePageText: "${fn:call('Common.page.beforePageText')}",
		        afterPageText: "${fn:call('Common.page.afterPageText')}",  
		        displayMsg: "${fn:call('Common.page.displayMsg')}"
		    }); 

			$memberTable.datagrid({
				url:'${path }/back/member/list/pager.shtml',
				toolbar:$("#memberToolBox"),
				pagination:true,
				rownumbers: true,
				singleSelect: true,
				width:'auto',
				height:'460',
				columns:[[
				    {checkbox:true,field:'id',align:'center'},
					{title:'会员名称',field:'username',align:'center',width:'100'},
					{title:'会员帮派',field:'faction',align:'center',width:'100',
						formatter: function(value,row,index){return value.name;}
					},
					{title:'会员职位',field:'job',align:'center',width:'100',
						formatter: function(value,row,index){return value.name;}
					},
					{title:'金币',field:'score',align:'center',width:'100'},
					{title:'创建时间',field:'createDate',align:'center',width:'100'},
					{title:'用户状态',field:'statusName',align:'center',width:'80'}
				]],
				idField:"id",
				pageNumber:1,
				pageSize:20,
				loadMsg:"正在加载,请等待..."
			});
			$memberTable.datagrid('getPager').pagination({  
		        pageSize: 20,
		        pageList: [10,20,30,50,100], 
		        beforePageText: '第',
		        afterPageText: '页    共 {pages} 页',  
		        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
		    });
			$searchButton.click(function(){
				$memberTable.datagrid('reload',{
					phone:$phone.val(),
					realname:$realname.val(),
					pageNumber:1
				});
			});
			$completeButton.click(function(){
				var selected = $memberTable.datagrid("getSelected");
				if(selected == "" || selected == null || selected.length <= 0){
					$.messager.alert("提示","请选择会员");
					return false;
				}
				$.ajax({
				    type: "POST",                                       
                    url: "${path}/weixin/common/doTask.shtml",          
                    dataType: "json",
                    data: { memberId: selected.id,taskId:$memberTable.data("taskId")},
                    beforeSend: function() {},     
                    success: function (data) {
                    	$.messager.alert("提示",data.content);
                    }        
				});
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
					<a href="#" button="reload" class="easyui-linkbutton" iconCls="icon-reload" plain="true"><fmt:message key="Common.button.refresh"/></a>
				</td>
			</tr>
		</table>
	</div>
	<div id="tools"><a id="selectSaveButton" href="javascript:void(0)" class="icon-save"></a></div>
	<div id="memberToolBox">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<td height="50">
					手机号：<input type="text" id="phone" class="text middle"/>
					微信名：<input type="text" id="realname" class="text middle"/>
					<a href="#" id="searchButton" class="easyui-linkbutton" iconCls="icon-search">查      询</a>
					<a href="#" id="completeButton" class="easyui-linkbutton" iconCls="icon-add">完      成</a>
				</td>
			</tr>
		</table>
	</div>
	<div id="memberWindow" class="easyui-window" title="选择会员" data-options="modal:true,closed:true,minimizable:false,maximizable:false,tools:'#tools'" style="width:800px;height:500px;padding:1px;">
		<table id="memberTable"></table>
	</div>
</body>
</html>