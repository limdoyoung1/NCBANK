<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}/" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>NC BANK</title>
<!-- Bootstrap CSS CDN -->

<!-- <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" rel="stylesheet">
 -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/readingboard.css" />
<!-- jQuery, Popper.js, and Bootstrap JS CDN -->
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
		<div class="board p-4 bg-light shadow-sm rounded">
			<div
				class="header d-flex justify-content-between align-items-center mb-4">
				<div class="title">
					<h2>${readContentBean.content_subject}</h2>
					<hr />
				</div>
				<div class="date">
					<p>${readContentBean.content_date}</p>
				</div>
			</div>
			<div class="content-group mb-4">
				<label class="font-weight-bold"></label>
				<textarea id="board_content" name="board_content"
					class="form-control" disabled="disabled">${readContentBean.content_text}</textarea>
			</div>
			<div class="form-group text-right">
				<a
					href="${root}board/main?board_info_idx=${board_info_idx}&page=${page}"
					class="btn btn-primary">목록보기</a>
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/bottom_info.jsp" />
	</div>
</body>
<script>
	function autoResizeTextarea(textarea) {
		textarea.style.height = 'auto';
		textarea.style.height = textarea.scrollHeight + 'px';
	}

	document.addEventListener('DOMContentLoaded', function() {
		var textarea = document.getElementById('board_content');
		autoResizeTextarea(textarea);

		textarea.addEventListener('input', function() {
			autoResizeTextarea(textarea);
		});
	});
</script>
</html>