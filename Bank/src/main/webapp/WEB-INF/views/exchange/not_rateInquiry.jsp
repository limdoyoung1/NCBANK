<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<c:set var='root' value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>exchange/not_rateInquiry</title>
</head>
<body>
	<script>
		alert('입력하신 조회일자는 영업일이 아닙니다.')
		location.href = '${root}/exchange/rateInquiry'
	</script>
</body>
</html>