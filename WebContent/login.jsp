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
<%
        String username = "";
		String password = "";
		String hash = "";
		String salt = "blaaaaaaaaaaaaaaaaaaaaaaaaaaaargh, GET THIS OUT OF THE SOURCE CODE, STORE SOMEWHERE SAFE, perhaps make random";
		int hashIterations = 100;
		
		byte[] saltB = salt.getBytes("utf8");
        if(request.getParameter("username") == null)
                username = "";
        else
                username = request.getParameter("username");
                if(request.getParameter("password") == null)
                password = "";
        else
                password = request.getParameter("password");
                MessageDigest cript = MessageDigest.getInstance("SHA-1");
                cript.reset();
                cript.update(saltB);
                //hash = new String(Hex.encodeHex(cript.digest()), CharSet.forName("UTF-8"));
                byte[] digest = cript.digest(password.getBytes("utf8"));
                
                for(int i = 0; i < hashIterations; i++){
                	cript.reset();
                    cript.update(saltB);
                    digest = cript.digest(digest);
                }
                for(int i = 0; i < digest.length; i++){
                	hash += (String.format("%02X", digest[i]) );
                }
                //hash = new BigInteger(1, cript.digest()).toString(16);
                
                
        out.println("Username: " + request.getParameter("username") + "<br>");
        out.println("Password: " + request.getParameter("password") + "<br>");
        out.println("Hash: " + hash + "<br>");
%>

<h1 id="title">Sign in</h1>
<div id="content-inner">
        <div id="content-main">
<form action="/4920_Project/login.jsp" accept-charset="UTF-8" method="post" id="user-login">
<fieldset><div class="fieldset-wrapper"><div class="form-item" id="edit-name-wrapper">
 <label for="edit-name">Username: <span class="form-required" title="This field is required.">*</span></label>
 <input type="text" maxlength="60" name="username" id="edit-name" size="60" value="" tabindex="1" class="form-text required">
 <% if(username.equals(""))	out.println("<font color=red>Please enter your username.</font>");%>
</div>
<div class="form-item" id="edit-pass-wrapper">
 <label for="edit-pass">Password: <span class="form-required" title="This field is required.">*</span></label>
 <input type="password" name="password" id="edit-pass" maxlength="128" size="60" tabindex="2" class="form-text required">
 <% if(password.equals(""))	out.println("<font color=red>Please enter your password.</font>");%>
</div>
</div></fieldset>
<input type="submit" name="op" id="edit-submit" value="Log in" tabindex="3" class="form-submit">
<div class="description">New to [THIS_WEB_SITE]? <a href="/signup.jsp" class="ext" target="_blank">Sign up</a><span class="ext"></span></div>
</form>
        </div>
      </div>
      
      
      <hr>

      <footer>
        <p>&copy; FIRE & ICE 2012</p>
      </footer>

    </div><!--/.fluid-container-->


</body>
</html>