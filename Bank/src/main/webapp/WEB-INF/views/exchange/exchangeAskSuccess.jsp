<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="root" value="${pageContext.request.contextPath}/" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>환전 신청 완료</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/exchangeAskSuccess.css">

</head>
<body>
	
    <div class="container">
	<c:import url="../include/top_menu.jsp" />
	<div class="contentWarpASKSuccess">
        <div class="LP">
            <c:import url="./LPSide.jsp"></c:import>
       

		    <div class="main-content">
 		        <h3>환전 신청 완료</h3>
		        <div class="withbuttonSuccess">
			        <table >
			            <tr>
			                <th>환전신청금액</th>
			                <td>${createExchangeBean.code_money} ${createExchangeBean.trade_money}</td>
			            </tr>
			            <tr>
			                <th>현재고시환율</th>
			                <td>${createExchangeBean.exchange_cash_buying_rate} 원</td>
			            </tr>
			            <tr>
			                <th>적용환율</th>
			                <td>${createExchangeBean.trade_rate} 원</td>
			            </tr>
			            <tr>
			                <th>우대금액</th>
			                <td>${createExchangeBean.preferential_money} 원</td>
			            </tr>
			            <tr>
			                <th>출금계좌번호</th>
			                <td>${createExchangeBean.account}</td>
			            </tr>
			            <tr>
			                <th>수령희망날짜</th>
			                <td>${createExchangeBean.trade_reservation_date}</td>
			            </tr>
			            <tr>
			                <th>수령희망 지점번호</th>
			                <td>${createExchangeBean.code_bank}</td>
			            </tr>
			            <tr>
			                <th>수령희망 지점이름</th>
			                <td>${createExchangeBean.code_bank_name}</td>
			            </tr>
			        </table>
			        
			        <div class="actionbutton" style="margin-top: 20px;">
			            <button>
			                <a href="${root}main">메인페이지 가기</a>
			            </button>
			            <button>
			                <a href="${root}exchange/exchangeHistory">환전 조회하기</a>
			            </button>
			        </div>
		        </div> <!-- withbuttonSuccess -->
		        
		    </div> <!-- main-content -->
    	</div> <!-- LP -->
	</div> <!-- contentWarpASKSuccess -->
	<c:import url="../include/bottom_info.jsp" />
	</div> <!-- container -->

</body>
</html>
