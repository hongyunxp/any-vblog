package com.ilovn.app.anyvblog.application;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

/**
 * 用于程序完全退出
 * 
 * @author zhaoyong
 * 
 */
public class ActivityStack extends Application {

	private List<Activity> activities = new LinkedList<Activity>();
	private static ActivityStack instance;

	private ActivityStack() {
	}

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static ActivityStack getInstance() {
		if (instance == null) {
			instance = new ActivityStack();
		}
		return instance;

	}

	/**
	 * 将activity加入链表
	 * 
	 * @param activity
	 */
	public void addActivity(Activity activity) {
		this.activities.add(activity);
	}

	/**
	 * 遍历链表，结束所有，然后退出
	 */
	public void exit() {
		Log.i("exit", "size = " + activities.size());
		for (Activity activity : activities) {
			if (activity != null) {
				activity.finish();
			}
		}
		System.exit(0);
	}
}
