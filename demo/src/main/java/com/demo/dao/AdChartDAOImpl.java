package com.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.demo.domain.ChartDateVO;
import com.demo.domain.ChartMemberTableVO;
import com.demo.domain.ChartProductTableVO;
import com.demo.domain.ChartProductVO;
import com.demo.dto.ChartMemberDTO;

@Repository
public class AdChartDAOImpl implements AdChartDAO {

	@Autowired
	private SqlSession session;
	
	public final static String NS="com.demo.mappers.AdChartMapper";


	/* 연간(최근 5년 간) 주문 횟수 / 주문 가격 */
	@Override
	public List<ChartDateVO> chartYearCount() throws Exception {
		return session.selectList(NS+".chartYearCount");
	}
	
	/* 월간(최근 1년 간) 주문 횟수 / 주문 가격 */
	@Override
	public List<ChartDateVO> chartMonthCount() throws Exception {
		return session.selectList(NS+".chartMonthCount");
	}
	
	/* 일간(최근 10일 간) 주문 횟수 / 주문 가격 */
	@Override
	public List<ChartDateVO> chartDayCount() throws Exception {
		return session.selectList(NS+".chartDayCount");
	}

	
	
	/* 상품별 가장 많이 팔린 상품 (가격 순) */
	@Override
	public List<ChartProductVO> chartProductPrice(Map<String, Object> map) throws Exception {
		return session.selectList(NS+".chartProductPrice", map);
	}

	/* 상품별 가장 많이 팔린 상품 (상품 순) */
	@Override
	public List<ChartProductVO> chartProductAmount(Map<String, Object> map) throws Exception {
		return session.selectList(NS+".chartProductAmount", map);
	}

	/* 상품별 가장 많이 팔린 상품 (가격 순) - 테이블 */
	@Override
	public List<ChartProductTableVO> tableProductPrice(Map<String, Object> map) throws Exception {
		return session.selectList(NS+".tableProductPrice", map);
	}

	/* 상품별 가장 많이 팔린 상품 (수량 순) - 테이블 */
	@Override
	public List<ChartProductTableVO> tableProductAmount(Map<String, Object> map) throws Exception {
		return session.selectList(NS+".tableProductAmount", map);
	}

	
	
	
	/* 회원 별 구매 수량/구매 금액 순 TOP 7 */
	/* 구매수량 순 회원 차트 */
	@Override
	public List<ChartMemberDTO> chartMemberAmount(Map<String, Object> map) throws Exception {
		return session.selectList(NS+".chartMemberAmount", map);
	}
	/* 구매금액 순 회원 차트 */
	@Override
	public List<ChartMemberDTO> chartMemberPrice(Map<String, Object> map) throws Exception {
		return session.selectList(NS+".chartMemberPrice", map);
	}
	/* 구매수량 순 회원 테이블 */
	@Override
	public List<ChartMemberTableVO> tableMemberAmount(Map<String, Object> map) throws Exception {
		return session.selectList(NS+".tableMemberAmount", map);
	}
	/* 구매금액 순 회원 테이블 */
	@Override
	public List<ChartMemberTableVO> tableMemberPrice(Map<String, Object> map) throws Exception {
		return session.selectList(NS+".tableMemberPrice", map);
	}

	
}
