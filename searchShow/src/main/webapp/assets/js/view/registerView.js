define(function(require){
	var app=require('app');
	var template=require('template');
	var mobileView={
		'closeDialog':function(){
			$('#alert .back-btn').click(function(){
		        $('.ui-dialog').dialog('close');
		    })
		},
		'register':function(){
			$('#register-page .sub-btn').click(function(){
				var o={
						openid:openid,
						name:$('#register-page #name').val(),
						mid:$('#register-page #mid').val()
				}
				if(isNull(o.name)){
					$('#register-page .msg').html('真实姓名不能为空！')
					return;
				}
				if(isNull(o.mid)){
					$('#register-page .msg').html('社员号不能为空！')
					return;
				}
				
				$.ajax({
					type : "POST",
					url : path + '/pub/applyMember.shtml',
					contentType:"application/json",
				    data:JSON.stringify(o),
					success : function(data) {
						if(data&&data.success){
							$('#alert .redirect-btn').show();
							$('#alert .back-btn').hide();
							$('#alert h3').html('感谢您的注册！');
							$('#alert p').html('注册审核通过后您将拥有葵花币使用权限');
							$.mobile.changePage('#alert');
						}else{
							$('#alert .redirect-btn').hide();
							$('#alert .back-btn-btn').show();
							$('#alert h3').html('注册提交失败！');
							$('#alert p').html('');
							$.mobile.changePage('#alert');
						}
					}
				});
			})
			
		},
		'initWx':function(){
			var o={
					url:window.location.href
			}
			$.ajax({
				type : "POST",
				url : path + '/pub/getSign.shtml',
				contentType:"application/json",
				data:JSON.stringify(o),
				success : function(json) {
					wx.config({
					    debug: false,
					    appId: json.appid,
					    timestamp: json.timestamp,
					    nonceStr: json.nonceStr,
					    signature: json.signature,
					    jsApiList: [
					      'hideOptionMenu'
					    ]
					});
					wx.ready(function(){
						wx.hideOptionMenu();
					})
				},
				error:function(){
					alert('error')
				}
			});
		},
		'init':function(){
			mobileView.initWx();
			mobileView.closeDialog();
			mobileView.register();
		}
	};
	return mobileView;
})