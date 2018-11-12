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
<title>分类管理</title>
<link rel="stylesheet" href="../static/css/main.css" />
<link rel="stylesheet" href="../static/layui/css/layui.css">
<script src="../static/layui/layui.js"></script>
</head>
<body>
<%@include file="head.jsp" %>
<!--内容主题区域start-->

		<div class="container" style="padding: 15px;">
			<fieldset class="layui-elem-field">
				<legend>文章分类<i class="layui-icon layui-icon-list" style="font-size: 20px; color: #1E9FFF;"></i></legend>
				<div class="layui-field-box" style="padding: 15px;">
					<div class="layui-tab layui-tab-card">
						<ul class="layui-tab-title">
							<li class="layui-this">分类删改</li>
							<li>添加分类</li>
						</ul>
						<div class="layui-tab-content">
							<!--分类删改start-->
							<div class="layui-tab-item layui-show">
								删除分类需确定该分类下无文章。
								<table class="layui-hide" id="categorylist" lay-filter="test"></table>
								<script type="text/html" id="operate">
									<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
									<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
								</script>
								<script>
									layui.use(['layer','table', 'element','jquery'], function() {
										var layer = layui.layer,
											table = layui.table,
											element = layui.element,
											$ = layui.$;
										table.render({
											elem: '#categorylist',
											cols: [
												[{
													fixed: 'left',
													title: '操作',
													width: 120,
													toolbar: '#operate'
												}, {
													field: 'categoryId',
													title: 'ID',
													hide: true
												}, {
													field: 'count',
													title: '文章数量',
													sort: true,
												}, {
													field: 'categoryName',
													title: '分类名字',
												}]
											],
											data: [
										<c:forEach items="${list }" var="category" varStatus="status">
											{	"categoryId": "${category.categoryId }",
												"categoryName": "${category.categoryName }",
												"count": "${category.count }" }
													<c:if test="${!status.last }">,</c:if>
										</c:forEach>
										]
										});
										//监听行工具事件
										table.on('tool(test)', function(obj) {
											var category = obj.data;
											if(obj.event === 'del') {
												layer.confirm('真的删除该分类么？', function(index) {
													var load = layer.load(4);
													$.ajax({
														type: "DELETE",
														url: "category/"+ category.categoryId,
														dataType:"json",
														success: function(data, textStatus) {
															layer.close(load);
															if(data.msg=="success"){
															layer.msg("删除成功！");
															obj.del();																
															}else{
																layer.msg("删除失败，"+data.msg);
															}
														},
														error: function(XMLHttpRequest, textStatus, errorThrown) {
															layer.close(load);
															layer.msg("删除失败，" + errorThrown);
														}
													});
												});
											} else if(obj.event === 'edit') {
												layer.prompt({
													value: category.categoryName,
													title: '分类名字'+"(空值无法提交。)"
												}, function(value, index) {
													layer.close(index);
													if(value=="") {
														layer.msg("文章分类不能为空。");
														return;
														}
													var load = layer.load(4);
													$.ajax({
														type: "PUT",
														url: "category",
														dataType:"json",
														data: "categoryId="+category.categoryId+"&categoryName=" + value,
														success: function(data) {
															layer.close(load);
															layer.msg(data.msg);
															obj.update({
																categoryName:value
															});
														},
														error: function(XMLHttpRequest, textStatus, errorThrown) {
															layer.close(load);
															layer.msg("修改失败，" + errorThrown);
														}
													});
												});
											}

										});
									});
								</script>
							</div>
							<!--分类删改end-->
							<!--添加分类start-->
							<div class="layui-tab-item">
								<form class="layui-form layui-form-pane" id="categoryForm"  onsubmit="return false;">
									<div class="layui-form-item">
										<label class="layui-form-label" for="categoryName">文章分类</label>
										<div class="layui-input-block">
											<input type="text" name="categoryName" id="categoryName"  placeholder="请输入分类名称" autocomplete="off" class="layui-input">
										</div>
									</div>
									<div class="layui-form-item">
										<div class="layui-input-block">
											<button type="button"class="layui-btn" onclick="layui.btn_submit()">立即提交</button>
											<button type="reset" class="layui-btn layui-btn-primary">重置</button>
										</div>
									</div>
								</form>
							</div>
						<script>
					layui.define(['layer','jquery'],function(exports){
						var layer = layui.layer,
							$ = layui.$;
						function btn_submit() {
								//校验表单
								var val = $("#categoryName").val();
								if(val==""){
									layer.msg("分类名字不能为空！！");
									return;
								}
								var load = layer.load(4);
								//异步提交表单
								$.ajax({
									url: "category",
									data: "categoryName="+val,
									type: "POST",
									success: function(data) {
										layer.close(load);
										if(data=="success"){
										layer.msg("添加成功！！");
										location.reload(true);												
										}else{
											layer.msg(data);
										}
									},
								error: function(XMLHttpRequest, textStatus, errorThrown) {
									layer.close(load);
									layer.msg("添加失败，" + errorThrown);
								}
							});
						}
						exports('btn_submit',btn_submit);	
					});
						</script>
					<!--添加分类end-->
						</div>
					</div>
				</div>
			</fieldset>
		</div>
</body>
</html>
