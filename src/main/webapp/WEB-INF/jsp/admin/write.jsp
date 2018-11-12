<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>
	<c:choose>
		<c:when test="${empty article.articleId }">发表文章</c:when>
		<c:otherwise>修改文章</c:otherwise>
	</c:choose>	
</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/static/css/main.css">
<script src="${pageContext.request.contextPath }/static/js/jquery.min.js"></script>
<!--layui依赖 -->
<link rel="stylesheet" href="${pageContext.request.contextPath }/static/layui/css/layui.css">
<script src="${pageContext.request.contextPath }/static/layui/layui.js"></script>
<!--wangEditor依赖-->
<script src="${pageContext.request.contextPath }/static/wangEditor/wangEditor.min.js"></script>
</head>
<body>
	<!-- 引入头 -->
	<%@include file="head.jsp"%>
	<!--内容主题区域start-->

	<div style="padding: 15px;">
		<fieldset class="layui-elem-field">
			<legend>
			<c:choose>
				<c:when test="${empty article.articleId }">发表文章</c:when>
				<c:otherwise>修改文章</c:otherwise>
			</c:choose>	
				<i class="layui-icon layui-icon-release"style="font-size: 20px; color: #1E9FFF;"></i>
			</legend>
			<div class="layui-field-box layui-container " style="padding: 15px;">
				<p class="message">${msg }</p>
				<form id="form" class="layui-form layui-form-pane"
					action="${pageContext.request.contextPath }/admin/article"
					method="post">
			<c:if test="${! empty article.articleId }">
				<!--判断是否添加字段-->
				<input type="hidden"name="_method" value="put" />
				<input type="hidden" name="articleId" value="${article.articleId }" />
			</c:if>
					<div class="layui-form-item">
						<label class="layui-form-label">文章类别</label>
						<div class="layui-input-block"
							style="position: relative; z-index: 1000;">
							<select name="category.categoryId" id="category">
								<option value=""></option>
							</select>
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label" for="title">标题</label>
						<div class="layui-input-block">
							<input type="text" name="articleTitle" id="title"
								value="${article.articleTitle }" required lay-verify="required"
								placeholder="请输入标题" autocomplete="off" class="layui-input" />
						</div>
					</div>
					<!-- 加载编辑器的容器 -->
					<div class="layui-form-item">
						<input type="hidden" name="articleContent" id="content" /> 
						<input type="hidden" name="articleBrief" id="brief" />
						<div id="editor">${article.articleContent }</div>
					</div>
					<div class="layui-form-item">
						<button class="layui-btn layui-btn-normal" type="submit">
							<i class="layui-icon layui-icon-add-circle">提交</i>
						</button>
						<button class="layui-btn layui-btn-normal" type="button"
							onclick="_clear()">
							<i class="layui-icon layui-icon-fonts-clear">清空文档</i>
						</button>
					</div>
				</form>
			</div>
		</fieldset>
	</div>	
	<!--内容主题区域end-->
	<script type="text/javascript">
		layui.use([ 'layer','form' ], function() {
			var layer = layui.layer,
				form = layui.form;
		});
		$(function(){
			$.ajax({
				url:"${pageContext.request.contextPath }/category",
				type:"GET",
				success:function(data){
					/*			
					<option value="0">java SE</option>
					<option value="1">JAVA WEB</option>
					<option value="2">数据库</option>
					<option value="3">算法</option>
					*/
					var cid;
					//若有categoryId,为cid赋值
					<c:if test="${! empty article.category.categoryId}">cid=${article.category.categoryId };</c:if>
					for(var i = 0;i<data.length;i++){
						//拼接
						if(data[i].categoryId == cid){
							$("#category").append("<option value='"+data[i].categoryId+"'selected='selected'>"+data[i].categoryName+"</option>");
						}else{
							$("#category").append("<option value='"+data[i].categoryId+"'>"+data[i].categoryName+"</option>");
					
						}
					}
				}
			});
			var E = window.wangEditor;
			editor = new E('#editor');			
			editor.customConfig.zIndex = 100;
			editor.customConfig.uploadImgServer = 'imageUp';
			editor.customConfig.uploadImgMaxSize = 5 * 1024 * 1024;
			editor.customConfig.uploadImgMaxLength = 1;
			editor.customConfig.uploadFileName = 'file';
			editor.create();
			//为表单提交添加监听事件
			$("#form").submit(function() {
				if($("#category").val() == '') {
					layer.msg('请选择类别');
					return false;
				}
				if(editor.txt.text()!='') {
					//取前100个字符作为摘要
					$("#brief").val(editor.txt.text().substr(0,100));
					$("#content").val(editor.txt.html());
					return true;
				} else {
					layer.msg('文档不能为空');
					return false;
				}
			});
		})
		function _clear() {
			editor.txt.clear();
		}
	</script>
</body>
</html>
