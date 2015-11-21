define(function(require){
	var app=require('app');
	var template=require('template');
	
	var jo={
		page:1,
		pageSize:10,
		pageNum:0,
		q:''
	}
	var mainView={
		'getList':function(){
			$.ajax({
				type : 'POST',
				url : path + '/list.shtml',
				contentType:'application/json',
			    data:JSON.stringify(jo),
				success:function(data) {
					 if(data){
						 $('table tbody').html(template('q-list',data));
						 jo.pageNum=data.pageNum;
						 /*显示分页*/
						 mainView.showPagination(jo);
					 }
				}
			});
		},
		'showPagination':function(jo){
			/*显示分页*/
			 $('#light-pagination').pagination({
				 	currentPage:jo.page,
					pages: jo.pageNum,
					cssStyle: 'light-theme',
					prevText: '<<',
					nextText: '>>',
					onPageClick: function(pageNumber, event) {
						 jo.page=pageNumber;
						 mainView.getList();
					}
			 });
		},
		'showUpdateBox':function(jo){
			$('.update-box').animate({top:'50%'},400);
		},
		'hideUpdateBox':function(){
			$('.update-box').animate({top:'-300px'},400,function(){
				$('#oldPwd').val('');
				$('#newPwd').val('');
				$('#repeatPwd').val('');
				$('.msg').html('');
			});
		},
		'showConfirmBox':function(){
			$('.confirm-box').animate({top:'50%'},400);
		},
		'hideConfirmBox':function(){
			$('.confirm-box').animate({top:'-30%'},400);
		},
		'updatePwd':function(){
			var o={
				oldPwd:$('#oldPwd').val(),
				newPwd:$('#newPwd').val(),
				repeatPwd:$('#repeatPwd').val()
			}
			if(isNull(o.oldPwd)){
				$('.update-box .msg').html('原密码不能为空!');
				return;
			}
			if(isNull(o.newPwd)){
				$('.update-box .msg').html('新密码不能为空!');
				return;
			}
			if(isNull(o.repeatPwd)){
				$('.update-box .msg').html('请再输入一次新密码!');
				return;
			}
			if(o.newPwd!=o.repeatPwd){
				$('.update-box .msg').html('两次输入的新密码不一致!');
				return;
			}
			$.ajax({
				type : 'POST',
				url : path + '/main/modifyPassword.shtml',
				contentType:'application/json',
			    data:JSON.stringify(o),
				success:function(data) {
					 if(data&&data.success){
						 $('.update-box .msg').html('修改密码成功!');
					 }else{
						 $('.update-box .msg').html(data.message);
					 }
				}
			});
		},
		'init':function(){
			$('.search-box #keyword').val('');
			 mainView.getList();
			 /*检索问题*/
			 $('.search-box #keyword').keyup(function(event){
				 if(event.keyCode==13){
					 jo.q=$(this).val();
					 jo.page=1;
					 mainView.getList();
				 }
			 })
			 
		}
	};
	return mainView;
})