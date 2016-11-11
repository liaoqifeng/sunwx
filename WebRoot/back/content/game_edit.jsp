<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>定义游戏规则</title>
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
			
			$("#addButton").click(function(){
				var $tr = $("#addItemsTemplate").tmpl({index:gameItemIndex,hortationIndex:gameItemIndex}).appendTo($setHortationTab.find("tbody"));
				var $img = $tr.find(".hortationImage");
				$img.browser({callback:function(data){
					$img.attr("src",data.content);
					$img.next().val(data.content);
				}});
				gameItemIndex++;
			});
			
			$setHortationTab.on("change",".hortationType",function(){
				var value = $(this).val();
				var $parent = $(this).closest("tr");
				var $product = $parent.find(".productId");
				var $score = $parent.find(".score");
				if(value == "physical"){
					$product.prop("disabled",false);
					$score.prop("disabled",true);
				}else{
					$product.prop("disabled",true);
					$score.prop("disabled",false);
				}
			});

			$setHortationTab.on("click",".delete",function(){
				$(this).closest("tr").remove();
			});

			$setHortationTab.find(".hortationImage").each(function(){
				var $this = $(this);
				$this.browser({callback:function(data,obj){
					$this.attr("src",data.content);
					$this.next().val(data.content);
				}});
			});

			$("#calculateButton").click(function(){
				var $rows = $setHortationTab.find("tbody tr");
				if($rows.size() <= 0){
					$.messager.alert('提示','您还没有设置奖品');
					return false;
				}
				if($rows.filter(".new").size() > 0){
					$.messager.alert('提示','请保存后再进行概率测试');
					return false;
				}
				$.ajax({
				    type: "POST",                                       
                    url: "${path}/back/game/calculate.shtml",          
                    dataType: "json",
                    data: { "id": ${game.id}},
                    cache:false,  
                    success: function (data) {
                    	$("#calculateTemplate").tmpl({data:data}).dialog({
            				title: '    测      试     概     率 &nbsp;&nbsp;(中奖测试以10000次为基准) ',
            				width: 600,
            				height: 600,
            				closed: false,
            				cache: false,
            				modal: true
            			});
                    }        
				});
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
				"不允许小于开始时间点"
			);
			$.validator.addMethod("isExitProduct", 
				function(value, element, param) {
					if(value == undefined || value == ""){
						return true;
					}
					var result = null;
					$.ajax({
					    type: "POST",                                       
	                    url: "${path}/back/game/isExits.shtml",          
	                    dataType: "json",
	                    data: { "productIds": value},
	                    cache:false,  
	                    async:false, 
	                    success: function (data) {
	                    	result = data;
	                    }        
					});
					return result;
				},
				"赠品不存在"
			);
			$.validator.addClassRules({
				hortationIndex: {
					required: true,
					integer:true
				},
				productId: {
					isExitProduct: true
				},
				title:{ required: true },
				score:{ integer: true },
				count:{ integer: true },
				probability:{
					required: true,
					decimal:{
						length:18,
						scale:6
					}
				}
			});
			$editForm.validate({
				rules: {
					title: "required",
					endTime: { compare: "#beginTime" },
					score:{ digits: true },
					count:{ digits: true }
				}
			});
			$saveButton.click(function(){
				var $rows = $setHortationTab.find("tbody tr");
				if($rows.size() <= 0){
					$.messager.alert('提示','您还没有设置奖品,不能提交');
					return false;
				}
				var isTrue = true;
				var probability = 0;
				$rows.each(function(i){
					if(parseInt($(this).find(".hortationIndex").val()) != i){
						isTrue = false;
					}
					probability += parseFloat($(this).find(".probability").val());
				});
				if(!isTrue){
					$.messager.alert('提示','奖品索引编号规则不正确,不能提交');
					return false;
				}
				if(probability > 1){
					$.messager.alert('提示','概率总和不能大于1,不能提交');
					return false;
				}
				$setHortationTab.find(".productId").each(function(){
					var $this = $(this);
					if(!$this.prop("disabled")){
						$this.next().val($this.val());	
					}
				});
				$editForm.submit();
			});
		});
	</script>
