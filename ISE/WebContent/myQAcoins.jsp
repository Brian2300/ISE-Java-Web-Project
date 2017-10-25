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
	//TransactionController.clearAllPendingTransactions();
	String stu_smu_email_id = student.getSmu_email_id();
	StudentDAO stu_dao = new StudentDAO();
	Student updated_student = stu_dao.retrieveStudentByEmailID(stu_smu_email_id);
	session.setAttribute("student", updated_student);
	out.print("Student id: " +updated_student.getSmu_email_id()+"</br>");
	out.print("QA coins balance: " +updated_student.getQa_coinsString());
	%>
	
	<br>
	<br>

	<div class="row">

				<div class="col-12">
					<div class="btn-group" role="group" aria-label="Basic example">
						<a class="btn btn-outline-primary"
							style="width: 14rem; height: 2.4rem" onclick="action()"><b>Action required</b></a>
					</div>
					<div class="btn-group" role="group" aria-label="Basic example">
						<a onclick="history()" class="btn btn-outline-primary"
							style="width: 14rem; height: 2.4rem"><b>Transaction history</b></a>
					</div>
				</div>

	</div>

	<script type="text/javascript">
	function action() {
	    document.getElementById("actionTable").setAttribute("style", "display"); 
	    document.getElementById("historyTable").setAttribute("style", "display:none"); 
		}
	function history() {
	    document.getElementById("actionTable").setAttribute("style", "display:none"); 
	    document.getElementById("historyTable").setAttribute("style", "display"); 
		}
