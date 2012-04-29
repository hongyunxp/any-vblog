package com.ilovn.app.anyvblog.model.sina;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import android.text.Html;

import com.ilovn.app.anyvblog.model.Data;
import com.ilovn.app.anyvblog.model.DataAdapter;
import com.ilovn.app.anyvblog.utils.Constants;

public class Status4Sina implements Data {
	private String created_at;	// 创建时间
	private String id;	// 微博ID
	private String text;	// 微博信息内容
	private String source;	// 微博来源
	private boolean favorited;	// 是否已收藏
	private boolean truncated;	// 是否被截断
	private String in_reply_to_status_id;	// 回复ID
	private String in_reply_to_user_id;	// 回复人UID
	private String in_reply_to_screen_name;	// 回复人昵称
	private String thumbnail_pic;	// 缩略图
	private String bmiddle_pic;	// 中型图片
	private String original_pic;	//原始图片
	private UserInfo4Sina user;	// 作者信息
	private Status4Sina retweeted_status;	// 转发的博文，内容为status，如果不是转发，则没有此字段
	private Geo4Sina geo;
	@Override
	public DataAdapter conver2Data() {
		DataAdapter dataAdapter = new DataAdapter();
		dataAdapter.setStatus(text);
		dataAdapter.setSp(Constants.SP_SINA);
		dataAdapter.setTime(coverTime(created_at));
		//翻页使用
		dataAdapter.setOrigtime(id);
		dataAdapter.setStatus_id(id);
		dataAdapter.setFrom(Html.fromHtml(source).toString());
		if (user != null) {
			dataAdapter.setName(user.getName());
			dataAdapter.setNick(user.getScreen_name());
			dataAdapter.setIsvip(user.isVerified());
			dataAdapter.setHead_url(user.getProfile_image_url());
			dataAdapter.setUser_id(user.getId());
		}
		dataAdapter.setImage_url(thumbnail_pic);
		dataAdapter.setImage_s(thumbnail_pic);
		dataAdapter.setImage_m(bmiddle_pic);
		dataAdapter.setImage_o(original_pic);
		dataAdapter.setIsfav(favorited);
		//地理位置信息
		if (geo != null) {
			if ("Point".equals(geo.getType())) {
				dataAdapter.setLat(Double.parseDouble(geo.getCoordinates().get(0)));
				dataAdapter.setLng(Double.parseDouble(geo.getCoordinates().get(1)));
			}
		}
		if (retweeted_status != null && !"".equals(retweeted_status)) {
			dataAdapter.setSource(true);
			dataAdapter.setSource_status(retweeted_status.getText());
			dataAdapter.setSource_time(coverTime(retweeted_status.getCreated_at()));
			dataAdapter.setSource_name(retweeted_status.getUser().getName());
			dataAdapter.setSource_nick(retweeted_status.getUser().getScreen_name());
			dataAdapter.setSource_from(Html.fromHtml(retweeted_status.getSource()).toString());
			dataAdapter.setSource_image_url(retweeted_status.getThumbnail_pic());
			dataAdapter.setSource_image_s(retweeted_status.getThumbnail_pic());
			dataAdapter.setSource_image_m(retweeted_status.getBmiddle_pic());
			dataAdapter.setSource_image_o(retweeted_status.getOriginal_pic());
			dataAdapter.setSource_isvip(retweeted_status.getUser().isVerified());dataAdapter.setSource_user_id(retweeted_status.getUser().getId());
		}
		return dataAdapter;
	}
	@Override
	public Map<String, Object> conver() {
		// TODO Auto-generated method stub
		return null;
	}
	private String coverTime(String timeString) {
		SimpleDateFormat format = new SimpleDateFormat("MM/dd HH:mm");
		return format.format(Date.parse(timeString));
	}

	@Override
	public String toString() {
		return "Status4Sina [created_at=" + created_at + ", id=" + id
				+ ", text=" + text + ", source=" + source + ", favorited="
				+ favorited + ", truncated=" + truncated
				+ ", in_reply_to_status_id=" + in_reply_to_status_id
				+ ", in_reply_to_user_id=" + in_reply_to_user_id
				+ ", in_reply_to_screen_name=" + in_reply_to_screen_name
				+ ", thumbnail_pic=" + thumbnail_pic + ", bmiddle_pic="
				+ bmiddle_pic + ", original_pic=" + original_pic + ", user="
				+ user + ", retweeted_status=" + retweeted_status + ", geo="
				+ geo + "]";
	}
	public boolean isFavorited() {
		return favorited;
	}
	public void setFavorited(boolean favorited) {
		this.favorited = favorited;
	}
	public Geo4Sina getGeo() {
		return geo;
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

	public String getThumbnail_pic() {
		return thumbnail_pic;
	}

	public void setThumbnail_pic(String thumbnail_pic) {
		this.thumbnail_pic = thumbnail_pic;
	}

	public String getBmiddle_pic() {
		return bmiddle_pic;
	}

	public void setBmiddle_pic(String bmiddle_pic) {
		this.bmiddle_pic = bmiddle_pic;
	}

	public String getOriginal_pic() {
		return original_pic;
	}

	public void setOriginal_pic(String original_pic) {
		this.original_pic = original_pic;
	}

	public UserInfo4Sina getUser() {
		return user;
	}

	public void setUser(UserInfo4Sina user) {
		this.user = user;
	}

	public Status4Sina getRetweeted_status() {
		return retweeted_status;
	}

	public void setRetweeted_status(Status4Sina retweeted_status) {
		this.retweeted_status = retweeted_status;
	}

}
