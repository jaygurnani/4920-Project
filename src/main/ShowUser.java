package main;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Database.Database;
import Database.Item;
import Database.User;

public class ShowUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//pass POSTed request to GET handler
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	//display is expected to GET requests, so users can pass around URLs and not lose results, etc
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		Database db = null;
		int id = 0;
		
		//check and get Parameters
		if (!request.getParameterMap().containsKey("id")) {
			System.out.println("no id");
			request.setAttribute("user", null);
			RequestDispatcher view = request.getRequestDispatcher("displayUser.jsp");
			view.forward(request, response);
			return;
		}
		//if id isn't a number, don't look up
		try {
			id = Integer.valueOf(request.getParameter("id"));
		} catch (NumberFormatException e) {
			System.out.println("INvalid id");
			request.setAttribute("user", null);
			RequestDispatcher view = request.getRequestDispatcher("displayUser.jsp");
			view.forward(request, response);
			return;
		}
		
		try {
			//get user and items from database
			db = new Database();
			User user = db.getUserById(id);
			List<Item> items = db.getItemsBySeller(user.getId());
	
			System.out.println(user + ", " + items);
			
			//pass user and items to display page
			request.setAttribute("user", user);
			request.setAttribute("itemList", items);
			RequestDispatcher view = request.getRequestDispatcher("displayUser.jsp");
			view.forward(request, response);
		} catch (Exception e) {
			RequestDispatcher view = request.getRequestDispatcher("dbError.jsp");
			view.forward(request, response);
		} finally {
			if (db != null) {
				db.close();
			}
		}
	}
}
