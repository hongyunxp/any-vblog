package com.ilovn.app.anyvblog.model.netease;

public class VideoInfo4Netease {
	private String title;
	private String flashUrl;
	private String coverUrl;
	private String shortUrl;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFlashUrl() {
		return flashUrl;
	}
	public void setFlashUrl(String flashUrl) {
		this.flashUrl = flashUrl;
	}
	public String getCoverUrl() {
		return coverUrl;
	}
	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}
	public String getShortUrl() {
		return shortUrl;
	}
	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}
	@Override
	public String toString() {
		return "VideoInfo4Netease [title=" + title + ", flashUrl=" + flashUrl
				+ ", coverUrl=" + coverUrl + ", shortUrl=" + shortUrl + "]";
	}
	
}
