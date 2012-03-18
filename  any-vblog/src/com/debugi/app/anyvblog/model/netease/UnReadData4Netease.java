package com.debugi.app.anyvblog.model.netease;

import com.debugi.app.anyvblog.model.UnReadAdapter;

public class UnReadData4Netease {
	/**
	 * 200 {"userId":"-539014509716384405","replyCount":"0","timelineCount":"1","followedCount":"0","directMessageCount":"0","retweetOfMeCount":"0","commentOfMeCount":"0"}
	 */
	private String userId;// 当前登录用户id
	private String replyCount;// 新@回复数量
	private String followedCount;// 新被关注数量
	private String directMessageCount;// 新私信数量
	private String timelineCount;// 新home_timeline数量
	private String commentOfMeCount;// 新评论数量
	private String retweetOfMeCount;// 新转发我的数量
	public UnReadAdapter conver() {
		UnReadAdapter adapter = new UnReadAdapter();
		adapter.setComments(Integer.parseInt(commentOfMeCount));
		adapter.setDirectmessages(Integer.parseInt(directMessageCount));
		adapter.setFollowers(Integer.parseInt(followedCount));
		adapter.setMentions(Integer.parseInt(replyCount));
		adapter.setStatus(Integer.parseInt(timelineCount));
		return adapter;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(String replyCount) {
		this.replyCount = replyCount;
	}
	public String getFollowedCount() {
		return followedCount;
	}
	public void setFollowedCount(String followedCount) {
		this.followedCount = followedCount;
	}
	public String getDirectMessageCount() {
		return directMessageCount;
	}
	public void setDirectMessageCount(String directMessageCount) {
		this.directMessageCount = directMessageCount;
	}
	public String getTimelineCount() {
		return timelineCount;
	}
	public void setTimelineCount(String timelineCount) {
		this.timelineCount = timelineCount;
	}
	public String getCommentOfMeCount() {
		return commentOfMeCount;
	}
	public void setCommentOfMeCount(String commentOfMeCount) {
		this.commentOfMeCount = commentOfMeCount;
	}
	public String getRetweetOfMeCount() {
		return retweetOfMeCount;
	}
	public void setRetweetOfMeCount(String retweetOfMeCount) {
		this.retweetOfMeCount = retweetOfMeCount;
	}
}
