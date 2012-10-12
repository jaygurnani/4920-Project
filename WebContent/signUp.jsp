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
				username: {
					required: true,
					minlength : 6,
				},

				emailaddress : {
					required : true,
					email : true,
				},
				
				paypal : {
					required : true,
					minlength : 6,
				},

				password : {
					required : true,
					minlength : 6,
					equalTo : "#confirmpassword",
				},
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
					<h1>Registration</h1>
					<small>Please enter your details here</small>
			</div>
			
			<form id="register" action="Controller" method="post">
			<div class="control-group">
				<label class="control-label"><h4>Username</h4></label> <input type="text" class="span4" name="username" placeholder="Username"></input>
				<label class="control-label"><h4>Email Address</h4></label> <input type="text" class="span4" name="emailAddress"></input>
				<label class="control-label"><h4>Address</h4></label> <input type="text" class="span4" name="address"></input>
				<label class="control-label"><h4>PayPal Account</h4></label> <input type="text" class="span4" name="paypalAcct"></input>
				<label class="control-label"><h4>Birthday</h4></label> <input type="date" class="span4" name="birthdayDate"></input> 
				<label class="control-label"><h4>About you<br></label><textarea class="span4" rows="4"placeholder="About you" name="about"></textarea>				
				<label class="control-label"><h4>Password</h4></label> <input type="password" class="span4" name="password"></input>
				<label class="control-label"><h4>Confirm Password</h4></label> <input type="password" class="span4" name="confirmpassword" id="confirmpassword"></input>
				<input type="hidden" name="command" value="register"></input>
				</div>
			<button class="btn btn-primary">Submit</button>
		</form>

		</div>
	</div>
</body>
</html>