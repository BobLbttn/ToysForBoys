<%@ page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix="v" uri='http://vdab.be/tags'%>
<%@taglib uri='http://java.sun.com/jsp/jstl/fmt' prefix='fmt'%>
<!doctype html>
<html lang='nl'>
<head>
	<v:head title='unshipped orders' />
</head>
<body>
	<h1>Unshipped orders</h1>
	<table>
		<thead>
			<tr>
				<th>ID</th>
				<th>Ordered</th>
				<th>Required</th>
				<th>Customer</th>
				<th>Comment</th>
				<th>Status</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${orders}" var="order">
				<c:url value='detail.html' var='orderDetailURL'>
					<c:param name='id' value='${order.id}'/>
				</c:url>
				<tr>
					<td><a href="${orderDetailURL}">${order.id}</a></td>
					<td><fmt:parseDate type="date" pattern="d-M-yy" value="${order.orderDate}" var="orderDate" /> <fmt:formatDate value="${orderDate}"/></td>
					<td><fmt:parseDate type="date" pattern="d-M-yy" value="${order.requiredDate}" var="reqDate"/> <fmt:formatDate value="${reqDate}"/></td>
					<td>${order.customerId}</td>
					<td>${order.comments}</td>
					<td><img src="images/${order.status}.png">order.status</td>
 				</tr>
			</c:forEach>			
		</tbody>
	</table>
</body>
</html>