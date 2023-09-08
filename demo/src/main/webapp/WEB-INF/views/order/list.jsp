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
<script type="text/javascript" src="/js/order/list.js"></script>
<%-- 스타일 --%>
<style>
	table .search{
	  border-top: 1px solid lightgrey;
	  border-bottom: 1px solid lightgrey;
	  border-collapse: collapse;
	}
	th, tr, td .search{
	  border-top: 1px solid lightgrey;
	  border-bottom: 1px solid lightgrey;
	  padding: 10px;
	}
	
	.color-black{
		color: black;
	}

	.btnDate{
		border: 1px solid grey;
		padding: 5px 10px;
		color: black;
	}
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
					주문 목록 <small>Order List</small>
				</h1>
				<ol class="breadcrumb">
					<li><a href="#"><i class="fa fa-dashboard"></i> MAIN</a></li>
					<li class="active">주문 목록</li>
				</ol>
			</section>

			<%-- MAIN CONTENT --%>
			<!-- Main content -->
			<section class="content container-fluid">

				<div class="row">
					<!-- left column -->
					<div class="box" style="border: none; padding: 10px 30px;">
						<div class="box-header">
							<div id="searchDiv" style="padding:20px 0px;">
								<label>검색</label><br>
								<form id="searchForm">
									<table class="search" >
										<tr style="height:70px;" >
											<th rowspan="2" style="padding-right:50px; background-color:#EEEEEE;">기간(주문일)</th>
											<td rowspan="2" style="padding:5px 30px;">
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
													<input id="term" name="term" type="hidden" value="${search.term}" />
													<input id="date_start" name="startDate" type="date" value="${search.startDate}" />
													&nbsp;~&nbsp;
													<input id="date_end" name="endDate" type="date" value="${search.endDate}" />
													<input id="btn_search" type="button" class="btn btn-default" value="검색" />
												</div>
											</td>
										</tr>
										<tr></tr>
									</table>
								</form>
								<br>
							</div>	
						</div>
						
						<div class="box-body">
						<label>주문 내역</label><br>
							<table class="table text-center">
								<%-- 상품이 존재하지 않는 경우 --%>
								<c:if test="${empty orderList}">
									<tr>
										<td colspan="11"> 
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
											(주문번호: ${orderVO.odr_code} ) </b>
										</td>
										<td> 
											<button class="btn btn-info" onclick="location.href='/order/read?odr_code=${orderVO.odr_code}';">
											주문 상세보기</button> 
										</td>
									<tr>
									<tr style="background-color: whitesmoke;">
										<td>IMAGE</td>
										<td>NAME</td>
										<td>PRICE</td>
										<td>AMOUNT</td>
										<td>TOTAL</td>
										<td>STATUS</td>
										<td>REVIEW</td>
									</tr>
									</c:if>
									<c:set var="code" value="${orderVO.odr_code}">	</c:set>
									<tr>
										<td class="col-md-2">
											<a href="/product/read?pdt_num=${orderVO.pdt_num}">
												<img src="/product/displayFile?fileName=${orderVO.pdt_img}" style="width:100px;">
											</a>
										</td>
										<td class="col-md-2">
											<a href="/product/read?pdt_num=${orderVO.pdt_num}"
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
										<td class="col-md-1">
											<c:choose>
												<c:when test="${orderVO.odr_status == 1}">입금완료</c:when>
												<c:when test="${orderVO.odr_status == 2}">배송준비</c:when>
												<c:when test="${orderVO.odr_status == 3}">배송중</c:when>
												<c:when test="${orderVO.odr_status == 4}">배송완료</c:when>
											</c:choose>
										</td>
										<td class="col-md-2">
											<button type="button" class="btn btn-flat" 
												onclick="location.href='/product/read?pdt_num=${orderVO.pdt_num}';" value="${orderVO.pdt_num}" >상품후기 쓰기</button>
										</td>
									</tr>
								</c:forEach>
							</table>
						</div>
						
						<%-- 페이지 표시 --%>
						<div class="box-footer container" style="width:100%;">
							<div class="text-center">
								<ul class="pagination">
									<c:if test="${pm.prev}">
										<li><a href="/order/list${pm.makeQuery(pm.startPage-1)}">&laquo;</a>
										</li>
									</c:if>
		
									<c:forEach begin="${pm.startPage}" end="${pm.endPage}"
										var="idx">
										<li <c:out value="${pm.cri.page == idx?'class =active':''}"/>>
											<a href="/order/list${pm.makeQuery(idx)}">${idx}</a>
										</li>
									</c:forEach>
		
									<c:if test="${pm.next && pm.endPage > 0}">
										<li><a href="/order/list${pm.makeQuery(pm.endPage +1)}">&raquo;</a>
										</li>
									</c:if>
								</ul>
							</div>
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