package com.debugi.app.anyvblog.model.tencent;

import com.debugi.app.anyvblog.model.Error;

public class ErrorImpl4Tencent implements Error {
	private int ret;
	private int errcode;
	private Object data;
	private String msg;
	@Override
	public String getMsg() {
//		String idString = "";
//		if ((code + "").length() == 1) {
//			idString = "tencent_" + code;
//		} else {
//			idString = "tencent_" + (code + "").substring(0, 1) + "_" + (code + "").substring(1);
//		}
//		try {
//			idString = R.string.class.getField(idString).get(idString).toString();
//			int Rid = Integer.parseInt(idString);
//			return context.getString(Rid);
//		} catch (Exception e1) {
//			Config.debug("error", "have exception");
//		}
		return msg;
		
	}
	public int getRet() {
		return ret;
	}
	public void setRet(int ret) {
		this.ret = ret;
	}
	public int getErrcode() {
		return errcode;
	}
	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
