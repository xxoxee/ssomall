
$(document).ready(function(){
	
	// 초기 작업
	init();
	
	/* 기간(일간, 월간, 연간) 버튼 클릭 시, 그에 맞는 차트를 보여줌 */
	$(".period").on("click", function(){

		var period = $(this).text();
		if(period == '일간 매출'){
			google.charts.setOnLoadCallback(drawDayChart);

		} else if(period == '월간 매출'){
			google.charts.setOnLoadCallback(drawMonthChart);

		} else if(period == '연간 매출'){
			google.charts.setOnLoadCallback(drawYearChart);

		} else{}
		
		// 적용되어 있던 색상 초기화
		$(".period").css("background-color", "white");
		$(".period").css("color", "black");
		
		// 클릭한 기간에 색상 적용
		$(this).css("background-color", "#3CA0EF");
		$(this).css("color", "white");
		$("#term").val($(this).index());
	});

});

/* 초기 작업 */
function init(){
	
	// 라이브러리 로드
	google.charts.load('current', {
		'packages' : [ 'bar' ]
	});
	// 일간 차트 출력 및 버튼 클릭
	google.charts.setOnLoadCallback(drawDayChart);
	$(".period").eq(0).css("background-color", "#3CA0EF");
	$(".period").eq(0).css("color", "white");
	
}


/* 일간 통계 */
function drawDayChart() {
	
	var jsonData = $.ajax({
		url : "/admin/chart/date/day",
		dataType : "json",
		async : false
	}).responseText; //제이슨파일을 text파일로 읽어들인다는 뜻

	var data = new google.visualization.DataTable(jsonData);

	var materialOptions = {
		width : 1000,
		height : 400,
		colors : [ '#9575cd', '#A5D8FA' ],
		chart : {
			title : '일간 매출 현황',
			subtitle : '최근 10일 간의 통계를 보여줍니다.'
		},
		titleTextStyle: {
			color: 'black'
		},
		series : {
			0 : {
				axis : 'price'
			}, // Bind series 0 to an axis named 'distance'.
			1 : {
				axis : 'count'
			}
		// Bind series 1 to an axis named 'brightness'.
		},
		axes : {
			y : {
				price : {
					label : 'Total Price'
				}, // Left y-axis.
				count : {
					side : 'right',
					label : 'Total Amount'
				}
			// Right y-axis.
			}
		}
	};

	// 가격 컬럼에 숫자 포맷 적용
	var formatter = new google.visualization.NumberFormat({
		pattern : '###,###,###'
	});
	formatter.format(data, 1);

	drawMaterialChart(data, materialOptions);
}




/* 월간 통계 */
function drawMonthChart() {

	var jsonData = $.ajax({
		url : "/admin/chart/date/month",
		dataType : "json",
		async : false
	}).responseText; //제이슨파일을 text파일로 읽어들인다는 뜻

	var data = new google.visualization.DataTable(jsonData);

	var materialOptions = {
		width : 1000,
		height : 400,
		colors : [ '#9575cd', '#A5D8FA' ],
		chart : {
			title : '월간 매출 현황',
			subtitle : '최근 1년 간의 통계를 달 별로 보여줍니다.'
		},
		titleTextStyle: {
			color: 'black'
		},
		series : {
			0 : {
				axis : 'price'
			}, // Bind series 0 to an axis named 'distance'.
			1 : {
				axis : 'count'
			}
		// Bind series 1 to an axis named 'brightness'.
		},
		axes : {
			y : {
				price : {
					label : 'Total Price'
				}, // Left y-axis.
				count : {
					side : 'right',
					label : 'Total Amount'
				}
			// Right y-axis.
			}
		}
	};

	// 가격 컬럼에 숫자 포맷 적용
	var formatter = new google.visualization.NumberFormat({
		pattern : '###,###,###'
	});
	formatter.format(data, 1);
	drawMaterialChart(data, materialOptions);
}





/* 연간 통계 */
function drawYearChart() {

	var jsonData = $.ajax({
		url : "/admin/chart/date/year",
		dataType : "json",
		async : false
	}).responseText; //제이슨파일을 text파일로 읽어들인다는 뜻

	var data = new google.visualization.DataTable(jsonData);

	var materialOptions = {
		width : 800,
		height : 400,
		colors : [ '#9575cd', '#A5D8FA' ],
		chart : {
			title : '연간 매출 현황',
			subtitle : '최근 5년 간의 통계를 보여줍니다.'
		},
		titleTextStyle: {
			color: 'black'
		},
		series : {
			0 : {
				axis : 'price'
			}, // Bind series 0 to an axis named 'distance'.
			1 : {
				axis : 'count'
			}
		// Bind series 1 to an axis named 'brightness'.
		},
		axes : {
			y : {
				price : {
					label : 'Total Price'
				}, // Left y-axis.
				count : {
					side : 'right',
					label : 'Total Amount'
				}
			// Right y-axis.
			}
		}
	};

	// 가격 컬럼에 숫자 포맷 적용
	var formatter = new google.visualization.NumberFormat({
		pattern : '###,###,###'
	});
	formatter.format(data, 1);

	drawMaterialChart(data, materialOptions);
}



function drawMaterialChart(data, materialOptions) {
	var materialChart = new google.charts.Bar(document
			.getElementById('chartDiv'));
	materialChart.draw(data, google.charts.Bar
			.convertOptions(materialOptions));
}