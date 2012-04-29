package com.debugi.app.anyvblog.model.tencent;

import java.util.Map;

import com.debugi.app.anyvblog.model.User;
import com.debugi.app.anyvblog.model.UserAdapter;
import com.debugi.app.anyvblog.utils.Constants;

public class InviteData4Tencent implements User {
	private String name;	//帐户名
	private String nick;	//昵称
	private String head;	//头像url
	private String openid;	//用户唯一id，与name相对应
	private int isvip;	//是否是认证用户，0-没有认证，1-认证用户
	private int sex;	//性别，1-男，2-女，其它值为没有设置
	
	@Override
	public UserAdapter conver2User() {
		UserAdapter adapter = new UserAdapter();
		adapter.setUser_id(openid);
		adapter.setName(name);
		adapter.setNick(nick);
		adapter.setHead_url(head);
		adapter.setSp(Constants.SP_TENCENT);
		if (isvip == 1) {
			adapter.setIsvip(true);
		} else {
			adapter.setIsvip(false);
		}
		if (sex == 1) {
			adapter.setSex("男");
		} else if(sex == 2) {
			adapter.setSex("女");
		} else {
			adapter.setSex("未填写");
		}
		return adapter;
	}

	@Override
	public Map<String, Object> conver() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public int getIsvip() {
		return isvip;
	}

	public void setIsvip(int isvip) {
		this.isvip = isvip;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return "InviteData4Tencent [name=" + name + ", nick=" + nick
				+ ", head=" + head + ", openid=" + openid + ", isvip=" + isvip
				+ ", sex=" + sex + "]";
	}
}
