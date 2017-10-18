<%@include file="protect.jsp"%>
<%@ page language="java"
	import="javazoom.upload.*,java.util.*,java.io.*,utility.BootstrapUpload,entity.*,servlets.TransactionController, dao.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>IS102 Web Platform QA coins</title>
<meta name="viewport" content="width=device-width, initial-scale=1">


<link rel="stylesheet" href="style/css/bootstrap.min.css">
<link rel="stylesheet" href="style/css/font-awesome.min.css">
<link rel="stylesheet" href="style/css/forumHomePageLayout.css">
</head>
<body>
	<%@ include file="navigationBar.jsp"%>
	<div class='container' align='center'>
	<%
	Student student = (Student) session.getAttribute("student");
	TransactionController.refundAllQa_coins();
	TransactionController.clearAllPendingTransactions();
	String stu_smu_email_id = student.getSmu_email_id();
	StudentDAO stu_dao = new StudentDAO();
	Student updated_student = stu_dao.retrieveStudentByEmailID(stu_smu_email_id);
	session.setAttribute("student", updated_student);
	out.print("Student id: " +updated_student.getSmu_email_id()+"</br>");
	out.print("QA coins balance: " +updated_student.getQa_coinsString());
	%>
	

	</div>
	
	<script src="http://d3js.org/d3.v3.min.js"></script>
	<script>
		var data = <%=updated_student.getJSON()%>

			var canvas = d3.select("body")
				.append("svg")
				.attr("width", 500)
				.attr("height", 500)
				
			canvas.selectAll("rect")
				.data(data)
				.enter()
					.append("rect")
					.attr("width",function(d){return d.age;})
					.attr("height", 40)
					.attr("y", function(d, i){return i*50;})
					.attr("fill", "blue");

		

	</script>
	
	</body>
	
