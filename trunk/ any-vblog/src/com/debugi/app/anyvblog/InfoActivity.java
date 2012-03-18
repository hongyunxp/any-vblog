package com.debugi.app.anyvblog;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.URLUtil;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.debugi.app.anyvblog.asynctask.SendAsyncTask;
import com.debugi.app.anyvblog.exception.NormalException;
import com.debugi.app.anyvblog.exception.ResponseException;
import com.debugi.app.anyvblog.model.SendData;
import com.debugi.app.anyvblog.model.UserAdapter;
import com.debugi.app.anyvblog.model.netease.SendGet_UserInfo4Netease;
import com.debugi.app.anyvblog.model.netease.UserInfo4Netease;
import com.debugi.app.anyvblog.model.sina.SendGet_UserInfo4Sina;
import com.debugi.app.anyvblog.model.sina.UserInfo4Sina;
import com.debugi.app.anyvblog.model.sohu.SendGet_UserInfo4Sohu;
import com.debugi.app.anyvblog.model.sohu.UserInfo4Sohu;
import com.debugi.app.anyvblog.model.tencent.GetResponse4Tencent;
import com.debugi.app.anyvblog.model.tencent.GetUserInfo4Tencent;
import com.debugi.app.anyvblog.model.tencent.SendGet_UserInfo4Tencent;
import com.debugi.app.anyvblog.utils.Config;
import com.debugi.app.anyvblog.utils.Constants;
import com.debugi.app.anyvblog.utils.MyHandler;
import com.debugi.app.anyvblog.utils.TextUtils;
import com.debugi.app.anyvblog.widget.AsyncImageView;
import com.debugi.open.oauth.model.HttpType;
import com.debugi.open.oauth.model.Response;
import com.google.gson.Gson;

public class InfoActivity extends Activity implements OnClickListener {
	private static final String TAG = "InfoActivity";
	final Activity activity = this;
	private UserAdapter user;
	private AsyncTask<Void, Void, Response> result;
	private Handler handler;
	private Response response;
	private TextView user_nick;
	private AsyncImageView user_head;
	private TextView user_name;
	private TextView user_sex;
	private TextView user_location;
	private TextView count_followers;
	private TextView count_friends;
	private TextView count_statuses;
	private TextView user_description;
	private LinearLayout last_status;
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
		setContentView(R.layout.info);
		initViews();
		user = Config.currentUser;
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
							Toast.makeText(activity, tip, Toast.LENGTH_SHORT)
									.show();
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
						if (res == null || res.getCode() != 200) {
							if (res == null) {
								return;
							}
							String tip = new ResponseException(res,
									Config.currentUser.getSp()).getMessage();
							Toast.makeText(activity, tip, Toast.LENGTH_SHORT)
									.show();
							return;
						}
						parseData(res);
					} catch (Exception e) {
						throw new NormalException(e.getMessage());
					}
					break;
				default:
					break;
				}
			}
		};

		getUserInfo();
	}

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
	}

	private void initViews() {
		user_nick = (TextView) findViewById(R.id.user_nick);
		user_head = (AsyncImageView) findViewById(R.id.user_head);
		user_head.setImageResource(R.drawable.head_default);

		user_name = (TextView) findViewById(R.id.user_name);
		user_sex = (TextView) findViewById(R.id.user_sex);
		user_location = (TextView) findViewById(R.id.user_location);
		count_followers = (TextView) findViewById(R.id.count_followers);
		count_friends = (TextView) findViewById(R.id.count_friends);
		count_statuses = (TextView) findViewById(R.id.count_statuses);
		user_description = (TextView) findViewById(R.id.user_description);
		last_status = (LinearLayout) findViewById(R.id.last_status);

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

	/**
	 * 展示数据
	 */
	private void fillData() {
		Gson gson = new Gson();
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
					Config.cachePath + "head_" + user.getUser_id() + ".jpg");
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
			sendData = new SendGet_UserInfo4Tencent(user.getUser_id());
			url = Constants.Tencent.GET_USERINFO;
			break;
		case Constants.SP_SINA:
			sendData = new SendGet_UserInfo4Sina(user.getUser_id());
			url = Constants.Sina.GET_USERINFO;
			break;
		case Constants.SP_NETEASE:
			sendData = new SendGet_UserInfo4Netease(user.getUser_id());
			url = Constants.NetEase.GET_USERINFO;
			break;
		case Constants.SP_SOHU:
			sendData = new SendGet_UserInfo4Sohu(user.getUser_id());
			url = String.format(Constants.Sohu.GET_OTHERUSERINFO,
					user.getUser_id());
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
		default:
			break;
		}

	}

	@Override
	protected void onResume() {
		if (!user.getUser_id().equals(Config.currentUser.getUser_id())) {
			user = Config.currentUser;
			getUserInfo();
		}
		super.onResume();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Config.debug(TAG, "click back catch on info");
			if (MyHandler.handler != null) {
				Message message = new Message();
				message.what = MyHandler.Handler_Back;
				MyHandler.handler.sendMessage(message);
			}
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}
}
