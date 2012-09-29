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

		//check and get Parameters
		if (!request.getParameterMap().containsKey("id")) {
			request.setAttribute("item", null);
			RequestDispatcher view = request.getRequestDispatcher("displayItem.jsp");
			view.forward(request, response);
			return;
		} else if (!request.getParameterMap().containsKey("bid")) {
			request.setAttribute("error", "You didn't supply a bid.");
			RequestDispatcher view = request.getRequestDispatcher("displayItem.jsp");
			view.forward(request, response);
			return;
		}
		int id = Integer.valueOf(request.getParameter("id"));
		String bidString = request.getParameter("bid").replaceAll("\\.", "");
		int bid = Integer.valueOf(bidString);
		 //TODO: fix when auth is setup
		String user = "Default User";
		
		try {
			//get item from database
			db = new Database();
			Item item = db.getItemById(id);
			request.setAttribute("item", item);
			
			//validate bid
			if (item == null) {
				RequestDispatcher view = request.getRequestDispatcher("displayItem.jsp");
				view.forward(request, response);
				return;		
			} else if (bid < item.getMinBid()) {
				request.setAttribute("error", "That bid was below the minimum bid.");
				RequestDispatcher view = request.getRequestDispatcher("displayItem.jsp");
				view.forward(request, response);
				return;
			} /* else if (user has bid lower previously) {
				request.setAttribute("error", "Sorry! That bid was below your previous bid.");
				RequestDispatcher view = request.getRequestDispatcher("display.jsp");
				view.forward(request, response);
			}*/

			
			//bid is valid, execute it
			if (bid > item.getFirstBid()) {
				item.setSecondBid(item.getFirstBid());
				item.setSecondBidUser(item.getFirstBidUser());
				item.setFirstBid(bid);
				item.setFirstBidUser(user);
				db.updateItem(id, item);
			} else if (bid > item.getSecondBid()) {
				item.setSecondBid(bid);
				item.setSecondBidUser(user);
				db.updateItem(id, item);
			}
			//send success message
			request.setAttribute("successfulBid", 
					             String.format("%d.%02d", (bid/100), (bid%100)));
			RequestDispatcher view = request.getRequestDispatcher("displayItem.jsp");
			view.forward(request, response);
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
