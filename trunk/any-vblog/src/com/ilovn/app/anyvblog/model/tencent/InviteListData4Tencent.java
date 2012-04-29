package com.ilovn.app.anyvblog.model.tencent;

import java.util.List;

public class InviteListData4Tencent {
	private int totalnum;	//互听的总数
	private List<InviteData4Tencent> info;
	public int getTotalnum() {
		return totalnum;
	}
	public void setTotalnum(int totalnum) {
		this.totalnum = totalnum;
	}
	public List<InviteData4Tencent> getInfo() {
		return info;
	}
	public void setInfo(List<InviteData4Tencent> info) {
		this.info = info;
	}
	@Override
	public String toString() {
		return "InviteListData4Tencent [totalnum=" + totalnum + ", info="
				+ info + "]";
	}
}
