<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<!--
            	描述：菜单栏
            -->
			<div class="container-fluid">
				<div class="col-md-4">
					<img src="${pageContext.request.contextPath}/img/logo2.png" />
				</div>
				<div class="col-md-5">
					<img src="${pageContext.request.contextPath}/img/header.png" />
				</div>
				<div class="col-md-3" style="padding-top:20px">
					<ol class="list-inline">
					<c:if test="${empty loginUser }">
						<li><a href="${pageContext.request.contextPath}/UserServlet?method=loginUI">登录</a></li>
						<li><a href="${pageContext.request.contextPath}/UserServlet?method=registUI">注册</a></li>
					</c:if>
					<c:if test="${not empty loginUser }">
						<li><a href="#">欢迎${loginUser.name }</a></li>
						<li><a href="${pageContext.request.contextPath}/UserServlet?method=logOut">退出</a></li>
						<li><a href="${pageContext.request.contextPath}/jsp/cart.jsp">购物车</a></li>
						<li><a href="${pageContext.request.contextPath}/OrderServlet?method=findOrdersByUidWithPage&num=1">我的订单</a></li>
					</c:if>
					</ol>
				</div>
			</div>
			<!--
            	描述：导航条
            -->
			<div class="container-fluid">
				<nav class="navbar navbar-inverse">
					<div class="container-fluid">
						<!-- Brand and toggle get grouped for better mobile display -->
						<div class="navbar-header">
							<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
								<span class="sr-only">Toggle navigation</span>
								<span class="icon-bar"></span>
								<span class="icon-bar"></span>
								<span class="icon-bar"></span>
							</button>
							<a class="navbar-brand" href="/store/index.jsp">首页</a>
						</div>

						<!-- Collect the nav links, forms, and other content for toggling -->
						<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
							<ul class="nav navbar-nav" id ="myUL">
							
				<%-- 			<c:forEach items="${AllCates }" var="c">
								<li><a href="#">${c.cname }</a></li>
							</c:forEach> --%>
							
							</ul>
							<form class="navbar-form navbar-right" role="search">
								<div class="form-group">
									<input type="text" class="form-control" placeholder="Search">
								</div>
								<button type="submit" class="btn btn-default">Submit</button>
							</form>

						</div>
						<!-- /.navbar-collapse -->
					</div>
					<!-- /.container-fluid -->
				</nav>
			</div>

</body>

<script>

	$(function(){
		//通过ajax请求CategoryServlet--->getAllCates得到分类的数据,转成JSON格式.
		var url = "/store/CategoryServlet";
		var json = {"method":"findAllCates"};
		
		$.post(url,json,function(data){
			//jquery遍历数据
			$.each(data,function(i,obj){
			/* 	var li="<li><a href='${pageContent.request.contentPath}/ProductServlet?method=findProductsWithCidAndPage&num=1&cid="+obj.cid+"'>"+obj.cname+"</a></li>"; */
				
				var li="<li><a href='/store/ProductServlet?method=findProductsWithCidAndPage&num=1&cid="+obj.cid+"'>"+obj.cname+"</a></li>";
			    $("#myUL").append(li);
			}); 
	
		} ,"json");
	});

</script>

<!-- <script>
$(function(){
	$.post("${pageContext.request.contextPath}/CategoryServlet",{"method":"findAllCats"},function(dt){
		// console.log(dt);
		//<li><a href="#">${c.cname}</a></li>
		//jquery遍历数据
		$.each(dt,function(i,obj){
			var li="<li><a href='${pageContext.request.contextPath}/ProductServlet?method=findProductsWithCidAndPage&num=1&cid="+obj.cid+"'>"+obj.cname+"</a></li>";
			$("#myUL").append(li);
		});		
		
	},"json");
}); -->
</script>
</html>