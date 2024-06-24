<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}/" />
<script>
	alert('회원님의 아이디는 '+"${findMemberIDBean.id}"+'입니다.')
	location.href="${root}user/login"
</script>