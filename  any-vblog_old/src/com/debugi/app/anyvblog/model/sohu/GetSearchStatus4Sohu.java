package com.debugi.app.anyvblog.model.sohu;

import java.util.List;

public class GetSearchStatus4Sohu {
	private int current_total_count;
	private List<Status4Sohu> statuses;
	public int getCurrent_total_count() {
		return current_total_count;
	}
	public void setCurrent_total_count(int current_total_count) {
		this.current_total_count = current_total_count;
	}
	public List<Status4Sohu> getStatuses() {
		return statuses;
	}
	public void setStatuses(List<Status4Sohu> statuses) {
		this.statuses = statuses;
	}
}
