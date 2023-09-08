package com.demo.domain;

/*
 * 회원별통계
 * 차트와 테이블을 만들기 위한 VO 객체
 */
public class ChartMemberTableVO {

	private String mem_id;
	private String mem_name; 
	private String mem_email;
	private String mem_phone;
	private String mem_nick;
	private String mem_point;
	private int totalAmount;
	private int totalPrice;
	
	/* Getter and Setter */
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public String getMem_name() {
		return mem_name;
	}
	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
	}
	public String getMem_email() {
		return mem_email;
	}
	public void setMem_email(String mem_email) {
		this.mem_email = mem_email;
	}
	public String getMem_phone() {
		return mem_phone;
	}
	public void setMem_phone(String mem_phone) {
		this.mem_phone = mem_phone;
	}
	public String getMem_nick() {
		return mem_nick;
	}
	public void setMem_nick(String mem_nick) {
		this.mem_nick = mem_nick;
	}
	public String getMem_point() {
		return mem_point;
	}
	public void setMem_point(String mem_point) {
		this.mem_point = mem_point;
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
		return "ChartMemberTableVO [mem_id=" + mem_id + ", mem_name=" + mem_name + ", mem_email=" + mem_email
				+ ", mem_phone=" + mem_phone + ", mem_nick=" + mem_nick + ", mem_point=" + mem_point + ", totalAmount="
				+ totalAmount + ", totalPrice=" + totalPrice + "]";
	}
	
	
	
}
