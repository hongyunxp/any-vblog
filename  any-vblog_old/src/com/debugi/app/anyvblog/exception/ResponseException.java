package com.debugi.app.anyvblog.exception;

import com.debugi.app.anyvblog.model.netease.ErrorImpl4Netease;
import com.debugi.app.anyvblog.model.sina.ErrorImpl4Sina;
import com.debugi.app.anyvblog.model.sohu.ErrorImpl4Sohu;
import com.debugi.app.anyvblog.model.tencent.ErrorImpl4Tencent;
import com.debugi.app.anyvblog.utils.Config;
import com.debugi.app.anyvblog.utils.Constants;
import com.debugi.open.oauth.model.Response;
import com.google.gson.Gson;

public class ResponseException extends Exception {
	private static final long serialVersionUID = -4639281510792831483L;
	private static final String TAG = "ResponseException";
	private Response response;
	private int sp;
	private String tip;

	public ResponseException(Response response, int sp) {
		this.response = response;
		this.sp = sp;
		tip = "";
		show();
	}

	public void show() {
		Config.debug(TAG, response.getBody());
		Gson gson = new Gson();
		try {
			switch (sp) {
			case Constants.SP_TENCENT:
				ErrorImpl4Tencent errorImpl4Tencent = gson.fromJson(response.getBody(), ErrorImpl4Tencent.class);
				tip = errorImpl4Tencent.getMsg();
				break;
			case Constants.SP_SINA:
				ErrorImpl4Sina errorImpl4Sina = gson.fromJson(response.getBody(), ErrorImpl4Sina.class);
				tip = errorImpl4Sina.getMsg();
				break;
			case Constants.SP_NETEASE:
				ErrorImpl4Netease errorImpl4Netease = gson.fromJson(response.getBody(), ErrorImpl4Netease.class);
				tip = errorImpl4Netease.getMsg();
				break;
			case Constants.SP_SOHU:
				ErrorImpl4Sohu errorImpl4Sohu = gson.fromJson(response.getBody(), ErrorImpl4Sohu.class);
				tip = errorImpl4Sohu.getMsg();
				break;
			default:
				break;
			}
		} catch (Exception e) {
			Config.debug(TAG, e.getMessage());
		}
		
		Config.debug(TAG, "tip:" + tip);
	}

	@Override
	public String getMessage() {
		return tip;
	}
	
}
