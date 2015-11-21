define(function(require){
	var app=require('app');
	var template=require('template');
	
	var jo={
		page:1,
		pageSize:10,
		pageNum:0,
		name:'',
		status:''
	}
	var View={
		'getList':function(){
			$.ajax({
				type : 'POST',
				url : path + '/member/memberList.shtml',
				contentType:'application/json',
			    data:JSON.stringify(jo),
				success:function(data) {
					 if(data){
						 $('table tbody').html(template('s-list',data));
						 jo.pageNum=data.pageNum;
						 //显示分页
						 View.showPagination(jo);
						 View.bindAwrad();
						 //绑定打赏事件
						 View.showAwardBox();
						 //绑定审核通过事件
						 View.bindApproveEvent();
						 //绑定审核不通过事件
						 View.bindRejectEvent();
					 }
				}
			});
		},
		'bindAwrad':function(){
			$(".award-box .award-btn").off().click(function(){
				var members=$(this).attr('data');
				if(isNull(members)){
					alert('至少选择一个学员!');
					return;
				}
				var awardNum=$(this).parent().parent().find('#award-num').val();
				if(isNull(awardNum)){
					alert('打赏金额不能为空!');
					return;
				}
				if(!isNumber(awardNum)){
					alert('打赏金额只能为正整数!');
					return;
				}
				var o={
					target:members.split(','),
					point:awardNum
				}
				$.ajax({
					type : 'POST',
					url : path + '/member/sendPointInBatch.shtml',
					contentType:'application/json',
				    data:JSON.stringify(o),
					success:function(data) {
						 if(data&&data.success){
							 View.getList(); 
						 }else{
							 alert('打赏失败!')
						 }
					}
				});
			});
		},
		'bindApproveEvent':function(){
			$('td .approve').click(function(){
				var o={
						id:$(this).attr('data-id'),
						status:$(this).attr('data-status')
				}
				$.ajax({
					type : 'POST',
					url : path + '/member/confirmMember.shtml',
					contentType:'application/json',
					data:JSON.stringify(o),
					success:function(data) {
						if(data){
							View.getList();
						}
					}
				});
			})
		},
		'bindRejectEvent':function(){
			$('td .reject').click(function(){
				var id=$(this).attr('data-id');
				$('.confirm-box .confirm-btn').attr('data-id',id);
				View.showConfirmBox();
			})
		},
		'bindConfirmEvent':function(){
			$('.confirm-box .confirm-btn').click(function(){
				var o={
						id:$(this).attr('data-id')
				}
				$.ajax({
					type : 'POST',
					url : path + '/member/refuseMember.shtml',
					contentType:'application/json',
					data:JSON.stringify(o),
					success:function(data) {
						if(data){
							View.getList();
						}
					}
				});
			})
			$('.confirm-box .cancel-btn').click(function(){
				View.hideConfirmBox();
			})
		},
		'showAwardBox':function(){
			$('td .award').click(function(){
				var id=$(this).attr('data-id');
				if($(this).parent().find('.award-box').is(':visible')){
					$('.award-box').slideUp('fast');
				}else{
					$('.award-box').slideUp('fast');
					$(this).parent().find('.award-box').slideDown('fast');
				}
			})
		},
		'showStatus':function(){
			$('th.status').click(function(){
				if($('th.status ul').is(':visible')){
					$('th.status ul').slideUp();
				}else{
					$('th.status ul').slideDown();
				}
			})
			$('th.status ul li').click(function(){
				jo.name='';
				jo.status=$(this).attr('data');
				View.getList();
			})
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
						 View.getList();
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
		'showConfirmBox':function(id){
			$('.confirm-box').animate({top:'50%'},400);
		},
		'hideConfirmBox':function(id){
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
			 View.getList();
			 View.showStatus();
			 View.bindConfirmEvent();
			 /*返回问题列表*/
			 $('.toolbar button').click(function(){
				 app.navigateTo('/index.shtml');
			 })
			 /*检索问题*/
			 $('.search-box #keyword').keyup(function(event){
				 if(event.keyCode==13){
					 jo.name=$(this).val();
					 jo.page=1;
					 View.getList();
				 }
			 })
			 /*批量打赏*/
			 $('#batch-award').click(function(){
				 if($('.toolbar .award-box').is(':visible')){
					 $('.toolbar .award-box').slideUp();
				 }else{
					var arr=[];
					$('table tbody input:checked').each(function(){
						arr.push($(this).val());
					})
					$('.award-box').slideUp('fast');
					$('.toolbar .award-box').slideDown('fast');
					$('.toolbar .award-box #user-count').text(arr.length);
					$('.toolbar .award-box .award-btn').attr('data',arr.join(','));
				 }
			 })
			 
			 $('.toolbar #award-num').blur(function(){
				 var expend=$(this).val()*$('.toolbar .award-box #user-count').text();
				 $('.toolbar .award-box #expend').text(expend);
			 })
			 
			 $('.update-pwd').click(function(){
				 View.showUpdateBox();
			 })
			 $('.save-pwd-btn').click(function(){
				 View.updatePwd();
			 })
			 $('.close-pwd-btn').click(function(){
				 View.hideUpdateBox();
			 })
			 
		}
	};
	return View;
})