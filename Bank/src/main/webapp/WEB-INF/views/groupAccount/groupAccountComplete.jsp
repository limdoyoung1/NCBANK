<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var='root' value="${pageContext.request.contextPath}/" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>모임통장 신청 완료</title>
<%--  <link rel="stylesheet" href="${root}css/createnext.css"> --%>
<link rel="stylesheet" href="${root}css/complete.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
<script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>

<script>
	// 카카오 초기화
	Kakao.init('ff8ba07dd1c6c1c318c25c022ce8bb5e'); // 앱 키

	function sendKakaoLink() {
		$.ajax({
			url : '${root}groupAccount/getGroupInfo',
			method : 'POST',
			data : {
				group_num : '${groupInfo.group_num}'
			},
			success : function(response) {
				console.log('Group info:', response); // 디버깅 로그 추가
				if (response && response.group_num) {
					const groupNum = response.group_num;
					Kakao.Link.sendCustom({
						templateId : 109036, // 템플릿 ID
						templateArgs : {
							'group_num' : groupNum
						// 동적 group_num 전달
						}
					});
				} else {
					console.error('Unexpected response structure:', response);
					alert('그룹 정보를 가져오는데 실패했습니다. 응답 데이터가 올바르지 않습니다.');
				}
			},
			error : function(error) {
				console.error('Error:', error);
				alert('그룹 정보를 가져오는데 실패했습니다.');
			}
		});
	}

	function formatAutoType(autoType) {
		return autoType == 0 ? '주간' : '월간';
	}

	function formatAutoNextDate(autoType, autoNextDate) {
		return autoType == '주간' ? autoNextDate + '요일' : autoNextDate + '일';
	}

	function formatMoney(money) {
		return money + '원';
	}

	window.onload = function() {
		var autoTypeElement = document.getElementById("auto_type_value");
		var autoNextDateElement = document.getElementById("auto_next_date_value");
		var autoMoneyElement = document.getElementById("auto_money");

		var autoTypeValue = autoTypeElement.innerText.trim();
		var autoNextDateValue = autoNextDateElement.innerText.trim();
		var autoMoneyValue = autoMoneyElement.innerText.trim();

		console.log('Auto Type:', autoTypeValue);
		console.log('Auto Next Date:', autoNextDateValue);
		console.log('Auto Money:', autoMoneyValue);

		var autoTypeFormatted = formatAutoType(autoTypeValue);
		var autoNextDateFormatted = formatAutoNextDate(autoTypeFormatted, autoNextDateValue);
		var autoMoneyFormatted = formatMoney(autoMoneyValue);

		autoTypeElement.innerText = autoTypeFormatted;
		autoNextDateElement.innerText = autoNextDateFormatted;
		autoMoneyElement.innerText = autoMoneyFormatted;
	}
</script>
</head>
<body>
	<div class="container">
		<c:import url="/WEB-INF/views/include/top_menu.jsp" />
		<div class="traveltitle">
			모임통장 개설 완료!
			<hr />
		</div>
		<div class="contents">
			<div class="menu1">
				<a href="group">모임약관동의</a> <a href="groupInvite">모임통장 초대</a>
				<div class="menu1-1">모임통장 개설</div>
				<div class="menuhr">
					<hr />
				</div>
			</div>
			<div class="contents-1">
				<div class="section-1">
					<div class="contentsText"></div>
					<div class="stepper">
						<div class="line"></div>
						<div class="step">
							<div class="circle">1</div>
						</div>
						<div class="step">
							<div class="circle">2</div>
						</div>
						<div class="step">
							<div class="circle active">3</div>
						</div>
					</div>
				</div>

				<div class="group71">
					<div class="flexClass">
						<span class="idbox"> <span class="groupname">${groupname}모임</span>
						</span>
					</div>
					<br />
					<div class="flexClass">
						<span class="idbox">연결 계좌번호</span> <span class="rec6" id="group_account">[NC뱅크]${accounts}</span>
					</div>
					<div class="flexClass">
						<span class="idbox">자동이체 주기</span> <span class="rec6" id="auto_type_value">${accountInfo.auto_type}</span>
					</div>
					<div class="flexClass">
						<span class="idbox">자동이체 납부일</span> <span class="rec6" id="auto_next_date_value">${accountInfo.auto_next_date}</span>
					</div>

					<div class="flexClass">
						<span class="idbox">회비</span> <span class="rec6" id="auto_money">${accountInfo.auto_money}</span>
					</div>
					<br />
					<div class="hanaClass">이제 모임통장에 친구를 초대해보세요!</div>
					<p></p>
					<p></p>

					<button class="applyBtn" onclick="sendKakaoLink()">카카오톡으로 모임원 초대하기</button>
					<p></p>

					<button class="homeBtn" onclick="location.href='${root}main'">메인으로 이동</button>
				</div>
			</div>
		</div>
		<footer>
		<c:import url="/WEB-INF/views/include/bottom_info.jsp" />
		</footer>
	</div>
</body>
</html>
