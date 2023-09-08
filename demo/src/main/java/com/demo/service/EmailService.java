package com.demo.service;

import com.demo.dto.EmailDTO;

public interface EmailService {

	// 메일 보내기
	public void sendMail(EmailDTO dto);
	
}
