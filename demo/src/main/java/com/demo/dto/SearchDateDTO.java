package com.demo.dto;

/*
 * 기간 검색에 필요한 DTO 객체 
 */
public class SearchDateDTO {

	/*
	 * int term
	 * 전체 : 0
	 * 오늘 : 1
	 * 어제 : 2
	 * 3일 : 3
	 * 7일 : 4
	 * 15일 : 5
	 * 1개월 : 6
	 * 3개월 : 7
	 * 6개월 : 8
	 * 직접설정 : null
	 * 
	 */
	private Integer term;
	private String startDate;
	private String endDate;
	
	/* Constructor */
	public SearchDateDTO() {
		this.term = 0;
	}
	
	/* Getter and Setter */
	public Integer getTerm() {
		return term;
	}
	public void setTerm(Integer term) {
		this.term = term;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	
	/* toString() */
	@Override
	public String toString() {
		return "SearchDateDTO [term=" + term + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}
	
	
	
}
