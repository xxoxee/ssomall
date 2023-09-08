package com.demo.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.dao.MemberDAO;
import com.demo.domain.MemberVO;
import com.demo.dto.MemberDTO;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDAO dao;
	
	@Inject
	private BCryptPasswordEncoder crptPassEnc;
	
	
	// memberVO 가져오기
	@Override
	public MemberVO readUserInfo(String mem_id) throws Exception {
		return dao.readUserInfo(mem_id);
	}
	
	// 로그인
	@Transactional
	@Override
	public MemberDTO login(MemberDTO dto) throws Exception {
		MemberDTO memDTO = dao.login(dto);
		
		if(memDTO != null) {
			// 비밀번호가 암호화 된 비밀번호와 일치하는지 확인
			if(crptPassEnc.matches(dto.getMem_pw(), memDTO.getMem_pw())) {
				dao.loginUpdate(memDTO.getMem_id());
			} else { 
			// 비밀번호가 일치하지 않으면, null 반환
				memDTO = null;
			}
		}
		return memDTO;
	}

	// 회원가입
	@Override
	public void join(MemberVO vo) throws Exception {
		dao.join(vo);
	}

	// 아이디 중복 체크
	@Override
	public int checkIdDuplicate(String mem_id) throws Exception {
		return dao.checkIdDuplicate(mem_id);
	}

	// 회원 정보 수정
	@Override
	public void modifyUserInfo(MemberVO vo) throws Exception {
		dao.modifyUserInfo(vo);
	}

	// 비밀번호 변경
	@Override
	public void changePw(MemberDTO dto) throws Exception {
		dao.changePw(dto);
	}
	
	// 회원탈퇴
	@Override
	public void deleteUser(String mem_id) throws Exception {
		dao.deleteUser(mem_id);
	}

	// 로그인 정보 쿠키 저장
	@Override
	public void saveCookie(String sessionKey, Date sessionLimit, String mem_id) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mem_session_key", sessionKey);
		map.put("mem_session_limit", sessionLimit);
		map.put("mem_id", mem_id);
		
		dao.saveCookie(map);
	}

	// 쿠키에 저장된 세션값으로 로그인 정보 가져옴
	@Override
	public MemberDTO checkUserSession(String value) throws Exception {
		return dao.checkUserSession(value);
	}

	// 이메일 가져오기
	@Override
	public String getEmail(String mem_id) throws Exception {
		return dao.getEmail(mem_id);
	}


}
