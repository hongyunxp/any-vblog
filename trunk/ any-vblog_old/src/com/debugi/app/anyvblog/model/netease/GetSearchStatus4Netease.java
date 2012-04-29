package com.debugi.app.anyvblog.model.netease;

import java.util.List;

public class GetSearchStatus4Netease {
	private int total;
	private List<Status4Netease> results;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<Status4Netease> getResults() {
		return results;
	}
	public void setResults(List<Status4Netease> results) {
		this.results = results;
	}
}
