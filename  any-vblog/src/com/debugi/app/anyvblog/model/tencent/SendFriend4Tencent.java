package com.debugi.app.anyvblog.model.tencent;

import com.debugi.app.anyvblog.model.SendData;
import com.debugi.open.oauth.model.Parameters;
/**
 * 添加关注和取消关注
 * @author Administrator
 *
 */
public class SendFriend4Tencent implements SendData {
	private String format;// 返回数据的格式（json或xml）
	private String name;// 他人的帐户名

	public SendFriend4Tencent(String name) {
		this("json", name);
	}

	public SendFriend4Tencent(String format, String name) {
		this.format = format;
		this.name = name;
	}

	@Override
	public Parameters conver() {
		Parameters parameters = new Parameters();
		parameters.addParameter("format", format);
		parameters.addParameter("name", name);
		return parameters;
	}

}
