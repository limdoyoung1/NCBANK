<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="root" value="${pageContext.request.contextPath}/" />

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>환율 조회</title>
</head>
<!-- Bootstrap CDN -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>


<link rel="stylesheet" href="${root}css/exchange/rateInquiry.css">


<body>
    <div class="container">

        <c:import url="../include/top_menu.jsp" />

        <div class="contentWarp">

            <div class="LPCP">

                <c:import url="./LPSide.jsp"></c:import>

                <div class="CP">
                    <form action="${root}exchange/rateInquiryDate" method="get">
                        <table class="tType02">
                            <colgroup>
                                <col width="20%">
                                <col width="80%">
                            </colgroup>
                            <tbody>
                                <tr>
                                    <th>조회일</th>
                                    <td>
                                        <div class="btnArea">
                                            <button type="button" class="bType02"
                                                onclick="today()">오늘</button>
                                            <button type="button" class="bType02"
                                                onclick="yesterday()">전일</button>
                                            <button type="button" class="bType02"
                                                onclick="tomorrow()">다음일</button>
                                        </div> <!-- required="required" : 반드시 폼 값을 채워야 한다. --> <input
                                            class="dateinput" type="date" id="inquiryDate"
                                            name="inquiryDate" required="required">
                                        <script type="text/javascript">
                                            // 서버에서 전달된 날짜 값 -> js 변수
                                            var inquiryDate = "${inquiryDate2}";
                                            // input에 기본값으로 설정
                                            document
                                                .getElementById("inquiryDate").value = inquiryDate;
                                        </script>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        <div class="submitArea">
                            <input type="submit" class="bType01" value="조회" />
                        </div>
                    </form>
                    <!-- Starg~End 날짜 기간의 환율 데이터 추가 test -->
                    <a href="${root}exchange/addRateInquiry_DateRange">범위기간DB추가</a>
                    <!-- 메일 전송 test -->
                    <!-- 
                    <a href="${root}exchange/sendNoticeMail">메일전송</a>
					 -->
                    <div class="inquiryTable">
                        <p class="txtRateBox">
                            <span class="rightText"> 기준일시 | <strong>${inquiryDate1}</strong>&nbsp;</span> 
                        </p>

                        <table class="tType01">
                            <thead>
                                <tr>
                                    <th rowspan="2">구분</th>
                                    <th rowspan="2" colspan="2">통화표시</th>
                                    <th rowspan="2">매매기준율</th>
                                    <th colspan="2">송금</th>
                                </tr>

                                <tr>
                                    <th>받으실 때</th>
                                    <th>보내실 때</th>
                                </tr>
                            </thead>
                            <!-- API 데이터를 가져와서 For Each -->
                            <tbody>
                                <c:forEach var="bean" items="${ExchangeRateList}">
                                    <c:if test="${'KRW' != bean.code_money.toUpperCase().trim()}">
                                        <tr>
                                            <td class="tLeft">${bean.code_money_name}</td>
                                            <td><img src="${root}img/Flags/${bean.code_money}.png"
                                                    onerror="this.style.display='none'"></td>
                                            <td>${bean.code_money}</td>
                                            <td>${bean.ex_standard}</td>
                                            <td class="tRight">${bean.ex_buy}</td>
                                            <td class="tRight">${bean.ex_sell}</td>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                            </tbody>
                        </table>

                        <c:if test="${null == ExchangeRateList or empty ExchangeRateList}">
                            <p class="exRateNotFound">
                                <span>해당 날짜의 환율 정보가 없습니다.</span>
                            </p>
                        </c:if>
                    </div>
                    <!-- div.inquiryTable -->
                </div>
            </div>


            <!-- div.CP -->
        </div>


        <c:import url="../include/bottom_info.jsp" />
    </div>


    <script type="text/javascript" src="${root}js/exchange/inquiryDateBtn.js"></script>

</body>

</html>