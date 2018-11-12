<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>评论管理</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/static/css/main.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/static/layui/css/layui.css">
<script src="${pageContext.request.contextPath }/static/layui/layui.js"></script>
</head>
<body>
	<%@include file="head.jsp" %>
	<!-- 内容主体区域 -->
	<div style="padding: 15px;">
		<fieldset class="layui-elem-field">
			<legend>
				评论列表<i class="layui-icon layui-icon-list"
					style="font-size: 20px; color: #1E9FFF;"></i>
			</legend>
			<div class="layui-field-box layui-container" style="padding: 15px;">
			<c:choose>
				<c:when test="${empty commentPage.list }">
				<!--无评论图片start-->
				<div>
					<img src="${pageContext.request.contextPath }/static/img/emptycomment.jpg" class="img-responsive"
						alt="Responsive image">
					</div>
				<!--无评论图片END-->
				</c:when>
				<c:otherwise>
				<!--评论start-->
				<c:forEach var="comment" items="${commentPage.list }" >
					<div class="data_list" id="${comment.commentId }">
						<!--评论信息start-->
						<h2>用户名:${comment.username }</h2>
						<p class="data">${comment.commentContent }</p>
						<!--评论信息end-->
						<div class="rightlocation">
							<button class="layui-btn layui-btn-danger" onclick="layui.delComment(${comment.commentId })">
								<i class="layui-icon layui-icon-delete"></i>删除
							</button>
						</div>
						<div class="clearfloat"></div>
						<hr class="layui-bg-blue">
						<span class="info">[<fmt:formatDate  value="${comment.commentTime }" type="both"/>]</span>
						<div class="clearfloat"></div>
					</div>
				</c:forEach>
				<!--评论end-->
			</c:otherwise>
		</c:choose>
				<div id="page"></div>
			</div>
		</fieldset>
	</div>
	<!--内容主题区域end-->
	<script>
		//JavaScript代码区域
		layui.use(['element','laypage','layer'],function() {
					var element = layui.element,
					laypage = layui.laypage, 
					layer = layui.layer;
					laypage.render({
							elem : 'page',
							curr :	${commentPage.curr },
							count : ${commentPage.total },
							limit : ${commentPage.limit },
							theme : '#1E9FFF',
							first : '首页',
							last : '尾页',
							prev : '<em>←</em>',
							next : '<em>→</em>',
							groups : 3,
							jump : function(obj, first) {
								//翻页函数
								if (!first) {
								window.location.href = "${pageContext.request.contextPath}/admin/comment/p/"+ obj.curr;
									}
								}
							});
						});
		//暴露出delComment()方法
		layui.define(['layer','jquery'],function(exports){
			var layer = layui.layer,
				$ = layui.$;
			function delComment(commentId){
				layer.confirm("确认删除该评论？",function(){
					$.ajax({
						type:"DELETE",
						url:"${pageContext.request.contextPath}/admin/comment/"+commentId,
						success:function(data){
							if(data.errno==0){
								layer.msg("删除成功！");
								$(".data_list").remove("#"+commentId);
							}else{
								layer.msg("删除异常！"+data.msg);
							}
						},
						error:function(XMLHttpRequest, textStatus, errorThrown){
							layer.msg("删除失败，"+ errorThrown);
						}
					});						
				});				
			}
			exports('delComment',delComment);
		})
	</script>
</body>
</html>
