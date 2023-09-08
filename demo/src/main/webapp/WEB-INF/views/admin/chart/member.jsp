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
<script type="text/javascript" src="/js/admin/chart/member.js"></script>



<%-- Handlebar Template: 통계테이블(판매수량) --%>
<script id="amountTableTemplate" type="text/x-handlebars-template">
	<table class="table table-striped text-center handlebarTable" >
		<tr>
			<th>순위</th>
			<th>아이디</th>
			<th>닉네임</th>
			<th>이름</th>
			<th>핸드폰</th>
			<th>이메일</th>
			<th>포인트</th>
			<th>총 구매 수량</th>
		</tr>

	{{#each .}}
		<tr>
			<td class="col-md-1">{{rank @index}}</td>
			<td class="col-md-1">{{mem_id}}</td>
			<td class="col-md-2">{{mem_nick}}</td>
			<td class="col-md-1">{{mem_name}}</td>
			<td class="col-md-1">{{mem_phone}}</td>
			<td class="col-md-1">{{mem_email}}
				<input type="hidden" name="mem_email" class="mem_email" value={{mem_email}} />
			</td>
			<td class="col-md-1">{{mem_point}}</td>
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
			<th>아이디</th>
			<th>닉네임</th>
			<th>이름</th>
			<th>핸드폰</th>
			<th>이메일</th>
			<th>포인트</th>
			<th>총 구매 수량</th>
		</tr>

		<tr>
			<td colspan="9">
				<p>조건에 해당하는 회원 순위가 존재하지 않습니다. </p>
			</td>
		</tr>
	</table>
</script>


<%-- Handlebar Template: 통계테이블(판매가격) --%>
<script id="priceTableTemplate" type="text/x-handlebars-template">
	<table class="table table-striped text-center handlebarTable" >
		<tr>
			<th>순위</th>
			<th>아이디</th>
			<th>닉네임</th>
			<th>이름</th>
			<th>핸드폰</th>
			<th>이메일</th>
			<th>포인트</th>
			<th>총 구매 가격</th>
		</tr>

	{{#each .}}
		<tr>
			<td class="col-md-1">{{rank @index}}</td>
			<td class="col-md-1">{{mem_id}}</td>
			<td class="col-md-2">{{mem_nick}}</td>
			<td class="col-md-1">{{mem_name}}</td>
			<td class="col-md-1">{{mem_phone}}</td>
			<td class="col-md-1">{{mem_email}}
				<input type="hidden" name="mem_email" class="mem_email" value={{mem_email}} />
			</td>
			<td class="col-md-1">{{mem_point}}</td>
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
			<th>아이디</th>
			<th>닉네임</th>
			<th>이름</th>
			<th>핸드폰</th>
			<th>이메일</th>
			<th>포인트</th>
			<th>총 구매 가격</th>
		</tr>

		<tr>
			<td colspan="9">
				<p>조건에 해당하는 회원 순위가 존재하지 않습니다. </p>
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
	.color-black{
		color: black;
	}

	.period{
		border: 1px solid lightgrey;
		padding: 15px 70px;
		margin: 0px;
			
	}
	
	.tableTab{
		border: 1px solid lightgrey;
		padding: 10px 50px;
	}
​
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
					회원별 통계 
				</h1>
				
				<ol class="breadcrumb">
					<li><a href="#"><i class="fa fa-dashboard"></i> 통계</a></li>
					<li class="active">회원별 통계</li>
				</ol>
			</section>

			
			<%-- MAIN CONTENT --%> 
			<section class="content container-fluid">
				<div class="box" style="border: none; padding: 10px 30px;">
				
					<div class="box-header">
						<div id="searchDiv" style="padding:20px 0px;">
							<div id="periodTabDiv" style="display:inline-block;">
								<b><span class="period">일간</span></b>
								<b><span class="period">월간</span></b>
								<b><span class="period">연간</span></b>
								<input id="period" type="hidden" value="일간" />
							</div>
							<div style="margin-left:50px; display:inline-block;">
								상세기간설정:
								<input id="searchDateDay" class="form-control" type="date" style="width:160px;"/>
								<input id="searchDateMonth" class="form-control" type="month" style="width:160px;" />
								<input id="searchDateYear" class="form-control" type="number" style="width:160px;" />
							</div>
							<button type="button" id="btn_date" class="btn btn-info" >변경</button>
							<br>
						</div>	
					</div>
 					
					<%-- 회원별 차트 --%>
					<div class="box-body" >
						<label>통계 그래프</label>
						<div style="width:100%; text-align: center; border:1px solid lightgrey; ">
							<div id="chartAmountDiv" style="display: inline-block;"></div>
							<div style="width:70px; display: inline-block;"></div>
							<div id="chartPriceDiv" style="display: inline-block;"></div>
						</div>
						
						<br><br><br>
						
						<div>
							<div>
								<label>통계 테이블</label><br><br>
								<b><span class="tableTab">수량 통계</span></b>
								<b><span class="tableTab">가격 통계</span></b>
								<button id="btn_send_mail" class="btn btn-warning" style="float:right; margin-bottom:5px;" >해당 회원 메일 보내기</button>
							</div>
							<%-- 핸들바 템플릿이 적용될 DIV --%>
							<form action="/email/sendMany" method="post" id="amountForm">
							<div id="tableAmountDiv" style="width:100%; text-align: center; margin-top: 10px;">
							</div>
							</form>
							<form action="/email/sendMany" method="post" id="priceForm">
							<div id="tablePriceDiv" style="width:100%; text-align: center; margin-top: 10px;">
							</div>
							</form>
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