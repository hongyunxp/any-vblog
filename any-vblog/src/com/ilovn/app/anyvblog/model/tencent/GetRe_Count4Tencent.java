package com.ilovn.app.anyvblog.model.tencent;

import java.util.HashMap;
import java.util.Map;

import com.ilovn.app.anyvblog.model.Data;
import com.ilovn.app.anyvblog.model.DataAdapter;

public class GetRe_Count4Tencent implements Data {
	private String ret;	//返回值，0-成功，非0-失败
	private String msg;	//错误信息
	private String errcode;	//返回错误码
	private HashMap<String, Re_CountData4Tencent> data;
	
	public String getRet() {
		return ret;
	}
	public void setRet(String ret) {
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
	public HashMap<String, Re_CountData4Tencent> getData() {
		return data;
	}
	public void setData(HashMap<String, Re_CountData4Tencent> data) {
		this.data = data;
	}
	@Override
	public Map<String, Object> conver() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String toString() {
		return "GetRe_Count4Tencent [ret=" + ret + ", msg=" + msg
				+ ", errcode=" + errcode + ", data=" + data + "]";
	}
	@Override
	public DataAdapter conver2Data() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
