package com.demo.dao;

import java.util.List;
import java.util.Map;

import com.demo.domain.OrderDetailVO;
import com.demo.domain.OrderListVO;
import com.demo.domain.OrderReadDetailVO;
import com.demo.domain.OrderVO;

public interface OrderDAO {

	// 주문코드 시퀀스 가져오기
	public int readOrderCode() throws Exception;
	
	// 주문내역 추가
	public void addOrderDetail(OrderDetailVO vo) throws Exception;
	
	// 주문정보 추가
	public void addOrder(OrderVO vo) throws Exception;
	
	// 주문목록
	public List<OrderListVO> orderList(Map<String, Object> map) throws Exception;
	
	// 총 주문 개수
	public int countList(Map<String, Object> map) throws Exception;
	
	// 주문 상세 정보
	public List<OrderReadDetailVO> readOrder(int odr_code) throws Exception;
	
	// 주문자 정보
	public OrderVO getOrder(int odr_code) throws Exception;
	
}
