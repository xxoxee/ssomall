$(document).ready(function(){
	/* ckEditor 작업 */
	// config.js를 사용하지 않고 개별 설정하는 부분
	var ckeditor_config = {
			resize_enabled : false,
			enterMode : CKEDITOR.ENTER_BR,
			shiftEnterMode : CKEDITOR.ENTER_P,
			toolbarCanCollapse : true,
			removePlugins : "elementspath", 
			// 파일 업로드 기능 추가
			// CKEditor를 이용해 업로드 사용 시 해당 주소에 업로드 됨
			filebrowserUploadUrl: '/admin/product/imgUpload'
	};
	CKEDITOR.replace("message", ckeditor_config);
	// config.js의 설정을 사용하려면, 다음과 같이 사용
	// CKEDITOR.replace("desc", "");


	/* 전송 버튼 클릭 시 이벤트 메소드 */
	$("#btn_submit").on("click", function(){

		var form = $("#sendMailForm");
		var senderName = $("#senderName");
		var senderMail = $("#senderMail");
		var receiveMail = $("input[name='receiveMail']");
		var subject = $("#subject");
		var message = CKEDITOR.instances['message'];

		// 유효성 검사
		if(senderName.val() == null || senderName.val() == ""){
			alert("발신자 이름을 입력해주세요.");
			senderName.focus();
			return;
			
		} else if(senderMail.val() == null || senderMail.val() ==""){
			alert("발신자 메일주소를 입력해주세요.");
			senderMail.focus();
			return;
			
		} else if(receiveMail.length<1){
			alert("수신자 메일주소를 입력해주세요.");
			receiveMail.focus();
			return;
			
		} else if(subject.val() == null || subject.val() ==""){
			alert("메일 제목을 입력해주세요.");
			subject.focus();
			return;
			
		} else if(message.getData() == null || message.getData() ==""){
			alert("메일 내용을 입력해주세요.");
			message.focus();
			return;
			
		} else {
			$("#sendStatus").text("메일을 전송중입니다. 잠시만 기다려주세요...");
			form.submit();
		}
		
	});
	
});

/* 수신자 이메일 주소 'x' 버튼 클릭 시 삭제 */
var removeMail = function(obj){
	$(obj).parent().remove();
}