package com.debugi.app.anyvblog.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {
	/**
	 * 检查当前网络是否可用
	 * @return
	 */
	public static boolean checkEnable(Context context) {
		boolean isEnable = false;
//		if (getIpAddress().size() > 0) {
//			isEnable = true;
//		}
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectivityManager.getActiveNetworkInfo();
		if (info != null && info.isAvailable()) {
			isEnable = true;
		}
		return isEnable;
	}
	/**
	 * 取得当前的所有ip
	 * @return
	 */
	public static List<String> getIpAddress() {
		List<String> ipaddrList = new ArrayList<String>();
		try {
			//取得网络接口信息
			Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
			while (enumeration.hasMoreElements()) {
				NetworkInterface networkInterface = (NetworkInterface) enumeration
						.nextElement();
				//取得网络地址
				Enumeration<InetAddress> inetAddress = networkInterface.getInetAddresses();
				while (inetAddress.hasMoreElements()) {
					InetAddress inetAddress2 = (InetAddress) inetAddress
							.nextElement();
					//如果不是127.0.0.1就存入list
					if (!inetAddress2.isLoopbackAddress()) {
						ipaddrList.add(inetAddress2.getHostAddress());
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return ipaddrList;
	}
}
