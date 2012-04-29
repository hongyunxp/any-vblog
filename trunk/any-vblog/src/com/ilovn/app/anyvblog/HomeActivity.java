package com.ilovn.app.anyvblog;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ilovn.app.anyvblog.R;
import com.ilovn.app.anyvblog.adapter.HomeAccountSpAdapter;
import com.ilovn.app.anyvblog.adapter.HomeTimeLineAdapter;
import com.ilovn.app.anyvblog.application.ActivityStack;
import com.ilovn.app.anyvblog.asynctask.GetHomeTimeLineAsyncTask;
import com.ilovn.app.anyvblog.asynctask.GetHomeTimeLineFromDBAsyncTask;
import com.ilovn.app.anyvblog.asynctask.SendAsyncTask;
import com.ilovn.app.anyvblog.db.DataHelper;
import com.ilovn.app.anyvblog.exception.NormalException;
import com.ilovn.app.anyvblog.exception.ResponseException;
import com.ilovn.app.anyvblog.model.DataAdapter;
import com.ilovn.app.anyvblog.model.SendData;
import com.ilovn.app.anyvblog.model.UserAdapter;
import com.ilovn.app.anyvblog.model.netease.SendHomeTimeLineData4Netease;
import com.ilovn.app.anyvblog.model.netease.Status4Netease;
import com.ilovn.app.anyvblog.model.sina.SendHomeTimeLineData4Sina;
import com.ilovn.app.anyvblog.model.sina.Status4Sina;
import com.ilovn.app.anyvblog.model.sohu.SendTimeLineData4Sohu;
import com.ilovn.app.anyvblog.model.sohu.Status4Sohu;
import com.ilovn.app.anyvblog.model.tencent.Data4Tencent;
import com.ilovn.app.anyvblog.model.tencent.GetTimeLine4Tencent;
import com.ilovn.app.anyvblog.model.tencent.SendHomeTimeLineData4Tencent;
import com.ilovn.app.anyvblog.utils.Config;
import com.ilovn.app.anyvblog.utils.Constants;
import com.ilovn.app.anyvblog.utils.MyHandler;
import com.ilovn.app.anyvblog.utils.TimeUtils;
import com.ilovn.app.anyvblog.widget.CustomListView;
import com.ilovn.app.anyvblog.widget.CustomListView.OnClickFooterListener;
import com.ilovn.app.anyvblog.widget.CustomListView.OnRefreshListener;
import com.ilovn.open.oauth.model.HttpType;
import com.ilovn.open.oauth.model.Response;

/**
 * 主页时间线
 * 
 * @author ilovyan
 * 
 */
public class HomeActivity extends Activity implements OnClickListener {
	/** Called when the activity is first created. */
	private static final String TAG = "HomeActivity";
	// 账户选择器
	private Spinner account_spinner;
	// 刷新按钮
	private Button refresh;
	// 写微博按钮
	private Button write;
	// 带下拉刷新的listview
	private CustomListView home_timeline;
	private final Activity activity = this;
	private List<Map<String, Object>> data;
	// 请求的数据
	private SendData sendData;
	// 请求url
	private String url;
	// 从数据库获取的用户列表
	private List<UserAdapter> users;
	// 是否已经切换了用户
	private boolean isUserChange;
	private SharedPreferences sharedPreferences;
	private Handler handler;
	// 动画
	private Animation animation;
	// 主页时间线适配器
	private HomeTimeLineAdapter adapter;
	// 异步任务
	private AsyncTask<Void, Void, Response> asyncTask = null;
	private AsyncTask<Integer, Void, List<DataAdapter>> asyncTask4DB = null;
	// 返回顶部按钮
	private Button back_top;
	// 是否载入完毕
	private boolean loaded;
	// 初始进入时的进度条
	private FrameLayout loading;
	// 当前的所有数据
	private List<DataAdapter> datas;
	private int type;

