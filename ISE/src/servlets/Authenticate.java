package servlets;

import java.io.*;
import java.util.UUID;

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

import msAuth.AuthHelper;

/**
 * Servlet implementation class Authenticate
 */
@WebServlet("/Authenticate")
public class Authenticate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Authenticate() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String errorMsg = "";
		String emailID = request.getParameter("emailID");
		String password = request.getParameter("password");
		
		StudentDAO studentDAO = new StudentDAO();
		Student student = studentDAO.retrieveStudent(emailID, password);
		
		ProfessorDAO professorDAO = new ProfessorDAO();
		Professor professor = professorDAO.retrieveProfessor(emailID, password);
		
    	UUID state = UUID.randomUUID();
  	  	UUID nonce = UUID.randomUUID();
  	  	
  	  	// Save the state and nonce in the session so we can
  	  	// verify after the auth process redirects back
  	  	HttpSession session = request.getSession();
  	  	session.setAttribute("expected_state", state);
  	  	session.setAttribute("expected_nonce", nonce);
  	  	
  	  	String loginUrl = AuthHelper.getLoginUrl(state, nonce);
  	  	session.setAttribute("loginUrl", loginUrl);
		
		if (student != null) {
			session.setAttribute("student", student);
			response.sendRedirect("homeStudent.jsp");

		} else if (professor != null){
			session.setAttribute("professor", professor);
			response.sendRedirect("home.jsp");
		
		} else {
			errorMsg = "Invalid username/password";
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			request.setAttribute("error", errorMsg);
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
