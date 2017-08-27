package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import msAuth.Event;
import msAuth.FullCalendarEvent;

import java.util.*;


/**
 * Servlet implementation class Calendarjson
 */
@WebServlet("/Calendarjson")
public class Calendarjson extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Calendarjson() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		
		HttpSession session = request.getSession();
		Event[] arrEvents = (Event[]) session.getAttribute("events");
		
		System.out.println("This is Events: " + arrEvents);
		FullCalendarEvent fcEvent = new FullCalendarEvent();
		for(Event event: arrEvents){
			fcEvent.setId(event.getId());
			fcEvent.setTitle(event.getSubject());
			fcEvent.setStart(event.getStart().toString());
			fcEvent.setEnd(event.getEnd().toString());
			list.add(fcEvent);	
		}
		
		FullCalendarEvent c = new FullCalendarEvent();
		c.setId("1");
		c.setStart("2017-08-28");
		c.setEnd("2017-08-29");
		c.setTitle("Task in Progress");
		list.add(c);	
		 
		 response.setContentType("application/json");
		 response.setCharacterEncoding("UTF-8");
		 PrintWriter out = response.getWriter();
		 out.write(new Gson().toJson(list));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
