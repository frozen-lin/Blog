<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>后台首页</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/static/css/main.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/static/layui/css/layui.css">
<script src="${pageContext.request.contextPath }/static/layui/layui.js"></script>
<style>
		a {
			color: white;
		}
</style>
</head>
<body>	
		<!-- 页首 -->
		<%@include file="head.jsp" %>
		<!--内容主题区域start-->
		<div style="padding: 15px;">
			<fieldset class="layui-elem-field">
				<legend>快速入口<i class="layui-icon layui-icon-console" style="font-size: 20px; color: #1E9FFF;"></i></legend>
				<div class="layui-field-box layui-container " style="padding: 15px;">
					<h3 class="message">欢迎回来,管理员!!</h3>
					<hr class="layui-bg-red">
					<div class="layui-row  layui-col-space20">
						<div class="layui-col-xs12 layui-col-sm6 layui-bg-blue">
							<a href="${pageContext.request.contextPath }/admin/category/0">
								<i class="layui-icon layui-icon-read" style="font-size: 60px;"></i>
								<div class="rightlocation layui-anim layui-anim-up">
									共发表了
									<p style="font-size:x-large">${articleCount }</p>
									篇文章
								</div>
							</a>
						</div>
						<div class="layui-col-xs12 layui-col-sm6 layui-bg-cyan">
							<a href="${pageContext.request.contextPath }/admin/link">
								<i class="layui-icon layui-icon-share" style="font-size: 60px;"></i>
								<div class="rightlocation layui-anim layui-anim-up">
									共添加了
									<p style="font-size:x-large">${linkCount }</p>
									条友链
								</div>
							</a>
						</div>
						<div class="layui-col-xs12 layui-col-sm6 layui-bg-red">
								<i class="layui-icon layui-icon-friends" style="font-size: 60px;"></i>
								<div class="rightlocation layui-anim layui-anim-up">
									共注册了
									<p style="font-size:x-large">${userCount }</p>
									位用户
								</div>
						</div>
						<div class="layui-col-xs12 layui-col-sm6 layui-bg-orange">
							<a href="${pageContext.request.contextPath }/admin/comment">
								<i class="layui-icon layui-icon-dialogue" style="font-size: 60px;"></i>
								<div class="rightlocation layui-anim layui-anim-up">
									共收到了
									<p style="font-size:x-large">${commentCount }</p>
									条评论
								</div>
							</a>
						</div>
					</div>
				</div>
			</fieldset>
		</div>
</body>
</html>
