<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="root" value="${pageContext.request.contextPath}/" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>이체내역 조회</title>
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
		window.location.href = "${root}trans/transferCheck?account="
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
						<c:when test="${not empty transfers}">
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
									<th><h4>입 / 출금 계좌</h4></th>
								</tr>
								<tr>
									<td>
										<table>
											<thead>
												<tr>
													<th>거래유형</th>
													<th>이체금액</th>
													<th>입금계좌</th>
													<!-- <th>이체 후 잔액</th> -->
													<th>출금계좌</th>
													<th>이체 메모</th>
													<th>일시</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="transfer" items="${transfers}">
													<tr>
														<td><c:if test="${transfer.trans_type eq 1}">입금</c:if>
															<c:if test="${transfer.trans_type eq 2}">출금</c:if></td>
														<td><c:if test="${transfer.trans_type eq 1}">+<script>
															document
																	.write(formatBalance("${transfer.trans_money}"));
														</script>
															</c:if> <c:if test="${transfer.trans_type eq 2}">-<script>
																document
																		.write(formatBalance("${transfer.trans_money}"));
															</script>
															</c:if></td>
														<td>[${transfer.code_organ_name}]&nbsp;<script>
															document
																	.write(formatAccount("${transfer.to_account}"));
														</script></td>
														<!-- <td>&#8361;&nbsp;<script>
															document
																	.write(formatBalance("${transfer.trans_balance}"));
														</script></td> -->
														<td>[NC뱅크]&nbsp;<script>
															document
																	.write(formatAccount("${transfer.from_account}"));
														</script></td>
														<td>${transfer.trans_text}</td>
														<td><fmt:formatDate value="${transfer.trans_date}"
																pattern="yyyy.MM.d" /></td>
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
			<c:import url="/WEB-INF/views/include/bottom_info.jsp" />
		</div>
	</div>
</body>
</html>
