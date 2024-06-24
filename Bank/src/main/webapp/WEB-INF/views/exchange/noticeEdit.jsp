<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="root" value="${pageContext.request.contextPath}/" />
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>환율알림변경</title>
</head>

<link rel="stylesheet" href="${root}css/exchange/noticeEdit.css">

<body>
	
	<div class="contentBox">

		<!-- if 환율 알림 등록시. 보여지는 안내문 -->
		<div class="textArea">
			<ul class="listType01">
				<li class="listStyleNone"><b>아래와 같이 환율알림서비스를 신청하신 정보가 있습니다. 변경 또는 해지 가능합니다.</b></li>
				<li>선택하신 통화의 환율이 지정하신 알림희망 환율범위에 도달하는 경우 이메일을 통해 안내 드립니다.</li>
				<li>3개월 내 지정하신 알림희망 환율범위에 도달하지 않았거나 도달하여 알림 안내를 받은 경우<br>
					서비스가 자동종료 되므로 추가로 알림을 받고자 하는 경우, 다시 서비스를 신청하시기 바랍니다.
				</li>
			</ul>
		</div> <!-- div.textArea -->
		
		<div class="contentArea"> 
			<form:form action="${root}exchange/noticeEditPro" method="post"
				modelAttribute="ExchangeNoticeBean">
				<form:hidden path="user_num"/>
				
				<table class="form-table">
					
					<tbody>
						<tr>
							<th scope="row">
								<b class="bTag01">*&nbsp;</b>통화종류
							</th>
							<td>
								<form:select path="code_money" class="inputType01">
									<c:forEach var="obj" items="${codeMoneyList}">
										<c:if test="${'KRW' != obj.code_money.toUpperCase().trim()}">
											<form:option value="${obj.code_money}">${obj.code_money}&nbsp;(${obj.code_money_name})</form:option>
										</c:if>
									</c:forEach>
								</form:select>
							</td>
						</tr>
					
						<tr>
							<th scope="row">
								<b class="bTag01">*&nbsp;</b>환율종류
							</th>
							<td>
								<form:select path="notice_rate_type" class="inputType01">
									<form:option value="1">매매기준율</form:option>
									<form:option value="2">송금보내실때</form:option>
									<form:option value="3">송금받으실때</form:option>
								</form:select>
							</td>
						</tr>
					
						<tr>
							<th scope="row">
								<b class="bTag01">*&nbsp;</b>알림희망환율범위
							</th>
							<td>
								<form:input path="notice_rate" class="inputType01"/>&nbsp;이하&nbsp;
								<!-- 환율 차트 판업 -->
								<button class="rateChartBtn btn btnStyle03">환율차트보기</button>
							</td>
						</tr>
					
						<tr>
							<th scope="row" > 
								<!-- 이메일 외 알림수단 추가시 rowspan = 2 하고 추가 -->
								<b class="bTag01">*&nbsp;</b>알림방법
							</th>
							
							<td>
								Email&nbsp;:&nbsp;
								<form:input path="notice_email" class="inputType01" />
							</td>
						</tr>
										
					</tbody>
					
				</table> <!-- 표 -->
				
				<div class="buttonArea">
					<form:button type="submit" class="btn btnStyle01">변경</form:button>
					<button class="deleteBtn btn btnStyle02" >해지</button>
				</div>
				
			</form:form>
		
		</div><!-- div.contentArea -->
		
	</div><!-- div.contentBox -->
	
	<script type="text/javascript">

		$(document).ready(function() { // 문서가 완전히 로드된 후 이벤트 핸들러 설정
			// rateChartBtn를 눌렀을 때 그래프 판업창 열기
			$('.rateChartBtn').on("click", function(e) {
				e.preventDefault();

				var url = "${root}/exchange/rateChart"
				var name = "환율 차트"
				var width = 500;
                var height = 700;
                var left = (window.screen.width / 2) - (width / 2);
                var top = (window.screen.height / 2) - (height / 2);
                var option = "width=" + width + ", height=" + height + ", top=" + top + ", left=" + left
                	+ ", munubar=no, scrollbars=no";
				window.open(url, name, option);
			});
			// 해지 버튼을 누를경우 한번 더 물어봄
			$('.deleteBtn').on("click", function(e) {
				e.preventDefault(); // 페이지의 기본 동작을 막음 (form:form의 action 막는용)
				if (confirm('신청된 알림 서비스를 해지하시겠습니까?')) {  // 사용자가 '확인'을 클릭했을 때
				    location.href='${root}exchange/noticeDeletPro'
				} else {  // 사용자가 '취소'를 클릭했을 때
				    console.log('취소되었습니다.');
				}
			});
			
		}); // $(document).ready
		
	</script>
	
</body>
</html>