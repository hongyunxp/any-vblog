package com.debugi.app.anyvblog.model.sohu;

import com.debugi.app.anyvblog.model.SendData;
import com.debugi.open.oauth.model.Parameters;

public class SendComment4Sohu implements SendData {
	private String id;//要评论的微博id
	private String comment;//评论内容
	private String reply_user_id;//回复评论的评论人id
	/**
	 * @param id 要评论的微博id
	 * @param comment 评论内容
	 */
	public SendComment4Sohu(String id, String comment) {
		this(id, comment, "");
	}
	/**
	 * @param id 要评论的微博id
	 * @param comment 评论内容
	 * @param reply_user_id 回复评论的评论人id
	 */
	public SendComment4Sohu(String id, String comment, String reply_user_id) {
		this.id = id;
		this.comment = comment;
		this.reply_user_id = reply_user_id;
	}

	@Override
	public Parameters conver() {
		Parameters parameters = new Parameters();
		parameters.addParameter("id", id);
		parameters.addParameter("comment", comment);
		if (reply_user_id != null && !"".equals(reply_user_id)) {
			parameters.addParameter("reply_user_id", reply_user_id);
		}
		return parameters;
	}

}
