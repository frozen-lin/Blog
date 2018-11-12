<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh">

<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<link rel="stylesheet"
	href="static/validator/vendor/bootstrap/css/bootstrap.css" />
<link rel="stylesheet" href="static/validator/dist/css/bootstrapValidator.css">
<link rel="stylesheet" href="static/css/register&login.css" />

<script type="text/javascript"
	src="static/js/jquery.min.js"></script>
<script type="text/javascript"
	src="static/validator/vendor/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="static/validator/dist/js/bootstrapValidator.js"></script>
<script type="text/javascript" src="static/js/register&login.js"></script>
<title>register</title>
<style type="text/css">
body {
	background-image: url("static/img/registerbackground.jpg");
}
</style>
<script type="text/javascript">
	$(function() {
		$('form').bootstrapValidator({
			message : 'This value is not valid',
			feedbackIcons : { /*input状态样式图片*/
				valid : 'glyphicon glyphicon-ok',
				invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			live : 'enabled',
			fields : {
				username : {
					validators : {
						notEmpty : {
							message : '用户名不能为空'
						},
						stringLength : {
							min : 3,
							max : 8,
							message : '用户名长度在3-8位之间'
						},
						remote : {
							url : 'confirmUsername',
							message : '用户名已存在',
							type : 'get',
							delay : 1000
						}
					}
				},
				password : {
					validators : {
						notEmpty : {
							message : '密码不能为空'
						},
						stringLength : {
							min : 6,
							max : 12,
							message : '密码长度必须在6-12位之间'
						},
						regexp : { //正则校验
							regexp : /^[a-zA-Z0-9_]+$/,
							message : '密码仅支数字，字母和下划线的组合'
						}
					}
				},
				verify : {
					validators : {
						notEmpty : {
							message : '验证码不能为空'
						},
						stringLength : {
							min : 4,
							max : 4,
							message : '验证码长度不正确'
						},
						remote : {
							url : 'confirmVerify',
							message : '验证码不正确',
							type : 'get',
							delay : 1000
						}
					}
				}
			}
		});
	});
	function changeVerify() {
		$("#img").attr("src", "getVerifyCode?" + new Date().getTime());
	}
</script>
</head>

<body>
	<div class="container">
		<div class="row">
			<div class="col-xs-10 col-xs-offset-1 col-sm-8 col-sm-offset-2">
				<div class="page-header">
					<h2>Register</h2>
				</div>
				<c:if test="${not empty msg }">
					<span class="label label-danger">${msg }</span>
				</c:if>
				<form action="${pageContext.request.contextPath }/register"
					method="post">
					<div class="form-group">
						<label for="username">用户名</label> <input name="username"
							type="text" class="form-control" id="username"
							placeholder="love you" onkeyup="removeblank(this);" />
					</div>
					<div class="form-group">
						<label for="password">密码</label> <input name="password"
							type="password" class="form-control" id="password"
							placeholder="password" onkeyup="removeblank(this);" />
					</div>
					<div class="form-group">
							<label for="verify">验证码</label>
							<img id="img" src="getVerifyCode" onclick="changeVerify();" /><a
								onclick="changeVerify();">换一张</a>
						<input name="verify" type="text" class="form-control" id="verify"
							placeholder="verify">
					</div>
					<button type="submit" class="btn btn-success">注册</button>
				</form>
				<a href="${pageContext.request.contextPath }/login">
					<button class="btn btn-link">已有账号,去登录</button>
				</a>
			</div>
		</div>
	</div>
	<%@include file="footer.jsp"%>
</body>