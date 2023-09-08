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
<%-- 체크박스 클릭 이벤트 메소드 --%>
<script type="text/javascript" src="/js/admin/order/list.js"></script>
<%-- 버튼 클릭 이벤트 메소드 --%>
<script>
	$(document).ready(function(){

		checkStatus();
		
		/* 단일 상품의 주문 현황을 수정 */
		$("button[name='btn_update_status']").on("click", function(){
			var pdt_num = $(this).val();
			var odr_code = $("#odr_code_"+pdt_num).val();
			var odr_status = $("#odr_status_"+pdt_num+" option:selected").val();
			
			$.ajax({
				url : "/admin/order/update/"+odr_code,
				type : 'post',
				dataType : 'text',
				data : {
					"pdt_num" : pdt_num,
					"odr_status" : odr_status
				},
				success : function(data) {
					alert("주문 현황이 수정되었습니다.");
					movePage();
					
				}
			});
		});

		
		/* 선택 상품 주문현황 수정 버튼 클릭 시 */
		$("#btn_update_check").on("click", function() {

			// 체크여부 유효성 검사
			if ($("input[name='checkProduct']:checked").length == 0) {
				alert("주문현황을 변경할 상품을 선택해주세요.");
				return;
			}
			
			var statusArr = [];
			var codeArr = [];
			var pdtArr = [];

			// 체크 된 상품의 value(cart_code)를 가져옴
			$("input[name='checkProduct']:checked").each(function(index, element) {
				var pdt_num = $(element).val();
				var odr_code = $("#odr_code_"+pdt_num).val();
				var odr_status = $("#odr_status_"+pdt_num+" option:selected").val();
				
				statusArr.push(odr_status);
				codeArr.push(odr_code);
				pdtArr.push(pdt_num);
			});

			$.ajax({
				url : '/admin/order/updateCheck',
				type : 'post',
				dataType : 'text',
				data : {
					"statusArr" : statusArr,
					"codeArr" : codeArr,
					"pdtArr" : pdtArr
				},
				success : function(data) {
					alert("선택한 주문 현황이 모두 수정되었습니다.");
					movePage();
				}
			});
			
		});

		
	});
	
