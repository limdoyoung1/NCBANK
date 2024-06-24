<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="root" value="${pageContext.request.contextPath}/" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>회비 관리</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
<link rel="stylesheet" href="${root}css/management.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>

<style>
    /* 테이블 헤더와 본문 내용 가운데 정렬 */
    #feesTable th, #feesTable td {
        text-align: center;
        vertical-align: middle;
    }
    footer {
    margin-top: 120px; /* 자동으로 아래로 밀리게 설정 */
    width: 100%; 
    background-color: #f1f1f1;
    text-align: center;
    padding: 50px 0;
}
</style>

<script>
    function formatTransferDate(timestamp) {
        var date = new Date(timestamp);
        var year = date.getFullYear();
        var month = ('0' + (date.getMonth() + 1)).slice(-2);
        var day = ('0' + date.getDate()).slice(-2);
        return year + '.' + month + '.' + day;
    }

    function fetchAccountTransfersAndMembers(account) {
        // Fetch group members
        $.ajax({
            url: '${root}groupAccount/groupMembers',
            type: 'GET',
            data: { account: account },
            success: function(membersData) {
                // Fetch group member details
                $.ajax({
                    url: '${root}groupAccount/groupMemberDetails',
                    type: 'GET',
                    data: { account: account },
                    success: function(detailsData) {
                        // Combine members and details data
                        var membersMap = {};
                        $.each(membersData, function(index, member) {
                            var detail = detailsData[index];
                            membersMap[member.user_num] = {
                                name: member.name,
                                joinDate: detail.group_joindate,
                                isLeader: detail.group_leader == '1'
                            };
                        });

                        // Fetch account transfers
                        $.ajax({
                            url: '${root}groupAccount/getAccountTransfers',
                            type: 'GET',
                            data: { account: account },
                            success: function(transfersData) {
                                var transfersHtml = '';
                                $.each(transfersData, function(index, transfer) {
                                    var member = membersMap[transfer.user_num] || { name: '박영도' };
                                    transfersHtml += '<tr>' +
                                        '<td>' + (index + 1) + '</td>' + // 순서 번호 추가
                                        '<td>' + member.name + '</td>' +
                                        '<td>' + formatTransferDate(transfer.trans_date) + '</td>' +
                                        '<td>' + (transfer.trans_type == 2 ? '출금' : '입금') + '</td>' +
                                        '<td>' + transfer.trans_money + '</td>' +
                                        '<td>' + transfer.trans_balance + '</td>' +
                                        '<td>' + (transfer.trans_text ? transfer.trans_text : '') + '</td>' +
                                        '</tr>';
                                });
                                $('#feesTable tbody').html(transfersHtml);
                            },
                            error: function(err) {
                                console.log('Error fetching transfers:', err);
                            }
                        });
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
    }

    $(document).ready(function() {
        $('#accounts').change(function() {
            var account = $(this).val();
            if (account) {
                fetchAccountTransfersAndMembers(account);
            }
        });
    });
</script>
</head>
<body>
    <div class="container">
        <div class="row">
            <c:import url="/WEB-INF/views/include/top_menu.jsp" />
            <div class="col-md-2">
                <div class="menu1">
                    <a href="${root}groupAccount/management">모임통장 정보</a>
                    <a href="${root}groupAccount/members">모임원 관리</a>
                    <div class="menu1-1">회비관리</div>
                    <div class="menuhr">
                        <hr/>
                    </div>
                </div>
            </div>
            <div class="col-md-10">
                <div class="main">
                    <div class="traveltitle">
                        <div class="idboxtitle">회비 관리</div>
                        <hr />
                    </div>
                    <form action="${root}groupAccount/groupAccountOpened" method="get">
                        <div class="flexClass">
                            <span class="idbox">모임통장 전환 계좌</span>
                            <select id="accounts" name="accounts" class="rec6">
                                <option value="" selected>선택</option>
                                <c:forEach var="groupAccount" items="${groupAccountList}">
                                    <option value="${groupAccount.account}">[NC뱅크]${groupAccount.account}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </form>
                    <div class="contents">
                        <div class="contents-1">
                            <div class="group71">
                                <table id="feesTable" class="table">
                                    <thead>
                                        <tr>
                                            <th>순서</th> <!-- 순서 열 추가 -->
                                            <th>이름</th>
                                            <th>날짜</th>
                                            <th>유형</th>
                                            <th>금액</th>
                                            <th>남은잔액</th>
                                            <th>비고</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <!-- 이체 내역이 여기에 표시됩니다 -->
                                    </tbody>
                                </table>
                                <div class="btn-right-container">
                                <button class="bookBtn1" onclick="location.href='${root}trans/transfer'">송금하기</button>
                                <button class="bookBtn2" onclick="location.href='${root}trans/transfer'">출금하기</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <footer>
            <c:import url="/WEB-INF/views/include/bottom_info.jsp" />
        </footer>
    </div>
</body>
</html>
