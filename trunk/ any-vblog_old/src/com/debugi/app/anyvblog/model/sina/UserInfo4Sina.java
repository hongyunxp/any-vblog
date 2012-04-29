package com.debugi.app.anyvblog.model.sina;

import java.util.Map;

import com.debugi.app.anyvblog.model.User;
import com.debugi.app.anyvblog.model.UserAdapter;
import com.debugi.app.anyvblog.utils.Constants;


public class UserInfo4Sina implements User {
	private String id;	//	int64	用户UID
	private String screen_name;	//	string	用户昵称
	private String name;	//	string	友好显示名称
	private int province;	//	int	用户所在地区ID
	private int city;	//	int	用户所在城市ID
	private String location;	//	string	用户所在地
	private String description;	//	string	用户描述
	private String url;	//	string	用户博客地址
	private String profile_image_url;	//	string	用户头像地址
	private String domain;	//	string	用户的个性化域名
	private String gender;	//string	性别，m：男、f：女、n：未知
	private int followers_count;	//	int	粉丝数
	private int friends_count;	//	int	关注数
	private int statuses_count;	//	int	微博数
	private int favourites_count;	//	int	收藏数
	private String created_at;	//	string	创建时间
	private boolean following;	//	boolean	当前登录用户是否已关注该用户
	private boolean	verified;	//	boolean	是否是微博认证用户，即带V用户
	private Status4Sina status;	//	object	用户的最近一条微博信息字段
	private boolean geo_enabled;
	@Override
	public UserAdapter conver2User() {
		UserAdapter adapter = new UserAdapter();
		adapter.setSp(Constants.SP_SINA);
		adapter.setUser_id(id);
		adapter.setName(name);
		adapter.setNick(screen_name);
		//adapter.setHead(null);
		adapter.setHead_url(profile_image_url);
		adapter.setCount_followers(followers_count);
		adapter.setCount_friends(friends_count);
		adapter.setCount_status(statuses_count);
		if ("m".equals(gender)) {
			adapter.setSex("男");
		} else if("f".equals(gender)) {
			adapter.setSex("女");
		} else {
			adapter.setSex("未知");
		}
		adapter.setDescription(description);
		adapter.setLocation(location);
		adapter.setIsvip(verified);
		adapter.setFriend(following);
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
	

	@Override
	public String toString() {
		return "UserInfo4Sina [id=" + id + ", screen_name=" + screen_name
				+ ", name=" + name + ", province=" + province + ", city="
				+ city + ", location=" + location + ", description="
				+ description + ", url=" + url + ", profile_image_url="
				+ profile_image_url + ", domain=" + domain + ", gender="
				+ gender + ", followers_count=" + followers_count
				+ ", friends_count=" + friends_count + ", statuses_count="
				+ statuses_count + ", favourites_count=" + favourites_count
				+ ", created_at=" + created_at + ", following=" + following
				+ ", verified=" + verified + ", status=" + status
				+ ", geo_enabled=" + geo_enabled + "]";
	}

	public boolean isGeo_enabled() {
		return geo_enabled;
	}

	public void setGeo_enabled(boolean geo_enabled) {
		this.geo_enabled = geo_enabled;
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

	public int getProvince() {
		return province;
	}

	public void setProvince(int province) {
		this.province = province;
	}

	public int getCity() {
		return city;
	}

	public void setCity(int city) {
		this.city = city;
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

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getFollowers_count() {
		return followers_count;
	}

	public void setFollowers_count(int followers_count) {
		this.followers_count = followers_count;
	}

	public int getFriends_count() {
		return friends_count;
	}

	public void setFriends_count(int friends_count) {
		this.friends_count = friends_count;
	}

	public int getStatuses_count() {
		return statuses_count;
	}

	public void setStatuses_count(int statuses_count) {
		this.statuses_count = statuses_count;
	}

	public int getFavourites_count() {
		return favourites_count;
	}

	public void setFavourites_count(int favourites_count) {
		this.favourites_count = favourites_count;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
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

	public Status4Sina getStatus() {
		return status;
	}

	public void setStatus(Status4Sina status) {
		this.status = status;
	}
}
