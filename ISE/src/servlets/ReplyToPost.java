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
@WebServlet("/ReplyToPost")
public class ReplyToPost extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int reply_qa_coins = 5;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReplyToPost() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Student student = (Student)session.getAttribute("student");
		Professor professor = (Professor)session.getAttribute("professor");
		int avatar_id=0;
		if(student != null){
			avatar_id = student.getAvatar_id();
			System.out.println(avatar_id);
		}
		
		if(professor != null){
			avatar_id = professor.getAvatar_id();
			System.out.println(avatar_id);
		}
		
		String tempPostID = (String)request.getParameter("postID");
		int post_id = Integer.parseInt(tempPostID);
		String tempPostTitle = request.getParameter("postTitle");
		String tempPostContent = request.getParameter("postContent");
		
        
		String errorMsg = "";
		PostDAO postDAO = new PostDAO();
		
		//System.out.println(post_id +" "+ tempPostTitle+" "+tempPostContent);
		
			
		if(tempPostContent.isEmpty()||tempPostContent == null){
			errorMsg = "Your reply content cannot be empty!";
			RequestDispatcher rd = request.getRequestDispatcher("replyToPost.jsp?post_id="+post_id);
			request.setAttribute("replyToPost", errorMsg);
			rd.forward(request, response);
			return;
		}else{
			
			postDAO.replyToPost(avatar_id, post_id, tempPostTitle, tempPostContent);
			if(student != null){
				// change here make it become pending instead or rewardQa_coins
				System.out.println(TransactionController.requestQa_coins(student,post_id));
				Post post = postDAO.retrievePostbyID(postDAO.lastPostIDofAvatar(avatar_id));
				TransactionController.rewardThoughtfulnessQAcoins(post, student);
				//System.out.println(TransactionController.rewardQa_coins(student,post_id));
			}
			RequestDispatcher rd = request.getRequestDispatcher("viewPost.jsp?post_id="+post_id);
			rd.forward(request, response);
			return;
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
