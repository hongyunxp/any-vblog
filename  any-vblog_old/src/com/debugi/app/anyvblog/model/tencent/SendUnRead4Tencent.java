package com.debugi.app.anyvblog.model.tencent;

import com.debugi.app.anyvblog.model.SendData;
import com.debugi.open.oauth.model.Parameters;

public class SendUnRead4Tencent implements SendData {

	private String format;//	 返回数据的格式（json或xml）
	private int op;//	 请求类型 0-仅查询，1-查询完毕后将相应计数清0
	private int type;//	 5-首页未读消息计数，6-@页未读消息计数，7-私信页消息计数，8-新增听众数，9-首页广播数（原创的） op=0时，type默认为0，此时返回所有类型计数；op=1时，需带上某种类型的type，清除该type类型的计数，并返回所有类型计数
	/**
	 * 请求未读信息
	 * @param op default 1; 0=only read
	 * @param type default 5, clear index unread count
	 */
	public SendUnRead4Tencent(int op, int type) {
		this("json", op, type);
	}

	public SendUnRead4Tencent(String format, int op, int type) {
		this.format = format;
		this.op = op;
		this.type = type;
	}

	@Override
	public Parameters conver() {
		Parameters parameters = new Parameters();
		parameters.addParameter("format", format);
		parameters.addParameter("op", op + "");
		parameters.addParameter("type", type + "");
		return parameters;
	}

}
