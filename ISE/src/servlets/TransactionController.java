package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.PostDAO;
import dao.StudentDAO;
import dao.TransactionDAO;
import entity.Post;
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
		return (student.getQa_coins()>=amount);
	}
	
	// every time someone access the QA coins page, this method will be trigered
	public static void batchProcessRefund() {
		
	}
	
	public static void depositQa_coins(Post post, Student student, double amount) {
		student.addQa_coins(-amount);
		Timestamp time_stamp = new Timestamp(System.currentTimeMillis());
		String timestamp =time_stamp.toString();
		Transaction tx = new Transaction(post, student,null, amount, timestamp, "toCentralPool");
		StudentDAO.updateQa_coins(student);
		TransactionDAO.insertTransaction(tx);
	}
	public static String rewardQa_coins(Student to_student, int post_id) {
		//String result = "";
		Double amount = 0.0;
		ArrayList<Transaction> transactions = TransactionDAO.retrieveTransactionByPostID(post_id);
		for(Transaction tx:transactions) {
			if(tx.getType().equals("toCentralPool")&& tx.getAmount()>0) {
				//Transaction(Post post, Student from_stu, Student to_stu, double amount, String timestamp, String type)
				PostDAO post_dao = new PostDAO();
				Post post = post_dao.retrievePostbyID(post_id);
				Student from_student = tx.getFrom_stu();
				Timestamp time_stamp = new Timestamp(System.currentTimeMillis());
				String timestamp =time_stamp.toString();
				amount = tx.getAmount();
				
				
				Transaction transfer = new Transaction(post, from_student, to_student, amount,timestamp, "transfer");
				to_student.addQa_coins(tx.getAmount());
				StudentDAO.updateQa_coins(to_student);
				//TransactionDAO.updateTransaction(tx, "settled");
				TransactionDAO.insertTransaction(transfer);
			}
		}
		
		return ("you are rewarded" + amount);
	}
	
	public static void closeTransaction(Transaction tx, Student to_stu) {
		
		
	}
	// refundQa_coins should check all transactions and refund all of them
	// retrieve all transactions that are not closed and refund them.
	public static boolean refundAllQa_coins() {
		ArrayList<Transaction> allTX = TransactionDAO.retrieveTransactionByType("toCentralPool");
		Timestamp current_time = new Timestamp(System.currentTimeMillis());
		System.out.println("Current time is "+current_time);
		Timestamp oneHourAgo = new Timestamp(current_time.getTime()-3600000);
		System.out.println("One hour before is "+oneHourAgo);
		System.out.println("One hour before is "+oneHourAgo.before(current_time));
		for(Transaction tx: allTX) {
			if(Timestamp.valueOf(tx.getTimestamp()).before(current_time)) {
				System.out.println(Timestamp.valueOf(tx.getTimestamp())+" is before "+current_time);			
				tx.getFrom_stu().addQa_coins(tx.getAmount());
				StudentDAO.updateQa_coins(tx.getFrom_stu());
				TransactionDAO.updateTransactionType(tx,"toCentralPool","refunded");
			}
		}
		
		return false;
		
	}

	public static void refundQa_coins(Student student, double amount) {
		
		
	}
	
	

}
