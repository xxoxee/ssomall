package com.demo.domain;

import java.util.Date;

/*
 * 관리자 - 회원목록 출력을 위한 VO 객체
 */
public class AdMemberListVO {

	private String mem_id;
	private String mem_name;
	private String mem_nick;
	private String mem_accept_e;
	private String mem_email;
	private int mem_point;
	private int mem_total_order;
	private int mem_total_price;
	private Date mem_date_sub;
	
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
	public String getMem_nick() {
		return mem_nick;
	}
	public void setMem_nick(String mem_nick) {
		this.mem_nick = mem_nick;
	}
	public String getMem_accept_e() {
		return mem_accept_e;
	}
	public void setMem_accept_e(String mem_accept_e) {
		this.mem_accept_e = mem_accept_e;
	}
	public String getMem_email() {
		return mem_email;
	}
	public void setMem_email(String mem_email) {
		this.mem_email = mem_email;
	}
	public int getMem_point() {
		return mem_point;
	}
	public void setMem_point(int mem_point) {
		this.mem_point = mem_point;
	}
	public int getMem_total_order() {
		return mem_total_order;
	}
	public void setMem_total_order(int mem_total_order) {
		this.mem_total_order = mem_total_order;
	}
	public int getMem_total_price() {
		return mem_total_price;
	}
	public void setMem_total_price(int mem_total_price) {
		this.mem_total_price = mem_total_price;
	}
	public Date getMem_date_sub() {
		return mem_date_sub;
	}
	public void setMem_date_sub(Date mem_date_sub) {
		this.mem_date_sub = mem_date_sub;
	}
	
	/* toString() */
	@Override
	public String toString() {
		return "AdMemberListVO [mem_id=" + mem_id + ", mem_name=" + mem_name + ", mem_nick=" + mem_nick
				+ ", mem_accept_e=" + mem_accept_e + ", mem_email=" + mem_email + ", mem_point=" + mem_point
				+ ", mem_total_order=" + mem_total_order + ", mem_total_price=" + mem_total_price + ", mem_date_sub="
				+ mem_date_sub + "]";
	}
	
	
	
}
