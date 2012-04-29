package com.ilovn.app.anyvblog;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ilovn.app.anyvblog.application.ActivityStack;
import com.ilovn.app.anyvblog.asynctask.SendAsyncTask;
import com.ilovn.app.anyvblog.asynctask.SendNoUpdateAsyncTask;
import com.ilovn.app.anyvblog.exception.NormalException;
import com.ilovn.app.anyvblog.exception.ResponseException;
import com.ilovn.app.anyvblog.model.SendData;
import com.ilovn.app.anyvblog.model.UserAdapter;
import com.ilovn.app.anyvblog.model.netease.SendFriend4Netease;
import com.ilovn.app.anyvblog.model.netease.SendGet_UserInfo4Netease;
import com.ilovn.app.anyvblog.model.netease.UserInfo4Netease;
import com.ilovn.app.anyvblog.model.sina.Friendships4Sina;
import com.ilovn.app.anyvblog.model.sina.SendGet_UserInfo4Sina;
import com.ilovn.app.anyvblog.model.sina.UserInfo4Sina;
import com.ilovn.app.anyvblog.model.sohu.SendGet_UserInfo4Sohu;
import com.ilovn.app.anyvblog.model.sohu.UserInfo4Sohu;
import com.ilovn.app.anyvblog.model.tencent.GetResponse4Tencent;
import com.ilovn.app.anyvblog.model.tencent.GetUserInfo4Tencent;
import com.ilovn.app.anyvblog.model.tencent.SendFriend4Tencent;
import com.ilovn.app.anyvblog.model.tencent.SendGet_UserInfo4Tencent;
import com.ilovn.app.anyvblog.oauth.OauthModel4Sina;
import com.ilovn.app.anyvblog.utils.Config;
import com.ilovn.app.anyvblog.utils.Constants;
import com.ilovn.app.anyvblog.utils.MyHandler;
import com.ilovn.app.anyvblog.utils.TextUtils;
import com.ilovn.app.anyvblog.widget.AsyncImageView;
import com.ilovn.open.oauth.OAuthServiceProvider;
import com.ilovn.open.oauth.model.HttpType;
import com.ilovn.open.oauth.model.OAuthRequest;
import com.ilovn.open.oauth.model.Response;
import com.ilovn.open.oauth.model.Token;

