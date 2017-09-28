package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import entity.Post;
import entity.Student;
import entity.Transaction;

public class TransactionDAO {
	private static final String TBLNAME = "transaction";
	
    private static void handleSQLException(SQLException ex, String sql, String... parameters) {
        String msg = "Unable to access data; SQL=" + sql + "\n";
        for (String parameter : parameters) {
            msg += "," + parameter;
        }
        Logger.getLogger(TransactionDAO.class.getName()).log(Level.SEVERE, msg, ex);
        throw new RuntimeException(msg, ex);
    }
    
    public static boolean insertTransaction(Transaction tx) {
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "";
        
        String from_stu_string = null;
        if(tx.getFrom_stu()!=null) {
        	from_stu_string = tx.getFrom_stu().getSmu_email_id();
        }     
        String post_id_string = Integer.toString(tx.getPost().getPost_id());
        String to_stu_string = null;
        if(tx.getTo_stu()!=null) {
        	to_stu_string = tx.getTo_stu().getSmu_email_id();
        }
       
        String tx_amount_string = Double.toString(tx.getAmount());
        String tx_time_string = tx.getTimestamp();
        String type_string = tx.getType();

    
        int numRecordsUpdated =0;
        try {
            conn = ConnectionManager.getConnection();
            sql = "INSERT INTO "+TBLNAME+"(`from_stu`, `post_id`, `to_stu`, `tx_amount`, `tx_time`, `type`) VALUES (?,?,?,?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, from_stu_string );
            stmt.setString(2, post_id_string );
            stmt.setString(3, to_stu_string );
            stmt.setString(4, tx_amount_string);
            stmt.setString(5, tx_time_string);
            stmt.setString(6, type_string);
            System.out.println("going to insert TX "+stmt );
            numRecordsUpdated = stmt.executeUpdate();
            System.out.println("num of records updated "+numRecordsUpdated );
        } catch (SQLException ex) {
        	ex.printStackTrace();
        } finally {
            ConnectionManager.close(conn, stmt);
        }
        if(numRecordsUpdated == 1) {
        	return true;
        }else {
        	return false;
        }
        
    }
 /*   
    public static Transaction retrieveTransactionByPostID(int postID) {
    			
    	Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "";
        Transaction tx = null;
        ResultSet rs = null;
                
        try {
            conn = ConnectionManager.getConnection();
            
            sql = "SELECT * FROM "+TBLNAME+" WHERE post_id =?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, Integer.toString(postID));
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                String from_stu = rs.getString(1);
                int post_id = rs.getInt(2);
                String to_stu = rs.getString(3);
                Double tx_amount = Double.parseDouble(rs.getString(4));
                String tx_time = rs.getString(5);
                String type = rs.getString(6);
                
                PostDAO post_dao = new PostDAO();
                Post post = post_dao.retrievePostbyID(post_id);
                StudentDAO student_dao = new StudentDAO();
                Student from_student = null;
                Student to_student = null;
                if(from_stu!=null && from_stu.length()>0) {
                	from_student = student_dao.retrieveStudentByEmailID(from_stu);
                }
                if(to_stu!=null && to_stu.length()>0) {
                	to_student = student_dao.retrieveStudentByEmailID(to_stu);
                }
                
                tx = new Transaction(post, from_student,tx_amount, tx_time, type);
                //Transaction(Post post, Student from_stu, double amount,String timestamp, String type)
            }
            //return resultUser;

        } catch (SQLException ex) {
            handleSQLException(ex, sql, "exception");
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return tx;
    			
    }
    */
    //sdsssssssssssssssssssssss
    public static ArrayList<Transaction> retrieveTransactionByPostID(int postID) {
		
    	Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "";
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();
        ResultSet rs = null;
                
        try {
            conn = ConnectionManager.getConnection();

            sql = "SELECT * FROM "+TBLNAME+" WHERE post_id =?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, Integer.toString(postID));
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                String from_stu = rs.getString(1);
                int post_id = rs.getInt(2);
                String to_stu = rs.getString(3);
                Double tx_amount = Double.parseDouble(rs.getString(4));
                String tx_time = rs.getString(5);
                String type = rs.getString(6);
                
                PostDAO post_dao = new PostDAO();
                Post post = post_dao.retrievePostbyID(post_id);
                StudentDAO student_dao = new StudentDAO();
                Student from_student = null;
                Student to_student = null;
                if(from_stu!=null && from_stu.length()>0) {
                	from_student = student_dao.retrieveStudentByEmailID(from_stu);
                }
                if(to_stu!=null && to_stu.length()>0) {
                	to_student = student_dao.retrieveStudentByEmailID(to_stu);
                }
                
                Transaction tx = new Transaction(post, from_student,to_student, tx_amount, tx_time, type);
                transactions.add(tx);
                
            }
            //return resultUser;

        } catch (SQLException ex) {
            handleSQLException(ex, sql, "exception");
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return transactions;
    			
    }
    public static ArrayList<Transaction> retrieveTransactionByType(String txType) {
		
    	Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "";
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();
        ResultSet rs = null;
                
        try {
            conn = ConnectionManager.getConnection();

            sql = "SELECT * FROM "+TBLNAME+" WHERE type =?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, txType);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                String from_stu = rs.getString(1);
                int post_id = rs.getInt(2);
                String to_stu = rs.getString(3);
                Double tx_amount = Double.parseDouble(rs.getString(4));
                String tx_time = rs.getString(5);
                String type = rs.getString(6);
                
                PostDAO post_dao = new PostDAO();
                Post post = post_dao.retrievePostbyID(post_id);
                StudentDAO student_dao = new StudentDAO();
                Student from_student = null;
                Student to_student = null;
                if(from_stu!=null && from_stu.length()>0) {
                	from_student = student_dao.retrieveStudentByEmailID(from_stu);
                }
                if(to_stu!=null && to_stu.length()>0) {
                	to_student = student_dao.retrieveStudentByEmailID(to_stu);
                }
                
                Transaction tx = new Transaction(post, from_student,to_student, tx_amount, tx_time, type);
                transactions.add(tx);
                
            }
            //return resultUser;

        } catch (SQLException ex) {
            handleSQLException(ex, sql, "exception");
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return transactions;
    			
    }
    
