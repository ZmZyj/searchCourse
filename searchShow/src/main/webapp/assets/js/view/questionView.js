define(function(require){
	var app=require('app');
	var template=require('template');
	var questionView={
		'showQrcode':function(){
			 /*$('.overlay').fadeIn();
			 $('body').css('overflow','hidden');
			 $('.qrcode-shadow').show();
 			 $('.qrcode-shadow').animate({
				top:'0'
			 },'slow');*/
			var v=$('.pub-result .url').text();
			var t=$('.pub-result .title').text();
			window.open(BASE('/qrcode.shtml?v='+encodeURIComponent(v)+'&t='+encodeURIComponent(encodeURIComponent(t))));
		},
		'hideQrcode':function(){
			 $('.qrcode-shadow').animate({
				top : '1999px'
			 }, 'slow', function() {
				$('.qrcode-shadow').hide();
				$('body').css('overflow','auto');
			 });
			 $('.overlay').delay(200).fadeOut();
		},
		'showPreview':function(){
			 $('.overlay').fadeIn();
			 $('body').css('overflow','hidden');
			 $('.preview-box').show();
			 $('.preview-box').animate({
				top:'50%' 
			 },'slow');
		},
		'hidePreview':function(){
			 $('.preview-box').animate({
				top : '1999px'
			 }, 'slow', function() {
				$('.preview-box').hide();
				$('body').css('overflow','auto');
			 });
			 $('.overlay').delay(200).fadeOut();

		},
		'showDetail':function(){
			$.ajax({
				type : "POST",
				url : path + '/main/findQuestion.shtml',
			    data:'id='+qid,
				success : function(data) {
					if(data&&data.success){
						 var qjo=data.question;
						 if(qtype=='keyword'){
							 $('.keyword-q .get-result').attr('data-id',qid);
							 $('.keyword-q textarea').val(qjo.title);
							 $('.keyword-q input').val(qjo.content.inputNum);
						 }else if(qtype=='vote'){
							 $('.vote-q .get-result').attr('data-id',qid);
							 $('.vote-q .title').val(qjo.title);
							 $('.vote-q .check-num').val(qjo.content.checkNum);
							 $('.vote-q .item-box').html('');
							 var _options=qjo.content.options;
							 for(var i=0;i<_options.length;i++){
								 var html='<div class="item">'
									 +'		<label>选项'+(i+1)+'：</label>'
									 +'		<input class="item-input" type="text" value="'+_options[i]+'"/>'
									 +'		<a class="del-item" href="javascript:void(0)">X</a>'
									 +'</div>';
								 $('.vote-q .item-box').append(html);
							 }
							 
						 }else if(qtype=='calculate'){
							 $('.calculate-q .get-result').attr('data-id',qid);
							 $('.calculate-q textarea').val(qjo.title);
						 }else if(qtype=='text'){
							 $('.text-q .get-result').attr('data-id',qid);
							 $('.text-q textarea').val(qjo.title);
						 }
						 if(qstatus=='edit'){
							 $('.get-result').removeAttr('disabled');
							 $('.get-result').addClass('active');
						 }
					}
				}
			 });
		},
		'createVote':function(){
			 //$('.vote-q .title').val(qjo.title);
			 $('.vote-q .item-box').html('');
			 var _options=keywords.split('%_%');
			 for(var i=0;i<_options.length;i++){
				 var html='<div class="item">'
					 +'		<label>选项'+(i+1)+'：</label>'
					 +'		<input class="item-input" type="text" value="'+_options[i]+'"/>'
					 +'		<a class="del-item" href="javascript:void(0)">X</a>'
					 +'</div>';
				 $('.vote-q .item-box').append(html);
			 }
		},
		'showResult':function(){
			 $('.pubQ-b').addClass('pubQ-a');
			 $('.pubQ-b').removeClass('pubQ-b');
			 $('.inputQ-o').addClass('inputQ-a');
			 $('.inputQ-o').removeClass('inputQ-o');
			 if(qstatus=='qrcode'){
				 $('.inputQ-b').addClass('inputQ-a');
				 $('.inputQ-b').removeClass('inputQ-b');
			 }
			 $('.pub-result').fadeIn('slow',function(){
				 /*复制文本到剪贴板*/
				 $('#copy-btn').zclip({
					 path : path+'/assets/swf/ZeroClipboard.swf',
					 copy : function() {
						 return $('.result-box .url').text();
					 },
					 afterCopy : function() {// 复制成功
						 if($(".op-tip").is(':hidden')){
							 $(".op-tip").show();
						 }
						 $(".op-tip").text('复制成功!').fadeOut(2000);
					 }
				 });
				 $('.qrcode-box button').click(function(){
					 $('.qrcode-shadow').qrcode({
						 width:$(window).height(),
						 height:$(window).height(),
						 text:$('.result-box .url').text()
					 });
					
					 $('.qrcode-shadow').css('width',$(window).height()+'px');
					 $('.qrcode-shadow').css('margin-left',-$(window).height()/2+'px');
					 questionView.showQrcode();
				 })
			 });
		},
		'viewQrcode':function(){
			$.ajax({
				type : "POST",
				url : path + '/main/getErweimaUrl.shtml',
				data : 'id=' + qid,
				success : function(data) {
					if (data && data.success) {
						$('.view-result').attr('data-id',data.id);
						$('.view-result').attr('data-type',data.type);
						if(data.type=='text'){
							$('.view-result').attr('data-url',path+'/main/downloadTextAnswer.shtml?id='+data.id);
						}
						$('.pub-result .title').html(data.title);
						$('.pub-result .url').text(data.questionUrl);
						$('.pub-result .qr-code').qrcode({
							 width:200,
							 height:200,
							 text:data.questionUrl
						});
						questionView.showResult();
					}
				}
			});
		},
		'init':function(){
			 /*选择问题类型按钮事件*/
			 $('.q-type button').click(function(){
				 var id=$(this).attr('id');
				 $('.q-type').fadeOut('fast',function(){
					 $('.selectQ-o').addClass('selectQ-a');
					 //$('.selectQ-o').removeClass('selectQ-o');
					 $('.inputQ-b').addClass('inputQ-a');
					 $('.inputQ-b').removeClass('inputQ-b');
					 if(id=='keyword-btn'){
						 $('.keyword-q').fadeIn('slow');
					 }else if(id=="vote-btn"){
						 $('.vote-q').fadeIn('slow');
					 }else if(id=="calculate-btn"){
						 $('.calculate-q').fadeIn('slow');
					 }else if(id=="text-btn"){
						 $('.text-q').fadeIn('slow');
					 }
				 });
			 })
			 $('.back-link').click(function(){
				 var _cur;
				 if($('.keyword-q').is(':visible')){
					 _cur=$('.keyword-q');
				 }else if($('.vote-q').is(':visible')){
					 _cur=$('.vote-q');
				 }else if($('.calculate-q').is(':visible')){
					 _cur=$('.calculate-q');
				 }else if($('.text-q').is(':visible')){
					 _cur=$('.text-q');
				 }
				 _cur.fadeOut('fast',function(){
					 //$('.selectQ-o').addClass('selectQ-a');
					 //$('.selectQ-o').removeClass('selectQ-o');
					 $('.inputQ-a').addClass('inputQ-b');
					 $('.inputQ-a').removeClass('inputQ-a');
					 $('.q-type').fadeIn('slow');
				 })
			 })
			 /*编辑问题跳转*/
			 if(qid!=null&&qid!='null'){
				 if(qstatus=='qrcode'){
					 $('.q-type').hide();
					 questionView.viewQrcode();
				 }else{
					 $('.q-type #'+qtype+'-btn').click();
					 questionView.showDetail();
					 $('.back-link').hide();
				 }
			 }else if(keywords!=null&&keywords!='null'){
				 $('.q-type #'+qtype+'-btn').click();
				 questionView.createVote();
				 $('.back-link').hide();
			 }
			 /*填写关键词问题标题事件*/
			 $('.content textarea').keyup(function(){
				 var c=$(this).val();
				 if(!isNull(c)){
					 $('.get-result').removeAttr('disabled');
					 $('.get-result').addClass('active');
				 }else{
					 $('.get-result').attr('disabled','disabled');
					 $('.get-result').removeClass('active');
				 }
			 })
			 /*生成关键词问题预览图*/
			 $('.keyword-q .preview-btn').click(function(){
				 var jo={};
				 jo.name=$('.keyword-q textarea').val();
				 jo.list=[];
				 n=parseInt($('.keyword-q input').val());
				 for(var i=0;i<n;i++){
					 jo.list.push({});
				 }
				 $('.preview-box .content').html(template('keyword-preview',jo));
				 questionView.showPreview();
				 
			 })
			 
			 /*生成关键词问题*/
			 $('.keyword-q .get-result').click(function(){
				 var o={
						 id:$(this).attr('data-id'),
						 title:$('.keyword-q textarea').val(),
						 type:'keyword',
						 content:{
							 inputNum:$('.keyword-q input').val(),
							 inputArr:[]
						 }
				 };
				 if(isNull(o.title)){
					 $('.keyword-q .msg').text('问题不能为空！');
					 return;
				 }
				 if(isNull(o.content.inputNum)){
					 $('.keyword-q .msg').text('请填写输入框数量！');
					 return;
				 }else if(!isNumber(o.content.inputNum)){
					 $('.keyword-q .msg').text('输入框数量必须大于0！');
					 return;
				 }
				 for(var i=0;i<parseInt(o.content.inputNum);i++){
					 o.content.inputArr.push('');
				 }
				 
				 $.ajax({
					type : "POST",
					url : path + '/main/question.shtml',
					contentType:"application/json",
				    data:JSON.stringify(o),
					success : function(data) {
						if(data&&data.success){
							$('.view-result').attr('data-id',data.id);
							$('.view-result').attr('data-type',data.type);
							$('.pub-result .title').html(o.title);
							$('.pub-result .url').text(data.questionUrl);
							$('.pub-result .qr-code').qrcode({
								 width:200,
								 height:200,
								 text:data.questionUrl
							});
						}
						$('.keyword-q').fadeOut('fast',function(){
							questionView.showResult();
						});
					},
					error:function(){
					}
				 });
			 })
			 
			 
			 /*填写投票问题标题事件*/
			 $('.vote-q .title').keyup(function(){
				 var c=$(this).val();
				 if(!isNull(c)){
					 $('.get-result').removeAttr('disabled');
					 $('.get-result').addClass('active');
				 }else{
					 $('.get-result').attr('disabled','disabled');
					 $('.get-result').removeClass('active');
				 }
			 })
			 /*添加投票问题选项事件*/
			 $('.btn-group .add-item').click(function(){
				 var n=$('.vote-q .item').length;
				 var html='<div class="item">'
						 +'		<label>选项'+(n+1)+'：</label>'
						 +'		<input class="item-input" type="text" />'
						 +'		<a class="del-item" href="javascript:void(0)">X</a>'
					     +'</div>';
				 $('.vote-q .item-box').append(html);
			 })
			 /*删除投票问题选项事件*/
			 $('.vote-q .item-box ').delegate('a','click',function(){
				 $(this).parent().remove();
			 })
			 /*生成投票问题预览图*/
			 $('.vote-q .preview-btn').click(function(){
				 var jo={};
				 jo.name=$('.vote-q .title').val();
				 jo.list=[];
				 //n=$('.vote-q .item').length;
				 $('.vote-q .item input').each(function(){
					 jo.list.push($(this).val());
				 })
				 $('.preview-box .content').html(template('vote-preview',jo));
				 
				 questionView.showPreview();
			 })
			 
			 /*生成投票问题*/
			 $('.vote-q .get-result').click(function(){
				 var options=[];
				 var flag=false;//判断选项是否为空 标识位
				 var checkNum=$('.check-num').val();
				 $('.vote-q .item input').each(function(){
					 if(isNull($(this).val())){
						 flag=true;
					 }
					 options.push($(this).val());
				 })
				 var o={
					     id:$(this).attr('data-id'),
						 title:$('.vote-q .title').val(),
						 type:'vote',
						 content:{
							 options:options,
							 checkNum:checkNum
						 }
				 };
				 //校验数据
				 if(isNull(o.title)){
					 $('.vote-q .msg').text('问题不能为空！');
					 return;
				 }
				 if(isNull(checkNum)){
					 $('.vote-q .msg').text('最多可选几项不能为空！');
					 return;
				 }
				 if(!isInteger(checkNum)){
					 $('.vote-q .msg').text('只能输入数字！');
					 return;
				 }else{
					 if(parseInt(checkNum)<0){
						 $('.vote-q .msg').text('只能输入正整数！');
						 return;
					 }
				 }
				 if($('.vote-q .item input').length==0){
					 $('.vote-q .msg').text('至少添加一个选项！');
					 return;
				 }
				 if(flag){
					 $('.vote-q .msg').text('选项内容不能为空！');
					 return;
				 }
				 
				 $.ajax({
					type : "POST",
					url : path + '/main/question.shtml',
					contentType:"application/json",
				    data:JSON.stringify(o),
					success : function(data) {
						if(data&&data.success){
							$('.view-result').attr('data-id',data.id);
							$('.view-result').attr('data-type',data.type);
							$('.pub-result .title').html(o.title);
							$('.pub-result .url').text(data.questionUrl);
							$('.pub-result .qr-code').qrcode({
								 width:200,
								 height:200,
								 text:data.questionUrl
							});
						}
						$('.vote-q').fadeOut('fast',function(){
							questionView.showResult();
						});
					},
					error:function(){
					}
				 });
			 })
			 
			 /*生成计算问题预览图*/
			 $('.calculate-q .preview-btn').click(function(){
				 var jo={};
				 jo.name=$('.calculate-q textarea').val();
				 $('.preview-box .content').html(template('calculate-preview',jo));
				 questionView.showPreview();
				 
			 })
			 
			 /*生成计算问题*/
			 $('.calculate-q .get-result').click(function(){
				 var o={
						 id:$(this).attr('data-id'),
						 title:$('.calculate-q textarea').val(),
						 type:'calculate',
						 content:{}
				 };
				 if(isNull(o.title)){
					 $('.calculate-q .msg').text('问题不能为空！');
					 return;
				 }
				 
				 $.ajax({
					type : "POST",
					url : path + '/main/question.shtml',
					contentType:"application/json",
				    data:JSON.stringify(o),
					success : function(data) {
						if(data&&data.success){
							$('.view-result').attr('data-id',data.id);
							$('.view-result').attr('data-type',data.type);
							$('.pub-result .title').html(o.title);
							$('.pub-result .url').text(data.questionUrl);
							$('.pub-result .qr-code').qrcode({
								 width:200,
								 height:200,
								 text:data.questionUrl
							});
						}
						$('.calculate-q').fadeOut('fast',function(){
							questionView.showResult();
						});
					},
					error:function(){
					}
				 });
			 })
			 
			 /*生成作业问题预览图*/
			 $('.text-q .preview-btn').click(function(){
				 var jo={};
				 jo.name=$('.text-q textarea').val();
				 $('.preview-box .content').html(template('text-preview',jo));
				 questionView.showPreview();
			 })
			 
			 /*生成作业问题*/
			 $('.text-q .get-result').click(function(){
				 var o={
						 id:$(this).attr('data-id'),
						 title:$('.text-q textarea').val(),
						 type:'text',
						 content:{}
				 };
				 if(isNull(o.title)){
					 $('.text-q .msg').text('问题不能为空！');
					 return;
				 }
				 
				 $.ajax({
					 type : "POST",
					 url : path + '/main/question.shtml',
					 contentType:"application/json",
					 data:JSON.stringify(o),
					 success : function(data) {
						 if(data&&data.success){
							 $('.view-result').attr('data-id',data.id);
							 $('.view-result').attr('data-type',data.type);
							 $('.view-result').attr('data-url',path+'/main/downloadTextAnswer.shtml?id='+data.id);
							 $('.pub-result .title').html(o.title);
							 $('.pub-result .url').text(data.questionUrl);
							 $('.pub-result .qr-code').qrcode({
								 width:200,
								 height:200,
								 text:data.questionUrl
							 });
						 }
						 $('.text-q').fadeOut('fast',function(){
							 questionView.showResult();
						 });
					 },
					 error:function(){
					 }
				 });
			 })
			 /*跳转到查看结果页面*/
			 $('.view-result').click(function(){
				 var id=$(this).attr('data-id');
				 var type=$(this).attr('data-type');
				 if(type=='text'){
					 window.open(BASE('/main/downloadTextAnswer.shtml?id='+id));
					 return;
				 }
				 window.open(BASE('/result.shtml?id='+id+'&type='+type));
			 })
			 /*返回问题列表*/
			 $('.back-list').click(function(){
				 app.navigateTo('/index.shtml');
			 })
			 /*隐藏预览图*/
			 $('.overlay .close-btn').click(function(){
				 if($('.qrcode-shadow').is(':visible')){
					 questionView.hideQrcode();
				 }else{
					 questionView.hidePreview();
				 }
			 })
		}
	};
	return questionView;
})