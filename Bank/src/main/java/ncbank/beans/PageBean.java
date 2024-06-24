package ncbank.beans;

public class PageBean {

	private int min;
	private int max;
	private int prevPage;
	private int nextPage;
	private int pageCnt;
	private int currentPage;

	public PageBean(int contentCnt, int currentPage, int contentPagecnt, int paginationCnt) {

		// 현재 페이지 번호
		this.currentPage = currentPage;
		// 전체 페이지 개수
		pageCnt = contentCnt / contentPagecnt;

		if (contentCnt % contentPagecnt > 0) {
			pageCnt++;
		} // if

		min = ((currentPage - 1) / contentPagecnt) * contentPagecnt + 1;
		max = min + paginationCnt - 1;

		if (max > pageCnt) {
			max = pageCnt;
		}

		prevPage = min - 1;
		nextPage = max + 1;
		// 마지막 페이지를 넘어가지 않도록
		if (nextPage > pageCnt) {
			nextPage = pageCnt;
		}

	}

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}

	public int getPrevPage() {
		return prevPage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public int getPageCnt() {
		return pageCnt;
	}

	public int getCurrentPage() {
		return currentPage;
	}

}
