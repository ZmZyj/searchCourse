define(function(require){
	return {
		'init':function(){
			var n=$(window).height()-60;
			$('h1').html(title);
			$('section').qrcode({
				 width:n,
				 height:n,
				 text:qurl
			});
			$('section').css({
				margin:'0 auto',
				width:n+'px'
			});
		}
	};
})