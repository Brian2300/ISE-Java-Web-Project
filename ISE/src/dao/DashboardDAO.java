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
}
