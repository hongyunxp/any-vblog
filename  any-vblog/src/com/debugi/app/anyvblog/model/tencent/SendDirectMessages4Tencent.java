package com.debugi.app.anyvblog.model.tencent;

import com.debugi.app.anyvblog.model.SendData;
import com.debugi.open.oauth.model.Parameters;

public class SendDirectMessages4Tencent implements SendData {
	private String format;	//返回数据的格式（json或xml）
	private int pageflag;	//分页标识（0：第一页，1：向下翻页，2：向上翻页）
	private String pagetime;	//本页起始时间（第一页：填0，向上翻页：填上一次请求返回的第一条记录时间，向下翻页：填上一次请求返回的最后一条记录时间）
	private int reqnum;	//每次请求记录的条数（1-70条）
	private String lastid;	//用于翻页，和pagetime配合使用（第一页：填0，向上翻页：填上一次请求返回的第一条记录id，向下翻页：填上一次请求返回的最后一条记录id）
	
	public SendDirectMessages4Tencent(int reqnum) {
		this(reqnum, 0);
	}

	public SendDirectMessages4Tencent(int reqnum, int pageflag) {
		this(pageflag, "0", reqnum, "0");
	}

	public SendDirectMessages4Tencent(int pageflag, String pagetime,
			int reqnum, String lastid) {
		this("json", pageflag, pagetime, reqnum, lastid);
	}

	public SendDirectMessages4Tencent(String format, int pageflag,
			String pagetime, int reqnum, String lastid) {
		this.format = format;
		this.pageflag = pageflag;
		this.pagetime = pagetime;
		this.reqnum = reqnum;
		this.lastid = lastid;
	}

	@Override
	public Parameters conver() {
		Parameters parameters = new Parameters();
		parameters.addParameter("format", format);
		parameters.addParameter("pageflag", pageflag + "");
		parameters.addParameter("pagetime", pagetime);
		parameters.addParameter("reqnum", reqnum + "");
		parameters.addParameter("lastid", lastid);
		return parameters;
	}

}
