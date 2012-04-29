package com.debugi.app.anyvblog.model.sina;

import com.debugi.app.anyvblog.model.SendData;
import com.debugi.open.oauth.model.Parameters;
/**
 * 评论请求数据包，使用POST请求
 * @author Administrator
 *
 */
public class SendComment4Sina implements SendData {
	/**
	 * 必选 类型及范围 说明 
	 * source true string 申请应用时分配的AppKey，调用接口时候代表应用的唯一身份。（采用OAuth授权方式不需要此参数）
	 * cid true int64 要回复的评论ID。 
	 * comment true string 要回复的评论内容。必须做URLEncode,信息内容不超过140个汉字。 
	 * id true int64 要评论的微博消息ID 
	 * without_mention false int 1：回复中不自动加入“回复@用户名”，0：回复中自动加入“回复@用户名”.默认为0. 注意事项
	 * 为防止重复提交，发表的评论与上次发表的评论内容相同的时候，将返回400错误。 如果id及cid不存在，将返回400错误
	 * 如果没有提供cid或cid非法，而id参数正确，则为对微博的评论。发表评论的返回格式参见statuses/comment
	 * comment_ori	false	int	当评论一条转发微博时，是否评论给原微博。0:不评论给原微博。1：评论给原微博。默认0.
	 */
	private String cid;
	private String comment;
	private String id;
	private int without_mention;
	private int comment_ori;
	/**
	 * 用于评论的构造方法
	 * @param comment 要回复的内容
	 * @param id 要评论的微博消息ID
	 */
	public SendComment4Sina(String comment, String id) {
		this(comment, "", id);
	}
	/**
	 * 用于回复的构造方法
	 * @param comment 要回复的内容
	 * @param cid 要回复的评论ID
	 * @param id 要评论的微博消息
	 */
	public SendComment4Sina(String comment, String cid, String id) {
		this(comment, cid, id, 0);
	}
	/**
	 * 
	 * @param comment 要回复的内容
	 * @param cid 要回复的评论ID
	 * @param id 要评论的微博消息
	 * @param without_mention 是否自动加入回复人
	 */
	public SendComment4Sina(String comment, String cid, String id,
			int without_mention) {
		this.comment = comment;
		this.cid = cid;
		this.id = id;
		this.without_mention = without_mention;
	}

	@Override
	public Parameters conver() {
		Parameters parameters = new Parameters();
		parameters.addParameter("comment", comment);
		if (cid != null && !"".equals(cid)) {
			parameters.addParameter("cid", cid);
		} parameters.addParameter("id", id);
		parameters.addParameter("without_mention", without_mention + "");
		parameters.addParameter("comment_ori", comment_ori + "");
		return parameters;
	}

}
