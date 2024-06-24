<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var='root' value="${pageContext.request.contextPath}/" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>모임 해체 완료</title>
<script type="text/javascript">
    function showAlertAndRedirect(message) {
        alert(message);
        window.location.href = "${root}main";
    }
</script>
</head>
<body onload="showAlertAndRedirect('${message}')">
</body>
</html>
