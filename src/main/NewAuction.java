package main;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Database.Database;

/**
 * Servlet implementation class NewAuction
 */
public class NewAuction extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Database db = null;
		Boolean loggedIn;
		
		//fail if not logged in
		loggedIn = (Boolean)request.getSession().getAttribute("loggedIn");
		if (loggedIn == null || loggedIn == false) {
			response.sendRedirect("index.jsp");
			return;
		}
		
		//set error text
		if (request.getParameterMap().containsKey("err")) {
			switch (Integer.valueOf(request.getParameter("err"))) {
				case 0:
					request.setAttribute("error", "You must enter an end date in the future.");
					break;
				case 1:
					request.setAttribute("error", "Your minimum bid was invalid.");
					break;
			}
		}
		
		//supply locations and categories
		try {
			db = new Database();
			
			List<String> locations = db.getLocations();
			request.setAttribute("locations", locations);
			
			List<String> categories = db.getCategories();
			request.setAttribute("categories", categories);
			
			RequestDispatcher view = request.getRequestDispatcher("newAuction.jsp");
			view.forward(request, response);
		} catch (Exception e) {
			System.out.println(e);
			RequestDispatcher view = request.getRequestDispatcher("dbError.jsp");
			view.forward(request, response);
		} finally {
			if (db != null) {
				db.close();
			}
		}
	}
}