</head>
<body>
	<div id="mainBox" class="main-box">
		<div id="mainPanel" class="easyui-panel" iconCls="icon-add" title="&nbsp;&nbsp;定义游戏规则">
			<form id="editForm" action="${path }/back/game/edit.shtml" method="post">
				<input type="hidden" name="id" value="${game.id }"/>
				<input type="hidden" name="type" value="${game.type }"/>
				<input type="hidden" name="cycle" value="${game.cycle }"/>
		    	<ul id="tab" class="tab">
					<li><input value="基本信息" type="button" /></li>
					<li><input value="设置奖品" type="button" /></li>
					<li><input value="游戏说明" type="button" /></li>
				</ul>
				<table width="100%" class="input tabContent table">
					<tr>
		    			<td width="30%" height="30" align="right"><font color="red">*</font>&nbsp;名称：&nbsp;&nbsp;</td>
		    			<td width="70%" align="left"><input type="text" name="title" class="text" value="${game.title }"/></td>
		    		</tr>
		    		<tr>
		    			<td height="30" align="right">&nbsp;图片：&nbsp;&nbsp;</td>
		    			<td align="left">
		    				<input type="text" id="image" name="image" value="${game.image }" class="text"/>
		    				<input type="button" id="browserButton" class="button" value="选择文件.." />
		    			</td>
		    		</tr>
		    		<tr style="display: none;">
		    			<td height="30" align="right">&nbsp;周期：&nbsp;&nbsp;</td>
		    			<td align="left">
							<select name="cycle">
		    				<c:forEach var="row" items="${cycles}">
		    				<option value="${row }"><fmt:message key="GameCycle.${row}"/></option>
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
		    					<option value="${row }" ${game.beginTime==row?'selected="selected"':'' }>${row }</option>
		    					</c:forEach>
		    				</select>
		    				&nbsp;&nbsp;&nbsp;&nbsp;结束时间点：
		    				<select name="endTime">
		    					<option value="">无限制</option>
		    					<c:forEach var="row" begin="0" end="23" step="1">
		    					<option value="${row }" ${game.endTime==row?'selected="selected"':'' }>${row }</option>
		    					</c:forEach>
		    				</select>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td height="30" align="right">&nbsp;状态：&nbsp;&nbsp;</td>
		    			<td align="left">
		    				<select name="status">
		    				<c:forEach var="row" items="${status}">
		    				<option value="${row }" ${game.status==row?'selected="selected"':'' }><fmt:message key="GameStatus.${row}"/></option>
		    				</c:forEach>
		    				</select>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td height="30" align="right">&nbsp;消耗金币公式：&nbsp;&nbsp;</td>
		    			<td align="left"><input type="text" name="expression" value="${game.expression }" class="text tip" title="留空为无消耗" maxlength="20"/>&nbsp;&nbsp;</td>
		    		</tr>
		    		<tr>
		    			<td></td>
		    			<td>参数:count(已抽取次数)  示例:count > 3 ? 3 : 2,表示如果已抽取次数大于3次,每次消耗3金币,否则每次消耗2金币</td>
		    		</tr>
		    		<tr>
		    			<td height="30" align="right">&nbsp;限制次数：&nbsp;&nbsp;</td>
		    			<td align="left"><input type="text" name="count" value="${game.count }" class="text tip" title="留空为无限制" maxlength="20"/>&nbsp;/&nbsp;天</td>
		    		</tr>
		    		<tr>
		    			<td height="30" align="right">&nbsp;游戏规则：&nbsp;&nbsp;</td>
		    			<td align="left"><textarea name="rules" rows="5" cols="50">${game.rules }</textarea></td>
		    		</tr>
				</table>
				<table id="setHortationTab" width="100%" class="input tabContent table">
					<thead>
						<tr>
							<td colspan="7">
								<a href="#" id="addButton" class="easyui-linkbutton" data-options="iconCls:'icon-add'">新      增</a>
								<a href="#" id="calculateButton" class="easyui-linkbutton" data-options="iconCls:'icon-save'">测      试     概     率  </a>
								<span><b style="color: red;margin-left:20px;">特别提醒：</b>&nbsp;&nbsp;奖品索引编号必须是从0开始并且不重复的连续整数,概率总和不能大于1,为了减少误差,最好总和等于1.</span>
							</td>
						</tr>
						<tr class="header">
							<td align="left" width="15%">奖品索引编号</td>
							<td align="left">奖品名称</td>
							<td align="left">奖品图片</td>
							<td align="left">奖品类别</td>
							<td align="left">商品编号</td>
							<td align="left">赠送金币</td>
							<td align="left">赠送数量</td>
							<td align="left">设置概率</td>
							<td align="left" width="4%">-</td>
						</tr>
					</thead>
					<tbody>
						<c:if test="${game.gameItems != null}">
						<c:forEach var="row" items="${game.gameItems}" varStatus="index">
						<tr>
							<td>
								<input type="text" name="gameItems[${index.index }].hortationIndex" value="${row.hortationIndex }" class="text short hortationIndex"/>
								<input type="hidden" name="gameItems[${index.index }].id" value="${row.id }"/>
								<input type="hidden" name="gameItems[${index.index }].winningCount" value="${row.winningCount }"/>	
								<input type="hidden" name="gameItems[${index.index }].createDate" value="${row.createDate }"/>	
							</td>
							<td><input type="text" name="gameItems[${index.index }].title" value="${row.title }" class="text short title"/></td>
							<td>
								<img src="${empty row.image ? fn:replace('[path]/back/images/default_no.jpg','[path]',path) : row.image }" class="hortationImage" title="点击更改图片" style="width:60px;height:60px;cursor: pointer;"/>
								<input type="hidden" name="gameItems[${index.index }].image" value="${row.image }"/>
							</td>
							<td>
								<select name="gameItems[${index.index }].hortationType" class="hortationType">
									<c:forEach var="type" items="${hortationTypes}">
									<option value="${type }" ${row.hortationType == type ? 'selected="selected"' : '' }><fmt:message key="HortationType.${type}"/></option>
									</c:forEach>
								</select>
							</td>
							<td><input type="text" name="gameItems[${index.index }].productIds" value="${row.product.number }" ${row.hortationType=='virtual'?'disabled="disabled"':'' } class="text short productId"/><input type="hidden" name="productNumbers" value="${row.product.number }"/></td>
							<td><input type="text" name="gameItems[${index.index }].score" value="${row.score }" class="text short score" ${row.hortationType=='physical'?'disabled="disabled"':'' }/></td>
							<td><input type="text" name="gameItems[${index.index }].count" value="${row.count }" class="text short count"/></td>
							<td><input type="text" name="gameItems[${index.index }].probability" value="${row.probability }" class="text short probability"/></td>
							<td><a href="javascript:void(o);" class="delete">[删除]</a></td>
						</tr>
						</c:forEach>
						</c:if>
					</tbody>
				</table>
	   			<table width="100%" class="input tabContent table">
	   				<tr>
		    			<td><textarea name="description" style="width:1000px;height:350px;">${game.description }</textarea></td>
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