	/**
	 * 解析response
	 * 
	 * @param response
	 * @return
	 */
	private List<DataAdapter> conver(Response response) {
		List<DataAdapter> datas = new ArrayList<DataAdapter>();
		if (response == null || response.getCode() != 200) {
			if (response == null) {
				return datas;
			}
			String tip = new ResponseException(response,
					Config.currentUser.getSp()).getMessage();
			Config.toast(activity, tip, Toast.LENGTH_SHORT);
			return datas;
		}
		try {
			Gson gson = new Gson();
			switch (Config.currentUser.getSp()) {
			case Constants.SP_TENCENT:
				GetTimeLine4Tencent data4Tencent = gson.fromJson(
						response.getBody(), GetTimeLine4Tencent.class);
				// 若返回值ret为0，则解析到适配
				if (data4Tencent.getRet() == 0) {
					for (Data4Tencent data : data4Tencent.getData().getInfo()) {
						datas.add(data.conver2Data());
					}
					if (data4Tencent.getData().getHasnext() == 1) {
						home_timeline
								.onClickComplete(R.string.footer_no_more_data);
					}
				}
				break;
			case Constants.SP_SINA:
				Type type = new TypeToken<List<Status4Sina>>() {
				}.getType();
				List<Status4Sina> datas4Sina = gson.fromJson(
						response.getBody(), type);
				for (Status4Sina data : datas4Sina) {
					datas.add(data.conver2Data());
				}
				break;
			case Constants.SP_NETEASE:
				Type type2 = new TypeToken<List<Status4Netease>>() {
				}.getType();
				List<Status4Netease> datas4Netease = gson.fromJson(
						response.getBody(), type2);
				for (Status4Netease status4Netease : datas4Netease) {
					datas.add(status4Netease.conver2Data());
				}
				break;
			case Constants.SP_SOHU:
				Type type3 = new TypeToken<List<Status4Sohu>>() {
				}.getType();
				List<Status4Sohu> datas4Sohu = gson.fromJson(
						response.getBody(), type3);
				for (Status4Sohu status4Sohu : datas4Sohu) {
					datas.add(status4Sohu.conver2Data());
				}
				break;
			default:
				break;
			}
		} catch (Exception e) {
			throw new NormalException(e.getMessage());
		}
		return datas;
	}

	/*
	 * 创建sendData
	 */
	private void createSendData(int type) {
		if (Config.currentUser == null) {
			return;
		}
		// 请求的记录数
		int count = 20;
		if (type == 0) {
			switch (Config.currentUser.getSp()) {
			case Constants.SP_TENCENT:
				sendData = new SendHomeTimeLineData4Tencent(count);
				url = Constants.Tencent.HOME_TIMELINE;
				break;
			case Constants.SP_SINA:
				sendData = new SendHomeTimeLineData4Sina(count);
				url = Constants.Sina.HOME_TIMELINE;
				break;
			case Constants.SP_NETEASE:
				sendData = new SendHomeTimeLineData4Netease(count);
				url = Constants.NetEase.HOME_TIMELINE;
				break;
			case Constants.SP_SOHU:
				sendData = new SendTimeLineData4Sohu(count);
				url = Constants.Sohu.HOME_TIMELINE;
				break;
			default:
				break;
			}
		} else {
			switch (Config.currentUser.getSp()) {
			case Constants.SP_TENCENT:
				url = Constants.Tencent.HOME_TIMELINE;
				sendData = new SendHomeTimeLineData4Tencent(1,
						Long.parseLong(getLastTime()), count);
				break;
			case Constants.SP_SINA:
				url = Constants.Sina.HOME_TIMELINE;
				sendData = new SendHomeTimeLineData4Sina(getLastTime(), count);
				break;
			case Constants.SP_NETEASE:
				url = Constants.NetEase.HOME_TIMELINE;
				// sendData = new SendHomeTimeLineData4Netease(count,
				// getLastTime());
				// 网页的翻页参数存在问题，和其api说明不相符
				sendData = new SendHomeTimeLineData4Netease(count,
						getLastTime(), null);
				break;
			case Constants.SP_SOHU:
				url = Constants.Sohu.HOME_TIMELINE;
				sendData = new SendTimeLineData4Sohu(count, getLastTime());
				break;
			default:
				break;
			}
		}
	}

