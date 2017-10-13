<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1";
	import="javazoom.upload.*,java.util.*,servlets.jsonDV"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title> D3 tutorial</title>
<script src="http://d3js.org/d3.v3.min.js"></script>
</head>

<body>
<%
jsonDV.printJson();
%>
	<script>
		var dataArray = [20, 50,15, 100];

		var canvas = d3.select("body")
						.append("svg")
						.attr("width", 2000)
						.attr("height", 2000);
		
		var bars = canvas.selectAll("rect")
						.data(dataArray)
						.enter()
							.append("rect")
							.attr("width",function(d){return d *10})
							.attr("height", 50)
							.attr("y", function(d,i) {return i*100})
							.attr("fill","red");


	</script>

</body>
</html>