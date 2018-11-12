<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>成功提示</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/static/css/main.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/static/layui/css/layui.css">
<script src="${pageContext.request.contextPath }/static/layui/layui.js"></script>
</head>
<body>
	<!-- 引入头 -->
	<%@include file="head.jsp"%>
	<div style="padding: 15px;">
		<fieldset class="layui-elem-field">
			<legend>
				信息提示<i class="layui-icon layui-icon-face-surprised"
					style="font-size: 20px; color: #1E9FFF;"></i>
			</legend>
			<div class="layui-field-box layui-container "
				style="padding: 15px; text-align: center;">
				<img src="${pageContext.request.contextPath }/static/img/success.jpg" />			
			<c:choose>
				<c:when test="${ empty param.msg }">
				<p class="message">操作成功!!!</p>				  
				</c:when>
				<c:otherwise>
				<p class="message">${param.msg }</p>
				</c:otherwise>
			</c:choose>
			</div>
		</fieldset>
	</div>
</body>
</html>
