<%@include file="protect.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>IS102 Web Platform</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" href="style/css/bootstrap.min.css">
<link rel="stylesheet" href="style/css/font-awesome.min.css">
<link rel='stylesheet' href="style/css/fullcalendar.css" />

<script src="style/js/jquery.min.js"></script>
<script src="style/js/moment.min.js"></script>
<script src="style/js/fullcalendar.js"></script>
<script type="text/javascript">
	$(document).ready(function() {

    // page is now ready, initialize the calendar...

    	$('#calendar').fullCalendar({
    		editable: false, // Don't allow editing of events
    		handleWindowResize: true,
    		weekends: true, // Hide weekends
    		header: {
    		    left:   'prev,next today',
    		    center: 'title',
    		    right:  'month,agendaWeek,agendaDay,list'
    		}, // Hide buttons/titles
    		defaultView: 'agendaWeek',
    		minTime: '08:00:00', // Start time for the calendar
    		maxTime: '24:00:00', // End time for the calendar
    		displayEventTime: true, // Display event time
    		events: "Calendarjson"
        	// put your options and callbacks here
    	})

	});
</script>

</head>
<body>
<jsp:include page="/Authcalendar"/>
<%
		String loginUrl = (String) session.getAttribute("loginUrl");
%>
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
				<li class="nav-item"><a class="nav-link" href="<%=loginUrl%>">Consultation</a></li>
				<li class="nav-item"><a class="nav-link" href="#">Dashboard</a></li>
				<li class="nav-item"><a class="nav-link" href="javascript:logInForum()" id="forum">Forum</a></li>
				<li class="nav-item"><a class="nav-link" href="logout.jsp">Logout</a></li>
			</ul>
		</div>
	</nav>
	
	<div id="calendar" style="width:65%"></div>

	
	<script src="//cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"></script>
	<script src="style/js/bootstrap.min.js"></script>
<%@ include file = "footer.jsp" %>
</body>
</html>