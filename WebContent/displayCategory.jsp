<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="main.*, java.util.*"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Fire & Ice Auction Site</title>
</head>

<!-- Stylesheet -->
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
<!-- End Stylesheet -->

<!-- Javascript -->
<script src="http://code.jquery.com/jquery-1.8.1.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<!-- End Javascript -->

</head>
<body style="padding-top:60px;">
<%@include file="header.jsp" %>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="page-header">
				<h1>${param.cat}</h1>
				<small>Displaying all items in the category.</small>
			</div>
			<table class="table table-bordered table-hover">
				<thead>
					<tr>
						<th>Description</th>
						<th>Location</th>
						<th>Ends In</th>
						<th>Seller</th>
						</tr>
				</thead>
				<tbody>
				<c:choose>
				<c:when test="${!empty itemList}">
					<c:forEach var="item" items="${itemList}">
						<tr>
							<td><a href="display?id=${item.id}">${item.description}</a></td>
							<td>${item.startLocation}</td>
							<td>${item.endsIn}</td>
							<td>${item.owner}</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr class="error"><td><p class="text-error">No items in category '${param.cat}'</p></td><td></td><td></td><td></td><td></td></tr>
				</c:otherwise>
				</c:choose>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>