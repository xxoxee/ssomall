package com.demo.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.domain.OrderReadDetailVO;
import com.demo.service.AdOrderService;
import com.demo.util.PageMaker;
import com.demo.util.SearchCriteria;

@Controller
@RequestMapping("/admin/order/*")
public class AdOrderController {

	@Inject
	private AdOrderService service;
	private static final Logger logger = LoggerFactory.getLogger(AdOrderController.class);
	
	/* 주문 목록 */
	@RequestMapping(value="/list")
	public void list(@ModelAttribute("cri") SearchCriteria cri,  
					@RequestParam(value = "check_status", required = false) List<Integer> status,
					@RequestParam(value="term", required=false, defaultValue = "0") int term, String date_start, String date_end,
					Model model) throws Exception {
		
		logger.info("=====list() execute...");
		logger.info("=====[받은 값] cri: " + cri.toString());
		logger.info("term: " + term +  "/date_start: " + date_start + " /date_end: " +date_end);
		
		
		Map<String, Object> result = null;
		
		if(status == null) { // 검색조건 없이 처음 주문목록이 호출됨
			// 주문 현황 설정(default: 전체)
			List<Integer> status2 = new ArrayList<Integer>();
			status2.add(1); 
			status2.add(2); 
			status2.add(3); 
			status2.add(4); 
			
			result = service.orderListAll(cri, status2, date_start, date_end);
			model.addAttribute("check_status", status2);
			
		} else {
			result = service.orderListAll(cri, status, date_start, date_end);
			model.addAttribute("check_status", status);
		}
		
		model.addAttribute("orderList", result.get("list"));
		model.addAttribute("term", term);
		model.addAttribute("date_start", date_start);
		model.addAttribute("date_end", date_end);
		
		
		// PageMaker 생성
		PageMaker pm = new PageMaker();
		pm.setCri(cri);
		int count = (int) result.get("count");
		pm.setTotalCount(count);
		
		model.addAttribute("pm", pm);
		
	}
	
	
	/* 배송현황 수정 */
	@ResponseBody
	@RequestMapping(value="/update/{odr_code}", method=RequestMethod.POST)
	public ResponseEntity<String> updateStatus(@PathVariable("odr_code") int odr_code,
												@RequestParam("pdt_num") int pdt_num, 
												@RequestParam("odr_status") int odr_status,
												Model model) throws Exception {
		logger.info("=====updateStatusPOST() execute...");
		
		ResponseEntity<String> entity = null;
		try {
			service.updateStatus(odr_status, odr_code, pdt_num);
			entity = new ResponseEntity<String>(HttpStatus.OK);
			
		} catch(Exception e) {
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			e.printStackTrace();
		}
		
		return entity;
	}
	
	/* 배송현황 수정 - 체크상품 모두 */
	@ResponseBody
	@RequestMapping(value="/updateCheck", method=RequestMethod.POST)
	public ResponseEntity<String> updateCheck( @RequestParam("statusArr[]") List<Integer> statusArr,
												@RequestParam("codeArr[]") List<Integer> codeArr,
												@RequestParam("pdtArr[]") List<Integer> pdtArr,
												Model model) throws Exception {
		logger.info("=====updateCheck() execute...");
		
		ResponseEntity<String> entity = null;
		try {
			for(int i=0; i<codeArr.size(); i++) {
				int odr_status = statusArr.get(i);
				int odr_code = codeArr.get(i);
				int pdt_num = pdtArr.get(i);
				service.updateStatus(odr_status, odr_code, pdt_num);
			}
			entity = new ResponseEntity<String>(HttpStatus.OK);
			
		} catch(Exception e) {
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			e.printStackTrace();
		}
		
		return entity;
	}
	
	/* 주문 상세 조회 (GET) */
	@RequestMapping(value="read/{odr_code}", method=RequestMethod.GET)
	public String readOrderDetail(@PathVariable("odr_code") int odr_code, Model model) throws Exception{
		
		logger.info("=====readOrderDetail() execute...");
		
		model.addAttribute("orderList", service.readOrder(odr_code));
		model.addAttribute("order", service.getOrder(odr_code));
		
		return "/admin/order/read";
	}
	
	
	
	
}
