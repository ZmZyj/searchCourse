// 令牌
$(document).ajaxSend(function(event, request, settings) {
	if (!settings.crossDomain && settings.type != null && settings.type.toLowerCase() == "post") {
		var token = getCookie("token");
		if (token != null) {
			request.setRequestHeader("token", token);
		}
	}
});
function getCookie(name) {
	if (name != null) {
		var value = new RegExp("(?:^|; )"
				+ encodeURIComponent(String(name)) + "=([^;]*)")
				.exec(document.cookie);
		return value ? decodeURIComponent(value[1]) : null;
	}
}
//显示加载器  
function showLoader() {  
    //显示加载器.for jQuery Mobile 1.2.0  
    $('.ui-loader').show()  
}  
  
//隐藏加载器.for jQuery Mobile 1.2.0  
function hideLoader()  
{  
    //隐藏加载器  
	$('.ui-loader').hide();  
}  
