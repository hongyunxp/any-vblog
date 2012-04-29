package com.ilovn.app.anyvblog.model.tencent;

import java.util.List;

public class ListUserInfos4Tencent {
	private int totalnum;	//所有记录的总数
	private String timestamp; // 服务器时间戳，不能用于翻页
	private int hasnext;	//搜索接口无该参数
	private List<UserInfo4Tencent> info;
	public int getTotalnum() {
		return totalnum;
	}
	public void setTotalnum(int totalnum) {
		this.totalnum = totalnum;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public List<UserInfo4Tencent> getInfo() {
		return info;
	}
	public void setInfo(List<UserInfo4Tencent> info) {
		this.info = info;
	}
	public void setHasnext(int hasnext) {
		this.hasnext = hasnext;
	}
	public int getHasnext() {
		return hasnext;
	}
}
