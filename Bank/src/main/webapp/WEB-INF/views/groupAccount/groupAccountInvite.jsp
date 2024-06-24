<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var='root' value="${pageContext.request.contextPath}/" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>complete</title>
  <link rel="stylesheet" href="${root}css/createnext.css">
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
            url: '${root}groupAccount/getGroupInfo',
            method: 'POST',
            data: { group_num: '${groupInfo.group_num}' },
            success: function(response) {
                console.log('Group info:', response); // 디버깅 로그 추가
                if (response && response.group_num) {
                    const groupNum = response.group_num;
                    Kakao.Link.sendCustom({
                        templateId: 109036, // 템플릿 ID
                        templateArgs: {
                            'group_num': groupNum // 동적 group_num 전달
                        }
                    });
                } else {
                    console.error('Unexpected response structure:', response);
                    alert('그룹 정보를 가져오는데 실패했습니다. 응답 데이터가 올바르지 않습니다.');
                }
            },
            error: function(error) {
                console.error('Error:', error);
                alert('그룹 정보를 가져오는데 실패했습니다.');
            }
        });
    }

    function formatAutoType(autoType) {
        return autoType == 0 ? '주간' : '월간';
    }

    function formatAutoNextDate(autoType, autoNextDate) {
        return autoType == 0 ? autoNextDate + '요일' : autoNextDate + '일';
    }

    function formatBalance(balance) {
        return balance + '원';
    }

    window.onload = function() {
        var autoTypeElement = document.getElementById("auto_type_value");
        var autoNextDateElement = document.getElementById("auto_next_date_value");
        var autoBalanceElement = document.getElementById("auto_balance_value");

        autoTypeElement.innerText = formatAutoType(autoTypeElement.innerText);
        autoNextDateElement.innerText = formatAutoNextDate(autoTypeElement.innerText, autoNextDateElement.innerText);
        autoBalanceElement.innerText = formatBalance(autoBalanceElement.innerText);
    }
  </script>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f8f9fa;
      margin: 0;
      padding: 0;
    }
    .main {
      max-width: 600px;
      margin: auto;
      padding: 20px;
      text-align: center;
      background-color: #ffffff;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
      border-radius: 8px;
      margin-top: 50px;
    }
    .info {
      text-align: left;
      margin-top: 20px;
    }
    .info span {
      display: block;
      margin: 5px 0;
    }
    .btn-invite {
      margin-top: 20px;
      background-color: #ffeb00;
      color: #3c1e1e;
      border: none;
      padding: 10px 20px;
      font-size: 16px;
      border-radius: 5px;
      cursor: pointer;
    }
    .btn-invite:hover {
      background-color: #ffd700;
    }
  </style>
</head>
<body>
  <div class="main">
    <c:import url="/WEB-INF/views/include/top_menu.jsp" />
    <h2>모임통장 신청 완료</h2>
    <p>이제 모임통장에 친구를 초대해보세요</p>

    <div class="info">
      <%-- <span>모임장 연결계좌: [NC뱅크]${accounts}</span> --%>
      <span>모임통장 계좌번호: [NC뱅크]${accounts}</span>
      <span>회비 정보: <span id="auto_type_value">${accountInfo.auto_type}</span> / <span id="auto_next_date_value">${accountInfo.auto_next_date}</span> / <span id="auto_balance_value">${accountInfo.auto_balance}</span></span>
      <span>모임통장 신청일: ${applicationDate}</span>
    </div>

    <button class="btn btn-primary btn-invite" onclick="sendKakaoLink()">카카오톡으로 모임원 초대하기</button>
    <c:import url="/WEB-INF/views/include/bottom_info.jsp" />
  </div>
</body>
</html>
