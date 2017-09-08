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
    //write later
    public static String[][] retrieveAttendanceByStudentAndWeek(String student, String week) { 
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "";
        ArrayList<String[]> attendance = new ArrayList<String[]>();
        String[][] result = null;
        ResultSet rs = null;

        try {
            conn = ConnectionManager.getConnection();

            sql = "SELECT `attendance` FROM `weekly_class_participation` WHERE `student_id`=? AND `week`=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, student);
            stmt.setString(2, week);
            System.out.println("the stat" + stmt);
            rs = stmt.executeQuery();
            int numRecords = 0;
//the bug is below
            while (rs.next()) {
            	System.out.println("the frist one is" + rs.getString(1));
            	System.out.println("here");
            	//System.out.println("the second one is" +rs.getString(2));
            	String[] single_record ={rs.getString(1)};
            	System.out.println("the single record is" +single_record[0]);
            	attendance.add(single_record);
            }
            result = attendance.toArray(new String[0][0]);

            System.out.println("the result" + result.length);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return result;
    }
    
    
    public static String[][] retrieveClasslistByGroup(String group) { 
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "";
        ArrayList<String[]> class_list = new ArrayList<String[]>();
        String[][] result = null;
        ResultSet rs = null;

        try {
            conn = ConnectionManager.getConnection();

            sql = "SELECT `stu_name`,`user_id` FROM `class_list` WHERE `group_id`=?";
            
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, group); //need to provide the group_id
            System.out.println(stmt);
            rs = stmt.executeQuery();
            int numRecords = 0;

            while (rs.next()) {
            	System.out.println(rs.getString(1));
            	System.out.println(rs.getString(2));
            	String[] single_record ={rs.getString(1),rs.getString(2)};
            	class_list.add(single_record);
            }
            result = class_list.toArray(new String[0][0]);
            System.out.println(result.length);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return result;
    }
    
    
    public static String[][] retrieveDistinctGroups(){
    	Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "";
        ArrayList<String[]> group_list = new ArrayList<String[]>();
        String[][] result = null;
        ResultSet rs = null;

        try {
            conn = ConnectionManager.getConnection();

            sql = "SELECT DISTINCT `group_id` FROM `student`";
            stmt = conn.prepareStatement(sql);

            rs = stmt.executeQuery();

            while (rs.next()) {
            	System.out.println(rs.getString(1));
            	String[] single_record ={rs.getString(1)};
            	group_list.add(single_record);
            }
            result = group_list.toArray(new String[0][0]);
            System.out.println(result[0][0]);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return result;
    	
    }
    public static String[][] retrieveDistinctWeeks(){
    	Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "";
        ArrayList<String[]> week_list = new ArrayList<String[]>();
        String[][] result = null;
        ResultSet rs = null;

        try {
            conn = ConnectionManager.getConnection();

            sql = "SELECT DISTINCT week FROM `weekly_class_participation`";
            stmt = conn.prepareStatement(sql);

            rs = stmt.executeQuery();

            while (rs.next()) {
            	System.out.println(rs.getString(1));
            	String[] single_record ={rs.getString(1)};
            	week_list.add(single_record);
            }
            result = week_list.toArray(new String[0][0]);
            System.out.println(result[0][0]);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return result;
    	
    }
}