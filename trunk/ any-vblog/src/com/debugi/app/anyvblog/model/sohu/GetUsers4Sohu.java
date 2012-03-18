package com.debugi.app.anyvblog.model.sohu;

import java.util.List;

public class GetUsers4Sohu {
	private int cursor_id;
	private List<UserInfo4Sohu> users;
	public int getCursor_id() {
		return cursor_id;
	}
	public void setCursor_id(int cursor_id) {
		this.cursor_id = cursor_id;
	}
	public List<UserInfo4Sohu> getUsers() {
		return users;
	}
	public void setUsers(List<UserInfo4Sohu> users) {
		this.users = users;
	}
}
