<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}/"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>noticeInputError.jsp</title>
</head>
<body>
	<script>
		alert('허용되지 않은 입력형식입니다.')
		location.href = '${root}exchange/noticContentIndex?${noticContentIndex}';
</script>
</body>
</html>