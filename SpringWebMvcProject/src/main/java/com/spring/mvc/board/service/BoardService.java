package com.spring.mvc.board.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.mvc.board.commons.PageVO;
import com.spring.mvc.board.commons.SearchVO;
import com.spring.mvc.board.model.BoardVO;
import com.spring.mvc.board.repository.IBoardMapper;

@Service
public class BoardService implements IBoardService {
	
	@Autowired
	private IBoardMapper mapper;

	@Override
	public void insert(BoardVO article) {
		mapper.insert(article);
	}

	@Override
	public List<BoardVO> getArticleList(SearchVO search) {
		/*
		//매퍼에게 전달할 맵 데이터를 생성
		Map<String, Object> datas = new HashMap<>();
		datas.put("paging", paging);
		datas.put("keyword", keyword);
		datas.put("condition", condition);		
		*/
		
		List<BoardVO> list = mapper.getArticleList(search);
		for(BoardVO vo : list) {
			
			long now = System.currentTimeMillis();
			
			long regTime = vo.getRegDate().getTime();
			
			if(now - regTime < 60 * 60 * 24 * 1000) {
				vo.setNewMark(true);
			}
		}
		
		
		
		return list;
	}

	@Override
	public BoardVO getArticle(int boardNo) {
		return mapper.getArticle(boardNo);
	}

	@Override
	public void update(BoardVO article) {
		mapper.update(article);
	}

	@Override
	public void delete(int boardNo) {
		mapper.delete(boardNo);
	}
	
	@Override
	public int countArticles(SearchVO search) {
		return mapper.countArticles(search);
	}


}







