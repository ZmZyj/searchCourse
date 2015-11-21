define(function(require){
	var app=require('app');
	var template=require('template');
	var View={
		'closeDialog':function(){
			$('#alert .back-btn').click(function(){
		        $('.ui-dialog').dialog('close');
		    })
		},
		'showKeywordQ':function(){
			$.mobile.changePage('#keyword-page');
			$.ajax({
				type : "POST",
				url : path + '/pub/questionDetail.shtml',
			    data:'id='+qid,
				success : function(data) {
					 if(data&&data.success){
						 $('#keyword-page').html(template('keyword-content',data)).trigger('create');
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
						$('#vote-page').html(template('vote-content',data)).trigger('create');
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
						$('#calculate-page').html(template('calculate-content',data)).trigger('create');
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
						$('#text-page').html(template('text-content',data)).trigger('create');
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
			View.initWx();
			if(qtype=='keyword'){
				View.showKeywordQ();
			}else if(qtype=='vote'){
				View.showVoteQ();
			}else if(qtype=='calculate'){
				View.showCalculateQ();
			}else if(qtype=='text'){
				View.showTextQ();
			}
			View.closeDialog();
		}
	};
	return View;
})