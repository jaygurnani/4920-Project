package main;

import java.io.IOException;
import java.net.URL;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Database.Database;
import Database.Item;
import Database.User;

/**
 * Servlet implementation class PayPalTest
 */
public class ShowFinishedAuction extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Database db = null;
		int id = 0;
		Boolean loggedIn;
		String userName;
		
		//fail if not logged in
		loggedIn = (Boolean)request.getSession().getAttribute("loggedIn");
		if (loggedIn == null || loggedIn == false) {
			request.setAttribute("notLoggedIn", true);
			RequestDispatcher view = request.getRequestDispatcher("displayFinished.jsp");
			view.forward(request, response);
			return;
		}
		userName = (String)request.getSession().getAttribute("userName");
		
		//check and get Parameters
		if (!request.getParameterMap().containsKey("id")) {
			request.setAttribute("item", null);
			RequestDispatcher view = request.getRequestDispatcher("displayFinished.jsp");
			view.forward(request, response);
			return;
		}
		try {
			id = Integer.valueOf(request.getParameter("id"));
		} catch (NumberFormatException e) {
			request.setAttribute("item", null);
			RequestDispatcher view = request.getRequestDispatcher("displayFinished.jsp");
			view.forward(request, response);
			return;
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
						
			//fail if user isn't the winner or seller
			if (item.getOwnerName().equals(userName)) {
				request.setAttribute("isSeller", true);
				
				//get winner from database
				User winner = db.getUserById(item.getFirstBidUserId());
				request.setAttribute("winner", winner);
			} else if (!item.getFirstBidUserName().equals(userName)) {
				request.setAttribute("wrongUser", true);
				RequestDispatcher view = request.getRequestDispatcher("displayFinished.jsp");
				view.forward(request, response);
				return;
			}
			
			
			//get seller from database
			User seller = db.getUserById(item.getOwnerId());
			
			//pass item and seller to display page
			request.setAttribute("item", item);
			request.setAttribute("seller", seller);
			
			RequestDispatcher view = request.getRequestDispatcher("displayFinished.jsp");
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
