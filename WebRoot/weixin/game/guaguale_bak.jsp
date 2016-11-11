<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<% request.setAttribute("path",request.getContextPath()); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width,user-scalable=no,initial-scale=1,maximum-scale=1" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>刮刮乐</title>
	<link rel="stylesheet" type="text/css" href="${path }/weixin/common/css/zepto.alert.css">
	<script type="text/javascript"  src="${path}/weixin/common/js/jquery.1.11.0.min.js"></script>
	<script type="text/javascript">//if(!isWeiXin()){window.location.href="${path}/weixin/wxbrowser.jsp";}</script>
</head>
<body style="background: #B81F24;">
	<div id="bg" style="background: url(${path}/weixin/common/images/guaguaka_bg.png);background-size: 100% 100%;">
		<div id="bg2" style="width:295px;height:195px;margin:0 auto;">
			<img id="bg2_img" src="${path}/weixin/common/images/s_title.png" width="295" height="100" style="position: absolute;top:50px;">
		</div>
		<div id="gua1" style="width:295px;margin:0 auto;position: relative;" >
			<img id="gua1_img" src="${path}/weixin/common/images/gua_image.png" style="width:300px;height:160px;position: absolute;">
			<div style="width: 284px;height: 144px;left:8px;top:8px;background: #cacaca;position: absolute;">
				<div id="gua_body" style="width: 100%;height: 100%;display:inline-block; position:relative;"></div>
			</div>
		</div>
		<div id="notify" style="width:300px;height:101px;margin:200px auto 0px auto;background: url(${path}/weixin/common/images/notice_bg.png);background-size: 300px 101px;">
			<div id="nImg_div" style="position: absolute;color:white;font-size: 17px;font-family: '黑体'" align="center">
				<div style="width:245px;height:101px;padding:10px 0px 0px 50px;" align="left">${game.rules }</div>
			</div>
		</div>
	</div>
<script type="text/javascript" src="${path }/weixin/common/js/zepto.alert.js" ></script>
<script type="text/javascript" src="${path}/weixin/common/js/wScratchPad.min.js""></script>
<script type="text/javascript">
	document.addEventListener('touchmove', function (e) { e.preventDefault(); } , false);
	var useragent = window.navigator.userAgent.toLowerCase();
	var $gua_body = $("#gua_body"); var click = false; var hortationItem = null; var isHortation=false;
	$gua_body.wScratchPad({
      bg: '#FFEEC3',
      fg: '#9C9C9C',
      size : 40,
      scratchMove: function (e, percent) {
		if (useragent.indexOf("android 4") > 0){
			$gua_body.css("color", "rgb(50,50,50)");
			if ($gua_body.css("color").indexOf("51") > 0) {
				$gua_body.css("color", "rgb(50,50,50)");
			} else if($gua_body.css("color").indexOf("50") > 0) {
				$gua_body.css("color", "rgb(51,51,51)");
			}
		}                                                                               
	      var $img=$gua_body.find("img");
    	  if(!isHortation){
    		  $img.hide();
    		  $.ajax({
  				url: "${path}/weixin/member/game/lottery.shtml?"+Math.random(),type: "POST",dataType: "json",cache: false,
  				beforeSend:function(){},
  				data:{id:${game.id}},
  				success: function(data) {
  					if(data.type == "success"){
  						hortationItem = $.parseJSON(data.content); var result = $.parseJSON(hortationItem.result);
  						if(result.image == null || result.image == ""){$('<div class="tip_text" style="position: absolute; width: 100%; height: 100%;line-height: 100px; display: block;text-align:center;"><h2>'+result.title+'</h2><div>').prependTo($gua_body);}
  						else{$img.show().attr("src",result.image);}
  	  				}else{$.dialog({content : data.content,title : 'ok',width:250,ok : function() {}});}
  				},
  				complete:function(){},
  				error:function(event, request, settings){alert("${fn:call('Common.net.networkNotAllowed')}");}
  			});
    		  isHortation=true;
  		}
        if (percent > 35 && !click) {
            var t = this; t.clear(); click=true;
          $.dialog({content :hortationItem.message,title : 'ok',width:250,okText: '再刮一次',cancelText: '取消',ok : function() {
        	  click=false;t.reset();isHortation=false;$gua_body.find(".tip_text").remove();$img.hide();hortationItem = null;
          },cancel:function(){}});
        }
      }
    });
</script>
</body>
</html>
