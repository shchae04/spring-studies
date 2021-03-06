package com.spring.db.model;

public class BoardVO {

	private String writer;
	private String title;
	private String content;
	private int boardNo;
	
	
	
	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "BoardVO [writer=" + writer + ", title=" + title + ", content=" + content + ", boardNo=" + boardNo + "]";
	}
	
	

	
}
