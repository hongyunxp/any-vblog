package com.debugi.app.anyvblog.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;

import com.debugi.app.anyvblog.db.DataHelper;
import com.debugi.app.anyvblog.utils.ImageUtils;
import com.debugi.open.oauth.model.Token;
/**
 * 该类适配数据库accounts表
 * @author Administrator
 *
 */
public class UserAdapter implements Serializable {
	private static final long serialVersionUID = 1L;
	private int sp;
	private String user_id;
	private String nick;
	private String name;
	private String token;
	private String token_secret;
	private String head_url;
	private Bitmap head;
	private String last_update;
	private boolean isvip;
	private String sex;
	private boolean friend;
	private boolean follower;
	private boolean black;
	private boolean self;
	private int count_friends;
	private int count_followers;
	private int count_status;
	private String description;
	private String domain;
	private String location;
	private DataAdapter status;
	public Token getAccessToken() {
		if (token != null && !"".equals(token) && token_secret != null && !"".equals(token_secret)) {
			return new Token(token, token_secret);
		} else {
			return null;
		}
	}
	public Map<String, Object> conver() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sp", sp);
		map.put("user_id", user_id);
		map.put("nick", nick);
		map.put("name", name);
		map.put("token", token);
		map.put("token_secret", token_secret);
		map.put("head_url", head_url);
		map.put("head", head);
		map.put("last_update", last_update);
		map.put("isvip", isvip);
		map.put("sex", sex);
		return map;
	}
	public ContentValues conver2ContentValues() {
		ContentValues values = new ContentValues();
		values.put("sp", sp);
		values.put("user_id", user_id);
		values.put("nick", nick);
		values.put("name", name);
		values.put("token", token);
		values.put("token_secret", token_secret);
		values.put("head", ImageUtils.conver2Byte(head));
		values.put("head_url", head_url);
		values.put("last_update", last_update);
		return values;
	}
	public static UserAdapter getDefaultUser(Context context) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		UserAdapter adapter;
		String user_id = sharedPreferences.getString("user_id", "");
		if (user_id == null || "".equals(user_id)) {
			return null;
		}
		DataHelper dataHelper = new DataHelper(context);
		adapter = dataHelper.loadOneUser(user_id, sharedPreferences.getInt("sp", 1));
		dataHelper.close();
		return adapter;
	}
	public boolean isIsvip() {
		return isvip;
	}
	public void setIsvip(boolean isvip) {
		this.isvip = isvip;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
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
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getToken_secret() {
		return token_secret;
	}
	public void setToken_secret(String token_secret) {
		this.token_secret = token_secret;
	}
	public Bitmap getHead() {
		return head;
	}
	public void setHead(Bitmap head) {
		this.head = head;
	}
	public String getLast_update() {
		return last_update;
	}
	public void setLast_update(String last_update) {
		this.last_update = last_update;
	}
	public void setHead_url(String head_url) {
		this.head_url = head_url;
	}
	public String getHead_url() {
		return head_url;
	}
	public boolean isFriend() {
		return friend;
	}
	public void setFriend(boolean friend) {
		this.friend = friend;
	}
	public boolean isFollower() {
		return follower;
	}
	public void setFollower(boolean follower) {
		this.follower = follower;
	}
	public boolean isBlack() {
		return black;
	}
	public void setBlack(boolean black) {
		this.black = black;
	}
	public int getCount_friends() {
		return count_friends;
	}
	public void setCount_friends(int count_friends) {
		this.count_friends = count_friends;
	}
	public int getCount_followers() {
		return count_followers;
	}
	public void setCount_followers(int count_followers) {
		this.count_followers = count_followers;
	}
	public int getCount_status() {
		return count_status;
	}
	public void setCount_status(int count_status) {
		this.count_status = count_status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLocation() {
		return location;
	}
	@Override
	public String toString() {
		return "UserAdapter [sp=" + sp + ", user_id=" + user_id + ", nick="
				+ nick + ", name=" + name + ", token=" + token
				+ ", token_secret=" + token_secret + ", head_url=" + head_url
				+ ", head=" + head + ", last_update=" + last_update
				+ ", isvip=" + isvip + ", sex=" + sex + ", friend=" + friend
				+ ", follower=" + follower + ", black=" + black
				+ ", count_friends=" + count_friends + ", count_followers="
				+ count_followers + ", count_status=" + count_status
				+ ", description=" + description + ", domain=" + domain
				+ ", location=" + location + "]";
	}
	public void setSelf(boolean self) {
		this.self = self;
	}
	public boolean isSelf() {
		return self;
	}
	public void setStatus(DataAdapter status) {
		this.status = status;
	}
	public DataAdapter getStatus() {
		return status;
	}
}
