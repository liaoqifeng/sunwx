;(function($){
	$.extend($.fn, {
	swipeDelete: function(option){
	    var defaults = { width:100,duration:500 };
	    var opts = $.extend({},defaults , option);
	    var $this = $(this);
	    var brower = wxBrower.versions();
	    $this[0].addEventListener("touchstart",function(e){
	    	if(brower instanceof Object){
		    	if(brower.type == "Android"){
		    		var pd = function (e) { e.preventDefault(); }
		    		document.addEventListener('touchmove', pd , false);
		    		setTimeout(function(){
		    			document.removeEventListener('touchmove', pd, false);
			    	},opts.duration);
		    	}
		    }
	    	var touch = event.changedTouches[0];
			var data = $this.data("params");
			if(data == undefined || data == null){
				data = {isDrag : true,x : touch.pageX,y : touch.pageY, endX:0, preX:0};
			}else{
				data = JSON.parse(data);
			}
			data.isDrag=true;
			data.x = touch.pageX;
			var drop = function(){
				if(Math.abs(data.endX)>=opts.width/2){
					if(data.endX > 0){
						$this.animate({"left":-opts.width},opts.duration);
						data.preX=-opts.width;
					}else{
						$this.animate({"left":0},opts.duration);
						data.endX=0;
						data.preX=0;
					}
				}else{
					$this.animate({"left":0},opts.duration);
					data.endX=0;
					data.preX=0;
				}
				data.isDrag = false,
				$this.data("params",JSON.stringify(data));
				$this[0].removeEventListener("touchmove", function () {}, false);
			}
			$this[0].addEventListener("touchmove",function(me){
				var movetouch = me.touches[0];
				if(data.isDrag){
					var x = movetouch.pageX;
					data.endX = data.x - x;
					if(data.preX != 0){
						if(data.endX > 0){
						}else{
							$this.css("left",data.preX+Math.abs(data.endX));
						}
					}else{
						if(data.endX > 0){
							if(data.endX <= opts.width){
								$this.css("left",(-data.endX));
							}
						}else{
							if(left <= 0){
								$this.css("left",(data.endX));
							}else{
								$this.css("left","auto");
							}
						}
					}
				}
			})
			$this[0].addEventListener("touchend",function(e){
				drop();
			});
		});
	    
	    return this;
	}
	});
})(Zepto);