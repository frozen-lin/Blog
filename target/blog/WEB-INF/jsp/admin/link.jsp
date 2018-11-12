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
<title>友链管理</title>
<link rel="stylesheet" href="../static/css/main.css" />
<link rel="stylesheet" href="../static/layui/css/layui.css">
<script src="../static/layui/layui.js"></script>
</head>
<body>
<%@include file="head.jsp" %>
<!--内容主题区域start-->

		<div style="padding: 15px;">
			<fieldset class="layui-elem-field">
				<legend>友链<i class="layui-icon layui-icon-share" style="font-size: 20px; color: #1E9FFF;"></i></legend>
				<div class="layui-field-box" style="padding: 15px;">
					<div class="layui-tab layui-tab-card">
						<ul class="layui-tab-title">
							<li class="layui-this">友链删改</li>
							<li>添加友链</li>
						</ul>
						<div class="layui-tab-content">
							<!--友链删改start-->
							<div class="layui-tab-item layui-show">
								<table class="layui-hide" id="categorylist" lay-filter="test"></table>
								<script type="text/html" id="operate">
									<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
									<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
								</script>
								<script>
									layui.use(['layer','table','jquery'], function() {
										var layer = layui.layer,
											table = layui.table,											
											$ = layui.$;
										var load = layer.load(4);
										table.render({
											elem: '#categorylist',
											url: '../link',
											parseData:function(res){
												for(var i=0;i<res.length;i++){
													res[i].link="<a href='"+res[i].link+"' target='_blank'>"+res[i].link+"</a>"
												}
												layer.close(load);
												return {
													"code":0,
													"msg":"",
													"count":res.length,
													"data":res
												}
											},
											cols: [
												[{
													fixed: 'left',
													title: '操作',
													toolbar: '#operate',
													width: 120
												}, {
													field: 'linkId',
													title: 'ID',
													hide:true
												}, {
													field: 'link',
													title: '链接',
												}, {
													field: 'linkName',
													title: '友链名字'
												}]
											]
										});
										//监听行工具事件
										table.on('tool(test)', function(obj) {
											var data = obj.data;
											if(obj.event == 'del') {
												layer.confirm('真的删除该链接么？', function(index) {
													var load = layer.load(4);
													$.ajax({
														type: "DELETE",
														url: "link/"+data.linkId,
														success: function(data, textStatus) {
															layer.close(load);
															if(data.errno==0){
																layer.msg("删除成功");
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
											} else if(obj.event == 'edit') {
												layer.open({
													content:
														//拼接表单html start
														'<form id="put_form" class="layui-form layui-form-pane">' +
														'<input type="hidden" name="linkId" value="'+data.linkId+'"/>'+
														'<div class="layui-form-item">' +
														'<label class="layui-form-label" for="put_link">友链链接*</label>' +
														'<div class="layui-input-block">' +
														'<input type="text" name="link" id="put_link" placeholder="请输入链接"  autocomplete="off" class="layui-input" value="' + data.link.substring(data.link.indexOf('>')+1,data.link.indexOf('</')) + '" />' +
														'</div>' +
														'</div>' +
														'<div class="layui-form-item">' +
														'<label class="layui-form-label" for="put_linkName">友链名称*</label>' +
														'<div class="layui-input-block">' +
														'<input type="text" name="linkName" id="put_linkName" placeholder="请输入友链名称" autocomplete="off" class="layui-input" value="' + data.linkName + '" />' +
														'</div>' +
														'</div>' +
														'</form>', //拼接表单html end
													btn: ['确定', '取消'],
													yes: function(index, layero) {
														//按钮【按钮一】的回调
														layer.close(index);
														if($("#put_link").val()==""){
															layer.msg("链接地址不能为空！");
															return;
														}
														if($("#put_linkName").val()==""){
															layer.msg("链接名称不能为空！");
															return;
														}
														var form_data = $("#put_form").serialize();
														var load = layer.load(4);
														$.ajax({
															type: "PUT",
															url: "link",
															data: form_data,
															success: function(data, textStatus) {
																layer.close(load);
																if(data.errno == 0){
																	layer.msg("修改成功！");
																	obj.update({
																		link:"<a href='"+$("#put_link").val()+"' target='_blank'>"+$("#put_link").val()+"</a>",
																		linkName: $("#put_linkName").val()
																});
																}else{
																	layer.msg("修改异常！"+data.msg);
																}
															},
															error: function(XMLHttpRequest, textStatus, errorThrown) {
																layer.close(load);
																layer.msg("修改失败，" + errorThrown);
															}
														});
													},
													btn2: function(index, layero) {
														//按钮【按钮二】的回调
														layer.close(index);
													}
												});
											}
										});
									});
								</script>
							</div>
							<!--友链删改end-->
							<!--添加友链start-->
							<div class="layui-tab-item">
								<form class="layui-form layui-form-pane" id="post_form" onsubmit="return false;">
									<div class="layui-form-item">
										<label class="layui-form-label" for="post_link">友链链接*</label>
										<div class="layui-input-block">
											<input type="text" name="link" id="post_link"  placeholder="请输入链接" autocomplete="off" class="layui-input">
										</div>
									</div>
									<div class="layui-form-item">
										<label class="layui-form-label" for="post_linkName">友链名称*</label>
										<div class="layui-input-block">
											<input type="text" name="linkName" id="post_linkName"  placeholder="请输入友链名称" autocomplete="off" class="layui-input">
										</div>
									</div>
									<div class="layui-form-item">
										<div class="layui-input-block">
											<button class="layui-btn" type="button" onclick="layui.btn_submit()">立即提交</button>
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
								if($("#post_link").val()==""){
									layer.msg("链接不能为空！！");
									return;
								}
								if($("#post_linkName").val()==""){
									layer.msg("友链名称不能为空！！");
									return;
								}
								var load = layer.load(4);
								//异步提交表单
								$.ajax({
									url: "link",
									data: $("#post_form").serialize(),
									type: "POST",
									success: function(data) {
										layer.close(load);
										if(data.errno == 0){
										layer.msg("添加成功！！");
										location.reload(true);												
										}else{
											layer.msg("添加异常，"+data.msg);
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
							<!--添加友链end-->
						</div>
					</div>
				</div>
			</fieldset>
		</div>
</body>
</html>
