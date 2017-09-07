package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import entity.Student;

public class AttendanceDAO {
//private static final String TBLNAME = "class_list";
	
    
    public static String[][] retrieveAttendanceByWeek(String week) { 
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "";
        ArrayList<String[]> attendance_list = new ArrayList<String[]>();
        String[][] result = null;
        ResultSet rs = null;

        try {
            conn = ConnectionManager.getConnection();

            sql = "SELECT class_list.user_id, weekly_class_participation.attendance from class_list left join weekly_class_participation "
            		+ "on class_list.user_id = weekly_class_participation.student_id where weekly_class_participation.week = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, week); //need to provide the group_id

            rs = stmt.executeQuery();
            int numRecords = 0;

            while (rs.next()) {
            	System.out.println(rs.getString(1));
            	System.out.println(rs.getString(2));
            	String[] single_record ={rs.getString(1),rs.getString(2)};
            	attendance_list.add(single_record);
            }
            result = attendance_list.toArray(new String[0][0]);


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return result;
    }
}