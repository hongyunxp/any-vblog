package com.ilovn.app.anyvblog;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ilovn.app.anyvblog.adapter.UsersAdapter;
import com.ilovn.app.anyvblog.application.ActivityStack;
import com.ilovn.app.anyvblog.asynctask.SendAsyncTask;
import com.ilovn.app.anyvblog.exception.NormalException;
import com.ilovn.app.anyvblog.exception.ResponseException;
import com.ilovn.app.anyvblog.model.SendData;
import com.ilovn.app.anyvblog.model.UserAdapter;
import com.ilovn.app.anyvblog.model.netease.GetUsers4Netease;
import com.ilovn.app.anyvblog.model.netease.SendUsers4Netease;
import com.ilovn.app.anyvblog.model.netease.UserInfo4Netease;
import com.ilovn.app.anyvblog.model.sina.GetUsers4Sina;
import com.ilovn.app.anyvblog.model.sina.SendUsers4Sina;
import com.ilovn.app.anyvblog.model.sina.UserInfo4Sina;
import com.ilovn.app.anyvblog.model.sohu.GetUsers4Sohu;
import com.ilovn.app.anyvblog.model.sohu.SendUsers4Sohu;
import com.ilovn.app.anyvblog.model.sohu.UserInfo4Sohu;
import com.ilovn.app.anyvblog.model.tencent.GetUserInfos4Tencent;
import com.ilovn.app.anyvblog.model.tencent.SendFollowers4Tencent;
import com.ilovn.app.anyvblog.model.tencent.UserInfo4Tencent;
import com.ilovn.app.anyvblog.utils.Config;
import com.ilovn.app.anyvblog.utils.Constants;
import com.ilovn.app.anyvblog.utils.MyHandler;
import com.ilovn.app.anyvblog.utils.TimeUtils;
import com.ilovn.app.anyvblog.widget.CustomListView;
import com.ilovn.app.anyvblog.widget.CustomListView.OnClickFooterListener;
import com.ilovn.app.anyvblog.widget.CustomListView.OnRefreshListener;
import com.ilovn.open.oauth.model.HttpType;
import com.ilovn.open.oauth.model.Response;

public class FollowersActivity extends Activity implements OnClickListener {
	/** Called when the activity is first created. */
	private static final String TAG = "FollowersActivity";
	private TextView user_nick;
	private Button refresh;
	private Button write;
	private CustomListView users_list;
	private final Activity activity = this;
	private SendData sendData;
	private Handler handler;
	private Animation animation;
	private UsersAdapter adapter;
	private AsyncTask<Void, Void, Response> asyncTask = null;
	private List<UserAdapter> datas;
	private String curUserNick;
	private String user_id;
	private String url;
	private UserAdapter curUser;
	// 类别，区分请求最新或者获取更多
	private int type;
	// 返回顶部按钮
	private Button back_top;
	// 是否载入完毕
	private boolean loaded;
	private FrameLayout loading;
	
