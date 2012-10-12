package servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Database.Database;

/**
 * Servlet implementation class Register
 */
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Database db = null;
		int count = 0;
		
		String username = (String) request.getParameter("username");
		String password = (String) request.getParameter("password");
		String paypal = (String) request.getParameter("paypalAcct");
		String email = (String) request.getParameter("emailAddress");
		String birthday = (String) request.getParameter("birthdayDate");
		String address = (String) request.getParameter("address");
		String about = (String) request.getParameter("about");
		
		try {
			db = new Database();
			count = db.countUsers();
			//Shift count by one more for new User;
			count++;
			db.createNewUser(count,username,password,paypal,email,birthday,address,about);
			response.sendRedirect("index.jsp");
			
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
