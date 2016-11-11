;(function($, window, undefined) {
 $.fn.loading = function(option){
	 var defaults = { label:"加载中...",lock:true};
	 var opts = $.extend({}, defaults  , option);
	 var win = $(window);
	 var left = (win.width() - 80) / 2;
     var top = (win.height() - 80) / 2 - 50;
     var $lock = $('<div class="wxLoading-mask" style="z-index:999998"></div>');
     if (opts.lock){
    	 $lock.appendTo('body');
     }
	 var templates = '<div class="wxLoading" style="display:none;z-index: 999999;left:'+left+'px;top:'+top+'px;">'+
		'<div class="wxLoading-wrap">'+
		'<div class="wxLoading-header-load"><img src="../../weixin/common/svg/circles.svg" width="28"></div>'+
		'<div class="wxLoading-content">加载中...</div></div></div>';
	 var $loading = $(templates).appendTo($(this)).show();
	 return {
		removeLoading:function(){
		 if (opts.lock){$lock.remove();}$loading.remove();
	 	}
	 };
 };
})(window.jQuery || window.Zepto, window);
