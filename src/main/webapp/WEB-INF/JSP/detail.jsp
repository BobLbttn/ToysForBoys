<%@ page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix="v" uri='http://vdab.be/tags'%>
<%@taglib uri='http://java.sun.com/jsp/jstl/fmt' prefix='fmt'%>

<!doctype html>
<html lang='nl'>
<head>
	<v:head title='Order ${param.id}' />
</head>
<body>
	<h1>Order ${param.id}</h1>
	
	<dl>
		<dt>Ordered:</dt>
		<dd>
			<fmt:parseDate type="date" pattern="yyyy-MM-dd" value="${order.orderDate}" var="orderDate" /> <fmt:formatDate type="date" pattern="dd/MM/yy" value="${orderDate}"/>
		</dd>
		<dt>Required:</dt>
		<dd>
			<fmt:parseDate type="date" pattern="yyyy-MM-dd" value="${order.requiredDate}" var="reqDate" /> <fmt:formatDate type="date" pattern="dd/MM/yy" value="${reqDate}"/>
		</dd>
		<dt>Customer:</dt>
		<dd>${order.customer.name}</dd>
		<dd>${order.customer.streetAndNumber}</dd>
		<dd>${order.customer.postalCode}&nbsp;${order.customer.city}&nbsp;${order.customer.state}</dd>
		<dd>${order.customer.country.name}</dd>
		<dt>Comments:</dt>
		<dd>${order.comments}</dd>
		<dt>Details:</dt>
		<dd>
		</dd>
		<dt>Value:</dt>
		<dd>
		</dd>
	</dl>
</body>
</html>