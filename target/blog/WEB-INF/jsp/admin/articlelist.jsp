<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>文章管理</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/static/css/main.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/static/layui/css/layui.css">
<script src="${pageContext.request.contextPath }/static/layui/layui.js"></script>
<style type="text/css">
img {
	max-width: 100%;
	max-height: 100%;
}
</style>
</head>
<body>
	<%@include file="head.jsp" %>
	<!-- 内容主体区域 -->
	<div style="padding: 15px;">
		<fieldset class="layui-elem-field">
			<legend>
				文章列表<i class="layui-icon layui-icon-list"
					style="font-size: 20px; color: #1E9FFF;"></i>
			</legend>
			<div class="layui-field-box layui-container" style="padding: 15px;">
				<div style="padding-bottom: 20px;">
						<span class="layui-breadcrumb" id="categorylist">
						</span>
				</div>
				<!--无文章时显示图片  -->
				<c:if test="${empty articlePage.list }">
				<div class="data_list" style="text-align :center">
				<img src="${pageContext.request.contextPath }/static/img/emptyarticle.jpg"/>
				</div>
				</c:if>
				<c:forEach var="article" items="${articlePage.list }">
				<!--文章start-->
					<div class="data_list" id="${article.articleId }">
						<!--文章信息start-->							
						<div>
							<h3>${article.articleTitle }</h3>
							<p class="data">${article.articleBrief }……</p>
						</div>
						<!--文章信息end-->
						<div class="rightlocation">
							<a href="${pageContext.request.contextPath }/admin/edit/${article.articleId}">
							<button class="layui-btn layui-btn-sm layui-btn-warm ">
							<i class="layui-icon layui-icon-edit"></i>编辑
							</button>
							</a>
							<button class="layui-btn layui-btn-sm layui-btn-danger" onclick="layui._delete(${article.articleId})">
							<i class="layui-icon layui-icon-delete"></i>删除
							</button>
						</div>
						<div class="clearfloat"></div>
						<hr class="layui-bg-blue">
						<span class="info">评论(${article.commentCount })——[<fmt:formatDate value="${article.articleTime }" type="both"/>]</span>
					</div>
					<!--文章end-->
				</c:forEach>
				<a href="${pageContext.request.contextPath }/admin/write">
				<button class="layui-btn layui-btn rightlocation">
					<i class="layui-icon layui-icon-add-circle"></i>发表文章
				</button>
				</a>
				<div class="clearfloat"></div>
				<div id="page"></div>
			</div>
		</fieldset>
	</div>
	<!--内容主题区域end-->
	<script>
		//JavaScript代码区域
		layui.use(['jquery','laypage'],function() {
			var $ = layui.$,
			laypage = layui.laypage;
			//请求category
			$.ajax({
				type:"get",
				url:"${pageContext.request.contextPath}/category",
				success:function(data){
					var cid = ${categoryId};
					var str = "<span lay-separator=''>|</span>";
					//拼接html,为category添加链接。
					if(cid!=0){
						str+="<a href='${pageContext.request.contextPath }/admin/category/0'>全部</a>";
						str+="<span lay-separator=''>|</span>";
					}else{
						str+="<a><span class='layui-badge-dot layui-bg-blue'></span><cite>全部</cite></a>";
						str+="<span lay-separator=''>|</span>";
					}
					for(var i=0;i<data.length;i++){
						if(data[i].categoryId!=cid){
							str+="<a href='${pageContext.request.contextPath }/admin/category/"+data[i].categoryId+"'>"+data[i].categoryName+"</a>";
							str+="<span lay-separator=''>|</span>";
						}else{
							str+="<a><span class='layui-badge-dot layui-bg-blue'></span><cite>"+data[i].categoryName+"</cite></a>";
							str+="<span lay-separator=''>|</span>";
						}
					}
					$("#categorylist").append(str);
				}
			});
			laypage.render({
					elem : 'page',
					count : ${articlePage.total},
					limit : ${articlePage.limit},
					curr :${articlePage.curr},
					theme : '#1E9FFF',
					first : '首页',
					last : '尾页',
					prev : '<em>←</em>',
					next : '<em>→</em>',
					groups : 3,
					jump : function(obj, first) {
					//obj包含了当前分页的所有参数：
					if(!first){
						window.location.href="${pageContext.request.contextPath}/admin/category/${categoryId}/p/"+obj.curr;
					}
				}
			});
		});
		//暴露出_delete()方法
		layui.define(['layer','jquery'],function(exports){
			var layer = layui.layer,
				$ = layui.$;
			function _delete(articleId){
				layer.confirm("确认删除该文章？",function(){
					$.ajax({
						type:"delete",
						url:"${pageContext.request.contextPath}/admin/article/"+articleId,
						success:function(data){
							if(data.errno==0){
								layer.msg("删除成功！");
								$(".data_list").remove("#"+articleId);
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
			exports('_delete',_delete);
		})
	</script>
</body>
</html>
