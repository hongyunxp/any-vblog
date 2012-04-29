package com.ilovn.app.anyvblog.model.netease;

import java.util.List;

public class GetUsers4Netease {
	private int next_cursor;
	private int previous_cursor;
	private List<UserInfo4Netease> users;
	public int getNext_cursor() {
		return next_cursor;
	}
	public void setNext_cursor(int next_cursor) {
		this.next_cursor = next_cursor;
	}
	public int getPrevious_cursor() {
		return previous_cursor;
	}
	public void setPrevious_cursor(int previous_cursor) {
		this.previous_cursor = previous_cursor;
	}
	public List<UserInfo4Netease> getUsers() {
		return users;
	}
	public void setUsers(List<UserInfo4Netease> users) {
		this.users = users;
	}
}
