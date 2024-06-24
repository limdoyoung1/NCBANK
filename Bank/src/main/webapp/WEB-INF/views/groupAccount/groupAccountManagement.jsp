<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="root" value="${pageContext.request.contextPath}/" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>모임통장 관리</title>
<!-- Bootstrap CDN -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
<link rel="stylesheet" href="${root}css/management.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
<script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>
<script>
	// 카카오 초기화
	Kakao.init('ff8ba07dd1c6c1c318c25c022ce8bb5e'); // 앱 키
	
	function sendKakaoLink(groupNum) {
	    Kakao.Link.sendCustom({
	        templateId: 109036, // 템플릿 ID
	        templateArgs: {
	            'group_num': groupNum // 동적 group_num 전달
	        }
	    });
	}
	</script>

<script type="text/javascript">
	    function formatDate(timestamp) {
	        var date = new Date(timestamp);
	        var year = date.getFullYear();
	        var month = ('0' + (date.getMonth() + 1)).slice(-2);
	        var day = ('0' + date.getDate()).slice(-2);
	        return year + '.' + month + '.' + day;
	    }
	
	    function formatAutoType(autoType) {
	        return autoType == 0 ? '주간' : '월간';
	    }
	
	    function formatAutoNextDate(autoType, autoNextDate) {
	        return autoType == 0 ? autoNextDate + '요일' : autoNextDate + '일';
	    }
	
	    function fetchAccountInfo(account) {
	    	const bankname = "[NC뱅크]";
	    	
	        $.ajax({
	            url: '${root}groupAccount/groupAccountInfo',
	            type: 'GET',
	            data: { account: account },
	            success: function(data) {
	                 $('#auto_money').text(data.auto_money + '원');
	                 $('#auto_type').text(formatAutoType(data.auto_type));
	                 $('#auto_next_date').text(formatAutoNextDate(data.auto_type, data.auto_next_date));
	                 $('#auto_end').text(formatDate(data.auto_end));
	                 $('#group_account').text(bankname + account); // 계좌번호 설정
	            },
	            error: function(err) {
	                console.log('Error fetching account info:', err);
	            }
	        });
	
	        $.ajax({
	            url: '${root}groupAccount/groupInfo',
	            type: 'GET',
	            data: { account: account },
	            success: function(data) {
	                $('#group_joindate').text(formatDate(data.group_joindate));
	                $('#group_leader').text(data.group_leader);
	                $('#group_name').text(data.group_name);
	                $('#group_num').val(data.group_num); // Hidden input에 group_num 설정
	            },
	            error: function(err) {
	                console.log('Error fetching group info:', err);
	            }
	        });
	
	        $.ajax({
	            url: '${root}groupAccount/groupMembers',
	            type: 'GET',
	            data: { account: account },
	            success: function(membersData) {
	                $.ajax({
	                    url: '${root}groupAccount/groupMemberDetails',
	                    type: 'GET',
	                    data: { account: account },
	                    success: function(detailsData) {
	                        var membersHtml = '';
	                        $.each(membersData, function(index, member) {
	                            var detail = detailsData[index];
	                            var isLeader = detail.group_leader == '1';
	                            var leaderText = isLeader ? '<span class="leader-text">모임장</span>' : '모임원';
	                            membersHtml += '<tr><td>' + member.name + '</td><td>' + member.phone + '</td><td>' + leaderText + '</td><td>' + formatDate(detail.group_joindate) + '</td></tr>';
	                        });
	                        $('#groupMembersTable tbody').html(membersHtml);
	                    },
	                    error: function(err) {
	                        console.log('Error fetching member details info:', err);
	                    }
	                });
	            },
	            error: function(err) {
	                console.log('Error fetching members info:', err);
	            }
	        });
	
	        $.ajax({
	            url: '${root}groupAccount/groupLeader',
	            type: 'GET',
	            data: { account: account },
	            dataType: 'json',
	            success: function(data) {
	                console.log('Group Leader:', data); // 디버그용 로그
	                if (data && data.name) {
	                    $('#group_leader_name').text(data.name); // 모임장 이름 표시
	                } else {
	                    console.error('Received invalid data:', data);
	                }
	            },
	            error: function(err) {
	                console.log('Error fetching group leader info:', err);
	            }
	        });
	
	        $.ajax({
	            url: '${root}groupAccount/totalBalance',
	            type: 'GET',
	            data: { account: account },
	            success: function(data) {
	                $('#ac_balance').text(data.ac_balance + '원');
	            },
	            error: function(err) {
	                console.log('Error fetching group info:', err);
	            }
	        });
	    }
	
	    function inviteToKakao() {
	        var groupNum = $('#group_num').val();
	        console.log("Inviting to Kakao with group_num: " + groupNum); // 디버그용 로그 추가
	        if (groupNum && groupNum != '0') {
	            sendKakaoLink(groupNum);
	        } else {
	            alert('모임 정보를 먼저 선택하세요.');
	        }
	    }
	    
	    document.addEventListener('DOMContentLoaded', function () {
	        const currentPath = window.location.pathname;
	        const menuItems = document.querySelectorAll('.col-md-2 ul li a');
	
	        menuItems.forEach(item => {
	            if (item.getAttribute('href') === currentPath) {
	                item.classList.add('active');
	            }
	        });
	    });
	    
	    function updateLinksWithAccount(account) {
	        var memberLink = document.querySelector('a[href*="groupAccount/members"]');
	        var bookLink = document.querySelector('a[href*="groupAccount/book"]');
	        memberLink.href = '${root}groupAccount/members?account=' + account;
	        bookLink.href = '${root}groupAccount/book?account=' + account;
	    }
	    
	    function confirmDelete() {
	        var group_num = document.getElementById("group_num").value;
	        var confirmation = confirm("모임을 해체하시겠습니까?");
	        if (confirmation) {
	            window.location.href = '${root}groupAccount/delete?group_num=' + group_num;
	        }
	    }
	</script>
