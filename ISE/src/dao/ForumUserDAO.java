package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;

import entity.ForumUser;

public class ForumUserDAO {
	 private static final String TBLNAME = "phpbb_users";
		
	    private void handleSQLException(SQLException ex, String sql, String... parameters) {
	        String msg = "Unable to access data; SQL=" + sql + "\n";
	        for (String parameter : parameters) {
	            msg += "," + parameter;
	        }
	        Logger.getLogger(ForumUserDAO.class.getName()).log(Level.SEVERE, msg, ex);
	        throw new RuntimeException(msg, ex);
	    }
	    
	    public int retrieveForumUserWithUsername(String username) {
	        Connection conn = null;
	        PreparedStatement stmt = null;
	        String sql = "";
	        ResultSet rs = null;
	        int count = 0;
	        
	        try {
	            conn = ConnectionManager.getConnection();

	            sql = "select count(*) from " + TBLNAME + " where username_clean = ?";
	            stmt = conn.prepareStatement(sql);
	            stmt.setString(1, username);
	      
	            rs = stmt.executeQuery();

	            while (rs.next()) {
	                String resultCount = rs.getString(1);
	               
	                count = Integer.parseInt(resultCount);
	            }
	            //return resultUser;

	        } catch (SQLException ex) {
	           // handleSQLException(ex, sql, "User={" + returnStudent + "}");
	        } finally {
	            ConnectionManager.close(conn, stmt, rs);
	        }
	        return count;
	    }
	    
	    public int retrieveForumUserWithEmail(String email) {
	        Connection conn = null;
	        PreparedStatement stmt = null;
	        String sql = "";
	        ResultSet rs = null;
	        int count = 0;
	        
	        try {
	            conn = ConnectionManager.getConnection();

	            sql = "select count(*) from " + TBLNAME + " where user_email = ?";
	            stmt = conn.prepareStatement(sql);
	            stmt.setString(1, email);
	      
	            rs = stmt.executeQuery();

	            while (rs.next()) {
	                String resultCount = rs.getString(1);
	               
	                count = Integer.parseInt(resultCount);
	            }
	            //return resultUser;

	        } catch (SQLException ex) {
	            //handleSQLException(ex, sql, "User={" + returnStudent + "}");
	        } finally {
	            ConnectionManager.close(conn, stmt, rs);
	        }
	        return count;
	    }
	    
	    public String retrieveUsernameWithEmailID(String emailID){
	    	Connection conn_email = null;
	        PreparedStatement stmt_email = null;
	        String sql_email = "";
	        ResultSet rs_email = null;
	        String email = "";
	        
	        PreparedStatement stmt_username = null;
	        Connection conn_username = null;
	        String sql_username = "";
	        ResultSet rs_username = null;
	        String username="";
	    
	        String tempTable = "";
	        
	        try {
	        	
	        	if (emailID.matches(".*\\d")){
	        		tempTable = "student";
	        	}else{
	        		tempTable = "professor";
	        	}
	        	
	            conn_email = ConnectionManager.getConnection();
	            sql_email = "select smu_email from "+tempTable+" where smu_email_id = ?";
	            stmt_email = conn_email.prepareStatement(sql_email);
	            stmt_email.setString(1, emailID);
	            rs_email = stmt_email.executeQuery();

	            while (rs_email.next()) {
	                email = rs_email.getString(1);
	            }
	            
	            conn_username = ConnectionManager.getConnection();
	            sql_username = "select username from " + TBLNAME + " where user_email = ?";
	            stmt_username = conn_username.prepareStatement(sql_username);
	            stmt_username.setString(1, email);
	            rs_username = stmt_username.executeQuery();

	            while (rs_username.next()) {
	                username = rs_username.getString(1);
	            }
	            //return resultUser;

	        } catch (SQLException ex) {
	            //handleSQLException(ex, sql, "User={" + returnStudent + "}");
	        } finally {
	            ConnectionManager.close(conn_email, stmt_email, rs_email);
	            ConnectionManager.close(conn_username, stmt_username, rs_username);
	        }
	        return username;
	    }
	    
}
