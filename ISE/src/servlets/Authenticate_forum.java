package servlets;

import java.io.*;
import dao.*;
import entity.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Authenticate
 */
@WebServlet("/Authenticate_forum")
public class Authenticate_forum extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Authenticate_forum() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String msg = "";
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		Boolean isStudent = email.substring(0,email.indexOf("@")).matches(".*\\d");
		String profRegMsg ="";
       if(!isStudent){
    	   ProfessorDAO pDAO = new ProfessorDAO();
   		   profRegMsg = pDAO.registerProfessor(email,password); 
       }
		
		
		ForumUser fu = new ForumUser(email,username,password);
		ForumUserDAO fuDao = new ForumUserDAO();
		int emailResult = fuDao.retrieveForumUserWithEmail(email);
		int usernameResult = fuDao.retrieveForumUserWithUsername(username);
		
		if (username.length()<3 || username.length()>20){
			msg = "Please enter the username with the right format!";
			RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
			request.setAttribute("rmsg", msg);
			rd.forward(request, response);
		}
		
		if (password.length()<6 || password.length()>100){
			msg = "Please enter the password with the right format!";
			RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
			request.setAttribute("rmsg", msg);
			rd.forward(request, response);
		}
		
		if(!profRegMsg.equals("")){
			msg = profRegMsg;
			RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
			request.setAttribute("rmsg", msg);
			rd.forward(request, response);
		}
		
		if (emailResult == 0 && usernameResult == 0){
			HttpSession session = request.getSession();
			session.setAttribute("forumUser", fu);
			if(isStudent){
				response.sendRedirect("homeStudent.jsp");
			}else{
			response.sendRedirect("home.jsp");
			}
			
		}else{
			msg = "username/email already exists!";
			RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
			request.setAttribute("rmsg", msg);
			rd.forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
