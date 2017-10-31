
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

	</style>


</head>
<body>


<script src="http://d3js.org/d3.v3.min.js"></script>
<script>

var margin = {top: 50, right: 20, bottom: 70, left: 40},
    width = 600 - margin.left - margin.right,
    height = 360 - margin.top - margin.bottom;
console.log(margin);

var x = d3.scale.ordinal().rangeRoundBands([0, width], .05);

var y = d3.scale.linear().range([height, 0]);



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
.text("Tag count")
.attr("x", 20)
.attr("y", -20);

d3.csv("tagCountGroup.csv", function(error, data) {

    data.forEach(function(d) {
        d.tag = d.tag;
        d.count = +d.count;
    });
	console.log(data);
  x.domain(data.map(function(d) { return d.tag; }));
  y.domain([0, d3.max(data, function(d) { return d.count; })]);

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
      .text("Count");

  svg_chart1.selectAll("bar")
      .data(data)
    .enter().append("rect")
      .style("fill", "steelblue")
      .attr("x", function(d) { return x(d.tag); })
      .attr("width", x.rangeBand())
      .attr("y", height)
      .attr("height", 2)
	      .transition()
	      	.duration(400)
			  .attr("y", function(d) { return y(d.count); })
      		  .attr("height", function(d) { return height - y(d.count); });


});

</script>
<script>

var width_chart2 = 600,
    height_chart2 = 360,
    radius = Math.min(width_chart2, height_chart2) / 2;

var color = d3.scale.ordinal()
    .range(["#98abc5", "#8a89a6", "#7b6888", "#6b486b", "#a05d56", "#d0743c", "#ff8c00"]);

var arc = d3.svg.arc()
    .outerRadius(radius - 35)
    .innerRadius(radius - 90);

var pie = d3.layout.pie()
    .sort(null)
    .value(function(d) { return d.postCount; });

var svg = d3.select("body").append("svg")
    .attr("width", width_chart2)
    .attr("height", height_chart2)
  .append("g")
    .attr("transform", "translate(" + width_chart2 / 2 + "," + height_chart2 / 2 + ")");
    
var title_chart2 = svg.append("text")
.text("Post count by group")
.attr("x", -230)
.attr("y", -150);

d3.csv("groupPostCount.csv", type, function(error, data) {
  if (error) throw error;

  var g = svg.selectAll(".arc")
      .data(pie(data))
    .enter()
    .append("g")
      .attr("class", "arc");

  g.append("path")
      .attr("d", arc)
      .style("fill", function(d) { return color(d.data.group); });

  g.append("text")
      .attr("transform", function(d) { return "translate(" + arc.centroid(d) + ")"; })
      .attr("dy", ".35em")
      .text(function(d) { return d.data.group; });
});

function type(d) {
  d.postCount = +d.postCount;
  return d;
}

</script>
	<%@ include file="footer.jsp"%>
	
	
	
</body>
</html>