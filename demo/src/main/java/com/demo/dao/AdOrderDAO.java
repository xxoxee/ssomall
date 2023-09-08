package com.demo.dao;

import java.util.List;
import java.util.Map;

import com.demo.domain.AdOrderListVO;
import com.demo.domain.OrderReadDetailVO;
import com.demo.domain.OrderVO;
import com.demo.util.Criteria;

public interface AdOrderDAO {

	// 모든 주문 목록
	public List<AdOrderListVO> orderListAll(Map<String, Object> map) throws Exception;
	
	// 총 주문 개수
	public int countListAll(Map<String, Object> map) throws Exception;
	
	// 배송현황 수정
	public void updateStatus(Map<String, Object> map) throws Exception;
	
	// 주문 상세 정보
	public List<OrderReadDetailVO> readOrder(int odr_code) throws Exception;
	
	// 주문자 정보
	public OrderVO getOrder(int odr_code) throws Exception;
}
