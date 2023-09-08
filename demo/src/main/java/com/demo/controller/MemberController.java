package com.demo.controller;

import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.demo.domain.MemberVO;
import com.demo.dto.MemberDTO;
import com.demo.service.MemberService;
import com.demo.util.SessionListener;

@Controller
@RequestMapping("/member/*")
public class MemberController {
	
	@Inject
	MemberService service;
	
	@Inject
	private BCryptPasswordEncoder crptPassEnc;
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	/* 로그인(GET) */
	@RequestMapping(value="login", method=RequestMethod.GET)
	public void loginGET() {
	}
	
	/* 로그인(POST) */
	@RequestMapping(value="loginPost", method=RequestMethod.POST)
	public String loginPOST(MemberDTO dto, RedirectAttributes rttr, HttpSession session, Model model, 
							HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		logger.info("=====loginPost() execute...");
		MemberDTO memDTO = service.login(dto);
		SessionListener listener =  SessionListener.getInstance();
		
		// 로그인 성공
		if(memDTO != null) { 
			// 중복로그인 확인
			if(listener.isUsing(memDTO.getMem_id())) {
				//// 중복 로그인인 경우, 로그인 실패 처리
				logger.info("=====로그인 실패_중복 로그인");
				rttr.addFlashAttribute("msg", "LOGIN_DUPLICATE");
				return "redirect:/member/login";
			}
			
			// 중복로그인이 아닌 경우, 로그인 성공 처리
			logger.info("=====로그인 성공");
			logger.info("useCookie? " + dto.isUseCookie());
			
			// 쿠키 사용여부 전달
			memDTO.setUseCookie(dto.isUseCookie());
			
			// 세션 작업
			listener.setSession(session, memDTO.getMem_id()); //해당 아이디에 리스너 추가
			session.setAttribute("user", memDTO);
			
			// 자동로그인을 사용할 경우 -> 쿠키 값 생성 및 저장
			if(memDTO.isUseCookie()) {
				
				// 로그인 쿠키 생성
				if(request.getParameter("useCookie")!= null) {
					logger.info("===== 쿠키 사용 :: 쿠키 생성 중... ");
					
					Cookie loginCookie = new Cookie("loginCookie", session.getId());
					loginCookie.setPath("/"); // 모든 경로에서 접근 가능 설정
					loginCookie.setMaxAge(60*60*24*7); // 7일
					response.addCookie(loginCookie);
				}
				
				// 쿠키 값 DB 저장
				int amount= 60*60*24*7; //7일 간 저장
				Date sessionLimit = new Date(System.currentTimeMillis()+(1000*amount));
				
				logger.info("=====자동로그인 설정돼있음 -> DB에 세션쿠키 값 저장 중...");
				service.saveCookie(session.getId(), sessionLimit, memDTO.getMem_id());
				
			}
			rttr.addFlashAttribute("msg", "LOGIN_SUCCESS");
			
			// dest 설정
			String dest = (String) session.getAttribute("dest");
			return "redirect:" + (dest != null ? dest : "/");
			
		// 로그인 실패
		} else {		 
			logger.info("=====로그인 실패");
			rttr.addFlashAttribute("msg", "LOGIN_FAIL");
			return "redirect:/member/login";
		}
		
	}
	
	/* 로그아웃 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(HttpSession session, HttpServletRequest request, 
						HttpServletResponse response, RedirectAttributes rttr) throws Exception {
		
		logger.info("=====logout execute()...");
	
		// 쿠키 삭제 및 DB 쿠키 값 없앰
		Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");
		if(loginCookie != null) {
			loginCookie.setPath("/");
			loginCookie.setMaxAge(0);
			response.addCookie(loginCookie);
			
			MemberDTO dto = (MemberDTO)session.getAttribute("user");
			
			service.saveCookie("none", new Date(), dto.getMem_id());
		}
		
		// 세션 무효화
		session.invalidate();
		
		// 로그아웃 성공 메시지 전송
		rttr.addFlashAttribute("msg", "LOGOUT_SUCCESS");
		
		return "redirect:/";
	}
	
	
	/* 회원가입(GET) */
	@RequestMapping(value="join", method=RequestMethod.GET)
	public void joinGET() {
	}
	
