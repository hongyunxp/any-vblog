package com.debugi.app.anyvblog.model.netease;

public class MusicInfo4Netease {
	private String shortUrl;
	private String songUrl;
	private String songName;
	private String singer;
	public String getShortUrl() {
		return shortUrl;
	}
	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}
	public String getSongUrl() {
		return songUrl;
	}
	public void setSongUrl(String songUrl) {
		this.songUrl = songUrl;
	}
	public String getSongName() {
		return songName;
	}
	public void setSongName(String songName) {
		this.songName = songName;
	}
	public String getSinger() {
		return singer;
	}
	public void setSinger(String singer) {
		this.singer = singer;
	}
	@Override
	public String toString() {
		return "MusicInfo4Netease [shortUrl=" + shortUrl + ", songUrl="
				+ songUrl + ", songName=" + songName + ", singer=" + singer
				+ "]";
	}
	
}
