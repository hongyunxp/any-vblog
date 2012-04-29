package com.ilovn.app.anyvblog.model.netease;

import java.util.Map;

import com.ilovn.app.anyvblog.model.Data;
import com.ilovn.app.anyvblog.model.DataAdapter;

public class GetSendOneData4Netease implements Data {
	private String id;
	private String source;
	private UserInfo4Netease user;
	private String text;
	private String created_at;
	private String in_reply_to_status_id;
	private String in_reply_to_user_id;
	private String in_reply_to_screen_name;
	private String in_reply_to_status_text;
	private String retweet_count;
	private String comments_count;
	private Geo4Netease geo;
	private Venue4Netease venue;
	private String favorited_at;
	private String in_reply_to_user_name;
	private boolean truncated;
	private boolean favorited;
	private String root_in_reply_to_status_id;
	private boolean is_retweet_by_user;
	private String retweet_user_id;
	

	@Override
	public DataAdapter conver2Data() {
		// TODO Auto-generated method stub
		return null;
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public UserInfo4Netease getUser() {
		return user;
	}

	public void setUser(UserInfo4Netease user) {
		this.user = user;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getIn_reply_to_status_id() {
		return in_reply_to_status_id;
	}

	public void setIn_reply_to_status_id(String in_reply_to_status_id) {
		this.in_reply_to_status_id = in_reply_to_status_id;
	}

	public String getIn_reply_to_user_id() {
		return in_reply_to_user_id;
	}

	public void setIn_reply_to_user_id(String in_reply_to_user_id) {
		this.in_reply_to_user_id = in_reply_to_user_id;
	}

	public String getIn_reply_to_screen_name() {
		return in_reply_to_screen_name;
	}

	public void setIn_reply_to_screen_name(String in_reply_to_screen_name) {
		this.in_reply_to_screen_name = in_reply_to_screen_name;
	}

	public String getIn_reply_to_status_text() {
		return in_reply_to_status_text;
	}

	public void setIn_reply_to_status_text(String in_reply_to_status_text) {
		this.in_reply_to_status_text = in_reply_to_status_text;
	}

	public String getRetweet_count() {
		return retweet_count;
	}

	public void setRetweet_count(String retweet_count) {
		this.retweet_count = retweet_count;
	}

	public String getComments_count() {
		return comments_count;
	}

	public void setComments_count(String comments_count) {
		this.comments_count = comments_count;
	}

	public Geo4Netease getGeo() {
		return geo;
	}

	public void setGeo(Geo4Netease geo) {
		this.geo = geo;
	}

	public Venue4Netease getVenue() {
		return venue;
	}

	public void setVenue(Venue4Netease venue) {
		this.venue = venue;
	}

	public String getFavorited_at() {
		return favorited_at;
	}

	public void setFavorited_at(String favorited_at) {
		this.favorited_at = favorited_at;
	}

	public String getIn_reply_to_user_name() {
		return in_reply_to_user_name;
	}

	public void setIn_reply_to_user_name(String in_reply_to_user_name) {
		this.in_reply_to_user_name = in_reply_to_user_name;
	}

	public boolean isTruncated() {
		return truncated;
	}

	public void setTruncated(boolean truncated) {
		this.truncated = truncated;
	}

	public boolean isFavorited() {
		return favorited;
	}

	public void setFavorited(boolean favorited) {
		this.favorited = favorited;
	}

	public String getRoot_in_reply_to_status_id() {
		return root_in_reply_to_status_id;
	}

	public void setRoot_in_reply_to_status_id(String root_in_reply_to_status_id) {
		this.root_in_reply_to_status_id = root_in_reply_to_status_id;
	}

	public boolean isIs_retweet_by_user() {
		return is_retweet_by_user;
	}

	public void setIs_retweet_by_user(boolean is_retweet_by_user) {
		this.is_retweet_by_user = is_retweet_by_user;
	}

	public String getRetweet_user_id() {
		return retweet_user_id;
	}

	public void setRetweet_user_id(String retweet_user_id) {
		this.retweet_user_id = retweet_user_id;
	}

	@Override
	public String toString() {
		return "GetSendOneData4Netease [id=" + id + ", source=" + source
				+ ", user=" + user + ", text=" + text + ", created_at="
				+ created_at + ", in_reply_to_status_id="
				+ in_reply_to_status_id + ", in_reply_to_user_id="
				+ in_reply_to_user_id + ", in_reply_to_screen_name="
				+ in_reply_to_screen_name + ", in_reply_to_status_text="
				+ in_reply_to_status_text + ", retweet_count=" + retweet_count
				+ ", comments_count=" + comments_count + ", geo=" + geo
				+ ", venue=" + venue + ", favorited_at=" + favorited_at
				+ ", in_reply_to_user_name=" + in_reply_to_user_name
				+ ", truncated=" + truncated + ", favorited=" + favorited
				+ ", root_in_reply_to_status_id=" + root_in_reply_to_status_id
				+ ", is_retweet_by_user=" + is_retweet_by_user
				+ ", retweet_user_id=" + retweet_user_id + "]";
	}

}
