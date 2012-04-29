package com.debugi.app.anyvblog.model.netease;

import com.debugi.app.anyvblog.model.SendData;
import com.debugi.open.oauth.model.Parameters;

public class SendComment4Netease implements SendData {
	private String id;	//必选参数，值为被评论微博的ID。如果回复某条评论，则此值为该评论的id。
	private String status;	//必选参数，评论内容。
	private String is_retweet;	//可选参数，是否转发 默认不转发 1为转发
	private String is_comment_to_root;	//是否评论给原微博 默认不评论 1为评论
	
	public SendComment4Netease(String id, String status) {
		this(id, status, "0");
	}

	public SendComment4Netease(String id, String status, String is_retweet) {
		this(id, status, is_retweet, "0");
	}

	public SendComment4Netease(String id, String status, String is_retweet,
			String is_comment_to_root) {
		this.id = id;
		this.status = status;
		this.is_retweet = is_retweet;
		this.is_comment_to_root = is_comment_to_root;
	}

	@Override
	public Parameters conver() {
		Parameters parameters = new Parameters();
		parameters.addParameter("id", id);
		parameters.addParameter("status", status);
		parameters.addParameter("is_retweet", is_retweet);
		parameters.addParameter("is_comment_to_root", is_comment_to_root);
		return parameters;
	}

}
