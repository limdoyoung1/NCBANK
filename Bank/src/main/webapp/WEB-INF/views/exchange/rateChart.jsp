<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="root" value="${pageContext.request.contextPath}/" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>환율차트</title>

<!-- 통계 그래프/차트 를 만들기 위한 라이브러리 -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>

<link rel="stylesheet" href="${root}css/exchange/rateChart.css">

<body>
    
    <div class="contentBox">
    
		<div class="top">
			<h1 class="subject">환율차트</h1>
			<div class="logo">
				<img src="${root}img/NCBankIcon_2.png" alt="NCBank"
					class="iconStyle01">
				<p class="logoName">NC뱅크</p>
			</div>
		</div>
		
		<div class="content">

            <div class="selectArea">
                <!-- 1: 매매기준, 2: 송금할때, 3: 송금받을때  -->
                <select class="sbStyle01" id="FXRate" name="period">
                    <option value="1">매매 기준율</option>
                    <option value="2">송금할 때</option>
                    <option value="3">송금받을 때</option>
                </select>
            </div>

            <div class="selectArea">

                <select class="sbStyle02" id="currency" name="currency">
                    <c:forEach var="obj" items="${codeMoneyList}">
                        <c:if test="${'KRW' != fn:trim(obj.code_money).toUpperCase()}">
                            <option class="op_code_money" value="${obj.code_money}">
                                ${obj.code_money}&nbsp;&nbsp;(${obj.code_money_name})</option>
                        </c:if>
                    </c:forEach>
                </select>

                <select class="sbStyle03" id="period" name="period">
                    <option value="1week">1주</option>
                    <option value="1month">1개월</option>
                    <option value="3month">3개월</option>
                    <option value="6month">6개월</option>
                </select>
            </div> <!-- div.selectArea -->

            <div class="textArea">
                <p class="textType01">(${finalInquiryDate} 기준)</p>
            </div>

            <canvas id="exchangeRateChart" width="400" height="200"></canvas>

            <div class="btnArea">
                <button type="button" class="btnStyle01" onclick="window.close();">닫기</button>
            </div>
        </div> <!-- div.content -->

    </div> <!-- div.contentBox -->

