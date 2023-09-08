$(document).ready(function(){
	
	init();
	
	/* 검색 조건 기간 설정 */
	$(".btnDate").on("click", function(){
		var term = $(this).find("span").text();
		var start = $("#date_start");
		var end = $("#date_end");
		var hidden_start = $("#hidden_date_start");
		var hidden_end = $("#hidden_date_end");

		var today = new Date();
		var startDate = new Date();
		
		switch(term){
		case "전체" : 
			start.val("");
			end.val("");
			hidden_start.val("");
			hidden_end.val("");
			break;
			
		case "오늘" : 
			start.val(moment(today).format('YYYY-MM-DD'));
			end.val(moment(today).format('YYYY-MM-DD'));
			hidden_start.val(moment(today).format('YYYY-MM-DD'));
			hidden_end.val(moment(today).format('YYYY-MM-DD'));
			break;
			
		case "어제" : 
			startDate.setDate(today.getDate()-1);
			
			start.val(moment(startDate).format('YYYY-MM-DD'));
			end.val(moment(today).format('YYYY-MM-DD'));
			hidden_start.val(moment(startDate).format('YYYY-MM-DD'));
			hidden_end.val(moment(today).format('YYYY-MM-DD'));
			break;
			
		case "3일" : 
			startDate.setDate(today.getDate()-3);
			
			start.val(moment(startDate).format('YYYY-MM-DD'));
			end.val(moment(today).format('YYYY-MM-DD'));
			hidden_start.val(moment(startDate).format('YYYY-MM-DD'));
			hidden_end.val(moment(today).format('YYYY-MM-DD'));
			break;
			
		case "7일" : 
			startDate.setDate(today.getDate()-7);
			
			start.val(moment(startDate).format('YYYY-MM-DD'));
			end.val(moment(today).format('YYYY-MM-DD'));
			hidden_start.val(moment(startDate).format('YYYY-MM-DD'));
			hidden_end.val(moment(today).format('YYYY-MM-DD'));
			break;
			
		case "15일" : 
			startDate.setDate(today.getDate()-15);
			
			start.val(moment(startDate).format('YYYY-MM-DD'));
			end.val(moment(today).format('YYYY-MM-DD'));
			hidden_start.val(moment(startDate).format('YYYY-MM-DD'));
			hidden_end.val(moment(today).format('YYYY-MM-DD'));
			break;
			
		case "1개월" : 
			startDate.setMonth(today.getMonth()-1);
			
			start.val(moment(startDate).format('YYYY-MM-DD'));
			end.val(moment(today).format('YYYY-MM-DD'));
			hidden_start.val(moment(startDate).format('YYYY-MM-DD'));
			hidden_end.val(moment(today).format('YYYY-MM-DD'));
			break;
			
		case "3개월" : 
			startDate.setMonth(today.getMonth()-3);
			
			start.val(moment(startDate).format('YYYY-MM-DD'));
			end.val(moment(today).format('YYYY-MM-DD'));
			hidden_start.val(moment(startDate).format('YYYY-MM-DD'));
			hidden_end.val(moment(today).format('YYYY-MM-DD'));
			break;
			
		case "6개월" : 
			startDate.setMonth(today.getMonth()-6);
			
			start.val(moment(startDate).format('YYYY-MM-DD'));
			end.val(moment(today).format('YYYY-MM-DD'));
			hidden_start.val(moment(startDate).format('YYYY-MM-DD'));
			hidden_end.val(moment(today).format('YYYY-MM-DD'));
			break;
			
		}
		
		// 적용되어 있던 색상 초기화
		$(".btnDate").css("background-color", "white");
		$(".btnDate").css("color", "black");
		// 클릭한 기간에 색상 적용
		$(this).css("background-color", "#3CA0EF");
		$(this).css("color", "white");
		
		var index = $(".btnDate").index($(this));
		$("#term").val(index);
		$("#hidden_term").val(index);
					
	});
	
	
	/* 검색 버튼 클릭 시 */
	$("#btn_search").on("click", function(){
		var form = $("#searchOrderForm");
		var status = $("input[name='check_status']:checked");
		
		var term = $("#term");
		var startDate = $("#date_start");
		var endDate = $("#date_end");
		
		// 검색 유효성 검사 - 주문 현황
		if(status.length < 1){
			alert("주문 현황을 선택해주세요.");
			status.focus();
			return;
		}
		
		// 검색 유효성 검사 - 기간 설정
		if(term.val== null || term.val()=="") {
			if(startDate.val() == null || startDate.val() == "" || endDate.val() == null || endDate.val() == ""){
			
				alert("기간(작성일)을 선택해주세요.");
				startDate.focus();
				return;
			}
		}
		
		//-을 구분자로 연,월,일로 잘라내어 배열로 반환
        var startArray = startDate.val().split('-');
        var endArray = endDate.val().split('-');   

		//배열에 담겨있는 연,월,일을 사용해서 Date 객체 생성
        var start_date = new Date(startArray[0], parseInt(startArray[1])-1, startArray[2]);
        var end_date = new Date(endArray[0], parseInt(endArray[1])-1, endArray[2]);
        
		// 검색 유효성 검사 - 시작일이 종료일보다 이후인 경우
        if(start_date.getTime() > end_date.getTime()){
			alert("시작일은 종료일보다 작아야합니다.");
			startDate.focus();
			return;
		} 
		
		
		form.submit();
		
	});
	
	
	/* 전체 선택 체크박스 클릭 시 */
	$("#checkAllStatus").on("click", function() {
		// 전체선택 클릭 여부로 다른 체크박스 체크
		$("input[name='check_status']").prop('checked', this.checked);
	});

	/* 체크박스 중 선택안된 체크박스 존재 시 전체선택 해제 */
	$("input[name='check_status']").on("click", function() {
		$("#checkAllStatus").prop('checked', false);
	});
	
	
	
	/* 전체 선택 체크박스 클릭 시 */
	$("#checkAll").on("click", function() {
		// 전체선택 클릭 여부로 다른 체크박스 체크
		$(".checkOrder").prop('checked', this.checked);
		$("input[name='checkProduct']").prop('checked', this.checked);
	});

	/* 주문코드 체크박스 중 선택안된 체크박스 존재 시 전체선택 해제 */
	$(".checkOrder").on("click", function() {
		$("#checkAll").prop('checked', false);
		var odr_code = $(this).val();
		$(".checkProduct_"+odr_code).prop('checked', this.checked);
	});
	

	/* 상품 체크박스 중 선택안된 체크박스 존재 시 전체선택 해제 */
	$("input[name='checkProduct']").on("click", function() {
		$("#checkAll").prop('checked', false);
		// TODO: 주문 코드 체크박스 해제
		var pdt_num = $(this).val();
		var odr_code = $("#odr_code_"+pdt_num).val();
		$("#checkOrder_" + odr_code).prop('checked', false);
	});
	
	
	
	
	/* 기간 직접 설정 시 기간 버튼 초기화 */
	$("#date_start").on("change", function(){
		// 적용되어 있던 색상 초기화
		$(".btnDate").css("background-color", "white");
		$(".btnDate").css("color", "black");
		$("#term").val("");
	});
	
});


/* 초기 설정 */
function init(){
	
	var term = $("#term").val();
	// 기간 default : 전체
	if(term != null && term != ""){
		$(".btnDate").eq(term).css("background-color", "#3CA0EF");
		$(".btnDate").eq(term).css("color", "white");
	} else {
		$(".btnDate").eq(0).css("background-color", "#3CA0EF");
		$(".btnDate").eq(0).css("color", "white");
		$("#term").val(0);
	}
	
}
