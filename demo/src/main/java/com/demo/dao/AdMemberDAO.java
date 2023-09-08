package com.demo.dao;

import java.util.List;
import java.util.Map;

import com.demo.domain.AdMemberListVO;
import com.demo.domain.MemberVO;

public interface AdMemberDAO {
	
	// 모든 회원 목록(총 주문 횟수)
	public int memberTotalOrder(String mem_id) throws Exception;
	
	// 모든 회원 목록(총 주문 개수)
	public int memberTotalPrice(String mem_id) throws Exception;
	
	// 모든 회원의 이메일 주소
	public List<String> memberEmailAll() throws Exception;
	
	// 검색/전체 회원 목록
	public List<AdMemberListVO> memberListSearch(Map<String, Object> map) throws Exception;
	
	// 검색/전체 회원 수 
	public int countListSearch(Map<String, Object> map) throws Exception;
	
	// 회원 상세 정보
	public MemberVO readMemberDetail(String mem_id) throws Exception;
}
