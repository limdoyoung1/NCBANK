<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="root" value="${pageContext.request.contextPath}/" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" content="width=device-width, initial-scale=1">
<title>마이페이지</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/joinform.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
</head>
<body>
	<c:import url="../include/top_menu.jsp" />

	<div class="container mt-5">
		<form:form modelAttribute="users" action="${root}user/mypage" method="get">
			<h2>
				${user.name}님의 마이페이지 
			</h2>
			<div class="info">
			<h3>개인정보</h3>
			<p>아이디 : ${user.id}</p>
			<p>전화번호 : ${user.phone}</p>
			<p>주소 : ${user.address}</p>
			<p>이메일 : ${user.email}</p>
			</div>
			<div id="accordion">
				<div class="card">
					<div class="card-header" id="headingOne">
						<h5 class="mb-0">
							<button class="btn btn-link" data-toggle="collapse"
								data-target="#collapseOne" aria-expanded="true"
								aria-controls="collapseOne">아코디언 메뉴 1</button>
						</h5>
					</div>
					<div id="collapseOne" class="collapse show"
						aria-labelledby="headingOne" data-parent="#accordion">
						<div class="card-body">첫 번째 아코디언 메뉴의 내용입니다.</div>
					</div>
				</div>
				<div class="card">
					<div class="card-header" id="headingTwo">
						<h5 class="mb-0">
							<button class="btn btn-link collapsed" data-toggle="collapse"
								data-target="#collapseTwo" aria-expanded="false"
								aria-controls="collapseTwo">아코디언 메뉴 2</button>
						</h5>
					</div>
					<div id="collapseTwo" class="collapse" aria-labelledby="headingTwo"
						data-parent="#accordion">
						<div class="card-body">두 번째 아코디언 메뉴의 내용입니다.</div>
					</div>
				</div>
				<div class="card">
					<div class="card-header" id="headingThree">
						<h5 class="mb-0">
							<button class="btn btn-link collapsed" data-toggle="collapse"
								data-target="#collapseThree" aria-expanded="false"
								aria-controls="collapseThree">아코디언 메뉴 3</button>
						</h5>
					</div>
					<div id="collapseThree" class="collapse"
						aria-labelledby="headingThree" data-parent="#accordion">
						<div class="card-body">세 번째 아코디언 메뉴의 내용입니다.</div>
					</div>
				</div>
			</div>
		</form:form>
	</div>

	<c:import url="../include/bottom_info.jsp" />
</body>
</html>