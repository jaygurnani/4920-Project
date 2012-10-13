package main;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import Database.Database;
import Database.Item;

/**
 * Servlet implementation class UploadImage
 */
public class UploadImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher("uploadImage.jsp");
		view.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id = 0;
		Boolean loggedIn;
		String userName;
		Database db;
		
		//fail if not multipart data
		if (!ServletFileUpload.isMultipartContent(request)) {
			RequestDispatcher v = request.getRequestDispatcher("img.jsp");
			v.forward(request, response);
			return;
		}
		
		//fail if not logged in
		loggedIn = (Boolean)request.getSession().getAttribute("loggedIn");
		if (loggedIn == null || loggedIn == false) {
			response.sendRedirect("index.jsp");
			return;
		}
		userName = (String)request.getSession().getAttribute("userName");
				
		
		//Set up ServletFileUpload
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		//Get path of image directory
		String path = getServletContext().getRealPath("img/database/");		

		try {
			List<FileItem> formFields = upload.parseRequest(request);
			//find the formField that is a file
			for (FileItem item : formFields) {
				if (item.getFieldName().equals("id")) {
					id = Integer.valueOf(item.getString());
					
					//fail if not the user who owns the item
					db = new Database();
					Item auction = db.getItemById(id);
					if (!auction.getOwnerName().equals(userName)) {
						response.sendRedirect("index.jsp");
						return;
					}
				}
				if (!item.isFormField()) {
					if (id == 0) {
						throw new Exception();
					}
					File imageFile = new File(path+"/"+id+".jpg");
					item.write(imageFile);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			RequestDispatcher view = request.getRequestDispatcher("dbError.jsp");
			view.forward(request, response);
			return;
		}
		
		response.sendRedirect("display?id="+id);
		return;
	}

}
