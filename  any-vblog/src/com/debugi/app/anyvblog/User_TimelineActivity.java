package com.debugi.app.anyvblog;

import java.lang.reflect.Type;
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
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.debugi.app.anyvblog.adapter.HomeTimeLineAdapter;
import com.debugi.app.anyvblog.asynctask.GetHomeTimeLineAsyncTask;
import com.debugi.app.anyvblog.asynctask.SendAsyncTask;
import com.debugi.app.anyvblog.exception.NormalException;
import com.debugi.app.anyvblog.exception.ResponseException;
import com.debugi.app.anyvblog.model.DataAdapter;
import com.debugi.app.anyvblog.model.SendData;
import com.debugi.app.anyvblog.model.UserAdapter;
import com.debugi.app.anyvblog.model.netease.SendUserTimeLineData4Netease;
import com.debugi.app.anyvblog.model.netease.Status4Netease;
import com.debugi.app.anyvblog.model.sina.SendUserTimeLineData4Sina;
import com.debugi.app.anyvblog.model.sina.Status4Sina;
import com.debugi.app.anyvblog.model.sohu.SendTimeLineData4Sohu;
import com.debugi.app.anyvblog.model.sohu.Status4Sohu;
import com.debugi.app.anyvblog.model.tencent.Data4Tencent;
import com.debugi.app.anyvblog.model.tencent.GetTimeLine4Tencent;
import com.debugi.app.anyvblog.model.tencent.SendUserTimeLineData4Tencent;
import com.debugi.app.anyvblog.utils.Config;
import com.debugi.app.anyvblog.utils.Constants;
import com.debugi.app.anyvblog.utils.MyHandler;
import com.debugi.app.anyvblog.utils.TimeUtils;
import com.debugi.app.anyvblog.widget.CustomListView;
import com.debugi.app.anyvblog.widget.CustomListView.OnClickFooterListener;
import com.debugi.app.anyvblog.widget.CustomListView.OnRefreshListener;
import com.debugi.open.oauth.model.HttpType;
import com.debugi.open.oauth.model.Response;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class User_TimelineActivity extends Activity implements OnClickListener {
	/** Called when the activity is first created. */
	private static final String TAG = "User_TimelineActivity";
	private TextView account_nick;
	private Button refresh;
	private Button write;
	private CustomListView timeline;
	private final Activity activity = this;
	private SendData sendData;
	private Handler handler;
	private Animation animation;
	private HomeTimeLineAdapter adapter;
	private AsyncTask<Void, Void, Response> asyncTask = null;
	private List<DataAdapter> datas;
	private String user_nick;
	private String user_id;
	private String url;
	// 类别，区分请求最新或者获取更多
	private int type;
	// 返回顶部按钮
	private Button back_top;
	// 是否载入完毕
	private boolean loaded;
	// 初始进入时的进度条
	private LinearLayout progressBar;
	private Button btn_back;
	private Button btn_home;
	private int count = 20;// 请求的记录数

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_timeline);

		initViews();

		getDataFromIntent(getIntent());

		account_nick.setText(user_nick + "的广播");
		Config.debug(TAG, "user_id=" + user_id);
		loaded = false;
		type = 0;

		if (!loaded) {
			progressBar.setVisibility(View.VISIBLE);
		} else {
			progressBar.setVisibility(View.GONE);
		}

		if (Config.currentUser == null) {
			Config.currentUser = UserAdapter.getDefaultUser(activity);
		}
		createSendData(type);

		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0x001:
					timeline.onRefreshComplete("上次更新:"
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
									Config.currentUser.getSp()).getMessage();
							Toast.makeText(activity, tip, Toast.LENGTH_SHORT)
									.show();
							return;
						}
						if (type == 0) {
							// 准备填充数据
							datas = parseData(response);
							fillData(datas);
							resetFooter();
						} else {
							List<DataAdapter> addDatas = parseData(response);
							// adapter.addDatas(addDatas);
							datas.addAll(addDatas);
							adapter.notifyDataSetChanged();
							resetFooter();
							if (addDatas.size() <= 0) {
								noDataFooter();
							}
						}
						loaded = true;
						if (progressBar != null) {
							progressBar.setVisibility(View.GONE);
							loaded = true;
						}
					} catch (Exception e) {
						throw new NormalException(e.getMessage());
					}
					break;
				default:
					break;
				}
				super.handleMessage(msg);
			}

		};

		asyncTask = new SendAsyncTask(activity, sendData, url, HttpType.GET,
				Config.currentUser, handler).execute();

		/**
		 * 更新完成
		 */
		timeline.onRefreshComplete("上次更新:"
				+ TimeUtils.coverTimeStamp(Config.currentUser.getLast_update()));
		/**
		 * list项目长按监听器
		 */
		timeline.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Config.debug(TAG,
						view.getId() + "  " + position + " " + view.getTag());
				adapter.setSelectedItem(position);
				adapter.notifyDataSetChanged();
				return true;
			}
		});
		/**
		 * 刷新监听器
		 */
		timeline.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				asyncTask = new SendAsyncTask(activity, sendData, url,
						HttpType.GET, Config.currentUser, handler).execute();
				animation = AnimationUtils.loadAnimation(activity,
						R.anim.rotate);
				refresh.startAnimation(animation);
			}
		});
		/**
		 * list点击监听
		 */
		timeline.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(activity, ShowOneDataActivity.class);
				String tags = view.getTag().toString();
				intent.putExtra("id",
						tags.substring(0, (tags.lastIndexOf("|"))));
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
			user_nick = intent.getExtras().getString("nick");
		} else {
			user_nick = "null";
		}

	}

	/**
	 * 初始化
	 */
	private void initViews() {
		account_nick = (TextView) findViewById(R.id.account_nick);
		timeline = (CustomListView) findViewById(R.id.home_timeline);
		refresh = (Button) findViewById(R.id.refresh);
		write = (Button) findViewById(R.id.write);
		refresh.setOnClickListener(this);
		write.setOnClickListener(this);
		progressBar = (LinearLayout) findViewById(R.id.progressbar);
		resetFooter();
		back_top = (Button) findViewById(R.id.back_top);
		back_top.setOnClickListener(this);
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_home = (Button) findViewById(R.id.btn_home);
		btn_back.setOnClickListener(this);
		btn_home.setOnClickListener(this);
		timeline.setOnClickFooterListener(new OnClickFooterListener() {
			
			@Override
			public void onClick() {
				// 请求更多数据
				Config.debug(TAG, "on click footer");
				createSendData(1);
				type = 1;
				asyncTask = new GetHomeTimeLineAsyncTask(activity, sendData, url,
						HttpType.GET, Config.currentUser, handler);
				asyncTask.execute();
			}
		});
	}

	/**
	 * 处理数据显示
	 * 
	 * @param datas
	 */
	private void fillData(List<DataAdapter> datas) {
		// 创建adapter
		adapter = new HomeTimeLineAdapter(activity, datas, R.layout.list_item);
		// 对listview设置adapter
		timeline.setAdapter(adapter);
		// listview的显示动画效果
		LayoutAnimationController animationController = new LayoutAnimationController(
				AnimationUtils.loadAnimation(activity, R.anim.alpha), 0.1f);
		animationController.setOrder(LayoutAnimationController.ORDER_NORMAL);
		timeline.setLayoutAnimation(animationController);
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
	private List<DataAdapter> parseData(Response response) {
		List<DataAdapter> datas = new ArrayList<DataAdapter>();
		try {
			Config.debug(TAG, response.getBody());
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
						// 已拉取完毕
						noDataFooter();
					}
					if (type != 0) {
						datas.remove(0);
					}
				}
				break;
			case Constants.SP_SINA:
				Type type1 = new TypeToken<List<Status4Sina>>() {
				}.getType();
				List<Status4Sina> datas4Sina = gson.fromJson(
						response.getBody(), type1);
				for (Status4Sina data : datas4Sina) {
					datas.add(data.conver2Data());
				}
				if (type != 0) {
					datas.remove(0);
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

	@Override
	protected void onNewIntent(Intent intent) {
		Config.debug(TAG, "on newIntent");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.refresh:
			animation = AnimationUtils.loadAnimation(activity, R.anim.rotate);
			refresh.startAnimation(animation);
			asyncTask = new SendAsyncTask(activity, sendData, url,
					HttpType.GET, Config.currentUser, handler).execute();
			break;
		case R.id.write:
			Intent intent = new Intent(activity, WriteActivity.class);
			intent.putExtra("at", "@" + user_id);
			activity.startActivity(intent);
			break;
		case R.id.back_top:
			// 回到顶部
			timeline.setSelection(1);
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

	/**
	 * 没有数据，改变footer的提示信息
	 */
	private void noDataFooter() {
		if (timeline != null) {
			timeline.onClickComplete(R.string.footer_no_more_data);
		}
	}

	/**
	 * 重置footer
	 */
	private void resetFooter() {
		if (timeline != null) {
			timeline.onClickComplete(R.string.footer_normal_tips);
		}
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
			return "0";
		}
		return datas.get(datas.size() - 1).getOrigtime();
	}


	/*
	 * 创建sendData
	 */
	private void createSendData(int type) {
		if (type == 0) {
			switch (Config.currentUser.getSp()) {
			case Constants.SP_TENCENT:
				sendData = new SendUserTimeLineData4Tencent(user_id, count);
				url = Constants.Tencent.USER_TIMELINE;
				break;
			case Constants.SP_SINA:
				sendData = new SendUserTimeLineData4Sina(user_id, count);
				url = Constants.Sina.USER_TIMELINE;
				break;
			case Constants.SP_NETEASE:
				sendData = new SendUserTimeLineData4Netease(user_id, count);
				url = Constants.NetEase.USER_TIMELINE;
				break;
			case Constants.SP_SOHU:
				sendData = new SendTimeLineData4Sohu(count);
				url = String.format(Constants.Sohu.USER_TIMELINE, user_id);
				break;
			default:
				break;
			}
		} else {
			switch (Config.currentUser.getSp()) {
			case Constants.SP_TENCENT:
				url = Constants.Tencent.USER_TIMELINE;
				sendData = new SendUserTimeLineData4Tencent(user_id, count,
						Long.parseLong(getLastTime()), 1);
				break;
			case Constants.SP_SINA:
				url = Constants.Sina.USER_TIMELINE;
				sendData = new SendUserTimeLineData4Sina(user_id,
						getLastTime(), count);
				break;
			case Constants.SP_NETEASE:
				url = Constants.NetEase.USER_TIMELINE;
				// sendData = new SendHomeTimeLineData4Netease(count,
				// getLastTime());
				// 网页的翻页参数存在问题，和其api说明不相符
				sendData = new SendUserTimeLineData4Netease(user_id, count,
						getLastTime());
				break;
			case Constants.SP_SOHU:
				url = String.format(Constants.Sohu.USER_TIMELINE, user_id);
				sendData = new SendTimeLineData4Sohu(count, getLastTime());
				break;
			default:
				break;
			}
		}
	}
}