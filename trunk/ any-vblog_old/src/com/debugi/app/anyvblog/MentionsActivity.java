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
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.debugi.app.anyvblog.adapter.HomeTimeLineAdapter;
import com.debugi.app.anyvblog.asynctask.GetMentionsTimeLineAsyncTask;
import com.debugi.app.anyvblog.exception.NormalException;
import com.debugi.app.anyvblog.exception.ResponseException;
import com.debugi.app.anyvblog.model.DataAdapter;
import com.debugi.app.anyvblog.model.SendData;
import com.debugi.app.anyvblog.model.UserAdapter;
import com.debugi.app.anyvblog.model.netease.SendMentionsTimeLine4Netease;
import com.debugi.app.anyvblog.model.netease.Status4Netease;
import com.debugi.app.anyvblog.model.sina.SendMentionsTimeLine4Sina;
import com.debugi.app.anyvblog.model.sina.Status4Sina;
import com.debugi.app.anyvblog.model.sohu.SendTimeLineData4Sohu;
import com.debugi.app.anyvblog.model.sohu.Status4Sohu;
import com.debugi.app.anyvblog.model.tencent.Data4Tencent;
import com.debugi.app.anyvblog.model.tencent.GetTimeLine4Tencent;
import com.debugi.app.anyvblog.model.tencent.SendMentionsTimeLine4Tencent;
import com.debugi.app.anyvblog.utils.Config;
import com.debugi.app.anyvblog.utils.Constants;
import com.debugi.app.anyvblog.utils.MyHandler;
import com.debugi.app.anyvblog.widget.CustomListView;
import com.debugi.app.anyvblog.widget.CustomListView.OnClickFooterListener;
import com.debugi.app.anyvblog.widget.CustomListView.OnRefreshListener;
import com.debugi.open.oauth.model.Response;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MentionsActivity extends Activity implements
		OnCheckedChangeListener, OnClickListener {
	private static final String TAG = "MentionsActivity";
	final Activity activity = this;
	private RadioButton mentions_type_reply;
	private RadioButton mentions_type_comment;
	private CustomListView mentions_timeline;
	private AsyncTask<Void, Void, Response> asyncTask;
	private SendData sendData;
	private List<DataAdapter> datas;
	private Handler handler;
	// 返回顶部按钮
	private Button back_top;
	// 是否载入完毕
	private boolean loaded;
	// 初始进入时的进度条
	private LinearLayout progressBar;
	// 类别，区分请求最新或者获取更多
	private int type;
	private int curBtn;
	private HomeTimeLineAdapter adapter;
	private UserAdapter user;
	private int count = 20;// 初始一个请求获取的条目数

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mentions);
		initViews();
		loaded = false;
		type = 0;
		user = Config.currentUser;
		if (!loaded) {
			progressBar.setVisibility(View.VISIBLE);
		} else {
			progressBar.setVisibility(View.GONE);
		}
		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case MyHandler.Handler_GetData:
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
						if (response != null && response.getCode() == 200) {
							if (type == 0) {
								datas = parseData(response);
								fillData(datas);
								mentions_timeline.onRefreshComplete();
								resetFooter();
								if (datas.size() <= 0) {
									noDataFooter();
								}
								if (progressBar != null) {
									progressBar.setVisibility(View.GONE);
									loaded = true;
								}
							} else {
								List<DataAdapter> temp = parseData(response);
								if (temp.size() <= 0) {
									noDataFooter();
								} else {
									if (user.getSp() == Constants.SP_SINA
											|| user.getSp() == Constants.SP_SOHU) {
										temp.remove(0);
									}
									datas.addAll(temp);
									adapter.notifyDataSetChanged();
									resetFooter();
								}
							}
						}
					} catch (Exception e) {
						throw new NormalException(e.getMessage());
					}
					break;

				default:
					break;
				}
			};
		};
		getData(R.id.mentions_type_reply, type);
		curBtn = R.id.mentions_type_reply;
		mentions_timeline.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				type = 0;
				getData(curBtn, type);
			}
		});
		// /**
		// * list项目长按监听器
		// */
		// mentions_timeline.setOnItemLongClickListener(new
		// OnItemLongClickListener() {
		//
		// @Override
		// public boolean onItemLongClick(AdapterView<?> parent, View view,
		// int position, long id) {
		// Config.debug(TAG,
		// view.getId() + "  " + position + " " + view.getTag());
		// if (curBtn == R.id.mentions_type_comment &&
		// Config.currentUser.getSp() == Constants.SP_SINA) {
		// return false;
		// } else {
		// adapter.setSelectedItem(position);
		// adapter.notifyDataSetChanged();
		// }
		// return true;
		// }
		// });
		mentions_timeline.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (curBtn == R.id.mentions_type_comment
						&& Config.currentUser.getSp() == Constants.SP_SINA) {
					return;
				}
				Intent intent = new Intent(activity, ShowOneDataActivity.class);
				String tags = view.getTag().toString();
				intent.putExtra("id",
						tags.substring(0, (tags.lastIndexOf("|"))));
				activity.startActivity(intent);
			}
		});
	}

	/**
	 * 初始化views，并设置所需监听器
	 */
	private void initViews() {
		mentions_type_reply = (RadioButton) findViewById(R.id.mentions_type_reply);
		mentions_type_comment = (RadioButton) findViewById(R.id.mentions_type_comment);
		mentions_timeline = (CustomListView) findViewById(R.id.mentions_list);
		mentions_type_reply.setOnCheckedChangeListener(this);
		mentions_type_comment.setOnCheckedChangeListener(this);

		progressBar = (LinearLayout) findViewById(R.id.progressbar);
		back_top = (Button) findViewById(R.id.back_top);
		back_top.setOnClickListener(this);

		resetFooter();

		mentions_timeline.setOnClickFooterListener(new OnClickFooterListener() {

			@Override
			public void onClick() {
				// 请求更多数据
				Config.debug(TAG, "on click footer");
				type = 1;
				getData(curBtn, type);
			}
		});
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			type = 0;
			if (progressBar != null) {
				progressBar.setVisibility(View.VISIBLE);
				loaded = false;
			}
			if (!loaded) {
				getData(buttonView.getId(), type);
			}
			curBtn = buttonView.getId();
		}
	}

	private void getData(int viewId, int pageType) {
		int type = 0;// 该参数仅用于除腾讯以外服务商用于识别调用类别
		if (pageType == 0) {
			switch (user.getSp()) {
			case Constants.SP_TENCENT:
				switch (viewId) {
				case R.id.mentions_type_reply:
					sendData = new SendMentionsTimeLine4Tencent(count, 0x3A);
					break;
				case R.id.mentions_type_comment:
					sendData = new SendMentionsTimeLine4Tencent(count, 0x40);
					break;
				default:
					break;
				}
				break;
			case Constants.SP_SINA:
				sendData = new SendMentionsTimeLine4Sina(count);
				switch (viewId) {
				case R.id.mentions_type_reply:
					type = 0;
					break;
				case R.id.mentions_type_comment:
					type = 1;
					break;
				default:
					break;
				}
				break;
			case Constants.SP_NETEASE:
				sendData = new SendMentionsTimeLine4Netease(count);
				switch (viewId) {
				case R.id.mentions_type_reply:
					type = 0;
					break;
				case R.id.mentions_type_comment:
					type = 1;
					break;
				default:
					break;
				}
				break;
			case Constants.SP_SOHU:
				sendData = new SendTimeLineData4Sohu(count);
				switch (viewId) {
				case R.id.mentions_type_reply:
					type = 0;
					break;
				case R.id.mentions_type_comment:
					type = 1;
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
		} else {
			// 加载更多
			switch (user.getSp()) {
			case Constants.SP_TENCENT:
				switch (viewId) {
				case R.id.mentions_type_reply:
					sendData = new SendMentionsTimeLine4Tencent(1,
							Long.parseLong(getLastTime()), getLastId(), count,
							0x3A);
					break;
				case R.id.mentions_type_comment:
					sendData = new SendMentionsTimeLine4Tencent(1,
							Long.parseLong(getLastTime()), getLastId(), count,
							0x40);
					break;
				default:
					break;
				}
				break;
			case Constants.SP_SINA:
				sendData = new SendMentionsTimeLine4Sina(getLastTime(), count);
				switch (viewId) {
				case R.id.mentions_type_reply:
					type = 0;
					break;
				case R.id.mentions_type_comment:
					type = 1;
					break;
				default:
					break;
				}
				break;
			case Constants.SP_NETEASE:
				sendData = new SendMentionsTimeLine4Netease(getLastTime(),
						count);
				switch (viewId) {
				case R.id.mentions_type_reply:
					type = 0;
					break;
				case R.id.mentions_type_comment:
					type = 1;
					break;
				default:
					break;
				}
				break;
			case Constants.SP_SOHU:
				sendData = new SendTimeLineData4Sohu(count, getLastTime());
				switch (viewId) {
				case R.id.mentions_type_reply:
					type = 0;
					break;
				case R.id.mentions_type_comment:
					type = 1;
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
		}
		asyncTask = new GetMentionsTimeLineAsyncTask(activity, user, sendData,
				type, handler).execute();
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

	private List<DataAdapter> parseData(Response response) {
		List<DataAdapter> datas = new ArrayList<DataAdapter>();
		try {
			Gson gson = new Gson();
			Type myType = null;
			switch (user.getSp()) {
			case Constants.SP_TENCENT:
				GetTimeLine4Tencent data4Tencent = gson.fromJson(
						response.getBody(), GetTimeLine4Tencent.class);
				// 若返回值ret为0，则解析到适配
				if (data4Tencent.getRet() == 0
						&& data4Tencent.getData() != null) {
					for (Data4Tencent data : data4Tencent.getData().getInfo()) {
						datas.add(data.conver2Data());
					}
					if (data4Tencent.getData().getHasnext() == 1) {
						// 已拉取完毕
						noDataFooter();
					}
				}
				break;
			case Constants.SP_SINA:
				myType = new TypeToken<List<Status4Sina>>() {
				}.getType();
				List<Status4Sina> datas4Sina = gson.fromJson(
						response.getBody(), myType);
				for (Status4Sina data : datas4Sina) {
					datas.add(data.conver2Data());
				}
				break;
			case Constants.SP_NETEASE:
				myType = new TypeToken<List<Status4Netease>>() {
				}.getType();
				List<Status4Netease> datas4Netease = gson.fromJson(
						response.getBody(), myType);
				for (Status4Netease status4Netease : datas4Netease) {
					datas.add(status4Netease.conver2Data());
				}
				break;
			case Constants.SP_SOHU:
				myType = new TypeToken<List<Status4Sohu>>() {
				}.getType();
				List<Status4Sohu> datas4Sohu = gson.fromJson(
						response.getBody(), myType);
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

	private void fillData(List<DataAdapter> datas) {
		// 创建adapter
		adapter = new HomeTimeLineAdapter(activity, datas, R.layout.list_item);
		// 对listview设置adapter
		mentions_timeline.setAdapter(adapter);
		// listview的显示动画效果
		LayoutAnimationController animationController = new LayoutAnimationController(
				AnimationUtils.loadAnimation(activity, R.anim.alpha), 0.1f);
		animationController.setOrder(LayoutAnimationController.ORDER_NORMAL);
		mentions_timeline.setLayoutAnimation(animationController);
	}

	/**
	 * 没有数据，改变footer的提示信息
	 */
	private void noDataFooter() {
		if (mentions_timeline != null) {
			mentions_timeline.onClickComplete(R.string.footer_no_more_data);
		}
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

	/**
	 * 获取最后一个id
	 * 
	 * @return
	 */
	private String getLastId() {
		if (datas == null || datas.size() <= 0) {
			return null;
		}
		return datas.get(datas.size() - 1).getStatus_id();
	}

	/**
	 * 重置footer
	 */
	private void resetFooter() {
		if (mentions_timeline != null) {
			mentions_timeline.onClickComplete(R.string.footer_normal_tips);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back_top:
			// 回到顶部
			mentions_timeline.setSelection(1);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onResume() {
		Config.debug(TAG, "on resume");
		if (!user.getUser_id().equals(Config.currentUser.getUser_id())) {
			type = 0;
			user = Config.currentUser;
			getData(curBtn, type);
			progressBar.setVisibility(View.VISIBLE);
		}
		super.onResume();
	}
}
