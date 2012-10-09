package main;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MakeBid extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//pass POSTed request to GET handler
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		Database db = null;
		int id;
		String bidString;
		int bid;

		//check and get Parameters
		if (!request.getParameterMap().containsKey("id")) {
			response.sendRedirect("display");
			return;
		} else if (!request.getParameterMap().containsKey("bid")) {
			response.sendRedirect("display?id="+request.getParameter("id")+"&err=0");
			return;
		}
		id = Integer.valueOf(request.getParameter("id"));
		bidString = request.getParameter("bid");
		
		//get bid amount as either dollars.cents or dollars 
		if (bidString.contains(".")) {
			bidString = bidString.replaceAll("\\.", "");
			bid = Integer.valueOf(bidString);
		} else {
			bid = Integer.valueOf(bidString) * 100;
		}

		//TODO: fix when auth is setup
		//default user is John Doe
		int userId = 10;
		
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
				response.sendRedirect("display?id="+id+"&err=1");
				return;
			}
			
			//bid is valid, execute it
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
