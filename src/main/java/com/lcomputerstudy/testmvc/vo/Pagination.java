package com.lcomputerstudy.testmvc.vo;

public class Pagination {
	int userCount;			// user테이블에 등록 된 총 user 수
	int page;				// 현재 페이지 번호
	int pageNum;			// userCount / page = 화면에 나타낼 user index, 
	int startPage;			// pagination의 시작(ex1,6,11)
	int endPage;			// pagination의 끝(ex5,10,15)
	int lastPage;			// (userCount/화면에 표시할 갯수), pagination 마지막 번호
	int prevPage;			// pagination의 이전 목록
	int nextPage;			// pagination의 다음 목록
	public static final int pageUnit=5;		// 한번에 볼러 올 pagination 수
	public static final int perPage=5;		// 한번에 불러 올 userCount 수
	Search search;			//추가
	
	public Pagination() {
		
	}
	public void init() {
		pageNum = (page-1)*perPage;		//userCount - pageNum이 나오는 숫자에서부터 rownum이 시작돼 user 인덱스를 불러온다.
		startPage = ((page-1) / pageUnit) * pageUnit + 1;  // ex) 1,6,11...
		lastPage = (int)Math.ceil(userCount / (float)perPage);	//제일 마지막페이지
		endPage = startPage + pageUnit - 1;		// ex) 5,10,15...
		endPage = endPage < lastPage ? endPage : lastPage;		//정보가 얼마 없을때를 대비
		prevPage = (startPage - pageUnit);		//5개 나오는 페이지에서 제일 첫번째꺼 ex)1,6,11...에서 6이면 prevPage는 1
		nextPage = (startPage + pageUnit);		//5개 나오는 페이지에서 제일 첫번째꺼 ex)1,6,11...에서 6이면 nextPage는 11
	}
		
	public int getUserCount() {
		return userCount;
	}
	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public int getLastPage() {
		return lastPage;
	}
	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}
	public int getPrevPage() {
		return prevPage;
	}
	public void setPrevPage(int prevPage) {
		this.prevPage = prevPage;
	}
	public int getNextPage() {
		return nextPage;
	}
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}
	public int getPageunit() {
		return pageUnit;
	}
	public int getPerpage() {
		return perPage;
	}
	public Search getSearch() {
		return search;
	}
	public void setSearch(Search search) {
		this.search = search;
	}
	
	
	

}
