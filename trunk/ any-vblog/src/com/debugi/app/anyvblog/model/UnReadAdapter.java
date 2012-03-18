package com.debugi.app.anyvblog.model;


public class UnReadAdapter {
	private int status;
	private int mentions;
	private int comments;
	private int followers;
	private int directmessages;
	public int sum() {
		return status + mentions + comments + followers + directmessages;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getMentions() {
		return mentions;
	}
	public void setMentions(int mentions) {
		this.mentions = mentions;
	}
	public int getComments() {
		return comments;
	}
	public void setComments(int comments) {
		this.comments = comments;
	}
	public int getFollowers() {
		return followers;
	}
	public void setFollowers(int followers) {
		this.followers = followers;
	}
	public int getDirectmessages() {
		return directmessages;
	}
	public void setDirectmessages(int directmessages) {
		this.directmessages = directmessages;
	}
}
