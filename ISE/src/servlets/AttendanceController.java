
package servlets;

import java.io.*;
import java.util.ArrayList;

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
	public static void printt(String[][] s) {
		for(String[]ss:s) {
			for(String sss: ss) {
				System.out.print("["+sss+"]");
			}
			System.out.println();
		}
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//request.getParameter("dsf");
		//String week = request.getParameter("week");
		String group_id = request.getParameter("group_id");
		//System.out.println("weel " + week);
		String[][]class_list =AttendanceDAO.retrieveClasslistByGroup(group_id);
		System.out.println("class_list is"+class_list.length);
		printt(class_list);
		//String[][] attendanceList = AttendanceDAO.retrieveAttendanceByWeek(week);
		 ArrayList<String[]> attendanceTable = new ArrayList<String[]>();
		 
		 //attendanceTable.add(AttendanceDAO.retrieveClasslistByGroup(group_id))
		String[][] weeks = AttendanceDAO.retrieveDistinctWeeks();
		System.out.println("weeks length"+weeks[0]);
		String[][] attendanceStringTable;
		//int numWeeks = weeks.length;
		
		for(String[]stu: class_list) {
			ArrayList<String> stu_attendance= new ArrayList<String>();
			for(String[]week:weeks) {
				String[][] attendance = AttendanceDAO.retrieveAttendanceByStudentAndWeek(stu[1], week[0]);
				System.out.println("stu and week" +stu[1] + week[0]);
				System.out.println("attendance" +attendance[0][0]);
				stu_attendance.add(attendance[0][0]);
			}
			attendanceTable.add(stu_attendance.toArray(new String[0]));
		}
		attendanceStringTable =attendanceTable.toArray(new String[0][0]);
		System.out.println("the size of attendance table is "+attendanceStringTable.length);
		request.setAttribute("attendanceList", attendanceStringTable);
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
