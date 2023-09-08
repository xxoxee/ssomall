
$(document).ready(function(){
	
	// 초기 작업
	init();
	drawChartTable();
	
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
		$(this).css("background-color", "#3CA0EF");
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

	
	/* 검색 버튼 클릭 시 */
	$("#btn_search").on("click", function(){
		
		var mainCategory = $("#mainCategory option:selected");
		var subCategory = $("#subCategory option:selected");

		var term = $("#term");
		var startDate = $("#date_start");
		var endDate = $("#date_end");
		
		//-을 구분자로 연,월,일로 잘라내어 배열로 반환
        var startArray = startDate.val().split('-');
        var endArray = endDate.val().split('-');   

		//배열에 담겨있는 연,월,일을 사용해서 Date 객체 생성
        var start_date = new Date(startArray[0], parseInt(startArray[1])-1, startArray[2]);
        var end_date = new Date(endArray[0], parseInt(endArray[1])-1, endArray[2]);
        
		// 시작일이 종료일보다 이후인 경우
        if(start_date.getTime() > end_date.getTime()){
			alert("시작일은 종료일보다 작아야합니다.");
			startDate.focus();
			return;
		} 
		
		
		// 검색 유효성 검사 - 기간 설정
		if(term.val== null || term.val()=="") {
			// TODO: term.val() init에서 확인 후 색칠하는 부분
			if(startDate.val() == null || startDate.val() == "" || endDate.val() == null || endDate.val() == ""){
			
				alert("기간(작성일)을 선택해주세요.");
				startDate.focus();
				return;
			}
		
		// 유효성 검사 통과 시, 통계그래프와 테이블 재생성
		} else{
			drawChartTable(mainCategory.val(), subCategory.val(), startDate.val(), endDate.val());
			
		}
	});

	
	/* 통계 테이블 탭 클릭 메소드 */
	$(".tableTab").on("click", function(){
		
		// 통계 테이블 default: 수량통계
		$(".tableTab").css("background-color", "white");
		$(".tableTab").css("color", "black");
		
		if($(this).text() == '수량 통계'){
			// 통계 테이블 default: 수량통계
			$("#tablePriceDiv").hide();
			$("#tableAmountDiv").show();
			
			$(".tableTab").eq(0).css("background-color", "#3CA0EF");
			$(".tableTab").eq(0).css("color", "white");
			
		} else {
			// 통계 테이블 : 가격 통계
			$("#tableAmountDiv").hide();
			$("#tablePriceDiv").show();
			
			$(".tableTab").eq(1).css("background-color", "#3CA0EF");
			$(".tableTab").eq(1).css("color", "white");
		}
	});
	
	
	/* 1차 카테고리에 따른 2차 카테고리 작업 */
	$("#mainCategory").on("change", function(){
		var mainCGCode= $(this).val();
		var url = "/admin/product/subCGList/" + mainCGCode;

		// REST 방식으로 전송
		$.getJSON(url, function(data){
			// 받은 데이터로 subCategory에 템플릿 적용
			subCGList(data, $("#subCategory") ,$("#subCGListTemplate"))
		});
	});

	
	
});

/* 초기 설정 */
function init(){
	
	// 기간 default : 전체
	$(".btnDate").eq(0).css("background-color", "#3CA0EF");
	$(".btnDate").eq(0).css("color", "white");
	$("#term").val(0);
	
	// 통계 테이블 default: 수량통계
	$(".tableTab").eq(0).css("background-color", "#3CA0EF");
	$(".tableTab").eq(0).css("color", "white");
	$("#tableAmountDiv").show();
	$("#tablePriceDiv").hide();
}



/* 2차 카테고리 템플릿 적용함수 */
function subCGList(subCGStr, target, templateObject) {

	var template = Handlebars.compile(templateObject.html());
	var options = template(subCGStr);

	// 기존 option 제거(누적방지)
	$("#subCategory option").remove();
	target.append(options);
}




