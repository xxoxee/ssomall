package com.demo.dao;

import java.util.List;
import java.util.Map;

import com.demo.domain.BoardVO;

public interface BoardDAO {
	
	// 게시판 순서 업데이트
	public void addOrder(Map<String, Integer> map) throws Exception;
	
	// 게시판 글 쓰기
	public void write(BoardVO vo) throws Exception;
	
	// 게시판 글 쓰기(댓글)
	public void reply(BoardVO vo) throws Exception;
	
	// 게시글 목록 리스트(공지사항 제외)
	public List<BoardVO> list(Map<String,Object> map) throws Exception;
	
	// 게시글 총 개수
	public int count(Map<String, Object> map) throws Exception;
	
	// 게시글 삭제
	public void delete(int brd_num) throws Exception;
	
	// 게시글 수정
	public void modify(BoardVO vo) throws Exception;
	
	// 공지사항 상위 5개 가져오기
	public List<BoardVO> listNoticeLimit5() throws Exception;
	
	// 공지사항 가져오기
	public List<BoardVO> listNotice(Map<String, Object> map) throws Exception;
	
	// 공지사항 개수
	public int countNotice(Map<String, Object> map) throws Exception;
	
	// 게시글 읽기
	public BoardVO read(int brd_num) throws Exception;
	
	// 게시글 조회 수 증가
	public void updateCount(int brd_num) throws Exception;

}
