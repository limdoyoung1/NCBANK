<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="root" value="${pageContext.request.contextPath}/" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>환율알림 메일 서비스</title>
</head>

<!-- 메일전송에 담기는 jsp에서는 css, style 안먹음 => 인라인으로 해야됨 -->
<link rel="stylesheet" href="${root}css/exchange/sendNoticeMail.css">

<body>
    <!-- 인라인으로 이미지 첨부 - cid:사전에정의한 인라인명 -->
    <div class="top" style="width: 850px; display: flex; margin: 5px 30px 5px;">
        <div class="logo" style="display: flex; margin: 5px 20px 5px;">
        	<!-- 인라인으로 이미지 첨부 - cid:사전에정의한 인라인명 -->
            <img src="cid:NCBankIcon" alt="NCBank" class="logoIcon" style="width: 100px; height: 100px;">&nbsp;
            <p class="logoName" style="margin: 18px 10px 0px; font-size: 50px;">NC 뱅크</p>
        </div>
    </div>

    <div class="contentBox" style="width: 850px; border: 5px solid deepskyblue; border-radius: 0px 50px;">
        <div class="content" style="width: 95%; margin: 0 auto; background-color: white;">
            <h2 class="subject" style="padding-bottom: 20px; border-bottom: 3px solid #c1bdba;">환율알림서비스 안내</h2>
            <p>선택하신 통화가 고객님이 요청하신 환율대에 도달하였습니다.</p>

            <!-- 환율알림서비스 신청 내역 -->
            <div class="tableBox" style="margin-bottom: 50px;">
                <div class="title" style="display: flex;">
                    <span class="cirlcleType01" style="width: 10px; height: 10px; margin: 25px 10px; background-color: #222222; border-radius: 50px;"></span>
                    <h4 class="titleContent">환율알림서비스 신청 내역</h4>
                </div>

                <table class="tType01" cellpadding="00" cellspacing="0" style="width: 100%; border-top: 3px solid #c1bdba; border-bottom: 3px solid #c1bdba;">
                    <tbody>
                        <tr>
                            <th style="width: 20%; padding: 10px 5px; color: #222222; font-size: 13px; background-color: #f5f5f5; border: 1px solid #e8e8e8;">신청 일시</th>
                            <td style="width: 30%; padding: 10px 5px; color: #222222; font-size: 13px; background-color: #ffffff; border: 1px solid #e8e8e8;">${noticeDate}</td>

                            <th style="width: 20%; padding: 10px 5px; color: #222222; font-size: 13px; background-color: #f5f5f5; border: 1px solid #e8e8e8;">통화종류</th>
                            <td style="width: 30%; padding: 10px 5px; color: #222222; font-size: 13px; background-color: #ffffff; border: 1px solid #e8e8e8;">${ExchangeNoticeBean.code_money}</td>
                        </tr>

                        <tr>
                            <th style="width: 20%; padding: 10px 5px; color: #222222; font-size: 13px; background-color: #f5f5f5; border: 1px solid #e8e8e8;">고시환율기준</th>
                            <td style="width: 30%; padding: 10px 5px; color: #222222; font-size: 13px; background-color: #ffffff; border: 1px solid #e8e8e8;">
                                <c:choose>
                                    <c:when test="${1 == ExchangeNoticeBean.notice_rate_type}">
                                        매매기준율
                                    </c:when>
                                    <c:when test="${2 == ExchangeNoticeBean.notice_rate_type}">
                                        송금보내실때
                                    </c:when>
                                    <c:when test="${3 == ExchangeNoticeBean.notice_rate_type}">
                                        송금받으실때
                                    </c:when>
                                    <c:otherwise>
                                        매매기준율
                                    </c:otherwise>
                                </c:choose>
                            </td>

                            <th style="width: 20%; padding: 10px 5px; color: #222222; font-size: 13px; background-color: #f5f5f5; border: 1px solid #e8e8e8;">통지환율대</th>
                            <td style="width: 30%; padding: 10px 5px; color: #222222; font-size: 13px; background-color: #ffffff; border: 1px solid #e8e8e8;">${ExchangeNoticeBean.notice_rate}&nbsp;원&nbsp;이하</td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <!-- 환율알림서비스 통지 결과 -->
            <div class="tableBox" style="margin-bottom: 50px;">
                <div class="title" style="display: flex;">
                    <span class="cirlcleType01" style="width: 10px; height: 10px; margin: 25px 10px; background-color: #222222; border-radius: 50px;"></span>
                    <h4 class="titleContent">환율알림서비스 통지 결과</h4>
                    <p class="subMsg" style="margin-top: 30px; margin-left: auto; color: #5a5a5a;">기준일시 : ${inquiryDate}</p>
                </div>

                <table class="tType02" cellpadding="00" cellspacing="0" style="width: 100%; border-top: 3px solid #c1bdba; border-bottom: 3px solid #c1bdba;">
                    <tbody>
                        <tr>
                            <th rowspan="2" style="width: 12%; padding: 10px 5px; color: #222222; font-size: 12px; background-color: #f5f5f5; border-bottom: 1px solid #e8e8e8;">통화</th>
                            <th rowspan="2" class="sideBorder" style="width: 12%; padding: 10px 5px; color: #222222; font-size: 12px; background-color: #f5f5f5; border-bottom: 1px solid #e8e8e8; border-left: 1px solid #e8e8e8; border-right: 1px solid #e8e8e8;">매매<br>기준율</th>
                            <th colspan="2" class="sideBorder width20" style="width: 20%; padding: 10px 5px; color: #222222; font-size: 12px; background-color: #f5f5f5; border-bottom: 1px solid #e8e8e8; border-left: 1px solid #e8e8e8; border-right: 1px solid #e8e8e8;">전신환</th>
                        </tr>

                        <tr>
                            <th class="sideBorder width10" style="width: 10%; padding: 10px 5px; color: #222222; font-size: 12px; background-color: #f5f5f5; border-bottom: 1px solid #e8e8e8; border-left: 1px solid #e8e8e8; border-right: 1px solid #e8e8e8;">보내실 때</th>
                            <th class="sideBorder width10" style="width: 10%; padding: 10px 5px; color: #222222; font-size: 12px; background-color: #f5f5f5; border-bottom: 1px solid #e8e8e8; border-left: 1px solid #e8e8e8; border-right: 1px solid #e8e8e8;">받으실 때</th>
                        </tr>

                        <tr>
                            <!-- 통화 -->
                            <td style="width: 12%; padding: 10px 5px; color: #222222; font-size: 13px; background-color: #ffffff; border-bottom: 1px solid #e8e8e8;">${ExchangeRateBean.code_money}</td>
                            <!-- 매매기준율 -->
                            <td class="sideBorder" style="width: 12%; padding: 10px 5px; color: #222222; font-size: 13px; background-color: #ffffff; border-bottom: 1px solid #e8e8e8; border-left: 1px solid #e8e8e8; border-right: 1px solid #e8e8e8;">${ExchangeRateBean.ex_standard}</td>
                            <!-- 전신환 | 보내실 때 -->
                            <td class="sideBorder width10" style="width: 12%; padding: 10px 5px; color: #222222; font-size: 13px; background-color: #ffffff; border-bottom: 1px solid #e8e8e8; border-left: 1px solid #e8e8e8; border-right: 1px solid #e8e8e8;">${ExchangeRateBean.ex_sell}</td>
                            <!-- 전신환 | 받으실 때 -->
                            <td class="sideBorder width10" style="width: 12%; padding: 10px 5px; color: #222222; font-size: 13px; background-color: #ffffff; border-bottom: 1px solid #e8e8e8; border-left: 1px solid #e8e8e8; border-right: 1px solid #e8e8e8;">${ExchangeRateBean.ex_buy}</td>
                        </tr>
                    </tbody>
                </table>
            </div> <!-- tableBox -->

            <div class="textListBox" style="margin-bottom: 50px; font-size: 12px;">
                <div class="textList" style="display: flex; background-color: #c1bdba;">
                    <span class="cirlcleType02" style="width: 5px; height: 5px; margin: 18px 10px; background-color: #222222; border-radius: 50px;"></span>
                    <p>실제 외환거래 시에는 거래시점의 고시환율이 적용됩니다.</p>
                </div>
                <div class="textList" style="display: flex; background-color: #c1bdba;">
                    <span class="cirlcleType02" style="width: 5px; height: 5px; margin: 18px 10px; background-color: #222222; border-radius: 50px;"></span>
                    <p>메일 통지 후 환율알림서비스 신청 내용은 자동 해지됩니다. 연장하거나 변경을 원하시면 환율알림서비스 페이지의 [변경]버튼을 클릭하세요.</p>
                </div>
            </div> <!-- div.textListBox -->

        </div> <!-- div.content -->

        <div class="footer" style="color: #222222; font-size: 12px; background-color: #f5f5f5; border-radius: 0px 0px 0px 50px;">
            <div class="footerContent" style="width: 70%; margin-left: 40px; padding-top: 30px;">
                <p>본 메일은 NC은행에서 발송한 발신전용 메일입니다.</p>
                <a href="#">홈페이지 바로가기</a>

                <p>
                    서울특별시 영등포구 국제금융로8길 26(여의도동) (주)NC은행 | 대표번호:1588-9999, 1599-9999<br>
                    사업자등록번호 : 201-81-68693 | 대표자 : 박영도 | 통신판매신고 : 중구 제 00368호<br>
                    Copyright kookmin bank.All rights reserved.
                </p>
                <br>
            </div>
        </div> <!-- div.footer -->
    </div> <!-- div.contentBox -->

</body>
</html>
