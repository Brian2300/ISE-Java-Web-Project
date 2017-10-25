
<%@ page language="java"
	import="javazoom.upload.*,java.util.*,java.io.*,utility.BootstrapUpload,entity.*,servlets.DashboardController, dao.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<title>Student Dashboard</title>
<meta name="viewport" content="width=device-width, initial-scale=1">


<link rel="stylesheet" href="style/css/bootstrap.min.css">
<link rel="stylesheet" href="style/css/font-awesome.min.css">
<link rel="stylesheet" href="style/css/forumHomePageLayout.css">

<%@ include file="navigationBar.jsp"%>



</head>
<body>
<%String attendanceData = (String)request.getAttribute("attendance"); %>
<script src="http://d3js.org/d3.v3.min.js"></script>
	<script>
  var attendanceData=<%=attendanceData%>
  //DashboardDAO.retrieveWeeklyAttendanceByStudent("jj.zheng.2013")
  
    console.log(attendanceData);
    
 // Set the dimensions of the canvas / graph
 
 //dashboard for weekly attendance
 
 	var	margin = {top: 30, right: 20, bottom: 30, left: 50},
    	width = 1000 - margin.left - margin.right,
    	height = 270 - margin.top - margin.bottom;
    
    var canvas = d3.select("body")
    				.append("svg")
					.attr("width", width + margin.left + margin.right)
					.attr("height", height + margin.top + margin.bottom)
					.append("g")
					.attr("transform", "translate(" + margin.left + "," + margin.top + ")");
    canvas.append("text")
    	.attr("id","title")
    	.text("Weekly attendance")
    	.attr("x",10)
    	.attr("y",10)
    	.attr("font-family","dans-serif")
	     .attr("font-size", "20px");
  	
   
    
    canvas.append("text")
	.attr("id","title")
	.text("Percentage: ")
	.attr("x",10)
	.attr("y",40)
	.attr("font-family","dans-serif")
    .attr("font-size", "20px");
    
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
		.attr("fill", function(d){if(d.attendance==1){return "#00ffff";}else{return "#ff9999";}});
	
	square.on("mouseover", handleMouseOver)
		  .on("mouseout", handleMouseOut);

	 function handleMouseOver(d, i) {  // Add interactivity

         // Use D3 to select element, change color and size
         d3.select(this).attr({
        	 x: function(){return (i*55-2);},
			 y: 98,
        	 width: 54,
	         height: 54
         });
         // Specify where to put label of text
         canvas.append("text").attr({
            id: "t" + d.week+"-" + i,  // Create an id for text so we can select it later for removing on mouseout
             x: function() {return i*55; },
             y: function() {return 90; }
         })
         .text(function() {
           return ("week" + d.week) ;  // Value of the text[d.week]
         }).attr("fill","black")
             .attr("font-family","dans-serif")
			.attr("font-size", "20px");
       }

      function handleMouseOut(d, i) {
         // Use D3 to select element, change color back to normal
         d3.select(this).attr({
        	 x: function(){return (i*55);},
			 y: 100,
        	 width: 50,
	         height:50
         });
         // Select text by id and then remove
         d3.select("#t" + d.week+"-" + i).remove();  // Remove text location
       }

	var	chart2 = d3.select("body")
	.append("svg")
		.attr("width", width + margin.left + margin.right)
		.attr("height", height + margin.top + margin.bottom)
			.append("g")
			.attr("transform", "translate(" + margin.left + "," + margin.top + ")");
	var title =["QQQQQQQQQ", "out","in"];
	var textMaker = chart2.selectAll("text")
					.data(title)
					.enter()
					.append("text");
	var textAttributes = textMaker
							.attr("x", 50)
							.attr("y", 60)
							.text(function(d){return d})
							.attr("font-family","dans-serif")
							.attr("font-size", "20px");
				
	
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
    					.attr("y",function(d){return 200-d.value;}) // use a scale
    					.attr("fill","red");
    					
 qa_bar.append("text")
			.text("hello")
			.style("text-anchor", "middle")
			.attr("x",function(d, i){return i*55;})
    		.attr("y",function(d){return 200-d.value;})
			.attr("fill", "blue")
			.attr("class", "timeLabel mono axis axis-worktime");
//
		

	</script>

	
	
	<%@ include file="footer.jsp"%>
	
	
	
</body>
</html>