</script>
<br>


	</div>
	<div class="row justify-content-md-center">
		<div class="col-12 col-md-auto">
			<%
			ArrayList<Transaction> transactions = TransactionDAO.retrieveTransactionByRelatedStudent(student);
			ArrayList<Transaction> pendingTx =new ArrayList<Transaction>();
			
			%>
			<div class="scroll" id="actionTable" style="display">
			

				<table class="table table-bordered">
					<thead class="thead-default">

						<tr>
							<th width="15%">Transaction Type</th>
							<th width="15%">Post Title</th>
							<th width="15%">Reply Content</th>
							<th width="20%">Transaction Time</th>
							<th width="10%">QA coins</th>
							<th width="25%">Actions</th>
						</tr>
					</thead>
						<%
						int i =0;
						if(transactions!=null && transactions.size()>0){
							for(Transaction transaction: transactions){
								if(!transaction.getType().equals("closed")){
								Post post = transaction.getPost();
								Post parentPost = null;
								Post childPost = null;
								if(post.getParent_id()!=0){
									PostDAO post_dao = new PostDAO();
									parentPost = post_dao.retrievePostbyID(post.getParent_id());
									childPost = post;
									
								}else{
									parentPost = post;
								}
								if(transaction.getFrom_stu()!= null){
									if(transaction.getType().equals("pending")&&transaction.getFrom_stu().getSmu_email_id().equals(updated_student.getSmu_email_id())){ 
									
						%>
					<tbody>
						<tr>
							<td><%
							
							String type = transaction.getType();
							String qa_coins ="";
							if(type.equals("approved")){
								if(student.getSmu_email_id().equals(transaction.getFrom_stu().getSmu_email_id())){
									out.println("Approved to reward the reply ");
									qa_coins="-"+transaction.getAmount();
								}else{
									out.println("Approved");
									qa_coins="+"+transaction.getAmount();
								}
							}else if(type.equals("rejected")){
								out.println("Rejected");
								qa_coins=""+transaction.getAmount();
							}else if(type.equals("toCentralPool")){
								out.println("QA coins deducted for rewarding");
								qa_coins="-"+transaction.getAmount();
							}else if(type.equals("sysReward")){
								out.println("System reward for thoughtfulness");
								qa_coins="+"+transaction.getAmount();
							}else if(type.equals("refunded")){
								out.println("Refund of QA coins reward");
								qa_coins=""+transaction.getAmount();
							}else if(type.equals("pending")){
								out.println("Pending reward approval");
								qa_coins=""+transaction.getAmount();
							}else if(type.equals("closed")){
								out.println("QA coins deducted for rewarding");
								qa_coins=""+transaction.getAmount();
							}
							
							
							%></td>
							<td><a href="viewPost.jsp?post_id=<%=parentPost.getPost_id()%>"><%=parentPost.getPost_title()%></a></td>
							<% if (childPost!=null){ %>
							<td><a href="viewPost.jsp?post_id=<%=childPost.getPost_id()%>"><%=childPost.getPost_content()%></a></td>
							<%}else{ %>
							<td></td>
							<%} %>
							<td><%=transaction.getTimestamp()%></td>
							<td><%=qa_coins%></td>
							<td>
							<%
								pendingTx.add(transaction);
									session.setAttribute("pendingTransaction", pendingTx);
									
				
							
							
						 // if approve or reject is pressed, parameter tx is pass to TXController 
							%>
								<div class="btn-group" role="group" aria-label="Basic example">
								<form action="myQAcoins" method="post">
								
							    <button class="btn btn-secondary" type="submit" name="button" value=<%="approved"+i %>>Approve</button>
							    <button class="btn btn-secondary" type="submit" name="button" value=<%="rejected"+i %>>Reject</button>
								</form>
							<%i++;
							} }%>
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
			
			<div class="scroll" id="historyTable" style="display:none">
			

				<table class="table table-bordered">
					<thead class="thead-default">

						<tr>
							<th width="30%">Transaction Type</th>
							<th width="25%">Post Title</th>
							<th width="15%">Reply Content</th>
							<th width="20%">Transaction Time</th>
							<th width="10%">QA coins</th>
						</tr>
					</thead>
						<%
						
						if(transactions!=null && transactions.size()>0){
							for(Transaction transaction: transactions){
								if(!transaction.getType().equals("closed")){
								Post post = transaction.getPost();
								Post parentPost = null;
								Post childPost = null;
								if(post.getParent_id()!=0){
									PostDAO post_dao = new PostDAO();
									parentPost = post_dao.retrievePostbyID(post.getParent_id());
									childPost = post;
									
								}else{
									parentPost = post;
								}
						%>
					<tbody>
						<tr>
							<td><%
							String type = transaction.getType();
							String qa_coins ="";
							if(type.equals("approved")){
								if(student.getSmu_email_id().equals(transaction.getFrom_stu().getSmu_email_id())){
									out.println("Approved to reward the reply ");
									qa_coins="-"+transaction.getAmount();
								}else{
									out.println("Approved");
									qa_coins="+"+transaction.getAmount();
								}
							}else if(type.equals("rejected")){
								out.println("Rejected");
								qa_coins=""+transaction.getAmount();
							}else if(type.equals("toCentralPool")){
								out.println("QA coins deducted for rewarding");
								qa_coins="-"+transaction.getAmount();
							}else if(type.equals("sysReward")){
								out.println("System reward for thoughtfulness");
								qa_coins="+"+transaction.getAmount();
							}else if(type.equals("refunded")){
								out.println("Refund of QA coins reward");
								qa_coins=""+transaction.getAmount();
							}else if(type.equals("pending")){
								out.println("Pending reward approval");
								qa_coins=""+transaction.getAmount();
							}else if(type.equals("closed")){
								out.println("QA coins deducted for rewarding");
								qa_coins=""+transaction.getAmount();
							}
							%></td>
							<td><a href="viewPost.jsp?post_id=<%=parentPost.getPost_id()%>"><%=parentPost.getPost_title()%></a></td>
							<% if (childPost!=null){ %>
							<td><a href="viewPost.jsp?post_id=<%=childPost.getPost_id()%>"><%=childPost.getPost_content()%></a></td>
							<%}else{ %>
							<td></td>
							<%} %>
							<td><%=transaction.getTimestamp()%></td>
							<td><%=qa_coins%></td>
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