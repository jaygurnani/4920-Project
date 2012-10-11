package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Controller
 */
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
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
		String command = request.getParameter("command");
		System.out.println("Command = " + command);
		
		if(command.equals("register"))	{
			//Forward to register
			getServletContext().getRequestDispatcher("/Register").forward(request, response);
		} else if (command.contains("login")){
			//Forward to Login
			getServletContext().getRequestDispatcher("/Login").forward(request, response);
			
			
			
		} else {
			//NO SPECIFIC COMMAND, SEND BACK TO INDEX.
			response.sendRedirect("index.jsp");
			return;
		}
		
	}

}
