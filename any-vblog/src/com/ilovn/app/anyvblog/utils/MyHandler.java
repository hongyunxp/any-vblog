package com.ilovn.app.anyvblog.utils;

import android.os.Handler;

public class MyHandler {
	public static Handler handler;
	public static Handler handler_home;
	//MyHandler What
	public static final int Handler_GetDataFromNet = 0x106;
	public static final int Handler_GetDataFromDB = 0x107;
	public static final int Handler_GetData = 0x108;
	
	public static final int Handler_Sohu_Source = 0x201;
	public static final int Handler_Location = 0x202;
	//UnRead SendCounts4Sina
	public static final int Handler_Clear_Count_Home = 0x301;
	public static final int Handler_Clear_Count_Mentions = 0x302;
	public static final int Handler_Clear_Count_Directmessages = 0x303;
	public static final int Handler_Clear_Count_Followers = 0x304;
	public static final int Handler_Get_UnRead_Count = 0x305;
	public static final int Handler_Clear_Count_All = 0x306;
	
	//save default account
	public static final int Handler_Save_Default_Account = 0x401;
	//exit
	public static final int Handler_Back = 0x402;
	
	//get data without update success
	public static final int Handler_GetData_Without_Update = 0x403;
	public static final int Handler_Saved = 0x404;
}
