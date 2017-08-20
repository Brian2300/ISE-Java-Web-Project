package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import entity.*;


public class ProfessorDAO {
	//for debugging purpose
		
/*		public static void main(String[] args){
			System.out.println("ssss");
			ProfessorDAO pd = new ProfessorDAO();
			pd.registerProfessor("tdai@smu.edu.sg","65438721");
	
		}*/
		
		    private static final String TBLNAME = "professor";
		
	    private void handleSQLException(SQLException ex, String sql, String... parameters) {
	        String msg = "Unable to access data; SQL=" + sql + "\n";
	        for (String parameter : parameters) {
	            msg += "," + parameter;
	        }
	        Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, msg, ex);
	        throw new RuntimeException(msg, ex);
	    }
	    
	    public Professor retrieveProfessor(String emailID, String password) {
	        Connection conn = null;
	        PreparedStatement stmt = null;
	        String sql = "";
	        Professor returnProfessor = null;
	        ResultSet rs = null;

	        try {
	            conn = ConnectionManager.getConnection();

	            sql = "select smu_email, password from " + TBLNAME + " where smu_email_id = ? and password = SHA1(?)";
	            stmt = conn.prepareStatement(sql);
	            stmt.setString(1, emailID);
	            stmt.setString(2, password);

	            rs = stmt.executeQuery();

	            while (rs.next()) {
	                String prof_smu_email = rs.getString(1);
	                String correctPassword = rs.getString(2);
	             
	                returnProfessor = new Professor(prof_smu_email,correctPassword);
	            }
	            //return resultUser;

	        } catch (SQLException ex) {
	            handleSQLException(ex, sql, "User={" + returnProfessor + "}");
	        } finally {
	            ConnectionManager.close(conn, stmt, rs);
	        }
	        return returnProfessor;
	    }
	    
	    public boolean CheckUniqueEmailID(String emailID) {
	        Connection conn = null;
	        PreparedStatement stmt = null;
	        String sql = "";
	        Professor returnProfessor = null;
	        ResultSet rs = null;

	        try {
	            conn = ConnectionManager.getConnection();

	            sql = "select smu_email, password from " + TBLNAME + " where smu_email_id = ? ";
	            stmt = conn.prepareStatement(sql);
	            stmt.setString(1, emailID);

	            rs = stmt.executeQuery();

	            while (rs.next()) {
	                String prof_smu_email = rs.getString(1);
	                String correctPassword = rs.getString(2);
	                returnProfessor = new Professor(prof_smu_email,correctPassword);
	            }
	            //return resultUser;

	        } catch (SQLException ex) {
	            handleSQLException(ex, sql, "User={" + returnProfessor + "}");
	        } finally {
	            ConnectionManager.close(conn, stmt, rs);
	        }
	       
	        if(returnProfessor !=null){
	        	return false;
	        }else{
	        	return true;
	        }
	    }
	    
	    public String registerProfessor(String email,String password){
	    	String msg = "";
	    	Connection conn = null;
		    PreparedStatement stmt = null;
		    String sql = "";
		    String emailID =  email.substring(0,email.indexOf("@"));
		    
		    if(!CheckUniqueEmailID(emailID)){
		    	msg = "email already exists!";
		    	return msg;
		    }
		    
		    try {
		    	conn = ConnectionManager.getConnection();
		    	sql = "insert into professor (smu_email, smu_email_id,password) values (?,?,SHA1(?))";
		        stmt = conn.prepareStatement(sql);
		        stmt.setString(1, email);
		        stmt.setString(2,emailID);
		        stmt.setString(3, password);  
		        stmt.executeUpdate();

	        } catch (SQLException ex) {
	        	 msg = "An exception occurs during registration";
		         handleSQLException(ex, sql, "msg={" + msg + "}");
		    } finally {
		            ConnectionManager.close(conn, stmt);
		    }
	    	return msg;
	    }


}
