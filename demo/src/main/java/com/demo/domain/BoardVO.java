package com.demo.domain;

import java.util.Date;

/*
 * 상품 Q&A 계층형(답변형)게시판 VO 객체
 */
public class BoardVO {

	private int		brd_num;
	private String 	mem_id;
	private String 	brd_title;
	private String 	brd_content;
	private Date 	brd_date_reg;
	// 계층형 게시판을 위해 추가한 컬럼
	private int		brd_group;
	private int		brd_order;
	private int		brd_level;
	private int 	brd_count;
	private String 	brd_is_del;
	
	/* Getter and Setter */
	public int getBrd_num() {
		return brd_num;
	}

	public void setBrd_num(int brd_num) {
		this.brd_num = brd_num;
	}

	public String getMem_id() {
		return mem_id;
	}

	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}

	public String getBrd_title() {
		return brd_title;
	}

	public void setBrd_title(String brd_title) {
		this.brd_title = brd_title;
	}

	public String getBrd_content() {
		return brd_content;
	}

	public void setBrd_content(String brd_content) {
		this.brd_content = brd_content;
	}

	public Date getBrd_date_reg() {
		return brd_date_reg;
	}

	public void setBrd_date_reg(Date brd_date_reg) {
		this.brd_date_reg = brd_date_reg;
	}

	public int getBrd_group() {
		return brd_group;
	}

	public void setBrd_group(int brd_group) {
		this.brd_group = brd_group;
	}

	public int getBrd_order() {
		return brd_order;
	}

	public void setBrd_order(int brd_order) {
		this.brd_order = brd_order;
	}

	public int getBrd_level() {
		return brd_level;
	}

	public void setBrd_level(int brd_level) {
		this.brd_level = brd_level;
	}

	public int getBrd_count() {
		return brd_count;
	}

	public void setBrd_count(int brd_count) {
		this.brd_count = brd_count;
	}
	
	public String getBrd_is_del() {
		return brd_is_del;
	}

	public void setBrd_is_del(String brd_is_del) {
		this.brd_is_del = brd_is_del;
	}


	/* toString() */
	@Override
	public String toString() {
		return "BoardVO [brd_num=" + brd_num + ", mem_id=" + mem_id + ", brd_title=" + brd_title + ", brd_content="
				+ brd_content + ", brd_date_reg=" + brd_date_reg + ", brd_group=" + brd_group + ", brd_order="
				+ brd_order + ", brd_level=" + brd_level + ", brd_count=" + brd_count + ", brd_is_del=" + brd_is_del
				+ "]";
	}
	

	
}
