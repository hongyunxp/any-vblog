package com.debugi.app.anyvblog.model.tencent;

import com.debugi.app.anyvblog.model.UnReadAdapter;
import com.google.gson.annotations.SerializedName;

public class UnReadData4Tencent {
	private int home;//首页未读消息计数
	@SerializedName("private")
	private int directmessage;//私信页未读消息计数
	private int fans;//新增听众数
	private int mentions;//提及我的未读消息计数
	private int create;//首页广播（原创）更新数
	
	public UnReadAdapter conver() {
		UnReadAdapter adapter = new UnReadAdapter();
		adapter.setStatus(home + create);
//		adapter.setComments(mentions);
		adapter.setDirectmessages(directmessage);
		adapter.setFollowers(fans);
		adapter.setMentions(mentions);
		return adapter;
	}
	
	public int getHome() {
		return home;
	}
	public void setHome(int home) {
		this.home = home;
	}
	public int getDirectmessage() {
		return directmessage;
	}
	public void setDirectmessage(int directmessage) {
		this.directmessage = directmessage;
	}
	public int getFans() {
		return fans;
	}
	public void setFans(int fans) {
		this.fans = fans;
	}
	public int getMentions() {
		return mentions;
	}
	public void setMentions(int mentions) {
		this.mentions = mentions;
	}
	public int getCreate() {
		return create;
	}
	public void setCreate(int create) {
		this.create = create;
	}
	

}
