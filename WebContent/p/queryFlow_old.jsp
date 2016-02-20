<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en" class="app">
<head>
<meta charset="utf-8" />
<title>西电小蘑菇</title>
<link href="p/images/xd_logo.png" rel="shortcut icon"/>
<meta name="description"
	content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1" />
<link rel="stylesheet" href="p/css/app.v2.css" type="text/css" />
<link rel="stylesheet" href="p/js/calendar/bootstrap_calendar.css"
	type="text/css" cache="false" />
<script src="p/js/app.v2.js"></script>
<script src="p/resources/js/jquery.validate.min.js"></script>
<!-- MetisMenu CSS -->
<link href="p/resources/bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="p/resources/dist/css/sb-admin-2.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="p/resources/bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

<style  type="text/css">
	.myhead
	{
		height: 44px;
		text-align:center;
		font-size:18px;
		line-height: 44px;
		font-family: "Microsoft Yahei",Arial;
		font-weight: lighter;
		letter-spacing: 0;
		color: #FFFFFF;
		margin-top: 0px;
		background-color: #099fde;
		font-color:#000000
	}
</style>
</head>
<body>
	<header style="font-color:#000000">
		<h1 class="myhead">流量查询</h1>
	</header>
	<div class="row" style="margin:0px;margin-top:-20px">
	    <div class="col-lg-12">
	        <h3 class="page-header">请登录<img src="p/images/xd_logo.png" class="m-r-sm pull-right"></h3>
	    </div>
	    <!-- /.col-lg-12 -->
	</div>
	<div class="row" style="margin:-10px">
		<div class="panel-body"> 
		<form class="form-inline" action="queryFlow" role="form"  method="post" id="login"> 
					<div class="form-group"> 
						<input name="username" placeholder="请输入账号" class="form-control input-lg"> 
					</div> 
					<div class="form-group"> 
						<input type="password" name="password" id="inputPassword" placeholder="请输入密码" class="form-control input-lg"> 
					</div> 
					<button type="submit" class="btn btn-success  btn-s-md pull-right">查    询</button>
	
		
		</form> 
		</div>
	</div>
	<!--弹出显示框-->
		<div class="modal" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		    <br><br><br><br><br>
		    <div class="modal-dialog">
		        <div class="modal-content">
		            <div class="modal-header">
		                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		                <h5 class="modal-title" id="myModalLabel">提示</h5>
		            </div>
		            <div class="modal-body">
		           		<h5><div id="result"></div></h5>
		            </div>
		            <div class="modal-footer">
		            	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		            </div>
		        </div>
		        <!-- /.modal-content -->
		    </div>
		</div>
</body>
<script>
	if('${status}' == "fail") {
		txtHtml="<p class='text-warning'>" + "${info}" + "</p>";
    	$("#result").html(txtHtml);
		$("#myModal").modal("show");
	}
	$().ready(function() {
		// validate signup form on keyup and submit
		$("#login").validate({
			rules: {
				username: {
					required: true,
					minlength: 5
				},
				password: {
					required: true,
					minlength: 5
				}
			},
			messages: {
				username: {
					required: "请输入账号",
					minlength: "账号长度至少为5"
				},
				password: {
					required: "请输入密码",
					minlength:" 密码长度至少为5"
				}
			}
		});
	});
	</script>

</html>
