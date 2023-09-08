
$(document).ready(function(){
	
	// 초기 작업
	init();
	
	/* 검색 조건 기간 설정 */
	$(".period").on("click", function(){
		var period = $(this).text();
		
		// 적용되어 있던 색상 초기화
		$(".period").css("background-color", "white");
		$(".period").css("color", "black");
		// 클릭한 기간에 색상 적용
		$(this).css("background-color", "#3CA0EF");
		$(this).css("color", "white");
		// value 변경
		$("#period").val(period);
		
		var searchDate = "";
		
		var index = $(".period").index($(this));
		if(index == 0){
			$("#searchDateDay").show();
			$("#searchDateMonth").hide();
			$("#searchDateYear").hide();
			searchDate = $("#searchDateDay").val();
			
		} else if(index == 1){
			$("#searchDateDay").hide();
			$("#searchDateMonth").show();
			$("#searchDateYear").hide();
			searchDate = $("#searchDateMonth").val();
			
		} else {
			$("#searchDateDay").hide();
			$("#searchDateMonth").hide();
			$("#searchDateYear").show();
			searchDate = $("#searchDateYear").val();
		}
		
		// 차트와 테이블 재생성
		drawChartTable(searchDate);
	});
	
	/* 상세 기간 설정 후 변경 버튼 클릭 시 */
	$("#btn_date").on("click", function(){
		var period = $("#period").val();
		var searchDate = "";
		
		if(period =="일간"){
			searchDate = $("#searchDateDay").val();
		} else if(period == "월간"){
			searchDate = $("#searchDateMonth").val();
		} else {
			searchDate = $("#searchDateYear").val();
		}
		
		// 유효성 검사
		if(searchDate == null || searchDate == ""){
			alert("상세 기간을 설정해주세요.");
		}
		
		drawChartTable(searchDate);
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
	
	/* 우수 회원 메일 보내기 클릭 이벤트 메소드 */
	$("#btn_send_mail").on("click", function(){
		
		if($(".mem_email").length<1){
			alert("메일을 보낼 회원이 존재하지 않습니다.");
			return;
		}
		
		
		var result = confirm("아래 회원들에게 메일을 보낼까요?");
		
		if(result){
			var tab="";
			$(".tableTab").each(function(i, e){
				if($(e).css("backgroundColor")=='#3CA0EF'){
					tab = $(e).text();
				}
			});
			
			if(tab == "수량 통계"){
				$("#amountForm").submit();
			} else{
				$("#priceForm").submit();
			}
			
		} else{}
	});

});

 


/* 초기 설정 */
function init(){
	
	// 기간 default : 전체
	$(".period").eq(0).css("background-color", "#3CA0EF");
	$(".period").eq(0).css("color", "white");
	
	// 통계 테이블 default: 수량통계
	$(".tableTab").eq(0).css("background-color", "#3CA0EF");
	$(".tableTab").eq(0).css("color", "white");
	$("#tableAmountDiv").show();
	$("#tablePriceDiv").hide();
	
	$("#searchDateMonth").hide();
	$("#searchDateYear").hide();
	
	var today = new Date();
	$("#searchDateYear").val(moment(today).format('YYYY'));
	$("#searchDateMonth").val(moment(today).format('YYYY-MM'));
	$("#searchDateDay").val(moment(today).format('YYYY-MM-DD'));
	
	var year = moment(today).format('YYYY');
	$("#searchDateYear").attr("max", year);
	$("#searchDateYear").attr("min", parseInt(year)-10);
	
	
	// 차트와 테이블 생성
	var searchDate = $("#searchDateDay").val();
	drawChartTable(searchDate);
}


/* 통계 그래프와 테이블 생성 */
function drawChartTable(searchDate){
	
	// 라이브러리 로드 및 차트 생성
	google.charts.load('current', {packages: ['corechart', 'bar']});
	google.charts.setOnLoadCallback(function(){
		drawChart($("#period").val(), searchDate);
	});
	
	// 통계 테이블 default: 수량통계
	$(".tableTab").css("background-color", "white");
	$(".tableTab").css("color", "black");
	$(".tableTab").eq(0).css("background-color", "#3CA0EF");
	$(".tableTab").eq(0).css("color", "white");
	
	$("#tableAmountDiv").show();
	$("#tablePriceDiv").hide();
	
}


/* 바 차트 그리기 */
function drawChart(period, searchDate) {
	var periodString = "";
	
	if(period == "일간"){
		periodString = "day";
		
	} else if(period == "월간"){
		periodString = "month";
		
	} else if(period == "연간"){
		periodString = "year";
		
	} else{}
	
	
	// 통계차트 구현
	memberAmount(periodString, searchDate);
	memberPrice(periodString, searchDate);
	
	// 통계테이블 구현 
	getAmountTable(periodString, searchDate);
	getPriceTable(periodString, searchDate);
	
}



/* 구매수량 순 회원 차트 JSON */
function memberAmount(period, searchDate){
	var amountJsonData = $.ajax({
		url : "/admin/chart/member/amount",
		type: 'post',
		dataType : "json",
		data: {
			"period" : period,
			"searchDate" : searchDate
		},
		async : false
	}).responseText; //제이슨파일을 text파일로 읽어들인다는 뜻
	
	var amountData = new google.visualization.DataTable(amountJsonData);
	drawChartAmount(amountData);
}

/* 구매금액 순 회원 차트 JSON  */
function memberPrice(period, searchDate){
	var priceJsonData = $.ajax({
		url : "/admin/chart/member/price",
		type: 'post',
		dataType : "json",
		data: {
			"period" : period,
			"searchDate" : searchDate
		},
		async : false
	}).responseText; //제이슨파일을 text파일로 읽어들인다는 뜻
	
	var priceData = new google.visualization.DataTable(priceJsonData);
	drawChartPrice(priceData);
}

/* 구매 수량 순 TOP 7 회원 차트 그리기 */
function drawChartAmount(amountData) {	

	var options = {
		title: '구매 수량 순 TOP 7 회원',
		width : 400,
		height : 250,
		colors : ['#9575cd'],
		hAxis: {
			title: '구매 수량',
			viewWindow: {
	              min:0
	         }
		},
		vAxis: {
			title: '회원 아이디'
		}
	};
	
	var chart = new google.visualization.BarChart(document.getElementById('chartAmountDiv'));
	chart.draw(amountData, options);
}

/* 구매 금액 순 TOP 7 회원 차트 그리기  */
function drawChartPrice(priceData) {

	var options = {
		title: '구매 금액 순 TOP 7 회원',
		width : 400,
		height : 250,
		colors : ['#A5D8FA'],
		hAxis: {
			title: '구매 금액',
			viewWindow: {
	              min:0
	         }
		},
		vAxis: { 
			title: '회원 아이디'
		}
	};
	
	var chart = new google.visualization.BarChart(document.getElementById('chartPriceDiv'));
	chart.draw(priceData, options);
}







/* 통계 테이블 - 구매 수량 */
function getAmountTable(period, searchDate){
	
	$.ajax({
		url: '/admin/chart/member/amountTable' ,
		type: 'post',
		data: {
			"period" : period,
			"searchDate" : searchDate
		},
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

/* 통계 테이블 - 구매 가격 */
function getPriceTable(period, searchDate){
	
	$.ajax({
		url: '/admin/chart/member/priceTable' ,
		type: 'post',
		data: {
			"period" : period,
			"searchDate" : searchDate
		},
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