	private Button btn_back;
	private Button btn_home;
	private int count = 20;// 请求的记录数
	private int curPage;
	private int nextCursor;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_timeline);
		ActivityStack.getInstance().addActivity(activity);
		initViews();

		getDataFromIntent(getIntent());
		curUser = Config.currentUser;

		loaded = false;
		type = 0;
		curPage = 1;
		nextCursor = -1;

		if (!loaded) {
			showProgress();
		} else {
			hiddenProgress();
		}

		if (Config.currentUser == null) {
			Config.currentUser = UserAdapter.getDefaultUser(activity);
		}

		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0x001:
					users_list.onRefreshComplete("上次更新:"
							+ TimeUtils.coverTimeStamp(Config.currentUser
									.getLast_update()));
					// 清除动画
					refresh.clearAnimation();
					break;
				case MyHandler.Handler_GetData:
					if (asyncTask == null) {
						Config.debug(TAG, "null-----");
						break;
					}
					try {
						Response response = asyncTask.get();
						if (response == null || response.getCode() != 200) {
							if (response == null) {
								return;
							}
							String tip = new ResponseException(response,
									curUser.getSp()).getMessage();
							Config.toast(activity, tip, Toast.LENGTH_SHORT);
							return;
						}
						if (type == 0) {
							// 准备填充数据
							datas = parseData(response);
							fillData(datas);
							users_list.onClickComplete(R.string.footer_normal_tips);
						} else {
							List<UserAdapter> addDatas = parseData(response);
							// adapter.addDatas(addDatas);
							datas.addAll(addDatas);
							adapter.notifyDataSetChanged();
							users_list.onClickComplete(R.string.footer_normal_tips);
							if (addDatas.size() <= 0) {
								users_list.onClickComplete(R.string.footer_no_more_data);
							}
						}
						loaded = true;
						curPage++;
						hiddenProgress();
					} catch (Exception e) {
						new NormalException("", e);
					}
					break;
				default:
					break;
				}
				super.handleMessage(msg);
			}

		};
		sendRequest();
		/**
		 * 更新完成
		 */
		users_list
				.onRefreshComplete("上次更新:"
						+ TimeUtils.coverTimeStamp(Config.currentUser
								.getLast_update()));

		/**
		 * 刷新监听器
		 */
		users_list.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				type = 0;
				curPage = 1;
				sendRequest();
				animation = AnimationUtils.loadAnimation(activity,
						R.anim.rotate);
				refresh.startAnimation(animation);
			}
		});
		/**
		 * list点击监听
		 */
		users_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(activity, UserInfoActivity.class);
				intent.putExtra("user_id", view.getTag().toString());
				activity.startActivity(intent);
			}
		});

	}

	/**
	 * 从Intent中获取必须数据
	 * 
	 * @param intent
	 */
	private void getDataFromIntent(Intent intent) {
		if (intent.getExtras().containsKey("user_id")) {
			user_id = intent.getExtras().getString("user_id");
		}
		if (intent.getExtras().containsKey("nick")) {
			curUserNick = intent.getExtras().getString("nick");
		} else {
			curUserNick = "null";
		}
		user_nick.setText(curUserNick + " 的粉丝");
	}

	private void sendRequest() {
		System.out.println(getNext());
		sendData = createSendData();
		asyncTask = new SendAsyncTask(activity, sendData, url, HttpType.GET,
				Config.currentUser, handler).execute();
		showProgress();
	}

	/**
	 * 初始化
	 */
	private void initViews() {
		user_nick = (TextView) findViewById(R.id.account_nick);
		users_list = (CustomListView) findViewById(R.id.home_timeline);
		refresh = (Button) findViewById(R.id.refresh);
		write = (Button) findViewById(R.id.write);
		refresh.setOnClickListener(this);
		write.setOnClickListener(this);
		loading = (FrameLayout) findViewById(R.id.loading);		
		back_top = (Button) findViewById(R.id.back_top);
		back_top.setOnClickListener(this);
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_home = (Button) findViewById(R.id.btn_home);
		btn_back.setOnClickListener(this);
		btn_home.setOnClickListener(this);
		
		users_list.setOnClickFooterListener(new OnClickFooterListener() {
			
			@Override
			public void onClick() {
				// 请求更多数据
				Config.debug(TAG, "on click footer");
				if (nextCursor == 0) {
					users_list.onClickComplete(R.string.footer_no_more_data);
					return;
				}
				type = 1;
				sendRequest();
			}
		});
	}

	/**
	 * 处理数据显示
	 * 
	 * @param datas
	 */
	private void fillData(List<UserAdapter> datas) {
		// 创建adapter
		adapter = new UsersAdapter(activity, datas, R.layout.users_item);
		// 对listview设置adapter
		users_list.setAdapter(adapter);
		// listview的显示动画效果
		LayoutAnimationController animationController = new LayoutAnimationController(
				AnimationUtils.loadAnimation(activity, R.anim.alpha), 0.1f);
		animationController.setOrder(LayoutAnimationController.ORDER_NORMAL);
		users_list.setLayoutAnimation(animationController);
		Message message = new Message();
		message.what = 0x001;
		handler.sendMessage(message);
	}

	/**
	 * 解析服务器响应
	 * 
	 * @param response
	 * @return
	 */
	private List<UserAdapter> parseData(Response response) {
		List<UserAdapter> datas = new ArrayList<UserAdapter>();
		if (response == null || response.getCode() != 200) {
			if (response == null) {
				return datas;
			}
			String tip = new ResponseException(response,
					curUser.getSp()).getMessage();
			Config.toast(activity, tip, Toast.LENGTH_SHORT);
			return datas;
		}
		if (response.getBody().length() <= 2) {
			return datas;
		}
		if (response.getCode() == 200) {
			Config.debug(TAG, response.getBody());
			try {
				Gson gson = new Gson();
				switch (Config.currentUser.getSp()) {
				case Constants.SP_TENCENT:
					GetUserInfos4Tencent infos4Tencent = gson.fromJson(
							response.getBody(), GetUserInfos4Tencent.class);
					// 若返回值ret为0，则解析到适配
					if (infos4Tencent != null
							&& infos4Tencent.getData().getHasnext() == 0
							&& infos4Tencent.getRet() == 0
							&& infos4Tencent.getData().getInfo().size() > 0) {
						for (UserInfo4Tencent user : infos4Tencent.getData()
								.getInfo()) {
							datas.add(user.conver2User());
						}
					}
					if (infos4Tencent.getData().getHasnext() == 1) {
						nextCursor = 0;
					}

					break;
				case Constants.SP_SINA:
					GetUsers4Sina users4Sina = gson.fromJson(
							response.getBody(), GetUsers4Sina.class);
					if (users4Sina != null && users4Sina.getUsers().size() > 0) {
						for (UserInfo4Sina user : users4Sina.getUsers()) {
							datas.add(user.conver2User());
						}
						nextCursor = users4Sina.getNext_cursor();
					}
					break;
				case Constants.SP_NETEASE:
					GetUsers4Netease users4Netease = gson.fromJson(
							response.getBody(), GetUsers4Netease.class);
					if (users4Netease != null
							&& users4Netease.getUsers().size() > 0) {
						for (UserInfo4Netease user : users4Netease.getUsers()) {
							datas.add(user.conver2User());
						}
						nextCursor = users4Netease.getNext_cursor();
						if (nextCursor == -1) {
							nextCursor = 0;
						}
					}
					break;
				case Constants.SP_SOHU:
					GetUsers4Sohu users4Sohu = gson.fromJson(
							response.getBody(), GetUsers4Sohu.class);
					if (users4Sohu != null && users4Sohu.getUsers().size() > 0) {
						for (UserInfo4Sohu user : users4Sohu.getUsers()) {
							datas.add(user.conver2User());
						}
						nextCursor = users4Sohu.getCursor_id();
					}
					break;
				default:
					break;
				}
			} catch (Exception e) {
				// do nothing
			}
		}
		return datas;
	}

	@Override
	protected void onNewIntent(Intent intent) {
		Config.debug(TAG, "on newIntent");
		getDataFromIntent(intent);
		curPage = 1;
		type = 0;
		sendRequest();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.refresh:
			animation = AnimationUtils.loadAnimation(activity, R.anim.rotate);
			refresh.startAnimation(animation);
			type = 0;
			curPage = 1;
			sendRequest();
			break;
		case R.id.write:
			Intent intent = new Intent(activity, WriteActivity.class);
			intent.putExtra("at", "@" + user_id);
			activity.startActivity(intent);
			break;
		case R.id.back_top:
			// 回到顶部
			users_list.setSelection(1);
			break;
		case R.id.btn_back:
			activity.finish();
			break;
		case R.id.btn_home:
			intent = new Intent(activity, MainActivity.class);
			activity.startActivity(intent);
			activity.finish();
			break;
		default:
			break;
		}

	}

	/*
	 * 创建sendData
	 */
	private SendData createSendData() {
		SendData sendData = null;
		switch (Config.currentUser.getSp()) {
		case Constants.SP_TENCENT:
			sendData = new SendFollowers4Tencent(user_id, count, (curPage - 1)
					* count, 1);
			url = Constants.Tencent.FOLLOWERS_LIST;
			break;
		case Constants.SP_SINA:
			sendData = new SendUsers4Sina(getNext(), count);
			url = String.format(Constants.Sina.FOLLOWERS_LIST, user_id);
			break;
		case Constants.SP_NETEASE:
			sendData = new SendUsers4Netease(user_id, getNext());
			url = Constants.NetEase.FOLLOWERS_LIST;
			break;
		case Constants.SP_SOHU:
			sendData = new SendUsers4Sohu(getNext(), count);
			url = String.format(Constants.Sohu.FOLLOWERS_LIST, user_id);
			break;
		default:
			break;
		}
		return sendData;
	}

	private int getNext() {
		if (type == 0) {
			nextCursor = -1;
		}
		return nextCursor;
	}

	@Override
	protected void onResume() {
		if (!curUser.getUser_id().equals(Config.currentUser.getUser_id())) {
			curUser = Config.currentUser;
			curPage = 1;
		}
		super.onResume();
	}
	
	/**
	 * 显示载入数据的提示信息
	 */
	private void showProgress() {
		if (loading != null) {
			loading.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 隐藏载入数据的提示信息
	 */
	private void hiddenProgress() {
		if (loading != null) {
			loading.setVisibility(View.GONE);
		}
	}
}