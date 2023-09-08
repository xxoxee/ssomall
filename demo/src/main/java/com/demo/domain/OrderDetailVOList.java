package com.demo.domain;

import java.util.List;

/*
 * 상품구매 DB작업 시 사용될 VO 객체
 */
public class OrderDetailVOList {

	private List<OrderDetailVO> orderDetailList;
	
	
	/* Getter and Setter */
	public List<OrderDetailVO> getOrderDetailList() {
		return orderDetailList;
	}
	public void setOrderDetailList(List<OrderDetailVO> orderDetailList) {
		this.orderDetailList = orderDetailList;
	}
	
	/* toString() */
	@Override
	public String toString() {
		return "OrderDetailVOList [orderDetailList=" + orderDetailList + "]";
	}
	
	
	
}
