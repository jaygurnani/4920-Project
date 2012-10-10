package main;

import java.io.IOException;

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
					request.setAttribute("error", "You didn't supply a bid.");
					break;
				case 1:
					request.setAttribute("error", "That bid was below the minimum bid.");
					break;
				case 2:
					request.setAttribute("error", "That bid was below your previous bid.");
					break;
			}
		} else if (request.getParameterMap().containsKey("bid")) {
			request.setAttribute("successfulBid", request.getParameter("bid"));
		}
		
		try {
			//get item from database
			db = new Database();
			Item item = db.getItemById(id);
			
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
