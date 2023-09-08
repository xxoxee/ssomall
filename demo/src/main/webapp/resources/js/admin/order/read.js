$(document).ready(function(){
	
	/* 전체 선택 체크박스 클릭 시 */
	$("#checkAll").on("click", function() {
		// 전체선택 클릭 여부로 다른 체크박스 체크
		$(".check").prop('checked', this.checked);
	});

	/* 체크박스 중 선택안된 체크박스 존재 시 전체선택 해제 */
	$(".check").on("click", function() {
		$("#checkAll").prop('checked', false);
	});
	
	
	/* 선택 상품 주문현황 수정 버튼 클릭 시 */
	$("#btn_update_check").on("click", function() {

		// 체크여부 유효성 검사
		if ($("input[name='check']:checked").length == 0) {
			alert("주문현황을 변경할 상품을 선택해주세요.");
			return;
		}
		
		var statusArr = [];
		var codeArr = [];
		var pdtArr = [];

		// 체크 된 상품의 value(cart_code)를 가져옴
		var odr_code = 0;
		$("input[name='check']:checked").each(function(index, element) {
			var pdt_num = $(element).val();
			var odr_status = $("#odr_status_"+pdt_num+" option:selected").val();
			odr_code = $("#odr_code_"+pdt_num).val();

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
				location.href = "/admin/order/read/" + odr_code;
			}
		});
	});
	
	
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
				location.href = "/admin/order/read/" + odr_code;
			}
		});
	});
	
	
});