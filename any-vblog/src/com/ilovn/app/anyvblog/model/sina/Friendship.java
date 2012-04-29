package com.ilovn.app.anyvblog.model.sina;

public class Friendship {
	private String id;//245110499
	private String screen_name;//"245110499"
    private boolean following; //是否关注
    private boolean followed_by;	//是否被关注
    private boolean notifications_enabled;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getScreen_name() {
		return screen_name;
	}
	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}
	public boolean isFollowing() {
		return following;
	}
	public void setFollowing(boolean following) {
		this.following = following;
	}
	public boolean isFollowed_by() {
		return followed_by;
	}
	public void setFollowed_by(boolean followed_by) {
		this.followed_by = followed_by;
	}
	public boolean isNotifications_enabled() {
		return notifications_enabled;
	}
	public void setNotifications_enabled(boolean notifications_enabled) {
		this.notifications_enabled = notifications_enabled;
	}
}
