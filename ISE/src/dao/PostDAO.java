package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import entity.*;


public class PostDAO {
	//for debugging purpose
	
		public static void main(String[] args){
			System.out.println("ssss");
			PostDAO pd = new PostDAO();
			HashMap<Integer, Post> map = pd.retrieveAll();
		    //System.out.println(map.size());
			for (Integer key : map.keySet()) {
			    System.out.println(key + " " + map.get(key) + " " +map.get(key).getAvatar_id() +"   "+ map.get(key).getPost_id());
			}
			
			HashMap<Integer,Post> aMap = pd.retrieveAPost(4);
			System.out.println("*****************************");
			for (Integer key : aMap.keySet()) {
			    System.out.println(key + " " + aMap.get(key) + " " +aMap.get(key).getAvatar_id() +"   "+ aMap.get(key).getParent_id());
			}

	
		}
	
		private static final String TBLNAME = "post";
		    
		
		
	    private void handleSQLException(SQLException ex, String sql, String... parameters) {
	        String msg = "Unable to access data; SQL=" + sql + "\n";
	        for (String parameter : parameters) {
	            msg += "," + parameter;
	        }
	        Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, msg, ex);
	        throw new RuntimeException(msg, ex);
	    }
	    
	    
	    public HashMap<Integer, Post> retrieveAll() {
	        HashMap<Integer, Post> postMap = new HashMap<>();

	        Connection conn = null;
	        ResultSet rs = null;
	        PreparedStatement preStmt = null;
	        Post tempPost = null;
	        try {
	            conn = ConnectionManager.getConnection();
	            String sql = "select * from " + TBLNAME + " where parent_id = post_id";
	            preStmt = conn.prepareStatement(sql);
	            rs = preStmt.executeQuery();

	            while (rs.next()) {
	            	int avatar_id = rs.getInt(1);
	            	int parent_id = rs.getInt(2);
	            	int level = rs.getInt(3);
	            	int post_id = rs.getInt(4);
	            	String post_title = rs.getString(5);
	            	String post_content = rs.getString(6);
	            	boolean is_question = rs.getBoolean(7);
	            	boolean is_bot = rs.getBoolean(8);
	            	boolean is_qa_bountiful = rs.getBoolean(9);
	            	String timestamp = rs.getString(10);
	            	int time_limit_qa = rs.getInt(11);
	            	int time_limit_bot = rs.getInt(12);
	            	float qa_coin_basic = rs.getFloat(13);
	            	float qa_coin_bounty = rs.getFloat(14);
	            	float thoughfulness_score = rs.getFloat(15);
	            	boolean no_show = rs.getBoolean(16);
	            	int previous_version = rs.getInt(17);
	            	int number_of_upvotes = rs.getInt(18);
	            	int number_of_downvotes = rs.getInt(19);

	             
	            	tempPost = new Post(avatar_id, parent_id, level, post_id, post_title, post_content, is_question, is_bot, is_qa_bountiful, timestamp, time_limit_qa, time_limit_bot, qa_coin_basic, qa_coin_bounty, thoughfulness_score,
	            		 no_show, previous_version, number_of_upvotes, number_of_downvotes);
	            	
	            	System.out.println(tempPost);	       
	                postMap.put(parent_id, tempPost);
	                System.out.println(postMap.size());
	                
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            ConnectionManager.close(conn, preStmt, rs);
	            return postMap;
	        }
	    }
	    
	    public HashMap<Integer, Post> retrieveAPost(int parentID) {
	        HashMap<Integer, Post> postMap = new HashMap<>();

	        Connection conn = null;
	        ResultSet rs = null;
	        PreparedStatement preStmt = null;
	        Post tempPost = null;
	        try {
	            conn = ConnectionManager.getConnection();
	            String sql = "select * from " + TBLNAME + " where parent_id = ?";
	            preStmt = conn.prepareStatement(sql);
	            preStmt.setInt(1, parentID);
	            rs = preStmt.executeQuery();

	            while (rs.next()) {
	            	int avatar_id = rs.getInt(1);
	            	int parent_id = rs.getInt(2);
	            	int level = rs.getInt(3);
	            	int post_id = rs.getInt(4);
	            	String post_title = rs.getString(5);
	            	String post_content = rs.getString(6);
	            	boolean is_question = rs.getBoolean(7);
	            	boolean is_bot = rs.getBoolean(8);
	            	boolean is_qa_bountiful = rs.getBoolean(9);
	            	String timestamp = rs.getString(10);
	            	int time_limit_qa = rs.getInt(11);
	            	int time_limit_bot = rs.getInt(12);
	            	float qa_coin_basic = rs.getFloat(13);
	            	float qa_coin_bounty = rs.getFloat(14);
	            	float thoughfulness_score = rs.getFloat(15);
	            	boolean no_show = rs.getBoolean(16);
	            	int previous_version = rs.getInt(17);
	            	int number_of_upvotes = rs.getInt(18);
	            	int number_of_downvotes = rs.getInt(19);

	             
	            	tempPost = new Post(avatar_id, parent_id, level, post_id, post_title, post_content, is_question, is_bot, is_qa_bountiful, timestamp, time_limit_qa, time_limit_bot, qa_coin_basic, qa_coin_bounty, thoughfulness_score,
	            		 no_show, previous_version, number_of_upvotes, number_of_downvotes);
	            	
	            	System.out.println(tempPost);	       
	                postMap.put(post_id, tempPost);
	                System.out.println(postMap.size());
	                
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            ConnectionManager.close(conn, preStmt, rs);
	            return postMap;
	        }
	    }
	    public Post retrieveAPosts() {
	        Connection conn = null;
	        PreparedStatement stmt = null;
	        String sql = "";
	        Post returnPost = null;
	        ResultSet rs = null;

	        try {
	            conn = ConnectionManager.getConnection();

	            sql = "select * from " + TBLNAME + " where parent_id = post_id";
	            stmt = conn.prepareStatement(sql);
	         
	            
	            rs = stmt.executeQuery();

	            while (rs.next()) {
	            	int avatar_id = rs.getInt(1);
	            	int parent_id = rs.getInt(2);
	            	int level = rs.getInt(3);
	            	int post_id = rs.getInt(4);
	            	String post_title = rs.getString(5);
	            	String post_content = rs.getString(6);
	            	boolean is_question = rs.getBoolean(7);
	            	boolean is_bot = rs.getBoolean(8);
	            	boolean is_qa_bountiful = rs.getBoolean(9);
	            	String timestamp = rs.getString(10);
	            	int time_limit_qa = rs.getInt(11);
	            	int time_limit_bot = rs.getInt(12);
	            	float qa_coin_basic = rs.getFloat(13);
	            	float qa_coin_bounty = rs.getFloat(14);
	            	float thoughfulness_score = rs.getFloat(15);
	            	boolean no_show = rs.getBoolean(16);
	            	int previous_version = rs.getInt(17);
	            	int number_of_upvotes = rs.getInt(18);
	            	int number_of_downvotes = rs.getInt(19);

	             
	            	returnPost = new Post(avatar_id, parent_id, level, post_id, post_title, post_content, is_question, is_bot, is_qa_bountiful, timestamp, time_limit_qa, time_limit_bot, qa_coin_basic, qa_coin_bounty, thoughfulness_score,
	            		 no_show, previous_version, number_of_upvotes, number_of_downvotes);
	            }
	            //return resultUser;

	        } catch (SQLException ex) {
	            handleSQLException(ex, sql, "Post={" + returnPost + "}");
	        } finally {
	            ConnectionManager.close(conn, stmt, rs);
	        }
	        return returnPost;
	    }
	    
	    

}
