package com.debugi.app.anyvblog.model.sohu;

import com.debugi.app.anyvblog.model.UnReadAdapter;

public class UnReadData4Sohu {
	private int new_msg_count;
	private int new_at_count;
	private int new_comment_count;
	private int new_followers_count;
	public UnReadAdapter conver() {
		UnReadAdapter adapter = new UnReadAdapter();
		adapter.setComments(new_comment_count);
		adapter.setFollowers(new_followers_count);
		adapter.setMentions(new_at_count);
		adapter.setStatus(new_msg_count);
		return adapter;
	}
	public int getNew_msg_count() {
		return new_msg_count;
	}
	public void setNew_msg_count(int new_msg_count) {
		this.new_msg_count = new_msg_count;
	}
	public int getNew_at_count() {
		return new_at_count;
	}
	public void setNew_at_count(int new_at_count) {
		this.new_at_count = new_at_count;
	}
	public int getNew_comment_count() {
		return new_comment_count;
	}
	public void setNew_comment_count(int new_comment_count) {
		this.new_comment_count = new_comment_count;
	}
	public int getNew_followers_count() {
		return new_followers_count;
	}
	public void setNew_followers_count(int new_followers_count) {
		this.new_followers_count = new_followers_count;
	}
}
