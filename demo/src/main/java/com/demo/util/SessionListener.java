package com.demo.util;

import java.util.Hashtable;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

/*
 * 세션에 속성이 추가되거나 삭제될 때, 호출되는 리스너
 */
public class SessionListener implements HttpSessionBindingListener {

	// 싱글톤 패턴
	private static SessionListener sessionListener = null;
	
	public static synchronized SessionListener getInstance() {
		if(sessionListener == null) {
			sessionListener = new SessionListener();
		}
		return sessionListener;
	}
	
	// 로그인 한 접속자를 저장
	// <세션, 아이디> 쌍으로 저장
	private static Hashtable<HttpSession, String> loginUsers = new Hashtable<HttpSession, String>();
	
	
	
	/* 세션에 속성이 추가될 때 자동 호출 */
	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		// 추가된 세션과 로그인 아이디를 Hashtable에 저장
		loginUsers.put(event.getSession(), event.getName());
		System.out.println("=====로그인 한 아이디 : " + event.getName());
		System.out.println("=====count: "+ getUserCount());
	}

	/* 세션에 속성이 제거될 때 자동 호출 */
	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		// 삭제할 세션과 로그인 아이디를 Hashtable에서 삭제
		loginUsers.remove(event.getSession());
		System.out.println("=====로그아웃 한 아이디: " + event.getName());
		System.out.println("=====count: "+ getUserCount());
	}
	
	
	
	
	/* 해당 아이디가 이미 세션에 존재하는지 확인 */
	public boolean isUsing(String loginId) {
		return loginUsers.containsValue(loginId);
	}
	
	/* 로그인 아이디 세션 추가 */
	public void setSession(HttpSession session, String loginId) {
		// 세션에 해당 아이디와 리스너 저장(해당 아이디에 리스너를 달아둠)
		// valueBound() 자동 호출
		session.setAttribute(loginId, this); 
	}
	
	/* 현재 접속자 수 */
	public int getUserCount() {
		return loginUsers.size();
	}
	
	
	
	
	

}
