package com.debugi.app.anyvblog.model.netease;

import com.debugi.app.anyvblog.model.SendData;
import com.debugi.open.oauth.model.Parameters;

public class SendGet_UserInfo4Netease implements SendData {
	/**
	 * 获取指定用户信息。
	 * user_id、name、screen_name三个参数如果都不传，则返回当前用户信息；
	 * 如果用户通行证身份验证成功且用户已经开通微博则返回http状态为200；
	 * 如果不是则返回401的状态和错误信；
	 * 根据参数查找不到对应的用户则返回404的状态和错误信息。
	 */
	private String id;	//可选参数，用户的个性网址或用户ID；
	private String name;	//可选参数，用户的昵称；
	private String user_id;	//可选参数，用户ID
	private String screen_name;	//用户的个性网址
	
	public SendGet_UserInfo4Netease(String id) {
		this.id = id;
	}
	@Deprecated
	public SendGet_UserInfo4Netease(String id, String name, String user_id,
			String screen_name) {
		this.id = id;
		this.name = name;
		this.user_id = user_id;
		this.screen_name = screen_name;
	}

	@Override
	public Parameters conver() {
		Parameters parameters = new Parameters();
		if (id != null && !"".equals(id)) {
			parameters.addParameter("id", id);
		}
		return parameters;
	}

}
