/**
 * page转场动画
 * 可自定义css动画
 */
J.Transition = (function($){
    var isBack,$current,$target,transitionName,
        animationClass = {
        //[[currentOut,targetIn],[currentOut,targetIn]]
        slide : [['slideLeftOut','slideLeftIn'],['slideRightOut','slideRightIn']],
        cover : [['','slideLeftIn'],['slideRightOut','']],
        slideUp : [['','slideUpIn'],['slideDownOut','']],
        slideDown : [['','slideDownIn'],['slideUpOut','']],
        popup : [['','scaleIn'],['scaleOut','']]
        };

    var _doTransition = function(){
        //触发 beforepagehide 事件
        $current.trigger('beforepagehide',[isBack]);
        //触发 beforepageshow 事件
        $target.trigger('beforepageshow',[isBack]);
        var c_class = transitionName[0]||'empty' ,t_class = transitionName[1]||'empty';
        $current.bind('webkitAnimationEnd.jingle', _finishTransition).addClass('anim '+ c_class);
        $target.addClass('anim animating '+ t_class);
    }
    var _finishTransition = function() {
        $current.off('webkitAnimationEnd.jingle');
        $target.off('webkitAnimationEnd.jingle');
        //reset class
        $current.attr('class','');
        $target.attr('class','active');
        //add custom events
        !$target.data('init') && $target.trigger('pageinit').data('init',true);
        !$current.data('init') && $current.trigger('pageinit').data('init',true);
        //触发pagehide事件
        $current.trigger('pagehide',[isBack]);
        //触发pageshow事件
        $target.trigger('pageshow',[isBack]);

        $current.find('article.active').trigger('articlehide');
        $target.find('article.active').trigger('articleshow');
        $current = $target = null;//释放
    }

    /**
     * 执行转场动画，动画类型取决于目标page上动画配置(返回时取决于当前page)
     * @param current 当前page
     * @param target  目标page
     * @param back  是否为后退
     */
    var run = function(current,target,back){
        //关闭键盘
        $(':focus').trigger('blur');
        isBack = back;
        $current = $(current);
        $target = $(target);
        var type = isBack?$current.attr('data-transition'):$target.attr('data-transition');
        type = type|| J.settings.transitionType;
        //后退时取相反的动画效果组
        transitionName  = isBack ? animationClass[type][1] : animationClass[type][0];
        _doTransition();
    }

    /**
     * 添加自定义转场动画效果
     * @param name  动画名称
     * @param currentOut 正常情况下当前页面退去的动画class
     * @param targetIn   正常情况下目标页面进入的动画class
     * @param backCurrentOut 后退情况下当前页面退去的动画class
     * @param backCurrentIn 后退情况下目标页面进入的动画class
     */
    var addAnimation = function(name,currentOut,targetIn,backCurrentOut,backCurrentIn){
        if(animationClass[name]){
            console.error('该转场动画已经存在，请检查你自定义的动画名称(名称不能重复)');
            return;
        }
        animationClass[name] = [[currentOut,targetIn],[backCurrentOut,backCurrentIn]];
    }
    return {
        run : run,
        add : addAnimation
    }

})(J.$);
/**
 * 常用工具类
 */
