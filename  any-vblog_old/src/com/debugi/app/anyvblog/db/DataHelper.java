package com.debugi.app.anyvblog.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import com.debugi.app.anyvblog.model.DataAdapter;
import com.debugi.app.anyvblog.model.UserAdapter;
import com.debugi.app.anyvblog.utils.Config;
import com.debugi.app.anyvblog.utils.ImageUtils;
import com.debugi.open.oauth.model.Token;

public class DataHelper {
	private static final String TAG = "DataHelper";
	private static String DB_NAME = "anyvblog.db";
	private static String TABLE_ACCOUNTS = "accounts";
	private static String TABLE_HOMELIST = "homelist";
	private static int VERSION = 1;
	private SQLiteDatabase db;
	private DBHelper dbHelper;
	public DataHelper(Context context) {
		dbHelper = new DBHelper(context, DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}
	/**
	 * close
	 */
	public void close() {
		db.close();
		dbHelper.close();
	}
	/**
	 * 载入一个用户
	 * @param user_id
	 * @param sp
	 * @return
	 */
	public UserAdapter loadOneUser(String user_id, int sp) {
		UserAdapter data = new UserAdapter();
		Cursor cursor = db.query(TABLE_ACCOUNTS, null, "user_id='" + user_id + "' and sp=" + sp, null, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()) {
			data.setUser_id(cursor.getString(cursor.getColumnIndex("user_id")));
			data.setSp(cursor.getInt(cursor.getColumnIndex("sp")));
			data.setNick(cursor.getString(cursor.getColumnIndex("nick")));
			data.setName(cursor.getString(cursor.getColumnIndex("name")));
			data.setToken(cursor.getString(cursor.getColumnIndex("token")));
			data.setToken_secret(cursor.getString(cursor.getColumnIndex("token_secret")));
			data.setHead(ImageUtils.conver2Bitmap(cursor.getBlob(cursor.getColumnIndex("head"))));
			data.setHead_url(cursor.getString(cursor.getColumnIndex("head_url")));
			data.setLast_update(cursor.getString(cursor.getColumnIndex("last_update")));
			cursor.moveToNext();
		}
		cursor.close();
		Config.debug(TAG, "load user " + data.getNick());
		return data;
	}
	/**
	 * 载入一个用户的信息
	 * @param adapter
	 * @return
	 */
	public UserAdapter loadOneUser(UserAdapter adapter) {
		UserAdapter data = new UserAdapter();
		Cursor cursor = db.query(TABLE_ACCOUNTS, null, "user_id='" + adapter.getUser_id() + "' and sp=" + adapter.getSp(), null, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()) {
			data.setUser_id(cursor.getString(cursor.getColumnIndex("user_id")));
			data.setSp(cursor.getInt(cursor.getColumnIndex("sp")));
			data.setNick(cursor.getString(cursor.getColumnIndex("nick")));
			data.setName(cursor.getString(cursor.getColumnIndex("name")));
			data.setToken(cursor.getString(cursor.getColumnIndex("token")));
			data.setToken_secret(cursor.getString(cursor.getColumnIndex("token_secret")));
			data.setHead(ImageUtils.conver2Bitmap(cursor.getBlob(cursor.getColumnIndex("head"))));
			data.setHead_url(cursor.getString(cursor.getColumnIndex("head_url")));
			data.setLast_update(cursor.getString(cursor.getColumnIndex("last_update")));
			cursor.moveToNext();
		}
		cursor.close();
		Config.debug(TAG, "load user " + data.getNick());
		return data;
	}
	/**
	 * 从数据库中载入所有的账户信息
	 * @return
	 */
	public List<UserAdapter> loadUsers() {
		List<UserAdapter> datas = new ArrayList<UserAdapter>();
		Cursor cursor = db.query(TABLE_ACCOUNTS, null, null, null, null, null, null);
		cursor.moveToFirst();
		UserAdapter adapter;
		while(!cursor.isAfterLast()) {
			adapter = new UserAdapter();
			adapter.setUser_id(cursor.getString(cursor.getColumnIndex("user_id")));
			adapter.setSp(cursor.getInt(cursor.getColumnIndex("sp")));
			adapter.setNick(cursor.getString(cursor.getColumnIndex("nick")));
			adapter.setName(cursor.getString(cursor.getColumnIndex("name")));
			adapter.setToken(cursor.getString(cursor.getColumnIndex("token")));
			adapter.setToken_secret(cursor.getString(cursor.getColumnIndex("token_secret")));
			adapter.setHead(ImageUtils.conver2Bitmap(cursor.getBlob(cursor.getColumnIndex("head"))));
			adapter.setHead_url(cursor.getString(cursor.getColumnIndex("head_url")));
			adapter.setLast_update(cursor.getString(cursor.getColumnIndex("last_update")));
			datas.add(adapter);
			adapter = null;
			cursor.moveToNext();
		}
		cursor.close();
		Config.debug(TAG, "load " + datas.size() + " users ");
		return datas;
	}
	
	/**
	 * 根据用户id和服务商sp获取用户Token对象，用户id和服务商sp需要构造为adapter对象
	 * @param adapter
	 * @return accessToken 
	 */
	public Token getToken(UserAdapter adapter) {
		Cursor cursor = db.query(TABLE_ACCOUNTS, null, "user_id='" + adapter.getUser_id() + "' and sp=" + adapter.getSp(), null, null, null, null);
		String token = "";
		String token_secret = "";
		if (cursor.moveToFirst()) {
			token = cursor.getString(cursor.getColumnIndex("token"));
			token_secret = cursor.getString(cursor.getColumnIndex("token_secret"));
		} else {
			Config.debug(TAG, "can't find the token & token_secret");
		}
		cursor.close();
		return new Token(token, token_secret);
	}
	/**
	 * 判断是否有已授权账户
	 * @return
	 */
	public boolean haveAccount() {
		boolean flag = false;
		Cursor cursor = db.query(TABLE_ACCOUNTS, null, null, null, null, null, null);
		flag = cursor.moveToFirst();
		cursor.close();
		return flag;
	}
	/**
	 * 判断用户是否存在
	 * @param adapter
	 * @return
	 */
	public boolean isUserExists(UserAdapter adapter) {
		boolean flag = false;
		Cursor cursor = db.query(TABLE_ACCOUNTS, null, "user_id='" + adapter.getUser_id() + "' and sp=" + adapter.getSp(), null, null, null, null);
		flag = cursor.moveToFirst();
		cursor.close();
		return flag;
	}
	/**
	 * 载入一条微博数据
	 * @param id
	 * @return
	 */
	public DataAdapter loadOneDataById(String id) {
		Cursor cursor = db.query(TABLE_HOMELIST, null, "status_id='" + id + "'", null, null, null, null);
		cursor.moveToFirst();
		DataAdapter data = new DataAdapter();
		while(!cursor.isAfterLast()) {
			data.setStatus_id(cursor.getString(cursor.getColumnIndex("status_id")));
			data.setNick(cursor.getString(cursor.getColumnIndex("nick")));
			data.setName(cursor.getString(cursor.getColumnIndex("name")));
			data.setHead(ImageUtils.conver2Bitmap(cursor.getBlob(cursor.getColumnIndex("head"))));
			data.setHead_url(cursor.getString(cursor.getColumnIndex("head_url")));
			if (cursor.getInt(cursor.getColumnIndex("isvip")) == 1) {
				data.setIsvip(true);
			} else {
				data.setIsvip(false);
			}
			if (cursor.getInt(cursor.getColumnIndex("isfav")) == 1) {
				data.setIsfav(true);
			} else {
				data.setIsfav(false);
			}
			data.setLat(cursor.getDouble(cursor.getColumnIndex("lat")));
			data.setLng(cursor.getDouble(cursor.getColumnIndex("long")));
			data.setTime(cursor.getString(cursor.getColumnIndex("time")));
			data.setOrigtime(cursor.getString(cursor.getColumnIndex("origtime")));
			data.setStatus(cursor.getString(cursor.getColumnIndex("status")));
			data.setImage(ImageUtils.conver2Bitmap(cursor.getBlob(cursor.getColumnIndex("image"))));
			data.setImage_url(cursor.getString(cursor.getColumnIndex("image_url")));
			data.setImage_s(cursor.getString(cursor.getColumnIndex("image_s")));
			data.setImage_m(cursor.getString(cursor.getColumnIndex("image_m")));
			data.setImage_o(cursor.getString(cursor.getColumnIndex("image_o")));
			data.setFrom(cursor.getString(cursor.getColumnIndex("sfrom")));
			data.setForward_count(cursor.getInt(cursor.getColumnIndex("forward_count")));
			data.setComment_count(cursor.getInt(cursor.getColumnIndex("comment_count")));
			data.setSp(cursor.getInt(cursor.getColumnIndex("sp")));
			data.setUser_id(cursor.getString(cursor.getColumnIndex("user_id")));
			data.setType(cursor.getInt(cursor.getColumnIndex("type")));
			if (cursor.getInt(cursor.getColumnIndex("source")) == 1) {
				data.setSource(true);
				data.setSource_status_id(cursor.getString(cursor.getColumnIndex("source_status_id")));
				data.setSource_nick(cursor.getString(cursor.getColumnIndex("source_nick")));
				data.setSource_name(cursor.getString(cursor.getColumnIndex("source_name")));
				if (cursor.getInt(cursor.getColumnIndex("source_isvip")) == 1) {
					data.setSource_isvip(true);
				} else {
					data.setSource_isvip(false);
				}
				data.setSource_status(cursor.getString(cursor.getColumnIndex("source_status")));
				data.setSource_time(cursor.getString(cursor.getColumnIndex("source_time")));
				data.setSource_image(ImageUtils.conver2Bitmap(cursor.getBlob(cursor.getColumnIndex("source_image"))));
				data.setSource_image_url(cursor.getString(cursor.getColumnIndex("source_image_url")));
				data.setSource_image_s(cursor.getString(cursor.getColumnIndex("source_image_s")));
				data.setSource_image_m(cursor.getString(cursor.getColumnIndex("source_image_m")));
				data.setSource_image_o(cursor.getString(cursor.getColumnIndex("source_image_o")));
				data.setSource_from(cursor.getString(cursor.getColumnIndex("source_from")));
				data.setSource_forward_count(cursor.getInt(cursor.getColumnIndex("source_forward_count")));
				data.setSource_comment_count(cursor.getInt(cursor.getColumnIndex("source_comment_count")));
			} else {
				data.setSource(false);
			}
			cursor.moveToNext();
		}
		cursor.close();
		return data;
	}
	/**
	 * 取出主页时间线数据
	 * @return
	 */
	public List<DataAdapter> loadHomeDatas() {
		List<DataAdapter> datas = new ArrayList<DataAdapter>();
 		Cursor cursor = db.query(TABLE_HOMELIST, null, null, null, null, null, "time desc");
 		cursor.moveToFirst();
 		while (!cursor.isAfterLast()) {
			DataAdapter data = new DataAdapter();
			data.setStatus_id(cursor.getString(cursor.getColumnIndex("status_id")));
			data.setNick(cursor.getString(cursor.getColumnIndex("nick")));
			data.setName(cursor.getString(cursor.getColumnIndex("name")));
			data.setHead(ImageUtils.conver2Bitmap(cursor.getBlob(cursor.getColumnIndex("head"))));
			data.setHead_url(cursor.getString(cursor.getColumnIndex("head_url")));
			if (cursor.getInt(cursor.getColumnIndex("isvip")) == 1) {
				data.setIsvip(true);
			} else {
				data.setIsvip(false);
			}
			if (cursor.getInt(cursor.getColumnIndex("isvip")) == 1) {
				data.setIsvip(true);
			} else {
				data.setIsvip(false);
			}
			data.setLat(cursor.getDouble(cursor.getColumnIndex("lat")));
			data.setLng(cursor.getDouble(cursor.getColumnIndex("long")));
			data.setTime(cursor.getString(cursor.getColumnIndex("time")));
			data.setOrigtime(cursor.getString(cursor.getColumnIndex("origtime")));
			data.setStatus(cursor.getString(cursor.getColumnIndex("status")));
			data.setImage(ImageUtils.conver2Bitmap(cursor.getBlob(cursor.getColumnIndex("image"))));
			data.setImage_url(cursor.getString(cursor.getColumnIndex("image_url")));
			data.setImage_s(cursor.getString(cursor.getColumnIndex("image_s")));
			data.setImage_m(cursor.getString(cursor.getColumnIndex("image_m")));
			data.setImage_o(cursor.getString(cursor.getColumnIndex("image_o")));
			data.setFrom(cursor.getString(cursor.getColumnIndex("sfrom")));
			data.setForward_count(cursor.getInt(cursor.getColumnIndex("forward_count")));
			data.setComment_count(cursor.getInt(cursor.getColumnIndex("comment_count")));
			data.setSp(cursor.getInt(cursor.getColumnIndex("sp")));
			data.setUser_id(cursor.getString(cursor.getColumnIndex("user_id")));
			data.setType(cursor.getInt(cursor.getColumnIndex("type")));
			if (cursor.getInt(cursor.getColumnIndex("source")) == 1) {
				data.setSource(true);
				data.setSource_status_id(cursor.getString(cursor.getColumnIndex("source_status_id")));
				data.setSource_nick(cursor.getString(cursor.getColumnIndex("source_nick")));
				data.setSource_name(cursor.getString(cursor.getColumnIndex("source_name")));
				if (cursor.getInt(cursor.getColumnIndex("source_isvip")) == 1) {
					data.setSource_isvip(true);
				} else {
					data.setSource_isvip(false);
				}
				data.setSource_status(cursor.getString(cursor.getColumnIndex("source_status")));
				data.setSource_time(cursor.getString(cursor.getColumnIndex("source_time")));
				data.setSource_image(ImageUtils.conver2Bitmap(cursor.getBlob(cursor.getColumnIndex("source_image"))));
				data.setSource_image_url(cursor.getString(cursor.getColumnIndex("source_image_url")));
				data.setSource_image_s(cursor.getString(cursor.getColumnIndex("source_image_s")));
				data.setSource_image_m(cursor.getString(cursor.getColumnIndex("source_image_m")));
				data.setSource_image_o(cursor.getString(cursor.getColumnIndex("source_image_o")));
				data.setSource_from(cursor.getString(cursor.getColumnIndex("source_from")));
				data.setSource_forward_count(cursor.getInt(cursor.getColumnIndex("source_forward_count")));
				data.setSource_comment_count(cursor.getInt(cursor.getColumnIndex("source_comment_count")));
			} else {
				data.setSource(false);
			}
			datas.add(data);
			data = null;
			cursor.moveToNext();
		}
 		cursor.close();
 		return datas;
 		
	}
	/**
	 * 保存一个授权账户
	 * @param adapter
	 * @return
	 */
	public long saveAccount(UserAdapter adapter) {
		/**
		 * 若已经存在该授权账户，则更新
		 */
		if (isUserExists(adapter)) {
			return updateAccount(adapter);
		} else {
			ContentValues values = adapter.conver2ContentValues();
			long _id = db.insert(TABLE_ACCOUNTS, null, values);
			return _id;
		}
	}
	/**
	 * @param datas
	 */
	public void saveHomeDatas(List<DataAdapter> datas) {
		db.delete(TABLE_HOMELIST, null, null);
		ContentValues values = new ContentValues();
 		for (DataAdapter data : datas) {
			values.put("status_id", data.getStatus_id());
			values.put("nick", data.getNick());
			values.put("name", data.getName());
			values.put("head", ImageUtils.conver2Byte(data.getHead()));
			values.put("head_url", data.getHead_url());
			values.put("isvip", data.isIsvip());
			values.put("isfav", data.isIsfav());
			values.put("type", data.getType());
			values.put("time", data.getTime());
			values.put("origtime", data.getOrigtime());
			values.put("status", data.getStatus());
			values.put("image", ImageUtils.conver2Byte(data.getImage()));
			values.put("image_url", data.getImage_url());
			values.put("image_s", data.getImage_s());
			values.put("image_m", data.getImage_m());
			values.put("image_o", data.getImage_o());
			values.put("sfrom", data.getFrom());
			values.put("forward_count", data.getForward_count());
			values.put("comment_count", data.getComment_count());
			values.put("long", data.getLng());
			values.put("lat", data.getLat());
			values.put("location", data.getLocation());
			values.put("source", data.isSource());
			values.put("source_nick", data.getSource_nick());
			values.put("source_name", data.getSource_name());
			values.put("source_isvip", data.isSource_isvip());
			values.put("source_time", data.getSource_time());
			values.put("source_status", data.getSource_status());
			values.put("source_status_id", data.getSource_status_id());
			values.put("source_image", ImageUtils.conver2Byte(data.getSource_image()));
			values.put("source_image_url", data.getSource_image_url());
			values.put("source_image_s", data.getSource_image_s());
			values.put("source_image_m", data.getSource_image_m());
			values.put("source_image_o", data.getSource_image_o());
			values.put("source_from", data.getSource_from());
			values.put("source_forward_count", data.getSource_forward_count());
			values.put("source_comment_count", data.getSource_comment_count());
			values.put("source_location", data.getSource_location());
			values.put("sp", data.getSp());
			values.put("user_id", data.getUser_id());
			db.insert(TABLE_HOMELIST, null, values);
		}
		
	}
	/**
	 * 追加数据
	 * @param datas
	 */
	public void addHomeDatas(List<DataAdapter> datas) {
		ContentValues values = new ContentValues();
 		for (DataAdapter data : datas) {
			values.put("status_id", data.getStatus_id());
			values.put("nick", data.getNick());
			values.put("name", data.getName());
			values.put("head", ImageUtils.conver2Byte(data.getHead()));
			values.put("head_url", data.getHead_url());
			values.put("isvip", data.isIsvip());
			values.put("isfav", data.isIsfav());
			values.put("type", data.getType());
			values.put("time", data.getTime());
			values.put("origtime", data.getOrigtime());
			values.put("status", data.getStatus());
			values.put("image", ImageUtils.conver2Byte(data.getImage()));
			values.put("image_url", data.getImage_url());
			values.put("image_s", data.getImage_s());
			values.put("image_m", data.getImage_m());
			values.put("image_o", data.getImage_o());
			values.put("sfrom", data.getFrom());
			values.put("forward_count", data.getForward_count());
			values.put("comment_count", data.getComment_count());
			values.put("long", data.getLng());
			values.put("lat", data.getLat());
			values.put("location", data.getLocation());
			values.put("source", data.isSource());
			values.put("source_nick", data.getSource_nick());
			values.put("source_name", data.getSource_name());
			values.put("source_isvip", data.isSource_isvip());
			values.put("source_time", data.getSource_time());
			values.put("source_status", data.getSource_status());
			values.put("source_status_id", data.getSource_status_id());
			values.put("source_image", ImageUtils.conver2Byte(data.getSource_image()));
			values.put("source_image_url", data.getSource_image_url());
			values.put("source_image_s", data.getSource_image_s());
			values.put("source_image_m", data.getSource_image_m());
			values.put("source_image_o", data.getSource_image_o());
			values.put("source_from", data.getSource_from());
			values.put("source_forward_count", data.getSource_forward_count());
			values.put("source_comment_count", data.getSource_comment_count());
			values.put("source_location", data.getSource_location());
			values.put("sp", data.getSp());
			values.put("user_id", data.getUser_id());
			db.insert(TABLE_HOMELIST, null, values);
		}
		
	}
	/**
	 * 更新账户信息BY user_id
	 * @param user_id
	 * @param name
	 * @param nick
	 * @param token
	 * @param token_secret
	 * @param head
	 * @return
	 */
	public int updateAccount(String user_id, int sp, Bitmap head) {
		ContentValues values = new ContentValues();
		values.put("user_id", user_id);
		values.put("sp", sp);
		if (head != null) {
			values.put("head", ImageUtils.conver2Byte(head));
		}
		int num = db.update(TABLE_ACCOUNTS, values, "user_id = " + "'" + user_id + "' and sp=" + sp, null);
		return num;
	}
	/**
	 * 根据adapter更新
	 * @param adapter
	 * @return
	 */
	public int updateAccount(UserAdapter adapter) {
		ContentValues values = adapter.conver2ContentValues();
		int num = db.update(TABLE_ACCOUNTS, values, "user_id = " + "'" + adapter.getUser_id() + "' and sp=" + adapter.getSp(), null);
		return num;
	}
	/**
	 * 删除账户;
	 * @param adapter 必须参数 user_id、sp
	 * @return
	 */
	public int deleteAccount(UserAdapter adapter) {
		return db.delete(TABLE_ACCOUNTS, "user_id = '" + adapter.getUser_id() + "' and sp=" + adapter.getSp(), null);
	}
}
