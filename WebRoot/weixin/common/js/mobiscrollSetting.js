function MobiScrollSetting() {
};

MobiScrollSetting.prototype = {
	datetime : {
		preset : 'datetime',
		minDate : new Date(1990, 1, 1),
		maxDate : new Date(2020, 12, 31),
		theme : 'android-ics light',
		display : 'bottom',
		mode : 'scroller',
		dateOrder : 'yymmdd',
		setText : '确定', // 确认按钮名称
		cancelText : '取消', // 取消按钮名称
		dateFormat : 'yy-mm-dd',
		timeWheels : 'HHii',
		timeFormat : 'HH:ii ',
		stepMinute : 5,
		lang : "zh"

	},

	select : {
		inputClass : "msSelect",
		theme : 'android-ics light',
		mode : "scroller",
		lang : "zh",
		label : "年龄",
		display : 'bottom'
	}
};

var msSetting = new MobiScrollSetting();