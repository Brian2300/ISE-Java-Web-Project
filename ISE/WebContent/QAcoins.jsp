<%@include file="protect.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ page language="java"
	import="javazoom.upload.*,java.util.*,java.io.*,utility.BootstrapUpload,entity.*,servlets.TransactionController, dao.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%@ include file="navigationBar.jsp"%>
<h1>My QA coins </h1>
<%
			Student student = (Student) session.getAttribute("student");
			if (student != null) {
				out.print(student.getSmu_email());
			}
%>
<p> the total number of QAcoins owned is 
<%
//out.print("nimahao " + TransactionController.refundAllQa_coins());
String stu_smu_email_id = student.getSmu_email_id();
StudentDAO stu_dao = new StudentDAO();
Student updated_student = stu_dao.retrieveStudentByEmailID(stu_smu_email_id);
session.setAttribute("student", updated_student);
out.print(updated_student.getQa_coins());
%>
</p>
<p>
Ways to gain QA coins
1.	Upon register +100
2.	Every question post +10
3.	Every thumbs gained for both question and reply, +5
</p>
<h3>
<%ArrayList<Transaction> transactions = TransactionDAO.retrieveTransactionByFromStu(student);
	if(transactions!=null && transactions.size()>0){
		for(Transaction transaction: transactions){
			out.println("==========================</br>");
			out.println(transaction.getFrom_stu().getSmu_email_id() +"</br>");
			out.println(transaction.getAmount()+"</br>");
			if(transaction.getPost()!= null){
				out.println(transaction.getPost().getPost_id()+"</br>");
				out.println(transaction.getPost().getPost_title()+"</br>");
			}
			out.println(transaction.getType()+"</br>");
		}
	}
%>

</h3>


</body>
</html>