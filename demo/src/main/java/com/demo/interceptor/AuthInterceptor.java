package com.demo.interceptor;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import com.demo.dto.MemberDTO;
import com.demo.service.MemberService;

/*
 * 로그인 정보를 확인하는 인터셉터
 * 로그인하지 않은 사용자일 경우, 로그인 페이지로 이동 시킴
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Inject
	private MemberService service;
	
	private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
	
	/*
	 * preHandler : 컨트롤러보다 먼저 수행
	 * @return
	 * true: 컨트롤러 동작을 진행
	 * false: 컨트롤러의 동작을 실행하지 않음
	 * 
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		logger.info("=====AuthInterceptor preHandle() execute...");
		
		HttpSession session = request.getSession();
		MemberDTO user = (MemberDTO) session.getAttribute("user");
		
		
		
		// 세션에 유저 정보가 없을 경우, 로그인 화면으로 이동
		if(user == null) {
			logger.info("=====This user is not logined.");
			
			// 자동로그인 유저인 경우, pass
			if(isSessionUser(request)) {
				return true;
			}
			// 자동로그인 유저가 아닌 경우, 로그인 페이지로 이동
			saveDestination(request);
			response.sendRedirect("/member/login");
			return false;
		}
		
		return true;
	}
	
	/*
	 * 세션에 이동하려고자 했던 uri 주소를 저장
	 * 로그인 성공 후 해당 페이지로 이동시키기 위함
	 */
	private void saveDestination(HttpServletRequest request) {
		String uri = request.getRequestURI();
		String query = request.getQueryString();
		
		if(query == null || query.equals("null")) {
		// 쿼리스트링이 비어있는 경우
			query = "";
		} else {
		// 쿼리 스트링이 존재하는 경우
			query= "?" +query;
		}
		
		if(request.getMethod().equals("GET")) {
			request.getSession().setAttribute("dest", uri+query);
		}
	}
	
	/*
	 * 자동 로그인 유저인지 세션 값 확인
	 */
	private boolean isSessionUser(HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();
		Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");
		
		if(loginCookie != null) {
			MemberDTO dto = service.checkUserSession(loginCookie.getValue());
			if(dto!= null) {
				logger.info("=====자동로그인 유저입니다. 해당 유저를 session에 추가합니다.");
				session.setAttribute("user", dto);
				return true;
			} 
		}
		
		logger.info("=====자동로그인 유저가 아닙니다.");
		return false;
	}
	
}
