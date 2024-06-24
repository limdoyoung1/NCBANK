<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="root" value="${pageContext.request.contextPath}/" />
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>환율알림 신청/변경 완료</title>
</head>

<link rel="stylesheet" href="${root}css/exchange/noticeEditSuccess.css">

<body>

    <div class="contentBox">

        <div class="contentArea">
            <img src="${root}img/doneIcon.png" alt="NCBank" class="iconStyle01">

            <h2 class="msg">환율알림서비스가 정상적으로 신청/변경 되었습니다.</h2>
        </div>

        <div class="btnArea">
            <a class="aBtnStyle01" href="${root}exchange/noticeEdit">확인</a>
        </div>

    </div>
	
</body>
</html>