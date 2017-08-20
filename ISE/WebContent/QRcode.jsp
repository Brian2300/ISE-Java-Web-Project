<!--<%@include file="protect.jsp"%>-->
<%@ page import = "java.io.*,java.util.*, java.util.concurrent.*, utility.*, entity.AuthenticationCode, dao.AuthenticationCodeDAO" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>IS102 Web Platform</title>
<meta name="viewport" content="width=device-width, initial-scale=1">


<link rel="stylesheet" href="style/css/bootstrap.min.css">
<link rel="stylesheet" href="style/css/font-awesome.min.css">

</head>
<body>
	<nav class="navbar navbar-toggleable-md navbar-light bg-faded">
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarTogglerDemo01"
			aria-controls="navbarTogglerDemo01" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarTogglerDemo01">
			<a class="navbar-brand" href="home.jsp">IS102</a>
			<ul class="navbar-nav mr-auto mt-2 mt-lg-0">
				<li class="nav-item"><a class="nav-link" href="attendance.jsp">Attendance</a></li>
				<li class="nav-item"><a class="nav-link" href="#">Post-class Summary</a></li>
				<li class="nav-item"><a class="nav-link" href="consultation.jsp">Consultation</a></li>
				<li class="nav-item"><a class="nav-link" href="#">Dashboard</a></li>
				<li class="nav-item"><a class="nav-link" href="javascript:logInForum()" id="forum">Forum</a></li>
				<li class="nav-item"><a class="nav-link" href="logout.jsp">Logout</a></li>
			</ul>
		</div>
	</nav>

	
	<%
	   
		// Set refresh, autoload time as 5 seconds
        response.setIntHeader("Refresh", 20);
	
	    String week = (String)request.getParameter("week");
	    String teachingGrp = (String)request.getParameter("session");   
        String passcode = RandomString.generateRandomString(8);
	    String time = RandomString.getCurrentTime();

	    AuthenticationCode newCode = new AuthenticationCode(week, teachingGrp, time, passcode);
		AuthenticationCodeDAO sd = new AuthenticationCodeDAO();
		sd.addNew(newCode);
		String deeplink = "https://telegram.me/IS102_BOT?start=";
	    boolean b=QRcode.generateQRcode(deeplink+passcode);
        TimeUnit.SECONDS.sleep(5);

	
		if (!b) { 
			//if(!b)
			out.println("<div class='card card-outline-info mb-4 text-center'>");
			out.println("<div class='card-block'>");
			out.println("<h3 class='card-title'>QR code valid for 25 seconds</h3>");
			out.println("<h3 class='card-title'>Passcode: /ta "+ passcode+ "  generated at  "+ time+"</h3>");
			//out.println("<img src='images/"+passcode+".png' class='rounded mx-auto d-block'alt='QR Code' width='25%'>");
			out.println("<img src='/ISE/RetrieveImg' class='rounded mx-auto d-block'alt='QR Code' width='30%'>");
			out.println("</div>");
			out.println("</div>");
		}
		
		out.println("<div class='container'>");
		out.println("<form class='form-horizontal' role='form' method='POST' action='attendance.jsp'>");
		out.println("<div class='form-group'>");
		out.println("<div class='row'>");
		out.println("<div class='col-md-5'></div>");
		out.println("<button type='submit' class='btn btn-primary' style='width: 12rem' name='stop' value='stop'>Stop</button>");
		out.println("</div>");
	    out.println("</div>");
		out.println("</form>");
		out.println("</div>");
		
	
	%>
	
	<script src="//cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"></script>
	<script src="style/js/jquery-3.2.1.min.js"></script>
	<script src="style/js/bootstrap.min.js"></script>
<%@ include file = "footer.jsp" %>
</body>
<%@include file="login_forum.jsp"%>
<script>
var username;
var password;

window.onload = function(){
	var nfuUser = '${user}';
	
	if(!!nfuUser){
		username = '${user.username}';
		password = '${user.password}';
	}
}

</script>
</html>