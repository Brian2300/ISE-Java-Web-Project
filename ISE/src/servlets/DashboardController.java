package servlets;

import javax.servlet.http.HttpSession;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import dao.DashboardDAO;
import entity.*;

/**
 * Servlet implementation class myQAcoins
 */
@WebServlet("/dashboard")
public class DashboardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardController(){
		super();
		// TODO Auto-generated constructor stub
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String errorMsg = "";
		HttpSession session = request.getSession();
		Student current_stu = (Student)session.getAttribute("student");
		Professor current_professor = (Professor)session.getAttribute("professor");
		if(current_stu!=null) {
			String attendanceData = DashboardDAO.retrieveWeeklyAttendanceByStudent(current_stu.getSmu_email_id());
			request.setAttribute("attendance", attendanceData);
			RequestDispatcher view = request.getRequestDispatcher("dashboardStudent.jsp");//make it as full path
			view.forward(request, response);
		}else if(current_professor!=null) {
			response.sendRedirect("dashboardProfessor.jsp");//make it as full path
		}
		
		response.sendRedirect("myQAcoins.jsp");
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
