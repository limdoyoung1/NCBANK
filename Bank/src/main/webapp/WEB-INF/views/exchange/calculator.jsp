<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="root" value="${pageContext.request.contextPath}/" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>환율 계산기</title>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script type="text/javascript">

$(document).ready(function() { // 문서가 완전히 로드된 후 이벤트 핸들러 설정
	
	// 서버에서 전달받은 환율데이터를 js 객체로 변환
	// 객체 배열 standard, but 등 배열 속 요소는 객체의 필드
	const exchangeRates = {
		standard: {},
		buy: {},
		sell: {}
	};
	<c:forEach var="obj" items="${ExchangeRateList}">
		exchangeRates.standard["${obj.code_money}"] = ${obj.ex_standard};
		exchangeRates.buy["${obj.code_money}"] = ${obj.ex_buy};
		exchangeRates.sell["${obj.code_money}"] = ${obj.ex_sell};
	</c:forEach>
	
	let currentRates = exchangeRates.standard;
	
	$('.selbox').change(function() {
		console.log("selbox.change()");
		// [1 : 매매 기준율] [2 : 솧금 보낼때] [3 : 송금받을때]
		var standard = parseInt($('.selbox').val());
		console.log("standard : " + standard);
		
		switch (standard) {
		case 1:
			currentRates = exchangeRates.standard;
			break;
			
		case 2:
			currentRates = exchangeRates.buy;
			break;
			
		case 3:
			currentRates = exchangeRates.sell;
			break;

		default:
			currentRates = exchangeRates.standard;
			break;
		}
		
		exchange('.currencyAmount', '.amount', 1);		
	}); // $('.selbox')
	
	
	// 원화 -> 통화
	$('.amount').on('input', function() {
		console.log("amount.on()");
		exchange('.amount', '.currencyAmount', 0);
	}); // $('.amount')
	
	// 통화 -> 원화
	$('.currencyAmount').on('input', function() {
		console.log("amount.currencyAmount()");
		exchange('.currencyAmount', '.amount', 1);
	}); // $('.currencyAmount')
	
	$('.ISOSelBox').change(function() {
		console.log("ISOSelBox.change()");
		exchange('.currencyAmount', '.amount', 1);
	}); // $('.ISOCodeList')
	
	// option : 0 : 원화 -> 통화 | 1 통화 -> 원화
	function exchange(input, output, option) {
		console.log("exchange : " + input + " -> " + output);
		
		// 입력된 값 가져오기
		var inputAmount = $(input).val();
		if ('' === inputAmount || isNaN(inputAmount)) { // 입력오류
			console.log("inputAmount 입력오류");
			return;
		}
		console.log("inputAmount : " + inputAmount);
		
		// select에서 선택한 옵션의 value -> 통화 코드
		var ISOCode = $('.ISOSelBox').val();
		if (null === ISOCode || '' === ISOCode) {
			alert('ISOCode error');
			return null;
		}
		console.log("ISOCode : " + ISOCode);
		
		var rate = currentRates[ISOCode];
		if (!rate) {
			alert("해당 통화에 대한 환율정보를 가져올수 없습니다.");
			return null;
		}
		
		var correctionRate = 1;
		// 100 단위로 매매 기준율이 제공되는 통화들
		switch (ISOCode) {
		case "JPY":
		case "IDR":
			correctionRate = 100;
			break;

		default:
			correctionRate = 1;
			break;
		}
		console.log("correctionRate : " + correctionRate);
		
		var convertedAmount;
		
		if (0 == option) { // 원화 -> 통화
			rate /= correctionRate;
			convertedAmount = (inputAmount / rate).toFixed(2);
			console.log("convertedAmount : " + convertedAmount);
		} else if (1 == option) { // 통화 -> 원화
			rate /= correctionRate;
			convertedAmount = (inputAmount * rate).toFixed(2);
			console.log("convertedAmount : " + convertedAmount);
		}
			
		$(output).val(convertedAmount);
	} // exchange
	
	/* ====== ====== */
	
}); // $(document).ready

</script>

</head>

<link rel="stylesheet" href="${root}css/exchange/calculator.css">

<body>
    <!-- contentBox 안에 top을 넣는다 or contentBox 와 top을 담을 div를 하나 더 만든다 -->
    <div class="contentBox">

        <div class="top">
            <h1 class="subject">환율계산기</h1>
            <div class="logo">
                <img src="${root}img/NCBankIcon_2.png" alt="NCBank" class="iconStyle01">
                <p class="logoName">NC뱅크</p>
            </div>
        </div>

        <div class="content">
            <select class="selbox sbStyle01" title="환율계산기준 선택">
                <!-- 230510 접근성반영(title추가) -->
                <option value="1">매매기준율</option>
                <option value="2">송금보내실때</option>
                <option value="3">송금받으실때</option>
            </select>


            <!-- label + select에 name, id 뺏음 -->
            <div class="selectArea">

                <select class="ISOSelBox sbStyle02">
                    <c:forEach var="obj" items="${ExchangeRateList}">
                        <c:if test="${'KRW' != obj.code_money.toUpperCase().trim()}">
                            <option class="op_code_money" value="${obj.code_money}">
                                ${obj.code_money}&nbsp;&nbsp;(${obj.code_money_name})</option>
                        </c:if>
                    </c:forEach>
                </select>

                <input type="number" class="currencyAmount inpStyle01" />
            </div> <!-- div.selectArea -->

            <div class="iconArea">
                <img src="${root}img/equal02.png" alt="equalIcon" class="iconStyle02">
            </div>

            <div class="selectArea">
                <input type="text" id="amount" class="sbStyle03"
                    value="${beanKRW.code_money} (${beanKRW.code_money_name})" readonly>
                <input type="number" class="amount inpStyle01" />
            </div>

            <div class="textArea">
                <p class="textType01">(${inquiryDate} 기준)</p>

                <ul class="listType01">
                    <li>
                        환율계산기는 단순 참고용으로 위 계산결과는 환율변동 또는 우대율 적용에 따라 실제 거래 시 적용되는 환율과
                        다를 수 있습니다.
                    </li>
                    <li>
                        현재 고시된 환율은 미달러 기준 1만 달러 상당액 미만 시 적용되는 환율입니다.
                        <br>(단, USD, EUR, JPY, GBP, CAD, NZD, AUD, CNY, CHF, HKD, SGD, AED, THB를 제외한
                        기타 통화는 미달러 기준 5만 달러 상당액 미만)
                    </li>
                </ul>
            </div>
            <div class="btnArea">
                <button type="button" class="btnStyle01" onclick="window.close();">닫기</button>
            </div>
        </div> <!-- div.content -->

    </div> <!-- div.contentBox -->

</body>
</html>