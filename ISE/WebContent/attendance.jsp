
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.TreeMap"%>
<%@page import="java.util.ArrayList"%>
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
    Group_id:<input type='text' name='group_id'/><br/>
    Week:<input type='text' name='week'/><br/>
    <select name="Group">
    <option value="1">1</option>
    <option value="2">2</option>
    <option value="3">3</option>
    </select>
    <select name="weekno">
    <option value="1">1</option>
    <option value="2">2</option>
    <option value="3">3</option>
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
        <th>Attendance</th>
   		</tr>
   		<% 
		for(String[] s:attendanceList){
			out.print("<tr><td>"+s[0]+"</td>");
			out.print("<td>"+s[1]+"</td>");
		}
		}
    	%>
    	
   </table>
    </body>
        
</html>
