<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>File Upload</title>
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
        

        <div class = "container">
            <%--form to allow user to select file for upload--%>
            <form action = "UploadController" method = "post" enctype = "multipart/form-data">
                Upload file here: <input type = "file" name="file-upload" accept=".zip" /> <br>
                <font color = "red"> Note: File should only be in zip format </font>
                
                <br><br>

                <input type = "submit" value = "Upload" />
                <font color = "red"> ${fileUploadError} </font> 
		 </div>
		 
            </form>
            </br>
            </br>
            </br>
            </br>
            </br>
            </br>
            </br>
            </br>
            </br>
            </br>
            </br>
            </br>
            </br>
            </br>
            </br>
            </br>
       
<script src="//cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"></script>
<script src="style/js/jquery-3.2.1.min.js"></script>
<script src="style/js/bootstrap.min.js"></script>
<%@ include file = "footer.jsp" %>
    </body>
</html>
