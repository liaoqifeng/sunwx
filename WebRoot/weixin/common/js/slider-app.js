;(function($, window, undefined) {
	$.extend($.fn, {
		slider: function(option){
			var brower = wxBrower.versions();
		    var defaults = {duration:6000};
		    var opts = $.extend({},defaults , option);
		    var $this = $(this);
		    var $indicators = $this.find(".mui-indicator");
		    var $items = $this.find(".mui-slider-item");
		    var index = 0;
		    var timer = null;
		    var calTimer = function(){timer = setInterval(function(){ slider("right");}, opts.duration);}
		    var slider = function(direction){
		    	window.clearInterval(timer);
		    	$items.hide();
		    	$indicators.removeClass("mui-active");
		    	if(direction == "left"){index--;if(index < 0){index = $indicators.size()-1;}
		    	}else{index++;if(index > $indicators.size()-1){index = 0;}}
	    		$items.eq(index).show();
		    	$indicators.eq(index).addClass("mui-active");
		    	
		    	calTimer();
		    }
		    calTimer();
        var startPosition, endPosition, deltaX, deltaY, moveLength;
        var pd = function (e) { e.preventDefault(); }
        $this[0].addEventListener('touchstart', function (e) {
            var touch = e.touches[0];
            startPosition = {
                x: touch.pageX,
                y: touch.pageY
            }
    		document.addEventListener('touchmove', pd , false);
        });

        $this[0].addEventListener('touchmove', function (e) {
            var touch = e.touches[0];
            endPosition = {
                x: touch.pageX,
                y: touch.pageY
            }
            deltaX = endPosition.x - startPosition.x;
            deltaY = endPosition.y - startPosition.y;
        });
        $this[0].addEventListener('touchend', function (e) {
        	if(deltaX > 0){
				slider("left");
			}
			if(deltaX<0){
				slider("right");
			}
			setTimeout(function(){
    			document.removeEventListener('touchmove', pd, false);
	    	},500);
        });
		    return this;
		}
	});
})(window.Zepto, window);