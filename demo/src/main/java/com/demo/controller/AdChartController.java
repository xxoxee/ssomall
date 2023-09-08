package com.demo.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.domain.ChartDateVO;
import com.demo.domain.ChartMemberTableVO;
import com.demo.domain.ChartProductTableVO;
import com.demo.domain.ChartProductVO;
import com.demo.dto.ChartMemberDTO;
import com.demo.service.AdChartService;



@Controller
@RequestMapping(value="/admin/chart/*")
public class AdChartController {
	
	@Inject
	private AdChartService service;
	
	private static final Logger logger = LoggerFactory.getLogger(AdChartController.class);
	
	/* 날짜 별 통계 */
	@RequestMapping("date")
	public void date(Model model) throws Exception{
		logger.info("=====date() execute...");
		
	}
	
	/* 날짜 별 통계(ajax) - 일간 */
	@ResponseBody
	@RequestMapping("date/day")
	public ResponseEntity<JSONObject> dateDay() throws Exception{
		logger.info("=====dateDay() execute...");
		
		ResponseEntity<JSONObject> entity = null;
		
		try {
			// 일간 차트 출력
			List<ChartDateVO> dayList = service.chartDayCount();
			logger.info("=====list: " + dayList.toString());
			
			entity = new ResponseEntity<JSONObject>(makeDayData(dayList), HttpStatus.OK); 
			
		} catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<JSONObject>(HttpStatus.BAD_REQUEST); 
		}
		
