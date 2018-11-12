<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<link rel="stylesheet" href="static/validator/vendor/bootstrap/css/bootstrap.css" />
		<link rel="stylesheet" href="static/css/register&login.css" />
		<title>login</title>
		<style type="text/css">
			body {
				background-image: url("static/img/loginbackground.jpg");
			}

		</style>
	</head>

	<body>
		<div class="container">
			<div class="row">
				<div class="col-xs-10 col-xs-offset-1 col-sm-8 col-sm-offset-2">
					<div class="page-header">
						<h2>Login</h2>
					</div>
					<c:if test="${not empty msg }">
					<span class="label label-danger">${msg }</span>
					</c:if>
					<!-- form: -->
					<form  action="${pageContext.request.contextPath}/login" method="post">
						<div class="form-group">
							<label for="username">用户名</label>
							<input name="username" type="text" class="form-control" id="username" placeholder="love you" onkeyup="removeblank(this)">
						</div>
						<div class="form-group">
							<label for="password">密码</label>
							<input name="password" type="password" class="form-control" id="password" placeholder="password" onkeyup="removeblank(this)">
						</div>
						<div class="checkbox">
							<label>
      							<input type="checkbox" name="autologin">自动登录
    						</label>
						</div>
						<button type="submit" class="btn btn-success">登录</button>
					</form>
					<a href="${pageContext.request.contextPath }/register"> <button class="btn btn-link">没有账号,去注册</button></a>
				</div>
			</div>
		</div>
		<%@include file="footer.jsp" %>
	</body>

</html>