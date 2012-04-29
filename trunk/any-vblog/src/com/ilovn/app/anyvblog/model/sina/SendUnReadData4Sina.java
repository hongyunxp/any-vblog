package com.ilovn.app.anyvblog.model.sina;

import com.ilovn.app.anyvblog.model.SendData;
import com.ilovn.open.oauth.model.Parameters;

public class SendUnReadData4Sina implements SendData {
	private int with_new_status;//默认为0。	1表示结果中包含new_status字段，0表示结果不包含new_status字段。new_status字段表示是否有新微博消息，1表示有，0表示没有
	private String since_id;//参数值为微博id。该参数需配合with_new_status参数使用，返回since_id之后，是否有新微博消息产生
	/**
	 * 是否包含new status, new_status字段表示是否有新微博消息，1表示有，0表示没有
	 * @param with_new_status
	 */
	public SendUnReadData4Sina(int with_new_status) {
		this(with_new_status, null);
	}

	public SendUnReadData4Sina(int with_new_status, String since_id) {
		this.with_new_status = with_new_status;
		this.since_id = since_id;
	}

	@Override
	public Parameters conver() {
		Parameters parameters = new Parameters();
		parameters.addParameter("with_new_status", with_new_status + "");
		if (since_id != null) {
			parameters.addParameter("since_id", since_id);
		}
		return parameters;
	}

}