</script>
<script>
	/* jsp로 넘어온 주문 현황 체크박스 출력 */
	var checkStatus = function(){
		if(${check_status}.length==4){
			// check All
			$("#checkAllStatus").prop('checked', true);	
			$("input[name='check_status']").prop('checked', true);
			return;
		}

		for(var i=0; i<${check_status}.length; i++){
			$("input[name='check_status']").each(function(index, element){
				if($(element).val()==${check_status}[i]){
					$(element).prop('checked', true);
				}	
			});
		}
	};

	/* 페이지 처리 - 특정 페이지로 이동 */
	var movePage = function(page){
		var form = $("#hiddenForm");
		var url = "";
		if(page != null && page != ""){
			url = "/admin/order/list" + page;
			
		} else {
			url ="/admin/order/list${pm.makeSearch(pm.cri.page)}";
		}
		form.attr('action', url);
		form.submit();
	};
	

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
  .btnDate{
  	border: 1px solid grey;
  	padding: 5px 10px;
  	color: black;
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
					주문 목록<small>Order List</small>
				</h1>
				<ol class="breadcrumb">
					<li><a href="#"><i class="fa fa-dashboard"></i>주문 관리</a></li>
					<li class="active">주문 목록 </li>
				</ol>
			</section>

			<%-- MAIN CONTENT --%>
			<!-- Main content -->
			<section class="content container-fluid">

				<div class="row">
					<!-- left column -->
					<div class="box" style="border: none; padding: 10px 30px;">
						<div class="box-header">
							<%-- 검색 조건 설정 및 페이지 이동에도 해당 값 유지 --%>
							<div>
								<label>>&nbsp;검색</label><br>
								<form id="searchOrderForm" action="/admin/order/list" method="post">
									<table class="search" style="display:inline-block;">
										<tr class="search">
											<th style="background-color: #EEEEEE; padding-right:50px;"> 주문 현황 </th>
											<td style="padding: 5px 20px;" >
												<input type="checkbox" id="checkAllStatus" value="0">전체&nbsp;&nbsp;&nbsp;
												<input type="checkbox" name="check_status" value="1">입금완료&nbsp;&nbsp;&nbsp;
												<input type="checkbox" name="check_status" value="2">배송준비&nbsp;&nbsp;&nbsp;
												<input type="checkbox" name="check_status" value="3">배송중&nbsp;&nbsp;&nbsp;
												<input type="checkbox" name="check_status" value="4">배송완료&nbsp;&nbsp;&nbsp;
											</td>
										</tr>	
										<tr class="search">
											<th style="background-color: #EEEEEE; padding-right:50px;">기간(주문일)</th>
											<td style="padding: 5px 20px;" >
												<a class="btnDate"><span>전체</span></a>
												<a class="btnDate"><span>오늘</span></a>
												<a class="btnDate"><span>어제</span></a>
												<a class="btnDate"><span>3일</span></a>
												<a class="btnDate"><span>7일</span></a>
												<a class="btnDate"><span>15일</span></a>
												<a class="btnDate"><span>1개월</span></a>
												<a class="btnDate"><span>3개월</span></a>
												<a class="btnDate"><span>6개월</span></a>
												<input id="term" name = "term" type="hidden" value="${term}">
												<input id="date_start" name="date_start" type="date" value="${date_start}" />
												&nbsp;~&nbsp;
												<input id="date_end" name="date_end" type="date" value="${date_end}" />
												
											</td>
										</tr>
										<tr class="search">
											<th style="background-color: #EEEEEE; padding-right:50px;"> 검색 키워드 </th>
											<td style="padding: 5px 20px;" >
												<select name="searchType" style="width:130px; height:26px;">
													<option value="null" <c:out value="${cri.searchType == null?'selected':''}"/>>검색조건 선택</option>
													<option value="num" <c:out value="${cri.searchType eq 'num'?'selected':''}"/>>주문번호</option>
													<option value="name" <c:out value="${cri.searchType eq 'email'?'selected':''}"/>>주문자 이름</option>
													<option value="id" <c:out value="${cri.searchType eq 'id'?'selected':''}"/>>주문자 아이디</option>
													<option value="phone" <c:out value="${cri.searchType eq 'phone'?'selected':''}"/>>주문자 핸드폰</option>
													<option value="address" <c:out value="${cri.searchType eq 'address'?'selected':''}"/>>주문자 주소</option>
													<option value="all" <c:out value="${cri.searchType eq 'all'?'selected':''}"/>>전체</option>
												</select>
												<input type="text" name='keyword' id="keyword" 
														style="width:250px; padding-left:5px;" value='${cri.keyword}' />
												<button id="btn_search" class="btn btn-default" type="button">검색</button>
											</td>
										</tr>	
									</table>
								</form>
							</div>
							<%-- 페이지 POST방식 이동을 위한 hidden form --%>
							<div>
								<form id="hiddenForm" action="/admin/order/list" method="post">
									<c:forEach items="${check_status}" var="check">
										<input type="hidden" name="check_status" value="${check}">
									</c:forEach>
									<input id="hidden_term" name="term" type="hidden" value="${term}">
									<input type="hidden" name="date_start" id="hidden_date_start" value="${date_start}">
									<input type="hidden" name="date_end" id="hidden_date_end" value="${date_end}">
								</form>
							</div>
							
						</div>
						<br>
						<div class="box-body">
							<%-- 상품이 존재하는 경우 상단 버튼 출력 --%>
							<c:if test="${!empty orderList}">
								<div style="margin: 0px 7px;">
									<input type="checkbox" id="checkAll" /><b>&nbsp;&nbsp;CHECK ALL</b>
									<input type="button" id="btn_update_check" class="btn btn-primary" 
										style="float:right; margin-bottom:5px;" value="선택 주문현황 수정" />
								</div>
							</c:if>
							<table class="table text-center">
								<%-- 상품이 존재하지 않는 경우 --%>
								<c:if test="${empty orderList}">
									<tr>
										<td colspan="7"> 
											<p style="padding:50px 0px; text-align: center;">주문하신 상품이 없습니다.</p>
										</td>
									<tr>
								</c:if>
								
								<%-- 상품이 존재하는 경우,  리스트 출력 --%>
								<c:forEach items="${orderList}" var="orderVO" varStatus="status">
									<c:if test="${status.index==0 || orderVO.odr_code != code}">
									<tr style="background-color: aliceBlue;" >
										<td colspan="6" style="text-align:left;">
											<b>주문날짜: <fmt:formatDate value="${orderVO.odr_date}" pattern="yyyy/MM/dd HH:mm:ss"/>
											(주문번호: ${orderVO.odr_code} ) </b> <br>
											주문자 아이디 : ${orderVO.mem_id} / 주문 총 금액: ${orderVO.odr_total_price}
										</td>
										<td> 
											<button class="btn btn-info" onclick="location.href='/admin/order/read/${orderVO.odr_code}';">
											주문 상세보기</button> 
										</td>
									<tr>
									<tr style="background-color: whitesmoke;">
										<td><input type="checkbox" id="checkOrder_${orderVO.odr_code}" class="checkOrder" value="${orderVO.odr_code}" ></td>
										<td>IMAGE</td>
										<td>NAME</td>
										<td>PRICE</td>
										<td>AMOUNT</td>
										<td>TOTAL</td>
										<td>STATUS</td>
									</tr>
									</c:if>
									<c:set var="code" value="${orderVO.odr_code}">	</c:set>
									<tr>
										<td class="col-md-1">
											<input type="checkbox" id="checkProduct_${orderVO.pdt_num}" class="checkProduct_${orderVO.odr_code}" 
												name="checkProduct" value="${orderVO.pdt_num}" />
											<input type="hidden" id="odr_code_${orderVO.pdt_num}" value="${orderVO.odr_code}">
										<td class="col-md-2">
											<a href="/admin/product/read?pdt_num=${orderVO.pdt_num}">
												<img src="/admin/product/displayFile?fileName=${orderVO.pdt_img}" style="width:100px;">
											</a>
										</td>
										<td class="col-md-2">
											<a href="/admin/product/read?pdt_num=${orderVO.pdt_num}"
												style="color: black;"> ${orderVO.pdt_name} </a>
										</td>
										<td class="col-md-1">
											<fmt:formatNumber value="${orderVO.odr_price}" pattern="###,###,###" /></p>
											
										<td class="col-md-1">
											<p>${orderVO.odr_amount}</p>
										</td>
										<td class="col-md-1">
											<fmt:formatNumber value="${orderVO.odr_price * orderVO.odr_amount}" pattern="###,###,###" /></p>
										</td>
										<td class="col-md-2">
											<select id="odr_status_${orderVO.pdt_num}" name="odr_status">
												<option value="1"
													<c:out value="${orderVO.odr_status == 1?'selected':''}"/>>입금완료</option>
												<option value="2"
													<c:out value="${orderVO.odr_status == 2?'selected':''}"/>>배송준비</option>
												<option value="3"
													<c:out value="${orderVO.odr_status == 3?'selected':''}"/>>배송중</option>
												<option value="4"
													<c:out value="${orderVO.odr_status == 4?'selected':''}"/>>배송완료</option>
											</select>
										<br>
											<button name="btn_update_status" type="button" class="btn btn-flat" value="${orderVO.pdt_num}" >주문 현황 수정</button>
										</td>
									</tr>
								</c:forEach>
							</table>
						</div>
					</div>
					
					<%-- 페이지 표시 --%>
					<div class="box-footer container" style="width:100%;">
						<div class="text-center">
							<ul class="pagination">
								<c:if test="${pm.prev}">
									<li><a onclick="movePage('${pm.makeSearch(pm.startPage-1)}');">&laquo;</a>
									</li>
								</c:if>
	
								<c:forEach begin="${pm.startPage}" end="${pm.endPage}" var="idx">
									<li <c:out value="${pm.cri.page == idx?'class =active':''}"/>>
										<a class="btn" onclick="movePage('${pm.makeSearch(idx)}');" >${idx}</a>
									</li>
								</c:forEach>
	
								<c:if test="${pm.next && pm.endPage > 0}">
									<li>
										<a onclick="movePage('${pm.makeSearch(endPage +1)}');">&raquo;</a>
									</li>
								</c:if>
							</ul>
						</div>
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