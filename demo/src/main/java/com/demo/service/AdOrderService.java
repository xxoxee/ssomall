package com.demo.service;

import java.util.List;
import java.util.Map;

import com.demo.domain.OrderReadDetailVO;
import com.demo.domain.OrderVO;
import com.demo.util.SearchCriteria;

public interface AdOrderService {

	// 모든 주문 목록과 주문 개수 반환
	public Map<String, Object> orderListAll(SearchCriteria cri, List<Integer> status, String date_start, String date_end) throws Exception;
	
	// 배송현황 수정
	public void updateStatus(int odr_status, int odr_code, int pdt_num) throws Exception;
	
	// 주문 상세 정보
	public List<OrderReadDetailVO> readOrder(int odr_code) throws Exception;
	
	// 주문자 정보
	public OrderVO getOrder(int odr_code) throws Exception;
}
