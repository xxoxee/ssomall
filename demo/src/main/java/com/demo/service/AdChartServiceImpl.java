package com.demo.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dao.AdChartDAO;
import com.demo.domain.ChartDateVO;
import com.demo.domain.ChartMemberTableVO;
import com.demo.domain.ChartProductTableVO;
import com.demo.domain.ChartProductVO;
import com.demo.dto.ChartMemberDTO;
import com.demo.dto.SearchDateDTO;

@Service
public class AdChartServiceImpl implements AdChartService {

	@Autowired
	private AdChartDAO dao;

	/* 기간 별 통계 */
	/* 연간(최근 5년 간) 주문 횟수 / 주문 가격 */
	@Override
	public List<ChartDateVO> chartYearCount() throws Exception {
		return dao.chartYearCount();
	}
	
	/* 월간(최근 1년 간) 주문 횟수 / 주문 가격 */
	@Override
	public List<ChartDateVO> chartMonthCount() throws Exception {
		return dao.chartMonthCount();
	}
	
	/* 일간(최근 10일 간) 주문 횟수 / 주문 가격 */
	@Override
	public List<ChartDateVO> chartDayCount() throws Exception {
		return dao.chartDayCount();
	}

	
	
	/* 상품별 통계 */
	/* 상품별 가장 많이 팔린 상품 (가격 순) */
	@Override
	public List<ChartProductVO> chartProductPrice(String cg_code, String cg_code_prt, String startDate, String endDate)
			throws Exception {
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("cg_code", cg_code);
		map.put("cg_code_prt", cg_code_prt);
		map.put("startDate", startDate);
		map.put("endDate", checkEndDate(endDate));
		
		return dao.chartProductPrice(map);
	}

	/* 상품별 가장 많이 팔린 상품 (상품 순) */
	@Override
	public List<ChartProductVO> chartProductAmount(String cg_code, String cg_code_prt, String startDate, String endDate)
			throws Exception {
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("cg_code", cg_code);
		map.put("cg_code_prt", cg_code_prt);
		map.put("startDate", startDate);
		map.put("endDate", checkEndDate(endDate));
		
		return dao.chartProductAmount(map);
	}

	/* 상품별 가장 많이 팔린 상품 (가격 순) - 테이블 */
	@Override
	public List<ChartProductTableVO> tableProductPrice(String cg_code, String cg_code_prt, String startDate, String endDate) throws Exception {
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("cg_code", cg_code);
		map.put("cg_code_prt", cg_code_prt);
		map.put("startDate", startDate);
		map.put("endDate", checkEndDate(endDate));
		
		return dao.tableProductPrice(map);
	}

	/* 상품별 가장 많이 팔린 상품 (수량 순) - 테이블 */
	@Override
	public List<ChartProductTableVO> tableProductAmount(String cg_code, String cg_code_prt, String startDate, String endDate) throws Exception {
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("cg_code", cg_code);
		map.put("cg_code_prt", cg_code_prt);
		map.put("startDate", startDate);
		map.put("endDate", checkEndDate(endDate));
		return dao.tableProductAmount(map);
	}
	

	
	
	
	/* 회원 별 통계 */
	/* 구매수량 순 회원 차트 */
	@Override
	public List<ChartMemberDTO> chartMemberAmount(String searchDate, String period) throws Exception {
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("searchDate", formatSearchDate(searchDate, period));
		map.put("period", period);
		
		return dao.chartMemberAmount(map);
	}
	/* 구매금액 순 회원 차트 */
	@Override
	public List<ChartMemberDTO> chartMemberPrice(String searchDate, String period) throws Exception {
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("searchDate", formatSearchDate(searchDate, period));
		map.put("period", period);
		
		return dao.chartMemberPrice(map);
	}
	
	/* 구매수량 순 회원 테이블 */
	@Override
	public List<ChartMemberTableVO> tableMemberAmount(String searchDate, String period) throws Exception {

		Map<String, Object> map = new HashMap<>();
		
		map.put("searchDate", formatSearchDate(searchDate, period));
		map.put("period", period);
		
		return dao.tableMemberAmount(map);
	}
	/* 구매금액 순 회원 테이블 */
	@Override
	public List<ChartMemberTableVO> tableMemberPrice(String searchDate, String period) throws Exception {
				
		Map<String, Object> map = new HashMap<>();
		
		map.put("searchDate", formatSearchDate(searchDate, period));
		map.put("period", period);
		
		return dao.tableMemberPrice(map);
	}
	
	
	
	
	
	
	
	/*
	 * 마지막 날짜 조정 함수
	 */
	public String checkEndDate(String endDate) throws Exception {
		
		// 마지막 날짜가 00시 00분 00초 기준이기 때문에 +1 작업 필요
		if(endDate != null && !endDate.equals("")) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date end = format.parse(endDate);
			
			Calendar c = Calendar.getInstance(); 
			c.setTime(end); 
			c.add(Calendar.DATE, 1); // date_end +1일로 날짜 설정
			end = c.getTime();
			
			endDate = format.format(end);
		}
		
		return endDate;
		
	}
	
	/* 기간에 해당하는 날짜 조정 */
	public String formatSearchDate(String searchDate, String period) throws Exception{
		
		String[] splitedDate = searchDate.split("-");
		String date = "";
		
		if(period.equals("day")) {
			date = splitedDate[0] + splitedDate[1] + splitedDate[2];
		} else if(period.equals("month")) {
			date = splitedDate[0] + splitedDate[1];
		} else {
			date = searchDate;
		}
		
		return date;
	}


	
}
