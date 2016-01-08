package cn.org.njsoft.model;

/**
 * 2015/12/6
 * PageDivided的封装类
 * @see cn.org.njsoft.model#PageDivided
 * @author JAQ
 *
 */
public class PageDivided {
	private int currentPage = 1;//当前页
	private int total;//数据总条数
	private int totalPage;//总页数
	private int start;//起始页
	private int end = 5;;//一页有几条数据
	
	public int getCurrentPage() {//get方法
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {//set方法
		this.currentPage = currentPage;
	}
	public int getTotalPage() {//get方法
		return totalPage;
	}
	public void setTotalPage(int totalPage) {//set方法
		this.totalPage = totalPage;
	}
	public int getStart() {//get方法
		return start;
	}
	public void setStart(int start) {//set方法
		this.start = start;
	}
	public int getEnd() {//get方法
		return end;
	}
	public void setEnd(int end) {//set方法
		this.end = end;
	}
	public int getTotal() {//get方法
		return total;
	}
	public void setTotal(int total) {//set方法
		this.total = total;
	}
}
