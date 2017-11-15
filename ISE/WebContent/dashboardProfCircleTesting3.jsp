
<%@ page language="java"
	import="javazoom.upload.*,java.util.*,java.io.*,utility.BootstrapUpload,entity.*,servlets.DashboardController, dao.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<title>Professor Dashboard</title>
<meta name="viewport" content="width=device-width, initial-scale=1">


<link rel="stylesheet" href="style/css/bootstrap.min.css">
<link rel="stylesheet" href="style/css/font-awesome.min.css">
<link rel="stylesheet" href="style/css/forumHomePageLayout.css">

<%@ include file="navigationBar.jsp"%>
<style>

	.axis {
	  font: 10px sans-serif;
	}

	.axis path,
	.axis line {
	  fill: none;
	  stroke: #000;
	  shape-rendering: crispEdges;
	}

		.x text{
			text-anchor: end;
			transform: rotate(-60deg);
		}
		.vertical{
			text-anchor: end;
			transform: rotate(-90deg);
		}
		#info{
			left: -1000px;
			position: absolute;
			min-width: 100px;
			padding: 10px;
			background: #000;
			color: #fff;
			-webkit-border-radius: 5px;
			-moz-border-radius: 5px;
			border-radius: 5px;
		}

	</style>


</head>
<body>
<div id="info" style=""></div>


<script src="https://d3js.org/d3.v4.min.js"></script>
<script>

// set the dimensions and margins of the graph
var margin = {top: 20, right: 20, bottom: 30, left: 40},
    width = 700 - margin.left - margin.right,
    height = 600 - margin.top - margin.bottom;

// set the ranges
var x = d3.scaleBand()
          .range([0, width])
          .padding(0.1);
var y = d3.scaleLinear()
          .range([height, 0]);
          
// append the svg object to the body of the page
// append a 'group' element to 'svg'
// moves the 'group' element to the top left margin
var svg_chart1 = d3.select("body").append("svg")
    .attr("width", width + margin.left + margin.right)
    .attr("height", height + margin.top + margin.bottom)
  .append("g")
    .attr("transform", 
          "translate(" + margin.left + "," + margin.top + ")");

// get the data
/*
d3.csv("sales.csv", function(error, data) {
  if (error) throw error;

  // format the data
  data.forEach(function(d) {
    d.sales = +d.sales;
  });
*/
//get data
var data = <%=DashboardDAO.retrieveD4data()%>
console.log(data);
  // Scale the range of the data in the domains
  x.domain(data.map(function(d) { return d.group; }));
  y.domain([0, d3.max(data, function(d) { return d.Qacoins; })]);

  var circle= svg_chart1.selectAll("circle")
  .data(data)
  .enter().append("circle")
  .style("border", "5px solid #666666")
  .style("opacity", .4)
  .style("border-radius","50%")
  .attr("r",   x.bandwidth()/7)//maintain a reasonable r
  .attr("cx", function(d) { return x(d.group)+  x.bandwidth()/2; })//magic
  .attr("cy",height/2 );
  	
d3.selectAll("circle")
	.transition()
	.duration(function(d){return Math.abs(height/2-d.Qacoins)*7 })
	.attr("cy", function(d) { return y(d.Qacoins); });
	
	circle.on("mouseover", handleMouseOver)
	.on("mouseout", handleMouseOut);
	
function handleMouseOver(d, i) {  // Add interactivity
	d3.select(this).attr({
	"stroke":"black",
	"r":  x.bandwidth()/7-3
	});
	console.log(d);
	console.log(i);
	let coord = d3.mouse(document.body);
	
	//info.style('left',(coord[0]+20)+'px')
	//	.style('top',(coord[1]+20)+'px');
	//	info.html('<div>Email id: '+d.email_id+'</div><div>Group: '+d.group+'</div><div>QA coins: '+d.Qacoins+'</div>');	
	}
function handleMouseOut(d, i) {
	d3.select(this).attr({
	"stroke":""
	});
	//info.style('left','-1000px')
	}

  // add the x Axis
  svg_chart1.append("g")
      .attr("transform", "translate(0," + height + ")")
      .call(d3.axisBottom(x));

  // add the y Axis
  svg_chart1.append("g")
      .call(d3.axisLeft(y));


</script>

	<%@ include file="footer.jsp"%>
	
	
	
</body>
</html>