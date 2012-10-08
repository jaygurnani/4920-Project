<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="main.*, java.util.*"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
				<h1>Search Results</h1>
				<small>You searched for '${param.q}' Found ${fn:length(itemList)} match<c:if test="${fn:length(itemList) != 1}">es</c:if>.</small>
				<span class="span4 pull-right">
					<form action="search" method="get">
					<input type="hidden" name="q" value="${param.q}">
					<select name="sortBy" onchange="this.form.submit()">
						<c:choose>
							<c:when test="${param.sortBy eq '0'}">
								<option selected="selected" value="0">Default</option>
							</c:when>
							<c:otherwise>
								<option value="0">Default</option>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${param.sortBy eq '1'}">
								<option selected="selected" value="1">Name (Alphabetically)</option>
							</c:when>
							<c:otherwise>
								<option value="1">Name (Alphabetically)</option>
							</c:otherwise>
						</c:choose>						
						<c:choose>
							<c:when test="${param.sortBy eq '2'}">
								<option selected="selected" value="2">Ending First</option>
							</c:when>
							<c:otherwise>
								<option value="2">Ending First</option>
							</c:otherwise>
						</c:choose>						
						<c:choose>
							<c:when test="${param.sortBy eq '3'}">
								<option selected="selected" value="3">Seller (Alphabetically)</option>	
							</c:when>
							<c:otherwise>
								<option value="3">Seller (Alphabetically)</option>	
							</c:otherwise>
						</c:choose>
					</select>
					</form>
				</span>
			</div>
			<table class="table table-bordered table-hover">
				<thead>
					<tr>
						<th>Description</th>
						<th>Category</th>
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
							<td>${item.category}</td>
							<td>${item.startLocation}</td>
							<td>${item.endsIn}</td>
							<td>${item.owner}</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr class="error"><td><p class="text-error">No results found.</p></td><td></td><td></td><td></td><td></td></tr>
				</c:otherwise>
				</c:choose>
				</tbody>
			</table>
		</div>
	</div>

</body>
</html>