<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>exchangeRate/main.jsp</title>
<!-- Bootstrap CDN -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
</head>

<style>
div {
	margin-top: 200px;
	background-color: pink;
}
</style>
<body>
	<c:set var="root" value="${pageContext.request.contextPath}" />

	<c:import url="../include/top_menu.jsp" />

	<div>
		<h2>exchangeRate Main!</h2>
		<h2>exchangeRate Main!</h2>
		<h2>exchangeRate Main!</h2>
		<h2>exchangeRate Main!</h2>
		<h2>exchangeRate Main!</h2>
		<h2>exchangeRate Main!</h2>
	</div>

	<c:import url="../include/bottom_info.jsp" />

</body>
</html>