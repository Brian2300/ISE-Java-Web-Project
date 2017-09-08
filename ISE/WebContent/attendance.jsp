
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.TreeMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dao.AttendanceDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Attendance Results Page</title>
    </head>
    <h1>Attendance summary
    </h1>
    <body>
    <form action = 'AttendanceController'>
    Group_id:
    <select name="group_id">
    <%
    String[][] distGroups = AttendanceDAO.retrieveDistinctGroups();
    for(String[]group: distGroups){
    	out.print("<option value="+group[0]+">"+group[0]+"</option>");
    }
    
    %>

    </select>
    
    <input type='submit' value='Retrieve Attendance'/>
    </form>


    	<%
    		
    	String[][] attendanceList = (String[][])request.getAttribute("attendanceList");
		if(attendanceList==null){
			out.print("no attendance record, please check");
		}
		else{%>
		<table border="2">
		<tr>
        <th>Student ID</th>
        <%
	    String[][] distWeeks = AttendanceDAO.retrieveDistinctWeeks();
	    for(String[]week: distWeeks){
	    	out.print("<th>week "+week[0]+"<th>");
	    }
	    %>
   		</tr>
   		<% 

		for(String[] stu_attendance:attendanceList){
			
			out.print("<tr>");
			for(String attendance: stu_attendance){
				out.print("<td>"+attendance+"<td>");
			}
			out.print("<tr>");
		}
		}
    	%>
    	
   </table>
    </body>
        
</html>
