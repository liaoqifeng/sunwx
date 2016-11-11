;(function($){
	$.extend($.fn, {
	swipeButton: function(option){
	    var defaults = { width:100,duration:100,label:'删除',callback:null};
	    var opts = $.extend({},defaults , option);
	    var $this = $(this);
	    var $btn = $('<button class="mui-btn" style="right: -'+opts.width+'px;position: absolute;z-index: 1;height: 100%;width: '+opts.width+'px;">'+opts.label+'</button>').insertAfter($this);
	    $btn.click(function(){
	    	if(opts.callback){
	    		opts.callback($this);
	    	}
	    });
	    var brower = wxBrower.versions();
	    $this[0].addEventListener("touchstart",function(e){
	    	if(brower instanceof Object){
		    	if(brower.type == "Android"){
		    		document.addEventListener('touchmove', function (event) {
		    		    event.preventDefault();
		    		}, false);
		    	}
		    }
	    	var touch = event.changedTouches[0];
			var data = $this.data("params");
			if(data == undefined || data == null){
				data = {isDrag : true,x : touch.pageX,y : touch.pageY, endX:0, preX:0};
			}else{
				data = JSON.parse(data);
			}
			alert($this.width());
			data.isDrag=true;
			data.x = touch.pageX;
			var drop = function(){
				if(Math.abs(data.endX)>=opts.width/2){
					if(data.endX > 0){
						$this.animate({"margin-left":-opts.width},opts.duration);
						$btn.animate({"right":0},opts.duration);
						data.preX=-opts.width;
					}else{
						$this.animate({"margin-left":0},opts.duration);
						$btn.animate({"right":-opts.width},opts.duration);
						data.endX=0;
						data.preX=0;
					}
				}else{
					$this.animate({"margin-left":0},opts.duration);
					$btn.animate({"right":-opts.width},opts.duration);
					data.endX=0;
					data.preX=0;
				}
				data.isDrag = false,
				$this.data("params",JSON.stringify(data));
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
					var left=parseFloat($this.css("margin-left"));
					if(data.preX != 0){
						if(data.endX > 0){
						}else{
							var l = data.preX+Math.abs(data.endX);
							$btn.css("right",-(opts.width+l)+"px");
							$this.css("margin-left",l);
						}
					}else{
						if(data.endX > 0){
							if(data.endX <= opts.width){
								$btn.css("right",(-opts.width+data.endX)+"px");
								$this.css("margin-left",-data.endX);
								console.log("endX:"+data.endX+"    "+"right:"+(-opts.width+data.endX)+"    left:"+(-data.endX));
							}
						}
					}
				}
			});
			$this[0].addEventListener("touchend",function(e){
				drop();
			});
		});
	    return this;
	}
	});
})(Zepto);