<script id="calculateTemplate" type="text/x-jquery-tmpl">
<table id="calculatePanel" width="100%" style="text-align:center;" class="table">
	<thead>
		<tr class="head" style="background-color:#ececec;font-weight:bold;">
			<td width="100">奖品索引编号</td>
			<td width="200">奖品名称</td>
			<td width="75">中奖次数</td>
			<td width="75">剩余数量</td>
			<td width="75">设置概率</td>
			<td width="75">计算概率</td>
		</tr>
	</thead>
	<tbody>
		
{{each(i,item) data}}
    <tr height="30">
			<td>{{= item.hortationIndex}}</td>
			<td>{{= item.title}}</td>
			<td>{{= item.winning}}</td>
			<td>{{= item.count}}</td>
			<td>{{= item.setProbability}}</td>
			<td>{{= item.probability}}</td>
		</tr>
{{/each}}
		
	</tbody>
</table>
</script>

<script id="addItemsTemplate" type="text/x-jquery-tmpl">
<tr class="new">
	<td><input type="text" name="gameItems[{{= index}}].hortationIndex" value="{{= hortationIndex}}" class="text short hortationIndex"/></td>
	<td><input type="text" name="gameItems[{{= index}}].title" class="text short title"/></td>
	<td>
		<img src="${path }/back/images/default_no.jpg" class="hortationImage" title="点击更改图片" style="width:60px;height:60px;cursor: pointer;"/>
		<input type="hidden" name="gameItems[{{= index}}].image" value=""/>
		<input type="hidden" name="gameItems[{{= index}}].winningCount" value="0"/>
	</td>
	<td>
		<select name="gameItems[{{= index}}].hortationType" class="hortationType">
			<c:forEach var="row" items="${hortationTypes}">
			<option value="${row }"><fmt:message key="HortationType.${row}"/></option>
			</c:forEach>
		</select>
	</td>
	<td><input type="text" name="gameItems[{{= index}}].productIds" class="text short productId"/><input type="hidden" name="productNumbers" value=""/></td>
	<td><input type="text" name="gameItems[{{= index}}].score" class="text short score" disabled="disabled"/></td>
	<td><input type="text" name="gameItems[{{= index}}].count" class="text short count"/></td>
	<td><input type="text" name="gameItems[{{= index}}].probability" class="text short probability"/></td>
	<td><a href="javascript:void(o);" class="delete">[删除]</a></td>
</tr>
</script>