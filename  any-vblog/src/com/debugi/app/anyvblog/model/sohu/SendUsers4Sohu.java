package com.debugi.app.anyvblog.model.sohu;

import com.debugi.app.anyvblog.model.SendData;
import com.debugi.open.oauth.model.Parameters;

public class SendUsers4Sohu implements SendData {
//	private String id;//	用户id，当获取其他人的好友列表时填写,使用第二种url
//	private int page;//	当前的页数(一旦传递了cursor值，page方式将会失效）
	private int cursor;//	下一页的cursor_id(cursor分页方式，点击查看更多)
	private int count;//	每页获取的用户数量，最多不超过20条，默认20条
	
	public SendUsers4Sohu(int cursor, int count) {
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
