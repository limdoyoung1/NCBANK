<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bottom_info.css">  
<!-- <script>
	document.addEventListener('DOMContentLoaded', function() {
		const currencySelect = document.getElementById('currencySelect');
		const currencyImage = document.getElementById('currencyImage');

		currencySelect.addEventListener('change', function() {
			const selectedValue = currencySelect.value;
			currencyImage.src = `img/${selectedValue}.png`;
			currencyImage.alt = `${selectedValue} 이미지`;
		});
	});
</script> -->
<footer>
	<div class="footWrap">
		<div class="foot_area_2015">
			<!-- 기본정보 -->
			<ul class="nav-fnb mb0">
				<li><a href="https://obank.kbstar.com/quics?page=C019763&amp;QSL=F">이용상담</a></li>
				<li><a href="https://obank.kbstar.com/quics?page=C040531&amp;QSL=F">보안프로그램</a></li>
				<li><a href="https://obank.kbstar.com/quics?page=C019933&amp;QSL=F">사고신고</a></li>
				<li><a href="https://obank.kbstar.com/quics?page=C023000&amp;QSL=F">보호금융상품등록부</a></li>
				<li><a href="https://obank.kbstar.com/quics?page=C036346&amp;QSL=F"><strong>전자민원접수</strong></a></li>
				<li><a href="https://obank.kbstar.com/quics?page=C108662&amp;QSL=F" class="fot_p_txt"><strong>개인정보처리방침</strong></a></li>
				<li><a href="https://obank.kbstar.com/quics?page=C019924&amp;QSL=F"><strong>신용정보활용체제</strong></a></li>
				<li><a href="https://obank.kbstar.com/quics?page=C108732&amp;QSL=F"><strong>위치기반서비스 이용약관</strong></a></li>
				<li><a href="https://omoney.kbstar.com/quics?page=C017681&amp;QSL=F"><strong>경영공시</strong></a></li>
			</ul>
			<ul class="nav-fnb">
				<li><a href="https://obank.kbstar.com/quics?page=C040593&amp;QSL=F">그룹 내 고객정보 제공안내</a></li>
				<li><a href="https://obank.kbstar.com/quics?page=C057315&amp;QSL=F">스튜어드십 코드</a></li>
			</ul>
			<!-- 바로가기 및 고객센터 -->
			<div class="info-group" id="navFooterGroup">
				<ul class="list">
					<!-- KB금융그룹네트워크 -->
					<li>
						<label for="networkSelect" class="tit"></label>
						<select id="networkSelect" class="info">
							<option value="https://www.kbfg.com">NC금융그룹</option>
							<option value="http://www.kbsec.co.kr">NC증권</option>
							<option value="http://www.kbinsure.co.kr">NC손해보험</option>
							<option value="https://www.kbcard.com">NC국민카드</option>
							<option value="http://www.kbam.co.kr">NC자산운용</option>
							<option value="http://www.kbcapital.co.kr">NC캐피탈</option>
							<option value="https://www.kblife.co.kr">NC라이프생명</option>
							<option value="http://www.kbret.co.kr">NC부동산신탁</option>
							<option value="https://www.kbsavings.com">NC저축은행</option>
							<option value="http://www.kbic.co.kr">NC인베스트먼트</option>
							<option value="http://www.kds.co.kr">NC데이타시스템</option>
							<option value="http://www.kbci.co.kr">NC신용정보</option>
							<option value="https://www.kbfg.com/kbresearch/main.do">NC경영연구소</option>
						</select>
					</li>
					<!-- 대표전화 -->
					<li class="ic1">
						<label for="phoneSelect" class="tit"></label>
						<select id="phoneSelect" class="info">
							<option value="tel:1588-9999">대표전화 1588-9999</option>
							<option value="tel:1599-9999">대표전화 1599-9999</option>
							<option value="tel:1644-9999">대표전화 1644-9999</option>
							<option value="tel:+82-2-6300-9999">해외에서 국내로 걸 때 +82-2-6300-9999</option>
							<option value="tel:1588-9008">대출 단기연체 및 만기안내 1588-9008</option>
							<option value="tel:+82-2-3702-1240">해외에서 국내로 걸 때 +82-2-3702-1240</option>
							<option value="tel:1800-9999">상담전용 1800-9999 (분실 및 사고신고 제외)</option>
							<option value="tel:1833-3938">투자상품 승낙확인 1833-3938</option>
							<option value="tel:+82-1833-3938">해외에서 국내로 걸 때 +82-1833-3938</option>
							<option value="tel:1599-9499">기업전용 1599-9499</option>
							<option value="tel:1566-9944">기업(B2B) 1566-9944</option>
							<option value="tel:1599-4477">외국인전용 1599-4477</option>
							<option value="tel:1644-3308">어르신전용 1644-3308</option>
						</select>
					</li>
					<!-- 비교조회서비스 -->
					<li>
						<label for="compareSelect" class="tit"></label>
						<select id="compareSelect" class="info" onchange="window.open(this.value, '_blank')">
							<option value="#">비교교회서비스</option>
							<option value="https://portal.kfb.or.kr/compare/receiving_deposit_3.php">은행금리비교</option>
							<option value="http://finlife.fss.or.kr">금융상품 한눈에</option>
							<option value="https://fine.fss.or.kr/fine/main/main.do?menuNo=900000">금융소비자정보포털 '파인'</option>
						</select>
					</li>
				</ul>
			</div>
			<!-- SNS -->
			<div class="info-sns">
				<ul>
					<li class="ic1"><a href="https://www.facebook.com/kbkookminbank" target="_blank" title="KB국민은행 페이스북 새창 열기" class="facebook"></a></li>
					<li class="ic2"><a href="https://instagram.com/kbkookminbank" target="_blank" title="KB국민은행 인스타그램 새창 열기" class="instagram"></a></li>
					<li class="ic3"><a href="https://www.youtube.com/user/openkbstar" target="_blank" title="KB국민은행 YouTube 새창 열기" class="youtube"></a></li>
					<li class="ic4"><a href="https://blog.naver.com/youngkbblog" target="_blank" title="KB국민은행 blog 새창 열기" class="blog"></a></li>
				</ul>
			</div>
				<!-- ################################## -->
				<!-- ### COPYRIGHT 및 마크 ### -->
				<!-- ################################## -->
				<p class="copy">Copyright NC NullCrush Bank. All Rights Reserved.</p>
				<!-- ################################## -->
			</div>
		</div>
</footer>
