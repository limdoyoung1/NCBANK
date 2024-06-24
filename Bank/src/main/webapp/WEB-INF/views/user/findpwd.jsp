<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="root" value="${pageContext.request.contextPath}/" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 찾기</title>
</head>
<body>
	<c:import url="/WEB-INF/views/include/top_menu.jsp" />
	<h2>비밀번호 재설정</h2>
	<form:form modelAttribute="findMemberPwdBean" action="${root}user/findpwd_pro" method="post">
	아이디 <form:input path="id" id="id"/><br>
	전화번호 <form:input path="phone" id="phone"/>
			<button type="button" class="button" onclick="">인증번호 받기</button>
			<input type="text" id="verfication" name="verfication" placeholder="인증번호" class="form-control"/>
			<button id="verify" type="button" class="btn" onclick="">인증번호 확인</button>
	새 비밀번호 <form:input path="pwd" id="pwd"/><br>
	비밀번호 확인 <form:input path="pwd2" id="pwd2"/>
	<form:button type="submit">비밀번호 재설정</form:button> 
	</form:form>
	<c:import url="/WEB-INF/views/include/bottom_info.jsp" />
</body>
</html>