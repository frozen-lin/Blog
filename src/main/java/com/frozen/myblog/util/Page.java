package com.frozen.myblog.util;

import java.util.List;

public class Page<T> {
	// 当前页码
	private int curr;
	// 数据总条数
	private int total;
	// 每页数据条数,默认5
	private int limit = 5;
	// 总页数
	private int pageTotal;
	//数据
	private List<T> list ;
	public Page() {

	}

	public int getCurr() {
		return curr;
	}

	public void setCurr(int curr) {
		this.curr = curr;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getPageTotal() {
		return pageTotal;
	}

	public void setPageTotal(int pageTotal) {
		this.pageTotal = pageTotal;
	}

	// 计算总页数
	public void calculatePageTotal() {
		this.pageTotal = (this.total % this.limit == 0) ? this.total / this.limit : this.total / this.limit + 1;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "Page [curr=" + curr + ", total=" + total + ", limit=" + limit + ", pageTotal=" + pageTotal + ", list="
				+ list + "]";
	}
	
}
