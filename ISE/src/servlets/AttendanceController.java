
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
@WebServlet("/AttendanceController")
public class AttendanceController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AttendanceController() {
		super();
		// TODO Auto-generated constructor stub
		
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//request.getParameter("dsf");
		String week = request.getParameter("week");
		String group_id = request.getParameter("group_id");
		//System.out.println("weel " + week);
		String[][] attendanceList = AttendanceDAO.retrieveAttendanceByWeek(week);

		request.setAttribute("attendanceList", attendanceList);
    	RequestDispatcher view = request.getRequestDispatcher("attendance.jsp");
        view.forward(request, response);
		
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
