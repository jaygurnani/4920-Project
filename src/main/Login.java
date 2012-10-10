package main;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	private static String salt = "blaaaaaaaaaaaaaaaaaaaaaaaaaaaargh, GET THIS OUT OF THE SOURCE CODE, STORE SOMEWHERE SAFE, perhaps make random";
	private static int hashIterations = 100;
	
	private String hash(String in) throws UnsupportedEncodingException, NoSuchAlgorithmException{
		String hash = "";
		
		byte[] saltB = salt.getBytes("utf8");
				
		MessageDigest cript = MessageDigest.getInstance("SHA-1");
        cript.reset();
        cript.update(saltB);
        //hash = new String(Hex.encodeHex(cript.digest()), CharSet.forName("UTF-8"));
        byte[] digest = cript.digest(in.getBytes("utf8"));
        
        for(int i = 0; i < hashIterations; i++){
        	cript.reset();
            cript.update(saltB);
            digest = cript.digest(digest);
        }
        for(int i = 0; i < digest.length; i++){
        	hash += (String.format("%02X", digest[i]) );
        }
        
		return hash;		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Database db = null;
		
		String username = "";
		String password = "";
		
		
		
		try {
			//get items from database
			db = new Database();
			
			 if(request.getParameter("username") != null && request.getParameter("password") != null){
				 username = request.getParameter("username");
				 password = hash(request.getParameter("password"));
			 }
			 
			 
			request.setAttribute("loggedIn", db.checkLogin(username, password));
			request.setAttribute("username", username);
			RequestDispatcher view = request.getRequestDispatcher("login.jsp");
			view.forward(request, response);

		} catch (Exception e) {
			System.out.println("Database error : " + e);
		} finally {
			if (db != null) {
				db.close();
			}
		}
	}

}
