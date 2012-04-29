package com.debugi.app.anyvblog.model;

import java.util.HashMap;
import java.util.Map;

import com.debugi.app.anyvblog.utils.Constants;
import com.debugi.open.oauth.model.Parameters;

public class SendOneData4Tianya implements SendData {
	private String outformat;
	private String word;
	

	public SendOneData4Tianya(String word) {
		this(Format.json.name(), word);
	}
	public SendOneData4Tianya(String outformat, String word) {
		this.outformat = outformat;
		this.word = word;
	}
	@Override
	public Parameters conver() {
		init();
		Map<String, String> data = new HashMap<String, String>();
		data.put("word", word);
		data.put("outformat", outformat);
		return new Parameters(data);
	}
	/**
	 * 初始化一些判定
	 */
	private void init() {
		if (outformat == null || "".equals(outformat)) {
			outformat = Format.json.name();
		} else if (word== null || "".equals(word)) {
			word = Constants.DEFAULT_MSG;
		}
	}
	/**
	 * 返回数据的格式
	 * @author Administrator
	 *
	 */
	private enum Format {
		json, xml
	}

}
