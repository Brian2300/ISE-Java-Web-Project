<%@include file="protect.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>IS102 Web Platform</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" href="style/css/bootstrap.min.css">
<link rel="stylesheet" href="style/css/font-awesome.min.css">

</head>
<body>
	<%@ include file="navigationBar.jsp"%>
	<style unselectable="on">
#wrap {
	width: 1000px;
	height: 900px;
	padding: 0;
	position: relative;
	left: 0px;
	top: 0px;
	overflow: hidden;
}

#frame {
	width: 1000px;
	height: 900px;
	position: relative;
	left: 0px;
	top: 0px;
}

#frame {
	-ms-zoom: 0.7;
}
</style>
	<div id="wrap" unselectable="on">
		<iframe id="frame"
			src="https://outlook.live.com/owa//calendar/97807ced-ea5e-4293-ad8c-4ed3bb235c8b/602cf2bc-ec86-4e30-b17d-c2fdda2aeb71/cid-6BC6524EE5D65C72/index.html"></iframe>
	</div>

	<script src="//cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"></script>
	<script src="style/js/jquery-3.2.1.min.js"></script>
	<script src="style/js/bootstrap.min.js"></script>
<%@ include file = "footer.jsp" %>
</body>
</html>