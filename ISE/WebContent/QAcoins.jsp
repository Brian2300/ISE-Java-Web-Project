<%@include file="protect.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ page language="java"
	import="javazoom.upload.*,java.util.*,java.io.*,utility.BootstrapUpload,entity.Student"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>My QA coins </h1>
<%
			Student student = (Student) session.getAttribute("student");
			if (student != null) {
				out.print(student.getSmu_email());
			}
%>
<p> the total number of QAcoins owned is 
<%

out.print(student.getQa_coins());
%>
</p>
<p>
Ways to gain QA coins
1.	Upon register +100
2.	Every question post +10
3.	Every thumbs gained for both question and reply, +5
</p>
</body>
</html>