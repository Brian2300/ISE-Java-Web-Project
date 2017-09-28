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

	<div style="margin-top: 2%"></div>
	<div class="container text-center">
		<header>
			<h2>My QA Coins</h2>
			<hr>
		</header>
	</div>
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
	<div class="row justify-content-md-center">
		<div class="col-12 col-md-auto">
			
			<div class="scroll">

				<table class="table table-bordered">
					<thead class="thead-default">

						<tr>
							<th width="20%">Transaction Type</th>
							<th width="30%">Post Title</th>
							<th width="20%">Transaction Time</th>
							<th width="10%">QA coins</th>
							<th width="20%">Actions</th>
						</tr>
					</thead>
						<%
						ArrayList<Transaction> transactions = TransactionDAO.retrieveTransactionByRelatedStudent(student);
						if(transactions!=null && transactions.size()>0){
							for(Transaction transaction: transactions){
								if(!transaction.getType().equals("closed")){
								Post post = transaction.getPost();
						%>
					<tbody>
						<tr>
							<td><%=transaction.getType() %></td>
							<td><a href="viewPost.jsp?post_id=<%=post.getPost_id()%>"><%=post.getPost_title()%></a></td>
							<td><%=transaction.getTimestamp()%></td>
							<td><%=transaction.getAmount()%></td>
							<td>
							<%
							if(transaction.getFrom_stu()!= null){
								if(transaction.getType().equals("pending")&&transaction.getFrom_stu().getSmu_email_id().equals(updated_student.getSmu_email_id())){ 
									session.setAttribute("pendingTransaction", transaction);
							
							
							
						 // if approve or reject if pressed, parameter tx is pass to TXController 
							%>
								<div class="btn-group" role="group" aria-label="Basic example">
								<form action="myQAcoins" method="post">
							    <button class="btn btn-secondary" type="submit" name="button" value="approved">Approve</button>
							    <button class="btn btn-secondary" type="submit" name="button" value="rejected">Reject</button>
								</form>
							<%} }%>
								</div>
							</td>
						</tr>
					</tbody>
						<%	}
						}
						}
						%>
				</table>
			</div>
		</div>
	</div>
	<div class="col-8"></div>


	<script
		src="//cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"></script>
	<script src="style/js/jquery-3.2.1.min.js"></script>
	<script src="style/js/bootstrap.min.js"></script>
	<%@ include file="footer.jsp"%>
</body>
</html>