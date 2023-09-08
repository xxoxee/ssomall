package com.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dao.AdMemberDAO;
import com.demo.domain.AdMemberListVO;
import com.demo.util.SearchCriteria;

@Service
public class AdMemberServiceImpl implements AdMemberService {

	@Autowired
	private AdMemberDAO dao;

	/* 모든 회원의 이메일 주소 */
	@Override
	public List<String> memberEmailAll() throws Exception {
		return dao.memberEmailAll();
	}

	/* 검색/전체 회원 목록 */
	@Override
	public List<AdMemberListVO> memberListSearch(SearchCriteria cri, String accept) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cri", cri);
		map.put("accept", accept);
		
		List<AdMemberListVO> list = dao.memberListSearch(map);
		if(list.size()>0) {
			for(AdMemberListVO vo : list) {
				vo.setMem_total_order(dao.memberTotalOrder(vo.getMem_id()));
				vo.setMem_total_price(dao.memberTotalPrice(vo.getMem_id()));
			}
		}
		
		return list;
	}

	/* 검색/전체 회원 수 */
	@Override
	public int countListSearch(SearchCriteria cri, String accept) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cri", cri);
		map.put("accept", accept);
		
		return dao.countListSearch(map);
	}

	/* 회원 상세 정보 */
	@Override
	public Map<String, Object> readMemberDetail(String mem_id) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberVO", dao.readMemberDetail(mem_id));
		map.put("totalOrder", dao.memberTotalOrder(mem_id));
		map.put("totalPrice", dao.memberTotalPrice(mem_id));
		
		return map;
	}
	
	
	
	
	

}
