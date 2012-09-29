package main;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Category extends HttpServlet {                               
	private static final long serialVersionUID = 1L; 
	
	//pass POSTed request to GET handler
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	//category is expected to GET requests, so users can pass around URLs and not lose results, etc
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		//check and get Parameters
		if (!request.getParameterMap().containsKey("cat")) {
			request.setAttribute("itemList", null);
			RequestDispatcher view = request.getRequestDispatcher("displayCategory.jsp");
			view.forward(request, response);
			return;
		}
		String category = request.getParameter("cat");
		
		Database db = null;
		
		try {
			//get items from database
			db = new Database();
			List<Item> items = db.getItemsByCategory(category);
			db.close();
			
			//pass items to display page
			request.setAttribute("itemList", items);			
			RequestDispatcher view = request.getRequestDispatcher("displayCategory.jsp");
			view.forward(request, response);
		} catch (Exception e) {
			System.out.println("Database error: " + e);
		} finally {
			if (db != null) {
				db.close();
			}
		}
	}
}