    public static Transaction retrieveTransactionByPostIDandType(int post_id, String txType) {
		
    	Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "";
        Transaction transaction = null;
        ResultSet rs = null;
                
        try {
            conn = ConnectionManager.getConnection();

            sql = "SELECT * FROM "+TBLNAME+" WHERE post_id = ? and type =?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, Integer.toString(post_id));
            stmt.setString(2, txType);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                String from_stu = rs.getString(1);
                int record_post_id = rs.getInt(2);
                String to_stu = rs.getString(3);
                Double tx_amount = Double.parseDouble(rs.getString(4));
                String tx_time = rs.getString(5);
                String type = rs.getString(6);
                
                PostDAO post_dao = new PostDAO();
                Post post = post_dao.retrievePostbyID(record_post_id);
                StudentDAO student_dao = new StudentDAO();
                Student from_student = null;
                Student to_student = null;
                if(from_stu!=null && from_stu.length()>0) {
                	from_student = student_dao.retrieveStudentByEmailID(from_stu);
                }
                if(to_stu!=null && to_stu.length()>0) {
                	to_student = student_dao.retrieveStudentByEmailID(to_stu);
                }
                
                transaction = new Transaction(post, from_student,to_student, tx_amount, tx_time, type);

                
            }
            //return resultUser;

        } catch (SQLException ex) {
            handleSQLException(ex, sql, "exception");
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return transaction;
    			
    }
    
    public static ArrayList<Transaction> retrieveTransactionByFromStu(Student student) {
		
    	Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "";
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();
        ResultSet rs = null;
                
        try {
            conn = ConnectionManager.getConnection();

            sql = "SELECT * FROM "+TBLNAME+" WHERE from_stu =?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, student.getSmu_email_id());
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                String from_stu = rs.getString(1);
                int post_id = rs.getInt(2);
                String to_stu = rs.getString(3);
                Double tx_amount = Double.parseDouble(rs.getString(4));
                String tx_time = rs.getString(5);
                String type = rs.getString(6);
                
                PostDAO post_dao = new PostDAO();
                Post post = post_dao.retrievePostbyID(post_id);
                StudentDAO student_dao = new StudentDAO();
                Student from_student = null;
                Student to_student = null;
                if(from_stu!=null && from_stu.length()>0) {
                	from_student = student_dao.retrieveStudentByEmailID(from_stu);
                }
                if(to_stu!=null && to_stu.length()>0) {
                	to_student = student_dao.retrieveStudentByEmailID(to_stu);
                }
                
                Transaction tx = new Transaction(post, from_student,to_student, tx_amount, tx_time, type);
                transactions.add(tx);
                
            }
            //return resultUser;

        } catch (SQLException ex) {
            handleSQLException(ex, sql, "exception");
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return transactions;
    			
    }
    public static ArrayList<Transaction> retrieveTransactionByRelatedStudent(Student student) {
		
    	Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "";
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();
        ResultSet rs = null;
                
        try {
            conn = ConnectionManager.getConnection();

            sql = "SELECT * FROM "+TBLNAME+" WHERE from_stu =? or to_stu=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, student.getSmu_email_id());
            stmt.setString(2, student.getSmu_email_id());
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                String from_stu = rs.getString(1);
                int post_id = rs.getInt(2);
                String to_stu = rs.getString(3);
                Double tx_amount = Double.parseDouble(rs.getString(4));
                String tx_time = rs.getString(5);
                String type = rs.getString(6);
                
                PostDAO post_dao = new PostDAO();
                Post post = post_dao.retrievePostbyID(post_id);
                StudentDAO student_dao = new StudentDAO();
                Student from_student = null;
                Student to_student = null;
                if(from_stu!=null && from_stu.length()>0) {
                	from_student = student_dao.retrieveStudentByEmailID(from_stu);
                }
                if(to_stu!=null && to_stu.length()>0) {
                	to_student = student_dao.retrieveStudentByEmailID(to_stu);
                }
                
                Transaction tx = new Transaction(post, from_student,to_student, tx_amount, tx_time, type);
                transactions.add(tx);
                
            }
            //return resultUser;

        } catch (SQLException ex) {
            handleSQLException(ex, sql, "exception");
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return transactions;
    			
    }
    public static void updateTransactionType(Transaction tx, String oldType, String newType){
		
    	
    	
    	Connection conn = null;
	    PreparedStatement stmt = null;
	    String sql = "";	   
	
	    
	    try {
	    	conn = ConnectionManager.getConnection();
	    	sql = "UPDATE "+ TBLNAME +" SET `type`=? WHERE `post_id`=? and from_stu=? and `type` =?";
	        stmt = conn.prepareStatement(sql);
	        String from_stu = tx.getFrom_stu().getSmu_email_id();
	        String post_id = Integer.toString(tx.getPost().getPost_id());
	        stmt.setString(1,newType); 
	        stmt.setString(2,post_id);
	        stmt.setString(3,from_stu);
	        stmt.setString(4,oldType); 
	        System.out.println(stmt);
	        stmt.executeUpdate();

        } catch (SQLException ex) {
        	 String msg = "An exception occurs when updating transaction";
	         handleSQLException(ex, sql, "msg={" + msg + "}");
	    } finally {
	            ConnectionManager.close(conn, stmt);
	    }
   }
    
}
