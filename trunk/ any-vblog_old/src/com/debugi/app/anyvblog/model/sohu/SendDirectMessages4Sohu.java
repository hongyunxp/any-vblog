package com.debugi.app.anyvblog.model.sohu;

import com.debugi.app.anyvblog.model.SendData;
import com.debugi.open.oauth.model.Parameters;

public class SendDirectMessages4Sohu implements SendData {
	private String since;	//更新起始时间点，只获取since时间之后的更新，但是since必须是24小时内的某个时间，时间格式：Fri Mar 12 22:21:42 +0800 2010
	private String since_id;	//获取指定的文章id之后的更新，这是一个长整型变量。
	private int page;	//当前的页数(一旦传递了cursor值，page方式将会失效）
	private int count;	//每页条数
	
	public SendDirectMessages4Sohu(int count) {
		this(count, 0);
	}



	public SendDirectMessages4Sohu(int count, int page) {
		this(null, page, count);
	}



	public SendDirectMessages4Sohu(String since, int page, int count) {
		this(since, null, page, count);
	}



	public SendDirectMessages4Sohu(String since, String since_id, int page,
			int count) {
		this.since = since;
		this.since_id = since_id;
		this.page = page;
		this.count = count;
	}



	@Override
	public Parameters conver() {
		Parameters parameters = new Parameters();
		parameters.addParameter("page", page + "");
		parameters.addParameter("count", count + "");
		return parameters;
	}

}
