<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="root" value="${pageContext.request.contextPath}/" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>계좌 개설</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/accountCreate.css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<c:import url="/WEB-INF/views/include/top_menu.jsp" />
		<div class="container-fluid">
			<div class="row">
				<div class="col-md-2">
					<div class="transfer">이체</div>
					<div class="transferhr">
						<hr />
						<ul>
							<li><a href="${root}account/accountCreate">계좌 개설</a></li>
							<li><a href="${root}trans/transfer">계좌 이체</a></li>
							<li><a href="${root}auto/transferAuto">자동이체 등록</a></li>
						</ul>
					</div>
				</div>
				<div class="col-md-10">
					<table>
						<tr>
							<th><h3>계좌 개설</h3></th>
						</tr>
						<tr>
							<td><c:if test="${not empty errorMessage}">
									<div style="color: red;">${errorMessage}</div>
								</c:if> <form:form modelAttribute="accountBean"
									action="${root}account/accountCreate" method="post">
									<table>
										<tr>
											<td>성명</td>
											<td>${users.name}</td>
										</tr>
										<tr>
											<td>주소</td>
											<td>${users.address}</td>
										</tr>
										<tr>
											<td>전화번호</td>
											<td>${users.phone}</td>
										</tr>

										<tr>
											<td>이메일</td>
											<td>${users.email}</td>
										</tr>
										<tr>
											<td>계좌 비밀번호</td>
											<td><input type="password" name="acPassword"
												required="required" placeholder="계좌 비밀번호" maxlength="4" /></td>
										</tr>
										<tr>
											<td>계좌 비밀번호 확인</td>
											<td><input type="password" name="acPasswordConfirm"
												required="required" placeholder="계좌 비밀번호 확인" maxlength="4" /></td>
										</tr>
										<tr>
											<td>계좌 분류</td>
											<td><form:select path="ac_type" required="true">
													<form:option value="0">저축예금</form:option>
													<form:option value="2">적금통장</form:option>
												</form:select></td>
										</tr>
										<tr>
											<td colspan="2" style="text-align: center; margin-top: 20px;">
												<button type="submit">계좌 개설</button>
											</td>
										</tr>
									</table>
								</form:form></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/bottom_info.jsp" />
	</div>
</body>
</html>