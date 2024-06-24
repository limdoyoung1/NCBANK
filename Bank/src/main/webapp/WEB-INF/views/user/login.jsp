<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="root" value="${pageContext.request.contextPath}/" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>로그인</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/login.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
</head>
<body>

	<div class="container">

		<c:import url="../include/top_menu.jsp" />
		<div class="loginform">
			<h2 class="text-primary">로그인</h2>
			<c:if test="${fail}">
				<div class="alert alert-danger">
					<h3>로그인 실패</h3>
					<p>아이디 비밀번호를 확인해주세요</p>
				</div>
			</c:if>
			<form:form action="${root}user/login_pro" method="post"
				modelAttribute="tempLoginBean">
				<div class="form-group">
					<form:label path="id">아이디</form:label>
					<form:input path="id" class="form-control" placeholder="아이디"
						required="required" />
					<form:errors path="id" style='color:red' />
				</div>
				<div class="form-group">
					<form:label path="pwd">비밀번호</form:label>
					<form:password path="pwd" class='form-control' placeholder="비밀번호"
						required="required" />
					<form:errors path='pwd' style='color:red' />
				</div>
				<div>
					<div class="form-group text-bottom">
						<a href="${root}user/findID" class="btn-findid">아이디 찾기</a> <a
							href="${root}user/findpwd" class="btn-findpwd">비밀번호 찾기</a>
					</div>
				</div>
				<div class="form-group text-right">
					<form:button class='btn btn-primary'>로그인</form:button>
					<a href="${root}user/join" class="btn btn-danger">회원가입</a>
				</div>
			</form:form>
		</div>
			<c:import url="../include/bottom_info.jsp" />
	</div>
</body>
</html>