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
<title>我登上了众里寻ta夫妻脸排行榜哟，快来帮我投票吧~</title>
<style type="text/css">
	/*.logo{
		background-image: url(p/images/logo2.png);
		background-repeat: no-repeat;
		width: 100%;
		height: 100px;
		padding-left: 120px;
		font-size: 30px;
		color: #877bc7;
		line-height: 80px;
	}
	.logo-text{
		width: 210px;
		border-bottom: 5px solid;
		top: -11px;
		position: relative;
	}*/
	body{
		background-color:#D2E9FF;
	}
	h3{
		border-bottom:4px solid #877bc7;
		padding-bottom:9px;
		margin-top:30px;
		width:200px;
	}
	.heart{
		width: 50px;
		position: relative;
		/*top: -9px;*/
		margin: 15px;
	}
	.content p{
		padding-left: 30px;
	}
	.title{
		/* margin-right: 40px; */
		font-weight:bold;
		font-size:15px;
		/*position: relative;
		top: -70px;*/
	}
	.content-text{
		margin-left: 20px;
	}
	/* .line{
		width: 30px !important;
		display: inline-block !important;
		border: 1px solid !important;
		margin: 0px 5px !important;
	} */
	button{
		background: rgb(135, 123, 199) none repeat scroll 0% 0%;
		border: medium none;
		border-radius: 5px;
		color: white;
		height: 27px;
		position: relative;
		top: -5px;
		left: 5px;
	}
	hr{
		border:0;
		border-top:1px solid red;
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
						<h3><span style="color:#7467BF">&nbsp;夫妻脸排行榜</span></h3>
					</div>
				</div>
			</div>
		</div>		
	</header>
	<div class="row" style="padding-left:10px;padding-right:10px" >
		<div class="col-lg-4 col-xs-12">
			<div class="row"><% int number = 1; %>
				<c:forEach items="${coupleList}" var="couple">
				<div class="content" id="${couple.uuid}">
					<p>
						<span class="title">Top<%=number %>:</span>
						<span>${couple.aName }</span>&nbsp;&nbsp;&&nbsp;&nbsp;<span>${couple.bName }</span>
					</p>
					<p>
						
						<img src="<c:url value="${couple.aPhoto}"/>" style="width:100px"/>
						<img src="p/images/heart3.png" class="heart">
						<img src="<c:url value="${couple.bPhoto }"/>" style="width:100px"/>
					</p>
					<!-- <p>
						${couple.aName } ${couple.bName }
					</p> -->	
					<p>
						<span class="content-text">夫妻脸指数<b> ${couple.matchs } </b>% 已有鲜花数<b id="flowerNum"> ${couple.countFollowers } </b></span>
						<button id="giveFlower" onclick="javascript:window.alert('活动已经结束，谢谢关注我们')">我要送花</button>
					</p>
				</div><% number++; %>
				<hr>
				</c:forEach>
			</div>
			<div class="row">
				<div style="float:left;"><img style="width:150px;height:150px" src="p/images/3.png"/></div>
				<div style="float:left">
					<p>你也想找到和你有缘的那个ta吗？</p>
					<p>不要犹豫啦，快快加入我们吧~！</p>
					<p>长按左边的二维码或搜索微信公众</p>
					<p>号：xdxiaomogu加入我们，你的ta</p>
					<p style="text-align:center">就在不远处等你哦~！</p>
				</div>
			</div>
		</div>
	</div>
</body>
<script src="p/js/jquery.min.js"></script>
<script>
	function sendFlower(id){
		//var id = $(".content").attr("id");
		//alert(id);
		$.ajax({
			type:'post',
			url:'Vote',
			data:{
				//identity: 10,
				rankerId: id,
			},
			success:function(data){
				if(data.Status == "success"){
					//alert(parseInt($("#"+id+" #flowerNum").html()));
					var oldVal = parseInt($("#"+id+" #flowerNum").html());
					var newVal = oldVal + 1;
					$("#"+id+" #flowerNum").html(newVal);
				}
				alert(data.Response);
			}
		});
	}
	/* $("#giveFlower").click(function(){
		
	}); */
</script>
</html>