package main;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Database.*;


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
		
		boolean authenticated = false;
		
		Hash h = new Hash();
		
		String referer;
		
		//set referer
		referer = request.getParameter("ref");
		if (referer == null || referer.equals("")) {
			referer = request.getHeader("referer");
		}
		
		try {
			//get items from database
			db = new Database();
			
			 if(request.getParameter("username") != null && request.getParameter("password") != null){
				 username = request.getParameter("username");
				 
				 password = h.calculate(request.getParameter("password"));
			 }
			 
			 authenticated = db.checkLogin(username, password);
			 request.getSession().setAttribute("loggedIn", authenticated);
			 
			 if(authenticated){
				 db = new Database();
				 User toEnter = db.getUserByName(username);
				 
				request.getSession().setAttribute("userName", username);

				request.getSession().setAttribute("userId", toEnter.getId());

				
				response.sendRedirect(referer);
			 } else {		    
				//display unsuccessful login page
				request.setAttribute("referer", referer);
				RequestDispatcher view = request.getRequestDispatcher("login.jsp");
				view.forward(request, response);
			 }
		} catch (Exception e) {
			System.out.println("Database error : " + e);
		} finally {
			if (db != null) {
				db.close();
			}
		}
	}

}

