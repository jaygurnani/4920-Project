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
		String show;
		Boolean loggedIn;
		String userName;
		
		//check if logged in
		loggedIn = (Boolean)request.getSession().getAttribute("loggedIn");
		if (loggedIn == null) {
			loggedIn = false;
		}
		userName = (String)request.getSession().getAttribute("userName");
		
		//check and get Parameters
		if (!request.getParameterMap().containsKey("id")) {
			request.setAttribute("user", null);
			RequestDispatcher view = request.getRequestDispatcher("displayUser.jsp");
			view.forward(request, response);
			return;
		}
		show = request.getParameter("show");
		//if id isn't a number, don't look up
		try {
			id = Integer.valueOf(request.getParameter("id"));
		} catch (NumberFormatException e) {
			request.setAttribute("user", null);
			RequestDispatcher view = request.getRequestDispatcher("displayUser.jsp");
			view.forward(request, response);
			return;
		}
		
		try {
			//get user and items from database
			db = new Database();
			User user = db.getUserById(id);
			List<Item> items;
			
			//check if user is logged in user and supply appropriate items
			if (loggedIn && user.getName().equals(userName)) {
				request.setAttribute("isUser", true);
				if (show != null && show.equals("1")) {
					request.setAttribute("showSelling", true);
					items = db.getItemsBySeller(user.getId());
				} else if (show != null && show.equals("2")) {
					request.setAttribute("showFinished", true);
					items = db.getFinishedItemsBySeller(user.getId());
				} else {
					request.setAttribute("showWon", true);
					items = db.getItemsByWinner(user.getId());
				}
			} else {
				items = db.getItemsBySeller(user.getId());
			}
			
			//pass user and items to display page
			request.setAttribute("user", user);
			request.setAttribute("itemList", items);
			RequestDispatcher view = request.getRequestDispatcher("displayUser.jsp");
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
