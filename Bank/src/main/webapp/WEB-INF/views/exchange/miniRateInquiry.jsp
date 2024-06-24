<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="root" value="${pageContext.request.contextPath}/" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>환율정보</title>
</head>

<link rel="stylesheet" href="${root}css/exchange/miniRateInquiry.css">

<body>

	<!-- ${root}img/Flags/${rateDTO.code_money}.png -->
	
    <div class="contentBox">
        <div class="top">
            <h2 class="subject">환율정보</h2>
            <span class="inquiryDate"> 기준일시 | <strong>${finalInquiryDate}</strong>&nbsp;</span>
        </div>

        <div class="tableArea">
            <table class="tType01">
                <thead>
                    <tr>
                        <th colspan="2">통화</th>
                        <th>매매기준율</th>
                        <th class="cBlue">송금 받을때</th>
                        <th class="cRed">송금 보낼때</th>
                    </tr>
                </thead>

                <tbody>
                    <c:forEach var="rateDTO" items="${rateDTOList}">
                        <tr>
                            <td>
                                <img class="FlagImg" src="${root}img/Flags/${rateDTO.code_money}.png" onerror="this.style.display='none'">
                            </td>
                            <td>${rateDTO.code_money}</td>
                            <td class="tR">${rateDTO.ex_standard}</td>
                            <td class="cBlue tR">${rateDTO.ex_buy}</td>
                            <td class="cRed tR">${rateDTO.ex_sell}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

    </div> <!-- div.contentBox -->

</body>
</html>