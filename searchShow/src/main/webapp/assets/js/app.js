
define(function(require){
	return {
		'navigateTo':function(url){
			if(url){
				location.href = BASE(url);
			}
		}
	};
});