J.Util = (function($){
    var parseHash = function(hash){
        var tag,query,param={};
        var arr = hash.split('?');
        tag = arr[0];
        if(arr.length>1){
            var seg,s;
            query = arr[1];
            seg = query.split('&');
            for(var i=0;i<seg.length;i++){
                if(!seg[i])continue;
                s = seg[i].split('=');
                param[s[0]] = s[1];
            }
        }
        return {
            hash : hash,
            tag : tag,
            query : query,
            param : param
        }
    }

    /**
     * 格式化date
     * @param date
     * @param format
     */
    var formatDate = function(date,format){
        var o =
        {
            "M+" : date.getMonth()+1, //month
            "d+" : date.getDate(),    //day
            "h+" : date.getHours(),   //hour
            "m+" : date.getMinutes(), //minute
            "s+" : date.getSeconds(), //second
            "q+" : Math.floor((date.getMonth()+3)/3),  //quarter
            "S" : date.getMilliseconds() //millisecond
        }
        if(/(y+)/.test(format))
            format=format.replace(RegExp.$1,(date.getFullYear()+"").substr(4 - RegExp.$1.length));
        for(var k in o)
            if(new RegExp("("+ k +")").test(format))
                format = format.replace(RegExp.$1,RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
        return format;
    }

    return {
        parseHash : parseHash,
        formatDate : formatDate
    }

})(J.$);
/**
 * 欢迎界面，可以制作酷炫吊炸天的欢迎界面哦
 * @module Welcome
 */
J.Welcome = (function($){
    /**
     * 显示欢迎界面
     */
    var showWelcome = function(){
        $.ajax({
            url : J.settings.basePagePath+'welcome.html',
            timeout : 5000,
            async : false,
            success : function(html){
                //添加到dom树中
                $('body').append(html);
                new J.Slider({
                    selector : '#jingle_welcome',
                    onAfterSlide  : J.settings.welcomeSlideChange
                });
            }
        })
    }
    /**
     * 关闭欢迎界面
     */
    var hideWelcome = function(){
        J.anim('#jingle_welcome','slideLeftOut',function(){
            $(this).remove();
            window.localStorage.setItem('hasShowWelcome',true);
        })
    }

    return {
        show : showWelcome,
        hide : hideWelcome
    }
})(J.$);
(function($){
    /*
     * alias func
     * 简化一些常用方法的写法
     ** /
    /**
     * 完善zepto的动画函数,让参数变为可选
     */
    J.anim  =  function(el,animName,duration,ease,callback){
        var d, e,c;
        var len = arguments.length;
        for(var i = 2;i<len;i++){
            var a = arguments[i];
            var t = $.type(a);
            t == 'number'?(d=a):(t=='string'?(e=a):(t=='function')?(c=a):null);
        }
        $(el).animate(animName,d|| J.settings.transitionTime,e||J.settings.transitionTimingFunc,c);
    }
    /**
     * 显示loading框
     * @param text
     */
    J.showMask = function(text){
        J.Popup.loading(text);
    }
    /**
     * 关闭loading框
     */
    J.hideMask = function(){
        J.Popup.close(true);
    }
    /**
     *  显示消息
     * @param text
     * @param type toast|success|error|info
     * @param duration 持续时间，为0则不自动关闭
     */
    J.showToast = function(text,type,duration){
        type = type || 'toast';
        J.Toast.show(type,text,duration);
    }
    /**
     * 关闭消息提示
     */
    J.hideToast = function(){
        J.Toast.hide();
    }
    J.alert = function(title,content){
        J.Popup.alert(title,content);
    }
    J.confirm = function(title,content,okCall,cancelCall){
        J.Popup.confirm(title,content,okCall,cancelCall);
    }
    /**
     * 弹出窗口
     * @param options
     */
    J.popup = function(options){
        J.Popup.show(options);
    }
    /**
     * 关闭窗口
     */
    J.closePopup = function(){
        J.Popup.close();
    }
    /**
     * 带箭头的弹出框
     * @param html [可选]
     * @param pos [可选]  位置
     * @param arrowDirection [可选] 箭头方向
     * @param onShow [可选] 显示之前执行
     */
    J.popover = function(html,pos,arrowDirection,onShow){
        J.Popup.popover(html,pos,arrowDirection,onShow);
    }
    /**
     * 自动渲染模板并填充到页面
     * @param containerSelector 欲填充的容器
     * @param templateId 模板ID
     * @param data 数据源
     * @param type [可选] add|replace
     */
    J.tmpl = function(containerSelector,templateId,data,type){
        J.Template.render(containerSelector,templateId,data,type);
    }
})(J.$);