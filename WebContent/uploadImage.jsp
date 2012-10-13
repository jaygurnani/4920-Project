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


</head>
<body style="padding-top:60px;">
<%@include file="header.jsp" %>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="page-header">
					<h1>Upload Image</h1>
					<small>Would you like to upload an image for this auction?</small>
			</div>
						
			<form class="form-inline" action="uploadImage" method="post" enctype="multipart/form-data">
			<input type="hidden" name="id" value="${param.id}">
			<label>Image</label> <input type="file" name="image">
			<button class="btn">Upload</button>
			</form>
			
			<a class="btn btn-inverse" href="display?id=${param.id}">No Thanks</a>

		</div>
	</div>
</body>
</html>