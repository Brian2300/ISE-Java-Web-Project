<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="dao.DashboardDAO"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<p>hello</p>
<body>
<p>==========D1 data ===========</p>
<%
out.println(DashboardDAO.retrieveD1data());
%>
<p>==========D2 data ===========</p>
<%
out.println(DashboardDAO.retrieveD2data());
%>
<p>==========D3 data ===========</p>
<%
out.println(DashboardDAO.retrieveD3data());

%>
<p>==========D4 data ===========</p>
<%
out.println(DashboardDAO.retrieveD4data());
%>
</body>
</html>