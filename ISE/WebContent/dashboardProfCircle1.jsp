
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

<script src="http://d3js.org/d3.v3.min.js"></script>
<script>
let info = d3.select('#info');
var margin = {top: 50, right: 20, bottom: 70, left: 40},
    width = 600 - margin.left - margin.right,
    height = 360 - margin.top - margin.bottom;
console.log(margin);

var x = d3.scale.ordinal().rangeRoundBands([0, width], .05);

var y = d3.scale.linear().range([height-30, 0]);

var xAxis = d3.svg.axis()
    .scale(x)
    .orient("bottom");

var yAxis = d3.svg.axis()
    .scale(y)
    .orient("left")
    .ticks(10);

var svg_chart1 = d3.select("body").append("svg")
    .attr("width", width + margin.left + margin.right)
    .attr("height", height + margin.top + margin.bottom)
  .append("g")
    .attr("transform", 
          "translate(" + margin.left + "," + margin.top + ")");
var title = svg_chart1.append("text")
.text("QA coins distribution")
.attr("x", 20)
.attr("y", -20);

var data = <%=DashboardDAO.retrieveD4data()%>
/*
console.log(data);
let max = d3.nest()
.rollup(function(v){return{
	max: d3.max(v,function(d){return d.Qacoins;})};
})
console.log(max);
*/
  x.domain(data.map(function(d) { return d.group; }));
  var maxQa = d3.max(d3.values(data.Qacoins));
 // console.log(d3.max(data, function(d) { return d.Qacoins; }));
  
 // y.domain([0, d3.max(data, function(d) { return d.Qacoins; })]);
 y.domain([0, 180]);//static

  svg_chart1.append("g")
      .attr("class", "x axis")
      .attr("transform", "translate(0," + height + ")")
      .call(xAxis)
    .selectAll("text")
      .style("text-anchor", "end")
      .attr("dx", "-.8em")
      .attr("dy", "-.55em")
      .attr("transform", "rotate(-90)" );

  svg_chart1.append("g")
      .attr("class", "y axis")
      .call(yAxis)
    .append("text")
      .attr("transform", "rotate(-90)")
      .attr("y", 6)
      .attr("dy", ".71em")
      .style("text-anchor", "end")
      .text("QA coins");

 var circle= svg_chart1.selectAll("circle")
			      .data(data)
			      .enter().append("circle")
			      .style("border", "5px solid #666666")
			      .style("opacity", .4)
			      .style("border-radius","50%")
			      .attr("r",  x.rangeBand()/7)
			      .attr("cx", function(d) { return x(d.group)+ x.rangeBand()/2; })
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
        	"r":  x.rangeBand()/7-3
         });
		 console.log(d);
	     console.log(i);
				let coord = d3.mouse(document.body);
				info.style('left',(coord[0]+20)+'px')
					.style('top',(coord[1]+20)+'px');
					info.html('<div>Email id: '+d.email_id+'</div><div>Group: '+d.group+'</div><div>QA coins: '+d.Qacoins+'</div>');	
     }
    function handleMouseOut(d, i) {
    	d3.select(this).attr({
        	"stroke":""
         });
    	info.style('left','-1000px')
     }
</script>

	<%@ include file="footer.jsp"%>
	
	
	
</body>
</html>