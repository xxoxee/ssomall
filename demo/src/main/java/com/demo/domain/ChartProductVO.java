package com.demo.domain;

/*
 * 상품 별 통계를 위한 VO 객체
 */
public class ChartProductVO {

	private String pdt_name;
	private int totalAmount;
	private int totalPrice;
	
	/* Getter and Setter */
	public String getPdt_name() {
		return pdt_name;
	}
	public void setPdt_name(String pdt_name) {
		this.pdt_name = pdt_name;
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
		return "ChartProductVO [pdt_name=" + pdt_name + ", totalAmount=" + totalAmount + ", totalPrice=" + totalPrice
				+ "]";
	}
	
	
	
}
