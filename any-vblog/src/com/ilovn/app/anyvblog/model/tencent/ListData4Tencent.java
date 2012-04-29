package com.ilovn.app.anyvblog.model.tencent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListData4Tencent {
	private String pos; // 本次拉取达到的最后一个位置，用于下次请求时的起始位置
	private int totalnum;	//所有记录的总数;在提及线才有的参数
	private String timestamp; // 服务器时间戳，不能用于翻页
	private int hasnext; // 0-表示还有微博可拉取，1-已拉取完毕
	private List<Data4Tencent> info;
	private HashMap<String, String> user;	//当页数据涉及到的用户的帐号与昵称映射

	public HashMap<String, String> getUser() {
		return user;
	}

	public void setUser(HashMap<String, String> user) {
		this.user = user;
	}

	public int getHasnext() {
		return hasnext;
	}

	public String getPos() {
		return pos;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setHasnext(int hasnext) {
		this.hasnext = hasnext;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public List<Data4Tencent> getInfo() {
		if (user == null) {
			return info;
		}
		/**
		 * 将微博内容数据中的“@”用户的账户名称改为昵称
		 */
		List<Data4Tencent> data4Tencents = new ArrayList<Data4Tencent>();
		for(Data4Tencent temp : info) {
			for(Map.Entry<String, String> entry : user.entrySet()) {
				String name = entry.getKey();
				String nick = entry.getValue();
				String content = temp.getText();
				content = content.replace(name, nick);
				temp.setText(content);
			}
			data4Tencents.add(temp);
		}
		return data4Tencents;
//		return info;
	}

	public void setInfo(List<Data4Tencent> info) {
		this.info = info;
	}

	public void setTotalnum(int totalnum) {
		this.totalnum = totalnum;
	}

	public int getTotalnum() {
		return totalnum;
	}
}
