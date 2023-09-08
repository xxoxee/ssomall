<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/views/include/plugin_js.jsp"%>
<%@include file="/WEB-INF/views/include/header.jsp"%>
<head>
<%-- 날짜변환 용 --%>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js"></script>
<%-- 클릭 이벤트 메소드 --%>
<script type="text/javascript" src="/js/board/list.js"></script>


<%-- 템플릿: 게시판 글 목록 (공지사항) --%>
<script id="templateNotice" type="text/x-handlebars-template">
	{{#each .}}
		<tr class="notices">
			<td class="col-md-1">{{brd_num}}</td>
			<td class="col-md-3" style="text-align: left;">
				<a onclick="showModal({{brd_num}}, {{brd_group}})"><b>{{brd_title}}</b></a>
			</td>
			<td class="col-md-1">{{mem_id}}</td>
			<td class="col-md-1">{{prettifyDate brd_date_reg}}</td>
			<td class="col-md-1">{{brd_count}}</td>
		</tr>
	{{/each}}
</script>

<%-- 템플릿: 게시판 글 목록 (게시글) --%>
<script id="template" type="text/x-handlebars-template">
	{{#each .}}
		<tr class="boards">
			<td class="col-md-1">{{brd_num}}</td>

			<td class="col-md-3" style="text-align: left;">
				{{#ifCond brd_is_del}}
					{{{tabTitle brd_title brd_level brd_is_del}}}
				{{else}}
					<a class="color-black" onclick="showModal({{brd_num}}, {{brd_group}}, {{brd_order}}, {{brd_level}})">{{{tabTitle brd_title brd_level}}}</a>
				{{/ifCond}}
				<input type="hidden" class="id" val="{{mem_id}}" />
				<input type="hidden" class="brd_num" val="{{brd_num}}" />
			</td>
			<td class="col-md-1">{{mem_id}}</td>
			<td class="col-md-1">{{prettifyDate brd_date_reg}}</td>
			<td class="col-md-1">{{brd_count}}</td>
		</tr>
	{{/each}}
</script>


<%-- 핸들바 사용자 정의 헬퍼 --%>
<script>
	$(document).ready(function(){
		
		/* 
		 * 사용자 정의 헬퍼(prettifyDate)
		 * : 매개변수로 받은 timeValue를 원하는 날짜 형태로 바꿔준다.
		 */ 
		Handlebars.registerHelper("prettifyDate", function(timeValue) {
			var dateObj = new Date(timeValue);
			var year = dateObj.getFullYear();
			var month = dateObj.getMonth() + 1;
			var date = dateObj.getDate();
			var hour = dateObj.getHours();
			var min = dateObj.getMinutes();
			var sec = dateObj.getSeconds();
			
			//return year + "/" + month + "/" + date + " " + hour + ":" + min + ":" + sec;
			return moment(dateObj).format('YYYY-MM-DD HH:mm:ss');
		});

		/* 
		 * 사용자 정의 헬퍼(tabTitle)
		 * :계층구조만큼 제목 들여쓰기
		 */ 
		Handlebars.registerHelper("tabTitle", function(title, level, isDel) {

			var tappedTitle = "";

			if(level > 1){
				for(var i=1; i<level; i++){
					tappedTitle += "&emsp; ";
				}
			}

			if(isDel == 'Y'){
				return tappedTitle + "<span style='color:#BBBBBB;'>" + "ㄴ삭제된 글입니다." + "</span>";
			}
			
			return tappedTitle + "ㄴ" + title;
		});

		/* 
		 * 사용자 정의 헬퍼(ifCond)
		 * : 해당 게시물이 삭제된 게시물인지 확인
		 *	 삭제된 게시물이면 'Y', 아니면 'N'
		 */ 
		Handlebars.registerHelper("ifCond", function(isDel, options) {

			if(isDel == 'Y'){
				return options.fn(this);
			} 
			return options.inverse(this);
		});
	});
</script>
	
	
<%-- 버튼 클릭 --%>
<script>
	$(document).ready(function(){
		checkTerm();
		
		/* 모달 창 - 게시글 댓글 달기 클릭 이벤트 */
		$("#btn_modal_reply").on("click", function(){
	
			// 읽기 전용 모달 -> 댓글 작성 모달 로 변환
			$("#brd_num").val("");
			$("#title_modal").val("");
			$("#content_modal").val("");
			$("#writer_id_modal").val("${sessionScope.user.mem_id}");
			
			activeButton($(".writeBoard"));
			readonlyText(false);
			$("#modal").show();
			
		});
		
	});
	

	/* 
	 * 모달 창을 띄움
	 * 
	 * 사용자가 해당 글의 작성자인 경우, 수정/삭제 모달 창
	 * 사용자가 해당 글의 작성자가 아닌 경우, 읽기전용 모달 창
	 */
	function showModal(brd_num, group, order, level){
		
		// 모달에 존재하던 이전 내용 초기화
		initModalValue();
		
		// 글쓰기 버튼을 클릭한 경우, DB작업없이 모달만 띄워줌
		if(brd_num == null || brd_num == ""){
			activeButton($(".writeBoard"));
			$("#writer_id_modal").val("${sessionScope.user.mem_id}");
			readonlyText(false);
			$("#modal").show();
			return;
		}
		
		$.ajax({
			url: "/board/read/"+brd_num,
			type: "post",
			dataType: "json",
			success: function(data){
				
				if(data.mem_id == "${sessionScope.user.mem_id}"){
					// 사용자 = 작성자 -> 수정 삭제 버튼 활성화
					activeButton($(".updateBoard"), $(".replyBoard"));
					readonlyText(false);
				} else{
					// 사용자 != 작성자 -> 답변달기 버튼 활성화 + 읽기 전용
					activeButton($(".replyBoard"));
					readonlyText(true);
				}

				if(group == 0){
					// 공지사항은 버튼 활성화 없음
					activeButton();
					readonlyText(true);
				}
				
				$("#brd_num").val(data.brd_num);
				$("#title_modal").val(data.brd_title);
				$("#writer_id_modal").val(data.mem_id);
				$("#content_modal").val(data.brd_content);

				$("#group").val(group);
				$("#order").val(order);
				$("#level").val(level);
				
				$("#modal").show();
			}
		});
	}

	/* 선택했던 기간에 색상 적용 */
	function checkTerm(){
		$(".btnDate").eq(${searchDate.term}).css("background-color", "#3C8DBC");
		$(".btnDate").eq(${searchDate.term}).css("color", "white");
	}
</script>

<%-- 스타일 --%>
<style>
	.color-black{
		color: black;
	}

	table .search{
	  width: 100%;
	  border-top: 1px solid lightgrey;
	  border-bottom: 1px solid lightgrey;
	  border-collapse: collapse;
	}
	th, tr, td .search{
	  border-bottom: 1px solid lightgrey;
	  padding: 10px;
	}
	.btnDate{
		border: 1px solid grey;
		padding: 5px 10px;
		color: black;
	}
  
	#modal{
		display: none;
		position: fixed; 
		width:100%;
		height:100%;
		z-index:100;
		background-color: rgb(0,0,0); /* Fallback color */
        background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
	}
	#modal .modal_content{
		width:600px;
		margin:200px auto;
		padding:20px 10px;
		background:#fff;
		border:1px solid #888;
	} 
	#modal .modal-body{
		height:150px;
	}
	#modal .modal_close{
		color: #aaa;
        float: right;
        font-size:20px;
        font-weight: bold;
	
	}
    #modal .modal_close:hover,
    #modal .modal_close:focus {
        color: black;
        text-decoration: none;
        cursor: pointer;
    }
    
</style>
</head>


<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<!-- Main Header -->
		<%@include file="/WEB-INF/views/include/main_header.jsp"%>

		<!-- Left side column. contains the logo and sidebar -->
		<%@include file="/WEB-INF/views/include/left.jsp"%>

		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>
					상품 Q&A <small>Q&A List</small>
				</h1>
				<ol class="breadcrumb">
					<li><a href="#"><i class="fa fa-dashboard"></i> 게시판</a></li>
					<li class="active">상품 Q&A</li>
				</ol>
			</section>

			<%-- MAIN CONTENT --%>
			<section class="content container-fluid">
				<div class="row">
					<div class="box" style="border: none;">
						<div class="box-header">
							<%-- 검색 조건 설정 및 페이지 이동에도 해당 값 유지 --%>
							<div style="margin-left:30px;">
								<label>>&nbsp;&nbsp;게시글 검색(공지 제외)</label><br>
								<table class="search" style="display:inline-block;">
									<tr class="search" style="height:70px;" >
										<th rowspan="2">기간(작성일)</th>
										<td rowspan="2">
											<div style="margin:10px 0px;">
												<a class="btnDate"><span>전체</span></a>
												<a class="btnDate"><span>오늘</span></a>
												<a class="btnDate"><span>어제</span></a>
												<a class="btnDate"><span>3일</span></a>
												<a class="btnDate"><span>7일</span></a>
												<a class="btnDate"><span>15일</span></a>
												<a class="btnDate"><span>1개월</span></a>
												<a class="btnDate"><span>3개월</span></a>
												<a class="btnDate"><span>6개월</span></a>
											</div>
											<div style="margin-bottom:5px;">
												<input id="term" name="term" type="hidden" value="${searchDate.term}" />
												<input id="date_start" name="startDate" type="date" value="${searchDate.startDate}" />
												&nbsp;~&nbsp;
												<input id="date_end" name="endDate" type="date" value="${searchDate.endDate}" />
											</div>
										</td>
									</tr>
									<tr></tr>
									<tr class="search">
										<th> 검색 키워드 </th>
										<td style="padding: 5px 0px;" >
											<select id="searchType" name="searchType" style="width:130px; height:26px;">
												<option value="null" <c:out value="${cri.searchType == null?'selected':''}"/>>검색조건 선택</option>
												<option value="title" <c:out value="${cri.searchType eq 'title'?'selected':''}"/>>제목</option>
												<option value="content" <c:out value="${cri.searchType eq 'content'?'selected':''}"/>>내용</option>
												<option value="writer" <c:out value="${cri.searchType eq 'writer'?'selected':''}"/>>작성자</option>
												<option value="titleContent" <c:out value="${cri.searchType eq 'titleContent'?'selected':''}"/>>제목+내용</option>
												<option value="all" <c:out value="${cri.searchType eq 'all'?'selected':''}"/>>전체</option>
											</select>
											<input type="text" name='keyword' id="keyword" 
													style="width:250px; padding-left:5px;" value='${cri.keyword}' />
											<button id="btn_search" class="btn btn-default">검색</button>
										</td>
									</tr>	
								</table>
									
								<div style="float: right; margin-top:80px; ">
									<button type="button" class="btn btn-info" onclick="showModal();" 
										style="margin: 0px 5px;">글 쓰기</button>
								</div>
							</div>
						</div>
					
						<%-- 공지사항/게시글 목록 --%>
						<div class="box-body">
							<div class="boardList">
								<table id="boardTable" class="table table-striped text-center">
									<tbody id="boardTableBody">
										<tr>
											<th>글번호</th>
											<th style="text-align: left;">제목</th>
											<th>작성자</th>
											<th>작성일</th>
											<th>조회 수</th>
										</tr>
										<tr class="noBoards"></tr>
									</tbody>
								</table>
							</div>
							
							<%-- Modal : 게시글 읽기/작성/수정 팝업 창 --%>
							<div id="modal" class="modal">
								<div class="modal_content">
									<div class="modal-header">
										<span class="modal_close">&times;</span>
										<input id="title_modal" placeholder="제목을 입력해주세요." style="width:90%; border: none;">
										<input id="brd_num" type="hidden">
										<input id="group" type="hidden">
										<input id="order" type="hidden">
										<input id="level" type="hidden">
									</div>
									<div class="modal-body">
										<textarea id="content_modal" placeholder="내용을 입력하세요." style="width:100%; border:none;" rows="5"></textarea>
									</div>
									<div class="modal-footer">
										<div class="writerDiv" style="display: inline-block; float:left;">
											<span>작성자: <input id="writer_id_modal" style="border:none;" readonly /></span>
									 	</div>
									 	<div class="updateBoard" style="display: inline-block;">
								        	<button type="button" class="btn btn-info" id="btn_modal_modify">MODIFY</button>
								        	<button type="button" class="btn btn-danger" id="btn_modal_delete">DELETE</button>
							        	</div>
							        	<div class="replyBoard" style="display: inline-block;">
							        		<button type="button" class="btn btn-success" id="btn_modal_reply">REPLY</button>
							        	</div>
							        	<div class="writeBoard" style="display: inline-block;">
							        		<button type="button" class="btn btn-info" id="btn_modal_write">WRITE</button>
							        	</div>
							      	</div>
								</div>
							</div>
						</div>
						<!-- /.box-body -->

						<%-- 페이징 기능 --%>
						<div class="box-footer">
							<div class="text-center">
								<ul id="pagination" class="pagination">
									<c:if test="${pm.prev}">
										<li><a href="/board/list${pm.makeSearch(pm.startPage-1)}">&laquo;</a>
										</li>
									</c:if>

									<c:forEach begin="${pm.startPage}" end="${pm.endPage}"
										var="idx">
										<li <c:out value="${pm.cri.page == idx?'class =active':''}"/>>
											<a href="${idx}">${idx}</a>
										</li>
									</c:forEach>

									<c:if test="${pm.next && pm.endPage > 0}">
										<li><a href="/board/list${pm.makeSearch(pm.endPage +1)}">&raquo;</a>
										</li>
									</c:if>
								</ul>
							</div>
						</div>
						<!-- /.box-footer-->
					</div>
				</div>
					<!--/.col (left) -->
			</section>
		</div>
		<!-- /.content -->
		<!-- /.content-wrapper -->

		<!-- Main Footer -->
		<%@include file="/WEB-INF/views/include/footer.jsp"%>

		<!-- Control Sidebar -->
		<aside class="control-sidebar control-sidebar-dark">
			<!-- Create the tabs -->
			<ul class="nav nav-tabs nav-justified control-sidebar-tabs">
				<li class="active"><a href="#control-sidebar-home-tab"
					data-toggle="tab"><i class="fa fa-home"></i></a></li>
				<li><a href="#control-sidebar-settings-tab" data-toggle="tab"><i
						class="fa fa-gears"></i></a></li>
			</ul>
			<!-- Tab panes -->
			<div class="tab-content">
				<!-- Home tab content -->
				<div class="tab-pane active" id="control-sidebar-home-tab">
					<h3 class="control-sidebar-heading">Recent Activity</h3>
					<ul class="control-sidebar-menu">
						<li><a href="javascript:;"> <i
								class="menu-icon fa fa-birthday-cake bg-red"></i>

								<div class="menu-info">
									<h4 class="control-sidebar-subheading">Langdon's Birthday</h4>

									<p>Will be 23 on April 24th</p>
								</div>
						</a></li>
					</ul>
					<!-- /.control-sidebar-menu -->

					<h3 class="control-sidebar-heading">Tasks Progress</h3>
					<ul class="control-sidebar-menu">
						<li><a href="javascript:;">
								<h4 class="control-sidebar-subheading">
									Custom Template Design <span class="pull-right-container">
										<span class="label label-danger pull-right">70%</span>
									</span>
								</h4>

								<div class="progress progress-xxs">
									<div class="progress-bar progress-bar-danger"
										style="width: 70%"></div>
								</div>
						</a></li>
					</ul>
					<!-- /.control-sidebar-menu -->

				</div>
				<!-- /.tab-pane -->
				<!-- Stats tab content -->
				<div class="tab-pane" id="control-sidebar-stats-tab">Stats Tab
					Content</div>
				<!-- /.tab-pane -->
				<!-- Settings tab content -->
				<div class="tab-pane" id="control-sidebar-settings-tab">
					<form method="post">
						<h3 class="control-sidebar-heading">General Settings</h3>

						<div class="form-group">
							<label class="control-sidebar-subheading"> Report panel
								usage <input type="checkbox" class="pull-right" checked>
							</label>

							<p>Some information about this general settings option</p>
						</div>
						<!-- /.form-group -->
					</form>
				</div>
				<!-- /.tab-pane -->
			</div>
		</aside>
		<!-- /.control-sidebar -->
		<!-- Add the sidebar's background. This div must be placed immediately after the control sidebar -->
		<div class="control-sidebar-bg"></div>
	</div>
	<!-- ./wrapper -->

</body>
</html>