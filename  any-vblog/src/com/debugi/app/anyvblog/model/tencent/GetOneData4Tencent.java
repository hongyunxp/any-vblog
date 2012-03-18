package com.debugi.app.anyvblog.model.tencent;



public class GetOneData4Tencent {
	private int ret; // 返回值，0-成功，非0-失败
	private String msg; // 错误信息
	private String errcode; // 返回错误码

	private Data4Tencent data;
	

	public Data4Tencent getData() {
		return data;
	}

	public void setData(Data4Tencent data) {
		this.data = data;
	}

	public String getErrcode() {
		return errcode;
	}

	public String getMsg() {
		return msg;
	}

	public int getRet() {
		return ret;
	}

	
	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setRet(int ret) {
		this.ret = ret;
	}

	@Override
	public String toString() {
		return "GetOneData4Tencent [ret=" + ret + ", msg=" + msg + ", errcode="
				+ errcode + ", data=" + data + "]";
	}

	
}
