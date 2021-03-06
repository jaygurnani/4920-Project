package main;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Database.Database;
import Database.Item;

public class MakeBid extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//pass POSTed request to GET handler
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		Database db = null;
		int id;
		String bidString;
		int bid;
		int userId = 0;
		Boolean loggedIn;
		String userName;

		//fail if not logged in
		loggedIn = (Boolean)request.getSession().getAttribute("loggedIn");
		if (loggedIn == null || loggedIn == false) {
			response.sendRedirect("display?id="+request.getParameter("id")+"&err=0");
			return;
		}
		userName = (String)request.getSession().getAttribute("userName");
		
		//check and get Parameters (id and bid)
		if (!request.getParameterMap().containsKey("id")) {
			response.sendRedirect("display");
			return;
		} else if (!request.getParameterMap().containsKey("bid")) {
			response.sendRedirect("display?id="+request.getParameter("id")+"&err=1");
			return;
		}
		try {
			id = Integer.valueOf(request.getParameter("id"));
			bidString = request.getParameter("bid");
		} catch (NumberFormatException e) {
			response.sendRedirect("display?id="+request.getParameter("id")+"&err=1");
			return;
		}
		
		//get bid amount as either dollars.cents or dollars 
		if (bidString.contains(".")) {
			bidString = bidString.replaceAll("\\.", "");
			bid = Integer.valueOf(bidString);
		} else {
			bid = Integer.valueOf(bidString) * 100;
		}
		
		try {
			//get item from database
			db = new Database();
			Item item = db.getItemById(id);
			request.setAttribute("item", item);
			
			//validate bid
			if (item == null) {
				response.sendRedirect("display");
				return;		
			} else if (bid < item.getMinBid()) {
				response.sendRedirect("display?id="+id+"&err=2");
				return;
			}
			
			//bid is valid, get userId
			userId = db.getUserByName(userName).getId();
			
			//execute bid
			if (bid > item.getFirstBid()) {
				db.updateItemBids(id, bid, userId, item.getFirstBid(), item.getFirstBidUserId());
			} else if (bid > item.getSecondBid() && (item.getFirstBidUserId() != userId)) {
				db.updateItemBids(id, item.getFirstBid(), item.getFirstBidUserId(), bid, userId);
			}
			//send success message
			response.sendRedirect("display?id="+id+"&bid=" +
					             String.format("%d.%02d", (bid/100), (bid%100)));
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
