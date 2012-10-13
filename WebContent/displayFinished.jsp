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
				<h1>Finished Auction</h1>
				<small>${item.description}</small>
			</div>
			
			<%-- Display Errors --%>
			<c:choose>
			<c:when test="${notLoggedIn}">
				<div class="alert alert-error">
					<h4>Error:</h4> You're not logged in.
				</div>
			</c:when>
			<c:when test="${wrongUser}">
				<div class="alert alert-error">
					<h4>Error:</h4> You are not the winner of this auction.
				</div>
			</c:when>
			<c:when test="${empty item}">
				<div class="alert alert-error">
					<h4>Error:</h4> No such item.
				</div>
			</c:when>
			<c:when test="${!item.finished}">
				<div class="alert alert-error">
					<h4>Error:</h4> This auction hasn't finished.
				</div>
			</c:when>
			<c:otherwise>
				<div class="alert alert-success">
					<h4>Congratulations!</h4>
					You won this item for $${item.winBidString}!
					<form action="https://www.sandbox.paypal.com/cgi-bin/webscr" method="post" target="blank">
						<%-- The seller's merchant account details --%>
						<input type="hidden" name="business" value="${seller.payPalAcct}">
						<%-- Specify button behavior 'Buy Now' --%>
						<input type="hidden" name="cmd" value="_xclick">
						<%-- Item details --%>
						<input type="hidden" name="item_name" value="${item.description}">
						<input type="hidden" name="amount" value="${item.winBidString}">
						<input type="hidden" name="currency_code" value="AUD">
						<%-- Testing return urls --%>
						<input type="hidden" name="returnURL" value="http://algorhyt.hm/">
						<input type="hidden" name="cancelReturn" value="http://parafox.net/">
						<%-- The actual button --%>
						<button type="submit" class="btn btn-primary">Pay Now via PayPal!</button>
					</form>
				</div>
				
				<%--Item Details --%>
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
								<tr><td>Seller:</td><td><a href="showUser?id=${item.ownerId}">${item.ownerName}</a></td></tr>
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