package com.demo.dto;

public class AdminDTO {

	private String 	admin_id;
	private String 	admin_pw;
	
	/* Getter and Setter */
	public String getAdmin_id() {
		return admin_id;
	}
	public void setAdmin_id(String admin_id) {
		this.admin_id = admin_id;
	}
	public String getAdmin_pw() {
		return admin_pw;
	}
	public void setAdmin_pw(String admin_pw) {
		this.admin_pw = admin_pw;
	}
	
	/* toString() */
	@Override
	public String toString() {
		return "AdminDTO [admin_id=" + admin_id + ", admin_pw=" + admin_pw + "]";
	}
	
	
	
	
	
}
