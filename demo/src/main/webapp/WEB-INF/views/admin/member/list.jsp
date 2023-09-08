<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/views/include/plugin_js.jsp"%>
<%@include file="/WEB-INF/views/include/header.jsp"%>
<head>
<%-- 체크박스/ 버튼 클릭 이벤트 메소드 --%>
<script type="text/javascript" src="/js/admin/member/list.js"></script>

<%-- 검색 버튼 클릭 --%>
<script>
	/* 검색 버튼 클릭 시 */
	$(document).ready(function(){
		$("#btn_search").on("click", function(){
	
			var accept = $("input[name='isAccept']:checked").val();
			var searchType = $("select[name='searchType'] option:selected");
			var keyword = $("#keyword");
			
			location.href = "/admin/member/list${pm.makeQuery(1)}"
						  + "&searchType=" + searchType.val()
						  + "&keyword=" + keyword.val()
						  + "&accept=" + accept;
			
		});
	});
</script>


<%-- 메시지 처리 --%>
<script>
	if ("${msg}" == "SEND_EMAIL_SUCCESS") {
		alert("메일을 성공적으로 보냈습니다");
		
	} else if ("${msg}" == "SEND_EMAIL_FAIL") {
		alert("메일을 보내는 데 실패하였습니다.");
		
	} 
</script>

<%-- 스타일 --%>
<style>
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
</style>
</head>


<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<!-- Main Header -->
		<%@include file="/WEB-INF/views/include/main_header_admin.jsp"%>

		<!-- Left side column. contains the logo and sidebar -->
		<%@include file="/WEB-INF/views/include/left_admin.jsp"%>

		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>
					회원 목록 <small>Member List</small>
				</h1>
				<ol class="breadcrumb">
					<li><a href="#"><i class="fa fa-dashboard"></i> 회원 관리</a></li>
					<li class="active">회원 목록</li>
				</ol>
			</section>

			<%-- MAIN CONTENT --%>
			<section class="content container-fluid">
				<div class="row">
					<div class="box" style="border: none;">
						<div class="box-header">
							<%-- 검색 조건 설정 및 페이지 이동에도 해당 값 유지 --%>
							<div style="margin-left:30px;">
								<label>>&nbsp;검색</label><br>
								<table class="search" style="display:inline-block;">
									<tr class="search">
										<th style="background-color:#EEEEEE; padding-right:40px;">메일 수신 동의 여부</th>
										<td style="padding-left:20px;">
											<input type="radio" name="isAccept" value="ALL" 
												<c:out value="${accept eq 'ALL'?'checked':''}"/>>전체&nbsp;&nbsp;&nbsp;
											<input type="radio" name="isAccept" value="Y"
												<c:out value="${accept eq 'Y'?'checked':''}"/>>수신 동의&nbsp;&nbsp;&nbsp;
											<input type="radio" name="isAccept" value="N"
												<c:out value="${accept eq 'N'?'checked':''}"/>>수신 거절
										</td>
									</tr>
									<tr class="search">
										<th style="background-color:#EEEEEE;"> 검색 키워드 </th>
										<td style="padding:5px 20px;">
											<select name="searchType" style="width:130px; height:26px;">
												<option value="null" <c:out value="${cri.searchType == null?'selected':''}"/>>검색조건 선택</option>
												<option value="name" <c:out value="${cri.searchType eq 'name'?'selected':''}"/>>이름</option>
												<option value="id" <c:out value="${cri.searchType eq 'id'?'selected':''}"/>>아이디</option>
												<option value="phone" <c:out value="${cri.searchType eq 'phone'?'selected':''}"/>>핸드폰</option>
												<option value="email" <c:out value="${cri.searchType eq 'email'?'selected':''}"/>>이메일</option>
												<option value="address" <c:out value="${cri.searchType eq 'address'?'selected':''}"/>>주소</option>
												<option value="all" <c:out value="${cri.searchType eq 'all'?'selected':''}"/>>전체</option>
											</select>
											<input type="text" name='keyword' id="keyword" 
													style="width:250px; padding-left:5px;" value='${cri.keyword}' />
											<button id="btn_search" class="btn btn-default">검색</button>
										</td>
									</tr>	
								</table>
									
								<div style="float: right; margin-top:80px; ">
									<button type="button" id="btn_email_all" class="btn btn-warning" 
										style="margin: 0px 5px;">전체 회원 메일 보내기</button>
									<button type="button" id="btn_email_check" class="btn btn-primary" 
										>선택 회원 메일 보내기</button>
								</div>
							</div>
						</div>
					
							<%-- 회원 목록 --%>
						<div class="box-body">
							<form id="sendMailAllForm" action="/email/sendAll" method="post"></form>
							<form id="memberForm" action="/email/sendCheck" method="post">
								<table class="table table-striped text-center">
									<tr>
										<th><input type="checkbox" id="checkAll" /></th>
										<th>아이디</th>
										<th>이름</th>
										<th>닉네임</th>
										<th>수신 동의</th>
										<th>이메일</th>
										<th>포인트</th>
										<th>주문 횟수</th>
										<th>주문 총액</th>
										<th>가입일</th>
									</tr>
									
									<%-- 상품 리스트 출력 --%>
									<c:if test="${empty memberList}">
										<tr>
											<td colspan="12"> 
												<p style="padding:50px 0px; text-align: center;">가입한 사용자가 존재하지 않습니다.</p>
											</td>
										<tr>
									</c:if>
									
									<c:forEach items="${memberList}" var="member">
										<tr>
											<td><input type="checkbox" name="check" class="check" value="${member.mem_id}"></td>
											<td class="col-md-1"><a href="/admin/member/read${pm.makeSearch(pm.cri.page)}&accept=${accept}&id=${member.mem_id}">${member.mem_id}</a>
												<input type="hidden" name="mem_id" value="${member.mem_id}" />
											</td>
											<td class="col-md-1">${member.mem_name}</td>
											<td class="col-md-1">${member.mem_nick}</td>
											<td class="col-md-1">${member.mem_accept_e}
												<input type="hidden" id="accept_${member.mem_id}" value="${member.mem_accept_e}" />
											</td>
											<td class="col-md-2">${member.mem_email}
												<input type="hidden" name="mem_email" value="${member.mem_email}" />
											</td>
											<td class="col-md-1">${member.mem_point}</td>
											<td class="col-md-1">${member.mem_total_order}</td>
											<td class="col-md-1">${member.mem_total_price}</td>
											<td class="col-md-2">
												<fmt:formatDate value="${member.mem_date_sub}" pattern="yyyy/MM/dd" /> 
											</td>
										</tr>
									</c:forEach>
								</table>
							</form>
						</div>
						<!-- /.box-body -->

						<%-- 페이징 기능 --%>
						<div class="box-footer">
							<div class="text-center">
								<ul class="pagination">
									<c:if test="${pm.prev}">
										<li><a href="/admin/member/list${pm.makeSearch(pm.startPage-1)}&accept=${accept}">&laquo;</a>
										</li>
									</c:if>

									<c:forEach begin="${pm.startPage}" end="${pm.endPage}"
										var="idx">
										<li <c:out value="${pm.cri.page == idx?'class =active':''}"/>>
											<a href="/admin/member/list${pm.makeSearch(idx)}&accept=${accept}">${idx}</a>
										</li>
									</c:forEach>

									<c:if test="${pm.next && pm.endPage > 0}">
										<li><a href="/admin/member/list${pm.makeSearch(pm.endPage +1)}&accept=${accept}">&raquo;</a>
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