package com.debugi.app.anyvblog.model.tencent;

public class GetUserInfos4Tencent {
	private int ret; // 返回值，0-成功，非0-失败
	private String msg; // 错误信息
	private String errcode; // 返回错误码
	private ListUserInfos4Tencent data;
	public int getRet() {
		return ret;
	}
	public void setRet(int ret) {
		this.ret = ret;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getErrcode() {
		return errcode;
	}
	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}
	public ListUserInfos4Tencent getData() {
		return data;
	}
	public void setData(ListUserInfos4Tencent data) {
		this.data = data;
	}
}
