package com.demo.domain;

import java.util.Date;

/*
 * 관리자 - 주문목록에 사용될 VO객체
 */
public class AdOrderListVO {

	private String pdt_img;
	private String pdt_name;
	private int odr_code;
	private int odr_status;
	private int pdt_num;
	private int odr_amount;
	private int odr_price;
	private String mem_id;
	private int odr_total_price; 
	private Date odr_date;
	
	/* Getter and Setter */
	public String getPdt_img() {
		return pdt_img;
	}
	public void setPdt_img(String pdt_img) {
		this.pdt_img = pdt_img;
	}
	public String getPdt_name() {
		return pdt_name;
	}
	public void setPdt_name(String pdt_name) {
		this.pdt_name = pdt_name;
	}
	public int getOdr_status() {
		return odr_status;
	}
	public void setOdr_status(int odr_status) {
		this.odr_status = odr_status;
	}
	public int getOdr_code() {
		return odr_code;
	}
	public void setOdr_code(int odr_code) {
		this.odr_code = odr_code;
	}
	public int getPdt_num() {
		return pdt_num;
	}
	public void setPdt_num(int pdt_num) {
		this.pdt_num = pdt_num;
	}
	public int getOdr_amount() {
		return odr_amount;
	}
	public void setOdr_amount(int odr_amount) {
		this.odr_amount = odr_amount;
	}
	public int getOdr_price() {
		return odr_price;
	}
	public void setOdr_price(int odr_price) {
		this.odr_price = odr_price;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public int getOdr_total_price() {
		return odr_total_price;
	}
	public void setOdr_total_price(int odr_total_price) {
		this.odr_total_price = odr_total_price;
	}
	public Date getOdr_date() {
		return odr_date;
	}
	public void setOdr_date(Date odr_date) {
		this.odr_date = odr_date;
	}
	
	/* toString() */
	@Override
	public String toString() {
		return "AdOrderListVO [pdt_img=" + pdt_img + ", pdt_name=" + pdt_name + ", odr_code=" + odr_code
				+ ", odr_status=" + odr_status + ", pdt_num=" + pdt_num + ", odr_amount=" + odr_amount + ", odr_price="
				+ odr_price + ", mem_id=" + mem_id + ", odr_total_price=" + odr_total_price + ", odr_date=" + odr_date
				+ "]";
	}
	
}
