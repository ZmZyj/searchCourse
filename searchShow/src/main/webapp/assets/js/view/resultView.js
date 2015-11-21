define(function(require){
	var app=require('app');
	var template=require('template');
	var resultView={
		'showStudent':function(){
			$('.user-list-box').css('min-height',$('.answer-box').height());
			$('.answer-item').click(function(){
				var _this=$(this);
				$('.answer-item').each(function(){
					$(this).find('i').hide();
					$(this).css({
						border:0,
						borderBottom:'1px solid #dfdfdf'
					})
				})
				$(this).css({
					border:'1px solid #16b394',
					borderRight:'0'
				})
				var o={
					qId:qid,
					content:$(this).attr('data'),
					position:$(this).attr('position')
				}
				$.ajax({
					async:false,
					type : 'POST',
					url : path + '/main/getStudent.shtml',
					contentType:'application/json',
				    data:JSON.stringify(o),
					success:function(data) {
						 if(data){
							 $('.user-list-box').html(template('user-list',data));
							 if($('.user-list-box').is(':hidden')){
								 $('.user-list-box').animate({left:'100%'},10,function(){
									 _this.find('i').show();
									 $('.user-list-box').show();
								 });
							 }else{
								 _this.find('i').show();
							 }
						 }
					}
				});
			})
		},
		'checkAll':function(){
			$('.check-all input').click(function(){
				if($(this).is(':checked')){
					$('.key-box input[type="checkbox"]').prop("checked", true);
				}else{
					$('.key-box input[type="checkbox"]').prop("checked", false);
				}
			})
			/*$('.key-box').click(function(event){
				event.stopPropagation();
			})*/
		},
		'createVote':function(){
			$('.create-q-btn').click(function(){
				var keywords=[];
				$('.key-box input[type="checkbox"]').each(function(){
					if($(this).is(':checked')){
						keywords.push($(this).val());
					}
				})
				if(keywords.length==0){
					$('.msg').show();
					$('.msg').text('至少选择一个关键字！').fadeOut(3000);
					return;
				}
				app.navigateTo('/question.shtml?type=vote&keywords='+encodeURI(encodeURI(keywords.join('%_%'))));
			})
		},
		'delKeyword':function(){
			$('.op-box .del-keyword').click(function(){
				var _this=$(this);
				var o={
						qId:qid,
						keyword:$(this).attr('data')
				}
				$.ajax({
					type : 'POST',
					url : path + '/main/deleteKeywordInResult.shtml',
					contentType:'application/json',
				    data:JSON.stringify(o),
					success:function(data) {
						 if(data&&data.success){
							 _this.parent().parent().remove();
						 }
					}
				});
			})
		},
		'updateKeyword':function(){
			$('.key-box label').click(function(){
				$(this).hide();
				$(this).next('input[type="text"]').show();
				$(this).next('input[type="text"]').focus();
			})
			$('.key-box [type="text"]').keypress(function(event){
				if(event.keyCode==13){
					 var o={
						qId:qid
					 };
					 o.origiKeyword=$(this).attr('ori-data');
					 o.desKeyword=$(this).val();
					 $.ajax({
							type : 'POST',
							url : path + '/main/changeKeywordInResult.shtml',
							contentType:'application/json',
						    data:JSON.stringify(o),
							success:function(data) {
								 if(data&&data.success){
									 resultView.keywordList();
								 }
							}
					});
				}
			})
			$('.key-box [type="text"]').blur(function(){
				$(this).hide();
				$(this).prev('label').show();
			})
		},
		'keywordList':function(){
			var jo={
					qId:qid,
					count:100
			};
			$.ajax({
				type : 'POST',
				url : path + '/main/getKeywordResult.shtml',
				contentType:'application/json',
			    data:JSON.stringify(jo),
				success:function(data) {
					 if(data){
						 $('.detail-box').html(template('keyword-result',data));
						 resultView.checkAll();
						 resultView.createVote();
						 resultView.updateKeyword();
						 resultView.delKeyword();
						 resultView.showStudent();
					 }
				}
			});
		},
		'voteList':function(){
			var jo={
					qId:qid,
					count:100
			};
			$.ajax({
				type : 'POST',
				url : path + '/main/getVoteResult.shtml',
				contentType:'application/json',
				data:JSON.stringify(jo),
				success:function(data) {
					if(data){
						$('.detail-box').html(template('vote-result',data));
						resultView.checkAll();
						resultView.updateKeyword();
						resultView.delKeyword();
						resultView.showStudent();
					}
				}
			});
		},
		'showResult':function(){
			var jo={
					qId:qid
			};
			$.ajax({
				async:false,
				type : 'POST',
				url : path + '/main/getCalculateResult.shtml',
				contentType:'application/json',
				data:JSON.stringify(jo),
				success:function(data) {
					if(data){
						$('.detail-box').html(template('calculate-result',data));
						$('.user-list-box').css('min-height',$('.answer-box').height());
						$('.user-list-box').animate({left:'100%'},10,function(){
							$('.user-list-box').show();
						});
						window.location.hash='#middle-value';
						resultView.autoScroll('#middle-value');
					}
				}
			});
		},
		'autoScroll':function(ele){
			var t=$(ele).offset().top;
			$('body,html').animate({'scrollTop':t-60},500)
		},
		'locatePosition':function(){
			$('.float-bar a').click(function(){
				var ele=$(this).attr('data');
				window.location.hash=ele;
				resultView.autoScroll(ele);
			})
		},
		'bindScrollEvent':function(){
			$(window).scroll(function () {
		       if ($(window).scrollTop() >200) {
		    	   $('.float-bar').show();
		       }else{
		    	   $('.float-bar').hide();
		       }
			});
		},
		'init':function(){
			if(qtype=='keyword'){
				resultView.keywordList();
			}else if(qtype=='vote'){
				resultView.voteList();
			}else if(qtype=='calculate'){
				resultView.bindScrollEvent();
				resultView.showResult();
				resultView.locatePosition();
			}
			
		}	  
	};
	return resultView;
})