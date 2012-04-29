package com.ilovn.app.anyvblog.model.sohu;

import com.ilovn.app.anyvblog.model.SendData;
import com.ilovn.open.oauth.model.Parameters;
/**
 * 该类多处复用，因为搜狐大多请求的id参数直接放在URL中
 * @author Administrator
 *
 */
public class SendTimeLineData4Sohu implements SendData {
	private String max_id;	//最大微博id，传递这个id之后，会返回比指定微博更早的微博。在传递了max_id之后，cursor和page都将不再起作用，它具有最高优先级。
	private String since;	//更新起始时间点，只获取since时间之后的更新，但是since必须是24小时内的某个时间，时间格式：Fri Mar 12 22:21:42 +0800 2010
	private String since_id;	//获取指定的文章id之后的更新，这是一个长整型变量。
	private int page;	//当前的页数(一旦传递了cursor值，page方式将会失效）
	private String cursor;	//下一页的cursor_id(cursor分页方式，点击查看更多)
	private int count;	//每页条数
	
	

	public SendTimeLineData4Sohu(int count) {
		this(count, null);
	}

	public SendTimeLineData4Sohu(int count, String max_id) {
		this.count = count;
		this.max_id = max_id;
	}

	public SendTimeLineData4Sohu(String max_id, String since, String since_id,
			int page, String cursor, int count) {
		this.max_id = max_id;
		this.since = since;
		this.since_id = since_id;
		this.page = page;
		this.cursor = cursor;
		this.count = count;
	}



	@Override
	public Parameters conver() {
		Parameters parameters = new Parameters();
		if (max_id != null) {
			parameters.addParameter("max_id", max_id);
		}
		parameters.addParameter("count", count + "");
		return parameters;
	}

}
