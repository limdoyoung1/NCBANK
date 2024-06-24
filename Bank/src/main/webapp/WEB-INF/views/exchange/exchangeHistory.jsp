<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="root" value="${pageContext.request.contextPath}/" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>환전 조회/관리</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/exchangeHistory.css">

</head>
<body>
	 <div class="container">
	<c:import url="../include/top_menu.jsp" />
	<div class="contentWarpASKHistory">
        <div class="LP">
            <c:import url="./LPSide.jsp"></c:import>
        

	    <div class="main-content">
	        <h3>환전 조회/관리</h3>
	        <table >
	            <thead>
	                <tr>
	                    <th>환전신청일</th>
	                    <th>거래구분</th>
	                    <th>통화</th>
	                    <th>금액</th>
	                    <th>적용환율</th>
	                    <th>지점 이름</th>
	                    <th>지점 전화번호</th>
	                    <th>지점 팩스번호</th>
	                </tr>
	            </thead>
	            <tbody>
	                <c:forEach var="trade" items="${tradePlusList}">
	                    <tr>
	                        <td>${trade.trade_date}</td>
	                        <td>${trade.trade_type}</td>
	                        <td>${trade.code_money}</td>
	                        <td>${trade.trade_money}</td>
	                        <td>${trade.trade_rate}</td>
	                        <td>${trade.code_bank_name}</td>
	                        <td>${trade.code_bank_tel}</td>
	                        <td>${trade.code_bank_fax}</td>
	                    </tr>
	                </c:forEach>
	            </tbody>
	        </table>
	        
	        
	        <!-- 페이지 관련 -->
	        <div class="pagination-container">
	            <ul class="pagination justify-content-center">
	                <c:choose>
	                    <c:when test="${0 >= pageBean.prevPage}">
	                        <li class="page-item disabled"> 
	                            <a href="#" class="page-link">이전</a>
	                        </li>
	                    </c:when>
	                    <c:otherwise>
	                        <li class="page-item">
	                            <a href="${root}exchange/exchangeHistory?user_num=${user_num}&page=${pageBean.prevPage}" class="page-link">이전</a>
	                        </li>
	                    </c:otherwise>
	                </c:choose>
	                <c:forEach var="idx" begin="${pageBean.min}" end="${pageBean.max}">
	                    <c:choose>
	                        <c:when test="${idx == pageBean.currentPage}">
	                            <li class="page-item active">
	                                <a href="#" class="page-link">${idx}</a>
	                            </li>
	                        </c:when>
	                        <c:otherwise>
	                            <li class="page-item">
	                                <a href="${root}exchange/exchangeHistory?user_num=${user_num}&page=${idx}" class="page-link">${idx}</a>
	                            </li>
	                        </c:otherwise>
	                    </c:choose>
	                </c:forEach>
	                <c:choose>
	                    <c:when test="${pageBean.nextPage > pageBean.pageCnt}">
	                        <li class="page-item disabled">
	                            <a href="#" class="page-link">다음</a>
	                        </li>
	                    </c:when>
	                    <c:otherwise>
	                        <li class="page-item">
	                            <a href="${root}exchange/exchangeHistory?user_num=${user_num}&page=${pageBean.nextPage}" class="page-link">다음</a>
	                        </li>
	                    </c:otherwise>
	                </c:choose>
	            </ul>
	        </div>
	        
	        <div class="actions">
	            <button>
	                <a href="${root}main">메인페이지</a>
	            </button>
	            <button>
	                <a href="${root}exchange/exchangeWallet">환전지갑</a>
	            </button>
	        </div>
	        
	    </div>
	</div>    
	</div>
	<c:import url="../include/bottom_info.jsp" />
	</div>
	
</body>
</html>
