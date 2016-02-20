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
</head>
<body>
	<%@ include file="navHead.jsp" %>
	<div class="row">
	    <div class="col-lg-12">
	        <h1 class="page-header">成绩查询统计</h1>
	    </div>
	    <!-- /.col-lg-12 -->
	</div>
	<div class="panel panel-default">
        <div class="panel-heading">
           成绩查询统计
        </div>
        <!-- /.panel-heading -->
        <div class="panel-body">

            <div class="table-responsive">
                <table class="table table-striped table-bordered">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>本科生查询次数</th>
                            <th>本科生成功查询次数</th>
                            <th>研究生查询次数</th>
                            <th>研究生成功查询次数</th>
                            <th>日期</th>
                        </tr>
                    </thead>
                    <tbody>
	                    <c:forEach items="${gradeList }" var="node" varStatus="s">
				        	<tr>
			          	    <td><c:out value="${s.index+1 }"></c:out></td>
			          	    
					        <td><c:out value="${node.bachelorNum }"></c:out></td>  
					        <td><c:out value="${node.bachelorOkNum }"></c:out></td>  
					        <td><c:out value="${node.masterNum }"></c:out></td>  
					        <td><c:out value="${node.masterOkNum }"></c:out></td>  
					        <td><c:out value="${node.date }"></c:out></td>  
	                        </tr>
				        </c:forEach>
                    </tbody>
                </table>
            </div>
            <!-- /.table-responsive -->
        </div>
        <!-- /.panel-body -->
    </div>
    <!--弹出显示框-->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                <h4 class="modal-title" id="myModalLabel">提示</h4>
	            </div>
	            <div class="modal-body">
	           		<div id="result"></div>
	            </div>
	            <div class="modal-footer">
	            	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	            </div>
	        </div>
	        <!-- /.modal-content -->
	    </div>
	</div>
	<%@ include file="navTail.jsp" %>
</body>
<script>
	if('${status}' == "success") {
		txtHtml="<p class='text-warning'>" + "${info}" + "</p>";
    	$("#result").html(txtHtml);
		$("#myModal").modal("show");
	}
</script>
</html>