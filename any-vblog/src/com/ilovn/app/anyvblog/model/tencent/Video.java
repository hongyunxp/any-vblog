package com.ilovn.app.anyvblog.model.tencent;

public class Video {
	private String picurl; // 缩略图
	private String player; // 播放器地址
	private String realurl; // 视频原地址
	private String shorturl; // 视频的短url
	private String title; // 视频标题

	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

	public String getRealurl() {
		return realurl;
	}

	public void setRealurl(String realurl) {
		this.realurl = realurl;
	}

	public String getShorturl() {
		return shorturl;
	}

	public void setShorturl(String shorturl) {
		this.shorturl = shorturl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
