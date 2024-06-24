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
<title>자동이체 조회/변경/취소</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/transferCheck.css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
<script>
	function filterTransfers() {
		var selectedAccount = document.getElementById("accountSelect").value;
		if (!selectedAccount) {
			alert('계좌를 선택해 주세요');
			return;
		}
		window.location.href = "${root}auto/transferAutoCheck?account="
				+ selectedAccount;
	}
	function formatBalance(balance) {
		return balance.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	}

	function formatAccount(account) {
		if (account.startsWith("005")) {
			return account.replace(/(\d{3})(\d{8})(\d{2})(\d{1})/,
					"$1-$2-$3-$4");
		}
		return account;
	}
</script>
</head>
<body>
	<div class="container">
		<c:import url="/WEB-INF/views/include/top_menu.jsp" />
		<div class="container-fluid">
			<div class="row">
				<div class="col-md-2">
					<div class="enquiry">조회</div>
					<div class="enquiryhr">
						<hr />
					</div>
					<ul>
						<li><a href="${root}account/accountCheck">계좌 조회</a></li>
						<li><a href="${root}trans/transferCheck">이체내역 조회</a></li>
						<li><a href="${root}auto/transferAutoCheck">자동이체 조회</a></li>
					</ul>
				</div>
				<div class="col-md-10">
					<c:choose>
						<c:when test="${not empty autoList}">
							<table>
								<tr>
									<td><select id="accountSelect">
											<option value="">계좌를 선택하세요</option>
											<c:forEach var="account" items="${accounts}">
												<option value="${account.account}"
													<c:if test="${account.account eq selectedAccount}">selected</c:if>>${account.account}</option>
											</c:forEach>
									</select>
										<button onclick="filterTransfers()">조회</button></td>
								</tr>
							</table>
							<table>
								<tr>
									<th><h2>자동이체 조회/수정/삭제</h2></th>
								</tr>
								<tr>
									<td>
										<table>
											<thead>
												<tr>
													<th>자동이체명</th>
													<th>이체금액</th>
													<th>입금계좌</th>
													<th>이체시작일</th>
													<th>이체종료일</th>
													<th>이체주기</th>
													<th>이체일</th>
													<th>업무</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="auto" items="${autoList}">
													<tr>
														<td>${auto.auto_name}</td>
														<td>&#8361;&nbsp;<script>
															document
																	.write(formatBalance("${auto.auto_money}"));
														</script></td>
														<td>[${auto.code_organ_name}]&nbsp;<script>
															document
																	.write(formatAccount("${auto.to_account}"));
														</script></td>
														<td><fmt:formatDate value="${auto.auto_start}"
																pattern="yyyy.MM.d" /></td>
														<td><fmt:formatDate value="${auto.auto_end}"
																pattern="yyyy.MM.d" /></td>
														<td><c:if test="${auto.auto_type eq 1}">매일</c:if> <c:if
																test="${auto.auto_type eq 2}">매주</c:if> <c:if
																test="${auto.auto_type eq 3}">매월</c:if></td>
														<td>${auto.auto_next_date}</td>
														<td>
															<button class="transfer-check-button"
																onclick="window.location.href='${root}auto/transferAutoFix?auto_num=${auto.auto_num}'">수정</button>
															<form:form action="${root}auto/deleteTransferAuto"
																method="post" onsubmit="return confirm('정말 삭제하시겠습니까?');">
																<input type="hidden" name="auto_num"
																	value="${auto.auto_num}" />
																<button type="submit">삭제</button>
															</form:form>
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</td>
								</tr>
							</table>
						</c:when>
						<c:otherwise>
							<table>
								<tr>
									<td><select id="accountSelect">
											<option value="">선택</option>
											<c:forEach var="account" items="${accounts}">
												<option value="${account.account}">${account.account}</option>
											</c:forEach>
									</select>
										<button onclick="filterTransfers()">조회</button></td>
								</tr>
								<tr>
									<td>
										<p>계좌를 선택해 주세요</p>
									</td>
								</tr>
							</table>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/bottom_info.jsp" />
	</div>
</body>
</html>
