package com.demo.controller;

import java.util.HashMap;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.domain.BoardVO;
import com.demo.dto.SearchDateDTO;
import com.demo.service.BoardService;
import com.demo.util.PageMaker;
import com.demo.util.SearchCriteria;

@Controller
@RequestMapping(value="/board/*")
public class BoardController {

	@Inject
	private BoardService service;
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	
	/* 게시판 목록 출력 */
	@RequestMapping(value="notice")
	public void notice(@ModelAttribute("searchDate") SearchDateDTO searchDate,
						@ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception{
		
		logger.info("notice() execute...");
		
		model.addAttribute("list", service.listNotice(cri, searchDate));
		
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.countNotice(cri, searchDate));
		
		model.addAttribute("pm", pageMaker);
		
	}
	
	
	
	/* 게시판 목록 출력 */
	@RequestMapping(value="list", method=RequestMethod.GET)
	public void list(@ModelAttribute("searchDate") SearchDateDTO searchDate,
					@ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception{
		
		logger.info("list() execute...");
		
	}
	
	/* 페이지 이동에 따른 게시판 ajax */
	@ResponseBody
	@RequestMapping(value="list/{page}", method=RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> listPage(@PathVariable("page") Integer page,
										@ModelAttribute("searchDate") SearchDateDTO searchDate,
										@ModelAttribute("cri") SearchCriteria cri) {
		
		logger.info("=====listPage() execute...");
		logger.info("=====page: " + page);
		logger.info("=====searchDate: " + searchDate.toString());
		logger.info("=====cri: " + cri.toString());
		
		ResponseEntity<Map<String, Object>> entity = null;
		
		try {
			cri.setPage(page);
			
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("noticeList", service.listNoticeLimit5());
			map.put("list", service.list(cri, searchDate));
			
			int count = service.count(cri, searchDate);
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCri(cri);
			pageMaker.setTotalCount(count);
			
			map.put("pageMaker", pageMaker);
			
			entity = new ResponseEntity<Map<String, Object>> (map, HttpStatus.OK);
			
		} catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<Map<String, Object>> (HttpStatus.BAD_REQUEST);
			
		}
		
		return entity;
	}

	/* 게시판 글 읽기(POST) */
	@RequestMapping(value="read/{brd_num}", method=RequestMethod.POST)
	public ResponseEntity<BoardVO> readPOST(@PathVariable("brd_num") Integer brd_num){
		logger.info("=====readPOST() execute...");
		logger.info("=====brd_num: "+brd_num);
		
		ResponseEntity<BoardVO> entity = null;
		try {
			entity = new ResponseEntity<BoardVO>(service.read(brd_num), HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<BoardVO>(HttpStatus.BAD_REQUEST);
			
		}
		return entity;
	}
	
	/* 게시판 글(원글/댓글) 쓰기(POST) */
	@ResponseBody
	@RequestMapping(value="write", method=RequestMethod.POST, produces ="application/text; charset=utf8")
	public ResponseEntity<String> writePOST(BoardVO vo) throws Exception {
		
		logger.info("=====writePOST() execute...");
		logger.info("=====vo: "+vo.toString());
		
		ResponseEntity<String> entity = null;
		try {
			service.write(vo);
			entity = new ResponseEntity<String>("글이 성공적으로 저장되었습니다.", HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			
		}
		return entity;
	}
	
	
	/* 게시판 글 수정하기(POST) */
	@ResponseBody
	@RequestMapping(value="modify", method=RequestMethod.POST, produces ="application/text; charset=utf8")
	public ResponseEntity<String> modifyPOST(BoardVO vo) throws Exception {
		
		logger.info("=====modifyPOST() execute...");
		logger.info("=====vo: "+vo.toString());
		
		ResponseEntity<String> entity = null;
		try {
			service.modify(vo);
			entity = new ResponseEntity<String>("글이 성공적으로 수정되었습니다.", HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>("글이 수정되는데 실패했습니다.", HttpStatus.BAD_REQUEST);
			
		}
		return entity;
	}
	
	/* 게시판 글 삭제하기(POST) */
	@ResponseBody
	@RequestMapping(value="delete/{brd_num}", method=RequestMethod.POST, produces ="application/text; charset=utf8")
	public ResponseEntity<String> deletePOST(@PathVariable("brd_num") int brd_num) throws Exception {
		
		logger.info("=====deletePOST() execute...");
		logger.info("=====brd_num: "+brd_num);
		
		ResponseEntity<String> entity = null;
		try {
			service.delete(brd_num);
			entity = new ResponseEntity<String>("글이 성공적으로 삭제되었습니다.", HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>("글이 삭제되는데 실패했습니다.", HttpStatus.BAD_REQUEST);
			
		}
		return entity;
	}
	
	
	
	
}
