<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en" class="app">
<head>
<meta charset="utf-8" />
<title>西电小蘑菇</title>
<link href="p/images/xd_logo.png" rel="shortcut icon"/>
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1" />
<link rel="stylesheet" href="p/css/app.v2.css" type="text/css" />
<script src="p/js/app.v2.js"></script>
<script src="p/resources/js/jquery.validate.min.js"></script>

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
	.select
	{
		border-bottom:4px solid #1491C5;
	}
</style>

</head>
<body>
	<header>
		<h1 class="myhead">成绩查询</h1>
	</header>
	<div class="btn-group btn-group-justified" style="margin-top:-10px;"> 
		<a id="one" onclick="show(1)" class="btn select" style="color:#808285">本科</a> 
		<a id="two" onclick="show(2)" class="btn " style="color:#808285">研究生</a>
	</div>
	<section class="vbox">	
		<section>		
			<section id="content">
				<section class="vbox">
					<section class="scrollable padder">	
					<div class="row">
	<div id="form1">
	<div  style="margin:0px;margin-top:-20px;color:black;font-weight: 700;">
	    <div class="col-lg-12">
	        <h4 class="page-header"><strong>请登录</strong><img src="p/images/xd_logo.png" class="m-r-sm pull-right"></h4>
	    </div>
	</div>
	<div  style="margin:-10px">
		<div class="panel-body"> 
		<form  action="queryGrade" method="post" id="login"> 
			<div class="form-group"> 
				<input name="username" placeholder="请输入本科生账号" class="form-control input-lg"> 
			</div> 
			<div class="form-group"> 
				<input type="password" name="password" id="inputPassword" placeholder="请输入密码" class="form-control input-lg"> 
			</div> 
			<button type="submit" class="btn btn-success  btn-s-md pull-right">查    询</button>
		</form> 
		</div>
	</div>
	</div>
	<div id="form2" style="display:none">
	<div class="row" style="margin:0px;margin-top:-20px;color:black;font-weight: 700;">
	    <div class="col-lg-12">
	        <h4 class="page-header"><strong>请登录</strong><img src="p/images/xd_logo.png" class="m-r-sm pull-right"></h4>
	    </div>
	</div>
	<div class="row" style="margin:-10px">
		<div class="panel-body"> 
		<form class="form-inline" action="queryMasterGrade" role="form"  method="post" id="login"> 
			<div class="form-group"> 
				<input name="username" placeholder="请输入研究生账号" class="form-control input-lg"> 
			</div> 
			<div class="form-group"> 
				<input type="password" name="password" id="inputPassword" placeholder="请输入密码" class="form-control input-lg"> 
			</div> 
			<button type="submit" class="btn btn-success  btn-s-md pull-right">查    询</button>
		</form> 
		</div>
	</div>
	</div>
</div></section></section></section></section></section>
			<!--弹出显示框-->
			<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
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
			    </div>
			</div>
<script>
	function show(num) {
		var temp1 = document.getElementById("form1");
		var temp2 = document.getElementById("form2");
		var button1 = document.getElementById("one");
		var button2 = document.getElementById("two");
		if (num == "1") {
			temp1.style.display="";
			temp2.style.display="none";
			button1.className= "btn select";
			button2.className= "btn";
			button1.style.color="#329ECB";
			button2.style.color="#808285";
		} else {
			temp1.style.display="none";
			temp2.style.display="";
			button1.className= "btn";
			button2.className= "btn select";
			button1.style.color="#808285";
			button2.style.color="#329ECB";
		}
	}
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
</body>
</html>
