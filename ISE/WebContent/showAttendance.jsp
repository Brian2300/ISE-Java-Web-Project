<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
    		
    		//String[][] attendanceList =(String[][])request.getAttribute("hello");
			String attendanceList = (String)request.getAttribute("hello");
    		if(attendanceList==null){
    			out.print("no attendance record, please check");
    		}
    		else{
    			out.print("there is attendance record, please check");
    		}
    		
    	%>
</body>
</html>