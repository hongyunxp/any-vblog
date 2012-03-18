package com.debugi.app.anyvblog.model.tencent;

public class ReturnData4Tencent {
	private String id; // id:微博的id
	private String time; // time:微博发表的时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Data [id=" + id + ", time=" + time + "]";
	}
}
