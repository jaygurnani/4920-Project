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

public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//pass POSTed request to GET handler
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	//search is expected to GET requests, so users can pass around URLs and not lose results, etc
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		Database db = null;		
		List<Item> items;
		int sortBy = 0;
		
		//get sortBy value if it exists, if it's an invalid value, use default
		if (request.getParameter("sortBy") != null) {
			try {
				sortBy = Integer.valueOf(request.getParameter("sortBy"));
			} catch (NumberFormatException e) {
				//do nothing
			}
		}
		
		try {
			//get items from database
			db = new Database();
			
			switch (sortBy) {
				case 1:
					items = db.sortedSearch(request.getParameter("q"), "Description");
					break;
				case 2:
					items = db.sortedSearch(request.getParameter("q"), "endDate");
					break;
				case 3:
					items = db.sortedSearch(request.getParameter("q"), "ownerName");
					break;
				default:
					items = db.search(request.getParameter("q"));
			}
			
			//pass items to results
			request.setAttribute("itemList", items);
			RequestDispatcher view = request.getRequestDispatcher("searchResults.jsp");
			view.forward(request, response);
		} catch (Exception e) {
			System.out.println("Database error : " + e);
			RequestDispatcher view = request.getRequestDispatcher("dbError.jsp");
			view.forward(request, response);
		} finally {
			if (db != null) {
				db.close();
			}
		}
	}
}