public class UserInfoActivity extends Activity implements OnClickListener {
	private static final String TAG = "UserInfoActivity";
	final Activity activity = this;
	private String user_id;
	private UserAdapter user;
	private AsyncTask<Void, Void, Response> result;
	private Handler handler;
	private Response response;
	private TextView user_nick;
	private AsyncImageView user_head;
	private TextView user_name;
	private TextView user_sex;
	private TextView user_location;
	private Button user_follow;
	private TextView user_follow_tips;
	private TextView count_followers;
	private TextView count_friends;
	private TextView count_statuses;
	private TextView user_description;
	private LinearLayout last_status;
	private LinearLayout btn_mid;
	private Button btn_back;
	private Button btn_home;
	private Button btn_at;
	private Button btn_directmessage;
	private Button btn_black;
	// one
	private TextView one_status;
	private AsyncImageView one_image;
	private RelativeLayout source_one;
	private TextView source_one_nick;
	private TextView source_one_status;
	private AsyncImageView source_one_image;
	private TextView source_one_from;
	private TextView one_from;
	private TextView one_time;
	private LinearLayout l_count_followers;
	private LinearLayout l_count_friends;
	private LinearLayout l_count_status;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userinfo);
		ActivityStack.getInstance().addActivity(activity);
		initViews();
		user_id = getUserId(getIntent());
		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case MyHandler.Handler_GetData:
					try {
						response = result.get();
						if (response == null || response.getCode() != 200) {
							if (response == null) {
								return;
							}
							String tip = new ResponseException(response,
									Config.currentUser.getSp()).getMessage();
							Config.toast(activity, tip, Toast.LENGTH_SHORT);
							return;
						}
					} catch (Exception e) {
						throw new NormalException(e.getMessage());
					}
					fillData();
					break;
				case MyHandler.Handler_GetData_Without_Update:
					try {
						Response res = result.get();
						Config.debug(TAG, res.getBody());
						if (res == null || res.getCode() != 200) {
							if (res != null) {
								String tip = new ResponseException(res,
										Config.currentUser.getSp())
										.getMessage();
								Config.toast(activity, tip, Toast.LENGTH_SHORT);
							}
							return;
						}
						parseData(res);
					} catch (Exception e) {
						throw new NormalException(e.getMessage());
					}
					break;
				case 0x1001:
					setFriendship();
					break;
				default:
					break;
				}
			}
		};

		getUserInfo();
	}

	/**
	 * 收听/取消收听
	 * 
	 * @param res
	 */
	private void parseData(Response res) {
		boolean successed = false;
		Config.debug(TAG, res.getBody());

		try {
			Gson gson = new Gson();
			switch (Config.currentUser.getSp()) {
			case Constants.SP_TENCENT:
				GetResponse4Tencent response4Tencent = gson.fromJson(
						res.getBody(), GetResponse4Tencent.class);
				if (response4Tencent.getRet() == 0) {
					Config.debug(TAG, "成功!");
					successed = true;
				}
				break;
			case Constants.SP_SINA:
				UserInfo4Sina info4Sina = gson.fromJson(res.getBody(),
						UserInfo4Sina.class);
				if (info4Sina != null && info4Sina.getId() != null) {
					Config.debug(TAG, "成功!");
					successed = true;
				}
				break;
			case Constants.SP_NETEASE:
				UserInfo4Netease info4Netease = gson.fromJson(res.getBody(),
						UserInfo4Netease.class);
				if (info4Netease != null && info4Netease.getId() != null) {
					Config.debug(TAG, "成功!");
					successed = true;
				}
				break;
			case Constants.SP_SOHU:
				UserInfo4Sohu info4Sohu = gson.fromJson(res.getBody(),
						UserInfo4Sohu.class);
				if (info4Sohu != null && info4Sohu.getId() != null) {
					Config.debug(TAG, "成功!");
					successed = true;
				}
				break;
			default:
				break;
			}
		} catch (Exception e) {
			throw new NormalException(e.getMessage());
		}
		if (successed) {
			getUserInfo();
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		Config.debug(TAG, "on newIntent");
		user_id = getUserId(intent);
		getUserInfo();
	}

	private void initViews() {
		user_nick = (TextView) findViewById(R.id.user_nick);
		user_head = (AsyncImageView) findViewById(R.id.user_head);
		user_head.setImageResource(R.drawable.head_default);

		user_name = (TextView) findViewById(R.id.user_name);
		user_sex = (TextView) findViewById(R.id.user_sex);
		user_location = (TextView) findViewById(R.id.user_location);
		user_follow = (Button) findViewById(R.id.user_follow);
		user_follow_tips = (TextView) findViewById(R.id.user_follow_tips);
		count_followers = (TextView) findViewById(R.id.count_followers);
		count_friends = (TextView) findViewById(R.id.count_friends);
		count_statuses = (TextView) findViewById(R.id.count_statuses);
		user_description = (TextView) findViewById(R.id.user_description);
		last_status = (LinearLayout) findViewById(R.id.last_status);
		btn_mid = (LinearLayout) findViewById(R.id.btn_mid);
		btn_at = (Button) findViewById(R.id.btn_at);
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_black = (Button) findViewById(R.id.btn_black);
		btn_directmessage = (Button) findViewById(R.id.btn_directmessage);
		btn_home = (Button) findViewById(R.id.btn_home);
		btn_at.setOnClickListener(this);
		btn_back.setOnClickListener(this);
		btn_black.setOnClickListener(this);
		btn_directmessage.setOnClickListener(this);
		btn_home.setOnClickListener(this);
		user_follow.setOnClickListener(this);

		l_count_followers = (LinearLayout) findViewById(R.id.l_count_followers);
		l_count_friends = (LinearLayout) findViewById(R.id.l_count_friends);
		l_count_status = (LinearLayout) findViewById(R.id.l_count_status);

		// one
		one_status = (TextView) findViewById(R.id.one_status);
		one_image = (AsyncImageView) findViewById(R.id.one_image);
		source_one = (RelativeLayout) findViewById(R.id.source_one);
		source_one_nick = (TextView) findViewById(R.id.source_one_nick);
		source_one_status = (TextView) findViewById(R.id.source_one_status);
		source_one_image = (AsyncImageView) findViewById(R.id.source_one_image);
		source_one_from = (TextView) findViewById(R.id.source_one_from);
		one_from = (TextView) findViewById(R.id.one_from);
		one_time = (TextView) findViewById(R.id.one_time);

		user_description.setText("正在获取数据……");

		// 注册监听器
		l_count_followers.setOnClickListener(this);
		l_count_friends.setOnClickListener(this);
		l_count_status.setOnClickListener(this);
	}

	private String getUserId(Intent intent) {
		if (intent.getExtras().containsKey("user_id")) {
			return intent.getExtras().getString("user_id");
		} else {
			return null;
		}
	}

	/**
	 * 展示数据
	 */
	private void fillData() {
		if (response == null || response.getCode() != 200) {
			if (response == null) {
				return;
			}
			String tip = new ResponseException(response,
					Config.currentUser.getSp()).getMessage();
			Config.toast(activity, tip, Toast.LENGTH_SHORT);
			return;
		}
		final Gson gson = new Gson();
		try {
			Config.debug(TAG, response.getBody());
			switch (Config.currentUser.getSp()) {
			case Constants.SP_TENCENT:
				GetUserInfo4Tencent info4Tencent = gson.fromJson(
						response.getBody(), GetUserInfo4Tencent.class);
				if (info4Tencent.getRet() == 0) {
					user = info4Tencent.getData().conver2User();
				}
				break;
			case Constants.SP_SINA:
				UserInfo4Sina info4Sina = gson.fromJson(response.getBody(),
						UserInfo4Sina.class);
				user = info4Sina.conver2User();
				break;
			case Constants.SP_NETEASE:
				UserInfo4Netease info4Netease = gson.fromJson(
						response.getBody(), UserInfo4Netease.class);
				user = info4Netease.conver2User();
				break;
			case Constants.SP_SOHU:
				UserInfo4Sohu info4Sohu = gson.fromJson(response.getBody(),
						UserInfo4Sohu.class);
				user = info4Sohu.conver2User();
				break;
			default:
				break;
			}
		} catch (Exception e) {
			throw new NormalException(e.getMessage());
		}
		user_nick.setText(user.getNick());
		if (user.getName() != null && !"".equals(user.getName())) {
			user_name.setText("@" + user.getName());
		}
		user_sex.setText(user.getSex());
		user_location.setText("所在地" + user.getLocation());

		if (user.getDescription() != null && !"".equals(user.getDescription())) {
			user_description.setText(user.getDescription());
		} else {
			user_description.setText("这家伙很懒，什么都没留下");
		}
		count_followers.setText(user.getCount_followers() + "");
		count_friends.setText(user.getCount_friends() + "");
		count_statuses.setText(user.getCount_status() + "");
		// 判断是否显示中间3个按钮
		if (user.getUser_id().equals(Config.currentUser.getUser_id())) {
			btn_mid.setVisibility(View.GONE);
			user_follow.setVisibility(View.GONE);
			user_follow_tips.setVisibility(View.GONE);
		} else {
			btn_mid.setVisibility(View.VISIBLE);
			user_follow.setVisibility(View.VISIBLE);
			user_follow_tips.setVisibility(View.VISIBLE);
		}
		if (user.getSp() != Constants.SP_SINA) {
			setFriendship();
		} else {
			user_follow.setText("正在获取收听详情……");
			user_follow_tips.setText("");
			new Thread(new Runnable() {

				@Override
				public void run() {
					OAuthRequest request = new OAuthRequest(HttpType.GET,
							Constants.Sina.FRIENDSHIPS);
					request.addQuerystringParameter("target_id", user_id);
					OAuthServiceProvider serviceProvider = new OauthModel4Sina()
							.getServiceProvider();
					Token accessToken = Config.currentUser.getAccessToken();
					serviceProvider.signRequest(accessToken, request);
					Response res = request.send();
					if (res == null || res.getCode() != 200) {
						if (res != null) {
							String tip = new ResponseException(res,
									Config.currentUser.getSp()).getMessage();
							Config.toast(activity, tip, Toast.LENGTH_SHORT);
						}
						return;
					}
					Config.debug(TAG, res.getBody());
					Friendships4Sina friendships4Sina = gson.fromJson(
							res.getBody(), Friendships4Sina.class);
					user.setFollower(friendships4Sina.getTarget().isFollowing());
					user.setFriend(friendships4Sina.getTarget().isFollowed_by());
					Message message = new Message();
					message.what = 0x1001;
					handler.sendMessage(message);
				}
			}).start();
		}

		// 检查是否有最近广播
		if (user.getStatus() != null) {
			last_status.setVisibility(View.VISIBLE);
			one_status.setText(TextUtils.parse(user.getStatus().getStatus(),
					activity));
			if (user.getStatus().getImage_s() != null) {
				one_image.setVisibility(View.VISIBLE);
				one_image.asyncLoadBitmapFromUrl(user.getStatus().getImage_s(),
						Config.cachePath + "s_"
								+ user.getStatus().getStatus_id() + ".jpg");
			} else {
				one_image.setVisibility(View.GONE);
			}
			if (user.getStatus().isSource()) {
				source_one.setVisibility(View.VISIBLE);
				source_one_from.setText(user.getStatus().getSource_from());
				source_one_nick.setText(user.getStatus().getSource_nick());
				source_one_status.setText(TextUtils.parse(user.getStatus()
						.getSource_status(), activity));
				if (user.getStatus().getSource_image_s() != null) {
					source_one_image.setVisibility(View.VISIBLE);
					source_one_image.asyncLoadBitmapFromUrl(user.getStatus()
							.getSource_image_s(), Config.cachePath + "s_"
							+ user.getStatus().getStatus_id() + ".jpg");
				} else {
					source_one_image.setVisibility(View.GONE);
				}
			} else {
				source_one.setVisibility(View.GONE);
			}
			one_from.setText(user.getStatus().getFrom());
			one_time.setText(user.getStatus().getTime());
		} else {
			last_status.setVisibility(View.GONE);
		}

		if (user.getHead_url() != null
				&& URLUtil.isNetworkUrl(user.getHead_url())) {
			user_head.asyncLoadBitmapFromUrl(user.getHead_url(),
					Config.cachePath + "head_" + user_id + ".jpg");
		}
	}

	private void setFriendship() {
		user_follow.setVisibility(View.VISIBLE);
		user_follow_tips.setVisibility(View.VISIBLE);
		if (user.isFollower() && user.isFriend()) {
			user_follow_tips.setText("已相互收听");
		} else if (user.isFollower() && !user.isFriend()) {
			user_follow_tips.setText("已收听我");
		} else if (user.isFriend() && !user.isFollower()) {
			user_follow_tips.setText("我已收听");
		} else {
			user_follow_tips.setText("");
		}
		if (user.isFriend()) {
			user_follow.setText("取消收听");
		} else {
			user_follow.setText("收听");
		}
	}

	/**
	 * 获取用户信息
	 */
	private void getUserInfo() {
		SendData sendData = null;
		String url = null;
		Config.debug(TAG, "正在为获取用户信息装配数据");
		switch (Config.currentUser.getSp()) {
		case Constants.SP_TENCENT:
			sendData = new SendGet_UserInfo4Tencent(user_id);
			url = Constants.Tencent.GET_OTHERUSERINFO;
			break;
		case Constants.SP_SINA:
			sendData = new SendGet_UserInfo4Sina(user_id);
			url = Constants.Sina.GET_USERINFO;
			break;
		case Constants.SP_NETEASE:
			sendData = new SendGet_UserInfo4Netease(user_id);
			url = Constants.NetEase.GET_USERINFO;
			break;
		case Constants.SP_SOHU:
			sendData = new SendGet_UserInfo4Sohu(user_id);
			url = String.format(Constants.Sohu.GET_OTHERUSERINFO, user_id);
			break;
		default:
			break;
		}
		result = new SendAsyncTask(activity, sendData, url, HttpType.GET,
				Config.currentUser, handler).execute();
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.btn_back:
			activity.finish();
			break;
		case R.id.btn_home:
			intent = new Intent(activity, MainActivity.class);
			activity.startActivity(intent);
			activity.finish();
			break;
		case R.id.l_count_followers:
			intent = new Intent(activity, FollowersActivity.class);
			intent.putExtra("user_id", user.getUser_id());
			intent.putExtra("nick", user.getNick());
			activity.startActivity(intent);
			break;
		case R.id.l_count_friends:
			intent = new Intent(activity, FriendsActivity.class);
			intent.putExtra("user_id", user.getUser_id());
			intent.putExtra("nick", user.getNick());
			activity.startActivity(intent);
			break;
		case R.id.l_count_status:
			intent = new Intent(activity, User_TimelineActivity.class);
			intent.putExtra("nick", user.getNick());
			intent.putExtra("user_id", user.getUser_id());
			activity.startActivity(intent);
			break;
		case R.id.btn_at:
			intent = new Intent(activity, WriteActivity.class);
			intent.putExtra("at", "@" + user_id);
			activity.startActivity(intent);
			break;
		case R.id.user_follow:
			user_follow_tips.setText("正在发送请求……");
			friendOperator();
			break;
		default:
			break;
		}

	}

	private void friendOperator() {
		String url = null;
		SendData sendData = null;
		if (user.isFriend()) {
			// 已经关注,取消关注
			switch (Config.currentUser.getSp()) {
			case Constants.SP_TENCENT:
				sendData = new SendFriend4Tencent(user.getUser_id());
				url = Constants.Tencent.DEL_FRIEND;
				break;
			case Constants.SP_SINA:
				url = String.format(Constants.Sina.DEL_FRIEND,
						user.getUser_id());
				break;
			case Constants.SP_NETEASE:
				sendData = new SendFriend4Netease(user.getUser_id());
				url = Constants.NetEase.DEL_FRIEND;
				break;
			case Constants.SP_SOHU:
				url = String.format(Constants.Sohu.DEL_FRIEND,
						user.getUser_id());
				break;
			default:
				break;
			}
		} else {
			switch (Config.currentUser.getSp()) {
			case Constants.SP_TENCENT:
				sendData = new SendFriend4Tencent(user.getUser_id());
				url = Constants.Tencent.ADD_FRIEND;
				break;
			case Constants.SP_SINA:
				url = String.format(Constants.Sina.ADD_FRIEND,
						user.getUser_id());
				break;
			case Constants.SP_NETEASE:
				sendData = new SendFriend4Netease(user.getUser_id());
				url = Constants.NetEase.ADD_FRIEND;
				break;
			case Constants.SP_SOHU:
				url = String.format(Constants.Sohu.ADD_FRIEND,
						user.getUser_id());
				break;
			default:
				break;
			}
		}
		result = new SendNoUpdateAsyncTask(activity, sendData, url,
				HttpType.POST, Config.currentUser, handler).execute();
	}
}
