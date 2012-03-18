package com.debugi.app.anyvblog.model.tencent;

import java.util.Map;

import com.debugi.app.anyvblog.model.Data;
import com.debugi.app.anyvblog.model.DataAdapter;

public class GetUserInfo4Tencent implements Data {
	private int ret; // 返回值，0-成功，非0-失败
	private String msg; // 错误信息
	private String errcode; // 返回错误码
	private UserInfo4Tencent data;
	
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
	public UserInfo4Tencent getData() {
		return data;
	}
	public void setData(UserInfo4Tencent data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "GetUserInfo4Tencent [ret=" + ret + ", msg=" + msg
				+ ", errcode=" + errcode + ", data=" + data + "]";
	}
	@Override
	public Map<String, Object> conver() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public DataAdapter conver2Data() {
		// TODO Auto-generated method stub
		return null;
	}

}
