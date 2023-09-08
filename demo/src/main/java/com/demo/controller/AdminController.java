package com.demo.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.domain.AdminVO;
import com.demo.dto.AdminDTO;
import com.demo.service.AdminService;
import com.demo.util.AdminSessionListener;

@Controller
@RequestMapping("/admin/*")
public class AdminController {

	@Autowired
	private AdminService service;
	
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	// 관리자 메인
	@RequestMapping(value="/main", method=RequestMethod.GET)
	public String adminMain() {
		return "admin/main";
	}
	
	// 관리자 로그인(POST)
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String loginPOST(AdminDTO dto, RedirectAttributes rttr, HttpSession session) throws Exception {
		logger.info("=====login(POST) execute()");
		logger.info("=====AdminDTO : " + dto.toString());
	
		AdminSessionListener listener =  AdminSessionListener.getInstance();
		
		AdminVO vo = null; 
		vo = service.login(dto);
		String msg = "";
		
		if(vo!= null) {
			// 로그인 성공 시, 중복로그인 확인
			if(listener.isUsing(vo.getAdmin_id())){
				// 중복 로그인인 경우, 로그인 실패 처리
				msg = "LOGIN_DUPLICATE";
				rttr.addFlashAttribute("msg", msg);
				return "redirect:/admin/main";
				
			} else {
				// 중복 로그인이 아닌 경우, 로그인 성공 처리
				listener.setSession(session, vo.getAdmin_id()); //해당 아이디에 리스너 추가
				session.setAttribute("admin", vo); 
				
				msg = "LOGIN_SUCCESS";
				rttr.addFlashAttribute("msg", msg);
				
				
				// dest 설정
				String dest = (String) session.getAttribute("adminDest");
				logger.info("=====dest: " + dest);
				return "redirect:" + (dest != null ? dest : "/admin/main");
			}
			
		} else {
			// 로그인 실패 시
			msg = "LOGIN_FAIL";
		}
		rttr.addFlashAttribute("msg", msg);
		return "redirect:/admin/main";
	}
	
	// 로그아웃(GET)
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(HttpSession session, RedirectAttributes rttr) {
		
		logger.info("====== logout(POST) execute");
		
		session.invalidate();
		rttr.addFlashAttribute("msg", "LOGOUT_SUCCESS");
		
		return "redirect:/admin/main";
	}
	
}
