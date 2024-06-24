<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="root" value="${pageContext.request.contextPath}/" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>NC 은행</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/boardmain.css" />
<!-- Bootstrap CDN -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<c:import url="/WEB-INF/views/include/top_menu.jsp" />

		<div class="board">
			<h4 class="card-title">${boardInfoName}</h4>
			<table class="table table-hover" id='board_list'>
				<thead>
					<tr>
						<th class="text-center d-none d-md-table-cell">NO</th>
						<th class="w-50">제목</th>
						<th class="text-center d-none d-md-table-cell">작성날짜</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var='obj' items='${contentList}'>
						<tr>
							<td class="text-center d-none d-md-table-cell">${obj.content_idx}</td>
							<td><a
								href="${root}board/read?board_info_idx=${board_info_idx}&content_idx=${obj.content_idx}&page=${page}">${obj.content_subject}</a></td>
							<td class="text-center d-none d-md-table-cell">${obj.content_date}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="d-block d-md-block">
				<ul class="pagination justify-content-center">
					<c:choose>
						<c:when test="${pageBean.prevPage <= 0}">
							<li class="page-item disabled"><a href="#" class="page-link"><</a></li>
						</c:when>
						<c:otherwise>
							<li class="page-item"><a
								href="${root}board/main?board_info_idx=${board_info_idx}&page=${pageBean.prevPage}"
								class="page-link">이전</a></li>
						</c:otherwise>
					</c:choose>

					<c:forEach var="idx" begin="${pageBean.min}" end="${pageBean.max}">
						<c:choose>
							<c:when test="${idx == pageBean.currentPage }">
								<!--  현재페이지 색변경(active) -->
								<li class="page-item active"><a
									href="${root}board/main?board_info_idx=${board_info_idx}&page=${idx}"
									class="page-link">${idx}</a></li>
							</c:when>
							<c:otherwise>
								<li class="page-item"><a
									href="${root}board/main?board_info_idx=${board_info_idx}&page=${idx}"
									class="page-link">${idx}</a></li>
							</c:otherwise>
						</c:choose>

					</c:forEach>
					<!-- Max값이 전체페이지보다 크거나 같으면 비활성화 disabled(부트스트랩) -->
					<c:choose>
						<c:when test="${pageBean.max >= pageBean.pageCnt}">
							<li class="page-item disabled"><a href="#" class="page-link">></a></li>
						</c:when>
						<c:otherwise>
							<li class="page-item"><a
								href="${root}board/main?board_info_idx=${board_info_idx}&page=${pageBean.nextPage}"
								class="page-link">></a></li>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>

			<div class="text-right">
				<c:if test="${loginUserId=='soldesk'}">
					<a href="${root}board/write?board_info_idx=${board_info_idx}"
						class="btn btn-primary">글쓰기</a>
				</c:if>
			</div>
		</div>

		<c:import url="/WEB-INF/views/include/bottom_info.jsp" />
	</div>
</body>