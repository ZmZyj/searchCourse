<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>

<head>
    <title>中欧创业营——首页</title>
    <meta charset="utf-8" />
    <link href="<%=path %>/assets/css/lib/simplePagination.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="<%=path %>/assets/css/global.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="<%=path %>/assets/css/style.css" media="all" rel="stylesheet" type="text/css"/>
	<script type="text/javascript">
    	var path='<%=path%>';
    </script>
    <script type="text/javascript" charset="utf-8" src="<%=path %>/assets/js/lib/jquery.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=path %>/assets/js/lib/jquery.simplePagination.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=path %>/assets/js/util/common.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=path %>/assets/js/util/validate.js"></script>
    <!--[if lt IE 9]>
	  <script src="<%=path %>/assets/js/ie/html5shiv.min.js" type="text/javascript"></script>
	<![endif]-->
</head>

<body>
	<div class="big-logo"></div>
	<header>
		<div class="logo">文章列表</div>
		<div class="person-info">
		</div>
	</header>
	<section>
		<div class="toolbar">
			<div class="search-box">
				<input id="keyword" type="text" placeholder="输入关键字"/>
			</div>
		</div>
		<table>
			<thead>
				<tr>
					<th>文章标题</th>
				</tr>
			</thead>
			<tbody>
				 
			</tbody>
		</table>
		<div id="light-pagination"></div>
	</section>
	<script id="q-list" type="text/html">
		{{each list as item i}}
		 <tr {{if i%2==0}}class="tr-striped"{{/if}}>
			<td><a href="{{item.articleUrl}}">{{item.articleTitle}}</a></td>
		</tr>
		{{/each}}
	</script>
    <script type="text/javascript" charset="utf-8" src="<%=path %>/assets/js/lib/require.js" data-main="<%=path %>/assets/js/boot.js"></script>
</body>

</html>
