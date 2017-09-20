package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AvatarDAO {
	 private static final String TBLNAME = "avatar";
	 
/*	 public static void main(String[] args){
		 AvatarDAO ad= new AvatarDAO();
		 System.out.println(ad.getAvatarName(2));
	 }*/
	 
	 private void handleSQLException(SQLException ex, String sql, String... parameters) {
	        String msg = "Unable to access data; SQL=" + sql + "\n";
	        for (String parameter : parameters) {
	            msg += "," + parameter;
	        }
	        Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, msg, ex);
	        throw new RuntimeException(msg, ex);
	 }
	 
	 public void registerAvatar(String avatarName){
		    Connection conn = null;
		    PreparedStatement stmt = null;
		    String sql = "";
		    
		    try {
		    	conn = ConnectionManager.getConnection();
		    	sql = "insert into avatar (avatar_name, icon, avatar_qa_coin, avatar_thoughtfulness_score, section_id, is_bot) values (?,?,?,?,?,?)";
		        stmt = conn.prepareStatement(sql);
		        stmt.setString(1, avatarName);
		        stmt.setString(2,"");//icon
		        stmt.setInt(3,0);//avatar_qa_coin
		        stmt.setInt(4, 0); //avatar_thoughtfulness_score
		        stmt.setString(5, ""); //section_id
		        stmt.setInt(6, 0); //is_bot
		        stmt.executeUpdate();

	        } catch (SQLException ex) {
	        	String msg = "An exception occurs during registration";
		        handleSQLException(ex, sql, "msg={" + msg + "}");
		    } finally {
		            ConnectionManager.close(conn, stmt);
		    }
	
	    }
	 
	 public int lastAvatarID(){
   	  Connection conn = null;
         PreparedStatement stmt = null;
         String sql = "";
         int returnAvatarID = 0;
         ResultSet rs = null;

         try {
             conn = ConnectionManager.getConnection();

             sql = "select MAX(avatar_id) FROM " + TBLNAME;
             stmt = conn.prepareStatement(sql);          
             rs = stmt.executeQuery();

             while (rs.next()) {              
           	  returnAvatarID = rs.getInt(1); 
             }

         } catch (SQLException ex) {
             handleSQLException(ex, sql, "avatar_id={" + returnAvatarID + "}");
         } finally {
             ConnectionManager.close(conn, stmt, rs);
         }
         return returnAvatarID;
   }
	 
	 
	 public String getAvatarName(int avatar_id){
		 
		 Connection conn = null;
         PreparedStatement stmt = null;
         String sql = "";
         String returnAvatarName = "";
         ResultSet rs = null;

         try {
             conn = ConnectionManager.getConnection();

             sql = "select avatar_name FROM " + TBLNAME +" WHERE avatar_id = ?";
             stmt = conn.prepareStatement(sql);    
             stmt.setInt(1, avatar_id);
             rs = stmt.executeQuery();

             while (rs.next()) {              
            	 returnAvatarName = rs.getString(1); 
             }

         } catch (SQLException ex) {
             handleSQLException(ex, sql, "avatar_name={" + returnAvatarName + "}");
         } finally {
             ConnectionManager.close(conn, stmt, rs);
         }
         return returnAvatarName;
		 
	 }
}
