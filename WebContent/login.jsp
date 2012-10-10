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
				<h1>Login: ${loggedIn}</h1>
				Welcome ${username}<br>
				Hash: ${hash}

			</div>

		<form action="Login" accept-charset="UTF-8" method="post" id="user-login">
		<fieldset><div class="fieldset-wrapper"><div class="form-item" id="edit-name-wrapper">
		<label for="edit-name">Username: <span class="form-required" title="This field is required.">*</span></label>
		<input type="text" maxlength="60" name="username" id="edit-name" size="60" value="" tabindex="1" class="form-text required">

		</div>
		<div class="form-item" id="edit-pass-wrapper">
		<label for="edit-pass">Password: <span class="form-required" title="This field is required.">*</span></label>
		<input type="password" name="password" id="edit-pass" maxlength="128" size="60" tabindex="2" class="form-text required">
		</div>
		</div></fieldset>
		<input type="submit" name="op" id="edit-submit" value="Log in" tabindex="3" class="form-submit">
		<div class="description">New to [THIS_WEB_SITE]? <a href="/signup.jsp" class="ext" target="_blank">Sign up</a><span class="ext"></span></div>
		</form>

		</div>
	</div>

</body>
</html>