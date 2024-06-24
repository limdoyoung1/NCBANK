<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="root" value="${pageContext.request.contextPath}/" />
<div class="account-info">
    <h3>모임통장 정보</h3>
    <table class="table">
        <tr>
            <th>자동이체 잔액</th>
            <th>자동이체 유형</th>
            <th>자동이체 날짜</th>
            <th>자동이체 종료일</th>
        </tr>
        <c:forEach var="auto" items="${autoList}">
            <tr>
                <td>${auto.auto_balance}</td>
                <td>${auto.auto_type}</td>
                <td>${auto.auto_next_date}</td>
                <td>${auto.auto_end}</td>
            </tr>
        </c:forEach>
    </table>

    <h3>모임원 정보</h3>
    <table class="table">
        <tr>
            <th>이름</th>
            <th>전화번호</th>
            <th>모임장 여부</th>
            <th>가입 날짜</th>
        </tr>
        <c:forEach var="member" items="${memberList}">
            <tr>
                <td>${member.name}</td>
                <td>${member.phone}</td>
                <td><c:if test="${member.group_leader == '1'}">모임장</c:if><c:if test="${member.group_leader == '0'}">모임원</c:if></td>
                <td>${member.group_joindate}</td>
            </tr>
        </c:forEach>
    </table>
</div>
