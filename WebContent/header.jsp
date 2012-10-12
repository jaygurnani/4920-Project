<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <div class="navbar navbar-inverse navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container-fluid">
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <a class="brand" href="./">FIRE&ICE</a>
          <div class="nav-collapse">

			<ul class="nav pull-right">
				<li class="divider-vertical"></li>
			  	<li>
			  	<form class="navbar-search form-search" action="search" method="get">
			  		<div class="input-append">
	  					<input type="text" class="span2 input-medium search-query" name="q" placeholder="Search for an item...">
	  					<button type="submit" class="btn btn-inverse">Search</button>
  					</div>
  				</form>
  				</li>
          		<li class="divider-vertical"></li>
          		
          		
          		<c:choose>
					<c:when test="${loggedIn}">
						<li><a href="/4920_Project/showUser?id=${userId}"><i class="icon-user icon-white"></i>${userName}</a></li>
						<li><a href="logout">Log out</a></li>
					</c:when>
					<c:otherwise>
						<li class="dropdown">
			            	<a class="dropdown-toggle" href="#" data-toggle="dropdown">Sign In <strong class="caret"></strong></a>
			            	<div class="dropdown-menu" style="padding: 15px; padding-bottom: 0px;">
			            		<!--  Login Form goes here -->
			              			<form action=Controller method ="post">
			              			<i class="icon-user"></i>Username: <input type="text" name="username" /><br />
			              			<i class="icon-asterisk"></i>Password: <input type="password" name ="password"><br />
			              			<input type="hidden" name="command" value="login"></input> 
			              			<input type="submit" value="Login" /></input>
			              			</form>
			            	</div>
		          		</li>
		          		<li><a href="signUp.jsp">Sign Up</a></li>
					</c:otherwise>
				</c:choose>  	     		
          		
       		 </ul>

            <ul class="nav">
              <li class="active"><a href="./">Home</a></li>
              <li><a href="#about">About</a></li>
              <li><a href="#contact">Contact</a></li>
              <li class="dropdown">
	            <a class="dropdown-toggle" href="#" data-toggle="dropdown">Categories <strong class="caret"></strong></a>
	            <ul class="dropdown-menu" role="menu" aria-labelledby="dLabel" style="padding: 15px; padding-bottom: 0px;">
	            	<li><a tabindex = "-1" href="category?cat=Photographs">Photographs</a></li>
	            	<li><a tabindex = "-1" href="category?cat=Speakers">Speakers</a></li>
	            	<li><a tabindex = "-1" href="category?cat=T-Shirts">T-Shirts</a></li>
	            	<li><a tabindex = "-1" href="category?cat=Movies">Movies</a></li>
	            	<li><a tabindex = "-1" href="category?cat=PC Video Games">PC Video Games</a></li>
	            	<li><a tabindex = "-1" href="category?cat=Music">Music</a></li>
	            	<li><a tabindex = "-1" href="category?cat=Cameras">Cameras</a></li>
	            	<li><a tabindex = "-1" href="category?cat=Fiction Books">Fiction Books</a></li>
	            	<li><a tabindex = "-1" href="category?cat=Insturments">Insturments</a></li>
	            </ul>
	          </li>
              	
            </ul>
                      
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>