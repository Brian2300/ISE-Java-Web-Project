<%@include file="protect.jsp"%>
<%@ page
	import="java.io.*,java.util.*, java.util.concurrent.*, utility.*, entity.*, dao.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>IS102 Web Platform</title>
<meta name="viewport" content="width=device-width, initial-scale=1">


<link rel="stylesheet" href="style/css/bootstrap.min.css">
<link rel="stylesheet" href="style/css/font-awesome.min.css">
<link rel="stylesheet" href="style/css/forumHomePageLayout.css">

</head>
<body>
	<%@ include file="navigationBar.jsp"%>

	<%
     
   //display msg for refining the post
  
  %>
	<div style="margin-top: 2%"></div>
	<div class="container text-center">
		<header>
			<h2>Post a New Question</h2>
			<hr>
		</header>
		<%
			// error msg for assign marks
			String errorMsgs = (String) request.getAttribute("newPostMsg");
			if (errorMsgs != null && errorMsgs.length() > 0) {
				out.println("<div id = 'myElem' class='container' align='center' style='padding:0px;height:40px'>");
				out.println("<div class='alert alert-warning' style='width:400px'>");
				out.println("<font color='red'>");
				out.println(errorMsgs);
				out.println("</font>");
			}
			request.removeAttribute("newPostMsg");
			out.println("</div>");
			out.println("</div><br>");
		%>
		<div class="row justify-content-md-left">
			<div class="col-12 col-md-auto">

				<div class="row">

					<div class="col-2">
						<div class="btn-group" role="group" aria-label="Basic example">

							<a class="btn btn-outline-primary" style="width: 10rem"
								href="https://research.larc.smu.edu.sg/ISE/forumHome.jsp"><b>Back to Forum </b></a>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="viewPostBoarder">
			<br>
			<div class="container">
				<form name="replyForm" method="post" action="PostNewQuestion">
					<div class="form-group row">
						<label for="inputEmail3" class="col-sm-2 col-form-label"><strong>Post
								Title</strong></label>
						<div class="col-sm-9">
							<input type="text" name="postTitle" class="form-control"
								id="inputEmail3" maxlength="140" placeholder="Enter your post title" />

						</div>
					</div>
				 <%Student student = (Student)session.getAttribute("student");
					if(student != null){
						out.println("<div class=\"form-group row\">\r\n" + 
								"						<label for=\"inputEmail3\" class=\"col-sm-2 col-form-label\"><strong>Reward\r\n" + 
								"						QA Coins</strong></label>\r\n" + 
								"						<div class=\"col-sm-9\">\r\n" + 
								"							<input type=\"number\" name=\"reward_qa_coins\" class=\"form-control\"\r\n" + 
								"								id=\"inputEmail3\" placeholder=\"Enter amount of QA coins\" />\r\n" + 
								"\r\n" + 
								"						</div>\r\n" + 
								"					</div>");
				} %>	

					<div class="form-group row">
						<label for="inputEmail3" class="col-sm-2 col-form-label"><strong>Tag</strong></label>
						<div class="col-sm-9">
						<%
						
						    TagDAO tagDAO = new TagDAO();
							List<Tag> tagList = tagDAO.retrieveAllTags();
							Tag tempTag = null;
							for(int i=0; i<tagList.size(); i++){	
								tempTag = tagList.get(i);
								out.println("<label class='checkbox-inline'> <input type='checkbox' name='tag' id='inlineCheckbox1' value='"
								+tempTag.getTag()+"'>"
								+tempTag.getTag()+"</label>");
							}
								
						%>
							
						</div>
					</div>




					<div class="form-group row">
						<label for="inputEmail3" class="col-sm-2 col-form-label"><strong>Post
								Content</strong></label>
						<div class="col-sm-9">
							<textarea name="postContent" class="form-control"
								id="inputEmail3" placeholder="Enter your post content"></textarea>
						</div>
					</div>

					<div class="form-group row">
						<div class="offset-sm-2 col-sm-8">

							<input type="submit" class="btn btn-primary" value="Submit">
						</div>
					</div>
				</form>
			</div>
		</div>



	</div>

	<script
		src="//cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"></script>
	<script src="style/js/jquery-3.2.1.min.js"></script>
	<script src="style/js/bootstrap.min.js"></script>
	<%@ include file="footer.jsp"%>
</body>


</html>
