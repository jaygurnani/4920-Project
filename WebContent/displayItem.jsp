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
				<h1>Item Details</h1>
				<small>${item.description}</small>
			</div>
			<c:choose>
			<c:when test="${empty item}">
				<div class="alert alert-error">
					<%--<button type="button" class="close" data-dismiss="alert">x</button>--%>
					<h4>Error:</h4> No such item.
				</div>
			</c:when>
			<c:otherwise>
				<c:if test="${!empty error}">
					<div class="alert alert-error">
						<h4>Sorry!</h4>
						${error}
					</div>
				</c:if>
				<c:if test="${!empty successfulBid}">
					<div class="alert alert-success">
						<h4>Success!</h4>
						Your bid of $${successfulBid} was successful. <br>
						Note: Any previous higher bids you made take precedence.
					</div>
				</c:if>
				<table class=table-bordered>
				<tr>
					<th>Image</th>
					<th>Details</th>
				<tr>
					<td><img src="img/missing.png" class=img-rounded"></td>
					<td>
						<table class=table-hover>
							<tbody>
								<tr><td>Category:</td><td>${item.category}</td></tr>
								<tr><td>Location:</td><td>${item.startLocation}</td></tr>
								<tr><td>Ships To:</td><td>${item.shipsTo}</td></tr>
								<tr><td>Ends In:</td><td>${item.endsIn} (${item.endDateString})</td></tr>
								<tr><td>Started:</td><td>${item.startDateString}</td></tr>
								<tr><td>Seller:</td><td>${item.owner}</td></tr>
								<tr>
									<td>Make Bid:</td>
									<td>
										<form action="makeBid" method="post" style="margin-bottom:0px;">
											<input type="hidden" name="id" value="${param.id}">
											<div class="input-prepend input-append span2" style="margin-bottom:0px;">
												<span class="add-on">$</span>
												<input class="span3" type="text" name="bid" placeholder="${item.minBidString}">
												<button class="btn" type="submit">Bid!</button>
											</div>
										</form>
										<small>(Minimum Bid $${item.minBidString})</small>
									</td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
				<tr><td colspan="2">Description:<br>?<br><br><br></td></tr>
				</table>
			</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>
</html>