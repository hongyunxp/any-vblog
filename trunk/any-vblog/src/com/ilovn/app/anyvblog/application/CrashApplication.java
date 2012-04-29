package com.ilovn.app.anyvblog.application;

import android.app.Application;

import com.ilovn.app.anyvblog.exception.CrashHandler;

public class CrashApplication extends Application {
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		CrashHandler crashHandler = CrashHandler.getInstance();
		// 注册crashHandler
		crashHandler.init(getApplicationContext());
		// 发送以前没发送的报告(可选)
//		crashHandler.sendPreviousReportsToServer();
	}
}
