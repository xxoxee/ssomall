$(document).ready(function(){
	
	getPage("/admin/board/list/1");
	
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
	
	
	/* 검색 버튼 클릭 시 */
	$("#btn_search").on("click", function(){
		var term = $("#term");
		var startDate = $("#date_start");
		var endDate = $("#date_end");
		var searchType = $("#searchType");
		var keyword = $("#keyword");
		
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
			if(startDate.val() == null || startDate.val() == "" || endDate.val() == null || endDate.val() == ""){
			
				alert("기간(작성일)을 선택해주세요.");
				startDate.focus();
				return;
			}
			
		} else if(searchType.val() == "null" && keyword.val()!=""){
			// 키워드는 있는데 검색조건이 없는 경우
			alert("검색조건을 선택하세요.")
			searchType.focus();
			
		} else if(searchType.val() != "null" && keyword.val()==""){
			// 검색조건은 있는데 키워드가 없는 경우
			alert("검색어를 입력하세요.")
			keyword.focus();
			
		} else{
			getPage("/admin/board/list/1");
		}
		
		
	});
	
	
	/* 상품 후기 하단 페이지 부분 클릭 시 - 페이지 이동 */
	$(".pagination").on("click", "li a", function(event) {

		event.preventDefault();
		
		replyPage = $(this).attr("href");
		getPage("/admin/board/list/" + replyPage);

	});

	
	/* 모달 창 닫기 */
	$(".modal_close").on("click", function(){
		var page = $("#pagination").find(".active a").attr("href");
		
		$("#modal").hide();
		getPage("/admin/board/list/"+page);
	});
	
	
	/* 모달 창 - 게시글(원글/댓글) 쓰기*/
	$("#btn_modal_write").on("click", function(){
		
		var title = $("#title_modal").val();
		var content = $("#content_modal").val();
		var mem_id = $("#writer_id_modal").val();

		var group = $("#group").val();
		var order = $("#order").val();
		var level = $("#level").val();

		var page = $("#pagination").find(".active a").attr("href");
		
		// 유효성 검사
		if(title==null || title==""){
			alert("제목을 입력해주세요.");
			$("#title_modal").focus();
			return;
			
		} else if(content == null || content == ""){
			alert("내용을 입력해주세요.");
			$("#content_modal").focus();
			return;
		}

		if(group=="" || group==null){
			writeBoard(page, title, content, mem_id);
			
		} else {
			order = parseInt(order) + parseInt(1);
			level = parseInt(level) + parseInt(1);
			replyBoard(page, title, content, mem_id, group, order, level);
		}
	});
	
	
	
	/* 모달 창 - 게시글 수정 */
	$("#btn_modal_modify").on("click", function(){
		var title = $("#title_modal").val();
		var content = $("#content_modal").val();
		var brd_num = $("#brd_num").val();
		
		var page = $("#pagination").find(".active a").attr("href");
		
		// 유효성 검사
		if(title==null || title==""){
			alert("제목을 입력해주세요.");
			$("#title_modal").focus();
			return;
			
		} else if(content == null || content == ""){
			alert("내용을 입력해주세요.");
			$("#content_modal").focus();
			return;
		}
		
		$.ajax({
			url:"/board/modify",
			type:"post",
			data: {
				"brd_title": title,
				"brd_content" : content,
				"brd_num" : brd_num
			},
			dataType: "text",
			success: function(msg){
				alert(msg);
				$("#modal").hide();
				
				if(page==null){
					getPage("/admin/board/list/1");
				} else{
					getPage("/admin/board/list/" + page);
				}
			}
		});
		
	});

	
	/* 모달 창 - 게시글 삭제 */
	$("#btn_modal_delete").on("click", function(){
		
		var brd_num = $("#brd_num").val();
		var page = $("#pagination").find(".active a").attr("href");
		
		
		var result = confirm("이 글을 삭제하시겠습니까?");
		
		if(result){
			$.ajax({
				url:"/admin/board/delete/"+brd_num,
				headers: { 
				      "Content-Type": "application/json",
				      "X-HTTP-Method-Override": "POST" },
				type:"post",
				dataType: "text",
				success: function(msg){
					alert(msg);
					$("#modal").hide();
					
					if(page==null){
						getPage("/admin/board/list/1");
					} else{
						getPage("/admin/board/list/" + page);
					}
				}
			});
		} else {}
	});
});


/* 
 * 페이지 이동에 따른 게시판 이동
 * 게시판 리스트 템플릿 적용 함수 
 * 게시판리스트를 DB에서 가져와서 뿌려주는 작업
 */