		return entity;
	}
	
	/* 날짜 별 통계(ajax) - 월간 */
	@ResponseBody
	@RequestMapping("date/month")
	public ResponseEntity<JSONObject> dateMonth() throws Exception{
		logger.info("=====dateMonth() execute...");
		
		ResponseEntity<JSONObject> entity = null;
		
		try {
			// 월간 차트 출력
			List<ChartDateVO> monthList = service.chartMonthCount();
			logger.info("=====list: " + monthList.toString());
			
			entity = new ResponseEntity<JSONObject>(makeMonthData(monthList), HttpStatus.OK); 
			
		} catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<JSONObject>(HttpStatus.BAD_REQUEST); 
		}
		
		return entity;
	}
	
	/* 날짜 별 통계(ajax) - 연간 */
	@ResponseBody
	@RequestMapping("date/year")
	public ResponseEntity<JSONObject> dateYear() throws Exception{
		logger.info("=====date() execute...");
		
		ResponseEntity<JSONObject> entity = null;
		
		try {
			// 연간 차트 출력
			List<ChartDateVO> yearList = service.chartYearCount();
			logger.info("=====list: " + yearList.toString());
			entity = new ResponseEntity<JSONObject>(makeYearData(yearList), HttpStatus.OK); 
			
		} catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<JSONObject>(HttpStatus.BAD_REQUEST); 
		}
		
		return entity;
	}
	
	
	/*
	 * 일간 차트(10일)
	 * 리스트에 담긴 내용을 JSP 구글 차트 API에서 참조할 수 있도록
	 * 배열 형태로 생성
	 */
	private JSONObject makeDayData(List<ChartDateVO> dayList) {
		
		List<ChartDateVO> list = new ArrayList<ChartDateVO>();
		
		// 9일 전으로 setting
		Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -9);
        
        DateFormat df = new SimpleDateFormat("yyyyMMdd");

        // 데이터가 존재하지 않는 기간에는 임의의 값(0)을 넣어주는 작업
        int index = -1;
        for(int i=0; i<10; i++) {
        	index = -1;
        	for(int j=0; j<dayList.size(); j++) {
	        	if(df.format(cal.getTime()).equals(dayList.get(j).getDay())) {
	        		index= j;
	        		break;
	        	}
        	}
        	// 존재하지 않으면 임의로 값을 넣어줌
        	if(index == -1) {
        		ChartDateVO vo = new ChartDateVO();
        		vo.setDay(df.format(cal.getTime()));
        		vo.setCount(0);
        		vo.setTotalPrice(0);
        		list.add(vo);
        	} else {
        		list.add(dayList.get(index));
        	}
        	cal.add(Calendar.DATE, +1);
        }
        
        // 날짜 포맷 조정 ex.20200416 -> 04/16
        for(ChartDateVO vo : list) {
        	String originDate = vo.getDay();
        	vo.setDay(originDate.substring(4,6)+"/"+originDate.substring(6));
        }
		
		// 반환
		return makeJSONData(list, "day");
	}
	
	/*
	 * 월간 차트(최근 1년)
	 * 리스트에 담긴 내용을 JSP 구글 차트 API에서 참조할 수 있도록
	 * 배열 형태로 생성
	 */
	private JSONObject makeMonthData(List<ChartDateVO> monthList) {
		
		List<ChartDateVO> list = new ArrayList<ChartDateVO>();
		
		// 해당년도 1월로 setting
		Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, -1 * cal.get(Calendar.MONTH));
        
        DateFormat df = new SimpleDateFormat("yyyyMM");

        // 데이터가 존재하지 않는 기간에는 임의의 값(0)을 넣어주는 작업
        int index = -1;
        for(int i=0; i<12; i++) {
        	index = -1;
        	for(int j=0; j<monthList.size(); j++) {
	        	if(df.format(cal.getTime()).equals(monthList.get(j).getMonth())) {
	        		index= j;
	        		break;
	        	}
        	}
        	// 존재하지 않으면 임의로 값을 넣어줌
        	if(index == -1) {
        		ChartDateVO vo = new ChartDateVO();
        		vo.setMonth(df.format(cal.getTime()));
        		vo.setCount(0);
        		vo.setTotalPrice(0);
        		list.add(vo);
        	} else {
        		list.add(monthList.get(index));
        	}
        	cal.add(Calendar.MONTH, +1);
        }
        
        // 날짜 포맷 조정 ex.202004 -> 2020/04
        for(ChartDateVO vo : list) {
        	String originDate = vo.getMonth();
        	logger.info("=====" +originDate);
        	vo.setMonth(originDate.substring(0,4)+"/"+originDate.substring(4,6));
        }
        
        return makeJSONData(list, "month");
	}
	
	/*
	 * 연간 차트(최근 5년)
	 * 리스트에 담긴 내용을 JSP 구글 차트 API에서 참조할 수 있도록
	 * 배열 형태로 생성
	 */
	private JSONObject makeYearData(List<ChartDateVO> yearList) {
		
		List<ChartDateVO> list = new ArrayList<ChartDateVO>();
		
		// 4년 전으로 setting
		Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.YEAR, -4);
        
        DateFormat df = new SimpleDateFormat("yyyy");

        // 데이터가 존재하지 않는 기간에는 임의의 값(0)을 넣어주는 작업
        int index = -1;
        for(int i=0; i<5; i++) {
        	index = -1;
        	for(int j=0; j<yearList.size(); j++) {
	        	if(df.format(cal.getTime()).equals(yearList.get(j).getYear())) {
	        		index= j;
	        		break;
	        	}
        	}
        	// 존재하지 않으면 임의로 값을 넣어줌
        	if(index == -1) {
        		ChartDateVO vo = new ChartDateVO();
        		vo.setYear(df.format(cal.getTime()));
        		vo.setCount(0);
        		vo.setTotalPrice(0);
        		list.add(vo);
        	} else {
        		list.add(yearList.get(index));
        	}
        	cal.add(Calendar.YEAR, +1);
        }
        
		// 반환
		return makeJSONData(list, "year");
	}
	
	
	/*
	 * 해당 리스트를 JSON객체로 생성
	 */
	private JSONObject makeJSONData(List<ChartDateVO> list, String period) {

		/* 최종 data 객체 */
		JSONObject data= new JSONObject();
		
		
		/* JSON title 작업 - column 정의 */
		JSONArray title = new JSONArray();
		JSONObject col1 = new JSONObject();
		JSONObject col2 = new JSONObject();
		JSONObject col3 = new JSONObject();
		
		col1.put("label", "날짜");
		col1.put("type", "string");
		col2.put("label", "총 주문 금액");
		col2.put("type" , "number");
		col3.put("label", "총 주문 수량");
		col3.put("type" , "number");
		
		title.add(col1);
		title.add(col2);
		title.add(col3);
		
		data.put("cols", title);
		
		JSONArray body = new JSONArray();
		for(ChartDateVO vo : list) {
			
			JSONObject date = new JSONObject();
			if(period.equals("day")) {
				date.put("v", vo.getDay());
			} else if(period.equals("month")) {
				date.put("v", vo.getMonth());
			} else {
				date.put("v", vo.getYear());
			}
			
			JSONObject price = new JSONObject();
			price.put("v", vo.getTotalPrice());
			
			JSONObject count = new JSONObject();
			count.put("v", vo.getCount());
			
			JSONArray row = new JSONArray();
			row.add(date);
			row.add(price);
			row.add(count);
			
			JSONObject c = new JSONObject();
			c.put("c", row);
			
			body.add(c);
		}
		
		data.put("rows", body);
		
		// 반환
		logger.info("=====jsonData(JSONString): " + data.toJSONString());
		logger.info("=====jsonData(String): " + data.toString());
		return data;
	}

	
	/******************************상품 통계 ***********************************/
	
	/*
	 * 상품별 통계 차트 보기
	 */
	@RequestMapping(value = "product")
	public void product() throws Exception{
		
		logger.info("=====product() execute...");
		
		
	}
	
	/* 
	 * 상품 별 통계 - 수량 순(ajax) 
	 */
	@ResponseBody
	@RequestMapping(value = "product/amount", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> productAmountChart(String cg_code, String cg_code_prt, 
												String startDate, String endDate) throws Exception{
		
		logger.info("=====productChart-amount() execute...");
		logger.info("받은 값 : " + cg_code + " / " + cg_code_prt + " / " + startDate + " / " + endDate);
		ResponseEntity<JSONObject> entity = null;
		
		try {
			// 상품별(카테고리 적용가능) 차트 출력
			List<ChartProductVO> productList = service.chartProductAmount(cg_code, cg_code_prt, startDate, endDate);
			logger.info("=====list: " + productList.toString());
			
			entity = new ResponseEntity<JSONObject>(makeJSONDataProduct(productList, "amount"), HttpStatus.OK); 
			
		} catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<JSONObject>(HttpStatus.BAD_REQUEST); 
		}
		
		return entity;
	}
	
	/* 
	 * 상품 별 통계 - 가격 순(ajax) 
	 */
	@ResponseBody
	@RequestMapping(value = "product/price", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> productPriceChart(String cg_code, String cg_code_prt, 
												String startDate, String endDate) throws Exception{
		
		logger.info("=====productChart-price() execute...");
		logger.info("받은 값 : " + cg_code + " / " + cg_code_prt + " / " + startDate + " / " + endDate);
		ResponseEntity<JSONObject> entity = null;
		
		try {
			// 상품별(카테고리 적용가능) 차트 출력
			List<ChartProductVO> productList = service.chartProductPrice(cg_code, cg_code_prt, startDate, endDate);
			logger.info("=====list: " + productList.toString());
			
			entity = new ResponseEntity<JSONObject>(makeJSONDataProduct(productList, "price"), HttpStatus.OK); 
			
		} catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<JSONObject>(HttpStatus.BAD_REQUEST); 
		}
		
		return entity;
	}
	
	
	/*
	 * 상품 별 통계 - 수량 테이블 (ajax)
	 */
	@ResponseBody
	@RequestMapping(value = "product/amountTable", method=RequestMethod.POST)
	public ResponseEntity<List<ChartProductTableVO>> productAmountTable(String cg_code, String cg_code_prt, 
																	String startDate, String endDate){
		
		logger.info("=====productAmountTable() execute...");
		logger.info("받은 값 : " + cg_code + " / " + cg_code_prt + " / " + startDate + " / " + endDate);
		
		
		ResponseEntity<List<ChartProductTableVO>> entity = null;
		
		try {
			List<ChartProductTableVO> list = service.tableProductAmount(cg_code, cg_code_prt, startDate, endDate);
			entity = new ResponseEntity<List<ChartProductTableVO>>(list , HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<List<ChartProductTableVO>>(HttpStatus.BAD_REQUEST);  
		}
		
		return entity;
	}
	
	/*
	 * 상품 별 통계 - 가격 테이블 (ajax)
	 */
	@ResponseBody
	@RequestMapping(value = "product/priceTable", method=RequestMethod.POST)
	public ResponseEntity<List<ChartProductTableVO>> productPriceTable(String cg_code, String cg_code_prt, 
																	String startDate, String endDate){
		
		logger.info("=====productPriceTable() execute...");
		logger.info("받은 값 : " + cg_code + " / " + cg_code_prt + " / " + startDate + " / " + endDate);
		
		ResponseEntity<List<ChartProductTableVO>> entity = null;
		
		try {
			List<ChartProductTableVO> list = service.tableProductPrice(cg_code, cg_code_prt, startDate, endDate);
			entity = new ResponseEntity<List<ChartProductTableVO>>(list , HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<List<ChartProductTableVO>>(HttpStatus.BAD_REQUEST); 
		}
		
		return entity;
	}
	
	
	
	/*
	 * 해당 리스트를 JSON객체로 생성
	 */
	private JSONObject makeJSONDataProduct(List<ChartProductVO> list, String col) {

		/* 최종 data 객체 */
		JSONObject data= new JSONObject();
		
		/* JSON title 작업 - column 정의 */
		JSONArray title = new JSONArray();
		JSONObject col1 = new JSONObject();
		JSONObject col2 = new JSONObject();
		
		col1.put("label", "상품명");
		col1.put("type", "string");
		
		if(col.equals("price")) {
			col2.put("label", "총 주문 금액");
			col2.put("type" , "number");
			
		} else {
			col2.put("label", "총 주문 수량");
			col2.put("type" , "number");
		}
		
		title.add(col1);
		title.add(col2);
		
		data.put("cols", title);
		
		
		/* JSON body 작업 - row별 column 정의 */
		JSONArray body = new JSONArray();
		for(ChartProductVO vo : list) {
			JSONArray row = new JSONArray();
			
			JSONObject name = new JSONObject();
			name.put("v", vo.getPdt_name()); // 상품명
			row.add(name);
			
			if(col.equals("price")) {
				JSONObject price = new JSONObject();
				price.put("v", vo.getTotalPrice()); // 가격
				row.add(price);
				
			} else {
				JSONObject count = new JSONObject();
				count.put("v", vo.getTotalAmount()); // 수량
				row.add(count);
			}
			
			JSONObject c = new JSONObject();
			c.put("c", row);
			
			body.add(c);
		}
		
		data.put("rows", body);
		
		// 반환
		logger.info("=====jsonData(String): " + data.toString());
		return data;
	}
	
	
	
	/******************************회원 통계 ***********************************/
	
	@RequestMapping("member")
	public void member() throws Exception{
		logger.info("=====member() execute...");
	}
	
	
	/*
	 * 회원 별 통계 - 수량 차트 (ajax)
	 */
	@ResponseBody
	@RequestMapping(value = "member/amount", method=RequestMethod.POST)
	public ResponseEntity<JSONObject> memberAmountChart(String searchDate, String period){
		
		logger.info("=====memberAmountChart() execute...");
		logger.info("받은 값 : " + searchDate + " / " + period);
		
		ResponseEntity<JSONObject> entity = null;
		
		try {
			entity = new ResponseEntity<JSONObject>(makeJSONDataMember(service.chartMemberAmount(searchDate, period), "amount"), HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<JSONObject>(HttpStatus.BAD_REQUEST); 
		}
		
		return entity;
	}
	
	/*
	 * 회원 별 통계 - 금액 차트 (ajax)
	 */
	@ResponseBody
	@RequestMapping(value = "member/price", method=RequestMethod.POST)
	public ResponseEntity<JSONObject> memberPriceChart(String searchDate, String period){
		
		logger.info("=====memberPriceChart() execute...");
		logger.info("받은 값 : " + searchDate + " / " + period);
		
		ResponseEntity<JSONObject> entity = null;
		
		try {
			entity = new ResponseEntity<JSONObject>(makeJSONDataMember(service.chartMemberPrice(searchDate, period), "price") , HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<JSONObject>(HttpStatus.BAD_REQUEST); 
		}
		
		return entity;
	}
	
	
	
	/*
	 * 회원 별 통계 - 수량 테이블 (ajax)
	 */
	@ResponseBody
	@RequestMapping(value = "member/amountTable", method=RequestMethod.POST)
	public ResponseEntity<List<ChartMemberTableVO>> memberAmountTable(String searchDate, String period){
		
		logger.info("=====memberAmountTable() execute...");
		logger.info("받은 값 : " + searchDate + " / " + period);
		
		ResponseEntity<List<ChartMemberTableVO>> entity = null;
		
		try {
			List<ChartMemberTableVO> list = service.tableMemberAmount(searchDate, period);
			entity = new ResponseEntity<List<ChartMemberTableVO>>(list , HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<List<ChartMemberTableVO>>(HttpStatus.BAD_REQUEST); 
		}
		
		return entity;
	}
	
	/*
	 * 회원 별 통계 - 가격 테이블 (ajax)
	 */
	@ResponseBody
	@RequestMapping(value = "member/priceTable", method=RequestMethod.POST)
	public ResponseEntity<List<ChartMemberTableVO>> memberPriceTable(String searchDate, String period){
		
		logger.info("=====memberPriceTable() execute...");
		logger.info("받은 값 : " + searchDate + " / " + period);
		
		ResponseEntity<List<ChartMemberTableVO>> entity = null;
		
		try {
			List<ChartMemberTableVO> list = service.tableMemberPrice(searchDate, period);
			entity = new ResponseEntity<List<ChartMemberTableVO>>(list , HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<List<ChartMemberTableVO>>(HttpStatus.BAD_REQUEST); 
		}
		
		return entity;
	}
	
	
	
	/*
	 * 해당 리스트를 JSON객체로 생성
	 */
	private JSONObject makeJSONDataMember(List<ChartMemberDTO> list, String col) {

		/* 최종 data 객체 */
		JSONObject data= new JSONObject();
		
		/* JSON title 작업 - column 정의 */
		JSONArray title = new JSONArray();
		JSONObject col1 = new JSONObject();
		JSONObject col2 = new JSONObject();
		
		col1.put("label", "회원 아이디");
		col1.put("type", "string");
		
		if(col.equals("price")) {
			col2.put("label", "총 구매 금액");
			col2.put("type" , "number");
			
		} else {
			col2.put("label", "총 구매 수량");
			col2.put("type" , "number");
		}
		
		title.add(col1);
		title.add(col2);
		
		data.put("cols", title);
		
		
		/* JSON body 작업 - row별 column 정의 */
		JSONArray body = new JSONArray();
		for(ChartMemberDTO dto : list) {
			JSONArray row = new JSONArray();
			
			JSONObject name = new JSONObject();
			name.put("v", dto.getMem_id()); // 회원 아이디
			row.add(name);
			
			if(col.equals("price")) {
				JSONObject price = new JSONObject();
				price.put("v", dto.getTotalPrice()); // 가격
				row.add(price);
				
			} else {
				JSONObject amount = new JSONObject();
				amount.put("v", dto.getTotalAmount()); // 수량
				row.add(amount);
			}
			
			JSONObject c = new JSONObject();
			c.put("c", row);
			
			body.add(c);
		}
		
		data.put("rows", body);
		
		// 반환
		logger.info("=====jsonData(String): " + data.toString());
		return data;
	}
	
}
