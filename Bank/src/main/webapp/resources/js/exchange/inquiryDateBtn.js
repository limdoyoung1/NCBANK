/* inquiryDate.js */
// 전일 다음일 버튼 처리

function today() {
	var currentDate = new Date();
	var year = currentDate.getFullYear();
	var month = currentDate.getMonth() + 1; // 0부터 시작해서 +1
	var day = currentDate.getDate();

	// 한자리수 	일경우 0 추가
	if (10 > month)
		month = '0' + month;
	if (10 > day)
		day = '0' + day;
	// yyyy-MM-dd 형식으로 직접 만듬
	var formattedDate = year + '-' + month + '-' + day;

	$('#inquiryDate').val(formattedDate);
}

function yesterday() {
	var inquiryDate = $('#inquiryDate').val();
	$('#inquiryDate').val(shiftDate(inquiryDate, -1));
}

function tomorrow() {
	var inquiryDate = $('#inquiryDate').val();
	$('#inquiryDate').val(shiftDate(inquiryDate, 1));
}

function shiftDate(strDate, val) {
	var date = new Date(strDate);
	date.setDate(date.getDate() + val);

	return date.toISOString().slice(0, 10); // ISO형식으로 변환
}