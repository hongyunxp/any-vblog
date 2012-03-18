package com.debugi.app.anyvblog.utils;






/**
 * 工具类，存储各个微博所需的必要参数
 * 
 * @author Administrator
 * 
 */
public class Constants {
	/**
	 * 腾讯微博相关参数
	 * @author Administrator
	 * 
	 */
	public static class Tencent {
		/**
		 * App_key
		 */
		public static final String CONSUMER_KEY = "8091c489bbcc4350b22c3c0e636b75fe";
		/**
		 * App_secret
		 */
		public static final String CONSUMER_SECRET = "e9c61732ed9fb2574a8a10b0ff9103af";

		public static final String TENCENT_TOKEN = "tencent_token";
		public static final String TENCENT_TOKEN_SECRET = "tencent_token_secret";

		public static final String SEND_ONE = "http://open.t.qq.com/api/t/add";
		public static final String HOME_TIMELINE = "http://open.t.qq.com/api/statuses/home_timeline";
		public static final String PUBLIC_TIMELINE = "http://open.t.qq.com/api/statuses/public_timeline";
		public static final String RE_COUNT = "http://open.t.qq.com/api/t/re_count";
		public static final String GET_ONE = "http://open.t.qq.com/api/t/show";
		public static final String GET_USERINFO = "http://open.t.qq.com/api/user/info";
		public static final String GET_OTHERUSERINFO = "http://open.t.qq.com/api/user/other_info";
		public static final String MENTIONS_TIMELINE = "http://open.t.qq.com/api/statuses/mentions_timeline";
		public static final String DIRECT_MESSAGES_RECV = "http://open.t.qq.com/api/private/recv";
		public static final String DIRECT_MESSAGES_SENT = "http://open.t.qq.com/api/private/send";
		public static final String DIRECT_MESSAGES_NEW = "http://open.t.qq.com/api/private/add";
		public static final String FORWARD = "http://open.t.qq.com/api/t/re_add";
		
		public static final String GET_INVITE_LIST = "http://open.t.qq.com/api/invite/get_invite_list";
		public static final String FRIENDS_LIST = "http://open.t.qq.com/api/friends/user_idollist";
		public static final String FOLLOWERS_LIST = "http://open.t.qq.com/api/friends/user_fanslist";
		/**
		 * 评论接口，回复和点评分离
		 */
		public static final String COMMENT_REPLY = "http://open.t.qq.com/api/t/reply";
		public static final String COMMENT = "http://open.t.qq.com/api/t/comment";
		
		/**
		 * 数据收藏
		 */
		public static final String FAV_ADD = "http://open.t.qq.com/api/fav/addt";
		public static final String FAV_DEL = "http://open.t.qq.com/api/fav/delt";
		
		/**
		 * 我发表的时间线
		 */
		public static final String BROADCAST_TIMELINE = "http://open.t.qq.com/api/statuses/broadcast_timeline";
		/**
		 * 其他用户的时间线
		 */
		public static final String USER_TIMELINE = "http://open.t.qq.com/api/statuses/user_timeline";
		/**
		 * 未读信息数
		 */
		public static final String UNREAD = "http://open.t.qq.com/api/info/update";
		/**
		 * 搜索 微博
		 */
		public static final String SEARCH_STATUS = "http://open.t.qq.com/api/search/t";
		/**
		 *  搜索 用户
		 */
		public static final String SEARCH_USER = "http://open.t.qq.com/api/search/user";
		
		public static final String ADD_FRIEND = "http://open.t.qq.com/api/friends/add";
		public static final String DEL_FRIEND = "http://open.t.qq.com/api/friends/del";
		
	}

	/**
	 * 新浪微博相关参数
	 * @author Administrator
	 * 
	 */
	public static class Sina {
		public static final String CONSUMER_KEY = "2266267350";
		public static final String CONSUMER_SECRET = "bc57ad47e4f0a3a80e08738049a3860d";

		public static final String SINA_TOKEN = "sina_token";
		public static final String SINA_TOKEN_SECRET = "sina_token_secret";

		public static final String SEND_ONE = "http://api.t.sina.com.cn/statuses/update.json";
		public static final String HOME_TIMELINE = "http://api.t.sina.com.cn/statuses/friends_timeline.json";
		public static final String GET_USERINFO = "http://api.t.sina.com.cn/users/show.json";
		public static final String GET_ONE = "http://api.t.sina.com.cn/statuses/show/%s.json";
		public static final String MENTIONS_TIMELINE = "http://api.t.sina.com.cn/statuses/mentions.json";
		public static final String COMMENTS_TIMELINE = "http://api.t.sina.com.cn/statuses/comments_timeline.json";
		//新浪微博私信接口不可用
		public static final String DIRECT_MESSAGES_RECV = "http://api.t.sina.com.cn/direct_messages.json";
		public static final String FORWARD = "http://api.t.sina.com.cn/statuses/repost.json";
		/**
		 * 回复和评论接口，由参数控制类别
		 */
		public static final String COMMENT = "http://api.t.sina.com.cn/statuses/comment.json";
		public static final String REPLY = "http://api.t.sina.com.cn/statuses/reply.json";
		/**
		 * 数据收藏
		 */
		public static final String FAV_ADD = "http://api.t.sina.com.cn/favorites/create.json";
		public static final String FAV_DEL = "http://api.t.sina.com.cn/favorites/destroy/%s.json";
		
