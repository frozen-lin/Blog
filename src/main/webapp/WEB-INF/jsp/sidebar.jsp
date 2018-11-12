<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!--侧边栏start-->
<div class="col-xs-11 col-sm-11 col-md-3  col-md-offset-1">
	<!--最新评论start-->
	<div class="data_list">
		<div class="data_list_title">
			<span class="glyphicon glyphicon-comment" aria-hidden="true"></span>
			最新评论
		</div>
		<ul id="newcomment">
		</ul>
	</div>
	<!--最新评论end-->
	<!--友情链接start-->
	<div class="data_list">
		<div class="data_list_title">
			<span class="glyphicon glyphicon-paperclip" aria-hidden="true"></span>
			友情链接
		</div>
		<ul id="linklist">
		</ul>
	</div>
	<!--友情链接end-->
</div>
<!--侧边栏end-->
<script type="text/javascript">
			$(function(){
				$.ajax({
					url:"${pageContext.request.contextPath}/link",
					method:"GET",
					success:function(data){
						var str ="" ;
						for(var i=0;i<data.length;i++){
							str +='<li><h3><span class="label label-success"><a target="_blank" href="'+
									data[i].link+'">'+data[i].linkName+'</a></span></h3></li>';
						}	
						$("#linklist").append(str);
					}
				});
				$.ajax({
					url:"${pageContext.request.contextPath}/newcomment",
					method:"GET",
					success:function(data){
						var str = "";
						for(var i =0;i<data.length;i++){
							str += '<li><p><h4>'+data[i].username+':<small>'+data[i].commentContent
									+'</small></h4></p></li>';
						}
					$("#newcomment").append(str);
					}
				});
			});
			
		</script>