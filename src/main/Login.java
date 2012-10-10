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

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Database db = null;
		
		String username = "";
		String password = "";
		
		Hash h = new Hash();
		
		
		try {
			//get items from database
			db = new Database();
			
			 if(request.getParameter("username") != null && request.getParameter("password") != null){
				 username = request.getParameter("username");
				 
				 password = h.calculate(request.getParameter("password"));
			 }
			 
			 
			request.setAttribute("loggedIn", db.checkLogin(username, password));
			request.setAttribute("user", username);
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
