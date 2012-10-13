package main;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Database.Database;
import Database.Item;
import Database.User;

public class DeleteAuction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//pass POSTed request to GET handler
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	               throws ServletException, IOException {
		Database db = null;
		int id;
		Boolean loggedIn;
		String userName;

		//fail if not logged in
		loggedIn = (Boolean)request.getSession().getAttribute("loggedIn");
		if (loggedIn == null || loggedIn == false) {
			response.sendRedirect(request.getHeader("referer"));
			return;
		}
		userName = (String)request.getSession().getAttribute("userName");
		
		//check and get id)
		if (!request.getParameterMap().containsKey("id")) {
			response.sendRedirect(request.getHeader("referer"));
			return;
		}
		try {
			id = Integer.valueOf(request.getParameter("id"));
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getHeader("referer"));
			return;
		}
				
		try {
			//get item from database
			db = new Database();
			Item item = db.getItemById(id);
			
			//get user id
			User user = db.getUserByName(userName);
			
			//fail if user isn't owner
			if (item.getOwnerId() != user.getId()) {
				response.sendRedirect(request.getHeader("referer"));
				return;
			}
			
			//delete item
			db.deleteItemById(id);

			//send success message
			response.sendRedirect("showUser?id="+user.getId()+"&deleted="+item.getDescription());
		} catch (Exception e) {
			System.out.println("Database error: " + e);
			RequestDispatcher view = request.getRequestDispatcher("dbError.jsp");
			view.forward(request, response);
		} finally {
			if (db != null) {
				db.close();
			}
		}
	}
}
