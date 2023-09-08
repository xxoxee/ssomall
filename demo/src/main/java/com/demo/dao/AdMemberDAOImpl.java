package com.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.demo.domain.AdMemberListVO;
import com.demo.domain.MemberVO;
import com.demo.util.Criteria;

@Repository
public class AdMemberDAOImpl implements AdMemberDAO {

	@Autowired
	private SqlSession session;
	public final static String NS="com.demo.mappers.AdMemberMapper";
	
	
	// 모든 회원 목록(총 주문 횟수)
	@Override
	public int memberTotalOrder(String mem_id) throws Exception {
		return session.selectOne(NS+".memberTotalOrder", mem_id);
	}
	
	// 모든 회원 목록 (총 주문 개수)
	@Override
	public int memberTotalPrice(String mem_id) throws Exception {
		return session.selectOne(NS+ ".memberTotalPrice", mem_id);
	}
	

	// 모든 회원의 이메일 주소를 가져옴
	@Override
	public List<String> memberEmailAll() throws Exception {
		return session.selectList(NS +".memberEmailAll");
	}

	// 검색 회원 목록
	@Override
	public List<AdMemberListVO> memberListSearch(Map<String, Object> map) throws Exception {
		return session.selectList(NS +".memberListSearch", map);
	}

	// 검색 회원 수
	@Override
	public int countListSearch(Map<String, Object> map) throws Exception {
		return session.selectOne(NS+ ".countListSearch", map);
	}

	// 회원 상세 정보
	@Override
	public MemberVO readMemberDetail(String mem_id) throws Exception {
		return session.selectOne(NS+ ".readMemberDetail", mem_id);
	}
	
}
