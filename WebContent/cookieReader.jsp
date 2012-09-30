<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*,java.security.MessageDigest,java.security.NoSuchAlgorithmException,java.util.Formatter" %>
    
    
    
    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Fire and Ice's Movie Website</title>

<!-- Stylesheet -->
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
<!-- End Stylesheet -->

<!-- Javascript -->
<script src="http://code.jquery.com/jquery-1.8.1.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<!-- End Javascript -->
</head>

<body>

    <div class="navbar navbar-inverse navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container-fluid">
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <a class="brand" href="#">FIRE&ICE</a>
          <div class="nav-collapse">
          
			<ul class="nav pull-right">
				<li><a href="#">Sign Up</a></li>
          		<li class="divider-vertical"></li>
         		<li class="drop down">
	            	<a class="dropdown-toggle" href="#" data-toggle="dropdown">Sign In <strong class="caret"></strong></a>
	            	<div class="dropdown-menu" style="padding: 15px; padding-bottom: 0px;">
	            		<!--  Login Form goes here -->
	              			<form action="/login.jsp" >
	              			Username: <input type="text" name="username" /><br />
	              			Password: <input type="password" name ="password"><br />
	              			<input type="submit" name="op" id="edit-submit" value="Log in" tabindex="3" class="form-submit">
	              			</form>
	            	</div>
          		</li>
       		 </ul>

            <ul class="nav">
              <li class="active"><a href="#">Home</a></li>
              <li><a href="#about">About</a></li>
              <li><a href="#contact">Contact</a></li>
            </ul>
                      
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>

    <div class="container-fluid">
<br><br><br><hr>

<h1 id="title">DEBUG-Cookie Reader</h1>
<%
		boolean loggedIn = false;
        String userID = "";
		String sessionID = "";

		Cookie[] cookies = null;
		cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
			    Cookie cookie = cookies[i];
			    if ("UID".equals(cookie.getName())){
			    	userID = cookie.getValue();
			    }else if ("SID".equals(cookie.getName())){
			    	sessionID = cookie.getValue();
			    }
			}
		} else {
		    out.println("No cookies founds<br>");
		}

		out.println("UserID: " + userID + "<br>");
		out.println("SessionIO: " + sessionID + "<br>");
%>

      <hr>

      <footer>
        <p>&copy; FIRE & ICE 2012</p>
      </footer>

    </div><!--/.fluid-container-->


</body>
</html>