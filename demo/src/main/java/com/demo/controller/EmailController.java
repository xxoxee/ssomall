package com.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.dto.EmailDTO;
import com.demo.service.AdMemberService;
import com.demo.service.EmailService;

@Controller
@RequestMapping("/email/*")
public class EmailController {
	
	@Inject
	private EmailService emailService;
	
	@Inject
	private AdMemberService service;
	
	private static final Logger logger = LoggerFactory.getLogger(EmailController.class);
	
	/* 이메일 인증코드 보내기 */
	@ResponseBody
	@RequestMapping("sendAuthCode")
	public ResponseEntity<String> sendAuthCode(@ModelAttribute EmailDTO dto, HttpSession session) {
		
		logger.info("=====email sendAuthCode execute()...");
		logger.info("=====EmailDTO : " + dto.toString());
		
		ResponseEntity<String> entity = null;
		
		// 6자리 인증 코드 생성
		String authcode = "";
		for(int i=0; i<6; i++) {
			authcode += String.valueOf((int)(Math.random()*10));
		}
		// 인증코드를 세션에 저장
		session.setAttribute("authcode", authcode);
		logger.info("=====authcode: " + authcode);
		
		try {
			dto.setSubject("[SSOMall] 인증코드입니다.");
			dto.setMessage("이메일 인증을 위해," +"\n"
					+ "아래 인증코드를 이메일 인증코드 란에 입력하세요.\n\n"
					+ "인증코드: " +authcode);
			emailService.sendMail(dto);
			entity = new ResponseEntity<String>(HttpStatus.OK);
			
		} catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	/* 한명에게 메일 보내기 */
	@ResponseBody
	@RequestMapping(value="sendOne", method=RequestMethod.POST)
	public ResponseEntity<String> sendOne(@ModelAttribute EmailDTO dto) {
		
		logger.info("=====email sendOne execute()...");
		logger.info("=====dto: " + dto.toString());
		
		ResponseEntity<String> entity = null;
		
		try {
			emailService.sendMail(dto);
			entity = new ResponseEntity<String>(HttpStatus.OK);
			
		} catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
		
	}
	
	/* 다수에게 메일 보내기 - 메일 직접 쓰기 */
	@RequestMapping(value="sendMany", method=RequestMethod.POST)
	public String sendMany(@RequestParam("mem_email") List<String> emailList,
							Model model) {
		
		logger.info("=====email sendMany execute()...");
		
		
		EmailDTO dto = new EmailDTO();
		dto.setReceiveMail(emailList);
		
		model.addAttribute("dto", dto);
		return "/email/write";
	}
	
	
	
	/* 선택 회원에게 메일 보내기 */
	@RequestMapping(value="sendCheck", method=RequestMethod.POST)
	public String sendCheck(@RequestParam("check") List<String> checkList,
							@RequestParam("mem_id") List<String> idList,
							@RequestParam("mem_email") List<String> emailList,
							Model model) throws Exception{
		
		logger.info("=====email sendCheck execute()...");
		
		List<String> checkEmailList = new ArrayList<String>();
		
		// 체크 값과 아이디 비교
		for(int i=0; i<checkList.size(); i++) {
			for(int j=0; j<idList.size(); j++) {
				if(checkList.get(i).equals(idList.get(j))){
					checkEmailList.add(emailList.get(j));
					break;
				}
			}
		}
		logger.info("=====emailList : " + checkEmailList.toString());
			
		EmailDTO dto = new EmailDTO();
		dto.setReceiveMail(checkEmailList);
		
		model.addAttribute("dto", dto);
		
		return "/email/write";
	}
	
	/* 모든 회원에게 메일 보내기 */
	@RequestMapping(value="sendAll", method=RequestMethod.POST)
	public String sendAll(Model model) throws Exception{
		
		logger.info("=====email sendAll execute()...");
		
		EmailDTO dto = new EmailDTO();
		dto.setReceiveMail(service.memberEmailAll());
		
		model.addAttribute("dto", dto);
		
		return "/email/write";
	}
	
	/* 실제 전송 */
	@RequestMapping(value="sendMail", method=RequestMethod.POST)
	public String sendMail(EmailDTO dto, RedirectAttributes rttr) throws Exception{
		
		logger.info("=====sendMail() execute...");
		logger.info("=====보낼 메일 정보: " + dto.toString());
		
		try {
			emailService.sendMail(dto);
			rttr.addFlashAttribute("msg", "SEND_EMAIL_SUCCESS");
			
		} catch(Exception e) {
			e.printStackTrace();
			rttr.addFlashAttribute("msg", "SEND_EMAIL_FAIL");
		}
		
		
		return "redirect:/admin/member/list";
	}
	
}
