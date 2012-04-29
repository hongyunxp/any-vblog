package com.ilovn.app.anyvblog.model.netease;

import java.util.Map;

import com.ilovn.app.anyvblog.model.User;
import com.ilovn.app.anyvblog.model.UserAdapter;
import com.ilovn.app.anyvblog.utils.Constants;

public class UserInfo4Netease implements User {
	private String name;
	private String location;
	private String id;
	private String description;
	private Status4Netease status;
	private String screen_name;
	private String gender;
	private String friends_count;
	private String followers_count;
	private String statuses_count;
	private String created_at;
	private String favourites_count;
	private String profile_image_url;
	private String url;
	private boolean blocking;
	private boolean following;
	private boolean followed_by;
	private boolean verified;
	private boolean geo_enabled;

	@Override
	public UserAdapter conver2User() {
		UserAdapter adapter = new UserAdapter();
		adapter.setSp(Constants.SP_NETEASE);
		adapter.setNick(name);
		adapter.setName(screen_name);
		adapter.setUser_id(id);
		//adapter.setHead(null);
		adapter.setHead_url(profile_image_url);
		adapter.setCount_followers(Integer.parseInt(followers_count));
		adapter.setCount_friends(Integer.parseInt(friends_count));
		adapter.setCount_status(Integer.parseInt(statuses_count));
		if ("1".equals(gender)) {
			adapter.setSex("男");
		} else if("2".equals(gender)) {
			adapter.setSex("女");
		} else {
			adapter.setSex("未知");
		}
		adapter.setDescription(description);
		adapter.setLocation(location);
		adapter.setIsvip(verified);
		adapter.setFriend(following);
		adapter.setFollower(followed_by);
		if (status != null) {
			adapter.setStatus(status.conver2Data());
		}
		return adapter;
	}

	@Override
	public Map<String, Object> conver() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isGeo_enabled() {
		return geo_enabled;
	}

	public void setGeo_enabled(boolean geo_enabled) {
		this.geo_enabled = geo_enabled;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Status4Netease getStatus() {
		return status;
	}

	public void setStatus(Status4Netease status) {
		this.status = status;
	}

	public String getScreen_name() {
		return screen_name;
	}

	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getFriends_count() {
		return friends_count;
	}

	public void setFriends_count(String friends_count) {
		this.friends_count = friends_count;
	}

	public String getFollowers_count() {
		return followers_count;
	}

	public void setFollowers_count(String followers_count) {
		this.followers_count = followers_count;
	}

	public String getStatuses_count() {
		return statuses_count;
	}

	public void setStatuses_count(String statuses_count) {
		this.statuses_count = statuses_count;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getFavourites_count() {
		return favourites_count;
	}

	public void setFavourites_count(String favourites_count) {
		this.favourites_count = favourites_count;
	}

	public String getProfile_image_url() {
		return profile_image_url;
	}

	public void setProfile_image_url(String profile_image_url) {
		this.profile_image_url = profile_image_url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isBlocking() {
		return blocking;
	}

	public void setBlocking(boolean blocking) {
		this.blocking = blocking;
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

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	@Override
	public String toString() {
		return "UserInfo4Netease [name=" + name + ", location=" + location
				+ ", id=" + id + ", description=" + description + ", status="
				+ status + ", screen_name=" + screen_name + ", gender="
				+ gender + ", friends_count=" + friends_count
				+ ", followers_count=" + followers_count + ", statuses_count="
				+ statuses_count + ", created_at=" + created_at
				+ ", favourites_count=" + favourites_count
				+ ", profile_image_url=" + profile_image_url + ", url=" + url
				+ ", blocking=" + blocking + ", following=" + following
				+ ", followed_by=" + followed_by + ", verified=" + verified
				+ ", geo_enabled=" + geo_enabled + "]";
	}

	
}
