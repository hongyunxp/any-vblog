package com.debugi.app.anyvblog.model.netease;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.Html;

import com.debugi.app.anyvblog.model.Data;
import com.debugi.app.anyvblog.model.DataAdapter;
import com.debugi.app.anyvblog.utils.Constants;
import com.debugi.app.anyvblog.utils.TimeUtils;

public class Status4Netease implements Data {
	private String id;
	private String source;
	private UserInfo4Netease user;
	private String flag;
	private String text;
	private String created_at;
	private String profile_image_url;
	private boolean truncated;
	private String retweet_count;
	private String in_reply_to_status_id;
	private String in_reply_to_user_id;
	private String in_reply_to_screen_name;
	private String in_reply_to_user_name;
	private String in_reply_to_status_text;
	private boolean favorited;
	private String favorited_at;
	private String root_in_reply_to_status_id;
	private String cursor_id;
	private String retweet_user_id;
	private String retweet_user_name;
	private String retweet_user_screen_name;
	private String retweet_created_at;
	private String root_in_reply_to_user_id;
	private String root_in_reply_to_screen_name;
	private String root_in_reply_to_user_name;
	private String root_in_reply_to_status_text;
	private String comments_count;
	private Geo4Netease geo;
	private boolean is_retweet_by_user;
	private Venue4Netease venue;
	private String in_reply_to_videoInfos;
	private String in_reply_to_musicInfos;
	private String root_in_reply_to_videoInfos;
	private String root_in_reply_to_musicInfos;
	private List<VideoInfo4Netease> videoInfos;
	private List<MusicInfo4Netease> musicInfos;
	//私信参数
	private UserInfo4Netease sender;
	private String recipient_id;
	private String sender_id;
	private String sender_screen_name;
	private String recipient_screen_name;
	private UserInfo4Netease recipient;
	private boolean followed_by;
	private Object audio_id;
	private Object original_filename;
	private String context_type;
	
	@Override
	public DataAdapter conver2Data() {
		DataAdapter adapter = new DataAdapter();
		adapter.setSp(Constants.SP_NETEASE);
		if (user != null) {
			adapter.setNick(user.getName());
			adapter.setName(user.getScreen_name());
			adapter.setIsvip(user.isVerified());
			adapter.setComment_count(Integer.parseInt(comments_count));
			adapter.setForward_count(Integer.parseInt(retweet_count));
			adapter.setFrom(Html.fromHtml(source).toString());
			adapter.setIsfav(favorited);
			adapter.setHead_url(user.getProfile_image_url());
			adapter.setUser_id(user.getId());
			if (user.isVerified()) {
				adapter.setIsvip(true);
			} else {
				adapter.setIsvip(false);
			}
		}
		//地理位置信息
		if (geo != null) {
			if ("Point".equals(geo.getType())) {
				adapter.setLat(Double.parseDouble(geo.getCoordinates().get(0)));
				adapter.setLng(Double.parseDouble(geo.getCoordinates().get(1)));
			}
		}
		if (root_in_reply_to_user_id != null && !"".equals(root_in_reply_to_user_id)) {
			adapter.setSource(true);
			adapter.setSource_comment_count(0);
			adapter.setSource_forward_count(0);
			adapter.setSource_name(root_in_reply_to_user_name);
			adapter.setSource_nick(root_in_reply_to_screen_name);
			adapter.setSource_status(root_in_reply_to_status_text);
			adapter.setSource_status_id(root_in_reply_to_status_id);
			adapter.setSource_time("");
			adapter.setSource_from("");
		}
		
		adapter.setStatus_id(id);
		adapter.setTime(TimeUtils.coverTime(created_at));
		//翻页
		adapter.setOrigtime(cursor_id);
		//通过正则表达式把图片URL取出
		Pattern pattern = Pattern.compile("http://\\S*");
		Matcher matcher = pattern.matcher(text);
		if (matcher.find()) {
			String t = matcher.group();
			adapter.setImage_url(t);
			adapter.setImage_s(t);
			adapter.setImage_m(t);
			adapter.setImage_o(t);
			text = matcher.replaceAll("");
		}
		adapter.setStatus(text);
		if (sender != null) {
			adapter.setImage_url(sender.getProfile_image_url());
			adapter.setNick(sender.getName());
			adapter.setName(sender_screen_name);
			adapter.setIsvip(sender.isVerified());
			adapter.setTohead_url(recipient.getProfile_image_url());
			adapter.setToisvip(recipient.isVerified());
			adapter.setToname(recipient.getScreen_name());
			adapter.setTonick(recipient.getName());
		}
		return adapter;
	}

	@Override
	public Map<String, Object> conver() {
		// TODO Auto-generated method stub
		return null;
	}

