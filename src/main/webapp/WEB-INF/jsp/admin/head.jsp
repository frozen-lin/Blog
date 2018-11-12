<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<div class="layui-layout layui-layout-admin">
		<div class="layui-header">
			<div class="layui-logo">
			<!--左侧下拉菜单start  -->
				<ul class="layui-nav layui-nav-tree" lay-filter="test">
					<li class="layui-nav-item"><a href="javascript:;">Blog后台菜单</a>
						<dl class="layui-nav-child">
								<dd class="layui-hide-sm">
									<a href="${pageContext.request.contextPath }/admin/index">后台首页<span class="layui-badge-dot"></span></a>
								</dd>
								<dd class="layui-hide-sm">
									<a href="${pageContext.request.contextPath }/">前台
									<span class="layui-badge-dot"></span>
									</a>
								</dd>
								<dd class="layui-hide-sm">
									<a href="${pageContext.request.contextPath }/admin/clearMailNum">评论<span id="mailNum1" class="layui-badge"></span></a>		
								</dd>
							<dd>
								<a href="${pageContext.request.contextPath }/admin/category">分类管理</a>
							</dd>
							<dd>
								<a href="${pageContext.request.contextPath }/admin/category/0">文章管理</a>
							</dd>
							<dd>
								<a href="${pageContext.request.contextPath }/admin/write">发表文章</a>
							</dd>
							<dd>
								<a href="${pageContext.request.contextPath }/admin/comment">评论列表</a>
							</dd>
							<dd>
								<a href="${pageContext.request.contextPath }/admin/link">友链管理</a>
							</dd>
							<dd>
								<a href="${pageContext.request.contextPath }/admin/carousel">轮播图</a>
							</dd>
						</dl></li>
				</ul>
				<!--左侧下拉菜单end  -->
			</div>
			<!-- 头部区域（可配合layui已有的水平导航） -->
			<ul class="layui-nav layui-layout-left">

					<li class="layui-nav-item layui-hide-xs layui-show-sm-inline-block">
					<a href="${pageContext.request.contextPath }/admin/index">后台首页</a>
					<span class="layui-badge-dot"></span>
					</li>
					<li class="layui-nav-item layui-hide-xs layui-show-sm-inline-block">
						<a href="${pageContext.request.contextPath }/">前台</a>
						<span class="layui-badge-dot"></span>
					</li>
			</ul>

			<ul class="layui-nav layui-layout-right">
					<li class="layui-nav-item layui-hide-xs layui-show-sm-inline-block">
						<a href="${pageContext.request.contextPath }/admin/clearMailNum" target="_blank">邮件<span id="mailNum2" class="layui-badge"></span></a>
					</li>
				<li class="layui-nav-item"><a href="javascript:;"> <img
						src="${pageContext.request.contextPath }/static/img/admin.jpg" class="layui-nav-img" /> ${sessionScope.user.username }
				</a>
					<dl class="layui-nav-child">
						<dd>
							<a href="${pageContext.request.contextPath }/loginout">退出</a>
						</dd>
					</dl></li>
			</ul>
		</div>
	</div>
	<script>
			//JavaScript代码区域
			layui.use(['element', 'layer','jquery' ], function() {
				var element = layui.element,
				layer = layui.layer,
				$ = layui.$;
				$.ajax({
					url:"${pageContext.request.contextPath}/admin/mail",
					method:"GET",
					success:function(data){
						$("#mailNum1").append(data);
						$("#mailNum2").append(data);
					}
				});
			});
	</script>
<!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
<!--[if lt IE 9]>
  <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
  <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
