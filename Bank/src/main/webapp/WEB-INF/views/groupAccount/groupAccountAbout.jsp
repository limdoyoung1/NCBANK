<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="root" value="${pageContext.request.contextPath}/" />

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>모임통장 소개</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="${root}css/about.css">
    <style>
      footer {
    margin-top: auto; /* 자동으로 아래로 밀리게 설정 */
    width: 100%; 
    background-color: #f1f1f1;
    text-align: center;
    padding: 40px;
    margin: -20px;
}
    </style>
    
</head>

<body>
    <div class="container">
        <c:import url="../include/top_menu.jsp" />
        <div class="traveltitle">
            모임통장 소개
            <hr />
        </div>
        <div class="video-container">
            <iframe width="720" height="405" src="https://www.youtube.com/embed/CoN_b0Sa80w" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
            <p class="video-source">출처: <a href="https://www.kakaobank.com" target="_blank">카카오뱅크</a></p>
        </div>
        <div class="contents">
            <div class="main-content">
                <div class="contents-1">
                    <h2>모임통장이란?</h2>
                    <p>모임통장은 친구, 가족, 동호회 등 여러 사람이 함께 사용하는 통장으로, 공동의 목적을 위해 자금을 모으고 관리할 수 있는 서비스입니다.</p>
                    <h3>주요 기능</h3>
                    <ul>
                        <li><strong>회비 관리:</strong> 모임 회비를 자동이체로 손쉽게 모으고, 관리할 수 있습니다.</li>
                        <li><strong>실시간 거래 내역 확인:</strong> 모임원 모두가 거래 내역을 실시간으로 확인할 수 있습니다.</li>
                        <li><strong>멤버 관리:</strong> 모임원을 쉽게 초대하고 관리할 수 있습니다.</li>
                        <li><strong>간편 송금:</strong> 모임원 간의 송금을 간편하게 처리할 수 있습니다.</li>
                    </ul>
                    <h3>이용 방법</h3>
                    <ol>
                        <li>계좌를 개설합니다..</li>
                        <li>모임통장 서비스를 신청합니다.</li>
                        <li>모임원을 초대합니다.</li>
                        <li>모임 회비를 모으고, 관리합니다.</li>
                        <li>필요 시 모임원 간 송금을 진행합니다.</li>
                    </ol>
                    <h3>유의 사항</h3>
                    <p>모임통장은 모임주 개인 명의의 통장으로, 모임주의 상태에 따라 서비스 이용이 제한될 수 있습니다. 서비스 이용 정지 사유를 해소한 후 다시 이용할 수 있습니다.</p>
                </div>
                <div class="btnArea">
                    <button class="btn-action" onclick="location.href='${root}groupAccount/create'">모임통장 개설하러 가기</button>
                </div>
            </div>
        </div>
        
        <footer>
        <c:import url="../include/bottom_info.jsp" />
        </footer>
    </div>
</body>

</html>
