package com.demo.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dao.AdOrderDAO;
import com.demo.domain.OrderReadDetailVO;
import com.demo.domain.OrderVO;
import com.demo.util.SearchCriteria;

@Service
public class AdOrderServiceImpl implements AdOrderService {

	@Autowired
	private AdOrderDAO dao;

	/*
	 *  모든 주문 목록(페이징+검사)와
	 *  해당하는 주문 목록의 수를 Map 형태로 리턴
	 */
	@Override
	public Map<String, Object> orderListAll(SearchCriteria cri, List<Integer> status, String date_start, String date_end) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 마지막 날짜가 00시 00분 00초 기준이기 때문에 +1 작업 필요
		if(date_end != null && !date_end.equals("")) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date end = format.parse(date_end);
			
			Calendar c = Calendar.getInstance(); 
			c.setTime(end); 
			c.add(Calendar.DATE, 1); // date_end +1일로 날짜 설정
			end = c.getTime();
			
			date_end = format.format(end);
		}
		
		map.put("cri", cri);
		map.put("status", status);
		map.put("date_start", date_start);
		map.put("date_end", date_end);
		
		System.out.print("=====listMap: ");
		System.out.println(map.toString());
		
		// DB작업 결과를 담을 Map
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("list", dao.orderListAll(map));   // 주문 목록
		resultMap.put("count", dao.countListAll(map));	// 주문 결과
		
		return resultMap;
	}

	// 배송현황 수정
	@Override
	public void updateStatus(int odr_status, int odr_code, int pdt_num) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("odr_status", odr_status);
		map.put("odr_code", odr_code);
		map.put("pdt_num", pdt_num);
		
		dao.updateStatus(map);
	}

	// 주문 상세 정보
	@Override
	public List<OrderReadDetailVO> readOrder(int odr_code) throws Exception {
		return dao.readOrder(odr_code);
	}

	// 주문자 정보
	@Override
	public OrderVO getOrder(int odr_code) throws Exception {
		return dao.getOrder(odr_code);
	}
}
