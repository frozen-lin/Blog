<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!--置顶导航start-->
		<nav class="navbar navbar-inverse navbar-fixed-top">
			<div class="container-fluid">
				<!--移动设备响应式 -->
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                        <span class="icon-bar"></span>
        				<span class="icon-bar"></span>
        				<span class="icon-bar"></span>
      				</button>
					<a class="navbar-brand" href="${pageContext.request.contextPath }/">Blog首页</a>
					<p class="navbar-text">努力了的才叫梦想</p>
				</div>
				<!-- 导航选项 -->
				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav">
				<c:choose>
					<c:when test="${empty sessionScope.user }">
						<li>
							<a href="${pageContext.request.contextPath }/login">登录</a>
						</li>
						<li>
							<a href="${pageContext.request.contextPath }/register">注册</a>
						</li>
					</c:when>
					<c:otherwise>
						<li>
						<p class="navbar-text"><strong>${sessionScope.user.username }，</strong>欢迎登录！！！</p>
						</li>
						<li>
							<a href="${pageContext.request.contextPath }/loginout">退出登录</a>
						</li>
						<li>
							<a href="${pageContext.request.contextPath }/comment">个人评论</a>
						</li>
					</c:otherwise>		
				</c:choose>
					</ul>
					<!--搜索框-->
					<form class="navbar-form navbar-right" action="${pageContext.request.contextPath }/search" id="searchForm">
						<div class="form-group">
							<input id="q" type="text" class="form-control" placeholder="请输入搜索内容" name="q" />
						</div>
						<button type="submit" class="btn btn-default">
							<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
							搜索</button>
					</form>
					<ul class="nav navbar-nav navbar-right">
						<li>
							<a data-toggle="modal" data-target="#myModal">联系博主</a>
						</li>
					</ul>
				</div>
				<!-- /.导航选项end -->
			</div>
			<!-- /.container-fluid -->
		</nav>
		<!--置顶导航end-->
		<!--联系  模态框start-->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog modal-lg" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title" id="myModalLabel">邮件联系(fonzen_lin@126.com)</h4>
						<h6>(使用博主小号邮箱发送邮件)</h6>
					</div>
					<div class="modal-body">
						<form id="emailForm" onsubmit="return false">
							<div class="form-group">
								<label for="mailTitle" class="control-label">标题</label>
								<input type="text" id="mailTitle" name="mailTitle" class="form-control"  />
							</div>
							<div class="form-group">
								<label for="mailContent" class="control-label">内容</label>
								<textarea id="mailContent" name="mailContent" class="form-control" rows="5"></textarea>
							</div>
							<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
							<button type="button" class="btn btn-primary" onclick="sendMail()">发送邮件</button>
						</form>
					</div>
				</div>
			</div>
		</div>
		<!--联系  模态框end-->
		<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
			<!-- Indicators -->
			<ol class="carousel-indicators">
				<li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
				<li data-target="#carousel-example-generic" data-slide-to="1"></li>
				<li data-target="#carousel-example-generic" data-slide-to="2"></li>
			</ol>

			<!-- 轮播图 -->
			<div class="carousel-inner" role="listbox" id="carousellist">
			<!--轮播图内容start-->
	
			<!--轮播图内容end-->
			</div>
			<!-- 轮播图控制 -->
			<a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
				<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
				<span class="sr-only">Previous</span>
			</a>
			<a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
				<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
				<span class="sr-only">Next</span>
			</a>
		</div>
		<!--文章分类导航start-->
		<nav class="navbar navbar-default">
			<div class="container-fluid">
				<!--移动设备响应式 -->
				<div>
					<button type="button" class="navbar-toggle collapsed " data-toggle="collapse" data-target="#bs-example-navbar-collapse-2" aria-expanded="false">
                        <span class="icon-bar"></span>
        				<span class="icon-bar"></span>
        				<span class="icon-bar"></span>
      				</button>
					<p class="navbar-text" style="font-weight: bold;text-align: center;">文章分类</p>
				</div>
				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-2">
					<!--分类列表-->
					<ul class="nav nav-pills nav-justified" id="categorylist">
						<li role="presentation">
							<a href="${pageContext.request.contextPath }/category/0">全部</a>
						</li>
					</ul>
				</div>
			</div>
		</nav>
		<script type="text/javascript">
			$(function(){
				//ajax请求分类
				$.ajax({
					url:"${pageContext.request.contextPath}/category",
					method:"GET",
					success:function(data){
						var str ="" ;
						for(var i=0;i<data.length;i++){
							str +="<li role='presentation'><a href='${pageContext.request.contextPath }/category/"+data[i].categoryId+"'>"+data[i].categoryName+"</a></li>";
						}	
						$("#categorylist").append(str);
					}
				});
				//ajax请求轮播图
				$.ajax({
					url:"${pageContext.request.contextPath}/carousel",
					method:"GET",
					success:function(data){
						var str="";
						for(var i=0;i<data.length;i++){
							if(i==0){
								str+='<div class="item active"><img src="'+data[i].carouselUrl+'"><div class="carousel-caption"><h2>'+data[i].carouselSign+'</h2><p>————'+data[i].carouselAuthor+'</p></div></div>';				
							}else{
								str+='<div class="item"><img src="'+data[i].carouselUrl+'"><div class="carousel-caption"><h2>'+data[i].carouselSign+'</h2><p>————'+data[i].carouselAuthor+'</p></div></div>';
							}
						}
					$("#carousellist").append(str);	
					}
				});
				//搜索表单非空校验
				$("#searchForm").submit(function(){
					if($("#q").val() ==""){
					　　	$("body").append('<div class="alert alert-danger" role="alert" style="text-align:center;position:fixed;z-index:9999;margin: auto;top: 30%;left: 0;right:0;width:auto">'+
					　　			'<button type="button" class="close" data-dismiss="alert">&times;</button>'+
					　　			'<strong >搜索信息不能为空！！！</strong></div>');
						return false;
					}
				});
			});
			//发送邮件
			function sendMail(){
				if(confirm('确认发送?')){
					//校验表单
					if($("#mailTitle").val()==""){
						alert("标题不能为空");
						return;
					}
					if($("#mailContent").val()==""){
						alert("内容不能为空");
						return;
					}
					var formdata = $("#emailForm").serialize();
					$('#myModal').modal('hide');
					$.ajax({
						url:"${pageContext.request.contextPath}/mail",
						method:"POST",
						data:formdata,
						success:function(data){
							if(data.errno==0){
								alert("发送成功");
							}else{
								alert("发送异常"+data.msg);
							}
						},
						error:function(XMLHttpRequest, textStatus, errorThrown){
							alert("发送失败"+data.errorThrown);
						}	
					});
					alert("已发送");
				}
			}
		</script>