<!--<%@include file="protect.jsp"%>-->
<%@ page import = "java.io.*,java.util.*, utility.*" %>
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

	<div style="margin-top: 2%"></div>
	<div class="container text-center">
		<header>
			<h2>Generate a dynamic QR Code</h2>
			<hr>
		</header>
		<form class="form-horizontal" role="form" method="GET"
			action="QRcode.jsp">
			<div class="form-group">
				<div class="row">
					<div class="col-md-5"></div>
					<div class="input-group-addon" style="width: 4rem;height: 2.6rem">
						<label>week: &nbsp</label>
					</div>

					<select name="week" id="week" required autofocus style="width: 10rem"
						onChange="checkWeek()">
						<option value="">Select a week</option>
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
						<option value="6">6</option>
						<option value="7">7</option>
						<option value="8">8</option>
						<option value="9">9</option>
						<option value="10">10</option>
						<option value="11">11</option>
						<option value="12">12</option>
					</select>

				</div>
				<br>
				<div class="row">
					<div class="col-md-5"></div>
					<div class="input-group-addon" style="width: 5rem;height: 2.6rem">
						<label>Session: &nbsp</label>
					</div>

					<select name="session" id="session" required autofocus style="width: 9rem"
						onChange="checkSession()">
						<option value="">Select a session</option>
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>

					</select>

				</div>
				<br>
				<div class="row">
					<div class="col-md-5" style="width: 5rem;height: 2.6rem"></div>
					<button type="submit" id="mySubmit" class="btn btn-primary"
						name="QRcode" value="QRcode" disabled style="width: 14rem">Generate your QR
						code</button>
				</div>
			</div>
		</form>
	</div>



	<script src="//cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"></script>
	<script src="style/js/jquery-3.2.1.min.js"></script>
	<script src="style/js/bootstrap.min.js"></script>
<%@ include file = "footer.jsp" %>
</body>

<script>
	var week;
	var session;

	function checkWeek() {
		week = document.getElementById("week").value;
		if (week == "") {
			alert("Please Select a week");
			document.getElementById("mySubmit").disabled = true;

		} else {
			alert("You have selected " + week + " !");
		}

	}
	function checkSession() {
		session = document.getElementById("session").value;
		if (session == "") {
			alert("Please Select a session");
			document.getElementById("mySubmit").disabled = true;
		} else {
			alert("You have selected " + session + " !");
			document.getElementById("mySubmit").disabled = false;
		}

	}
	

</script>

</html>