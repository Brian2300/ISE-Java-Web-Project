<%@include file="protect.jsp"%>
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

	<script src="//cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"></script>
<script src="style/js/jquery-3.2.1.min.js"></script>
<script src="style/js/bootstrap.min.js"></script>

</body>

<%@include file="login_forum.jsp"%>

<script>
var rusername;
var remail;
var rpassword;

var username;
var password;

window.onload = function(){
	var fu = '${forumUser}';
	var nfuUser = '${user}';
	
	if (!!fu){
		//alert("successfully registration")
		username = '${forumUser.username}';
		password = '${forumUser.password}';
		
		rusername = '${forumUser.username}';
		remail = '${forumUser.email}';
		rpassword = '${forumUser.password}';
		setForm();
		document.getElementById("register").click();
	}
	
	if(!!nfuUser){
		username = '${user.username}';
		password = '${user.password}';
	}
}

function setForm(){
	 document.getElementById("rusername").value = rusername;
	 document.getElementById("remail").value = remail;
	 document.getElementById("rpassword").value = rpassword;
	 $("#register").click(function() {

    var url = "../CAT/forum/integration.php"; // the script where you handle the form input.

    $.ajax({
           type: "POST",
           url: url,
           data: $("#forum_register").serialize(), // serializes the form's elements.
           //success: function(data)
          // {
              // alert(data); // show response from the php script.
           //}
         });
    return false; // avoid to execute the actual submit of the form.
});
}


</script>

<form action="..CAT/forum/integration.php" method="post" id = "forum_register">
       <input type="hidden" name="rusername" id="rusername" size="10" title="Username"/> <br>
	   <input type="hidden" name="remail" id="remail" size="10" title="Email"/><br>
       <input type="hidden" name="rpassword" id="rpassword" size="10" title="Password"/><br>
	   <input id="register" type="submit" style = "visibility:hidden;" name="register" value="register" />
</form>

</html>