<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/views/include/plugin_js.jsp"%>
<%@include file="/WEB-INF/views/include/header.jsp"%>
<head>
<script src="/ckeditor/ckeditor.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>

<%-- ckEditor 처리, 클릭 이벤트 --%>
<script type="text/javascript" src="/js/email/write.js"></script>
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
					이메일 전송 <small>Send Email</small>
				</h1>
				<ol class="breadcrumb">
					<li><a href="#"><i class="fa fa-dashboard"></i> 회원 관리</a></li>
					<li class="active">이메일 전송</li>
				</ol>
			</section>


			<%-- MAIN CONTENT --%>
			<section class="content container-fluid">
				<div class="row">
					<div class="box-body">
						<form id='sendMailForm' role="form" action="/email/sendMail" method="post" enctype="multipart/form-data">
							<div class="form-group">
								<label style="width:40%; margin-right:10px;">SENDER NAME</label> 
								<label style="width:40%;">SENDER EMAIL</label><br> 
								<input type="text" id="senderName" name="senderName" class="form-control" 
										style="width:40%; margin-right:10px; display:inline-block; float:left;"
										value="${dto.senderName}">
								<input type="text" id="senderMail" name="senderMail" class="form-control" 
										style="width:40%; margin-right:10px; display:inline-block;"
										value="${dto.senderMail}"><br>
							</div>
							<div class="form-group">
								<label>RECIEVER MAIL</label><br>
								<c:forEach var="receiveMail" items="${dto.receiveMail}">
									<div class="receiveMail">
										<input type="text" name="receiveMail" class="form-control"
											style="width:90%; display:inline-block;"
											value="${receiveMail}">
										<span class="delete_receiver" style="font-size:20px; padding:2px 5px;"
											onclick="removeMail(this)">&times;</span>
									</div>
								</c:forEach>
							</div>
							<div class="form-group">
								<label>SUBJECT</label> 
								<input type="text" id="subject" name="subject" class="form-control"
										placeholder="제목을 입력하세요.">
							</div>
							<div class="form-group">
								<label>MESSAGE</label>
								<textarea class="form-control" id="message" name="message"
									placeholder="내용을 입력하세요."></textarea>
							</div>
						</form>
					</div>	
							
					<div class="box-footer">
						<div>
							<hr>
						</div>
						<button id="btn_submit" type="button" class="btn btn-primary">SEND</button>
						<span id="sendStatus" style="margin-left: 10px; color: blue;"></span>
					</div>
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