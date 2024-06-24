<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="root" value="${pageContext.request.contextPath}/" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 찾기</title>
</head>
<body>
	<c:import url="/WEB-INF/views/include/top_menu.jsp" />
	<h2>아이디 찾기</h2>
	<form:form modelAttribute="findMemberIDBean" action="${root}user/findID_pro" method="post">
	이름  <form:input path="name" id="name"/><br>
	전화번호  <form:input path="phone" id="phone"/><br>
	
	<form:button type="submit">아이디 찾기</form:button>
	
	</form:form>
	<c:import url="/WEB-INF/views/include/bottom_info.jsp" />
	
</body>
</html>