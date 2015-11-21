define(function(require){
	var app=require('app');
	var template=require('template');
    var BALANCE=0;
	var mobileView={
		'closeDialog':function(){
			$('#alert .back-btn').click(function(){
		        $('.ui-dialog').dialog('close');
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
		'showKeywordQ':function(){
			$.mobile.changePage('#keyword-page');
			$.ajax({
				type : "POST",
				url : path + '/pub/questionDetail.shtml',
			    data:'id='+qid,
				success : function(data) {
					 if(data&&data.success){
						 $('#keyword-page [data-role="content"]').html(template('keyword-content',data)).trigger('create');
						 $('#keyword-page .sub-btn').click(function(){
								var o={
										qId:qid,
										openid:openid,
										content:[]
								}
								$('#keyword-page input[type="text"]').each(function(){
									o.content.push($(this).val());
								})
								$.ajax({
									type : "POST",
									url : path + '/pub/answer.shtml',
									contentType:"application/json",
								    data:JSON.stringify(o),
									success : function(data) {
										if(data&&data.success){
											$('#alert h3').html('提交成功!');
											$.mobile.changePage('#alert');
										}else{
											if(data&&data.message){
												$('#alert h3').html(data.message);
												$.mobile.changePage('#alert');
											}
										}
									}
								});
							})
					 }
				}
			});
			
		},
		'showVoteQ':function(){
			$.mobile.changePage('#vote-page');
			$.ajax({
				type : "POST",
				url : path + '/pub/questionDetail.shtml',
				data:'id='+qid,
				success : function(data) {
					if(data&&data.success){
						var checkNum=data.question.content.checkNum;
						$('#vote-page [data-role="content"]').html(template('vote-content',data)).trigger('create');
						$('#vote-page .answer').click(function(){
							if($(this).hasClass('checked')){
								$(this).removeClass('checked');
								$(this).addClass('unchecked');
							}else{
								$(this).removeClass('unchecked');
								$(this).addClass('checked');
							}
						})
						
						$('#vote-page .sub-btn').click(function(){
							var o={
									qId:qid,
									openid:openid,
									content:[]
							}
							if($('#vote-page .checked').length==0){
								$('#alert h3').html('请至少选择一个选项!');
								$.mobile.changePage('#alert');
								return;
							}
							var length=$('#vote-page .checked').length;
							if(length>checkNum){
								$('#alert h3').html('最多只能选择'+checkNum+'项!');
								$.mobile.changePage('#alert');
								return;
							}
							$('#vote-page .checked').each(function(){
								o.content.push($(this).attr('data'));
							})
							$.ajax({
								type : "POST",
								url : path + '/pub/answer.shtml',
								contentType:"application/json",
								data:JSON.stringify(o),
								success : function(data) {
									if(data&&data.success){
										$('#alert h3').html('提交成功!');
										$.mobile.changePage('#alert');
									}else{
										if(data&&data.message){
											$('#alert h3').html(data.message);
											$.mobile.changePage('#alert');
										}
									}
								}
							});
						})
					}
				}
			});
			
		},
		'showCalculateQ':function(){
			$.mobile.changePage('#calculate-page');
			$.ajax({
				type : "POST",
				url : path + '/pub/questionDetail.shtml',
				data:'id='+qid,
				success : function(data) {
					if(data&&data.success){
						$('#calculate-page [data-role="content"]').html(template('calculate-content',data)).trigger('create');
						$('#calculate-page .sub-btn').click(function(){
							var o={
									qId:qid,
									openid:openid,
									content:[]
							}
							var num=$('#calculate-page input[type="text"]').val();
							if(isNaN(num)){
								$('#alert h3').html('请输入数字!');
								$.mobile.changePage('#alert');
								return;
							}else if(num>9999999999999){
								$('#alert h3').html('您填写的数字不能超过9999999999999!');
								$.mobile.changePage('#alert');
								return;
							}
							o.content.push(num);
							$.ajax({
								type : "POST",
								url : path + '/pub/answer.shtml',
								contentType:"application/json",
								data:JSON.stringify(o),
								success : function(data) {
									if(data&&data.success){
										$('#alert h3').html('提交成功!');
										$.mobile.changePage('#alert');
									}else{
										if(data&&data.message){
											$('#alert h3').html(data.message);
											$.mobile.changePage('#alert');
										}
									}
								}
							});
						})
					}
				}
			});
		},
		'showTextQ':function(){
			$.mobile.changePage('#text-page');
			$.ajax({
				type : "POST",
				url : path + '/pub/questionDetail.shtml',
				data:'id='+qid,
				success : function(data) {
					if(data&&data.success){
						$('#text-page [data-role="content"]').html(template('text-content',data)).trigger('create');
						$('#text-page .sub-btn').click(function(){
							var o={
									qId:qid,
									openid:openid,
									name:'',
									content:[]
							}
							o.name=$('#text-page .submitter').val();
							o.content.push($('#text-page .answer').val());
							$.ajax({
								type : "POST",
								url : path + '/pub/answer.shtml',
								contentType:"application/json",
								data:JSON.stringify(o),
								success : function(data) {
									if(data&&data.success){
										$('#alert h3').html('提交成功!');
										$.mobile.changePage('#alert');
									}else{
										if(data&&data.message){
											$('#alert h3').html(data.message);
											$.mobile.changePage('#alert');
										}
									}
								}
							});
						})
					}
				}
			});
		},
		'integralDetail':function(){
			var o={
				openid:openid
			}
			$.ajax({
				type : "POST",
				url : path + '/point/currentPoint.shtml',
				contentType:"application/json",
				data:JSON.stringify(o),
				success : function(data) {
					if(data&&data.success){
						$('#integral-detail .name').text(data.point); 
						BALANCE=data.point;
					}
				}
			});
		},
		'integralExpend':function(){
			var o={
				openid:openid
			}
			$.ajax({
				type : "POST",
				url : path + '/point/sendList.shtml',
				contentType:"application/json",
				data:JSON.stringify(o),
				success : function(data) {
					if(data&&data.success){
						$('#integral-expend').html(template('expend-info',data)).trigger('create');
						$('#integral-expend .ui-grid-b').click(function(){
							var _r=$(this).find('.reason');
							if(_r.is(':visible')){
								_r.slideUp('fast');
							}else{
								_r.slideDown('fast');
							}
						})
					}
				}
			});
		},
		'integralIncome':function(){
			var o={
				openid:openid
			}
			$.ajax({
				type : "POST",
				url : path + '/point/receiveList.shtml',
				contentType:"application/json",
				data:JSON.stringify(o),
				success : function(data) {
					if(data&&data.success){
						$('#integral-income').html(template('income-info',data)).trigger('create');
						$('#integral-income .ui-grid-b').click(function(){
							var _r=$(this).find('.reason');
							if(_r.is(':visible')){
								_r.slideUp('fast');
							}else{
								_r.slideDown('fast');
							}
						})
					}
				}
			});
		},
		'integralPerson':function(){
				var o={
					openid:openid
				}
				$.ajax({
					type : "POST",
					url : path + '/pub/formalMemberList.shtml',
					contentType:"application/json",
					data:JSON.stringify(o),
					success : function(data) {
						if(data&&data.success){
							$('#integral-person').html(template('contacts',data)).trigger('create');
							$('#integral-person li').click(function(){
								 var v=$(this).find('span').text(); 
								 $('#integral-reward #target').attr('data',$(this).attr('id'));
								 $('#integral-reward #target').val(v);
								 $.mobile.changePage('#integral-reward');
							})
						}
					}
				});
		},
		'integralSubmit':function(){
			$('#integral-reward .msg').text('');
			$('#integral-reward #submit').click(function(){
				var o={
						source:openid,
						target:$('#integral-reward #target').attr('data'),
						point:$('#integral-reward #point').val(),
						memory:$('#integral-reward #memory').val()
				};
				if(isNull(o.point)){
					$('#integral-reward .msg').text('打赏金额不能为空！');
					return;
				}
				if(!isNumber(o.point)&&o.point>0){
					$('#integral-reward .msg').text('打赏金额只能输入正整数！');
					return;
				}
				if(BALANCE-o.point<0){
					$('#integral-reward .msg').text('余额不足！');
					return;
				}
				$.ajax({
					type : "POST",
					url : path + '/point/sendPoint.shtml',
					contentType:"application/json",
					data:JSON.stringify(o),
					success : function(data) {
						if(data&&data.success){
							BALANCE=data.point;
							$('#integral-detail .name').text(data.point); 
							$.mobile.changePage('#award-tip');
						}
					}
				});
			})
		},
		'awardDialog':function(){
			$('#award-tip .back-btn').click(function(){
		        if(qtype=='keyword'){
		        	$.mobile.changePage('#keyword-page');
				}else if(qtype=='vote'){
					$.mobile.changePage('#vote-page');
				}else if(qtype=='calculate'){
					$.mobile.changePage('#calculate-page');
				}else if(qtype=='text'){
					$.mobile.changePage('#text-page');
				}
		    })
		},
		'init':function(){
			mobileView.initWx();
			if(qtype=='keyword'){
				mobileView.showKeywordQ();
			}else if(qtype=='vote'){
				mobileView.showVoteQ();
			}else if(qtype=='calculate'){
				mobileView.showCalculateQ();
			}else if(qtype=='text'){
				mobileView.showTextQ();
			}
			mobileView.closeDialog();
			mobileView.awardDialog();
			$(document).on("pagebeforeshow","#integral-detail",function(event){
				//获取账户余额
				mobileView.integralDetail();
			});
			$(document).on("pagebeforeshow","#integral-expend",function(event){
				//获取支出信息
				mobileView.integralExpend();
			});
			$(document).on("pagebeforeshow","#integral-income",function(event){
				//获取收入信息
				mobileView.integralIncome();
			});
			$(document).on("pagebeforeshow","#integral-person",function(event){
				//获取通讯录
				mobileView.integralPerson();
			});
			$(document).on("pagebeforeshow","#integral-reward",function(event){
				//清空表单数据
				$('#integral-reward #point').val('');
				$('#integral-reward #memory').val('');
				$('#integral-reward .msg').text('');
			});
			//获取账户余额
			mobileView.integralDetail();
			//提交打赏
			mobileView.integralSubmit();
		}
	};
	return mobileView;
})