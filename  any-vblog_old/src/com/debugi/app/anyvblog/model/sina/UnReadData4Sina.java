package com.debugi.app.anyvblog.model.sina;

import com.debugi.app.anyvblog.model.UnReadAdapter;

public class UnReadData4Sina {
	/**
	 * {"new_status":1,"followers":0,"status":3,"dm":0,"mentions":0,"comments":0}
	 */
	private int comments;
    private int followers;
    private int new_status;
    private int dm;
    private int mentions;
    private int status;
    public UnReadAdapter conver() {
    	UnReadAdapter adapter = new UnReadAdapter();
    	adapter.setComments(comments);
    	adapter.setDirectmessages(dm);
    	adapter.setFollowers(followers);
    	adapter.setMentions(mentions);
    	adapter.setStatus(status);
    	return adapter;
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
	public int getNew_status() {
		return new_status;
	}
	public void setNew_status(int new_status) {
		this.new_status = new_status;
	}
	public int getDm() {
		return dm;
	}
	public void setDm(int dm) {
		this.dm = dm;
	}
	public int getMentions() {
		return mentions;
	}
	public void setMentions(int mentions) {
		this.mentions = mentions;
	}
}