function getPage(pageInfo) {

	var searchType = $("#searchType").val();
	var keyword = $("#keyword").val();
	var term = $("#term").val();
	var startDate = $("#date_start").val();
	var endDate = $("#date_end").val();
	
	var data = {
		searchType : searchType,
		keyword: keyword,
		term : term,
		startDate : startDate,
		endDate : endDate
	}
	
	$.ajax({
		url : pageInfo,
		type: 'post',
		data: data,
		dataType: 'json',
		success: function(data){
			// 공지사항이 존재
			if(data.noticeList.length>0){
				printNotice(data.noticeList, $("#boardTableBody"), $('#templateNotice'))
			}
			
			// 게시글이 존재
			if(data.list.length>0){
				// 템플릿 적용
				printData(data.list, $("#boardTableBody"), $('#template'));
				printPaging(data.pageMaker, $(".pagination"));
				
			// 상품후기가 존재하지 않음
			} else{
				printNoData($("#boardTableBody"),$(".pagination"));
			}
			
		}
	});

}

/*
 * printNotice()
 * : 공지사항 리스트(최대 5개)를 보여주는 템플릿 적용
 */
var printNotice = function(list, target, templateObject) {
	var template = Handlebars.compile(templateObject.html());

	var html = template(list);
	$(".notices").remove(); // 기존 데이터 삭제
	target.append(html);
}

/*
 * printNoData()
 * : 상품후기 리스트가 존재하지 않을 때 보여줌
 */
var printNoData = function(target, pagingTarget) {
	$(".boards").remove();
	$(".noBoards").remove();
	
	var html = "<tr class='noBoards'>"
					+"<td colspan='6'>" 
					+"<p style='padding:50px 0px; text-align: center;'>게시글이 존재하지 않습니다.</p>"
					+"</td>"
				+"</tr>";
	
	target.append(html);
	pagingTarget.html("");
	
}

/*
 * printData()
 * : 상품후기 리스트를 보여주는 템플릿 적용
 */
var printData = function(list, target, templateObject) {
	var template = Handlebars.compile(templateObject.html());

	var html = template(list);
	$(".boards").remove(); // 기존 데이터 삭제
	$(".noBoards").remove();
	target.append(html);
}


/*
 * printPaging()
 * : 게시판 리스트의 하단 페이지 부분 작업
 */
var printPaging = function(pageMaker, target) {

	var str = "";

	target.empty(); // 기존 페이지 부분 삭제
	
	// 이전
	if (pageMaker.prev) {
		str += "<li><a href='" + (pageMaker.startPage - 1)
				+ "'> << </a></li>";
	}
	// 페이지 인덱스
	for (var i = pageMaker.startPage, len = pageMaker.endPage; i <= len; i++) {
		var strClass = pageMaker.cri.page == i ? 'class=active' : '';
		str += "<li "+strClass+"><a href='"+i+"'>" + i + "</a></li>";
	}
	// 다음
	if (pageMaker.next) {
		str += "<li><a href='" + (pageMaker.endPage + 1)
				+ "'> >> </a></li>";
	}

	target.html(str);
};

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

/* 모달 창에 상황에 맞는 버튼 활성화 */
function activeButton(...targets){

	$(".updateBoard").hide();
	$(".replyBoard").hide();
	$(".writeBoard").hide();
	
	if(targets.length > 0){
		for(var i=0; i<targets.length; i++){
			targets[i].show();
		}
	}
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

/*
 * 게시글(원글) 쓰기 ajax 작업
 */
function writeBoard(page, title, content, mem_id){
	
	$.ajax({
		url:"/admin/board/write",
		type:"post",
		data: {
			"brd_title": title,
			"brd_content" : content,
			"mem_id" : mem_id,
			"brd_order" : 1,
			"brd_level" : 1
		},
		dataType: "text",
		success: function(msg){
			alert(msg);
			if(page==null){
				$("#modal").hide();
				getPage("/admin/board/list/1");
			} else{
				$("#modal").hide();
				getPage("/admin/board/list/" + page);
			}
		}
	});
}

/*
 * 게시글(댓글) 쓰기 ajax 작업
 */
function replyBoard(page, title, content, mem_id, group, order, level){
	
	$.ajax({
		url:"/admin/board/write",
		type:"post",
		data: {
			"brd_title": title,
			"brd_content" : content,
			"mem_id" : mem_id,
			"brd_group" : group,	// 부모의 그룹과 일치
			"brd_order" : order,	// 부모의 순서 +1
			"brd_level" : level	// 부모의 계층 +1
		},
		dataType: "text",
		success: function(msg){
			alert(msg);
			if(page==null){
				$("#modal").hide();
				getPage("/admin/board/list/1");
			} else{
				$("#modal").hide();
				getPage("/admin/board/list/" + page);
			}
		}
	});
}



