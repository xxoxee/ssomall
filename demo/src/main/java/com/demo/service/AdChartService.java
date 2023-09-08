package com.demo.service;

import java.util.List;
import java.util.Map;

import com.demo.domain.ChartDateVO;
import com.demo.domain.ChartMemberTableVO;
import com.demo.domain.ChartProductTableVO;
import com.demo.domain.ChartProductVO;
import com.demo.dto.ChartMemberDTO;

public interface AdChartService {

	/* 기간별 통계 */
	// 연간(최근 5년 간) 주문 횟수 / 주문 가격
	public List<ChartDateVO> chartYearCount() throws Exception;
	
	// 월간(최근 1년 간) 주문 횟수 / 주문 가격
	public List<ChartDateVO> chartMonthCount() throws Exception;
	
	// 일간(최근 10일 간) 주문 횟수 / 주문 가격
	public List<ChartDateVO> chartDayCount() throws Exception;
	
	
	/* 상품별 통계 */
	// 상품별 가장 많이 팔린 상품 (가격 순)
	public List<ChartProductVO> chartProductPrice(String cg_code, String cg_code_prt, String startDate, String endDate) throws Exception;
	
	// 상품별 가장 많이 팔린 상품 (수량 순)
	public List<ChartProductVO> chartProductAmount(String cg_code, String cg_code_prt, String startDate, String endDate) throws Exception;
	
	// 상품별 가장 많이 팔린 상품 (가격 순) - 테이블
	public List<ChartProductTableVO> tableProductPrice(String cg_code, String cg_code_prt, String startDate, String endDate) throws Exception;
	
	// 상품별 가장 많이 팔린 상품 (상품 순) - 테이블
	public List<ChartProductTableVO> tableProductAmount(String cg_code, String cg_code_prt, String startDate, String endDate) throws Exception;
	
	
	/* 회원별 통계 */
	// 구매수량/ 금액 순 회원 차트
	public List<ChartMemberDTO> chartMemberAmount(String searchDate, String period) throws Exception;
	public List<ChartMemberDTO> chartMemberPrice(String searchDate, String period) throws Exception;
	// 구매수량/ 금액 순 회원 테이블
	public List<ChartMemberTableVO> tableMemberAmount(String searchDate, String period) throws Exception;
	public List<ChartMemberTableVO> tableMemberPrice(String searchDate, String period) throws Exception;
}
