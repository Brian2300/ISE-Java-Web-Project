package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import entity.Student;

public class RegisterStatusDAO {
//private static final String TBLNAME = "class_list";
	
    
    public static String[][] retrieveRegisterStatus() { //pass in String[] group_id
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "";
        ArrayList<String[]> register_stu = new ArrayList<String[]>();
        String[][] result = null;
        ResultSet rs = null;

        try {
            conn = ConnectionManager.getConnection();

            sql = "Select class_list.group_id,class_list.stu_name, class_list.user_id,if(length(student.password)>0, \" Registered\",\"Not registered\") "
            		+ "as register_status from class_list left join student on class_list.user_id  = student.smu_email_id ORDER by group_id, stu_name;";
            stmt = conn.prepareStatement(sql);
            //stmt.setString(1, emailID); need to provide the group_id

            rs = stmt.executeQuery();
            int numRecords = 0;

            while (rs.next()) {
            	String[] single_record ={rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)};
            	register_stu.add(single_record);
            }
            result = register_stu.toArray(new String[0][0]);


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return result;
    }
}
