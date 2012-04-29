package com.debugi.app.anyvblog.model.sohu;

import java.util.Map;

import com.debugi.app.anyvblog.model.User;
import com.debugi.app.anyvblog.model.UserAdapter;
import com.debugi.app.anyvblog.utils.Constants;

public class UserInfo4Sohu implements User {
	private String id;
	private String screen_name;
	private String name;
	private String location;
	private String description;
	private String url;
	private String profile_image_url;
	private boolean protect;	//protected
	private int followers_count;
	private String profile_background_color;
	private String profile_text_color;
	private String profile_link_color;
	private String profile_sidebar_fill_color;
	private String profile_sidebar_border_color;
	private int friends_count;
	private String created_at;
	private int favourites_count;
	private String utc_offset;
	private String gender;
	private String time_zone;
	private String profile_background_image_url;
	private String notifications;
	private boolean geo_enabled;
	private int statuses_count;
	private boolean following;
	private boolean verified;
	private String lang;
	private boolean contributors_enabled;
	
	@Override
	public UserAdapter conver2User() {
		UserAdapter adapter = new UserAdapter();
		adapter.setSp(Constants.SP_SOHU);
		adapter.setUser_id(id);
		adapter.setName(name);
		adapter.setNick(screen_name);
		//adapter.setHead(null);
		adapter.setHead_url(profile_image_url);
		adapter.setCount_followers(followers_count);
		adapter.setCount_friends(friends_count);
		adapter.setCount_status(statuses_count);
		if ("0".equals(gender)) {
			adapter.setSex("男");
		} else if("1".equals(gender)) {
			adapter.setSex("女");
		} else {
			adapter.setSex("未知");
		}
		adapter.setDescription(description);
		adapter.setLocation(location);
		adapter.setIsvip(verified);
		adapter.setFriend(following);
		return adapter;
	}

	@Override
	public Map<String, Object> conver() {
		// TODO Auto-generated method stub
		return null;
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getProfile_image_url() {
		return profile_image_url;
	}

	public void setProfile_image_url(String profile_image_url) {
		this.profile_image_url = profile_image_url;
	}

	public boolean isProtect() {
		return protect;
	}

	public void setProtect(boolean protect) {
		this.protect = protect;
	}

	public int getFollowers_count() {
		return followers_count;
	}

	public void setFollowers_count(int followers_count) {
		this.followers_count = followers_count;
	}

	public String getProfile_background_color() {
		return profile_background_color;
	}

	public void setProfile_background_color(String profile_background_color) {
		this.profile_background_color = profile_background_color;
	}

	public String getProfile_text_color() {
		return profile_text_color;
	}

	public void setProfile_text_color(String profile_text_color) {
		this.profile_text_color = profile_text_color;
	}

	public String getProfile_link_color() {
		return profile_link_color;
	}

	public void setProfile_link_color(String profile_link_color) {
		this.profile_link_color = profile_link_color;
	}

	public String getProfile_sidebar_fill_color() {
		return profile_sidebar_fill_color;
	}

	public void setProfile_sidebar_fill_color(String profile_sidebar_fill_color) {
		this.profile_sidebar_fill_color = profile_sidebar_fill_color;
	}

	public String getProfile_sidebar_border_color() {
		return profile_sidebar_border_color;
	}

	public void setProfile_sidebar_border_color(String profile_sidebar_border_color) {
		this.profile_sidebar_border_color = profile_sidebar_border_color;
	}

	public int getFriends_count() {
		return friends_count;
	}

	public void setFriends_count(int friends_count) {
		this.friends_count = friends_count;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public int getFavourites_count() {
		return favourites_count;
	}

	public void setFavourites_count(int favourites_count) {
		this.favourites_count = favourites_count;
	}

	public String getUtc_offset() {
		return utc_offset;
	}

	public void setUtc_offset(String utc_offset) {
		this.utc_offset = utc_offset;
	}

	public String getTime_zone() {
		return time_zone;
	}

	public void setTime_zone(String time_zone) {
		this.time_zone = time_zone;
	}

	public String getProfile_background_image_url() {
		return profile_background_image_url;
	}

	public void setProfile_background_image_url(String profile_background_image_url) {
		this.profile_background_image_url = profile_background_image_url;
	}

	public String getNotifications() {
		return notifications;
	}

	public void setNotifications(String notifications) {
		this.notifications = notifications;
	}

	public boolean isGeo_enabled() {
		return geo_enabled;
	}

	public void setGeo_enabled(boolean geo_enabled) {
		this.geo_enabled = geo_enabled;
	}

	public int getStatuses_count() {
		return statuses_count;
	}

	public void setStatuses_count(int statuses_count) {
		this.statuses_count = statuses_count;
	}

	public boolean isFollowing() {
		return following;
	}

	public void setFollowing(boolean following) {
		this.following = following;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public boolean isContributors_enabled() {
		return contributors_enabled;
	}

	public void setContributors_enabled(boolean contributors_enabled) {
		this.contributors_enabled = contributors_enabled;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "UserInfo4Sohu [id=" + id + ", screen_name=" + screen_name
				+ ", name=" + name + ", location=" + location
				+ ", description=" + description + ", url=" + url
				+ ", profile_image_url=" + profile_image_url + ", protect="
				+ protect + ", followers_count=" + followers_count
				+ ", profile_background_color=" + profile_background_color
				+ ", profile_text_color=" + profile_text_color
				+ ", profile_link_color=" + profile_link_color
				+ ", profile_sidebar_fill_color=" + profile_sidebar_fill_color
				+ ", profile_sidebar_border_color="
				+ profile_sidebar_border_color + ", friends_count="
				+ friends_count + ", created_at=" + created_at
				+ ", favourites_count=" + favourites_count + ", utc_offset="
				+ utc_offset + ", time_zone=" + time_zone
				+ ", profile_background_image_url="
				+ profile_background_image_url + ", notifications="
				+ notifications + ", geo_enabled=" + geo_enabled
				+ ", statuses_count=" + statuses_count + ", following="
				+ following + ", verified=" + verified + ", lang=" + lang
				+ ", contributors_enabled=" + contributors_enabled + "]";
	}
}
