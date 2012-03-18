package com.debugi.app.anyvblog.model.sina;

import com.debugi.app.anyvblog.model.Counts;

public class Counts4Sina {
	private String id;
    private int comments;
    private int rt;
    public Counts conver() {
    	return new Counts(id, comments, rt);
    }
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getComments() {
		return comments;
	}
	public void setComments(int comments) {
		this.comments = comments;
	}
	public int getRt() {
		return rt;
	}
	public void setRt(int rt) {
		this.rt = rt;
	}
	@Override
	public String toString() {
		return "Counts4Sina [id=" + id + ", comments=" + comments + ", rt=" + rt
				+ "]";
	}
}
