<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Fire & Ice Auction Site</title>

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
				<h1>${user.name}</h1>
				<small>User Details</small>
			</div>
			<c:choose>
			<c:when test="${empty user}">
				<div class="alert alert-error">
					<h4>Error:</h4> No such user.
				</div>
			</c:when>
			<c:otherwise>
				<c:if test="${!empty param.deleted}">
					<div class="alert alert-info">
						<h4>Deletion Successful!</h4>
						Your item '${param.deleted}' has been deleted.
					</div>
				</c:if>
				<table class="table table-hover">
					<tbody>
						<tr><td>Rating:</td><td>${user.rating} (${user.ratingCount} ratings)</td></tr>
						<tr><td>e-mail:</td><td><a href="mailto:${user.email}">${user.email}</a></td></tr>
						<tr><td>About:</td><td>${user.about}</td></tr>
					</tbody>
				</table>
				<c:if test="${isUser}"><a href="showUser?id=${user.id}&show=0">Show Items Won</a> or <a href="showUser?id=${user.id}&show=1">Show Listed Auctions</a></c:if>
				<c:choose>
				<c:when test="${showWon}">
				<h3>Items Won:</h3>
				<table class="table table-bordered table-hover">
					<thead>
						<tr>
							<th>Description</th>
							<th>Category</th>
							<th>Location</th>
							<th>Seller</th>
						</tr>
					</thead>
					<tbody>
					<c:choose>
					<c:when test="${!empty itemList}">
						<c:forEach var="item" items="${itemList}">
							<tr>
								<td><a href="finished?id=${item.id}">${item.description}</a></td>
								<td><a href="category?cat=${item.category}">${item.category}</a></td>
								<td>${item.startLocation}</td>
								<td><a href="showUser?id=${item.ownerId}">${item.ownerName}</a></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="error"><td><p class="text-error">No items found.</p></td><td></td><td></td><td></td><td></td></tr>
					</c:otherwise>
					</c:choose>
					</tbody>
				</table>
				</c:when>
				<c:otherwise>
				<h3>Selling items:</h3>
				<table class="table table-bordered table-hover">
					<thead>
						<tr>
							<th>Description</th>
							<th>Category</th>
							<th>Location</th>
							<th>Ends In</th>
						</tr>
					</thead>
					<tbody>
					<c:choose>
					<c:when test="${!empty itemList}">
						<c:forEach var="item" items="${itemList}">
							<tr>
								<td><a href="display?id=${item.id}">${item.description}</a></td>
								<td><a href="category?cat=${item.category}">${item.category}</a></td>
								<td>${item.startLocation}</td>
								<td>${item.endsIn}</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="error"><td><p class="text-error">No items found.</p></td><td></td><td></td><td></td><td></td></tr>
					</c:otherwise>
					</c:choose>
					</tbody>
				</table>
				</c:otherwise>
				</c:choose>
			</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>
</html>