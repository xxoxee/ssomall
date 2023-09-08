package com.demo.service;

import java.util.List;
import java.util.Map;

import com.demo.domain.AdMemberListVO;
import com.demo.util.SearchCriteria;

public interface AdMemberService {

	// 모든 회원의 이메일 주소
	public List<String> memberEmailAll() throws Exception;
	
	// 검색/전체 회원 목록
	public List<AdMemberListVO> memberListSearch(SearchCriteria cri, String accept) throws Exception;
	
	// 검색/전체 회원 수 
	public int countListSearch(SearchCriteria cri, String accept) throws Exception;
	
	// 회원 상세 정보
	public Map<String, Object> readMemberDetail(String mem_id) throws Exception;
}
