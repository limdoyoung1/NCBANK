<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var='root' value="${pageContext.request.contextPath}/" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>연결중...</title>
  <script>
    // JavaScript로 alert 창을 띄우고 로그인 페이지로 리다이렉트
    window.onload = function() {
      alert('${notLoginMessage}');
      window.location.href = '${root}user/login';
    }
  </script>
</head>
<body>
</body>
</html>
