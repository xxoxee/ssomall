package com.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.demo.domain.BoardVO;

@Repository
public class AdBoardDAOImpl implements AdBoardDAO {

	@Autowired
	private SqlSession session;
	
	public final static String NS="com.demo.mappers.AdBoardMapper";

	
	/* 게시판 순서 업데이트 */
	@Override
	public void addOrder(Map<String, Integer> map) throws Exception {
		session.update(NS+".addOrder", map);
	}
	
	/* 게시판 글 쓰기 */
	@Override
	public void write(BoardVO vo) throws Exception {
		session.insert(NS+".write", vo);
	}
	
	/* 게시판 글 쓰기(댓글) */
	@Override
	public void reply(BoardVO vo) throws Exception {
		session.insert(NS+".reply", vo);
	}

	/* 게시글 목록 */
	@Override
	public List<BoardVO> list(Map<String, Object> map) throws Exception {
		return session.selectList(NS+".list", map);
	}

	/* 게시글 총 개수 */
	@Override
	public int count(Map<String, Object> map) throws Exception {
		return session.selectOne(NS+".count", map);
	}

	/* 게시글 삭제 */
	@Override
	public void delete(int brd_num) throws Exception {
		session.delete(NS+".delete", brd_num);
	}

	/* 게시글 수정 */
	@Override
	public void modify(BoardVO vo) throws Exception {
		session.update(NS+".modify", vo);
	}

	/* 공지사항 상위 5개 */
	@Override
	public List<BoardVO> listNoticeLimit5() throws Exception {
		return session.selectList(NS+".listNoticeLimit5");
	}
	
	/* 공지사항 */
	@Override
	public List<BoardVO> listNotice(Map<String, Object> map) throws Exception {
		return session.selectList(NS+".listNotice", map);
	}

	/* 공지사항 수 */
	@Override
	public int countNotice(Map<String, Object> map) throws Exception {
		return session.selectOne(NS+".countNotice", map);
	}

	/* 게시글 읽기 */
	@Override
	public BoardVO read(int brd_num) throws Exception {
		return session.selectOne(NS+".read", brd_num);
	}

	/* 조회 수 증가 */
	@Override
	public void updateCount(int brd_num) throws Exception {
		session.update(NS+".updateCount", brd_num);
	}

	/* 공지사항 쓰기 */
	@Override
	public void writeNotice(BoardVO vo) throws Exception{
		session.insert(NS+".writeNotice", vo);
	}

	/* 공지사항 삭제 */
	@Override
	public void deleteNotice(int brd_num) throws Exception {
		session.delete(NS+".deleteNotice", brd_num);
	}
	
	
	
}