	/* 회원가입(POST) */
	@RequestMapping(value="join", method=RequestMethod.POST)
	public String joinPOST(MemberVO vo, RedirectAttributes rttr) throws Exception {
		
		// 비밀번호 암호화
		vo.setMem_pw(crptPassEnc.encode(vo.getMem_pw()));
		
		service.join(vo);
		rttr.addFlashAttribute("msg", "REGISTER_SUCCESS");
		
		return "redirect:/";
	}
	
	
	/* 아이디 중복체크 */
	@ResponseBody
	@RequestMapping(value = "checkIdDuplicate", method=RequestMethod.POST)
	public ResponseEntity<String> checkIdDuplicate(@RequestParam("mem_id") String mem_id) throws Exception {
		
		logger.info("=====checkIdDuplicate execute()...");
		ResponseEntity<String> entity = null;
		try {
			int count = service.checkIdDuplicate(mem_id);
			if(count>0) {
				// 아이디 중복
				entity = new ResponseEntity<String>("FAIL", HttpStatus.OK);
			} else {
				// 사용가능한 아이디
				entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	/* 
	 * 이메일 인증 코드 확인 
	 * - 입력된 인증 코드와 세션에 저장해 두었던 인증 코드가 일치하는지 확인
	 */
	@ResponseBody
	@RequestMapping(value = "checkAuthcode", method=RequestMethod.POST)
	public ResponseEntity<String> checkAuthcode(@RequestParam("code") String code, HttpSession session){
		
		ResponseEntity<String> entity = null;
		
		try {
			if(code.equals(session.getAttribute("authcode"))) {
				// 인증코드 일치
				entity= new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
			} else {
				// 인증코드 불일치
				entity= new ResponseEntity<String>("FAIL", HttpStatus.OK);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			entity= new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	/* 비밀번호 재확인(GET) - 회원 정보 수정을 위함 */
	@RequestMapping(value="checkPw", method=RequestMethod.GET)
	public void checkPwGET(@ModelAttribute("url") String url) {
		
	}
	
	/* 
	 * 비밀번호 재확인(POST) - 회원 정보 수정을 위함
	 * 받은 url에 해당하는 jsp를 출력
	 * 
	 * @Params
	 * String url: 이동할 jsp페이지 이름
	 * 
	 * @return
	 * String : 받은 url에 일치하는 jsp호출
	 */
	@RequestMapping(value="checkPw", method=RequestMethod.POST)
	public String checkPwPOST(@RequestParam("url") String url,
							  @RequestParam("mem_pw") String pw,
							  HttpSession session, Model model) throws Exception {
		
		logger.info("=====checkPw() execute..."); 
		logger.info("=====url: " + url + ", mem_pw: " + pw); 
		
		MemberDTO dto = (MemberDTO) session.getAttribute("user");
		//logger.info("=====세션 저장 값: " + dto.toString());
		
		if(crptPassEnc.matches(pw, dto.getMem_pw())) {
			// 비밀번호가 일치하는 경우, url 확인 
			if(url.equals("modify")) {
				model.addAttribute("vo", service.readUserInfo(dto.getMem_id()));
				return "/member/modify";
				
			} else if(url.equals("changePw")) {
				return "/member/changePw";
				
			} else if(url.equals("delete")) {
				model.addAttribute("email", service.getEmail(dto.getMem_id()));
				return "/member/delete";
				
			}
		} 
		
		// 비밀번호가 일치하지 않거나, url이 정해진 url이 아닌 경우
		model.addAttribute("url", url);
		model.addAttribute("msg", "CHECK_PW_FAIL");
		return "/member/checkPw";
	}
	
	/*
	 * 비밀번호 확인 Ajax용
	 */
	@ResponseBody
	@RequestMapping("checkPwAjax")
	public ResponseEntity<String> checkPwAjax(@RequestParam("mem_pw") String mem_pw, HttpSession session) {
		
		logger.info("=====checkPwAjax() execute...");
		ResponseEntity<String> entity = null;
		MemberDTO dto = (MemberDTO) session.getAttribute("user");
		logger.info("=====mem_pw: " + mem_pw);
		logger.info("=====dto: " + dto.toString());
		
		if(crptPassEnc.matches(mem_pw, dto.getMem_pw())) {
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
			
		} else {
			entity = new ResponseEntity<String>("FAIL", HttpStatus.OK);
		}
		
		return entity;
	}
	
	
	/* 회원 정보 수정(POST) */
	@RequestMapping(value="modify", method=RequestMethod.POST)
	public String modifyPOST(MemberVO vo, RedirectAttributes rttr, HttpSession session) throws Exception {

		MemberDTO dto = new MemberDTO();
		dto.setMem_id(vo.getMem_id());
		dto.setMem_pw(vo.getMem_pw());
		
		vo.setMem_pw(crptPassEnc.encode(vo.getMem_pw()));
		service.modifyUserInfo(vo);
		session.setAttribute("user", service.login(dto));
		
		rttr.addFlashAttribute("msg", "MODIFY_USER_SUCCESS");
		
		return "redirect:/";
	}
	
	/* 비밀번호 변경(POST) */
	@RequestMapping(value="changePw", method=RequestMethod.POST)
	public String changePWPOST(MemberDTO dto, RedirectAttributes rttr, HttpSession session) throws Exception {
		
		logger.info("=====changePWPOST() execute...");
		// 비밀번호 암호화 후 변경
		dto.setMem_pw(crptPassEnc.encode(dto.getMem_pw()));
		service.changePw(dto);
		
		// 세션의 비밍번호 재설정
		MemberDTO memDTO = (MemberDTO) session.getAttribute("user");
		memDTO.setMem_pw(dto.getMem_pw());
		session.setAttribute("user", memDTO);
		
		rttr.addFlashAttribute("msg", "CHANGE_PW_SUCCESS");
		return "redirect:/";
	}
	
	/* 회원 탈퇴{POST) */
	@RequestMapping(value="delete", method=RequestMethod.POST)
	public String deletePOST(String mem_id, HttpSession session, RedirectAttributes rttr) throws Exception {
		
		logger.info("=====deletePOST() execute...");
		
		service.deleteUser(mem_id);
		
		
		
		
		session.invalidate();
		rttr.addFlashAttribute("msg", "DELETE_USER_SUCCESS");
		
		return "redirect:/";
	}
	
}
