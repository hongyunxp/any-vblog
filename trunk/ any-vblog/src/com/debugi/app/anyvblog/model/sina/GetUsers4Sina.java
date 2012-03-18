package com.debugi.app.anyvblog.model.sina;

import java.util.List;

public class GetUsers4Sina {
	private List<UserInfo4Sina> users;
	private int previous_cursor;
	private int next_cursor;
	public List<UserInfo4Sina> getUsers() {
		return users;
	}
	public void setUsers(List<UserInfo4Sina> users) {
		this.users = users;
	}
	public int getPrevious_cursor() {
		return previous_cursor;
	}
	public void setPrevious_cursor(int previous_cursor) {
		this.previous_cursor = previous_cursor;
	}
	public int getNext_cursor() {
		return next_cursor;
	}
	public void setNext_cursor(int next_cursor) {
		this.next_cursor = next_cursor;
	}
}