<style>
.leader-text {
	font-weight: bold;
}

.btn-container {
	display: flex;
	justify-content: center;
	margin-top: 100px;
}

.btn-container button {
	margin: 0 10px;
}
   footer {
    margin-top: auto; /* 자동으로 아래로 밀리게 설정 */
    width: 100%; 
    background-color: #f1f1f1;
    text-align: center;
    padding: 0px 0;
}
</style>
</head>
<body>
	<div class="container">
		<div class="row">
			<c:import url="/WEB-INF/views/include/top_menu.jsp" />
			<div class="col-md-2">
				<div class="menu1">
                <div class="menu1-1">모임통장 정보</div>
                <div class="menuhr">
                    <hr/>
                </div>
                <a href="${root}groupAccount/members">모임원 관리</a>
                <a href="${root}groupAccount/book">회비 관리</a>
            </div>

			</div>
			<div class="col-md-10">
				<div class="main">
					<div class="traveltitle">
						<div class="idboxtitle">모임통장 정보</div>
						<hr />
					</div>
					<div class="contents">
						<div class="contents-1">
							<div class="group71">
								<form action="${root}groupAccount/groupAccountOpened"
									method="get">
									<div class="flexClass">
										<span class="idbox">모임통장 전환 계좌</span> <select id="accounts"
											name="accounts" class="rec6"
											onchange="fetchAccountInfo(this.value)">
											<option value="" selected>선택</option>
											<c:forEach var="groupAccount" items="${groupAccountList}">
												<option value="${groupAccount.account}"
													<c:if test="${param.account == groupAccount.account}">selected</c:if>>[NC뱅크]${groupAccount.account}</option>
											</c:forEach>
										</select>
									</div>
								</form>
								<br>
								<div class="section">
									<span class="idboxtitle">자동이체 정보</span>
									<table class="table">
										<tr>
											<th>자동이체 금액</th>
											<td><span id="auto_money"></span></td>
										</tr>
										<tr>
											<th>자동이체 주기</th>
											<td><span id="auto_type"></span></td>
										</tr>
										<tr>
											<th>자동이체 납부일</th>
											<td><span id="auto_next_date"></span></td>
										</tr>
										<tr>
											<th>자동이체 종료일</th>
											<td><span id="auto_end"></span></td>
										</tr>
									</table>
									<div class="btn-right-container">
										<button class="autoBtn"
											onclick="location.href='${root}account/transferAutoFix'">자동이체
											정보 수정</button>
									</div>
								</div>
								<br>
								<div class="section">
									<span class="idboxtitle">모임정보</span>
									<table class="table">
										<tr>
											<th>모임통장 계좌번호</th>
											<td><span id="group_account"></span></td>
										</tr>
										<tr>
											<th>모임가입 날짜</th>
											<td><span id="group_joindate"></span></td>
										</tr>
										<tr>
											<th>모임장</th>
											<td><span id="group_leader_name"></span></td>
										</tr>
										<tr>
											<th>모임 이름</th>
											<td><span id="group_name"></span></td>
										</tr>
										<tr>
											<th>모임통장 총 잔액</th>
											<td><span id="ac_balance"></span></td>
										</tr>
									</table>
								</div>
								<br>
								<div class="section">
									<span class="idboxtitle">모임원 정보</span>
									<table id="groupMembersTable" class="table">
										<thead>
											<tr>
												<th>이름</th>
												<th>전화번호</th>
												<th>모임장 여부</th>
												<th>가입 날짜</th>
											</tr>
										</thead>
										<tbody>
											<!-- 모임원 정보-->
										</tbody>
									</table>
								</div>
								<input type="hidden" id="group_num" value="">
							</div>
							<input type="hidden" id="group_num" value="${group_num}">
							<div class="btn-container">
								<button class="applyBtn" onclick="inviteToKakao()">모임원
									초대하기</button>
								<button class="applyBtn"
									onclick="location.href='${root}trans/transfer'">송금하기</button>
		
									<button class="deleteBtn" onclick="confirmDelete()">모임해체</button>

							</div>
						</div>
					</div>
				</div>
				<div class="footer"></div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/bottom_info.jsp" />
	</div>
</body>
</html>