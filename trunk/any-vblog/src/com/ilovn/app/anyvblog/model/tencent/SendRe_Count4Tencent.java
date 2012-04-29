package com.ilovn.app.anyvblog.model.tencent;

import java.util.List;

import com.ilovn.app.anyvblog.model.SendData;
import com.ilovn.open.oauth.model.Parameters;

public class SendRe_Count4Tencent implements SendData {

	private String format; // format 返回数据的格式（json或xml）
	private List<String> ids; // ids 微博id的列表
	private String flag; // flag 0－获取转发计数，1－获取点评计数 2－两者都获取

	
	public SendRe_Count4Tencent(List<String> ids) {
		this(ids, "2");
	}

	public SendRe_Count4Tencent(List<String> ids, String flag) {
		this(Format.json.name(), ids, flag);
	}

	public SendRe_Count4Tencent(String format, List<String> ids, String flag) {
		this.format = format;
		this.ids = ids;
		this.flag = flag;
	}

	@Override
	public Parameters conver() {
		Parameters parameters = new Parameters();
		StringBuffer sb = new StringBuffer();
		for (String id : ids) {
			sb.append(id).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		parameters.addParameter("format", format);
		parameters.addParameter("ids", sb.toString());
		parameters.addParameter("flag", flag);
		return parameters;
	}

	private enum Format {
		json, xml
	}
}
