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
<link href="css/main.css" rel="stylesheet">
<!-- End Stylesheet -->

<!-- Javascript -->
<script src="jquery/jquery-1.8.1.js"></script>
<script src="jquery/jquery.validate.pack.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript">
	$(document).ready(function()	{
		$("#register").validate({
			rules: {
				name: {
					required: true,
					minlength : 6,
				},
				minBid: {
					required: true,
					minlength : 1,
				}
			}
		});
	});

</script>
<!-- Javascript -->

</head>
<body style="padding-top:60px;">
<%@include file="header.jsp" %>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="page-header">
					<h1>New Auction</h1>
					<small>Enter information about your new auction</small>
			</div>
			
			<c:if test="${!empty error}">
					<div class="alert alert-error">
						<h4>Error!</h4>
						${error}
					</div>
				</c:if>
			
			<form id="register" action="makeAuction" method="post">
			<div class="control-group">
				<label class="control-label"><h4>Item Name</h4></label> <input type="text" maxlength="100 " class="span4" name="name" value="${param.name}"></input>
				<!--<label class="control-label"><h4>Description</h4></label> <textarea class="span4" name="description" rows="5">${param.description}</textarea>-->
				<label class="control-label"><h4>Category</h4></label> <select name="category"><c:forEach var="category" items="${categories}"><option>${category}</option></c:forEach></select>
				<label class="control-label"><h4>Ships From</h4></label> <select name="shipsFrom"><c:forEach var="location" items="${locations}"><option>${location}</option></c:forEach></select>
				<label class="control-label"><h4>Ships To</h4></label> <select name="shipsTo"><c:forEach var="location" items="${locations}"><option>${location}</option></c:forEach></select>
				<label class="control-label"><h4>Minimum Bid</h4></label> <div class="input-prepend"><span class="add-on">$</span><input type="text" class="input-mini" name="minBid" placeholder="1.00" value="${param.minBid}"></input></div>
				<label class="control-label"><h4>End Date</h4></label> <select class="input-mini" name="day"><c:forEach var="day" begin="1" end="31"><option>${day}</option></c:forEach></select> 
																	   <select class="input-mini" name=month><c:forEach var="mon" begin="1" end="12"><option>${mon}</option></c:forEach></select>
																	   <select class="input-small" name="year"><c:forEach var="year" begin="2012" end="2015"><option>${year}</option></c:forEach></select>
				</div>
			<button class="btn btn-primary">Submit</button>
		</form>

		</div>
	</div>
</body>
</html>