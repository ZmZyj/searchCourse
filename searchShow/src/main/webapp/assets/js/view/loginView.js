define(function(require){
	return {
		'init':function(){
			$("form").submit(function() {
        		var $this = $(this);
        		if ($this.attr("method") != null && $this.attr("method").toLowerCase() == "post" && $this.find("input[name='token']").size() == 0) {
        			var token = getCookie("token");
        			if (token != null) {
        				$this.append('<input type="hidden" name="token" value="' + token + '" \/>');
        			}
        		}
        	});
    		$(document).keydown(function(event){
    			  if(event.keyCode==13){
    			  	$('#login-btn').click();
    			  }
    		});
			$('#login-btn').click(function(){
				var _name=$('#username').val();
    			var _pwd=$('#password').val();
    			if(isNull(_name)){
    				$('.msg').html('用户名不能为空!');
    				$('.msg').show();
    				return;
    			}
    			if(isNull(_pwd)){
    				$('.msg').html('密码不能为空!');
    				$('.msg').show();
    				return;
    			}
    			$('form').submit();
			})
		}
	};
})