/* 통계 그래프와 테이블 생성 */
function drawChartTable(mainCategory, subCategory, startDate, endDate){
	var data;
	
	// 카테고리 조건 없음
	if(mainCategory == null || mainCategory=='default'){ 
		// 날짜 조건 있음
		if(startDate != null && startDate != ""){
			data = {
					"startDate" : startDate,
					"endDate" : endDate
			};
			
		// 날짜 조건 없음
		} else{
			data = {};
		}
		
	// 카테고리 조건 있음
	} else {
		// 1차 카테고리:선택/ 2차 카테고리:전체
		if(subCategory == 'all'){
			
			// 날짜 조건 있음
			if(startDate != null && startDate != ""){
				data = {
						"cg_code_prt" : mainCategory,
						"startDate" : startDate,
						"endDate" : endDate
				};
				
			// 날짜 조건 없음
			} else{
				data = {
						"cg_code_prt" : mainCategory
				};
			}
			
		// 1차 카테고리:선택/ 2차 카테고리:선택
		} else{
			// 날짜 조건 있음
			if(startDate != null && startDate != ""){
				data = {
						"cg_code" : subCategory,
						"startDate" : startDate,
						"endDate" : endDate
				};
				
			// 날짜 조건 없음
			} else{
				data = {
						"cg_code" : subCategory
				};
			}
		}
	} 
	
	console.log(data);
	
	// 라이브러리 로드 및 차트 생성
	google.charts.load("current", {packages:["corechart"]});
	google.charts.setOnLoadCallback(function () {
        drawChart(data);
	});
	
	// 통계테이블 구현 
	getAmountTable(data);
	getPriceTable(data);
	
	// 통계 테이블 default: 수량통계
	$(".tableTab").css("background-color", "white");
	$(".tableTab").css("color", "black");
	$(".tableTab").eq(0).css("background-color", "#3CA0EF");
	$(".tableTab").eq(0).css("color", "white");
	
	$("#tableAmountDiv").show();
	$("#tablePriceDiv").hide();
	
}


/* 도넛 차트 그리기 */
function drawChart(data) {
	
	// 판매 수량 순 TOP 7 상품
	var amountJsonData = $.ajax({
		url : "/admin/chart/product/amount",
		type: 'post',
		data: data,
		dataType : "json",
		async : false
	}).responseText; //제이슨파일을 text파일로 읽어들인다는 뜻
	
	var amountData = new google.visualization.DataTable(amountJsonData);
	
	// 판매 가격 순 TOP 7 상품
	var priceJsonData = $.ajax({
		url : "/admin/chart/product/price",
		type: 'post',
		data: data,
		dataType : "json",
		async : false
	}).responseText; //제이슨파일을 text파일로 읽어들인다는 뜻

	var priceData = new google.visualization.DataTable(priceJsonData);
	
	
	// 차트 그리기
	drawChartAmount(amountData);
	drawChartPrice(priceData);
}

/* 도넛 차트 - 판매수량 상위 TOP 7 */
function drawChartAmount(amountData) {
	
    var options = {
		width : 500,
		height : 300,
	    title: '판매수량 상위 TOP 7 상품 ',
	    pieHole: 0.4,
    };

    var chart = new google.visualization.PieChart(document.getElementById('chartAmountDiv'));
    chart.draw(amountData, options);
 }

/* 도넛 차트 - 판매가격 상위 TOP 7 */
function drawChartPrice(priceData) {
	
    var options = {
		width : 500,
		height : 300,
	    title: '판매가격 상위 TOP 7 상품 ',
	    pieHole: 0.4,
    };

    var chart = new google.visualization.PieChart(document.getElementById('chartPriceDiv'));
    chart.draw(priceData, options);
 }



/* 통계 테이블 - 판매 수량 */
function getAmountTable(data){
	
	$.ajax({
		url: '/admin/chart/product/amountTable' ,
		type: 'post',
		data: data,
		dataType: 'json',
		success: function(list){
			// 테이블에 표현할 데이터가 존재하는 경우,
			if(Object.keys(list).length > 0){
				printTable(list, $("#tableAmountDiv"), $('#amountTableTemplate'));
				
			// 테이블에 표현할 데이터가 존재하지 않는 경우,
			} else{
				printTable(list, $("#tableAmountDiv"), $('#noAmountTableData'));
			}
		}
	});
}

/* 통계 테이블 - 판매 가격 */
function getPriceTable(data){
	
	$.ajax({
		url: '/admin/chart/product/priceTable' ,
		type: 'post',
		data: data,
		dataType: 'json',
		success: function(list){
			// 테이블에 표현할 데이터가 존재하는 경우,
			if(Object.keys(list).length > 0){
				printTable(list, $("#tablePriceDiv"), $('#priceTableTemplate'));
			
			// 테이블에 표현할 데이터가 존재하지 않는 경우,
			} else{
				printTable(list, $("#tablePriceDiv"), $('#noPriceTableData'));
			}
		}
	});
}


/*
 * printTable()
 * : 통계테이블을 보여주는 템플릿 적용
 */
var printTable = function(list, target, templateObject) {
	var template = Handlebars.compile(templateObject.html());

	var html = template(list);
	target.empty(); // 기존 데이터 삭제
	target.append(html);
}





