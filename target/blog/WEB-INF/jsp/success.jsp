<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<link rel="stylesheet" href="${pageContext.request.contextPath }/static/validator/vendor/bootstrap/css/bootstrap.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/static/validator/vendor/bootstrap/css/bootstrap-theme.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/static/css/main.css" />
<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/static/validator/vendor/bootstrap/js/bootstrap.min.js"></script>
<title>成功提示</title>
</head>
<body>
<%@include file="head.jsp" %>
		<div class="container">
			<div class="row">
		<!--页面左侧主体start-->
				<div class="data_list col-xs-12 col-sm-12 col-md-8">
					<div class="data_list_title">
						<span class="glyphicon glyphicon-thumbs-up" aria-hidden="true"></span> 信息提示
					</div>
					<div class="content" align="center">
							<img class="img-responsive" src="static/img/success.jpg">
							<c:choose>
								<c:when test="${ empty param.msg }">
								<p class="message">操作成功!!!</p>				  
								</c:when>
								<c:otherwise>
									<p class="message">${param.msg }</p>
								</c:otherwise>
							</c:choose>
					</div>
				</div>
				<!--页面左侧主体end-->
				<!--侧边栏start-->
				<%@include file="sidebar.jsp" %>
				<!--侧边栏end-->
			</div>
		</div>
	<%@include file="footer.jsp" %>
</body>
</html>
