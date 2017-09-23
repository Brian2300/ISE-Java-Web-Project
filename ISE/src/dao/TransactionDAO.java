package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import entity.Transaction;

public class TransactionDAO {
	private static final String TBLNAME = "transaction";
	
    private void handleSQLException(SQLException ex, String sql, String... parameters) {
        String msg = "Unable to access data; SQL=" + sql + "\n";
        for (String parameter : parameters) {
            msg += "," + parameter;
        }
        Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, msg, ex);
        throw new RuntimeException(msg, ex);
    }
    
    public static boolean insertTransaction(Transaction tx) {
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "";
        
        String from_stu_string = tx.getFrom_stu().getSmu_email_id();
        String tx_amount_string = Double.toString(tx.getAmount());
        String tx_time_string = tx.getTimestamp();
        String type_string = tx.getType();

    
        int numRecordsUpdated =0;
        try {
            conn = ConnectionManager.getConnection();
            sql = "INSERT INTO `transaction`(`from_stu`, `to_stu`, `tx_amount`, `tx_time`, `type`) VALUES (?,?,?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, from_stu_string );
            stmt.setString(2, "" );
            stmt.setString(3, tx_amount_string);
            stmt.setString(4, tx_time_string);
            stmt.setString(5, type_string);
            System.out.println("going to update TX "+stmt );
            numRecordsUpdated = stmt.executeUpdate();
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
    
    public ArrayList<Integer> retrieveIndiClassPart(String smu_email_id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "";
        ArrayList<Integer> indiRecord = new  ArrayList<>();
        ResultSet rs = null;
                
        try {
            conn = ConnectionManager.getConnection();
            
            sql = "select week from "+TBLNAME+" where student_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, smu_email_id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int week = Integer.parseInt(rs.getString(1));
                indiRecord.add(week);
            }
            //return resultUser;

        } catch (SQLException ex) {
            handleSQLException(ex, sql, "individual Record={" + indiRecord + "}");
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return indiRecord;
    }
    
}
