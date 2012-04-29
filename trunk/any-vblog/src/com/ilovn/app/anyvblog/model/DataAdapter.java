package com.ilovn.app.anyvblog.model;

import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;

/**
 * 该类用于适配主页时间线数据，各服务商主页时间线数据内部转换到该适配器
 * 
 * @author Administrator
 * 
 */
public class DataAdapter {
	@SuppressWarnings("unused")
	private static final String TAG = "AnyvBlog_DataAdapter";
	private String status_id;
	private String nick;
	private String name;
	private String head_url;
	private Bitmap head;
	private boolean isvip;
	private String time;
	private String origtime;
	private String status;
	private Bitmap image;
	private String image_url;
	private String image_s;
	private String image_m;
	private String image_o;
	private String from;	//sfrom
	private int type; // 类型,确定是对话、评论、转播……1-原创发表，2-转载，3-私信，4-回复，5-空回，6-提及，7-评论
	private int forward_count;
	private int comment_count;
	private boolean isfav;
	private boolean geo;
	private double lng; // long
	private double lat;
	private boolean self;
	private String location;
	private boolean source;
	private String source_nick;
	private String source_name;
	private boolean source_isvip;
	private String source_time;
	private String source_status;
	private Bitmap source_image;
	private String source_image_url;
	private String source_image_s;
	private String source_image_m;
	private String source_image_o;
	private String source_from;
	private String source_location;
	private int source_forward_count;
	private int source_comment_count;
	private String source_status_id;
	private String source_user_id;
	private int sp;
	private String user_id;
	//以下参数在私信中有效
	private Bitmap tohead;
	private String tohead_url;
	private boolean toisvip;
	private String toname;
	private String tonick;
	
	
	public Map<String, Object> conver() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", status_id);
		map.put("name", name);
		map.put("nick", nick);
		map.put("time", time);
		map.put("origtime", origtime);
		map.put("head", head);
		map.put("head_url", head_url);
		map.put("status", status);
		map.put("from", from);
		map.put("image", image);
		map.put("image_url", image_url);
		map.put("image_s", image_s);
		map.put("image_m", image_m);
		map.put("image_o", image_o);
		map.put("geo", geo);
		map.put("long", lng);
		map.put("lat", lat);
		map.put("lacation", location);
		map.put("isvip", isvip);
		map.put("forward_count", forward_count);
		map.put("comment_count", comment_count);
		map.put("type", type);
		map.put("sp", sp);
		map.put("user_id", user_id);
		map.put("toname", toname);
		map.put("tonick", tonick);
		map.put("tohead_url", tohead_url);
		map.put("tohead", tohead);
		map.put("toisvip", toisvip);
		map.put("isfav", isfav);

