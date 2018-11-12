<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html> 
<html> 
<head> 
<meta charset="UTF-8"> 
<link rel="stylesheet" href="static/validator/vendor/bootstrap/css/bootstrap.css" />
<link rel="stylesheet" href="static/validator/vendor/bootstrap/css/bootstrap-theme.min.css" />
<link rel="stylesheet" href="static/css/main.css" />

<script type="text/javascript" src="static/js/jquery.min.js"></script>
<script type="text/javascript" src="static/validator/vendor/bootstrap/js/bootstrap.min.js"></script>
<title>个人评论</title> 
</head> 
<body>
<%@include file="head.jsp" %>
		<div class="container">
			<div class="row">
		<!--页面左侧主体start-->
				<div class="data_list col-xs-12 col-sm-12 col-md-8">
					<div class="data_list_title">
						<span class="glyphicon glyphicon-user" aria-hidden="true"></span> 个人评论
					</div>
					<c:choose>
						<c:when test="${empty list }">
						<!--无评论图片start-->
						<div>
							<img src="static/img/emptycomment.jpg" class="img-responsive"
							alt="Responsive image">
						</div>
						<!--无评论图片END-->
						</c:when>
						<c:otherwise>
							<c:forEach var="comment" items="${list }">
							<div class="data_list">
								<p class="data">${comment.commentContent }</p>
								<div class="rightlocation">
									<button type="button"  class="btn btn-warning" onclick="delComment(${comment.commentId})">删除</button>
								</div>
								<div class="clearfloat"></div>
								<div class="boundary"></div>
								<span class="info">[<fmt:formatDate value="${comment.commentTime }" type="both"/>]</span>
							</div>	
							</c:forEach>
						</c:otherwise>
					</c:choose>		
				</div>
				<script type="text/javascript">
					function delComment(commentId){
						if(confirm("确认删除该评论吗?")){
							$.ajax({
								url:"comment/"+commentId,
								method:"DELETE",
								success:function(data){
									if(data.errno==0){
										alert("删除成功");
										location.reload(true);
									}else{
										alert("删除异常，"+data.msg);
									}
								},
								error:function(XMLHttpRequest, textStatus, errorThrown){
									alert("删除失败，"+errorThrown);
								}
							})
						}
					}
				</script>
				<!--页面左侧主体end-->
				<!--侧边栏start-->
				<%@include file="sidebar.jsp" %>
				<!--侧边栏end-->
			</div>
		</div>
	<%@include file="footer.jsp" %>
 
</body> 
</html> 