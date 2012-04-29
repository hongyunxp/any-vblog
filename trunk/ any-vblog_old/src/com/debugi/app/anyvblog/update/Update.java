package com.debugi.app.anyvblog.update;

import java.io.Serializable;

public class Update implements Serializable {
	private static final long serialVersionUID = 3314373375452832933L;
	private String version;
	private String url;
	private String update_time;
	private String size;
	private String desc;
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getSize() {
		return size;
	}
	@Override
	public String toString() {
		return "Update [version=" + version + ", url=" + url + ", update_time="
				+ update_time + ", size=" + size + ", desc=" + desc + "]";
	}
}
