package com.ilovn.app.anyvblog.model.sina;

import com.ilovn.app.anyvblog.model.SendData;
import com.ilovn.open.oauth.model.Parameters;
/**
 * 获取粉丝和好友使用同一个
 * @author Administrator
 *
 */
public class SendUsers4Sina implements SendData {
	//:id	false	int64/string	用户ID(int64)或者昵称(string)。该参数为一个REST风格参数。调用示例见注意事项
	//user_id	false	int64	用户ID，主要是用来区分用户ID跟微博昵称。当微博昵称为数字导致和用户ID产生歧义，特别是当微博昵称和用户ID一样的时候，建议使用该参数
	//screen_name	false	string	微博昵称，主要是用来区分用户UID跟微博昵称，当二者一样而产生歧义的时候，建议使用该参数
	private int cursor;//	false	int	用于分页请求，请求第1页cursor传-1，在返回的结果中会得到next_cursor字段，表示下一页的cursor。next_cursor为0表示已经到记录末尾。
	private int count;//	false	int，默认20，最大200	每页返回的最大记录数，最大不能超过200，默认为20。
	
	public SendUsers4Sina(int cursor, int count) {
		this.cursor = cursor;
		this.count = count;
	}

	@Override
	public Parameters conver() {
		Parameters parameters = new Parameters();
		parameters.addParameter("cursor", cursor + "");
		parameters.addParameter("count", count + "");
		return parameters;
	}

}
