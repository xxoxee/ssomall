package com.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.demo.domain.AdOrderListVO;
import com.demo.domain.OrderReadDetailVO;
import com.demo.domain.OrderVO;
import com.demo.util.Criteria;

@Repository
public class AdOrderDAOImpl implements AdOrderDAO {

	@Autowired
	private SqlSession session;
	public final static String NS = "com.demo.mappers.AdOrderMapper";

	// 모든 주문 목록(페이징)
	@Override
	public List<AdOrderListVO> orderListAll(Map<String, Object> map) throws Exception {
		return session.selectList(NS +".orderListAll", map);
	}

	// 총 주문 개수
	@Override
	public int countListAll(Map<String, Object> map) throws Exception {
		return session.selectOne(NS+ ".countListAll", map);
	}
	
	// 배송현황 수정
	@Override
	public void updateStatus(Map<String, Object> map) throws Exception {
		session.update(NS+".updateStatus", map);
	}

	// 주문 상세 정보
	@Override
	public List<OrderReadDetailVO> readOrder(int odr_code) throws Exception {
		return session.selectList(NS+ ".readOrder", odr_code);
	}

	// 주문자 정보
	@Override
	public OrderVO getOrder(int odr_code) throws Exception {
		return session.selectOne(NS+ ".getOrder", odr_code);
	}

	
}