		// System.out.println("source==>" + source);
		if (source) {
			//Config.debug(TAG, "source is not null at model");
			map.put("source", "Any微博");
			map.put("source_name", source_name);
			map.put("source_nick", source_nick);
			map.put("source_time", source_time);
			map.put("source_status", source_status);
			map.put("source_from", source_from);
			map.put("source_isvip", source_isvip);
			map.put("source_forward_count", source_forward_count);
			map.put("source_comment_count", source_comment_count);
			map.put("source_status_id", source_status_id);
			map.put("source_location", source_location);
			map.put("source_image_url", source_image_url);
			map.put("source_image_s", source_image_s);
			map.put("source_image_m", source_image_m);
			map.put("source_image_o", source_image_o);
			map.put("source_image", source_image);
			map.put("source_user_id", source_user_id);
		} else {
			map.put("source", null);
		}
		return map;
	}

	public int getComment_count() {
		return comment_count;
	}

	public int getForward_count() {
		return forward_count;
	}

	public String getFrom() {
		return from;
	}

	public Bitmap getImage() {
		return image;
	}

	public String getImage_url() {
		return image_url;
	}

	public double getLat() {
		return lat;
	}

	public double getLng() {
		return lng;
	}

	public String getLocation() {
		return location;
	}

	public String getName() {
		return name;
	}

	public String getNick() {
		return nick;
	}

	public int getSource_comment_count() {
		return source_comment_count;
	}

	public int getSource_forward_count() {
		return source_forward_count;
	}

	public String getSource_from() {
		return source_from;
	}

	public Bitmap getSource_image() {
		return source_image;
	}

	public String getSource_image_url() {
		return source_image_url;
	}

	public String getSource_name() {
		return source_name;
	}

	public String getSource_nick() {
		return source_nick;
	}

	public String getSource_status() {
		return source_status;
	}

	public String getSource_status_id() {
		return source_status_id;
	}

	public String getSource_time() {
		return source_time;
	}

	public String getStatus() {
		return status;
	}

	public String getStatus_id() {
		return status_id;
	}

	public String getTime() {
		return time;
	}

	public boolean isGeo() {
		return geo;
	}

	public boolean isIsvip() {
		return isvip;
	}

	public boolean isSource() {
		return source;
	}

	public boolean isSource_isvip() {
		return source_isvip;
	}

	public void setComment_count(int comment_count) {
		this.comment_count = comment_count;
	}

	public void setForward_count(int forward_count) {
		this.forward_count = forward_count;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setGeo(boolean geo) {
		this.geo = geo;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public void setIsvip(boolean isvip) {
		this.isvip = isvip;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public void setSource(boolean source) {
		this.source = source;
	}

	public void setSource_comment_count(int source_comment_count) {
		this.source_comment_count = source_comment_count;
	}

	public void setSource_forward_count(int source_forward_count) {
		this.source_forward_count = source_forward_count;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

	public void setSource_image(Bitmap source_image) {
		this.source_image = source_image;
	}

	public void setSource_image_url(String source_image_url) {
		this.source_image_url = source_image_url;
	}

	public void setSource_isvip(boolean source_isvip) {
		this.source_isvip = source_isvip;
	}

	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}

	public void setSource_nick(String source_nick) {
		this.source_nick = source_nick;
	}

	public void setSource_status(String source_status) {
		this.source_status = source_status;
	}

	public void setSource_status_id(String source_status_id) {
		this.source_status_id = source_status_id;
	}

	public void setSource_time(String source_time) {
		this.source_time = source_time;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setStatus_id(String status_id) {
		this.status_id = status_id;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public boolean isSelf() {
		return self;
	}

	public void setSelf(boolean self) {
		this.self = self;
	}

	public Bitmap getTohead() {
		return tohead;
	}

	public void setTohead(Bitmap tohead) {
		this.tohead = tohead;
	}

	public String getTohead_url() {
		return tohead_url;
	}

	public void setTohead_url(String tohead_url) {
		this.tohead_url = tohead_url;
	}

	public boolean isToisvip() {
		return toisvip;
	}

	public void setToisvip(boolean toisvip) {
		this.toisvip = toisvip;
	}

	public String getToname() {
		return toname;
	}

	public void setToname(String toname) {
		this.toname = toname;
	}

	public String getTonick() {
		return tonick;
	}

	public void setTonick(String tonick) {
		this.tonick = tonick;
	}
	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setSource_location(String source_location) {
		this.source_location = source_location;
	}

	public String getSource_location() {
		return source_location;
	}

	public int getSp() {
		return sp;
	}

	public void setSp(int sp) {
		this.sp = sp;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public void setIsfav(boolean isfav) {
		this.isfav = isfav;
	}

	public boolean isIsfav() {
		return isfav;
	}

	public void setHead_url(String head_url) {
		this.head_url = head_url;
	}

	public String getHead_url() {
		return head_url;
	}

	public void setHead(Bitmap head) {
		this.head = head;
	}

	public Bitmap getHead() {
		return head;
	}

	public void setSource_user_id(String source_user_id) {
		this.source_user_id = source_user_id;
	}

	public String getSource_user_id() {
		return source_user_id;
	}

	public void setImage_o(String image_o) {
		this.image_o = image_o;
	}

	public String getImage_o() {
		return image_o;
	}

	public void setImage_m(String image_m) {
		this.image_m = image_m;
	}

	public String getImage_m() {
		return image_m;
	}

	public void setImage_s(String image_s) {
		this.image_s = image_s;
	}

	public String getImage_s() {
		return image_s;
	}

	public String getSource_image_s() {
		return source_image_s;
	}

	public void setSource_image_s(String source_image_s) {
		this.source_image_s = source_image_s;
	}

	public String getSource_image_m() {
		return source_image_m;
	}

	public void setSource_image_m(String source_image_m) {
		this.source_image_m = source_image_m;
	}

	public String getSource_image_o() {
		return source_image_o;
	}

	public void setSource_image_o(String source_image_o) {
		this.source_image_o = source_image_o;
	}

	public void setOrigtime(String origtime) {
		this.origtime = origtime;
	}

	public String getOrigtime() {
		return origtime;
	}

	@Override
	public String toString() {
		return "DataAdapter [status_id=" + status_id + ", nick=" + nick
				+ ", name=" + name + ", head_url=" + head_url + ", head="
				+ head + ", isvip=" + isvip + ", time=" + time + ", origtime="
				+ origtime + ", status=" + status + ", image=" + image
				+ ", image_url=" + image_url + ", image_s=" + image_s
				+ ", image_m=" + image_m + ", image_o=" + image_o + ", from="
				+ from + ", type=" + type + ", forward_count=" + forward_count
				+ ", comment_count=" + comment_count + ", isfav=" + isfav
				+ ", geo=" + geo + ", lng=" + lng + ", lat=" + lat + ", self="
				+ self + ", location=" + location + ", source=" + source
				+ ", source_nick=" + source_nick + ", source_name="
				+ source_name + ", source_isvip=" + source_isvip
				+ ", source_time=" + source_time + ", source_status="
				+ source_status + ", source_image=" + source_image
				+ ", source_image_url=" + source_image_url
				+ ", source_image_s=" + source_image_s + ", source_image_m="
				+ source_image_m + ", source_image_o=" + source_image_o
				+ ", source_from=" + source_from + ", source_location="
				+ source_location + ", source_forward_count="
				+ source_forward_count + ", source_comment_count="
				+ source_comment_count + ", source_status_id="
				+ source_status_id + ", source_user_id=" + source_user_id
				+ ", sp=" + sp + ", user_id=" + user_id + ", tohead=" + tohead
				+ ", tohead_url=" + tohead_url + ", toisvip=" + toisvip
				+ ", toname=" + toname + ", tonick=" + tonick + "]";
	}

}
