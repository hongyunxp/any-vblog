package com.debugi.app.anyvblog.model.sohu;

import java.util.Map;

import com.debugi.app.anyvblog.model.Data;
import com.debugi.app.anyvblog.model.DataAdapter;

public class GetSendOneData4Sohu implements Data {
	private String created_at;
	private String id;
	private String text;
	private String source;
	private String favorited;
	private String truncated;
	private String in_reply_to_status_id;
	private String in_reply_to_user_id;
	private String in_reply_to_screen_name;
	private String in_reply_to_status_text;
	private String small_pic;
	private String middle_pic;
	private String original_pic;
	private UserInfo4Sohu user;
	
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

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getFavorited() {
		return favorited;
	}

	public void setFavorited(String favorited) {
		this.favorited = favorited;
	}

	public String getTruncated() {
		return truncated;
	}

	public void setTruncated(String truncated) {
		this.truncated = truncated;
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

	public String getSmall_pic() {
		return small_pic;
	}

	public void setSmall_pic(String small_pic) {
		this.small_pic = small_pic;
	}

	public String getMiddle_pic() {
		return middle_pic;
	}

	public void setMiddle_pic(String middle_pic) {
		this.middle_pic = middle_pic;
	}

	public String getOriginal_pic() {
		return original_pic;
	}

	public void setOriginal_pic(String original_pic) {
		this.original_pic = original_pic;
	}

	public UserInfo4Sohu getUser() {
		return user;
	}

	public void setUser(UserInfo4Sohu user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "GetSendOneData4Sohu [created_at=" + created_at + ", id=" + id
				+ ", text=" + text + ", source=" + source + ", favorited="
				+ favorited + ", truncated=" + truncated
				+ ", in_reply_to_status_id=" + in_reply_to_status_id
				+ ", in_reply_to_user_id=" + in_reply_to_user_id
				+ ", in_reply_to_screen_name=" + in_reply_to_screen_name
				+ ", in_reply_to_status_text=" + in_reply_to_status_text
				+ ", small_pic=" + small_pic + ", middle_pic=" + middle_pic
				+ ", original_pic=" + original_pic + ", user=" + user + "]";
	}

}
