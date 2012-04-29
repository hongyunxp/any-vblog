package com.ilovn.app.anyvblog.model.tencent;

public class GetSendOne4Tencent {
	private int ret; // ret: 返回值，0-成功，非0-失败
	private String msg; // msg:错误信息
	private String errcode; // errcode:返回错误码
	private ReturnData4Tencent data; //

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

	public ReturnData4Tencent getData() {
		return data;
	}

	public void setData(ReturnData4Tencent data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ReturnData4TencentWithSendOne [ret=" + ret + ", msg=" + msg
				+ ", errcode=" + errcode + ", data=" + data + "]";
	}

}
