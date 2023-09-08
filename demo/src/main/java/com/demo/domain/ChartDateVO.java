package com.demo.domain;

public class ChartDateVO {

	private String year;
	private String month;
	private String day;
	private int count;
	private int totalPrice;
	
	/* Getter and Setter */
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
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
		return "ChartDateVO [year=" + year + ", month=" + month + ", day=" + day + ", count=" + count + ", totalPrice="
				+ totalPrice + "]";
	}
	
	
}
