(function(require, window) {
	window.VERSION=Date.parse(new Date());
	
	//require config
    require.config({　　　　
        baseUrl: 'assets/js',
        urlArgs:"ver="+ VERSION,
        paths: {
        	//libs　
        	'jquery':'lib/jquery.min',　　　　　
            'template': 'lib/template',
            //view
            'loginView':'view/loginView',
            'mainView':'view/mainView',
            'reviewView':'view/reviewView',
            'questionView':'view/questionView',
            'resultView':'view/resultView',
            'mobileView':'view/mobileView',
            'omobileView':'view/omobileView',
            'codeView':'view/codeView',
            'registerView':'view/registerView'
        }　　
    });
	
    //获取绝对路径
	window.BASE = function(path){
		if (!window.location.origin)
   			window.location.origin = window.location.protocol+"//"+window.location.host;

		path = path.replace(/^\//,'');
		return window.location.origin+'/'+path;
	};
    
	require(['init'],function(Init){
		Init.init();
	})
})(require, window);