		/**
		 * 用户时间线
		 */
		public static final String USER_TIMELINE = "http://api.t.sina.com.cn/statuses/user_timeline.json";
		/**
		 * 未读信息数
		 */
		public static final String UNREAD = "http://api.t.sina.com.cn/statuses/unread.json";
		/**
		 * 未读消息清零
		 */
		public static final String UNREAD_RESET = "http://api.t.sina.com.cn/statuses/reset_count.json";
		/**
		 * 评论和转发数量
		 */
		public static final String COUNTS = "http://api.t.sina.com.cn/statuses/counts.json";
		
		public static final String FOLLOWERS_LIST = "http://api.t.sina.com.cn/statuses/followers/%s.json";
		public static final String FRIENDS_LIST = "http://api.t.sina.com.cn/statuses/friends/%s.json";
		
		public static final String ADD_FRIEND = "http://api.t.sina.com.cn/friendships/create/%s.json";
		public static final String DEL_FRIEND = "http://api.t.sina.com.cn/friendships/destroy/%s.json";
		public static final String FRIENDSHIPS = "http://api.t.sina.com.cn/friendships/show.json";
	}

	/**
	 * 网易微博相关参数
	 * @author Administrator
	 */
	public static class NetEase {
		public static final String CONSUMER_KEY = "SkufIdZqmLwfD6xk";
		public static final String CONSUMER_SECRET = "OsQ8foSzBlugOL6a3YHoZzCW5f4YkjV4";

		public static final String NETEASE_TOKEN = "netease_token";
		public static final String NETEASE_TOKEN_SECRET = "netease_token_secret";

		public static final String SEND_ONE = "http://api.t.163.com/statuses/update.json";
		public static final String GET_USERINFO = "http://api.t.163.com/users/show.json";
		public static final String HOME_TIMELINE = "http://api.t.163.com/statuses/home_timeline.json";
		public static final String GET_ONE = "http://api.t.163.com/statuses/show/%s.json";
		public static final String COMMENTS_TIMELINE = "http://api.t.163.com/statuses/comments_to_me.json";
		public static final String MENTIONS_TIMELINE = "http://api.t.163.com/statuses/mentions.json";
		public static final String DIRECT_MESSAGES_RECV = "http://api.t.163.com/direct_messages.json";
		public static final String DIRECT_MESSAGES_SENT = "http://api.t.163.com/direct_messages/sent.json";
		public static final String DIRECT_MESSAGES_NEW = "http://api.t.163.com/direct_messages/new.json";
		public static final String FORWARD = "http://api.t.163.com/statuses/retweet/%s.json";
		
		public static final String COMMENT = "http://api.t.163.com/statuses/reply.json";
		
		public static final String FAV_ADD = "http://api.t.163.com/favorites/create/%s.json";
		public static final String FAV_DEL = "http://api.t.163.com/favorites/destroy/%s.json";
		/**
		 * 用户时间线
		 */
		public static final String USER_TIMELINE = "http://api.t.163.com/statuses/user_timeline.json";
		/**
		 * 未读信息数
		 */
		public static final String UNREAD = "http://api.t.163.com/reminds/message/latest.json";
		
		public static final String SEARCH_STATUS = "http://api.t.163.com/1/statuses/search.json";
		public static final String SEARCH_USER = "http://api.t.163.com/1/users/search.json";
		
		public static final String FRIENDS_LIST = "http://api.t.163.com/statuses/friends.json";
		public static final String FOLLOWERS_LIST = "http://api.t.163.com/statuses/followers.json";
		
		public static final String ADD_FRIEND = "http://api.t.163.com/friendships/create.json";
		public static final String DEL_FRIEND = "http://api.t.163.com/friendships/destroy.json";
	}

	/**
	 * 搜狐微博相关数据
	 * @author Administrator
	 * 
	 */
	public static class Sohu {
		public static final String CONSUMER_KEY = "g6Gxwc4KSjtsd6i2ONu5";
		public static final String CONSUMER_SECRET = "GxOjSb(K)C(APH2$KR3H^xxXifX6g2UlaX4XpwB-";

		public static final String SOHU_TOKEN = "sohu_token";
		public static final String SOHU_TOKEN_SECRET = "sohu_token_secret";

