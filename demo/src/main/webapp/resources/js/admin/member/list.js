$(document).ready(function(){
	
	/* 전체 선택 체크박스 클릭 시 */
	$("#checkAll").on("click", function(){
		// 전체선택 클릭 여부로 다른 체크박스 체크
		$(".check").prop('checked', this.checked);
	});
	
	/* 체크박스 중 선택안된 체크박스 존재 시 전체선택 해제 */ 
	$(".check").on("click", function(){
		$("#checkAll").prop('checked', false);
	});

	
	/* 선택 회원 메일 보내기 버튼 클릭 시 */ 
	$("#btn_email_check").on("click", function(){

		var form = $("#memberForm");
		
		// 체크여부 유효성 검사
		if($("input[name='check']:checked").length==0){
			alert("메일을 보낼 회원을 선택해주세요.");
			return;
		}
		
		
		// 체크 된 상품의 value(pdt_num)을 가져옴
		$("input[name='check']:checked").each(function(i, e){
			var mem_id = $(e).val();
			var accept = $("#accept_" + mem_id).val();
			
			// 메일 수신동의에 동의하지 않은 회원 존재 알림
			if(accept == 'N'){
				var result = confirm("메일 수신동의에 동의하지 않은 회원이 포함되어 있습니다.\n" +
								"수신동의하지 않은 회원에게도 메일을 보내시겠습니까?");
				if(result){
					form.submit();
					
				} else { return; }
			}
			form.submit();
		});
	});
	
	/* 전체 회원 메일 보내기 */
	$("#btn_email_all").on("click", function(){
		
		var form = $("#sendMailAllForm");
		
		var result = confirm("전체 회원에게 메일을 보내시겠습니까?");
		if(result){
			form.submit();
			
		}else {}
	});
	
});

