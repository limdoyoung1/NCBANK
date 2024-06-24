<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var='root' value="${pageContext.request.contextPath }/" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>create</title>
<!-- Bootstrap CDN -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/agreement.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
<script defer src="${pageContext.request.contextPath}/js/agreement.js"></script>
<script type="text/javascript">
    var isUserLoggedIn = ${loginUserBean.userLogin ? 'true' : 'false'};
</script>
<style>
footer {
    margin-top: auto; /* 자동으로 아래로 밀리게 설정 */
    width: 100%; 
    background-color: #f1f1f1;
    text-align: center;
    padding: -20px 0;
    margin: -20px;
}
</style>

</head>
<body>
    <div class="container">
    <c:import url="/WEB-INF/views/include/top_menu.jsp" />
        <div class="traveltitle">
            모임통장 계좌 개설
            <hr />
        </div>
        
        <div class="contents">
            <div class="menu1">
                <div class="menu1-1">모임약관동의</div>
                <div class="menuhr">
                    <hr/>
                </div>
                <a href="">모임통장 개설</a>
                <a href="">모임통장 초대</a>
            </div>

            <div class="contents-1">
                <div class="section-1">
                    <div class="contentsText"></div>
                    <div class="stepper">
                        <div class="line"></div>
                        <div class="step">
                            <div class="circle active">1</div>
                        </div>
                        <div class="step">
                            <div class="circle">2</div>
                        </div>
                        <div class="step">
                            <div class="circle">3</div>
                        </div>
                    </div>
                </div>
                
                <div class="section-3">
                    <div class="newAccount">안내 및 유의사항을 꼭 확인하세요!</div>
                    <div class="hanaClassBox">
                    <div class="hanaClass">NCBANK 모임통장</div>
                    </div>
                    <div class="applyBox">
                        <button class="applyBtn" onclick="openModal();">개설하기</button>
                    </div>
                    <div class="modal" id="myModal">
                    <div class="modal-content">
                    <span class="close-btn" onclick="closeModal()">&times;</span>
                    <h2>모임통장 만들기</h2>
                    <div class="group71">
                        <form:form method="post" action="${root}groupAccount/groupAccountOpened" id="groupForm">
                            <br />
                            <div class="flexClass">
                                <span class="idbox">모임명</span> <input type="text"
                                    name="groupname" class="rec6" placeholder="모임명을 입력해주세요"
                                    autocomplete="off" />
                            </div>
                            <br />
                           
                            
                            <div class="flexClass">
                                <span class="idbox">계좌선택</span> 
                                <select id="accounts" name="accounts" class="rec6">
                                    <option value="" selected>선택</option>
                                    <c:forEach var="account" items="${accountList}">
                                        <option value="${account.account}">[NC뱅크]${account.account}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            
                            <br />
                            <button id="calculate" type="submit">
                                <span>모임 만들기</span>
                            </button>
                        </form:form>
                    </div>
                    </div>
                    </div>
                </div>
                
                <div class="section-4">
                    <div class="classSection">
                        <div class="class-1" id="agreeDetail"
                            onclick="toggleAgreement('agreeDetail1')">모임통장 안내</div>

                        <div class="plus">
                            <button class="plusButton" data-target="agreeDetail1">&#43;</button>
                        </div>
                    </div>
                    <div class="agreeDetail" id="agreeDetail1">
                        <div class="g__pop-content">
                            <strong>서비스 개요</strong>
                            <p>NC모임통장 입출금통장에 모임통장서비스를 신청하면 회비조회, 회비관리, 멤버관리가 가능한 모임통장으로
                                사용할 수 있는 서비스</p>
                            <strong>서비스 대상고객</strong>
                            <ul>
                                <li>• 모임주 : NC모임통장 입출금통장 보유 고객</li>
                                <li>• 모임멤버 : NC모임통장 회원 가입 고객 (만 14세 이상)</li>
                            </ul>
                            <strong>서비스 대상상품</strong>
                            <p>NC모임통장 입출금통장</p>
                            <span>※ 입출금통장 1개당 모임통장서비스는 1개만 신청 가능 (입출금통장 1개에 복수의
                                모임통장서비스 신청은 불가)</span><br> <span>※ 단, 마이너스통장 등의 한도 대출 이용
                                계좌, 대출 이자, 원리금 납부계좌 등 대출에 활용 중인 계좌나 압류, 질권, 금융사기 등 사고 신고된 계좌는 신청
                                불가</span> <strong>서비스 신청 및 참여 한도</strong>
                            <ul>
                                <li>• (모임주) 신청 가능 수 : 인당 최대 100개 모임 개설 가능</li>
                                <li>• (모임멤버) 참여 가능 수 : 인당 최대 30개 모임 참여 가능</li>
                            </ul>
                            <span>※ 모임통장서비스 1개당 모임멤버는 최대 100명까지 참여 가능</span> <strong>제공서비스
                                안내</strong>
                            <ul>
                                <li>• 모임멤버 초대 : 카카오톡 친구 또는 채팅방에 있는 모임멤버들을 빠르고 쉽게 초대 가능</li>
                                <li>• 모임통장 조회 : 모임주의 모임통장 회비내역을 멤버들도 함께 조회하고 확인</li>
                                <li>• 모임통장 관리 : 모임통장 회비납입 현황을 한눈에 확인하고, 메시지 카드를 통해 손쉽게 회비
                                    요청</li>
                            </ul>
                        </div>
                    </div>
                    <hr>
                    <div class="classSection">
                        <div class="class-1" onclick="toggleAgreement('agreeDetail2')">모임통장
                            유의사항</div>
                        <div class="plus">
                            <button class="plusButton" data-target="agreeDetail2">&#43;</button>
                        </div>
                    </div>
                    <div class="agreeDetail" id="agreeDetail2">
                        <div class="g__pop-content">
                            <strong>서비스 신청 제한 계좌</strong>
                            <ul>
                                <li>• 비상금대출, 마이너스 통장 등 한도 대출 이용 계좌</li>
                                <li>• NC모임통장 대출 이자 또는 원리금 납부 이용 계좌</li>
                                <li>• NC모임통장 개인사업자통장 가입 계좌</li>
                                <li>• 압류, 질권이 설정되었거나, 금융사기 등 사고신고 등록 계좌</li>
                            </ul>
                            <strong>서비스 이용 계좌 거래 제한</strong>
                            <ul>
                                <li>• 비상금대출, 마이너스 통장 등 한도 대출로 이용 불가</li>
                                <li>• NC모임통장 대출 이자 또는 원리금 납부계좌로 이용 불가</li>
                                <li>• NC모임통장 개인사업자통장으로 상품 전환 불가</li>
                            </ul>
                            <strong>서비스 이용 정지</strong>
                            <p>모임통장 또는 모임주가 다음에 해당되는 경우 모임통장서비스 이용이 일시적으로 중단됨 (단, 이용정지
                                사유가 해소된 경우 모임주는 모바일앱을 통해 즉시 이용정지해제 신청 가능)</p>
                            <ol>
                                <li>1. 모임통장을 장기간 사용하지 않는 경우 (1년간 로그인 또는 입출금이 없는 경우)</li>
                                <li>2. 모임통장에 압류 등이 설정되거나, 금융사기 등 사고신고가 등록된 경우</li>
                                <li>3. 모임주가 고객센터를 통해 직접 요청한 경우</li>
                            </ol>
                            <strong>서비스 유의사항</strong>
                            <ul>
                                <li>• 모임통장은 모임주 개인 명의의 통장으로 멤버들이 납입한 모임회비의 지급, 해지 권한은 모임주에게
                                    있으며, 압류 등 모임주의 상태에 따라 모임회비 및 모임통장서비스 이용이 제한될 수 있음</li>
                                <li>• 모임통장서비스를 종료하려면 모임에 참여 중인 멤버를 모두 내보내기 한 이후에 가능하며, 해지 후
                                    모임통장 내용 복원은 불가함</li>
                                <li>• 모임통장서비스는 NC모임통장 입출금통장에 연계되어 제공되는 서비스로, 입출금통장에 대한 자세한
                                    사항은 NC모임통장 입출금통장 상품설명서 및 약관을 참고하시기 바랍니다.</li>
                            </ul>
                            <strong>서비스 관련 문의 및 민원 절차</strong>
                            <p>서비스에 대한 문의·민원 등 상담이 필요하실 경우 NC모임통장 고객센터, 앱, 인터넷 홈페이지를 통해
                                상담이 가능합니다.</p>
                        </div>
                    </div>
                    <hr>
                    <div class="classSection">
                        <div class="class-1" onclick="toggleAgreement('agreeDetail13')">상품설명서
                            및 이용약관</div>
                        <div class="plus">
                            <button class="plusButton" data-target="agreeDetail3">&#43;</button>
                        </div>
                    </div>
                    <div class="agreeDetail" id="agreeDetail3">
                        <div class="g__pop-content info_cont">
                            <strong>모임통장서비스 설명서 <a
                                href="https://og.kakaobank.io/download/2ed43d04-96c0-493b-abbc-6a057991ba3b"
                                target="_blank" class="link_download" title="모임통장서비스 설명서 다운로드"
                                data-show-doc="true"
                                data-html-url="https://og.kakaobank.io/view/48b56e95-2d94-43bb-a01e-8a3eb4fba227"
                                data-txt-url="https://og.kakaobank.io/view/012b144f-f0b1-4f22-a8fb-6711e7c93c0a"
                                data-doc-nm="모임통장서비스 설명서"> 다운로드 <span
                                    class="img_kakaobank img_download"></span>
                            </a>
                            </strong> <span class="line_g"></span> <strong class="tit_g">상품
                                이용 약관<a
                                href="https://og.kakaobank.io/download/36aebd05-2a5d-4f4c-b994-a310bc1aeb56"
                                target="_blank" class="link_download" title="모임통장서비스 통합 약관 다운로드"
                                data-show-doc="true"
                                data-html-url="https://og.kakaobank.io/view/692ba316-0e50-4984-8c0a-8f692ad08790"
                                data-txt-url="https://og.kakaobank.io/view/76f0fb34-03f5-41fd-aed2-0c4546bad33f"
                                data-doc-nm="모임통장서비스 통합 약관"> 다운로드<span
                                    class="img_kakaobank img_download"></span>
                            </a>
                            </strong>
                            <ul>
                                <li style="margin-bottom: 4px;">•<a
                                    href="https://og.kakaobank.io/download/944aa98f-d706-4cfd-92a4-e22f0e94fa4f"
                                    target="_blank" style="text-decoration: underline;"
                                    title="모임통장서비스 이용약관 다운로드" data-show-doc="true"
                                    data-html-url="https://og.kakaobank.io/view/02693de8-1704-43f0-b2f0-b4a7b7bfe2dd"
                                    data-txt-url="https://og.kakaobank.io/view/d5b914d9-8533-435d-8a53-a19420b7463f"
                                    data-doc-nm="모임통장서비스 이용약관"> 모임통장서비스 이용약관 </a>
                                </li>
                            </ul>
                        </div>
                    </div>

                    <hr>
                    <div class="modal" id="agreeModal">
                        <div id="joinForm">
                            <ul class="join_box">
                                <h2>약관동의</h2>
                                <li class="checkBox check01">
                                    <ul class="clearfix">
                                        <li>이용약관, 입출금 예금약관, 계좌간 자동이체 이용약관(필수)에 모두 동의합니다.</li>
                                        <li class="checkAllBtn"><input type="checkbox"
                                            name="chkAll" id="chk" class="chkAll"></li>
                                    </ul>
                                </li>
                                <li class="checkBox check02">
                                    <ul class="clearfix">
                                        <li>이용약관 동의(필수)</li>
                                        <li class="checkBtn"><input type="checkbox" name="chk">
                                        </li>
                                    </ul> <textarea name="" id="">
                                    <jsp:include
                                            page="../form/form01.jsp" />
                                </textarea>
                                </li>
                                <li class="checkBox check03">
                                    <ul class="clearfix">
                                        <li>입출금 예금약관 동의(필수)</li>
                                        <li class="checkBtn"><input type="checkbox" name="chk">
                                        </li>
                                    </ul> <textarea name="" id="">
                                    <jsp:include
                                            page="../form/form02.jsp" />
                                </textarea>
                                </li>
                                <li class="checkBox check03">
                                    <ul class="clearfix">
                                        <li>계좌간 자동이체약관 동의(필수)</li>
                                        <li class="checkBtn"><input type="checkbox" name="chk">
                                        </li>
                                    </ul> <textarea name="" id="">
                                     <jsp:include
                                            page="../form/form03.jsp" />
                                </textarea>
                                </li>

                                <li style="text-align: center;"><button
                                        onclick="groupFunc()" class="fpmgBt2" type="submit">약관동의</button></li>

                            </ul>

                        </div>
                    </div>

                </div>
            </div>
        </div>
        <footer>
    <c:import url="/WEB-INF/views/include/bottom_info.jsp" />
    </footer>
    </div>
    
    
    <script>
    document.querySelector('.plusButton').addEventListener('click', function() {
        var section = document.querySelector('.section-4');
        var maxHeight = parseInt(window.getComputedStyle(section).getPropertyValue('max-height'), 10);
        var newMaxHeight = maxHeight + 100; // 세션의 최대 높이를 100px씩 늘림
        section.style.maxHeight = newMaxHeight + 'px';
    });
    
    function openModal() {
        if(!isUserLoggedIn){
            alert('로그인 해주세요.');
            window.location.href = '${root}user/login'; 
            return;
        }
        var modal = document.getElementById('agreeModal');
        modal.style.display = 'block';
        
    }
    
    function groupFunc() {
        var modal = document.getElementById('agreeModal');
        var agreementCheckboxes = document.querySelectorAll('.checkBox input[type="checkbox"]');
        var allChecked = false;
        let accountList = [];
        
        // 개별 약관 동의 여부 확인
        if(agreementCheckboxes[0].checked){
            allChecked = true;
        } else {
            var cnt = 0;
            for (var i = 1; i < agreementCheckboxes.length; i++) {
                if (agreementCheckboxes[i].checked) {
                    cnt ++;
                    if(cnt == 3){
                        allChecked = true;
                    }
                }
            }
        }
        
        // 모두 동의시
        if (allChecked) {
            modal.style.display = 'none';
            var modal = document.getElementById('myModal');
            modal.style.display = 'block';
        } else {
            alert("모든 약관에 동의해주세요.");
        }
    }
    
    </script>

</body>
</html>
