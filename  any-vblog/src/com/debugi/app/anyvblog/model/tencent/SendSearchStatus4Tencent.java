package com.debugi.app.anyvblog.model.tencent;

import com.debugi.app.anyvblog.model.SendData;
import com.debugi.open.oauth.model.Parameters;

public class SendSearchStatus4Tencent implements SendData {
	private String format;// 返回数据的格式（json或xml）
	private String keyword;// 搜索关键字
	private int pagesize;// 每页大小（1-30个）
	private int page;// 页码
	private int contenttype;// 消息的正文类型（按位使用）0-所有，0x01-纯文本，0x02-包含url，0x04-包含图片，0x08-包含视频，0x10-包含音频
	private int sorttype;// 排序方式 0-表示按默认方式排序(即时间排序(最新))
	private int msgtype;// 消息的类型（按位使用） 0-所有，1-原创发表，2
						// 转载，8-回复(针对一个消息，进行对话)，0x10-空回(点击客人页，进行对话)
	private int searchtype;// 搜索类型 0-默认搜索类型（现在为模糊搜索），1-模糊搜索，8-实时搜索
	private String starttime;// 开始时间，用UNIX时间表示（从1970年1月1日0时0分0秒起至现在的总秒数）
	private String endtime;// 结束时间，与starttime一起使用（必须大于starttime）
	private String province;// 省编码（不填表示忽略地点搜索）
	private String city;// 市编码（不填表示按省搜索）
	private double longitue;// 经度，（实数）*1000000
	private double latitude;// 纬度，（实数）*1000000
	private int radius;// 半径（整数，单位米，不大于20000）

	public SendSearchStatus4Tencent(String keyword, int pagesize, int page) {
		this(keyword, pagesize, page, null, null);
	}

	public SendSearchStatus4Tencent(String keyword, int pagesize, int page,
			String starttime, String endtime) {
		this("json", keyword, pagesize, page, 0, 0, 0, 0, null, null, null,
				null, 0, 0, 0);
	}

	public SendSearchStatus4Tencent(String format, String keyword,
			int pagesize, int page, int contenttype, int sorttype, int msgtype,
			int searchtype, String starttime, String endtime, String province,
			String city, double longitue, double latitude, int radius) {
		this.format = format;
		this.keyword = keyword;
		this.pagesize = pagesize;
		this.page = page;
		this.contenttype = contenttype;
		this.sorttype = sorttype;
		this.msgtype = msgtype;
		this.searchtype = searchtype;
		this.starttime = starttime;
		this.endtime = endtime;
		this.province = province;
		this.city = city;
		this.longitue = longitue;
		this.latitude = latitude;
		this.radius = radius;
	}

	@Override
	public Parameters conver() {
		Parameters parameters = new Parameters();
		parameters.addParameter("format", format);
		parameters.addParameter("keyword", keyword);
		parameters.addParameter("pagesize", pagesize + "");
		parameters.addParameter("page", page + "");
		parameters.addParameter("contenttype", contenttype + "");
		parameters.addParameter("sorttype", sorttype + "");
		parameters.addParameter("msgtype", msgtype + "");
		parameters.addParameter("searchtype", searchtype + "");
		if (starttime != null) {
			parameters.addParameter("starttime", starttime);
		} else if (endtime != null) {
			parameters.addParameter("endtime", endtime);
		} else if (province != null) {
			parameters.addParameter("province", province);
		} else if (city != null) {
			parameters.addParameter("city", city);
		} else if (longitue != 0 && latitude != 0) {
			parameters.addParameter("longitue", longitue + "");
			parameters.addParameter("latitude", latitude + "");
		} else if (radius != 0) {
			parameters.addParameter("radius", radius + "");
		}
		return parameters;
	}

}
