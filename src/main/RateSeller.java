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

public class RateSeller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//pass POSTed request to GET handler
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		Database db = null;
		int id;
		int rating;
		int userId = 0;
		Boolean loggedIn;
		String userName;

		//fail if not logged in
		loggedIn = (Boolean)request.getSession().getAttribute("loggedIn");
		if (loggedIn == null || loggedIn == false) {
			response.sendRedirect(request.getHeader("referer"));
			return;
		}
		userName = (String)request.getSession().getAttribute("userName");
		
		//check and get Parameters (id and rating)
		if (!request.getParameterMap().containsKey("id")) {
			response.sendRedirect(request.getHeader("referer"));
			return;
		} else if (!request.getParameterMap().containsKey("rating")) {
			response.sendRedirect(request.getHeader("referer"));
			return;
		}
		try {
			id = Integer.valueOf(request.getParameter("id"));
			rating = Integer.valueOf(request.getParameter("rating"));
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getHeader("referer"));
			return;
		}
		
		try {
			//get item from database
			db = new Database();
			Item item = db.getItemById(id);

			//get seller from database
			User seller = db.getUserById(item.getOwnerId());
			
			//calculate new rating
			int newRating = ((seller.getRating() * seller.getRatingCount()) + rating) / (seller.getRatingCount() + 1);
			
			//set new rating
			db.updateRating(id, seller.getDetailsId(), newRating, seller.getRatingCount() + 1);
			
			//send success message
			response.sendRedirect("finished?id="+id);
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
