package com.demo.service;

import java.util.List;

import javax.inject.Inject;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.demo.dto.EmailDTO;

@Service
public class EmailServiceImpl implements EmailService {
	
	@Inject
	JavaMailSender mailSender;

	/*
	 * 이메일 전송
	 */
	@Override
	public void sendMail(EmailDTO dto) {
		MimeMessage msg = mailSender.createMimeMessage();
		
		try {
			// 보내는 사람 설정(이메일, 이름)
			msg.addFrom(new InternetAddress[] { new InternetAddress(dto.getSenderMail(), dto.getSenderName())});
			// 이메일 제목
			msg.setSubject(dto.getSubject(), "utf-8");
			// 이메일 본문(html 형식으로 전송)
			msg.setText(dto.getMessage(), "utf-8", "html");
			
			/* 받는 사람 설정(이메일) */
			List<String> emailList = dto.getReceiveMail();
			
			if(emailList.size()==1) {
				// 받는 사람이 한 명일 경우,
				msg.addRecipient(RecipientType.TO, new InternetAddress(emailList.get(0)));
				
			} else {
				// 받는 사람이 여러명일 경우,
				InternetAddress[] recipient = new InternetAddress[emailList.size()];
				
				for(int i=0; i<emailList.size(); i++) {
					recipient[i] = new InternetAddress(emailList.get(i));
				}
				msg.setRecipients(RecipientType.TO, recipient);
			}
			
			// 메일 보내기
			mailSender.send(msg);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

}
