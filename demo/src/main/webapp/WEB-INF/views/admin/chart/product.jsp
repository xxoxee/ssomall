<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/views/include/plugin_js.jsp" %>
<%@include file="/WEB-INF/views/include/header.jsp"%>
<head>
<%-- 구글 차트 API --%>
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<%-- 날짜변환 용 --%>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js"></script>
<%--핸들바 --%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>
<script type="text/javascript" src="/js/admin/chart/product.js"></script>

<%-- Handlebar Template: 2차 카테고리 --%>
<script id="subCGListTemplate" type="text/x-handlebars-template">
	<option value="all">전체</option>
	{{#each .}}
	<option value="{{cg_code}}">{{cg_name}} </option>
	{{/each}}
</script>

<%-- Handlebar Template: 통계테이블(판매수량) --%>
<script id="amountTableTemplate" type="text/x-handlebars-template">
	<table class="table table-striped text-center handlebarTable" >
		<tr>
			<th>순위</th>
			<th>상품코드</th>
			<th>상품명</th>
			<th>판매가</th>
			<th>재고</th>
			<th>총 판매수량</th>
		</tr>

	{{#each .}}
		<tr>
			<td class="col-md-1">{{rank @index}}</td>
			<td class="col-md-1">{{pdt_num}}</td>
			<td class="col-md-2">{{pdt_name}}</td>
			<td class="col-md-1">{{pdt_price}}</td>
			<td class="col-md-1">{{pdt_amount}}</td>
			<td class="col-md-1">{{totalAmount}}</td>
		</tr>
	{{/each}}
	</table>
</script>

<%-- Handlebar Template: 통계테이블(판매수량) : 데이터X --%>
<script id="noAmountTableData" type="text/x-handlebars-template">
	<table class="table table-striped text-center handlebarTable" >
		<tr>
			<th>순위</th>
			<th>상품코드</th>
			<th>상품명</th>
			<th>판매가</th>
			<th>재고</th>
			<th>총 판매수량</th>
		</tr>	

		<tr>
			<td colspan="8">
				<p>조건에 해당하는 상품 순위가 존재하지 않습니다. </p>
			</td>
		</tr>
	</table>
</script>


<%-- Handlebar Template: 통계테이블(판매가격) --%>
<script id="priceTableTemplate" type="text/x-handlebars-template">
	<table class="table table-striped text-center handlebarTable" >
		<tr>
			<th>순위</th>
			<th>상품코드</th>
			<th>상품명</th>
			<th>판매가</th>
			<th>재고</th>
			<th>총 판매가격</th>
		</tr>	

	{{#each .}}
		<tr>
			<td class="col-md-1">{{rank @index}}</td>
			<td class="col-md-1">{{pdt_num}}</td>
			<td class="col-md-2">{{pdt_name}}</td>
			<td class="col-md-1">{{pdt_price}}</td>
			<td class="col-md-1">{{pdt_amount}}</td>
			<td class="col-md-1">{{totalPrice}}</td>
		</tr>
	{{/each}}
	</table>
</script>

<%-- Handlebar Template: 통계테이블(판매가격) : 데이터X --%>
<script id="noPriceTableData" type="text/x-handlebars-template">
	<table class="table table-striped text-center handlebarTable" >
		<tr>
			<th>순위</th>
			<th>상품코드</th>
			<th>상품명</th>
			<th>판매가</th>
			<th>재고</th>
			<th>총 판매가격</th>
		</tr>	

		<tr>
			<td colspan="8">
				<p>조건에 해당하는 상품 순위가 존재하지 않습니다. </p>
			</td>
		</tr>
	</table>
</script>

<%--Handlebar helper --%>
<script>
	$(document).ready(function(){
		/* 
		 * 사용자 정의 헬퍼(rank)
		 * : 1부터 최대 7까지 순위를 출력
		 */ 
		Handlebars.registerHelper("rank", function(index) {
			
			return index + 1;
		});
	});
</script>

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
	
	.tableTab{
	border: 1px solid lightgrey;
	padding: 10px 50px;
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
					상품별 통계 
				</h1>
				<ol class="breadcrumb">
					<li><a href="#"><i class="fa fa-dashboard"></i> 통계</a></li>
					<li class="active">상품별 통계</li>
				</ol>
			</section>

			
			<%-- MAIN CONTENT --%> 
			<section class="content container-fluid">
				<div class="box" style="border: none; padding: 10px 30px;">
				
					<div class="box-header">
						<div id="searchDiv" style="padding:20px 0px;">
							<label>검색</label><br>
							<table class="search" >
								<tr style="height:70px;" >
									<th rowspan="2" style="padding-right:50px; background-color:#EEEEEE;">기간(구매일)</th>
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
											<input id="term" name="term" type="hidden" value="${searchDate.term}" />
											<input id="date_start" name="startDate" type="date" value="${searchDate.startDate}" />
											&nbsp;~&nbsp;
											<input id="date_end" name="endDate" type="date" value="${searchDate.endDate}" />
										</div>
									</td>
								</tr>
								<tr></tr>
								<tr class="search" >
									<th class="search" style="padding-right:50px; background-color:#EEEEEE;">카테고리</th>
									<td style="padding:5px 30px;">
										<select class="form-control" id="mainCategory" name="cg_code_prt" style="width:170px; margin-right:10px; display: inline-block;" >
										  <option value="default">1차 카테고리 선택</option>
										  <c:forEach items="${userCategoryList}" var="vo">
										  	<option value="${vo.cg_code}">${vo.cg_name}</option>
										  </c:forEach>
										</select>
										<select class="form-control" id="subCategory" name="cg_code" style="width: 170px; display: inline-block;">
										 	<option value="default">2차 카테고리 선택</option>
										</select>
										<button id="btn_search" class="btn btn-default" type="button">검색</button>
									</td>
								</tr>
							</table>
							<br>
						</div>	
					</div>
 					
					<%-- 상품별 차트 --%>
					<div class="box-body" >
						<label>통계 그래프</label>
						<div style="width:100%; text-align: center; border:1px solid lightgrey;">
							<div id="chartAmountDiv" style="display: inline-block;"></div>
							<div id="chartPriceDiv" style="display: inline-block;"></div>
						</div>
						
						<br><br><br>
						
						<div>
							<div>
								<label>통계 테이블</label><br><br>
								<b><span class="tableTab">수량 통계</span></b>
								<b><span class="tableTab">가격 통계</span></b>
							</div>
							<%-- 핸들바 템플릿이 적용될 DIV --%>
							<div id="tableAmountDiv" style="width:100%; text-align: center; margin-top: 10px;">
							</div>
							<div id="tablePriceDiv" style="width:100%; text-align: center; margin-top: 10px;">
							</div>
						</div>
					</div>
				</div>
				
			</section>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->

		<!-- Main Footer -->
		<%@include file="/WEB-INF/views/include/footer.jsp" %>

		<!-- Control Sidebar -->
		<aside class="control-sidebar control-sidebar-dark">
			<!-- Create the tabs -->
			<ul class="nav nav-tabs nav-justified control-sidebar-tabs">
				<li class="active">
					<a href="#control-sidebar-home-tab" data-toggle="tab"><i class="fa fa-home"></i></a>
				</li>
				<li>
					<a href="#control-sidebar-settings-tab" data-toggle="tab"><i class="fa fa-gears"></i></a>
				</li>
			</ul>
			<!-- Tab panes -->
			<div class="tab-content">
				<!-- Home tab content -->
				<div class="tab-pane active" id="control-sidebar-home-tab">
					<h3 class="control-sidebar-heading">Recent Activity</h3>
					<ul class="control-sidebar-menu">
						<li>
							<a href="javascript:;"> <i class="menu-icon fa fa-birthday-cake bg-red"></i>

								<div class="menu-info">
									<h4 class="control-sidebar-subheading">Langdon's Birthday</h4>

									<p>Will be 23 on April 24th</p>
								</div>
							</a>
						</li>
					</ul>
					<!-- /.control-sidebar-menu -->

					<h3 class="control-sidebar-heading">Tasks Progress</h3>
					<ul class="control-sidebar-menu">
						<li>
							<a href="javascript:;">
								<h4 class="control-sidebar-subheading">
									Custom Template Design
									<span class="pull-right-container">
										<span class="label label-danger pull-right">70%</span>
									</span>
								</h4>

								<div class="progress progress-xxs">
									<div class="progress-bar progress-bar-danger" style="width: 70%"></div>
								</div>
							</a>
						</li>
					</ul>
					<!-- /.control-sidebar-menu -->

				</div>
				<!-- /.tab-pane -->
				<!-- Stats tab content -->
				<div class="tab-pane" id="control-sidebar-stats-tab">Stats Tab Content</div>
				<!-- /.tab-pane -->
				<!-- Settings tab content -->
				<div class="tab-pane" id="control-sidebar-settings-tab">
					<form method="post">
						<h3 class="control-sidebar-heading">General Settings</h3>

						<div class="form-group">
							<label class="control-sidebar-subheading">
								Report panel usage
								<input type="checkbox" class="pull-right" checked>
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