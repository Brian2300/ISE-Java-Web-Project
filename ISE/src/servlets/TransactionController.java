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
	public static String rewardThoughtfulnessQAcoins(Post post, Student to_student) {
		float thoughtfulnessScore = post.getThoughfulness_score();
		//below formula can be changed
		double rewardQa_coins =5.1 + thoughtfulnessScore * 1.0;
		
		Timestamp time_stamp = new Timestamp(System.currentTimeMillis());
		String timestamp =time_stamp.toString();
		Student from_stu = null;
		Transaction tx = new Transaction(post,from_stu,to_student,rewardQa_coins, timestamp,"sysReward");
		TransactionDAO.insertTransaction(tx);
		to_student.addQa_coins(rewardQa_coins);
		StudentDAO.updateQa_coins(to_student);
		
		return"done";
	}
	public static boolean checkSufficientBalance(Student student, double amount) {
		return (student.getQa_coins()>=amount);
	}
	
	// every time someone access the QA coins page, this method will be trigered
	
	
	public static double getRewardQa_coins(Post post) {
		ArrayList<Transaction> transactions = TransactionDAO.retrieveTransactionByPostID(post.getPost_id());
		double rewardQa_coins =0.0;
		for(Transaction tx: transactions) {
			if(tx.getType().equals("toCentralPool")){
				rewardQa_coins = tx.getAmount();
			}
		}
		return rewardQa_coins;
	}
	public static void depositQa_coins(Post post, Student student, double amount) {
		student.addQa_coins(-amount);
		Timestamp time_stamp = new Timestamp(System.currentTimeMillis());
		String timestamp =time_stamp.toString();
		Transaction tx = new Transaction(post, student,null, amount, timestamp, "toCentralPool");
		StudentDAO.updateQa_coins(student);
		TransactionDAO.insertTransaction(tx);
	}
	public static String requestQa_coins(Student to_student, int post_id, int reply_post_id) {
		//String result = "";
		Double amount = 0.0;
		ArrayList<Transaction> transactions = TransactionDAO.retrieveTransactionByPostID(post_id);
		for(Transaction tx:transactions) {
			if(tx.getType().equals("toCentralPool")&& tx.getAmount()>0) {
				//Transaction(Post post, Student from_stu, Student to_stu, double amount, String timestamp, String type)
				PostDAO post_dao = new PostDAO();
				Post post = post_dao.retrievePostbyID(reply_post_id);
				Student from_student = tx.getFrom_stu();
				Timestamp time_stamp = new Timestamp(System.currentTimeMillis());
				String timestamp =time_stamp.toString();
				amount = tx.getAmount();
				Transaction txRequest = new Transaction(post, from_student, to_student, amount,timestamp, "pending");
				TransactionDAO.insertTransaction(txRequest);
			}
		}
		
		return ("you are requesting " + amount);
	}
	
	public static String rewardQa_coins(Transaction pendingTx, String type) {
		String result = "";
		if(pendingTx == null) {
			return "Err pendingTx is null";
		}
		Student from_student = pendingTx.getFrom_stu();
		Student to_student = pendingTx.getTo_stu();
		Post post = pendingTx.getPost();
		Double amount = pendingTx.getAmount();
		
		Timestamp current_time = new Timestamp(System.currentTimeMillis());
		Timestamp timeLimit = new Timestamp(current_time.getTime()-3600000*24);
		
		if(type.equals("approved")) {
			//QA coins transfer from central pool to B's account, which is originated from A's account
			Transaction approvedTx = new Transaction(post, from_student, to_student, amount,current_time.toString(),"approved");
			TransactionDAO.insertTransaction(approvedTx);
			TransactionDAO.updateTransactionType(pendingTx, "pending", "closed");
			//transfer the QA coins to to-student
			to_student.addQa_coins(amount);
			StudentDAO.updateQa_coins(to_student);
			// update the toCentralPool transaction to closed
			Transaction tx = TransactionDAO.retrieveTransactionByPostIDandType(post.getParent_id(),"toCentralPool");
			
			TransactionDAO.updateTransactionType(tx, "toCentralPool", "closed");
			//close other pending transactions
			
			int post_id = tx.getPost().getPost_id();
			PostDAO postDAO = new PostDAO();
			ArrayList<Post> childrenPosts = postDAO.retrieveChildPost(post_id);
			for(Post childPost:childrenPosts) {
				ArrayList<Transaction>otherRelatedTransactions = TransactionDAO.retrieveTransactionByPostID(childPost.getPost_id());
				for(Transaction relatedTx: otherRelatedTransactions) {
					if (relatedTx.getType().equals("pending")){
						TransactionDAO.updateTransactionType(relatedTx, "pending", "closed");
					}
				}
			}
			
			
		}else if(type.equals("rejected")) {
			Transaction approvedTx = new Transaction(post, from_student, to_student, amount,current_time.toString(),"rejected");
			TransactionDAO.insertTransaction(approvedTx);
			TransactionDAO.updateTransactionType(pendingTx, "pending", "closed");
			amount = 0.0;
		}else {
			result ="type not valid";
		}
		
		return ("you are rewarded" + amount);
	}
	

	// refundQa_coins should check all transactions and refund all of them
	// retrieve all transactions that are not closed and refund them.
	public static void refundAllQa_coins() {
		// this method should also credit all QA coins to B is A didn't apporve within 24 hours
		ArrayList<Transaction> allTX = TransactionDAO.retrieveTransactionByType("toCentralPool");
		Timestamp current_time = new Timestamp(System.currentTimeMillis());
		Timestamp oneHourAgo = new Timestamp(current_time.getTime()-3600000*24);//it is 1 day now
		for(Transaction tx: allTX) {
			if(Timestamp.valueOf(tx.getTimestamp()).before(oneHourAgo)) {			
				tx.getFrom_stu().addQa_coins(tx.getAmount());
				StudentDAO.updateQa_coins(tx.getFrom_stu());
				TransactionDAO.updateTransactionType(tx,"toCentralPool","refunded");
			}
		}		
	}
	public static void clearAllPendingTransactions() {
		// this method clear all pending transactions if there is no action taken against it
		// will auto credit to B's account after 24 hours
		ArrayList<Transaction> allTX = TransactionDAO.retrieveTransactionByType("pending");
		Timestamp current_time = new Timestamp(System.currentTimeMillis());
		Timestamp oneHourAgo = new Timestamp(current_time.getTime()-3600000*24);//it is 1 day now
		for(Transaction pendingTx: allTX) {
			if(Timestamp.valueOf(pendingTx.getTimestamp()).before(oneHourAgo)) {		
				Student from_student = pendingTx.getFrom_stu();
				Student to_student = pendingTx.getTo_stu();
				Post post = pendingTx.getPost();
				Double amount = pendingTx.getAmount();
				pendingTx.getTo_stu().addQa_coins(amount);
				StudentDAO.updateQa_coins(pendingTx.getTo_stu());
				Transaction approvedTx = new Transaction(post, from_student, to_student, amount,current_time.toString(),"approved");
				TransactionDAO.insertTransaction(approvedTx);
				TransactionDAO.updateTransactionType(pendingTx,"pending","closed");
				Transaction tx = TransactionDAO.retrieveTransactionByPostIDandType(post.getPost_id(),"toCentralPool");
				TransactionDAO.updateTransactionType(tx, "toCentralPool", "closed");
				
			}
		}		
	}

}
