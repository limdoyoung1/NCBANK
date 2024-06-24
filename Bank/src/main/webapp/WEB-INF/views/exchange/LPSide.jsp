<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="root" value="${pageContext.request.contextPath}/" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>LPSide</title>
</head>
<link rel="stylesheet" href="${root}css/exchange/LPSide.css">

<body>
	<div class="LPWrap">
        <div class="LPC01"> <!-- menu1 -->
            <h5 class="subject">외환</h5> <!-- menu1-1 -->
            <ul>
                <li>
                    <a href="${root}exchange/rateInquiry">환율 조회</a>
                </li>

                <li>
                    <a href="${root}exchange/notice">환율 알림<br />서비스</a>
                </li>

                <li>
                    <a href="${root}exchange/exchangeAsk">환전 신청</a>
                </li>

                <li>
                    <a href="${root}exchange/exchangeHistory">환전 내역<br />조회</a>
                </li>

                <hr class="contour"/>
                <li class="rateCalculator">환율 계산기</li>
                <!-- 편의성으로 잠깐 가져다 놓음거임 -->
                <li class="rateChartBtn">환율 차트</li>
            </ul>

        </div>
    </div>

	<script type="text/javascript">
		$(document).ready(function() { // 문서가 완전히 로드된 후 이벤트 핸들러 설정
			$('.rateCalculator').on("click", function(e) {
				e.preventDefault();

				var url = "${root}/exchange/calculator"
				var name = "환율 계산기"
				var width = 500;
	             var height = 700;
	             var left = (window.screen.width / 2) - (width / 2);
	             var top = (window.screen.height / 2) - (height / 2);
	             var option = "width=" + width + ", height=" + height + ", top=" + top + ", left=" + left
	             	+ ", munubar=no, scrollbars=yes";
				window.open(url, name, option);
			}); // .rateCalculator
			
			// 편의성으로 잠깐 가져다 놓음
			$('.rateChartBtn').on("click", function (e) {
                e.preventDefault();

                var url = "${root}/exchange/rateChart"
                var name = "환율 차트"
                var width = 500;
                var height = 700;
                var left = (window.screen.width / 2) - (width / 2);
                var top = (window.screen.height / 2) - (height / 2);
                var option = "width=" + width + ", height=" + height + ", top=" + top + ", left=" + left
                	+ ", munubar=no, scrollbars=no";
                window.open(url, name, option);
            }); // .rateChartBtn
			
         }); // $(document).ready
     </script>

</body>
</html>