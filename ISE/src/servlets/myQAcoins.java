package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import dao.ProfessorDAO;
import dao.StudentDAO;
import entity.Professor;
import entity.Student;
import entity.Transaction;

/**
 * Servlet implementation class myQAcoins
 */
@WebServlet("/myQAcoins")
public class myQAcoins extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public myQAcoins(){
		super();
		// TODO Auto-generated constructor stub
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String errorMsg = "";
		HttpSession session = request.getSession();
		ArrayList<Transaction> pendingTxs =(ArrayList<Transaction>)session.getAttribute("pendingTransaction");
		if(pendingTxs == null) {
			errorMsg = "no pending transactions";
			response.sendRedirect("myQAcoins.jsp");
		}
		String txType = request.getParameter("button").substring(0, 8);
		int index = Integer.parseInt(request.getParameter("button").substring(8));
		Transaction pendingTx = pendingTxs.get(index);
		if(txType == null) {
			errorMsg ="type is null";
		}else if(txType.equals("approved")) {
			//QA coins transfer from central pool to B's account, which is originated from A's account
			System.out.println("user pressed approved");
			TransactionController.rewardQa_coins(pendingTx, "approved");
		}else if(txType.equals("rejected")) {
			//A rejects the transaction, pending become rejected, toCentralPool become closed
			System.out.println("user pressed rejected");
			TransactionController.rewardQa_coins(pendingTx, "rejected");
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
