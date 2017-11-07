package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;



import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class DashboardDAO {
	private static void handleSQLException(SQLException ex, String sql, String... parameters) {
        String msg = "Unable to access data; SQL=" + sql + "\n";
        for (String parameter : parameters) {
            msg += "," + parameter;
        }
        Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, msg, ex);
        throw new RuntimeException(msg, ex);
    }
	public static String retrieveWeeklyAttendanceByStudent(String student_id){
		JsonArray result = new JsonArray();
		Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "";
        ResultSet rs = null;
        try {
            conn = ConnectionManager.getConnection();
            sql = "SELECT `student_id`,`week`,`attendance` FROM `weekly_class_participation` WHERE student_id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, student_id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String smu_student_id = rs.getString(1); 
                int week = rs.getInt(2);
    			int attendance = rs.getInt(3); 
    			JsonObject json = new JsonObject();
            	json.addProperty("student",smu_student_id);
                json.addProperty("week", week);
                json.addProperty("attendance", attendance);
                result.add(json);
            }
        } catch (SQLException ex) {
            handleSQLException(ex, sql, "error");
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return result.toString();
	}
	
	public static String retrieveD1data(){
		JsonArray result = new JsonArray();
		Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "";
        ResultSet rs = null;
        try {
            conn = ConnectionManager.getConnection();
            sql = "Select T2.smu_email_id, T2.group_id, T1.tag,T1.post_id from\r\n" + 
            		"(SELECT post_tag.tag_id, tag.tag, post_id\r\n" + 
            		"FROM `post_tag` \r\n" + 
            		"left join tag on post_tag.tag_id = tag.tag_id) as T1\r\n" + 
            		"left join \r\n" + 
            		"(select post.post_id, post.avatar_id,student.smu_email_id, student.group_id\r\n" + 
            		"from post\r\n" + 
            		"left join student on post.avatar_id = student.avatar_id) as T2\r\n" + 
            		"on T1.post_id = T2.post_id";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String smu_student_id = rs.getString(1); 
                String group = rs.getString(2);
                String tag = rs.getString(3);
                int post_id= rs.getInt(4);
                if(smu_student_id!=null&&group!=null&&tag!=null&&post_id!=0) {
                	JsonObject json = new JsonObject();
                	json.addProperty("email_id",smu_student_id);
                    json.addProperty("Group", group);
                    json.addProperty("tag", tag);
                    json.addProperty("post_id", post_id);
                    result.add(json);
                }
    			
            }
        } catch (SQLException ex) {
            handleSQLException(ex, sql, "error");
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return result.toString();
	}
	
	public static String retrieveD2data(){
		JsonArray result = new JsonArray();
		Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "";
        ResultSet rs = null;
        try {
            conn = ConnectionManager.getConnection();
            sql = "Select student.smu_email_id, student.group_id,post.post_id\r\n" + 
            		"from post\r\n" + 
            		"left join student on student.avatar_id = post.avatar_id";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String smu_student_id = rs.getString(1); 
                String group = rs.getString(2);
                int post_id= rs.getInt(3);
                if(smu_student_id!=null&&group!=null&&post_id!=0) {
                	JsonObject json = new JsonObject();
                	json.addProperty("email_id",smu_student_id);
                    json.addProperty("group", group);
                    json.addProperty("post_id", post_id);
                    result.add(json);
                }
    			
            }
        } catch (SQLException ex) {
            handleSQLException(ex, sql, "error");
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return result.toString();
	}
	
	public static String retrieveD3data(){
		JsonArray result = new JsonArray();
		Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "";
        ResultSet rs = null;
        try {
            conn = ConnectionManager.getConnection();
            sql = "SELECT weekly_post_summary.smu_email_id, student.group_id,weekly_post_summary.week, weekly_post_summary.mark FROM `weekly_post_summary`\r\n" + 
            		"Left join student on weekly_post_summary.smu_email_id =student.smu_email_id";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String smu_student_id = rs.getString(1); 
                String group = rs.getString(2);
                int week= rs.getInt(3);
                int mark= rs.getInt(4);
                if(smu_student_id!=null&&group!=null) {
                	JsonObject json = new JsonObject();
                	json.addProperty("email_id",smu_student_id);
                    json.addProperty("group", group);
                    json.addProperty("week", week);
                    json.addProperty("mark", mark);
                    result.add(json);
                }
    			
            }
        } catch (SQLException ex) {
            handleSQLException(ex, sql, "error");
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return result.toString();
	}
	
	public static String retrieveD4data(){
		JsonArray result = new JsonArray();
		Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "";
        ResultSet rs = null;
        try {
            conn = ConnectionManager.getConnection();
            sql = "SELECT smu_email_id,`group_id`,`qa_coins` FROM `student` WHERE 1";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String smu_student_id = rs.getString(1); 
                String group = rs.getString(2);
                Double qa_coins= rs.getDouble(3);
                String qa_coins_string = String.format("%.2f", qa_coins);

                if(smu_student_id!=null&&group!=null) {
                	JsonObject json = new JsonObject();
                	json.addProperty("email_id",smu_student_id);
                    json.addProperty("group", group);
                    json.addProperty("Qacoins", qa_coins_string);
                    result.add(json);
                }
    			
            }
        } catch (SQLException ex) {
            handleSQLException(ex, sql, "error");
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return result.toString();
	}
}
