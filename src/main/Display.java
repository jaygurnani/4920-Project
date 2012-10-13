package main;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Database.Database;
import Database.Item;

public class Display extends HttpServlet {
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
			request.setAttribute("item", null);
			RequestDispatcher view = request.getRequestDispatcher("displayItem.jsp");
			view.forward(request, response);
			return;
		}
		//if id isn't a number, don't look up
		try {
			id = Integer.valueOf(request.getParameter("id"));
		} catch (NumberFormatException e) {
			request.setAttribute("item", null);
			RequestDispatcher view = request.getRequestDispatcher("displayItem.jsp");
			view.forward(request, response);
		}
		
		//Handle messages (from MakeBid)
		if (request.getParameterMap().containsKey("err")) {
			switch (Integer.valueOf(request.getParameter("err"))) {
				case 0:
					request.setAttribute("error", "You're not logged in.");
					break;
				case 1:
					request.setAttribute("error", "Your bid was invalid.");
					break;
				case 2:
					request.setAttribute("error", "That bid was below the minimum bid.");
					break;
			}
		} else if (request.getParameterMap().containsKey("bid")) {
			request.setAttribute("successfulBid", request.getParameter("bid"));
		}
		
		//Check existence of image
		URL imgUrl = getServletContext().getResource("/img/database/"+id+".jpg");
		if (imgUrl != null) {
			request.setAttribute("img", "img/database/"+id+".jpg");
		} else {
			request.setAttribute("img", "img/missing.png");
		}
		
		
		try {
			//get item from database
			db = new Database();
			Item item = db.getItemById(id);
			
			//check if user is the owner of the auction
			if (loggedIn && item.getOwnerName().equals(userName)) {
				request.setAttribute("isOwner", true);
			}
			
			//pass item to display page
			request.setAttribute("item", item);
			RequestDispatcher view = request.getRequestDispatcher("displayItem.jsp");
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
