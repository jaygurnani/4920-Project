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
				<h1>PayPal Test</h1>
				<small>Testing payment via PayPal</small><br>
				<small>acct: '${seller.payPalAcct}' item_name: '${item.description}' amount: '${item.winBidString}'</small>
				
			</div>
			<c:choose>
			<c:when test="${empty item}">
				<div class="alert alert-error">
					<h4>Error:</h4> No such item.
				</div>
			</c:when>
			<c:otherwise>
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
					<button type="submit" class="btn btn-primary">Pay via PayPal</button>
				</form>
			</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>
</html>