package com.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.service.AdMemberService;
import com.demo.util.PageMaker;
import com.demo.util.SearchCriteria;

@Controller
@RequestMapping("/admin/member/*")
public class AdMemberController {

	@Autowired
	private AdMemberService service;
	
	private static final Logger logger = LoggerFactory.getLogger(AdMemberController.class);
	
	
	/*
	 * 전체 회원 목록을 가져옴(검색 포함)
	 */
	@RequestMapping(value="list", method=RequestMethod.GET)
	public void listSearch(@ModelAttribute("cri") SearchCriteria cri, 
							@RequestParam(required = false, defaultValue = "ALL")String accept,
							Model model) throws Exception{
		
		logger.info("=====list() execute...");
		logger.info("=====cri: " + cri + "/ accept: "+ accept);
		model.addAttribute("memberList", service.memberListSearch(cri, accept));
		
		// PageMaker 생성
		PageMaker pm = new PageMaker();
		pm.setCri(cri);
		int count = service.countListSearch(cri, accept);
		pm.setTotalCount(count);
		
		model.addAttribute("pm", pm);
		model.addAttribute("accept", accept);
		
	}
	
	/*
	 * 선택한 회원의 상세 정보를 가져옴
	 */
	@RequestMapping(value="read", method=RequestMethod.GET)
	public void read(@RequestParam("id") String mem_id, 
					@ModelAttribute("cri") SearchCriteria cri,
					@RequestParam(required = false, defaultValue = "ALL") String accept,
					Model model) throws Exception{
		
		logger.info("=====read() execute...");
		
		Map<String, Object> map = service.readMemberDetail(mem_id);
		model.addAttribute("memberVO", map.get("memberVO"));
		model.addAttribute("totalOrder", map.get("totalOrder"));
		model.addAttribute("totalPrice", map.get("totalPrice"));
		
		model.addAttribute("accept", accept);
		
		// PageMaker 생성 - 상품목록으로 돌아가기 클릭 시 이동하기 위해서
		PageMaker pm = new PageMaker();
		pm.setCri(cri);
		
		model.addAttribute("pm", pm);
		
	}
}
