<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>会员登录</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
		<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" />

		<style>
			body {
				margin-top: 20px;
				margin: 0 auto;
			}
			
			.carousel-inner .item img {
				width: 100%;
				height: 300px;
			}
		</style>
	</head>

	<body>
		<%@ include file="header.jsp" %>

		<div class="container">
			<div class="row">

				<div style="margin:0 auto; margin-top:10px;width:950px;">
					<strong>我的订单</strong>
					<table class="table table-bordered">
					
						<c:forEach items="${pb.data }" var="order">
						<tbody>
							<tr class="success">
								<th colspan="2">${order.oid } </th>
								<c:if test="${order.state==1}">
									<th colspan="2"><a href="${pageContext.request.contextPath}/order?method=findOrderByOid&oid=${order.oid }">去支付</a></th>
								</c:if>
								<c:if test="${order.state==2}">
									<th colspan="2">待发货</th>
								</c:if>
								<c:if test="${order.state==3}">
									<th colspan="2"><a href="javascript:;">已发货</a></th>
								</c:if>
								<c:if test="${order.state==4}">
									<th colspan="2">已签收</th>
								</c:if>
								<th colspan="1">￥${order.total } </th>
							</tr>
							<tr class="warning">
								<th>图片</th>
								<th>商品</th>
								<th>价格</th>
								<th>数量</th>
								<th>小计</th>
							</tr>
							<c:forEach items="${order.listItem }" var="oi">
							<tr class="active">
								<td width="60" width="40%">
									<input type="hidden" name="id" value="22">
									<img src="${pageContext.request.contextPath}/${oi.product.pimage}" width="70" height="60">
								</td>
								<td width="30%">
									<a target="_blank">${oi.product.pname}</a>
								</td>
								<td width="20%">
									￥${oi.product.shop_price}
								</td>
								<td width="10%">
									${oi.count}
								</td>
								<td width="15%">
									<span class="subtotal">￥${oi.subtotal}</span>
								</td>
							</tr>
							</c:forEach>
						</tbody>
						</c:forEach>
						
					</table>
				</div>
			</div>
			<div style="text-align: center;">
				<ul class="pagination" style="text-align:center; margin-top:10px;">
				<c:if test="${pb.pageNumber==1 }">
					<li class="disabled"><a href="javascript:;" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
				</c:if>
				<c:if test="${pb.pageNumber!=1 }">
					<li><a href="${pageContext.request.contextPath}/order?method=findOrderByPage&pageNumber=${pb.pageNumber-1}" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
				</c:if>
				<c:forEach begin="1" end="${pb.totalPage }" var="n">
					<c:if test="${pb.pageNumber!=n }">
						<li><a href="${pageContext.request.contextPath}/order?method=findOrderByPage&pageNumber=${n}">${n}</a></li>
					</c:if>
					<c:if test="${pb.pageNumber==n }">
						<li class="disabled"><a href="javascript:;">${n}</a></li>
					</c:if>
				</c:forEach>
				<!-- <li class="active"><a href="#">1</a></li>
				<li><a href="#">2</a></li>
				<li><a href="#">3</a></li>
				<li><a href="#">4</a></li>
				<li><a href="#">5</a></li>
				<li><a href="#">6</a></li>
				<li><a href="#">7</a></li>
				<li><a href="#">8</a></li>
				<li><a href="#">9</a></li> -->
				
				<c:if test="${pb.pageNumber==pb.totalPage }">
					
					<li class="disabled">
						<a href="javascript:;" aria-label="Next">
							<span aria-hidden="true">&raquo;</span>
						</a>
					</li>
				</c:if>
				<c:if test="${pb.pageNumber!=pb.totalPage }">
					<li>
						<a href="${pageContext.request.contextPath}/order?method=findOrderByPage&pageNumber=${pb.pageNumber+1}" aria-label="Next">
							<span aria-hidden="true">&raquo;</span>
						</a>
					</li>
				</c:if>
			</ul>
			</div>
		</div>

		<div style="margin-top:50px;">
			<img src="${pageContext.request.contextPath}/image/footer.jpg" width="100%" height="78" alt="我们的优势" title="我们的优势" />
		</div>

		<div style="text-align: center;margin-top: 5px;">
			<ul class="list-inline">
				<li><a>关于我们</a></li>
				<li><a>联系我们</a></li>
				<li><a>招贤纳士</a></li>
				<li><a>法律声明</a></li>
				<li><a>友情链接</a></li>
				<li><a target="_blank">支付方式</a></li>
				<li><a target="_blank">配送方式</a></li>
				<li><a>服务声明</a></li>
				<li><a>广告声明</a></li>
			</ul>
		</div>
		<div style="text-align: center;margin-top: 5px;margin-bottom:20px;">
			Copyright &copy; 2005-2016 传智商城 版权所有
		</div>
	</body>

</html>