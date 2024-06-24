<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="root" value="${pageContext.request.contextPath}/" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>환전지갑</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/exchangeWallet.css">

</head>
<body>
	<div class="contentWarp">
	<c:import url="../include/top_menu.jsp" />
	<div class="ASKWallet">
        <div class="LP">
            <c:import url="./LPSide.jsp"></c:import>
       

	    <div class="main-content">
	        <h1>환전지갑</h1>
	        <div class="withbuttonWallet">
		        <table >
		            <thead>
		                <tr>
		                    <th>통화코드</th>
		                    <th>잔액</th>
		                </tr>
		            </thead>
		            <tbody>
		                <c:forEach var="wallet" items="${walletAllList}">
		                    <tr>
		                        <td>${wallet.code_money}</td>
		                        <td>${wallet.w_balance}</td>
		                    </tr>
		                </c:forEach>
		            </tbody>
		        </table>
		        
		        <div class="actions">
		            <button>
		                <a href="${root}exchange/exchangeHistory">환전 조회</a>
		            </button>
		            <button>
		                <a href="${root}main">메인페이지</a>
		            </button>
		        </div>
	        </div>
	    </div>
	</div>
	</div>
	<c:import url="../include/bottom_info.jsp" />
	</div>
	

</body>
</html>