	public UserInfo4Netease getSender() {
		return sender;
	}

	public void setSender(UserInfo4Netease sender) {
		this.sender = sender;
	}

	public String getRecipient_id() {
		return recipient_id;
	}

	public void setRecipient_id(String recipient_id) {
		this.recipient_id = recipient_id;
	}

	public String getSender_id() {
		return sender_id;
	}

	public void setSender_id(String sender_id) {
		this.sender_id = sender_id;
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

	public UserInfo4Netease getRecipient() {
		return recipient;
	}

	public void setRecipient(UserInfo4Netease recipient) {
		this.recipient = recipient;
	}

	public boolean isFollowed_by() {
		return followed_by;
	}

	public void setFollowed_by(boolean followed_by) {
		this.followed_by = followed_by;
	}

	public Object getAudio_id() {
		return audio_id;
	}

	public void setAudio_id(Object audio_id) {
		this.audio_id = audio_id;
	}

	public Object getOriginal_filename() {
		return original_filename;
	}

	public void setOriginal_filename(Object original_filename) {
		this.original_filename = original_filename;
	}

	public String getContext_type() {
		return context_type;
	}

	public void setContext_type(String context_type) {
		this.context_type = context_type;
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

	public String getIn_reply_to_user_name() {
		return in_reply_to_user_name;
	}

	public void setIn_reply_to_user_name(String in_reply_to_user_name) {
		this.in_reply_to_user_name = in_reply_to_user_name;
	}

	public UserInfo4Netease getUser() {
		return user;
	}

	public void setUser(UserInfo4Netease user) {
		this.user = user;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getRetweet_count() {
		return retweet_count;
	}

	public void setRetweet_count(String retweet_count) {
		this.retweet_count = retweet_count;
	}

	public String getIn_reply_to_status_text() {
		return in_reply_to_status_text;
	}

	public void setIn_reply_to_status_text(String in_reply_to_status_text) {
		this.in_reply_to_status_text = in_reply_to_status_text;
	}

	public boolean isFavorited() {
		return favorited;
	}

	public void setFavorited(boolean favorited) {
		this.favorited = favorited;
	}

	public String getFavorited_at() {
		return favorited_at;
	}

	public void setFavorited_at(String favorited_at) {
		this.favorited_at = favorited_at;
	}

	public String getRoot_in_reply_to_status_id() {
		return root_in_reply_to_status_id;
	}

	public void setRoot_in_reply_to_status_id(String root_in_reply_to_status_id) {
		this.root_in_reply_to_status_id = root_in_reply_to_status_id;
	}

	public String getCursor_id() {
		return cursor_id;
	}

	public void setCursor_id(String cursor_id) {
		this.cursor_id = cursor_id;
	}

	public String getRetweet_user_id() {
		return retweet_user_id;
	}

	public void setRetweet_user_id(String retweet_user_id) {
		this.retweet_user_id = retweet_user_id;
	}

	public String getRetweet_user_name() {
		return retweet_user_name;
	}

	public void setRetweet_user_name(String retweet_user_name) {
		this.retweet_user_name = retweet_user_name;
	}

	public String getRetweet_user_screen_name() {
		return retweet_user_screen_name;
	}

	public void setRetweet_user_screen_name(String retweet_user_screen_name) {
		this.retweet_user_screen_name = retweet_user_screen_name;
	}

	public String getRetweet_created_at() {
		return retweet_created_at;
	}

	public void setRetweet_created_at(String retweet_created_at) {
		this.retweet_created_at = retweet_created_at;
	}

	public String getRoot_in_reply_to_user_id() {
		return root_in_reply_to_user_id;
	}

	public void setRoot_in_reply_to_user_id(String root_in_reply_to_user_id) {
		this.root_in_reply_to_user_id = root_in_reply_to_user_id;
	}

	public String getRoot_in_reply_to_screen_name() {
		return root_in_reply_to_screen_name;
	}

	public void setRoot_in_reply_to_screen_name(String root_in_reply_to_screen_name) {
		this.root_in_reply_to_screen_name = root_in_reply_to_screen_name;
	}

	public String getRoot_in_reply_to_user_name() {
		return root_in_reply_to_user_name;
	}

	public void setRoot_in_reply_to_user_name(String root_in_reply_to_user_name) {
		this.root_in_reply_to_user_name = root_in_reply_to_user_name;
	}

	public String getRoot_in_reply_to_status_text() {
		return root_in_reply_to_status_text;
	}

	public void setRoot_in_reply_to_status_text(String root_in_reply_to_status_text) {
		this.root_in_reply_to_status_text = root_in_reply_to_status_text;
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

	public boolean isIs_retweet_by_user() {
		return is_retweet_by_user;
	}

	public void setIs_retweet_by_user(boolean is_retweet_by_user) {
		this.is_retweet_by_user = is_retweet_by_user;
	}

	public Venue4Netease getVenue() {
		return venue;
	}

	public void setVenue(Venue4Netease venue) {
		this.venue = venue;
	}

	public String getIn_reply_to_videoInfos() {
		return in_reply_to_videoInfos;
	}

	public void setIn_reply_to_videoInfos(String in_reply_to_videoInfos) {
		this.in_reply_to_videoInfos = in_reply_to_videoInfos;
	}

	public String getIn_reply_to_musicInfos() {
		return in_reply_to_musicInfos;
	}

	public void setIn_reply_to_musicInfos(String in_reply_to_musicInfos) {
		this.in_reply_to_musicInfos = in_reply_to_musicInfos;
	}

	public String getRoot_in_reply_to_videoInfos() {
		return root_in_reply_to_videoInfos;
	}

	public void setRoot_in_reply_to_videoInfos(String root_in_reply_to_videoInfos) {
		this.root_in_reply_to_videoInfos = root_in_reply_to_videoInfos;
	}

	public String getRoot_in_reply_to_musicInfos() {
		return root_in_reply_to_musicInfos;
	}

	public void setRoot_in_reply_to_musicInfos(String root_in_reply_to_musicInfos) {
		this.root_in_reply_to_musicInfos = root_in_reply_to_musicInfos;
	}

	public List<VideoInfo4Netease> getVideoInfos() {
		return videoInfos;
	}

	public void setVideoInfos(List<VideoInfo4Netease> videoInfos) {
		this.videoInfos = videoInfos;
	}

	public List<MusicInfo4Netease> getMusicInfos() {
		return musicInfos;
	}

	public void setMusicInfos(List<MusicInfo4Netease> musicInfos) {
		this.musicInfos = musicInfos;
	}

	public String getProfile_image_url() {
		return profile_image_url;
	}

	public void setProfile_image_url(String profile_image_url) {
		this.profile_image_url = profile_image_url;
	}

	@Override
	public String toString() {
		return "Status4Netease [id=" + id + ", source=" + source + ", user="
				+ user + ", flag=" + flag + ", text=" + text + ", created_at="
				+ created_at + ", profile_image_url=" + profile_image_url
				+ ", truncated=" + truncated + ", retweet_count="
				+ retweet_count + ", in_reply_to_status_id="
				+ in_reply_to_status_id + ", in_reply_to_user_id="
				+ in_reply_to_user_id + ", in_reply_to_screen_name="
				+ in_reply_to_screen_name + ", in_reply_to_user_name="
				+ in_reply_to_user_name + ", in_reply_to_status_text="
				+ in_reply_to_status_text + ", favorited=" + favorited
				+ ", favorited_at=" + favorited_at
				+ ", root_in_reply_to_status_id=" + root_in_reply_to_status_id
				+ ", cursor_id=" + cursor_id + ", retweet_user_id="
				+ retweet_user_id + ", retweet_user_name=" + retweet_user_name
				+ ", retweet_user_screen_name=" + retweet_user_screen_name
				+ ", retweet_created_at=" + retweet_created_at
				+ ", root_in_reply_to_user_id=" + root_in_reply_to_user_id
				+ ", root_in_reply_to_screen_name="
				+ root_in_reply_to_screen_name
				+ ", root_in_reply_to_user_name=" + root_in_reply_to_user_name
				+ ", root_in_reply_to_status_text="
				+ root_in_reply_to_status_text + ", comments_count="
				+ comments_count + ", geo=" + geo + ", is_retweet_by_user="
				+ is_retweet_by_user + ", venue=" + venue
				+ ", in_reply_to_videoInfos=" + in_reply_to_videoInfos
				+ ", in_reply_to_musicInfos=" + in_reply_to_musicInfos
				+ ", root_in_reply_to_videoInfos="
				+ root_in_reply_to_videoInfos
				+ ", root_in_reply_to_musicInfos="
				+ root_in_reply_to_musicInfos + ", videoInfos=" + videoInfos
				+ ", musicInfos=" + musicInfos + ", sender=" + sender
				+ ", recipient_id=" + recipient_id + ", sender_id=" + sender_id
				+ ", sender_screen_name=" + sender_screen_name
				+ ", recipient_screen_name=" + recipient_screen_name
				+ ", recipient=" + recipient + ", followed_by=" + followed_by
				+ ", audio_id=" + audio_id + ", original_filename="
				+ original_filename + ", context_type=" + context_type + "]";
	}
}
