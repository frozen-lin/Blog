<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<link rel="stylesheet" href="../static/validator/vendor/bootstrap/css/bootstrap.css" />
<link rel="stylesheet" href="../static/validator/vendor/bootstrap/css/bootstrap-theme.min.css" />
<link rel="stylesheet" href="../static/validator/dist/css/bootstrapValidator.css">
<link rel="stylesheet" href="../static/css/main.css" />
<link rel="stylesheet" href="../static/wangEditor/wangEditor.min.css" />

<script type="text/javascript" src="../static/js/jquery.min.js"></script>
<script type="text/javascript" src="../static/validator/vendor/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="../static/validator/dist/js/bootstrapValidator.js"></script>
<title>${article.articleTitle }</title>
<script type="text/javascript">
	$(function() {
				$('#commentForm').bootstrapValidator({
					message: 'This value is not valid',
					feedbackIcons: { /*input状态样式图片*/
						valid: 'glyphicon glyphicon-ok',
						invalid: 'glyphicon glyphicon-remove',
						validating: 'glyphicon glyphicon-refresh'
						},
					live: 'enabled',
					fields: {
						commentContent: {
							validators: {
								notEmpty: {
									message:'评论不能为空'
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
								url : '../confirmVerify',
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
		$("#img").attr("src", "../getVerifyCode?" + new Date().getTime());
	}
</script>
</head>
<body>
	<%@include file="head.jsp"%>
	<!--路径导航start-->
	<ol class="breadcrumb">
		<li><a href="${pageContext.request.contextPath }">首页</a></li>
		<li><a
			href="${pageContext.request.contextPath }/category/${article.category.categoryId } ">${article.category.categoryName }</a></li>
		<li class="active">正文</li>
	</ol>
	<!--路径导航end-->
	<div class="container">
		<div class="row">
			<!--页面左侧主体start-->
			<div class="data_list col-xs-12 col-sm-12 col-md-8">
				<div class="data_list_title">
					<span class="glyphicon glyphicon-list" aria-hidden="true"></span>article
				</div>
				<!--文章内容start-->
					<div class="panel panel-info">
						<div class="panel-heading" style="text-align: center;">
							<h3 class="panel-title">${article.articleTitle }</h3>
						</div>
						<span class="info">---最后修改于[<fmt:formatDate value="${article.articleTime }" type="both"/>]</span>
						<div class="boundary "></div>
						<div class="panel-body">
							${article.articleContent }
						</div>
					</div>
				<!--文章内容end-->
				<!--翻页start-->
				<nav>
					<ul class="pager" style="text-align: left;">
					<c:choose>
						<c:when test="${empty article.pre }">
						<li class="disabled"><span aria-hidden="true">&larr;上一篇</span></li>
						<span class="label label-success">没有了</span>
						<br />
						</c:when>
						<c:otherwise>
						<li><a href="${pageContext.request.contextPath }/article/${article.pre.articleId}">&larr;上一篇</a></li>
						<span class="label label-info">${article.pre.articleTitle }</span>
						<br />
						</c:otherwise>
					</c:choose>
					
					<c:choose>
						<c:when test="${empty article.next }">
						<li class="disabled"><span aria-hidden="true">下一篇&rarr;</span></li>
						<span class="label label-success">没有了</span>
						</c:when>
						<c:otherwise>
						<li><a href="${pageContext.request.contextPath }/article/${article.next.articleId}">下一篇&rarr;</a></li>
						<span class="label label-info">${article.next.articleTitle }</span>
						</c:otherwise>
					</c:choose>
					</ul>
				</nav>
				<!--翻页end-->
				<!--发表评论start-->
				<div class="data_list">
					<div class="data_list_title">
						<span class="glyphicon"><img src="../static/img/comment.png"/></span>发表评论
					</div>
					<form id="commentForm" onsubmit="flase">
							<input type="hidden" name="articleId" value="${article.articleId }"/>
							<div class="form-group">
								<label for="commentContent" class="control-label">内容</label>
								<textarea name="commentContent" id="commentContent" class="form-control" rows="5" placeholder="说点什么吧..."></textarea>
							</div>
							<div class="form-group">
								<label for="verify">验证码</label>
								<img id="img" src="../getVerifyCode" onclick="changeVerify();" style="vertical-align:baseline;" />
								<a onclick="changeVerify();">换一张</a>
								<input name="verify" type="text" class="form-control" id="verify" placeholder="verify">
							</div>
							<div class="rightlocation">
								<button type="button" class="btn btn-default" onclick="ajax_submit()">提交评论</button>		
							</div>
							<div class="clearfloat"></div>
					</form>
				</div>
				<script type="text/javascript">
				//异步提交函数.
				function ajax_submit(){
					//检测bootstrapValidator是否校验完毕
					var bv = $("#commentForm").data('bootstrapValidator');
					if(bv.isValid()){
						var formdata = $("#commentForm").serialize();
						$.ajax({
							url:"../comment",
							method:"POST",
							data:formdata,
							success:function(data){
								if(data.errno==0){
									alert("评论提交成功!!");
									location.reload(true);
								}else{
									alert("评论提交异常，"+data.msg);
								}
							},
							error:function(XMLHttpRequest, textStatus, errorThrown){
								alert("评论提交失败，"+errorThrown);
							}
						});		
					}else{
						alert("请先正确输入");
					}
				}
					//避免二次提交
				</script>
				<!--发表评论end-->
				<!--文章评论start-->
				<div class="data_list">
					<div class="data_list_title">
						<span class="glyphicon glyphicon-comment" aria-hidden="true"></span>
						文章评论
					</div>
					<c:choose>
					<c:when test="${empty article.commentList}">
					<!--无评论图片start-->
					<div>
						<img src="../static/img/emptycomment.jpg" class="img-responsive"
							alt="Responsive image">
					</div>
					<!--无评论图片END-->
					</c:when>
					<c:otherwise>
					<c:forEach var="comment" items="${article.commentList }">
					<!--评论start-->
					<div class="container-fluid data_list">
						<div class="row">
							<div class="col-xs-3 col-sm-3">
								<img src="../static/img/profilephoto/${comment.commentId %5 }.jpg" class="img-responsive img-circle  icon"
									alt="Responsive image">
								<h6 class="text-center">${comment.username }</h6>
							</div>
							<div class="col-xs-9 col-sm-9">
								<p class="data">${comment.commentContent }</p>
							</div>
						</div>
						<div class="boundary"></div>
						<span class="info">[<fmt:formatDate value="${comment.commentTime }" type="both"/>]</span>
					</div>
					<!--评论end-->
					</c:forEach>
					</c:otherwise>
					</c:choose>	
				</div>
				<!--文章评论end-->
			</div>
		<%@include file="sidebar.jsp"%>
		</div>
	</div>
	<%@include file="footer.jsp" %>
</body>
</html>
