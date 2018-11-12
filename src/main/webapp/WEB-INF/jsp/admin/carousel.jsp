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
<title>轮播图管理</title>
<link rel="stylesheet" href="../static/css/main.css" />
<link rel="stylesheet" href="../static/layui/css/layui.css" media="all">
<script src="../static/layui/layui.js"></script>
<style type="text/css">
img {
	max-width: 100%;
	max-height: 100%;
	width:auto;
}
</style>
</head>
<body>
	<%@include file="head.jsp" %>
	<!--内容主题区域start-->
		<div style="padding: 15px;">
			<fieldset class="layui-elem-field">
				<legend>轮播图管理<i class="layui-icon layui-icon-carousel" style="font-size: 20px; color: #1E9FFF;"></i></legend>
				<div class="layui-field-box" style="padding: 15px;">
					<div class="layui-tab layui-tab-card">
						<ul class="layui-tab-title">
							<li class="layui-this">管理轮播图</li>
							<li>上传</li>
						</ul>
						<div class="layui-tab-content">
							<!--管理start-->
							<div class="layui-tab-item  layui-show">
								<h3>轮播图按order排序,只取前三张进行轮播展示。</h3>
								<h4 style="color: rgb(114, 130, 85); ">请单击单元格进行修改。</h4>
								<div class="site-demo-flow" id="LAY">
									<table lay-filter="carousel">
										<thead>
											<tr>
												<th lay-data="{fixed: 'left', width: 70, align: 'center', toolbar: '#bar'}" rowspan="3">操作</th>
												<th lay-data="{field:'carouselId',hide:true}">Id</th>
												<th lay-data="{field:'carouselUrl',align: 'center',width:120}">图片展示</th>
												<th lay-data="{field:'carouselOrder',event:'editOrder', align: 'center',width:70}">order</th>
												<th lay-data="{field:'carouselAuthor',event:'editAuthor', align: 'center',width:100}">作者</th>
												<th lay-data="{field:'carouselSign',event:'editSign'}">签名</th>

											</tr>
										</thead>
										<tbody>
										<c:forEach var="carousel" items="${list }"> 
											<tr>
												<td></td>
												<td>${carousel.carouselId }</td>
												<td><img lay-src="${carousel.carouselUrl }"></td>
												<td>${carousel.carouselOrder }</td>
												<td>${carousel.carouselAuthor }</td>
												<td>${carousel.carouselSign }</td>
											</tr>
										</c:forEach>
										</tbody>
									</table>
								</div>
								<script type="text/html" id="bar">
									<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete">删除</a>
								</script>
								<script>
									layui.use(['layer', 'table', 'flow', 'jquery'], function() {
										var layer = layui.layer,
											table = layui.table,
											flow = layui.flow,
											$ = layui.$;
										//转换静态表格
										table.init('carousel');
										//图片懒加载
										flow.lazyimg({
											elem: '#LAY img'
										});
										//监听单元格事件
										table.on('tool(carousel)', function(obj) {
											var carousel = obj.data;
											if(obj.event === "delete") {
												layer.confirm("确认删除吗？",function(){
													var load = layer.load(4);
													$.ajax({
														url:"carousel/"+carousel.carouselId,
														method:"DELETE",
														success:function(data){
															layer.close(load);
															if(data.errno =="0"){
																layer.msg("删除成功！！");
																obj.del();
															}else{
																layer.msg("删除异常，"+data.msg);
															}
														},					
														error: function(XMLHttpRequest, textStatus, errorThrown) {
															layer.close(load);
															layer.msg("删除失败，" + errorThrown);
														}
													});
												});
											} else {
												var field,
													oldValue,
													formType = 0;
												//判断事件,确定修改的字段和弹出框类型
												if(obj.event === "editOrder") {
													field = "carouselOrder";
													oldValue = carousel.carouselOrder;
												}
												if(obj.event === "editAuthor") {
													field = "carouselAuthor";
													oldValue = carousel.carouselAuthor;
												}
												if(obj.event === "editSign") {
													field = "carouselSign";
													oldValue = carousel.carouselSign;
													formType = 2;
												}
												<!--弹出框-->
												layer.prompt({
													formType: formType,
													title: "修改" + field+"(空值无法提交。)",
													value: oldValue
												}, function(value, index) {
													layer.close(index);	
													var load = layer.load(4);
													$.ajax({
														url: 'carousel',
														method: "PUT",
														data: "carouselId=" + carousel.carouselId + "&" + field + "=" + value, //拼接参数
														success: function(data) {
															layer.close(load);
															if(data.errno == 0) {
																layer.msg("修改成功！！");
																//拼接js代码
																eval("obj.update({" + field + ":value})");
															} else {
																layer.msg("修改异常，" + data.msg);
															}
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
							<!--管理end-->
							<!--上传start-->
							<div class="layui-tab-item">
								<div class="layui-upload">
									<h3>最好上传尺寸为851×315的图片，最大5M。</h3>
									<button type="button" class="layui-btn" id="choose">
  										<i class="layui-icon">&#xe67c;</i>上传轮播图
									</button>
									<p id="imgName" style="padding: 15px;color:#666666"></p>
									<blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;">
										预览图：
										<img class="layui-upload-img" id="preview">
									</blockquote>
									<button type="button" class="layui-btn" id="upload">开始上传</button>
									<script>
										layui.use(['layer','jquery','upload', 'element'], function() {
											var layer = layui.layer,
												$ = layui.jquery,
												upload = layui.upload,
												element = layui.element;
											upload.render({
												elem: '#choose',
												url: 'carousel',
												auto: false,
												size: 5*1024,
												acceptMime: 'image/*',
												bindAction: '#upload',
												choose: function(obj) {
													//预读本地文件示例，不支持ie8
													obj.preview(function(index, file, result) {
														$('#preview').attr('src', result); //图片链接（base64）
														$('#imgName').html(file.name);
													});
												},
												done: function(res) {
													if(res.errno==0){
														layer.msg("上传成功！！");
														location.reload(true);
													}else{
														layer.msg("上传失败！！")
													}
												}
											});
										});
									</script>
								</div>
							</div>
							<!--上传end-->
						</div>
					</div>
				</div>
			</fieldset>
		</div>
</body>
</html>
