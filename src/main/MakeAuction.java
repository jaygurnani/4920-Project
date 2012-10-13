package main;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Database.Database;

/**
 * Servlet implementation class MakeAuction
 */
public class MakeAuction extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Database db = null;
		
		Boolean loggedIn;
		String userName;
		int userId;
		
		int itemId;
		String name;
		String description;
		String category;
		String shipsFrom;
		int shipsFromIndex;
		String shipsTo;
		int shipsToIndex;
		String minBidString;
		int minBid;
		String year;
		String month;
		String day;
		Date endDate;
		
		//build fail redirect string
		String failRedirect = "newAuction?";
		for (Object param :request.getParameterMap().keySet()) {
			failRedirect += param + "=" + request.getParameter((String)param) + "&";
		}

		//fail if not logged in
		loggedIn = (Boolean)request.getSession().getAttribute("loggedIn");
		if (loggedIn == null || loggedIn == false) {
			response.sendRedirect(failRedirect);
			return;
		}
		userName = (String)request.getSession().getAttribute("userName");
		
		//check and get Parameters
		if (!request.getParameterMap().containsKey("name")) {
			response.sendRedirect(failRedirect);
			return;
		} else if (!request.getParameterMap().containsKey("description")) {
			response.sendRedirect(failRedirect);
			return;
		} else if (!request.getParameterMap().containsKey("category")) {
			response.sendRedirect(failRedirect);
			return;
		} else if (!request.getParameterMap().containsKey("shipsFrom")) {
			response.sendRedirect(failRedirect);
			return;
		} else if (!request.getParameterMap().containsKey("shipsTo")) {
			response.sendRedirect(failRedirect);
			return;
		} else if (!request.getParameterMap().containsKey("minBid")) {
			response.sendRedirect(failRedirect);
			return;
		} else if (!request.getParameterMap().containsKey("day")) {
			response.sendRedirect(failRedirect);
			return;
		} else if (!request.getParameterMap().containsKey("month")) {
			response.sendRedirect(failRedirect);
			return;
		} else if (!request.getParameterMap().containsKey("year")) {
			response.sendRedirect(failRedirect);
			return;
		}
		name = request.getParameter("name");
		description = request.getParameter("description");
		category = request.getParameter("category");
		minBidString = request.getParameter("minBid");
		shipsTo = request.getParameter("shipsTo");
		shipsFrom = request.getParameter("shipsTo");
		year = request.getParameter("year");
		month = request.getParameter("month");
		day = request.getParameter("day");
		try {
			endDate = df.parse(year+"-"+month+"-"+day);
			
		} catch (Exception e) {
			response.sendRedirect(failRedirect);
			return;
		}
		try {
			//get bid amount as either dollars.cents or dollars 
			if (minBidString.contains(".")) {
				minBidString = minBidString.replaceAll("\\.", "");
				minBid = Integer.valueOf(minBidString);
			} else {
				minBid = Integer.valueOf(minBidString) * 100;
			}
		} catch (Exception e) {
			response.sendRedirect(failRedirect+"err=1");
			return;
		}
		//fail if date is before today
		if (endDate.before(new Date())) {
			response.sendRedirect(failRedirect+"err=0");
			return;
		}
		
		//insert into database
		try {
			db = new Database();
			
			//verify shipsTo and shipsFrom
			shipsToIndex = db.getLocationIndex(shipsTo);
			shipsFromIndex = db.getLocationIndex(shipsFrom);
			if (shipsToIndex == 0 || shipsFromIndex == 0) {
				response.sendRedirect(failRedirect);
				return;
			}
			
			userId = db.getUserByName(userName).getId();
			itemId = db.createNewAuction(name, minBid, shipsFromIndex, shipsToIndex, 
					                     category, new Date(), endDate, userId);
			response.sendRedirect("uploadImage?id="+itemId);
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
