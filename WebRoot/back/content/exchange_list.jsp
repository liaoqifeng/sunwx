<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>礼品兑换信息列表</title>
	<%@ include file="/back/common/metainfo.jsp" %>
	<script type="text/javascript">
		function doEdit(id){
			window.location.href='${path}/back/article/edit/'+id+'.shtml';
		}
		$().ready(function(){
			var $mainTable = $("#main-table");
			var $toolBox = $("#toolBox");
			var $mainBox = $("#mainBox");
			var $realName = $("#realName");
			var $phone = $("#phone");
			var $isCollect = $("#isCollect");
			var $searchButton = $("#searchButton");
			$mainTable.datagrid({
				url:'${path }/back/exchangeInfo/list/pager.shtml',
				idField:'id',
				toolbar:$toolBox,
				pagination:true,
				singleSelect:true,
				width:'auto',
				height:$mainBox.height(),
				columns:[[
				    {checkbox:true,field:'id',align:'center'},
					{title:'微信名',field:'member',align:'center',width:'100',
				    	formatter: function(value,row,index){return (row.member.realname==null?'-':row.member.realname);}
			    	},
					{title:'手机号',field:'category',align:'center',width:'100',
						formatter: function(value,row,index){return (row.member.phone==null?'-':row.member.phone);}
					},
					{title:'礼品编号',field:'productNumber',align:'center',width:'100',
						formatter: function(value,row,index){ return row.product.number;}
					},
					{title:'礼品名称',field:'productName',align:'center',width:'200'},
					{title:'是否领取',field:'isCollect',align:'center',width:'80',
						formatter: function(value,row,index){
							if(value){
								return "<font color='green'>${fn:call('Common.yes')}</color>";
							}else{
								return "<font color='red'>${fn:call('Common.no')}</color>";
							}
						}
					},
					{title:'兑换时间',field:'createDate',align:'center',width:'150'},
					{title:'领取时间',field:'modifyDate',align:'center',width:'150',
						formatter: function(value,row,index){
							if(row.isCollect){
								return value;
							}else{
								return "-";
							}
						}
					},
					{title:'${fn:call("Common.operator")}',field:'operator',align:'center',width:'100',
						formatter: function(value,row,index){
							if(row.isCollect){
								return "已经领取";
							}else{
								var html = '<a href="#" data-id="'+row.id+'" class="collect" style=" text-decoration:none;">[领取]</a>';
		                   		return html;
							}
							
						}
					}
				]],
				pageNumber:1,
				pageSize:20,
				loadMsg:"${fn:call('Common.message.loading')}",
				onLoadSuccess:function(){
					$mainBox.find("a.collect").unbind("click").bind("click",function(){
						var $this = $(this);
						$.ajax({
						    type: "POST",                                       
			                url: "${path}/back/exchangeInfo/collect.shtml",          
			                dataType: "json",
			                data: { id: $this.data("id")},  
			                success: function (data) {
			             	   if(data.type == "success"){ 
			             		  $.messager.alert('提示',"领取成功");
			             		 reload();
				             	}else{
				             		$.messager.alert('提示',"领取失败");
						        }
			                }        
						});
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
			var reload = function(){
				var realName = $realName.val();
				var phone = $phone.val();
				var isCollect = $isCollect.val();
				$mainTable.datagrid('reload',{
					realName: realName,
					phone: phone,
					isCollect: isCollect,
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
					<a href="#" button="reload" class="easyui-linkbutton" iconCls="icon-reload" plain="true"><fmt:message key="Common.button.refresh"/></a>
				</td>
				<td align="right">
					微信名：<input type="text" id="realName" class="text"/>
					手机号：<input type="text" id="phone" class="text"/>
					是否领取：
					<select id="isCollect" class="select">
						<option value="">--<fmt:message key="Common.message.select"/>--</option>
						<option value="true"><fmt:message key="Common.yes"/></option>
						<option value="false"><fmt:message key="Common.no"/></option>
					</select>
					<a href="#" id="searchButton" class="easyui-linkbutton" iconCls="icon-search"><fmt:message key="Common.button.search"/></a>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>