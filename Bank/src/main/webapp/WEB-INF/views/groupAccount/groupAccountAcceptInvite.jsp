<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var='root' value="${pageContext.request.contextPath}/" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>모임통장 초대</title>
  <link rel="stylesheet" href="${root}css/acceptinvite.css">
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
  <style>
    body {
      font-family: '맑은 고딕', 'Malgun Gothic', '돋움', Dotum, Helvetica, AppleGothic, Sans-serif;
      background-color: #f8f9fa;
      margin: 0;
      padding: 0;
      display: flex;
      flex-direction: column;
      min-height: 100vh;
    }
  
 
    .btn-container {
      margin-top: 20px;
    }
    .btn-invite {
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
    .acceptBtn, .declineBtn {
      font-weight: 700;
      font-size: 18px;
      color: #FFFFFF;
      border: 0;
      padding: 5px 20px;
      border-radius: 10px;
      margin: 0 10px;
      transition: background-color 0.3s ease;
    }
    .acceptBtn {
      background: rgb(83, 169, 255);
    }
    .acceptBtn:hover {
      background-color: #0056b3;
    }
    .declineBtn {
      background: #555555;
    }
    .declineBtn:hover {
      background-color: #777777;
    }
    footer {
      text-align: center;
      padding: 10px;
      background: #f8f9fa;
      flex-shrink: 0;
    }
    h2, p {
      font-family: '맑은 고딕', 'Malgun Gothic', '돋움', Dotum, Helvetica, AppleGothic, Sans-serif;
    }
    h2 {
      font-size: 24px;
      font-weight: 700;
      color: #333;
      margin-bottom: 20px;
    }
    p {
      font-size: 16px;
      font-weight: 400;
      color: #666;
      margin-bottom: 30px;
    }
  </style>
</head>
<body>
  <div class="container">
    <c:import url="/WEB-INF/views/include/top_menu.jsp" />
    <div class="main">
      <h2>모임통장 초대</h2>
      <p>모임통장에 초대되었습니다. 수락하시겠습니까?</p>
      <form method="post" action="${root}groupAccount/acceptInvite">
        <input type="hidden" name="group_num" value="${group_num}" />
        <div class="btn-container">
          <button type="submit" name="action" value="accept" class="acceptBtn">수락</button>
          <button type="submit" name="action" value="decline" class="declineBtn">거절</button>
        </div>
      </form>
    </div>
    <div class="blank"></div>
  <footer>
    <c:import url="/WEB-INF/views/include/bottom_info.jsp" />
  </footer>
  </div>
</body>
</html>
