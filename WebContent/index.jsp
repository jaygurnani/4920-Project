<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.sql.*, org.sqlite.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Fire & Ice Auction Website</title>

<!-- Stylesheet -->
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="css/main.css" rel="stylesheet">
<!-- End Stylesheet -->

<!-- Javascript -->
<script src="http://code.jquery.com/jquery-1.8.1.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<!-- End Javascript -->
</head>

<body>
<%@include file="header.jsp" %>
    <div class="container-fluid">
      <div class="row-fluid">
      
      <div class="span2">
      </div>	
      
        <div class="span9">
        	<br>
        	<table>
        		<tr>
        			<td>
        				Photographs
        			</td>
        			<td>
        				<img src="img/database/30.jpg" class=img-rounded"><br>
        				<p><a href="display?id=30">Albert Einstein, with pipe in mouth 192</p></a>
        			</td>
        			<td>
        				<img src="img/database/42.jpg" class=img-rounded"><br>
        				<p><a href="display?id=42">Public fountain,Port-a823u-Prince,Hayti,823W.I.,</p></a>
        			</td>
        			<td>
        				<img src="img/database/62.jpg" class=img-rounded">
        				<p><a href="display?id=62">PIRATES OF THE CARIBBEAN STRANGER SKULL POSTER ST</p></a>
        			</td>
        		</tr>
        		
        		<tr>
        			<td>
        				T-Shirts
        			</td>
        			<td>
        				<img src="img/database/208.jpg" class=img-rounded"><br>
        				<p><a href="display?id=208">Mens Ralph Lauren POLO Shirt BIG PONY NWT - M, L, XL,</p></a>
        			</td>
        			<td>
        				<img src="img/database/231.jpg" class=img-rounded"><br>
        				<p><a href="display?id=231">Element T Shirt - Black - Size</p></a>
        			</td>
        			<td>
        				<img src="img/database/249.jpg" class=img-rounded">
        				<p><a href="display?id=249">TOMMY HILFIGER Polo Shirt Size Med</p></a>
        			</td>
        		</tr>
        		
        		<tr>
        			<td>
        				Fiction Books
        			</td>
        			<td>
        				<img src="img/database/710.jpg" class=img-rounded"><br>
        				<p><a href="display?id=710">Down Under - Bill Bry</p></a>
        			</td>
        			<td>
        				<img src="img/database/725.jpg" class=img-rounded"><br>
        				<p><a href="display?id=725">The Book of Rapture - Nikki Gemm/p></a>
        			</td>
        			<td>
        				<img src="img/database/744.jpg" class=img-rounded">
        				<p><a href="display?id=744">Webster Dictionary Published 1</p></a>
        			</td>
        		</tr>
        		
        		<tr>
        			<td>
        				Phones
        			</td>
        			<td>
        				<img src="img/database/801.jpg" class=img-rounded"><br>
        				<p><a href="display?id=801">New Unlocked ANDROID 2.3 Dual SIM WiFi TV 3.2MP Camera</p></a>
        			</td>
        			<td>
        				<img src="img/database/811.jpg" class=img-rounded"><br>
        				<p><a href="display?id=811">Brand New Motorola Milestone 2 A953 8GB Black 5. MP Mo</p></a>
        			</td>
        			<td>
        				<img src="img/database/826.jpg" class=img-rounded">
        				<p><a href="display?id=826">New SAMSUNG S5511 S5511T Next G Blue Tick - Unlock</p></a>
        			</td>
        		</tr>	
        	
        	</table>
        </div><!--/span-->
      </div><!--/row-->

      <hr>

      <footer>
        <p>&copy; FIRE & ICE 2012</p>
      </footer>

    </div><!--/.fluid-container-->


</body>
</html>