<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="description"
	content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1" />
<title>${errorInfo}</title>
</head>
<body>
<h1>${errorInfo}</h1><br/>
<script language="JavaScript1.2" type="text/javascript"> 
function delayURL(url, time) { 
setTimeout("top.location.href='" + url + "'", time); 
} 
</script> 
<span id="time" style="background: red">3</span> 
秒钟之后自动跳转，如果不跳转，请点击下面链接 
<a href="${jumpUrl}">目标页面</a> 
<script type="text/javascript"> 
delayURL("${jumpUrl}", 3000); 
</script> 
</body>
</html>