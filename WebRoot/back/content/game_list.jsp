<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>游戏列表</title>
	<%@ include file="/back/common/metainfo.jsp" %>
	<script type="text/javascript">
		function doEdit(id){
			window.location.href='${path}/back/game/edit/'+id+'.shtml';
		}
		$().ready(function(){
			var $mainTable = $("#main-table");
			var $toolBox = $("#toolBox");
			var $mainBox = $("#mainBox");
			$mainTable.datagrid({
				url:'${path }/back/game/list/pager.shtml',
				idField:'id',
				toolbar:$toolBox,
				pagination:true,
				width:'auto',
				height:$mainBox.height(),
				columns:[[
				    {checkbox:true,field:'id',align:'center'},
					{title:'${fn:call("Common.label.title")}',field:'title',align:'center',width:'200'},
					{title:'消耗金币公式',field:'expression',align:'center',width:'150',
						formatter: function(value,row,index){return value==null?"无限制":value+"";}
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
					{title:'${fn:call("Common.operator")}',field:'operator',align:'center',width:'100',
						formatter: function(value,row,index){
							var html = '<a href="#" onclick="doEdit(' + row.id + ')" style=" text-decoration:none;">[${fn:call("Common.edit")}]</a>';
	                   		return html;
						}
					}
				]],
				pageNumber:1,
				pageSize:20,
				loadMsg:"${fn:call('Common.message.loading')}"
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
		    
			var reload = function(){
				var title = $title.val();
				var articleCategoryId = $articleCategoryId.val();
				var isPublish = $isPublish.val();
				var isTop = $isTop.val();
				$mainTable.datagrid('reload',{
					title: title,
					articleCategoryId:articleCategoryId,
					isPublish:isPublish,
					isTop:isTop,
					pageNumber:1
				});
			};
			
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
</body>
</html>