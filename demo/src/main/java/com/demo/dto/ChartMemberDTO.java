package com.demo.dto;

/*
 *	회원 별 통계 차트 생성에 필요한 DTO 객체 
 */
public class ChartMemberDTO {

	private String mem_id;
	private int totalAmount;
	private int totalPrice;
	
	/* Getter and Setter */
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public int getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	/* toString() */
	@Override
	public String toString() {
		return "ChartMemberDTO [mem_id=" + mem_id + ", totalAmount=" + totalAmount + ", totalPrice=" + totalPrice + "]";
	}
	
	
}
