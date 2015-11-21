define(function(require){
	return {
		"init":function(){
			var href=window.location.href;
			if(href.indexOf('/login')>-1){
				var loginView=require('loginView');
				loginView.init();
			}else if(href.indexOf('/index')>-1){
				var mainView=require('mainView');
				mainView.init();
			}else if(href.indexOf('/review')>-1){
				var reviewView=require('reviewView');
				reviewView.init();
			}else if(href.indexOf('/question')>-1){
				var questionView=require('questionView');
				questionView.init();
			}else if(href.indexOf('/result')>-1){
				var resultView=require('resultView');
				resultView.init();
			}else if(href.indexOf('/mobile')>-1){
				var mobileView=require('mobileView');
				mobileView.init();
			}else if(href.indexOf('/oldmobile')>-1){
				var omobileView=require('omobileView');
				omobileView.init();
			}else if(href.indexOf('/qrcode')>-1){
				var codeView=require('codeView');
				codeView.init();
			}else if(href.indexOf('/register')>-1){
				var registerView=require('registerView');
				registerView.init();
			}
		}
	};
});