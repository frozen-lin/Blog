<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<link rel="stylesheet" href="${pageContext.request.contextPath }/static/validator/vendor/bootstrap/css/bootstrap.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/static/validator/vendor/bootstrap/css/bootstrap-theme.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/static/css/main.css" />
<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/static/validator/vendor/bootstrap/js/bootstrap.min.js"></script>
<title>blog</title>
</head>
<body>

<%@include file="head.jsp" %>
<!--路径导航start-->
		<ol class="breadcrumb">
			<li>
				<a href="${pageContext.request.contextPath }">首页</a>
			</li>
			<c:if test="${! empty categoryId}">
				<li class="active"> ${categoryName }</li>
			</c:if>
		</ol>
		<!--路径导航end-->
		<div class="container">
			<div class="row">
		<!--页面左侧主体start-->
				<div class="data_list col-xs-12 col-sm-12 col-md-8">
					<div class="data_list_title">
						<span class="glyphicon glyphicon-list" aria-hidden="true"></span> 博客文章
					</div>
				<!--无文章时显示图片  -->
				<c:if test="${empty articlePage.list }">
				<div class="data_list" style="text-align :center">
				<img src="${pageContext.request.contextPath }/static/img/emptyarticle.jpg" class="img-responsive  icon"/>
				</div>
				</c:if>
					<!--文章start-->
				<c:forEach var="article" items="${articlePage.list }">
					<div class="container-fluid">
						<div class="row data_list">
							<div class="col-xs-4 col-sm-4">
								<a href="${pageContext.request.contextPath }/article/${article.articleId}" class="img-rounded">
								 <img src="${pageContext.request.contextPath }/static/img/articlephoto/${article.articleId % 5}.jpg" class="img-responsive  icon" alt="Responsive image">
								</a>
							</div>
							<div class="col-xs-8 col-sm-8">
								<h3>${article.articleTitle }</h3>
								<p class="data">${article.articleBrief }……</p>
								<a class="btn btn-info rightlocation" href="${pageContext.request.contextPath }/article/${article.articleId}" role="button">点此阅读</a>
							</div>
							<div class="boundary"></div>
							<span class="info">评论(${article.commentCount })——发表于[<fmt:formatDate value="${article.articleTime }" type="both"/>]</span>
						</div>
					</div>
				</c:forEach>
					<!--文章end-->
					<!--分页start-->
					<nav aria-label="Page navigation">
						<ul class="pagination pagination-sm">
						<c:if test="${! empty categoryId }">
					<!-- 按文章分类分页start -->
					<!-- 当前页 gt1就有上一页 -->
							<c:choose>
								<c:when test="${articlePage.curr > 1 }">
									<li>
										<a href="${pageContext.request.contextPath }/category/${categoryId }/p/${articlePage.curr-1 }" aria-label="Previous">上一页</a>
									</li>
								</c:when>
								<c:otherwise>
									<li class="disabled" aria-hidden="true"><span>上一页</span></li>
								</c:otherwise>
							</c:choose>
					<!--上一页end -->
							<!-- 页码展示判断 -->
							<c:choose>
								<c:when test="${articlePage.pageTotal < 4 }">
								<!-- 总页数 lt4时 -->
									<c:forEach var="i" begin="1" end="${articlePage.pageTotal }">
										<c:if test="${i == articlePage.curr }">
											<li class="active">
												<span>${i } <span class="sr-only">(current)</span></span>
											</li>
										</c:if>
										<c:if test="${i != articlePage.curr }">
											<li>
												<a href="${pageContext.request.contextPath }/category/${categoryId }/p/${i }">${i }</a>
											</li>
										</c:if>
									</c:forEach>
								</c:when>
								<c:otherwise>
								<!--总页数 ge4 -->
									<c:choose>
										<c:when test="${articlePage.curr == 1}">
											<!--当前页为第一页 -->
											<c:forEach var="i" begin="1" end="3">
												<c:if test="${i == articlePage.curr }">
													<li class="active">
														<span>${i } <span class="sr-only">(current)</span></span>
													</li>
												</c:if>
												<c:if test="${i != articlePage.curr }">
													<li>
														<a href="${pageContext.request.contextPath }/category/${categoryId }/p/${i }">${i }</a>
													</li>
												</c:if>
											</c:forEach>
										</c:when>
										<c:when test="${articlePage.curr >= articlePage.pageTotal-1 }">
										<!-- 当前页 ge倒数第二页时 -->
											<c:forEach var="i" begin="${articlePage.pageTotal-2 }" end="${articlePage.pageTotal }">
												<c:if test="${i == articlePage.curr }">
													<li class="active">
														<span>${i } <span class="sr-only">(current)</span></span>
													</li>
												</c:if>
												<c:if test="${i != articlePage.curr }">
													<li>
														<a href="${pageContext.request.contextPath }/category/${categoryId }/p/${i }">${i }</a>
													</li>
												</c:if>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<c:forEach var="i" begin="${articlePage.curr-1 }" end="${articlePage.curr+1 }">
												<c:if test="${i == articlePage.curr }">
													<li class="active">
														<span>${i } <span class="sr-only">(current)</span></span>
													</li>
												</c:if>
												<c:if test="${i != articlePage.curr }">
													<li>
														<a href="${pageContext.request.contextPath }/category/${categoryId }/p/${i }">${i }</a>
													</li>
												</c:if>
											</c:forEach>
										</c:otherwise>
									</c:choose>		
								</c:otherwise>	
							</c:choose>	
						<!-- 页码展示end -->																				
							<!--当前页 lt总页数 ,就有下一页  -->		
							<c:choose>
								<c:when test="${articlePage.curr < articlePage.pageTotal }">
									<li>
										<a href="${pageContext.request.contextPath }/category/${categoryId}/p/${articlePage.curr+1 }" aria-label="Next">下一页</a>
									</li>
								</c:when>
								<c:otherwise>
									<li class="disabled" aria-hidden="true"><span>下一页</span></li>
								</c:otherwise>
							</c:choose>
							<!-- 下一页end -->
						<!-- 文章分类分页end -->
					</c:if>
					
					
					<c:if test="${empty categoryId }">
						<!-- 搜索分页start -->
							<!-- 当前页 gt1就有上一页 -->
							<c:choose>
								<c:when test="${articlePage.curr > 1 }">
									<li>
										<a href="${pageContext.request.contextPath }/search/p/${articlePage.curr-1}?q=${param.q }" aria-label="Previous">上一页</a>
									</li>
								</c:when>
								<c:otherwise>
									<li class="disabled" aria-hidden="true"><span>上一页</span></li>
								</c:otherwise>
							</c:choose>
							<!--上一页end-->
							<!-- 页码展示判断 -->
							<c:choose>
								<c:when test="${articlePage.pageTotal < 4 }">
								<!--总页数lt4-->
									<c:forEach var="i" begin="1" end="${articlePage.pageTotal }">
										<c:if test="${i == articlePage.curr }">
											<li class="active">
												<span>${i } <span class="sr-only">(current)</span></span>
											</li>
										</c:if>
										<c:if test="${i != articlePage.curr }">
											<li>
												<a href="${pageContext.request.contextPath }/search/p/${i }?q=${param.q }">${i }</a>
											</li>
										</c:if>
									</c:forEach>
								</c:when>
								<c:otherwise>
								<!--总页数ge4-->
								<c:choose>
									<c:when test="${articlePage.curr == 1}">
									<!--当前页为第一页 -->
										<c:forEach var="i" begin="1" end="3">
											<c:if test="${i == articlePage.curr }">
												<li class="active">
													<span>${i } <span class="sr-only">(current)</span></span>
												</li>
											</c:if>
											<c:if test="${i != articlePage.curr }">
												<li>
													<a href="${pageContext.request.contextPath }/search/p/${i }?q=${param.q }">${i }</a>
												</li>
											</c:if>
										</c:forEach>
									</c:when>
									<c:when test="${articlePage.curr >= articlePage.pageTotal-1 }">
									<!-- 当前页 ge倒数第二页时 -->
										<c:forEach var="i" begin="${articlePage.pageTotal-2 }" end="${articlePage.pageTotal }">
											<c:if test="${i == articlePage.curr }">
												<li class="active">
													<span>${i } <span class="sr-only">(current)</span></span>
												</li>
											</c:if>
											<c:if test="${i != articlePage.curr }">
												<li>
													<a href="${pageContext.request.contextPath }/search/p/${i }?q=${param.q }">${i }</a>
												</li>
											</c:if>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<c:forEach var="i" begin="${articlePage.curr-1 }" end="${articlePage.curr+1 }">
											<c:if test="${i == articlePage.curr }">
												<li class="active">
													<span>${i } <span class="sr-only">(current)</span></span>
												</li>
											</c:if>
											<c:if test="${i != articlePage.curr }">
												<li>
													<a href="${pageContext.request.contextPath }/search/p/${i }?q=${param.q }">${i }</a>
												</li>
											</c:if>
										</c:forEach>
									</c:otherwise>
								</c:choose>		
							</c:otherwise>	
						</c:choose>	
						<!-- 页码展示end -->																				
						<!--当前页 lt总页数 ,就有下一页  -->		
							<c:choose>
								<c:when test="${articlePage.curr < articlePage.pageTotal }">
									<li>
										<a href="${pageContext.request.contextPath }/search/p/${articlePage.curr+1 }?q=${param.q }" aria-label="Next">下一页</a>
									</li>
								</c:when>
								<c:otherwise>
									<li class="disabled" aria-hidden="true"><span>下一页</span></li>
								</c:otherwise>
							</c:choose>
							<!-- 下一页end -->
						<!-- 搜索分页end -->
				</c:if>		
			</ul>
		</nav>
					<!--分页end-->
					
				</div>
				<!--页面左侧主体end-->
				<!--侧边栏start-->
				<%@include file="sidebar.jsp" %>
				<!--侧边栏end-->
			</div>
		</div>
		<%@include file="footer.jsp" %>
</body>
</html>
