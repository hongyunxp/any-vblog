package com.ilovn.app.anyvblog.model.netease;

import com.ilovn.app.anyvblog.model.SendData;
import com.ilovn.open.oauth.model.Parameters;

public class SendForward4Netease implements SendData {
	private String id;	//必选参数，值为被转发微博的ID
	private String status;	//可选参数，评论内容，默认为“转发微博。”
	private String is_comment;	//可选参数，是否评论 默认不评论 1为评论
	private String is_comment_to_root;	//可选参数，是否评论给原微博 默认不评论 1为评论
	
	public SendForward4Netease(String id, String status) {
		this(id, status, "0");
	}

	public SendForward4Netease(String id, String status, String is_comment) {
		this(id, status, is_comment, "0");
	}

	public SendForward4Netease(String id, String status, String is_comment,
			String is_comment_to_root) {
		this.id = id;
		this.status = status;
		this.is_comment = is_comment;
		this.is_comment_to_root = is_comment_to_root;
	}

	@Override
	public Parameters conver() {
		Parameters parameters = new Parameters();
		parameters.addParameter("id", id);
		if (status == null || "".equals(status)) {
			status = "转发微博";
		}
		parameters.addParameter("status", status);
		parameters.addParameter("is_comment", is_comment);
		parameters.addParameter("is_comment_to_root", is_comment_to_root);
		return parameters;
	}

}
