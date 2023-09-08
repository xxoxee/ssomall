$(document).ready(function(){
	
	/* 검색 조건 기간 설정 */
	$(".btnDate").on("click", function(){
		var term = $(this).find("span").text();
		var start = $("#date_start");
		var end = $("#date_end");

		var today = new Date();
		var startDate = new Date();
		
		switch(term){
		case "전체" : 
			start.val("");
			end.val("");
			break;
		case "오늘" : 
			start.val(moment(today).format('YYYY-MM-DD'));
			end.val(moment(today).format('YYYY-MM-DD'));
			break;
		case "어제" : 
			startDate.setDate(today.getDate()-1)
			start.val(moment(startDate).format('YYYY-MM-DD'));
			end.val(moment(today).format('YYYY-MM-DD'));
			break;
		case "3일" : 
			startDate.setDate(today.getDate()-3)
			start.val(moment(startDate).format('YYYY-MM-DD'));
			end.val(moment(today).format('YYYY-MM-DD'));
			break;
		case "7일" : 
			startDate.setDate(today.getDate()-7)
			start.val(moment(startDate).format('YYYY-MM-DD'));
			end.val(moment(today).format('YYYY-MM-DD'));
			break;
		case "15일" : 
			startDate.setDate(today.getDate()-15)
			start.val(moment(startDate).format('YYYY-MM-DD'));
			end.val(moment(today).format('YYYY-MM-DD'));
			break;
		case "1개월" : 
			startDate.setMonth(today.getMonth()-1)
			start.val(moment(startDate).format('YYYY-MM-DD'));
			end.val(moment(today).format('YYYY-MM-DD'));
			break;
		case "3개월" : 
			startDate.setMonth(today.getMonth()-3)
			start.val(moment(startDate).format('YYYY-MM-DD'));
			end.val(moment(today).format('YYYY-MM-DD'));
			break;
		case "6개월" : 
			startDate.setMonth(today.getMonth()-6)
			start.val(moment(startDate).format('YYYY-MM-DD'));
			end.val(moment(today).format('YYYY-MM-DD'));
			break;
		}
		
		// 적용되어 있던 색상 초기화
		$(".btnDate").css("background-color", "white");
		$(".btnDate").css("color", "black");
		// 클릭한 기간에 색상 적용
		$(this).css("background-color", "#3C8DBC");
		$(this).css("color", "white");
		$("#term").val($(this).index());
	});
	
	/* 기간 직접 설정 시 기간 버튼 초기화 */
	$("#date_start").on("change", function(){
		// 적용되어 있던 색상 초기화
		$(".btnDate").css("background-color", "white");
		$(".btnDate").css("color", "black");
		$("#term").val("");
	});
	
	
});


/* 모달 창에 이전에 존재하던 값 초기화 */
function initModalValue(){
	$("#brd_num").val("");
	$("#title_modal").val("");
	$("#content_modal").val("");
	$("#writer_id_modal").val("");
	
	$("#group").val("");
	$("#order").val("");
	$("#level").val("");
}


/* 모달 창 읽기 전용/ 쓰기 가능 설정 */
function readonlyText(set){

	if(set){ // 읽기 전용
		$("#title_modal").attr("readonly", true);
		$("#content_modal").attr("readonly", true);
		
	} else{ // 쓰기 가능
		$("#title_modal").attr("readonly", false);
		$("#content_modal").attr("readonly", false);
	}

}




