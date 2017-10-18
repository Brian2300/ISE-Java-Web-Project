
<%@ page language="java"
	import="javazoom.upload.*,java.util.*,java.io.*,utility.BootstrapUpload,entity.*,servlets.DashboardController, dao.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="navigationBar.jsp"%>



</head>
<body>
<%String attendanceData = (String)request.getAttribute("attendance"); %>
<script src="http://d3js.org/d3.v3.min.js"></script>
	<script>
  var attendanceData=<%=attendanceData%>
  //DashboardDAO.retrieveWeeklyAttendanceByStudent("jj.zheng.2013")
  var title =["QA coins", "out","in"];
    console.log(attendanceData);
    
 // Set the dimensions of the canvas / graph
 
 //dashboard for weekly attendance
 
 	var	margin = {top: 30, right: 20, bottom: 30, left: 50},
    	width = 1000 - margin.left - margin.right,
    	height = 300 - margin.top - margin.bottom;
    
    var canvas = d3.select("body")
    				.append("svg")
					.attr("width", width + margin.left + margin.right)
					.attr("height", height + margin.top + margin.bottom)
					.append("g")
					.attr("transform", "translate(" + margin.left + "," + margin.top + ")");
    var square = canvas.selectAll("rect")
    					.data(attendanceData)
    					.enter()
    						.append("rect")
    						.attr("width", 50)
    						.attr("height", 50)
    						.attr("x",function(d, i){return i*55;})
    						.attr("y",100)
    						.attr("stroke", "black")
    						.attr("rx",5)
    						.attr("ry",5)
    						.attr("fill","white");
	d3.selectAll("rect")
		.transition()
		.delay(function(d,i){return i*100;})
		.duration(function(d,i){return i*50;})
		.attr("fill", function(d){if(d.attendance==1){return "green";}else{return "red";}});
	
	var	chart2 = d3.select("body")
	.append("svg")
		.attr("width", width + margin.left + margin.right)
		.attr("height", height + margin.top + margin.bottom)
			.append("g")
			.attr("transform", "translate(" + margin.left + "," + margin.top + ")");
	
	var textMaker = canvas.selectAll("text")
					.data(title)
					.enter()
					.append("text");
	var textAttributes = textMaker
							.attr("x", 50)
							.attr("y", 60)
							.text(function(d){return d})
							.attr("font-family","dans-serif")
							.attr("font-size", "12px")
							.attr("z-index", ".3")
							.attr("fill", "black");
					
	
	//dashboard for QA coins
	var qa_coins = [100,20,30];
    var qa_coinsdata =[{"name":"Balance", "value":100},
					    {"name":"Outward reward pending approval", "value":20},
					    {"name":"Inward reward pending approval", "value":30},
					    ];
    
	var qa_bar = chart2.selectAll("rect")
					.data(qa_coinsdata)
					.enter()
						.append("rect")
						.attr("width", 50)
    					.attr("height", function(d){return d.value;})
    					.attr("x",function(d, i){return i*55;})
    					.attr("y",function(d){return 260-d.value;}) // use a scale
    					.attr("fill","red");
    					
 qa_bar.append("text")
			.text("hello")
			.style("text-anchor", "middle")
			.attr("x",function(d, i){return i*55;})
    		.attr("y",function(d){return 260-d.value;})
			.attr("fill", "blue")
			.attr("class", "timeLabel mono axis axis-worktime");
//
		

	</script>

	
	
	<%@ include file="footer.jsp"%>
	
	
	
</body>
</html>