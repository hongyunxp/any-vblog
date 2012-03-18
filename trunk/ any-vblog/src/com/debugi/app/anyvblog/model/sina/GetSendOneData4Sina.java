package com.debugi.app.anyvblog.model.sina;

import java.util.Map;

import com.debugi.app.anyvblog.model.Data;
import com.debugi.app.anyvblog.model.DataAdapter;

public class GetSendOneData4Sina implements Data {
	private String created_at;
	private String text;
	private boolean truncated;
	private String in_reply_to_status_id;
	private Map<String, String> annotations;
	private String in_reply_to_screen_name;
	private Geo4Sina geo;
	private UserInfo4Sina user;
	private boolean favorited;
	private String in_reply_to_user_id;
	private String id;
	private String source;
	private String mid;
	
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

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public void setGeo(Geo4Sina geo) {
		this.geo = geo;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isTruncated() {
		return truncated;
	}

	public void setTruncated(boolean truncated) {
		this.truncated = truncated;
	}

	public String getIn_reply_to_status_id() {
		return in_reply_to_status_id;
	}

	public void setIn_reply_to_status_id(String in_reply_to_status_id) {
		this.in_reply_to_status_id = in_reply_to_status_id;
	}

	public Map<String, String> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(Map<String, String> annotations) {
		this.annotations = annotations;
	}

	public String getIn_reply_to_screen_name() {
		return in_reply_to_screen_name;
	}

	public void setIn_reply_to_screen_name(String in_reply_to_screen_name) {
		this.in_reply_to_screen_name = in_reply_to_screen_name;
	}

	public UserInfo4Sina getUser() {
		return user;
	}

	public void setUser(UserInfo4Sina user) {
		this.user = user;
	}

	public boolean isFavorited() {
		return favorited;
	}

	public void setFavorited(boolean favorited) {
		this.favorited = favorited;
	}

	public String getIn_reply_to_user_id() {
		return in_reply_to_user_id;
	}

	public void setIn_reply_to_user_id(String in_reply_to_user_id) {
		this.in_reply_to_user_id = in_reply_to_user_id;
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

	@Override
	public String toString() {
		return "GetSendOneData4Sina [created_at=" + created_at + ", text="
				+ text + ", truncated=" + truncated
				+ ", in_reply_to_status_id=" + in_reply_to_status_id
				+ ", annotations=" + annotations + ", in_reply_to_screen_name="
				+ in_reply_to_screen_name + ", geo=" + geo + ", user=" + user
				+ ", favorited=" + favorited + ", in_reply_to_user_id="
				+ in_reply_to_user_id + ", id=" + id + ", source=" + source
				+ ", mid=" + mid + "]";
	}

}
