
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.TreeMap"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bootstrap Results Page</title>
    </head>
    <h1>Student registration status table
    </h1>
    <body>
<%
TreeMap<Integer, ArrayList<String>> invalidStudents = (TreeMap<Integer, ArrayList<String>>)request.getAttribute("invalidStudents");
out.print(invalidStudents.size()+" errors " );  
int numOfRecords =(int)request.getAttribute("numOfRecords");
%>
<table class="table table-bordered" style="text-align:center">
                <tr><th colspan="2" style="text-align:center ; background-color:#B8B8B8">Results for uploading class list</th></tr>
                <tr>
                    <td colspan="2">
                        No. of records successfully loaded : <%=numOfRecords %>
                    </td>
                </tr>
                <tr><th colspan="2" style="text-align:center ; background-color:#B8B8B8">List of Errors</th></tr>
                <tr>
                    <th style="text-align:center">Row Number</th>
                    <th style="text-align:center">Error Messages</th>
                </tr>

                <%                    Iterator<Integer> studIter = invalidStudents.keySet().iterator();
                    if (invalidStudents.keySet().size() == 0) {
                        out.println("<tr>");
                        out.println("<td colspan='2'>");
                        out.println("No invalid rows");
                        out.println("</td>");
                        out.println("</tr>");
                    }

                    while (studIter.hasNext()) {
                        Integer rowNo = studIter.next();
                        out.println("<tr>");
                        out.println("<td>");
                        out.println(rowNo);
                        out.println("</td>");
                        out.println("<td>");

                        String errorMsg = invalidStudents.get(rowNo).toString();
                        errorMsg = errorMsg.replace("[", "");
                        errorMsg = errorMsg.replace("]", "");

                        out.println(errorMsg);
                        out.println("</td>");
                        out.println("</tr>");
                    }
                %>    
            </table>

<table border="2">
<tr>
        <td>Group ID</td>
        <td>Student Name</td>
        <td>Student ID</td>
        <td>Registration status</td>
   </tr>

    	<%
    		String[][] studentsRegistration = (String[][])request.getAttribute("studentsRegistration");
    		for(String[] s:studentsRegistration){
    			out.print("<tr><td>"+s[0]+"</td>");
    			out.print("<td>"+s[1]+"</td>");
    			out.print("<td>"+s[2]+"</td>");
    			out.print("<td>"+s[3]+"</td></tr>");
    		}
    	%>
    	
   </table>
    </body>
        
</html>
