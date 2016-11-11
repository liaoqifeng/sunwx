;(function($, window, undefined) {
	$.extend($.fn, {
	swipeButton: function(option){
	    var defaults = { width:100,height:50,duration:100,label:'删除',callback:null};
	    var opts = $.extend({},defaults , option);
	    var $this = $(this).css("overflow-x","hidden");
	    var $swipe = $this.find(".wx-swipe").width($this.width()+opts.width);
	    var $btn = $('<button class="mui-btn mui-btn-negative" style="border-radius: 0px;float:right;height: '+opts.height+'px;width:'+opts.width+'px;border:0px;margin-top:'+(($swipe.height()-opts.height)/2)+'px;">'+opts.label+'</button>').prependTo($swipe);
	    $btn.click(function(){
	    	if(opts.callback){
	    		opts.callback($this);
	    	}
	    });
	    var brower = wxBrower.versions();
	    var data = {isDrag : true,x : 0, endX:0, preX:0};
	    $this[0].addEventListener("touchstart",function(e){
	    	if(brower instanceof Object){
		    	if(brower.type == "Android"){
		    		document.addEventListener('touchmove', function (event) {
		    		    event.preventDefault();
		    		}, false);
		    	}
		    }
	    	var touch = event.changedTouches[0];
	    	data = {isDrag : true,x : touch.pageX};
			var drop = function(){
				var sl = $this[0].scrollLeft;
				if(Math.abs(data.endX)>=opts.width/3){
					if(data.endX > 0){
						var timer = setInterval(function(){
							sl += 3;
							if(sl > opts.width){
								clearInterval(timer);
								sl = opts.width;
							}
							$this[0].scrollLeft=sl;
						},10);
						data.preX=opts.width;
					}else{
						if(data.preX == 0) return false;
						var timer = setInterval(function(){
							sl -= 3;
							if(sl <= 0){
								clearInterval(timer);
								sl = 0;
							}
							$this[0].scrollLeft=sl;
						},10);
						data.endX=0;
						data.preX=0;
					}
				}
				data.isDrag = false,
				$this[0].removeEventListener("touchmove", function () {}, false);
				if(brower instanceof Object){
			    	if(brower.type == "Android"){
			    		document.removeEventListener('touchmove', function (event) {}, false);
			    	}
			    }
			}
			$this[0].addEventListener("touchmove",function(me){
				var movetouch = me.touches[0];
				if(data.isDrag){
					var x = movetouch.pageX;
					data.endX = data.x - x;
				}
			});
			$this[0].addEventListener("touchend",function(e){
				drop();
			});
		});
	    return this;
	}
	});
})(window.jQuery || window.Zepto, window);