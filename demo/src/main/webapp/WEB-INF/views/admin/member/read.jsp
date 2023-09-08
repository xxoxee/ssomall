<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/views/include/plugin_js.jsp"%>
<%@include file="/WEB-INF/views/include/header.jsp"%>
<head>
<script src="/ckeditor/ckeditor.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>

<%-- ckEditor, 버튼 클릭 이벤트 메소드 --%>
<script>
	$(document).ready(function(){
		/* 상품 목록 버튼 클릭 시 */
		$("#btn_list").on("click", function(){
			location.href="/admin/member/list${pm.makeSearch(pm.cri.page)}&accept=${accept}";
		});
	});
</script>
	
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
					회원 상세 정보 <small>Member Detail</small>
				</h1>
				<ol class="breadcrumb">
					<li><a href="#"><i class="fa fa-dashboard"></i> 회원 관리</a></li>
					<li class="active">회원 상세</li>
				</ol>
			</section>


			<%-- MAIN CONTENT --%>
			<section class="content container-fluid">

				<!-- 상품등록 폼 -->
				<div class="row">
					<!-- left column -->
					<div class="col-md-12">
						<!-- general form elements -->
						<div class="box box-primary">
							<!-- /.box-header -->
							
							<%-- 상품 상세 정보 출력 --%>
							<div class="box-body" style="padding:20px 50px;">
								<div class="form-group">
									<label>ID</label> 
									<span class="form-control">${memberVO.mem_id}</span>
								</div>
								<div class="form-group">
									<label style="width:40%; margin-right:10px;" >NAME</label>
									<label style="width:40%;" >NICKNAME</label> <br />
									<span class="form-control" style="width:40%; margin-right:10px; display:inline-block;">${memberVO.mem_name}</span>
									<span class="form-control" style="width:40%;  display:inline-block;">${memberVO.mem_nick}</span>
								</div>
								<div class="form-group">
									<label>Phone</label> 
									<span class="form-control">${memberVO.mem_phone}</span>
								</div>
								<div class="form-group">
									<label>EMAIL</label> 
									<span class="form-control">${memberVO.mem_email}</span>
								</div>
								<div class="form-group">
									<label>ADDRESS</label> <br />
									<span class="form-control" style="width:40%;">${memberVO.mem_zipcode}</span>
									<span class="form-control" >${memberVO.mem_addr}</span>
									<span class="form-control" >${memberVO.mem_addr_d}</span>
								</div>
								<div class="form-group">
									<label style="width:40%; margin-right:10px;">TOTAL ORDER</label> 
									<label style="width:40%;">TOTAL PRICE</label> <br />
									<span class="form-control" style="width:40%; margin-right:10px; display:inline-block;">
										${totalOrder}</span>
									<span class="form-control" style="width:40%; display: inline-block;">
										${totalPrice}</span>
								</div>
								<div class="form-group">
									<label>POINT</label> 
									<span class="form-control">${memberVO.mem_point}</span>
								</div>
								<div class="form-group">
									<label>EMAIL ACCEPT</label> 
									<span class="form-control">${memberVO.mem_accept_e}</span>
								</div>
								<div class="form-group">
									<label style="width:40%; margin-right:10px;">REGISTER DATE</label> 
									<label style="width:40%;">LAST LOGIN DATE</label> <br />
									<span class="form-control" style="width:40%; margin-right:10px; display:inline-block;">
										<fmt:formatDate value="${memberVO.mem_date_sub}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
									<span class="form-control" style="width:40%; display: inline-block;">
										<fmt:formatDate value="${memberVO.mem_date_up}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
								</div>
							</div>
							
							
							<!-- /.box-body -->
							<div class="box-footer">
								<div>
									<hr>
								</div>
								<button id="btn_list" type="button" class="btn btn-primary" >GO List</button>
							</div>
						
						</div>
						<!-- /.box -->	
					</div>
					<!--/.col (left) -->

				</div>
			</section>
			<!-- /.content -->
		</div>
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