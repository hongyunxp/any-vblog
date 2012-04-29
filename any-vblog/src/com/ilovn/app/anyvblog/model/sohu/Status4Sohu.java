package com.ilovn.app.anyvblog.model.sohu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ilovn.app.anyvblog.model.Data;
import com.ilovn.app.anyvblog.model.DataAdapter;
import com.ilovn.app.anyvblog.utils.Constants;
import com.ilovn.app.anyvblog.utils.TimeUtils;

public class Status4Sohu implements Data {
	private String created_at;
	private String id;
	private String text;
	private String source;
	private boolean favorited;
	private String truncated;
	private String in_reply_to_status_id;
	private String in_reply_to_user_id;
	private String in_reply_to_screen_name;
	private String in_reply_to_status_text;
	private String small_pic;
	private String middle_pic;
	private String original_pic;
	private UserInfo4Sohu user;
	private String coordinates;
	private boolean in_reply_to_has_image;
	private List<HashMap<String, String>> pics;
	private int picsCount;
	//私信参数
	private String sender_id;
	private String recipient_id;
	private String sender_screen_name;
	private String recipient_screen_name;
	private UserInfo4Sohu sender;
	private UserInfo4Sohu recipient;

	
	@Override
	public DataAdapter conver2Data() {
		DataAdapter adapter = new DataAdapter();
		adapter.setSp(Constants.SP_SOHU);
		if (user != null) {
			adapter.setIsvip(user.isVerified());
			adapter.setName(user.getName());
			adapter.setNick(user.getScreen_name());
			adapter.setGeo(user.isGeo_enabled());
			adapter.setComment_count(0);
			adapter.setForward_count(0);
			adapter.setFrom(source);
			adapter.setImage_url(small_pic);
			adapter.setImage_s(small_pic);
			adapter.setImage_m(middle_pic);
			adapter.setImage_o(original_pic);
			adapter.setIsfav(favorited);
			adapter.setHead_url(user.getProfile_image_url());
			adapter.setUser_id(user.getId());
			if (user.isVerified()) {
				adapter.setIsvip(true);
			} else {
				adapter.setIsvip(false);
			}
		}
		adapter.setImage_s(small_pic);
		adapter.setImage_m(middle_pic);
		adapter.setImage_o(original_pic);
		if (in_reply_to_status_id != null && !"".equals(in_reply_to_status_id)) {
			adapter.setSource(true);
			adapter.setSource_status_id(in_reply_to_status_id);
			adapter.setSource_comment_count(0);
			adapter.setSource_forward_count(0);
			adapter.setSource_name(in_reply_to_screen_name);
			adapter.setSource_nick(in_reply_to_screen_name);
			adapter.setSource_status(in_reply_to_status_text);
			adapter.setSource_time("");
			adapter.setSource_from("");
		}
		adapter.setStatus(text);
		adapter.setStatus_id(id);
		adapter.setTime(TimeUtils.coverTime(created_at));
		//翻页
		adapter.setOrigtime(id);
		if (sender != null) {
			adapter.setImage_url(sender.getProfile_image_url());
			adapter.setName(sender.getName());
			adapter.setNick(sender_screen_name);
			adapter.setIsvip(sender.isVerified());
			adapter.setTohead_url(recipient.getProfile_image_url());
			adapter.setToisvip(recipient.isVerified());
			adapter.setToname(recipient.getName());
			adapter.setTonick(recipient.getScreen_name());
		}
		return adapter;
	}

	@Override
	public Map<String, Object> conver() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getCreated_at() {
		return created_at;
	}

	public String getSender_id() {
		return sender_id;
	}

	public void setSender_id(String sender_id) {
		this.sender_id = sender_id;
	}

	public String getRecipient_id() {
		return recipient_id;
	}

	public void setRecipient_id(String recipient_id) {
		this.recipient_id = recipient_id;
	}

	public String getSender_screen_name() {
		return sender_screen_name;
	}

	public void setSender_screen_name(String sender_screen_name) {
		this.sender_screen_name = sender_screen_name;
	}

	public String getRecipient_screen_name() {
		return recipient_screen_name;
	}

	public void setRecipient_screen_name(String recipient_screen_name) {
		this.recipient_screen_name = recipient_screen_name;
	}

	public UserInfo4Sohu getSender() {
		return sender;
	}

	public void setSender(UserInfo4Sohu sender) {
		this.sender = sender;
	}

	public UserInfo4Sohu getRecipient() {
		return recipient;
	}

	public void setRecipient(UserInfo4Sohu recipient) {
		this.recipient = recipient;
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

	public boolean isFavorited() {
		return favorited;
	}

	public void setFavorited(boolean favorited) {
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

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	public boolean isIn_reply_to_has_image() {
		return in_reply_to_has_image;
	}

	public void setIn_reply_to_has_image(boolean in_reply_to_has_image) {
		this.in_reply_to_has_image = in_reply_to_has_image;
	}

	public List<HashMap<String, String>> getPics() {
		return pics;
	}

	public void setPics(List<HashMap<String, String>> pics) {
		this.pics = pics;
	}

	public int getPicsCount() {
		return picsCount;
	}

	public void setPicsCount(int picsCount) {
		this.picsCount = picsCount;
	}

	@Override
	public String toString() {
		return "Status4Sohu [created_at=" + created_at + ", id=" + id
				+ ", text=" + text + ", source=" + source + ", favorited="
				+ favorited + ", truncated=" + truncated
				+ ", in_reply_to_status_id=" + in_reply_to_status_id
				+ ", in_reply_to_user_id=" + in_reply_to_user_id
				+ ", in_reply_to_screen_name=" + in_reply_to_screen_name
				+ ", in_reply_to_status_text=" + in_reply_to_status_text
				+ ", small_pic=" + small_pic + ", middle_pic=" + middle_pic
				+ ", original_pic=" + original_pic + ", user=" + user
				+ ", coordinates=" + coordinates + ", in_reply_to_has_image="
				+ in_reply_to_has_image + ", pics=" + pics + ", picsCount="
				+ picsCount + "]";
	}

}
