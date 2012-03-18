package com.debugi.app.anyvblog.model.netease;

import java.util.List;

public class GetSearchUser4Netease {
	private int total;
	private List<UserInfo4Netease> results;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<UserInfo4Netease> getResults() {
		return results;
	}
	public void setResults(List<UserInfo4Netease> results) {
		this.results = results;
	}
}
