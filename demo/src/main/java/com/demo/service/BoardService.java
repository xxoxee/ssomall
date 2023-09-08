package com.demo.service;

import java.util.List;

import com.demo.domain.BoardVO;
import com.demo.dto.SearchDateDTO;
import com.demo.util.SearchCriteria;

public interface BoardService {

	// 게시판 글 쓰기
	public void write(BoardVO vo) throws Exception;
	
	// 게시글 목록 리스트(공지사항 제외)
	public List<BoardVO> list(SearchCriteria cri, SearchDateDTO searchDate) throws Exception;
	
	// 게시글 총 개수
	public int count(SearchCriteria cri, SearchDateDTO searchDate) throws Exception;
	
	// 게시글 삭제
	public void delete(int brd_num) throws Exception;
	
	// 게시글 수정
	public void modify(BoardVO vo) throws Exception;
	
	// 공지사항 상위 5개 가져오기
	public List<BoardVO> listNoticeLimit5() throws Exception;
	
	// 공지사항 가져오기
	public List<BoardVO> listNotice(SearchCriteria cri, SearchDateDTO searchDate) throws Exception;
	
	// 공지사항 개수
	public int countNotice(SearchCriteria cri, SearchDateDTO searchDate) throws Exception;
	
	// 게시글 읽기
	public BoardVO read(int brd_num) throws Exception;
	
}
