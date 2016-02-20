<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="description"
	content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1" />
	<link rel="stylesheet" href="p/css/app.v2.css" type="text/css" />
<title>我参加了众里寻ta活动，在西电找到了心仪的ta,你还不行动吗</title>
<style>
h3{
		border-bottom:4px solid #877bc7;
		padding-bottom:9px;
		margin-top:30px;
		width:200px;
	}
.triangle_border_left{
    margin-left: 90px;
    position: relative;
    margin-top: -25px;
}
.triangle_border_left span{
    display: block;
    width: 0;
    height: 0;
    border-width: 10px 10px 10px 0;
    border-style: solid;
    border-color: transparent #877bc7 transparent transparent;
    position: absolute;
    top: 0px;
    left: 0px;
}

.text{
	margin-left: 100px;
    background: #877bc7;
    position: relative;
    padding: 10px;
    top: -10px;
    border-radius: 14px;
    color: white;
    width: 60%;
}
.pic{
			width:120px;
			height:120px;
		}
</style>
</head>
<body>
	<header>
		<div class="row" style="margin-left:10px;margin-top:20px">
			<div class="col-lg-4 col-xs-12">
				<div class="row">
					<div class="col-xs-4" style="float:left"><img src="p/images/logo2.png"/></div>
					<div class="col-xs-8">
						<h3><span style="color:#e35969;font-size:34px;"><b>&nbsp;ta</b></span><span style="color:#7467BF">的信息</span></h3>
					</div>
				</div>
			</div>
		</div>		
	</header>
	<div class="row" style="padding-left:10px;padding-right:10px;margin-top:30px">
		<div class="col-lg-4">
			<div class="row" style="margin-left:-20px">
				<div class="panel-body text-center">
					<div class="row">
						<div class="col-xs-1"></div>
						<div class="col-xs-4">
							<img class="pic" alt="" src="<c:url value="${matchPic}"/>">
						</div>
						<div class="col-xs-2" >
							<img src="p/images/heart2.png" width="50px" style="margin-top:20px">
						</div>
						<div class="col-xs-4">
							<img class="pic" alt="" src="<c:url value="${thisPic}"/>">
						</div>
					</div>
					<div class="row" style="margin-top:30px">						
							<span style="color:red;font-size:20px;font-weight:bold">你们的匹配程度为：<span class="confidence" val="${con}">${con} %</span></span>						
					</div>
				</div>
			</div>
		</div>		
	</div>
	<div>
		<img src="p/images/QQ.png" style="margin-top: 40px;margin-left: 45px;">
		
		<div>
			<div class="triangle_border_left">
	    		<span></span>
			</div>
			<div class="text">
				<p>姓名：${matchPerson.name }</p>
				<p>学校：${matchPerson.school }</p>
				<p>学院：${matchPerson.college }</p>
				<p>年级：${matchPerson.grade }</p>
				<p>QQ: ${matchPerson.qq }</p>
				<p>兴趣爱好：${matchPerson.hobby }</p>
			</div>
		
		</div>
	</div>
	<div style="margin-top:40px">
		<img src="p/images/QQ.png" style="margin-left: 45px;">
		<div class="triangle_border_left">
	    		<span></span>
			</div>
		<div class="text">
		心动了吗？快点行动吧~ ！ta在等你哦~ ！
		</div>
	</div>
	<div style="margin-top:40px">
		<img src="p/images/QQ.png" style="margin-left: 45px;">
		<div class="triangle_border_left">
	    		<span></span>
			</div>
		<div class="text">
		分享到朋友圈让好朋友们看到与你匹配的ta
		</div>
	</div>
</body>
</html>