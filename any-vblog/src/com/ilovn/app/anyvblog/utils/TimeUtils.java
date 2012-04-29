package com.ilovn.app.anyvblog.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {
	public static String coverTimeStamp(String timeString) {
		if (timeString == null || "".equals(timeString)) {
			return "";
		}
		Timestamp stamp = new Timestamp(Long.parseLong(timeString));
		SimpleDateFormat format = new SimpleDateFormat("MM/dd HH:mm");
		return format.format(stamp);
	}
	public static String coverTime(String timeString) {
		if (timeString == null || "".equals(timeString)) {
			return "";
		}
		SimpleDateFormat format = new SimpleDateFormat("MM/dd HH:mm");
		return format.format(Date.parse(timeString));
	}
}
