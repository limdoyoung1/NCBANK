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
<title>계좌 조회</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/accountCheck.css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
<script>
	function formatBalance(balance) {
		return balance.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	}

	function formatAccountNumber(accountNumber) {
		return accountNumber.replace(/(\d{3})(\d{8})(\d{2})(\d{1})/,
				"$1-$2-$3-$4");
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
					<table>
						<tr>
							<th><h3>입 / 출금 계좌</h3></th>
						</tr>
						<tr>
							<td>
								<table>
									<thead>
										<tr>
											<th>계좌분류</th>
											<th>계좌번호</th>
											<th>잔액</th>
											<th>계좌개설일</th>
											<th>업무</th>
										</tr>
									</thead>
									<tbody>
										<c:set var="totalBalance" value="0" />
										<c:forEach var="account" items="${accounts}">
											<tr>
												<td><c:choose>
														<c:when test="${account.ac_type == 0}">
											저축예금
											</c:when>
														<c:when test="${account.ac_type == 1}">
											모임통장
											</c:when>
													</c:choose></td>
												<td><script>
													document
															.write(formatAccountNumber("[NC뱅크]&nbsp;${account.account}"));
												</script></td>
												<td><script>
													document
															.write("&#8361;&nbsp;"
																	+ formatBalance("${account.ac_balance}"));
												</script></td>
												<td><fmt:formatDate value="${account.ac_date}"
														pattern="yyyy.MM.d" /></td>
												<td>
													<button class="transfer-check-button"
														onclick="window.location.href='${root}trans/transferCheck'">조회</button>
													<button class="transfer-button"
														onclick="window.location.href='${root}trans/transfer'">이체</button>
												</td>
											</tr>
											<c:set var="totalBalance"
												value="${totalBalance + account.ac_balance}" />
										</c:forEach>
										<tr>
											<th colspan="4" class="total">입 / 출금계좌 총 잔액</th>
											<td class="total-balance"><script>
												var totalBalance = <c:out value="${totalBalance}" />;
												document
														.write("&#8361;&nbsp;"
																+ formatBalance(totalBalance));
											</script></td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/bottom_info.jsp" />
	</div>
</body>
</html>