	/**
	 * 处理数据
	 * 
	 * @param datas
	 *            主页时间线的数据
	 */
	private void fillData(List<DataAdapter> datas) {
		if (datas == null || datas.size() <= 0) {
			return;
		}
		// 创建adapter
		adapter = new HomeTimeLineAdapter(activity, datas, R.layout.list_item);
		// 对listview设置adapter
		home_timeline.setAdapter(adapter);
		// listview的显示动画效果
		LayoutAnimationController animationController = new LayoutAnimationController(
				AnimationUtils.loadAnimation(activity, R.anim.alpha), 0.1f);
		animationController.setOrder(LayoutAnimationController.ORDER_NORMAL);
		home_timeline.setLayoutAnimation(animationController);
		Message message = new Message();
		message.what = 0x001;
		handler.sendMessage(message);
	}

	/**
	 * 启动时从数据库获取数据
	 */
	private void getData() {
		asyncTask4DB = new GetHomeTimeLineFromDBAsyncTask(activity, handler)
				.execute();
	}

	/**
	 * 载入用户账户
	 */
	private void initAccounts() {
		DataHelper dataHelper = new DataHelper(activity);
		// 载入所有用户信息
		users = dataHelper.loadUsers();
		dataHelper.close();

		if (Config.currentUser == null) {
			Config.currentUser = UserAdapter.getDefaultUser(activity);
		}

		data = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		// 适配数据
		for (int i = 0; i < users.size(); i++) {
			map = new HashMap<String, Object>();
			map.put("icon", users.get(i).getHead());
			switch (users.get(i).getSp()) {
			case Constants.SP_TENCENT:
				map.put("name", users.get(i).getNick() + "<腾讯>");
				break;
			case Constants.SP_SINA:
				map.put("name", users.get(i).getNick() + "<新浪>");
				break;
			case Constants.SP_NETEASE:
				map.put("name", users.get(i).getNick() + "<网易>");
				break;
			case Constants.SP_SOHU:
				map.put("name", users.get(i).getNick() + "<搜狐>");
				break;
			default:
				break;
			}
			map.put("user_id", users.get(i).getUser_id());
			map.put("sp", users.get(i).getSp());
			map.put("icon", users.get(i).getHead());
			data.add(map);
			map = null;
		}
		// 设置适配器
		account_spinner.setAdapter(new HomeAccountSpAdapter(activity, data,
				R.layout.oauth_item));

		for (int i = 0; i < users.size(); i++) {
			if (Config.currentUser != null
					&& users.get(i).getUser_id()
							.equals(Config.currentUser.getUser_id())
					&& users.get(i).getSp() == Config.currentUser.getSp()) {
				account_spinner.setSelection(i);
			}
		}
		/**
		 * 账户选择器的监听器
		 */
		account_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				UserAdapter temp = users.get(position);
				if (Config.currentUser == null) {
					Config.currentUser = users.get(position);
					isUserChange = true;
				}
				if (Config.currentUser != null
						&& (!temp.getUser_id().equals(
								Config.currentUser.getUser_id()) || temp
								.getSp() != Config.currentUser.getSp())) {
					Config.currentUser = users.get(position);
					isUserChange = true;
					// 获取最新信息
					type = 0;
					createSendData(type);
					asyncTask = new GetHomeTimeLineAsyncTask(activity,
							sendData, url, HttpType.GET, Config.currentUser,
							handler).execute();
					animation = AnimationUtils.loadAnimation(activity,
							R.anim.rotate);
					refresh.startAnimation(animation);
					showProgress();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// isUserChange = false;
			}
		});
	}

	/**
	 * 初始化控件
	 */
	private void initViews() {
		account_spinner = (Spinner) findViewById(R.id.account_spinner);
		home_timeline = (CustomListView) findViewById(R.id.home_timeline);
		refresh = (Button) findViewById(R.id.refresh);
		write = (Button) findViewById(R.id.write);
		loading = (FrameLayout) findViewById(R.id.loading);
		refresh.setOnClickListener(this);
		write.setOnClickListener(this);

		back_top = (Button) findViewById(R.id.back_top);
		back_top.setOnClickListener(this);

		home_timeline.setOnClickFooterListener(new OnClickFooterListener() {

			@Override
			public void onClick() {
				// 请求更多数据
				Config.debug(TAG, "on click footer");
				createSendData(1);
				type = 1;
				asyncTask = new GetHomeTimeLineAsyncTask(activity, sendData,
						url, HttpType.GET, Config.currentUser, handler);
				asyncTask.execute();
			}
		});

	}

	@Override
	public void onClick(View v) {
		if (Config.currentUser == null) {
			return;
		}
		switch (v.getId()) {
		case R.id.refresh:
			// 请求最新数据
			animation = AnimationUtils.loadAnimation(activity, R.anim.rotate);
			refresh.startAnimation(animation);
			type = 0;
			createSendData(type);
			asyncTask = new SendAsyncTask(activity, sendData, url,
					HttpType.GET, Config.currentUser, handler).execute();
			break;
		case R.id.write:
			// 写微博
			Intent intent = new Intent(activity, WriteActivity.class);
			activity.startActivity(intent);
			break;
		case R.id.back_top:
			// 回到顶部
			home_timeline.setSelection(1);
			break;
		default:
			break;
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		ActivityStack.getInstance().addActivity(activity);
		initViews();
		isUserChange = false;
		loaded = false;

		if (!loaded) {
			showProgress();
		} else {
			hiddenProgress();
		}

		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0x001:
					home_timeline.onRefreshComplete("上次更新:"
							+ TimeUtils.coverTimeStamp(Config.currentUser
									.getLast_update()));
					// 清除动画
					refresh.clearAnimation();
					isUserChange = false;
					hiddenProgress();
					loaded = true;
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
									Config.currentUser.getSp()).getMessage();
							Config.toast(activity, tip, Toast.LENGTH_SHORT);
							return;
						}
						if (type == 0) {
							datas = conver(response);
							fillData(datas);
							saveData(datas, type);
							home_timeline
									.onClickComplete(R.string.footer_normal_tips);
						} else {
							List<DataAdapter> addDatas = conver(response);
							// 新浪的机制，向下翻页需要去掉
							if (Config.currentUser.getSp() == Constants.SP_SINA) {
								addDatas.remove(0);
							}
							datas.addAll(addDatas);
							adapter.notifyDataSetChanged();
							saveData(addDatas, type);
							home_timeline
									.onClickComplete(R.string.footer_normal_tips);
							if (addDatas.size() <= 0) {
								home_timeline
										.onClickComplete(R.string.footer_no_more_data);
							}
						}
					} catch (Exception e) {
						throw new NormalException(e.getMessage());
					}
					refresh.clearAnimation();
					break;
				case MyHandler.Handler_GetDataFromDB:
					if (asyncTask4DB == null) {
						Config.debug(TAG, "null-----");
						break;
					}
					try {
						datas = asyncTask4DB.get();
						if (datas != null && datas.size() > 0) {
							if (datas.get(0).getSp() != Config.currentUser
									.getSp()) {
								createSendData(0);
								asyncTask = new GetHomeTimeLineAsyncTask(
										activity, sendData, url, HttpType.GET,
										Config.currentUser, handler).execute();
								isUserChange = false;
								showProgress();
								loaded = false;
								return;
							}
							fillData(datas);
							home_timeline
									.onClickComplete(R.string.footer_normal_tips);
						} else {
							Config.debug(TAG, "数据为空");
							createSendData(0);
							asyncTask = new GetHomeTimeLineAsyncTask(activity,
									sendData, url, HttpType.GET,
									Config.currentUser, handler).execute();
						}
					} catch (Exception e) {
						throw new NormalException(e.getMessage());
					}
					break;
				case MyHandler.Handler_Save_Default_Account:
					saveDefaultAccount();
					break;
				default:
					break;
				}
				super.handleMessage(msg);
			}
		};
		MyHandler.handler_home = handler;
		// 从数据库获取数据
		getData();

		/**
		 * 更新完成
		 */
		if (Config.currentUser != null) {
			home_timeline.onRefreshComplete("上次更新:"
					+ TimeUtils.coverTimeStamp(Config.currentUser
							.getLast_update()));
		}
		/**
		 * list项目长按监听器
		 */
		home_timeline.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Config.debug(TAG,
						view.getId() + "  " + position);
				adapter.setSelectedItem(position);
				adapter.notifyDataSetChanged();
				return true;
			}
		});
		/**
		 * list点击监听
		 */
		home_timeline.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (isUserChange) {
					Config.debug(TAG, "用户已经切换，不能现在获取微博信息，请等候列表更新……");
					Config.toast(activity, "用户已经切换，不能现在获取微博信息，请等候列表更新……",
							Toast.LENGTH_SHORT);
				} else {
					Intent intent = new Intent(activity,
							ShowOneDataActivity.class);
					intent.putExtra("id", datas.get(position - 1)
							.getStatus_id());
					activity.startActivity(intent);
				}
			}
		});
		/**
		 * 刷新监听器
		 */
		home_timeline.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				createSendData(0);
				asyncTask = new GetHomeTimeLineAsyncTask(activity, sendData,
						url, HttpType.GET, Config.currentUser, handler)
						.execute();
				isUserChange = false;
				animation = AnimationUtils.loadAnimation(activity,
						R.anim.rotate);
				refresh.startAnimation(animation);
			}
		});

	}

	@Override
	protected void onDestroy() {
		saveDefaultAccount();
		super.onDestroy();
	}

	/**
	 * 保存默认账户
	 */
	private void saveDefaultAccount() {
		Log.i(TAG, "save current user...");
		sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(activity);
		Editor editor = sharedPreferences.edit();
		editor.putInt("sp", Config.currentUser.getSp());
		editor.putString("user_id", Config.currentUser.getUser_id());
		editor.putString("token", Config.currentUser.getToken());
		editor.putString("token_secret", Config.currentUser.getToken_secret());
		editor.putString("nick", Config.currentUser.getNick());
		editor.putString("name", Config.currentUser.getName());
		editor.putString("last_update", Config.currentUser.getLast_update());
		editor.putString("head_url", Config.currentUser.getHead_url());
		editor.commit();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		Config.debug(TAG, "on newIntent");
		if (intent.getExtras() != null
				&& intent.getExtras().containsKey("fresh")) {
			boolean fresh = intent.getBooleanExtra("fresh", false);
			if (fresh) {
				createSendData(0);
				asyncTask = new GetHomeTimeLineAsyncTask(activity, sendData,
						url, HttpType.GET, Config.currentUser, handler)
						.execute();
				isUserChange = false;
				animation = AnimationUtils.loadAnimation(activity,
						R.anim.rotate);
				refresh.startAnimation(animation);
			}
		}
	}

	/**
	 * 保存数据到数据库
	 * 
	 * @param datas
	 *            数据
	 * @param type
	 *            类型 =0 保存新的数据 !=0 追加数据
	 */
	private void saveData(List<DataAdapter> datas, int type) {
		if (datas == null || datas.size() <= 0) {
			return;
		}
		// 将数据存到数据库
		DataHelper dataHelper = new DataHelper(activity);
		if (type == 0) {
			String now = Calendar.getInstance().getTimeInMillis() + "";
			Config.currentUser.setLast_update(now);
			dataHelper.updateAccount(Config.currentUser);
			dataHelper.saveHomeDatas(datas);
		} else {
			// 追加
			dataHelper.addHomeDatas(datas);
		}
		// 关闭数据库连接
		dataHelper.close();
	}

	/**
	 * 获取第一个原始时间点
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	private String getFirstTime() {
		if (datas == null || datas.size() <= 0) {
			return null;
		}
		return datas.get(0).getOrigtime();
	}

	/**
	 * 获取最后一个原始时间点
	 * 
	 * @return
	 */
	private String getLastTime() {
		if (datas == null || datas.size() <= 0) {
			return null;
		}
		return datas.get(datas.size() - 1).getOrigtime();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Config.debug(TAG, "click back catch on home");
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

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// 账户操作
		initAccounts();
		if (Config.isNeedFresh) {
			createSendData(0);
			asyncTask = new GetHomeTimeLineAsyncTask(activity, sendData, url,
					HttpType.GET, Config.currentUser, handler).execute();
			isUserChange = false;
			animation = AnimationUtils.loadAnimation(activity, R.anim.rotate);
			refresh.startAnimation(animation);
			Config.isNeedFresh = false;
		}
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