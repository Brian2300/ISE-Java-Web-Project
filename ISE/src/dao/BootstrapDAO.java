/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;


//import ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * Data Access Object for Bootstrapping data into the system. 
 */
public class BootstrapDAO {

    /*
    ==============================================================
       FOR CSV FILE, TO INSERT DATA INTO TABLE
    ==============================================================
     */
    
    /**
    *
    * Batch processes the Students from the given parameter, students, into the BIOS System. 
    * @param students list of Students to be placed in the database
    * 
    * @return successfulRows
    */
    public static int batchProcessClassList(String[][] class_list) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        int[] count = null;
System.out.println("BootstrapDAO here");
        try {

            //Get connection
            conn = ConnectionManager.getConnection();

            // Create SQL statement
            String SQL = "INSERT INTO class_list (stu_name, user_id, group_id)"
                    + "VALUES(?,?,?)";

            // Create PrepareStatement object
            pstmt = conn.prepareStatement(SQL);

            //Set auto-commit to false
            conn.setAutoCommit(false);

            for (String[] student : class_list) {
            	pstmt.setString(1, student[1]);
                pstmt.setString(2, student[2]);
                pstmt.setString(3, student[5]);
                pstmt.addBatch();
            }

            //Create an int[] to hold returned values
            count = pstmt.executeBatch();

            //Explicitly commit statements to apply changes
            conn.commit();
            
        } catch (SQLException e) {

        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }

            } catch (SQLException e) {

            }
        }

        int successfulRows = 0;
        if (count != null) {
            for (int i : count) {
                if (i >= 0) {
                    successfulRows++;
                }
            }
        }

        return successfulRows;
    }

   
    /**
    *
    * Remove all the data in the database. 
    */
    public static void wipeTables(String section) {

        Connection conn = null;
        Statement stmt = null;
        System.out.println("Modify wipe table here");

        try {
        	

            conn = ConnectionManager.getConnection();
            stmt = conn.createStatement();
            PreparedStatement pstmt = null;
            String clearSectionSQL = "DELETE FROM `class_list` WHERE `group_id`=?";
         // Create PrepareStatement object
            pstmt = conn.prepareStatement(clearSectionSQL);

            //Set auto-commit to false
            conn.setAutoCommit(false);
            pstmt.setString(1, section);
            pstmt.execute();
            conn.commit();
        } catch (SQLException ex) {

        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (stmt != null) {
                    stmt.close();
                }

            } catch (SQLException e) {

            }
        }

    }
    

}
