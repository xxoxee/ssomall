package com.demo.domain;

/*
 * 상품별 통계 - 수량/ 가격 순 
 * 통계 테이블 구현에 사용되는 VO 객체
 */
public class ChartProductTableVO {

	private int pdt_num;
	private String pdt_name;
	private int pdt_price;
	private int pdt_amount;
	private int totalAmount;
	private int totalPrice;
	
	/* Getter and Setter */
	public int getPdt_num() {
		return pdt_num;
	}
	public void setPdt_num(int pdt_num) {
		this.pdt_num = pdt_num;
	}
	public String getPdt_name() {
		return pdt_name;
	}
	public void setPdt_name(String pdt_name) {
		this.pdt_name = pdt_name;
	}
	public int getPdt_price() {
		return pdt_price;
	}
	public void setPdt_price(int pdt_price) {
		this.pdt_price = pdt_price;
	}
	public int getPdt_amount() {
		return pdt_amount;
	}
	public void setPdt_amount(int pdt_amount) {
		this.pdt_amount = pdt_amount;
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
		return "ChartProductTableVO [pdt_num=" + pdt_num + ", pdt_name=" + pdt_name + ", pdt_price=" + pdt_price
				+ ", pdt_amount=" + pdt_amount + ", totalAmount=" + totalAmount + ", totalPrice=" + totalPrice + "]";
	}
	
}
