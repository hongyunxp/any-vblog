package com.debugi.app.anyvblog.model.tencent;

import com.debugi.app.anyvblog.model.SendData;
import com.debugi.open.oauth.model.Parameters;

public class SendMentionsTimeLine4Tencent implements SendData {

	private String format;	//返回数据的格式（json或xml）
	private int pageflag;	//分页标识（0：第一页，1：向下翻页，2：向上翻页）
	private long pagetime;	//本页起始时间（第一页：填0，向上翻页：填上一次请求返回的第一条记录时间，向下翻页：填上一次请求返回的最后一条记录时间）
	private int reqnum;	//每次请求记录的条数（1-70条）
	private String lastid;	//用于翻页，和pagetime配合使用（第一页：填0，向上翻页：填上一次请求返回的第一条记录id，向下翻页：填上一次请求返回的最后一条记录id）
	private int type;	//拉取类型0x1 原创发表0x2 转载0x8 回复0x10 空回	0x20 提及 0x40 点评 如需拉取多个类型请使用|，如(0x1|0x2)得到3，则type=3即可，填零表示拉取所有类型
	private int contenttype;	//内容过滤。0-表示所有类型，1-带文本，2-带链接，4-带图片，8-带视频，0x10-带音频
	
	public SendMentionsTimeLine4Tencent(int reqnum, int type) {
		this(reqnum, 0, reqnum, "0", type, 0);
	}

	/**
	 * @param pageflag 分页标识（0：第一页，1：向下翻页，2：向上翻页）
	 * @param pagetime 本页起始时间（第一页：填0，向上翻页：填上一次请求返回的第一条记录时间，向下翻页：填上一次请求返回的最后一条记录时间）
	 * @param reqnum 每次请求记录的条数（1-300条）
	 * @param lastid 用于翻页，和pagetime配合使用（第一页：填0，向上翻页：填上一次请求返回的第一条记录id，向下翻页：填上一次请求返回的最后一条记录id）
	 */
	public SendMentionsTimeLine4Tencent(int pageflag, long pagetime, String lastid, int reqnum, int type) {
		this(pageflag, pagetime, reqnum, lastid, type, 0);
	}

	public SendMentionsTimeLine4Tencent(int pageflag, long pagetime, int reqnum, String lastid,
			int type, int contenttype) {
		this("json", reqnum, pageflag, pagetime, lastid, type, contenttype);
	}

	public SendMentionsTimeLine4Tencent(String format, int reqnum,
			int pageflag, long pagetime, String lastid, int type,
			int contenttype) {
		this.format = format;
		this.reqnum = reqnum;
		this.pageflag = pageflag;
		this.pagetime = pagetime;
		this.lastid = lastid;
		this.type = type;
		this.contenttype = contenttype;
	}

	@Override
	public Parameters conver() {
		Parameters parameters = new Parameters();
		parameters.addParameter("format", format);
		parameters.addParameter("pageflag", pageflag + "");
		parameters.addParameter("pagetime", pagetime + "");
		parameters.addParameter("reqnum", reqnum + "");
		parameters.addParameter("lastid", lastid);
		parameters.addParameter("type", type + "");
		parameters.addParameter("contenttype", contenttype + "");
		return parameters;
	}

}
