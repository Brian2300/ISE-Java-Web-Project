
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
<div style="width: 1450px; margin: 0 auto">
	<div><svg id="dashboard1" width="700" height="600">
		<text x="80" y="60" font-size ="25px">Tag Distribution</text>
	</svg>
		<svg id="dashboard2" width="700" height="600">
			<text x="80" y="60" font-size ="25px">Post Distribution</text>
		</svg></div>
	<div><svg id="dashboard3" width="700" height="600">
		<text x="80" y="130" font-size ="25px">Mark Distribution</text>
	</svg>
		<svg id="dashboard4" width="700" height="600">
			<text x="80" y="130" font-size ="25px">QA coins Distribution</text>
		</svg></div>
</div>


<script src="https://d3js.org/d3.v4.min.js"></script>
<script src="//d3js.org/d3-scale-chromatic.v0.3.min.js"></script>
<script>
	var d1Data = <%=DashboardDAO.retrieveD1data()%>
	var d2Data = <%=DashboardDAO.retrieveD2data()%>
	var d3Data = <%=DashboardDAO.retrieveD3data()%>
	var d4Data = <%=DashboardDAO.retrieveD4data()%>
	
	
	
	var categorical = [
		  { "name" : "schemeAccent", "n": 8},
		  { "name" : "schemeDark2", "n": 8},
		  { "name" : "schemePastel2", "n": 8},
		  { "name" : "schemeSet2", "n": 8},
		  { "name" : "schemeSet1", "n": 9},
		  { "name" : "schemePastel1", "n": 9},
		  { "name" : "schemeCategory10", "n" : 10},
		  { "name" : "schemeSet3", "n" : 12 },
		  { "name" : "schemePaired", "n": 12},
		  { "name" : "schemeCategory20", "n" : 20 },
		  { "name" : "schemeCategory20b", "n" : 20},
		  { "name" : "schemeCategory20c", "n" : 20 }
		]
	var colorScale = d3.scaleOrdinal(d3[categorical[0].name])
	
	var sequentialButtons = d3.select(".categoricalButtons")
    .selectAll("button")
    .data(categorical)
    .enter().append("button")
    .text(function(d) { return d.name; })
    .on("click", function(buttonValue) {

     var colorScale = d3.scaleOrdinal(d3[buttonValue.name]);
    }
	let info = d3.select('#info');
	let rainbow = (t)=>{
		//let color = d3.scaleLinear().domain([0.1,0.2,0.5,0.6,0.7,0.8,1]).range(["#c7001e", "#f6a580", "#cccccc", "#92c6db", "#086fad"]);
		//let color = d3.scaleLinear().domain([0.1,0.2,0.5,0.6,0.7,0.8,1]).range(["#ff8c00","#d0743c","#a05d56", "#6b486b","#7b6888", "#f1a340","#998ec3"]);		
		let color = d3.scaleLinear().domain([0.1,0.2,0.5,0.6,0.7,0.8,1]).range(["#9933ff","#cc33ff", "#9966ff","#9999ff","#99ccff","#66ccff"]);		
		return colorScale(t);
	};
//below is to select data loading source
/*	
	control({
		d1:'d1TagCount.csv',
		d2:'d2groupPostCount.csv',
		d3:'d3Marks.csv',
		d4:'d4QAcoins.csv',
	},true);
*/
	control({
		d1:d1Data,
		d2:d2Data,
		d3:d3Data,
		d4:d4Data,
	},false);

	function control(data, isCsv) {
		if(isCsv){
			let queue = d3.queue();
			queue.defer(d3.csv,data.d1)//'d1TagCount.csv'
				.defer(d3.csv,data.d2)//'d2groupPostCount.csv'
				.defer(d3.csv,data.d3,row=>{row.mark = +row.mark; return row;})//'d3Marks.csv'
				.defer(d3.csv,data.d4,row=>{row.Qacoins = +row.Qacoins; return row;})//'d4QAcoins.csv'
				.await((error,d1Data, d2Data, d3Data, d4Data)=>{
					if(error) throw new Error(error);
					draw(d1Data, d2Data, d3Data, d4Data);
				});
		}else {
			draw(data.d1, data.d2, data.d3, data.d4);
		}
	}

function draw(d1Data, d2Data, d3Data, d4Data) {
	let margin = {
		left:100,
		right:100,
		top:100,
		bottom:100
	};

	let svg_d1 = d3.select('#dashboard1');
	let d1_g = svg_d1.append('g').attr('transform','translate('+margin.left+','+margin.top+')');
	svg_d1.on('click',()=>{
		if(svg_d1.attr('switch')==='on'){
			svg_d1.attr('switch','false');
			renderData1(d1Data);
		}
	});
	renderData1(d1Data);
	function renderData1(d1Data, group) {
		let width_d1 = +svg_d1.attr('width') - margin.left - margin.right;
		let height_d1 = +svg_d1.attr('height') - margin.top - margin.bottom;
		d1_g.remove();
		d1_g = svg_d1.append('g').attr('transform','translate('+margin.left+','+margin.top+')');
		let duration=1000;

		let groups = d3.set(d1Data,d=>d.Group);
		let d1_nest = d3.nest().key(d=>d.tag).key(d=>d.Group).entries(d1Data);
		let data14Stack = d1_nest.map(d=>{
			let data = d.values;
			let obj = {};
			obj.tag = d.key;
			data.forEach(_d=>{
				obj[_d.key] = _d.values.length;
			});
			return obj;
		});


		if(group){

			data14Stack.forEach(d=>{
				groups.each(g=>{
					if(d[g] === undefined){
						d[g] = 0;
					}else if(g !== group){
						d[g] = 0;
					}
				});
			});

		}else {

			data14Stack.forEach(d=>{
				groups.each(g=>{
					if(d[g] === undefined){
						d[g] = 0;
					}
				});
			});
		}
		data14Stack = data14Stack.sort((a,b)=>{
			let sumb=0, suma = 0;
			groups.each(g=>{
				suma+=a[g];
				sumb+=b[g];
			});

			return sumb - suma;
		});

		let stack = d3.stack()
			.keys(groups.values())
			.order(d3.stackOrderNone)
			.offset(d3.stackOffsetNone);
		let data1Series = stack(data14Stack);
		let g_v = groups.values();
		data1Series.forEach((d,i)=>{
			d.forEach(v=>{
				v.group = g_v[i];
			})
		});

		let d1_scaleX = d3.scaleBand()
			.domain(data14Stack.map(d=>d.tag))
			.rangeRound([0, width_d1])
			.paddingInner(0.05)
			.paddingOuter(0.05)
			.align(0.5);

		let d1_scaleY = d3.scaleLinear()
			.domain([0, d3.max(data14Stack, d=>{let sum = 0;groups.each(g=>sum +=d[g]); return sum})]).nice()
			.rangeRound([height_d1, 0]);
		let z = d3.scaleOrdinal()
			.domain(groups.values())
			.range(["#c7001e", "#f6a580", "#cccccc", "#92c6db", "#086fad"]);
			//.range(["#998ec3", "#f1a340","#7b6888","#6b486b", "#a05d56", "#d0743c", "#ff8c00"]);

		d1_g.append('g').selectAll('g')
			.data(data1Series)
			.enter().append('g')
			.style('fill',d=>z(d.key))
			.on('click',d=>{
				if(svg_d1.attr('switch')==="on"){
					svg_d1.attr('switch','false');
					renderData1(d1Data);
				}else {
					svg_d1.attr('switch','on');
					renderData1(d1Data,d.key);
				}

				d3.event.stopPropagation();
			})
			.selectAll("rect")
			.data(function(d) { return d;})
			.enter().append("rect")
			.attr("x", d => d1_scaleX(d.data.tag))
			.attr("y", height_d1)
			.attr("height",0)
			.attr("width", d1_scaleX.bandwidth())
			.on('mousemove',(d,i)=>{
				let coord = d3.mouse(document.body);
				info.style('left',(coord[0]+20)+'px')
					.style('top',(coord[1]+20)+'px');
				info.html('<div>tag: '+d.data.tag+'</div><div>group: '+d.group+'</div>'+
'<div>number of post_id count: '+d.data[d.group]+'</div>');
			})
			.on('mouseout',()=>{info.style('left','-1000px')})
			.transition()
			.duration(duration)
			.attr("y", d => d1_scaleY(d[1]))
			.attr('height', d => d1_scaleY(d[0]) - d1_scaleY(d[1]));

		d1_g.append("g")
			.attr("class", "axis x")
			.attr("transform", "translate(0," + height_d1 + ")")
			.call(d3.axisBottom(d1_scaleX));
		d1_g.append('g')
			.append("text")
			.attr("x", width_d1)
			.attr("y", d1_scaleY(d1_scaleY.ticks()[0]))
			.attr("fill", "#000")
			.style('font-size',13)
			.attr("font-weight", "bold")
			.attr("text-anchor", "start")
			.text("tag");

		d1_g.append("g")
			.attr("class", "axis")
			.call(d3.axisLeft(d1_scaleY));
		d1_g.append('g')
			.append("text")
			.classed('vertical',true)
			.attr("x", 2)
			.attr("y", d1_scaleY(d1_scaleY.ticks().pop()) - 30)
			.attr("fill", "#000")
			.style('font-size',13)
			.attr("font-weight", "bold")
			.text("number of post");
	}


	let svg_d2 = d3.select('#dashboard2');
	let d2_g = svg_d2.append('g').attr('transform','translate('+margin.left+','+margin.top+')');

	svg_d2.on('click',()=>{
		if(svg_d2.attr('switch')==='on'){
			svg_d2.attr('switch','false');
			renderData2(d2Data);
		}
	});
	renderData2(d2Data);
	function renderData2(d2Data, group) {
		//let rainbow = d3.interpolateRainbow;
		let width_d2 = +svg_d2.attr('width') - margin.left - margin.right;
		let height_d2 = +svg_d2.attr('height') - margin.top - margin.bottom;
		d2_g.remove();
		d2_g = svg_d2.append('g').attr('transform','translate('+margin.left+','+margin.top+')');
		let duration=1000;
		let radius = 200;
		let d2_nest = d3.nest().key(d=>d.group).entries(d2Data);
		let data24Arc;
		if(group){
			let data = d2_nest.find(d=>d.key === group).values;
			let _nest = d3.nest().key(d=>d.email_id).entries(data);
			data24Arc = _nest.map(d=>({tag:d.key, val:d.values.length}));

		}else {
			data24Arc = d2_nest.map(d=>({tag:d.key, val:d.values.length}));
		}
		data24Arc = data24Arc.sort((a,b)=>b.val - a.val);

		let data2Pie = d3.pie().sort(null)
			.value(function(d) { return d.val; });
		let data2arc = d3.arc()
			.outerRadius(radius - 10)
			.innerRadius(radius - 100);
		let labelArc = d3.arc()
			.outerRadius(radius - 55)
			.innerRadius(radius - 55);
		let arcs = data2Pie(data24Arc);

		let arcs_d2 = d2_g.append('g')
			.attr('transform','translate('+width_d2/2+','+height_d2/2+')')
			.selectAll('.arc').data(arcs);
		arcs_d2.enter().append('path')
			.classed('arc',true)
			.merge(arcs_d2)
			.on('click',d=>{
				if(svg_d2.attr('switch') === 'on'){
					svg_d2.attr('switch','false');
					renderData2(d2Data);
				}else {
					svg_d2.attr('switch','on');
					renderData2(d2Data, d.data.tag);
				}
				d3.event.stopPropagation();
			})
			.on('mousemove',d=>{
				let coord = d3.mouse(document.body);
				info.style('left',(coord[0]+20)+'px')
					.style('top',(coord[1]+20)+'px');
				if(svg_d2.attr('switch') === 'on'){
					info.html('<div>email_id: '+d.data.tag+'</div><div>number of post: '+d.value+'</div>');
				}else {
					info.html('<div>number of post: '+d.value+'</div><div>percentage: '+(d.value/d2Data.length*100).toFixed(2) +"%"+'</div>');
				}
			})
			.on('mouseout',()=>{info.style('left','-1000px')})
			.style("fill",(d,i)=>rainbow(i/data24Arc.length))
			.transition().duration(duration)
			.attrTween('d',d=>{
				let start = {innerRadius:0,outerRadius:0,startAngle:0, endAngle:0};
				let interpolate = d3.interpolate(start, d);
				return function (t) {
					return data2arc(interpolate(t));
				};
			});

		let labels_d2 = d2_g.append('g')
			.attr('transform','translate('+width_d2/2+','+height_d2/2+')')
			.selectAll('.label').data(arcs);
		labels_d2.enter().append('text')
			.classed('label',true)
			.merge(labels_d2)
			.text(d=>d.data.tag)
			.style('text-anchor','middle')
			.style("opacity",0)
			.transition().duration(duration)
			.attrTween('x',d=>{
				let start = {innerRadius:0,outerRadius:0,startAngle:0, endAngle:0};
				let interpolate = d3.interpolate(start, d);
				return (t)=>labelArc.centroid(interpolate(t))[0];
			})
			.attrTween('y',d=>{
				let start = {innerRadius:0,outerRadius:0,startAngle:0, endAngle:0};
				let interpolate = d3.interpolate(start, d);
				return (t)=>labelArc.centroid(interpolate(t))[1];
				//labelArc.centroid(d)[1]
			})
			.style('opacity',1);
	}

	let svg_d3 = d3.select('#dashboard3');
	svg_d3.on('click',()=>{
		if(svg_d3.attr('switch')==='on'){
			svg_d3.attr('switch','false');
			renderData3(d3Data);
		}
	});
	renderData3(d3Data);
	function renderData3(data, week, group) {
		//let rainbow = d3.interpolateRainbow;
		let width = +svg_d3.attr('width') - margin.left - margin.right;
		let height = +svg_d3.attr('height') - margin.top - margin.bottom;
		let g_top = svg_d3.select('#g_top');
		g_top.empty()?"":g_top.remove();
		g_top = svg_d3.append('g').attr('id','g_top').attr('transform','translate('+margin.left+','+margin.top+')');
		let duration=1000;

		let d_nest = d3.nest().key(d=>d.week).key(d=>d.group).entries(data);
		let d_rect;
		if(group){
			let _ = d_nest.find(d=>d.key === week).values.find(d=>d.key === group);
			d_rect = _.values.map(d=>{
				let obj = {};
				obj.week = d.email_id;
				obj.val = [{week:d.email_id,group:d.group, val: d.mark}];
				return obj;
			});
			d_rect.sort((a,b)=>{
				return b.val[0].val - a.val[0].val;
			});
		}else {
			d_rect = d_nest.map(d=>{
				let obj = {};
				obj.week = d.key;
				obj.val = d.values.map(v=>{
					let o = {};
					o.week = d.key;
					o.group = v.key;
					o.val = d3.mean(v.values, m=>m.mark);
					return o;
				});
				return obj;
			});
			/*d_rect.forEach(r=>{
				r.val = r.val.sort((a,b)=>b.val - a.val);
			});*/
		}


		let keys=[];
		d_rect.forEach(d=>{
			d.val.forEach(v=>{
				keys.push(v.group);
			})
		});

		let scale_x = d3.scaleBand()
			.domain(d_rect.map(d=>d.week)).rangeRound([0, width])
			.paddingInner(0.05)
			.paddingOuter(0.05)
			.align(0.5);
		let scale_y = d3.scaleLinear()
			.domain([0,d3.max(d_rect.map(d=>d3.max(d.val,v=>v.val)))])
			.range([height,100]);

		g_top.selectAll('g')
			.data(d_rect)
			.enter().append('g')
			.selectAll('rect')
			.data(d=>d.val)
			.enter().append('rect')
			.on('click',d=>{

				if(svg_d3.attr('switch') === 'on'){
					svg_d3.attr('switch','false');
					renderData3(data);
				}else {
					svg_d3.attr('switch','on');
					renderData3(data, d.week, d.group);
				}
				d3.event.stopPropagation();
			})
			.on('mousemove',d=>{

				let coord = d3.mouse(document.body);
				info.style('left',(coord[0]+20)+'px')
					.style('top',(coord[1]+20)+'px');
				if(svg_d3.attr('switch') === 'on'){
					info.html('<div>email id: '+d.week+'</div><div>mark: '+d.val+'</div>');
				}else {
					info.html('<div>average mark: '+d.val.toFixed(2)+'</div><div>group: '+d.group+'</div>');
				}
			})
			.on('mouseout',()=>{info.style('left','-1000px')})
			.style('fill',(d,i,arr)=>rainbow(i/arr.length))
			.attr('width', (d,i,arr)=>scale_x.bandwidth()/arr.length)
			.attr('x',(d,i,arr)=>scale_x(d.week)+ scale_x.bandwidth()/arr.length*i)
			.attr('y',height)
			.attr('height',0)
			.transition().duration(duration)
			.attr('y',d=>scale_y(d.val))
			.attr('height',d=>height - scale_y(d.val));

		g_top.append("g")
			.attr("class", ()=>{
				if(svg_d3.attr('switch') === 'on' && d_rect.map(d=>d.week).length>4){
					return "axis x";
				}else {
					return "axis";
				}
			})
			.attr("transform", "translate(0," + height + ")")
			.call(d3.axisBottom(scale_x));
		g_top.append('g')
			.append("text")
			.attr("x", width)
			.attr("y", scale_y(scale_y.ticks()[0]))
			.attr("fill", "#000")
			.style('font-size',13)
			.attr("font-weight", "bold")
			.attr("text-anchor", "start")
			.text(()=>{
				if(svg_d3.attr('switch') === 'on'){
					return "email id";
				}else {
					return "week";
				}

			});

		g_top.append("g")
			.attr("class", "axis")
			.call(d3.axisLeft(scale_y));

		g_top.append('g')
			.append("text")
			.classed('vertical',true)
			.attr("x", -100)
			.attr("y", - 30)
			.attr("fill", "#000")
			.style('font-size',13)
			.attr("font-weight", "bold")
			.text(()=>{
				if(svg_d3.attr('switch') === 'on'){
					return "mark";
				}else {
					return "average mark";
				}

			});
	}


	d4Data = d4Data.map(d=>({
		email_id: d.email_id,
		group: d.group,
		week: d.group,
		mark:d.Qacoins
	}));
	let svg_d4 = d3.select('#dashboard4');
	svg_d4.on('click',()=>{
		if(svg_d4.attr('switch')==='on'){
			svg_d4.attr('switch','false');
			renderData4(d4Data);
		}
	});
	renderData4(d4Data);

	function renderData4(data, week, group) {
		console.log(data);
	
		
		
		//let rainbow = d3.interpolateRainbow;
		let width = +svg_d4.attr('width') - margin.left - margin.right;
		let height = +svg_d4.attr('height') - margin.top - margin.bottom;
		let g_top = svg_d4.select('#g_top');
		g_top.empty()?"":g_top.remove();
		g_top = svg_d4.append('g').attr('id','g_top').attr('transform','translate('+margin.left+','+margin.top+')');
		let duration=1000;

		let d_nest = d3.nest().key(d=>d.week).key(d=>d.group).entries(data);
		let d_rect;
		if(group){
			let _ = d_nest.find(d=>d.key === week).values.find(d=>d.key === group);

			d_rect = _.values.map(d=>{
				let obj = {};
				obj.week = d.email_id;
				obj.val = [{week:d.email_id,group:d.group, val: d.mark}];
				return obj;
			});
		}else {
			d_rect = d_nest.map(d=>{
				let obj = {};
				obj.week = d.key;
				obj.val = d.values.map(v=>{
					let o = {};
					o.week = d.key;
					o.group = v.key;
					o.val = d3.mean(v.values, m=>m.mark);
					return o;
				});
				return obj;
			});
		}
		d_rect.sort((a,b)=>{
			return b.val[0].val - a.val[0].val;
		});


		let keys=[];
		d_rect.forEach(d=>{
			d.val.forEach(v=>{
				keys.push(v.group);
			})
		});

		let scale_x = d3.scaleBand()
			.domain(d_rect.map(d=>d.week)).rangeRound([0, width])
			.paddingInner(0.05)
			.paddingOuter(0.05)
			.align(0.5);
		let scale_y = d3.scaleLinear()
			.domain([0,d3.max(d_rect.map(d=>d3.max(d.val,v=>v.val)))])
			.range([height,100]);

		g_top.selectAll('g')
			.data(d_rect)
			.enter().append('g')
			.style('fill',(d,i,arr)=>rainbow(i/arr.length))
			.selectAll('rect')
			.data(d=>d.val)
			.enter().append('rect')
			.on('click',d=>{

				if(svg_d3.attr('switch') === 'on'){
					svg_d3.attr('switch','false');
					renderData4(data);
				}else {
					svg_d3.attr('switch','on');
					renderData4(data, d.week, d.group);
				}
				d3.event.stopPropagation();
			})
			.on('mousemove',d=>{
				let coord = d3.mouse(document.body);
				info.style('left',(coord[0]+20)+'px')
					.style('top',(coord[1]+20)+'px');
				if(svg_d3.attr('switch') === 'on'){
					info.html('<div>email id: '+d.week+'</div><div>QA coins: '+d.val.toFixed(2)+'</div>');
				}else {
					info.html('<div>average QA coins: '+d.val.toFixed(2)+'</div><div>group: '+d.group+'</div>');
				}
			})
			.on('mouseout',()=>{info.style('left','-1000px')})

			.attr('width', (d,i,arr)=>scale_x.bandwidth()/arr.length)
			.attr('x',(d,i,arr)=>scale_x(d.week)+ scale_x.bandwidth()/arr.length*i)
			.attr('y',height)
			.attr('height',0)
			.transition().duration(duration)
			.attr('y',d=>scale_y(d.val))
			.attr('height',d=>height - scale_y(d.val));

		g_top.append("g")
			.attr("class", ()=>{
				if(svg_d3.attr('switch') === 'on' && d_rect.map(d=>d.week).length>4){
					return "axis x";
				}else {
					return "axis";
				}
			})
			.attr("transform", "translate(0," + height + ")")
			.call(d3.axisBottom(scale_x));
		g_top.append('g')
			.append("text")
			.attr("x", width)
			.attr("y", scale_y(scale_y.ticks()[0]))
			.attr("fill", "#000")
			.style('font-size',13)
			.attr("font-weight", "bold")
			.attr("text-anchor", "start")
			.text(()=>{
				if(svg_d3.attr('switch') === 'on'){
					return "email id";
				}else {
					return "group";
				}

			});

		g_top.append("g")
			.attr("class", "axis")
			.call(d3.axisLeft(scale_y));
		g_top.append('g')
			.append("text")
			.classed('vertical',true)
			.attr("x", -100)
			.attr("y", - 30)
			.attr("fill", "#000")
			.style('font-size',13)
			.attr("font-weight", "bold")
			.text(()=>{
				if(svg_d3.attr('switch') === 'on'){
					return "QAcoins";
				}else {
					return "average QA coins";
				}
			});
	}
}
</script>




<%@ include file="footer.jsp"%>
</body>
</html>