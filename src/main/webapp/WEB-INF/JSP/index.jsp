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
	<form>
	<table>
		<colgroup>
			<col style="width:5%">
			<col style="width:7%">
			<col style="width:7%">
			<col style="width:20%">
			<col style="width:40%">
			<col style="width:15%">
			<col style="width:6%">
		</colgroup>
		<thead>
			<tr>
				<th>ID</th>
				<th>Ordered</th>
				<th>Required</th>
				<th>Customer</th>
				<th>Comment</th>
				<th>Status</th>
				<th>Shipped</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${orders}" var="order">
				<c:url value='detail.html' var='orderDetailURL'>
					<c:param name='id' value='${order.id}'/>
				</c:url>
				<tr>
					<td style="text-align:right"><a href="${orderDetailURL}">${order.id}</a></td>
					<td style="text-align:right"><fmt:parseDate type="date" pattern="yyyy-MM-dd" value="${order.orderDate}" var="orderDate" /> <fmt:formatDate type="date" pattern="d-M-yy" value="${orderDate}"/></td>
					<td style="text-align:right"><fmt:parseDate type="date" pattern="yyyy-MM-dd" value="${order.requiredDate}" var="reqDate"/> <fmt:formatDate type="date" pattern="d-M-yy" value="${reqDate}"/></td>
					<td>${order.customer.name}</td>
					<td>${order.comments}</td>
					<td><img src="images/${order.status}.png">&nbsp;${order.status}</td>
					<td><input type="checkbox" name="id" value="${order.id}" /></td>
 				</tr>
			</c:forEach>			
		</tbody>
	</table>
	<div class="setshipped">
		<input type="submit" value="Set as shipped" >
	</div>
	</form>
</body>
</html>