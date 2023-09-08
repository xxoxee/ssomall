package com.demo.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.dao.AdBoardDAO;
import com.demo.domain.BoardVO;
import com.demo.dto.SearchDateDTO;
import com.demo.util.SearchCriteria;

@Service
public class AdBoardServiceImpl implements AdBoardService {
	
	@Autowired
	private AdBoardDAO dao;

	
	/* 게시판 글 쓰기 */
	@Transactional
	@Override
	public void write(BoardVO vo) throws Exception {
		
		// 원글이 아닌 경우(댓글인 경우), 순서 조정
		if(vo.getBrd_level()!=1) {
			Map<String, Integer> map = new HashMap<>();
			map.put("brd_group", vo.getBrd_group());
			map.put("brd_order", vo.getBrd_order());
			
			dao.addOrder(map);
			dao.reply(vo);
			
		} else {
			// 글 추가
			dao.write(vo);
		}
		
	}

	/* 게시글 목록 */
	@Override
	public List<BoardVO> list(SearchCriteria cri, SearchDateDTO searchDate) throws Exception {
		
		// 마지막 날짜가 00시 00분 00초 기준이기 때문에 +1 작업 필요
		searchDate.setEndDate(checkEndDate(searchDate));
		
		Map<String, Object> map = new HashMap<>();
		map.put("cri", cri);
		map.put("term", searchDate);
		
		return dao.list(map);
	}

	/* 게시글 총 개수 */
	@Override
	public int count(SearchCriteria cri, SearchDateDTO searchDate) throws Exception {
		
		// 마지막 날짜가 00시 00분 00초 기준이기 때문에 +1 작업 필요
		searchDate.setEndDate(checkEndDate(searchDate));
		
		Map<String, Object> map = new HashMap<>();
		map.put("cri", cri);
		map.put("term", searchDate);
		
		return dao.count(map);
	}

	/* 게시글 삭제 */
	@Override
	public void delete(int brd_num) throws Exception {
		dao.delete(brd_num);
	}

	/* 게시글 수정 */
	@Override
	public void modify(BoardVO vo) throws Exception {
		dao.modify(vo);
	}

	/* 공지사항 상위 5개 */
	@Override
	public List<BoardVO> listNoticeLimit5() throws Exception {
		return dao.listNoticeLimit5();
	}
	
	/* 공지사항 */
	@Override
	public List<BoardVO> listNotice(SearchCriteria cri, SearchDateDTO searchDate) throws Exception {
		
		// 마지막 날짜가 00시 00분 00초 기준이기 때문에 +1 작업 필요
		searchDate.setEndDate(checkEndDate(searchDate)); 
		
		Map<String, Object> map = new HashMap<>();
		map.put("cri", cri);
		map.put("term", searchDate);
		
		return dao.listNotice(map);
	}

	/* 공지사항 수 */
	@Override
	public int countNotice(SearchCriteria cri, SearchDateDTO searchDate) throws Exception {
		
		// 마지막 날짜가 00시 00분 00초 기준이기 때문에 +1 작업 필요
		searchDate.setEndDate(checkEndDate(searchDate));
		
		Map<String, Object> map = new HashMap<>();
		map.put("cri", cri);
		map.put("term", searchDate);
				
		return dao.countNotice(map);
	}

	/* 게시글 읽기 */
	@Override
	public BoardVO read(int brd_num) throws Exception {
		dao.updateCount(brd_num); // 조회 수 증가
		return dao.read(brd_num);
	}
	
	/*
	 * 마지막 날짜 조정 함수
	 */
	public String checkEndDate(SearchDateDTO searchDate) throws Exception {
		
		// 마지막 날짜가 00시 00분 00초 기준이기 때문에 +1 작업 필요
		String date_end  = searchDate.getEndDate();
		
		if(date_end != null && !date_end.equals("")) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date end = format.parse(date_end);
			
			Calendar c = Calendar.getInstance(); 
			c.setTime(end); 
			c.add(Calendar.DATE, 1); // date_end +1일로 날짜 설정
			end = c.getTime();
			
			date_end = format.format(end);
		}
		
		return date_end;
		
	}

	/* 공지사항 작성 */
	@Override
	public void writeNotice(BoardVO vo) throws Exception {
		dao.writeNotice(vo);
	}

	/* 공지사항 삭제 */
	@Override
	public void deleteNotice(int brd_num) throws Exception {
		dao.deleteNotice(brd_num);
	}
	
	
	
}
