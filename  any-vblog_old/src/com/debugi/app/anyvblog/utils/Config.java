package com.debugi.app.anyvblog.utils;

import java.io.File;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;

import com.debugi.app.anyvblog.R;
import com.debugi.app.anyvblog.model.UserAdapter;

public class Config {
	public static UserAdapter currentUser;
	public static boolean debug;
	public static boolean showPic;
	public static boolean autoCheckUpdate;
	public static String cachePath;
	public static final String UPDATE_CHECK_URL = "http://debugi.com/anyvblog/update.php";

	public static String getCachePath(Context context) {
		File path;
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			path = Environment.getExternalStorageDirectory();
		} else {
			path = Environment.getDownloadCacheDirectory();
		}
		File dir = new File(path, "AnyvBlog");
		if (!dir.exists()) {
			if (dir.mkdirs()) {
				return dir.toString() + File.separator;
			} else {
				return path.toString() + File.separator;
			}
		} else {
			return dir.toString() + File.separator;
		}
	}
	/**
	 * 获取设置中的图文浏览模式
	 * @param context
	 * @return
	 */
	public static boolean isShowPic(Context context) {
		boolean show = false;
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		show = preferences.getBoolean(context.getString(R.string.show_pic), true);
		return show;
	}
	/**
	 * 获取设置中的检查更新模式
	 * @param context
	 * @return
	 */
	public static boolean isAutoCheckUpdate(Context context) {
		boolean auto = false;
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		auto = preferences.getBoolean(context.getString(R.string.auto_update), true);
		return auto;
	}
	/**
	 * 写入已查看更新信息
	 * @param context
	 * @return
	 */
	public static void alreadySeeNews(Context context) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = preferences.edit();
		editor.putBoolean("seeNews", true);
		editor.commit();
	}
	/**
	 * 获取是否查看更新说明
	 * @param context
	 * @return
	 */
	public static boolean isAlreadySeeNews(Context context) {
		boolean flag = false;
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		flag = preferences.getBoolean("seeNews", false);
		return flag;
	}

	/**
	 * 调试信息
	 * 
	 * @param tag
	 * @param content
	 */
	public static void debug(String tag, Object content) {
		if (!debug) {
			return;
		} else if (content == null) {
			return;
		}
		Log.i(tag, content.toString());
	}
}