		public static final String SEND_ONE = "http://api.t.sohu.com/statuses/update.json";
		public static final String GET_USERINFO = "http://api.t.sohu.com/users/show.json";
		public static final String GET_OTHERUSERINFO = "http://api.t.sohu.com/users/show/%s.json";
		public static final String HOME_TIMELINE = "http://api.t.sohu.com/statuses/friends_timeline.json";
		public static final String GET_ONE = "http://api.t.sohu.com/statuses/show/%s.json";
		public static final String COMMENTS_TIMELINE = "http://api.t.sohu.com/statuses/comments_timeline.json";
		public static final String MENTIONS_TIMELINE = "http://api.t.sohu.com/statuses/mentions_timeline.json";
		public static final String DIRECT_MESSAGES_RECV = "http://api.t.sohu.com/direct_messages.json";
		public static final String DIRECT_MESSAGES_SENT = "http://api.t.sohu.com/direct_messages/sent.json";
		public static final String DIRECT_MESSAGES_NEW = "http://api.t.sohu.com/direct_messages/new.json";
		public static final String FORWARD = "http://api.t.sohu.com/statuses/transmit/%s.json";
		
		public static final String COMMENT = "http://api.t.sohu.com/statuses/comment.json";
		
		public static final String FAV_ADD = "http://api.t.sohu.com/favourites/create/%s.json";
		public static final String FAV_DEL = "http://api.t.sohu.com/favourites/destroy/%s.json";
		
		/**
		 * 用户时间线
		 */
		public static final String USER_TIMELINE = "http://api.t.sohu.com/statuses/user_timeline/%s.json";
		/**
		 * 未读信息数
		 */
		public static final String UNREAD = "http://api.t.sohu.com/statuses/check.json";
		public static final String COUNTS = "http://api.t.sohu.com/statuses/counts.json";
		
		public static final String SEARCH_STATUS = "http://api.t.sohu.com/statuses/search.json";
		public static final String SEARCH_USER = "http://api.t.sohu.com/users/search.json";
		
		public static final String FOLLOWERS_LIST = "http://api.t.sohu.com/statuses/followers/%s.json";
		public static final String FRIENDS_LIST = "http://api.t.sohu.com/statuses/friends/%s.json";
		
		public static final String ADD_FRIEND = "http://api.t.sohu.com/friendships/create/%s.json";
		public static final String DEL_FRIEND = "http://api.t.sohu.com/friendships/destroy/%s.json";
	}

	public static class Tianya {
		public static final String CONSUMER_KEY = "eb53dc9b4f899ed9ddaf2b56a08f1edb04f1247e6";
		public static final String CONSUMER_SECRET = "d6c591ee4502d1dbbdcfbb434cb3148d";

		public static final String TIANYA_TOKEN = "tianya_token";
		public static final String TIANYA_TOKEN_SECRET = "tianya_token_secret";

		public static final String SEND_ONE = "http://open.tianya.cn/api/weibo/add.php";
	}

	public static final String CALL_BACK_SCHEMA = "anyweibo-oauth";
	public static final String CALL_BACK_HOST = "callback";
	/**
	 * 回调地址,用于在OAuth中回调
	 */
	public static final String CALL_BACK_URL = CALL_BACK_SCHEMA + "://"
			+ CALL_BACK_HOST;
	public static final String SP = "sp";
	public static final int SP_TENCENT = 1;
	public static final int SP_SINA = 2;
	public static final int SP_NETEASE = 3;
	public static final int SP_SOHU = 4;
	public static final int SP_TIANYA = 5;

	public static final String DEFAULT_MSG = "(来自Any微博)";
	//1-原创发表，2-转载，3-私信，4-回复，5-空回，6-提及，7-评论
	public static final int TYPE_SELF = 1;
	public static final int TYPE_FORWARD = 2;
	public static final int TYPE_PRIVATE = 3;
	public static final int TYPE_REPLY = 4;
	public static final int TYPE_NULL_REPLY = 5;
	public static final int TYPE_MENTIONS = 6;
	public static final int TYPE_COMMENT = 7;
	//私信 收件箱、发件箱
	public static final int DIRECTMESSAGE_RECV = 1;
	public static final int DIRECTMESSAGE_SENT = 2;
	
	//转播类型,用于跨服务商转播时识别
	public static final int FORWARD_TYPE_F = 1;//转发
	public static final int FORWARD_TYPE_N = 2;//发送
	
	//评论类型，回复、评论
	public static final int COMMENT_TYPE_REPLY = 1;//回复
	public static final int COMMENT_TYPE_COMMENT = 2;//点评
	
	//收藏处理类型 增加、删除
	public static final int FAV_TYPE_ADD = 1;
	public static final int FAV_TYPE_DEL = 2;
	
	//quickbar
	public static final int Quickbar_Forward = 0x101;
	public static final int Quickbar_Comment = 0x102;
	public static final int Quickbar_Fav = 0x103;
	public static final int Quickbar_Profile = 0x104;
	public static final int Quickbar_Other = 0x105;
	
}
