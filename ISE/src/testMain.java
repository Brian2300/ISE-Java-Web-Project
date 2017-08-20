

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Timer;
import java.io.*;
import dao.*;
import entity.*;
import utility.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class QRgenerate
 */
@WebServlet("/aaaaa")
public class testMain extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public testMain() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String errorMsg = "";
		String successMsg ="";
		String qr = request.getParameter("QRcode");
		String passcode = RandomString.generateRandomString(8);	
		
	

		if(qr.equals("QRcode")){
			if(QRcode.generateQRcode(passcode)){
				errorMsg = "error";
				RequestDispatcher rd = request.getRequestDispatcher("attendance.jsp");
				request.setAttribute("error", errorMsg);
				request.setAttribute("passcode", passcode);
				rd.forward(request, response);
			}else{
				successMsg = "QR Successful!";
				RequestDispatcher rd = request.getRequestDispatcher("attendance.jsp");
				request.setAttribute("success", successMsg);
				request.setAttribute("passcode", passcode);
				rd.forward(request, response);
			}
			
		}
	}

	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
