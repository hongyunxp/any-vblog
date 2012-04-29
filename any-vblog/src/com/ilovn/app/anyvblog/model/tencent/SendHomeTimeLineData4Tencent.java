package com.ilovn.app.anyvblog.model.tencent;

import com.ilovn.app.anyvblog.model.SendData;
import com.ilovn.open.oauth.model.Parameters;

public class SendHomeTimeLineData4Tencent implements SendData {
	private String format;	//返回数据的格式（json或xml）
	private int pageflag;	//分页标识（0：第一页，1：向下翻页，2：向上翻页）
	private long pagetime;	//本页起始时间（第一页：填0，向上翻页：填上一次请求返回的第一条记录时间，向下翻页：填上一次请求返回的最后一条记录时间）
	private int reqnum;		//每次请求记录的条数（1-300条）
	private int type;		//拉取类型0x1 原创发表0x2 转载0x8 回复0x10 空回0x20 提及0x40 点评如需拉取多个类型请使用|，如(0x1|0x2)得到3，则type=3即可，填零表示拉取所有类型
	private int contenttype;//	 内容过滤。0-表示所有类型，1-带文本，2-带链接，4-带图片，8-带视频，0x10-带音频
	
	public SendHomeTimeLineData4Tencent(int reqnum) {
		this(0, 0, reqnum);
	}
	/**
	 * @param pageflag 分页标识（0：第一页，1：向下翻页，2：向上翻页）
	 * @param pagetime 本页起始时间（第一页：填0，向上翻页：填上一次请求返回的第一条记录时间，向下翻页：填上一次请求返回的最后一条记录时间）
	 * @param reqnum 每次请求记录的条数（1-300条）
	 */
	public SendHomeTimeLineData4Tencent(int pageflag, long pagetime, int reqnum) {
		this(pageflag, pagetime, reqnum, 0, 0);
	}

	public SendHomeTimeLineData4Tencent(int pageflag, long pagetime, int reqnum,
			int type, int contenttype) {
		this(Format.json.name(), pageflag, pagetime, reqnum, type, contenttype);
	}

	public SendHomeTimeLineData4Tencent(String format, int pageflag,
			long pagetime, int reqnum, int type, int contenttype) {
		this.format = format;
		this.pageflag = pageflag;
		this.pagetime = pagetime;
		this.reqnum = reqnum;
		this.type = type;
		this.contenttype = contenttype;
	}

	@Override
	public Parameters conver() {
		init();
		Parameters parameters = new Parameters();
		parameters.addParameter("format", format);
		parameters.addParameter("pageflag", pageflag + "");
		parameters.addParameter("pagetime", pagetime + "");
		parameters.addParameter("reqnum", reqnum + "");
		parameters.addParameter("type", type + "");
		parameters.addParameter("contenttype", contenttype + "");
		return parameters;
	}
	
	private void init() {
		if (format == null || "".equals(format)) {
			format = Format.json.name();
		} else if (pageflag < 0 || pageflag > 2) {
			pageflag = 0;
		} else if (reqnum < 1 || reqnum > 70) {
			reqnum = 30;
		} else if ((contenttype % 2) != 0 || contenttype < 0 || contenttype > 0x10) {
			contenttype = 0;
		}
	}
	
	private enum Format {
		json, xml
	}
}