<script>
$(document).ready(function() {
    // 데이터 초기화
    const exchangeRates = {};

    // JSP 변수를 JSON 배열 형식으로 변환
    const exchangeRateList = [
        <c:forEach var="obj" items="${ExchangeRateList}">
            {
				// 속성들에 값을 채운다
                codeDate: "${obj.code_date_str}",
                codeMoney: "${obj.code_money}",
                exStandard: ${obj.ex_standard},
                exBuy: ${obj.ex_buy},
                exSell: ${obj.ex_sell}
            }<c:if test="${!fn:endsWith(fn:trim(obj), ']}')}">,</c:if>
        </c:forEach>
    ];
    console.log(exchangeRateList);
    
    // exchangeRateList 배열의 각 객체를 순회하면서 exchangeRates에 객체 데이터 삽입
    exchangeRateList.forEach(obj => {
        const codeDate = obj.codeDate;
        const codeMoney = obj.codeMoney;
        
        // 날짜가 존재하지 않으면 초기화
        if (!exchangeRates[codeDate]) {
            exchangeRates[codeDate] = { standard: {}, buy: {}, sell: {} };
        }
        
        // codeDate 로 키값을 가지며, 각 날짜별로 standard, buy, sell 의 객체를 가진다
        // 하위 객체의 codeMoney 키값을 가지는 곳에 데이터 삽입
        exchangeRates[codeDate].standard[codeMoney] = obj.exStandard;
        exchangeRates[codeDate].buy[codeMoney] = obj.exBuy;
        exchangeRates[codeDate].sell[codeMoney] = obj.exSell;
        /*
        exchangeRates [codeDate]
        ㄴ standard [codeMoney] = 데이터
        ㄴ buy [codeMoney] = 데이터
        ㄴ sell [codeMoney] = 데이터
        */
    });
    console.log(exchangeRates);
    
    // 차트 객체 저장 변수
    let exchangeRateChart;
    
    function renderChart(codeMoney, FXRate, period) {
        const ctx = document.getElementById('exchangeRateChart').getContext('2d');
        const dates = Object.keys(exchangeRates).sort();
        
        const filteredDates = []; // 필더링된 날짜
     	// 만약 90일의 데이터가 들어와있다 -> chart.js에서 축의 레이블을 자동으로 조정해서 표시해줌 -> option - scaies로 조절 가능
        
        const filteredRates = { // 날짜를 기준으로 필터링된 데이터
        	standard: [], buy: [], sell: []
        }
        
        // 오늘 날짜
        const today = new Date();
        // 각 기간에대한 날짜 계산
        const periodMap = {
           '1week': 7, '1month': 30, '3month': 90, '6month': 180
        };
        
        console.log("period : " + period);
        const datsToInclude = periodMap[period];
        
     	// 날짜별 데이터 필터링
        dates.forEach(codeDate => {
            const date = new Date(codeDate); // String -> Date
            const diffTime = Math.abs(today - date); // 환율의 날짜와 오늘날짜 차이를 밀리초 단위로 계산
            // 24 시간 60분 60초 1000밀리초 | Math.ceil : 소수점을 올림해 일수를 반올림
            const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24)); // 밀리초 단위를 일 단위로 변환
            // 선택 기간 이내의 날짜만 필터링
            // ex) 3개월 -> datsToInclude : 90, 오늘과의 환율날 차이가 90일 이하인 데이터
            if (diffDays <= datsToInclude) {
                filteredDates.push(codeDate);
                filteredRates.standard.push(exchangeRates[codeDate].standard[codeMoney]);
                filteredRates.buy.push(exchangeRates[codeDate].buy[codeMoney]);
                filteredRates.sell.push(exchangeRates[codeDate].sell[codeMoney]);
            }
        });
        
        console.log("codeMoney : " + codeMoney);
        
        
        console.log("FXRate : " + FXRate);
        let datasets = [];
       	// 선택한 기준 환율에 대한 데이터 구분
        if ('1' === FXRate) {
        	let lab = codeMoney + " Standard Rate (" + period + ")";
            datasets.push({
            	label: lab,
                data: filteredRates.standard,
                backgroundColor: 'rgba(75, 192, 192, 0.2)',
                borderColor: 'rgba(75, 192, 192, 1)',
                borderWidth: 1,
                fill: false
            });
        } else if ('2' === FXRate) {
        	let lab = codeMoney + " Buy Rate (" + period + ")";
            datasets.push({
                label: lab,
                data: filteredRates.buy,
                backgroundColor: 'rgba(192, 75, 75, 0.2)',
                borderColor: 'rgba(192, 75, 75, 1)',
                borderWidth: 1,
                fill: false
            });
        } else if ('3' === FXRate) {
        	let lab = codeMoney + " Sell Rate (" + period + ")";
            datasets.push({
                label: lab,
                data: filteredRates.sell,
                backgroundColor: 'rgba(75, 75, 192, 0.2)',
                borderColor: 'rgba(75, 75, 192, 1)',
                borderWidth: 1,
                fill: false
            });
        }
        
        // exchangeRateChart 가 존재하면 삭제
        if (exchangeRateChart) {
            exchangeRateChart.destroy();
        }

        exchangeRateChart = new Chart(ctx, {
			// 차트 종류
            type: 'line',
            // 차트 데이터
            data: {
				// 축 제목
                labels: filteredDates,
                // 각 축에 들어갈 데이터
                datasets: datasets
            }, // data
            // 
            options: {
                scales: {
                	x: {
						ticks: {
							// autoSkip : 레이블이 너무 많을 경우 자동으로 레이블을 스킵, Chart.js 가 겹치지 않도록 자동으로 조정
							autoSkip: true, 
							// maxTicksLimit : 한번에 표시할 최대 레이블 수 제한
							maxTicksLimit: 20 
						}
					},
                    y: {
						// y 축 시작을 0 으로 설정
                        beginAtZero: false
                    }
                }
            }
           
        }); // exchangeRateChart
        
    } // renderChart

    // 통화 변경시
    $('#currency').change(function() {
    	const selectedCodeMoney = $('#currency').val();
    	const selectedRate = $('#FXRate').val();
        const selectedPeriod = $('#period').val();
        renderChart(selectedCodeMoney, selectedRate, selectedPeriod);
    });
    $('#FXRate').change(function() {
    	const selectedCodeMoney = $('#currency').val();
    	const selectedRate = $('#FXRate').val();
        const selectedPeriod = $('#period').val();
        renderChart(selectedCodeMoney, selectedRate, selectedPeriod);
    });
    $('#period').change(function() {
    	const selectedCodeMoney = $('#currency').val();
    	const selectedRate = $('#FXRate').val();
        const selectedPeriod = $('#period').val();
        renderChart(selectedCodeMoney, selectedRate, selectedPeriod);
    });

    // 초기 그래프 렌더링
    renderChart($('#currency').val(), $('#FXRate').val(), $('#period').val());
    
});

</script>

</body>
</html>
