package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.PostDAO;
import dao.StudentDAO;
import dao.TagDAO;
import dao.TransactionDAO;
import entity.Post;
import entity.Professor;
import entity.Student;
import entity.Transaction;

/**
 * Servlet implementation class PostNewQuestion
 */
@WebServlet("/PostNewQuestion")
public class PostNewQuestion extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final double post_qa_coins = 5;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostNewQuestion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		
		//int avatar_id = 1;
		String post_title =  request.getParameter("postTitle");
		// check error here
		String reward_qa_coins_string = request.getParameter("reward_qa_coins");
		double reward_qa_coins =0;
		if(reward_qa_coins_string!=null) {
			reward_qa_coins =Double.parseDouble(reward_qa_coins_string);
		}
		
		String post_content =  request.getParameter("postContent");
		String[] post_tag =  request.getParameterValues("tag");
		
		if(post_tag != null){
			for(int i =0; i < post_tag.length; i++){
				System.out.println("************* "+post_tag[i]);
			}
		}
		
		
		
		PostDAO postDAO = new PostDAO();
		TagDAO tagDAO = new TagDAO();
		
		String errorMsg = "";
		if(post_title.isEmpty()||post_title == null){
			errorMsg = "The post title cannot be empty!";
			RequestDispatcher rd = request.getRequestDispatcher("newPost.jsp");
			request.setAttribute("newPostMsg", errorMsg);
			rd.forward(request, response);
			return;
		}else if(!TransactionController.checkSufficientBalance(student, reward_qa_coins)) {
			errorMsg = "Not sufficient QA coins to reward";
			RequestDispatcher rd = request.getRequestDispatcher("newPost.jsp");
			request.setAttribute("newPostMsg", errorMsg);
			rd.forward(request, response);
			return;
		}else if(post_tag == null){
			errorMsg = "The post tag cannot be empty!";
			RequestDispatcher rd = request.getRequestDispatcher("newPost.jsp");
			request.setAttribute("newPostMsg", errorMsg);
			rd.forward(request, response);
			return;
		}else if(post_content.isEmpty()||post_content == null){
			errorMsg = "The post content cannot be empty!";
			RequestDispatcher rd = request.getRequestDispatcher("newPost.jsp");
			request.setAttribute("newPostMsg", errorMsg);
			rd.forward(request, response);
			return;
		}else{
			
			postDAO.addNewPost(avatar_id, post_title, post_content);
			tagDAO.addTag(postDAO.lastPostIDofAvatar(avatar_id), post_tag);
			if(student != null){
				Post post = postDAO.retrievePostbyID(postDAO.lastPostIDofAvatar(avatar_id));
				TransactionController.depositQa_coins(post, student, reward_qa_coins);
			}
			
			
			RequestDispatcher rd = request.getRequestDispatcher("forumHome.jsp");
			rd.forward(request, response);
			return;
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
