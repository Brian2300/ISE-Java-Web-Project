package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Student;
import entity.Transaction;

/**
 * Servlet implementation class TransactionController
 */
@WebServlet("/TransactionController")
public class TransactionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransactionController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public static boolean checkSufficientBalance(Student student, double amount) {
		
		return false;
	}
	
	public static void depositQa_coins(Student student, double amount) {
		Transaction tx = new Transaction(post, student, amount, "toCentralPool")
		
	}
	
	public static void closeTransaction(Transaction tx, Student to_stu) {
		
		
	}
	// refundQa_coins should check all transactions and refund all of them
	// retrieve all transactions that are not closed and refund them.
	public static void refundAllQa_coins() {
		
		
	}

	public static void refundQa_coins(Student student, double amount) {
		
		
	}
	
	

}
