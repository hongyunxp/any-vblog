package com.ilovn.app.anyvblog;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
import android.widget.FrameLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ilovn.app.anyvblog.adapter.HomeTimeLineAdapter;
import com.ilovn.app.anyvblog.adapter.MentionsTwoViewAdapter;
import com.ilovn.app.anyvblog.application.ActivityStack;
import com.ilovn.app.anyvblog.asynctask.GetMentionsTimeLineAsyncTask;
import com.ilovn.app.anyvblog.exception.NormalException;
import com.ilovn.app.anyvblog.model.DataAdapter;
import com.ilovn.app.anyvblog.model.SendData;
import com.ilovn.app.anyvblog.model.UserAdapter;
import com.ilovn.app.anyvblog.model.netease.SendMentionsTimeLine4Netease;
import com.ilovn.app.anyvblog.model.netease.Status4Netease;
import com.ilovn.app.anyvblog.model.sina.SendMentionsTimeLine4Sina;
import com.ilovn.app.anyvblog.model.sina.Status4Sina;
import com.ilovn.app.anyvblog.model.sohu.SendTimeLineData4Sohu;
import com.ilovn.app.anyvblog.model.sohu.Status4Sohu;
import com.ilovn.app.anyvblog.model.tencent.Data4Tencent;
import com.ilovn.app.anyvblog.model.tencent.GetTimeLine4Tencent;
import com.ilovn.app.anyvblog.model.tencent.SendMentionsTimeLine4Tencent;
import com.ilovn.app.anyvblog.utils.Config;
import com.ilovn.app.anyvblog.utils.Constants;
import com.ilovn.app.anyvblog.utils.MyHandler;
import com.ilovn.app.anyvblog.widget.CustomListView;
import com.ilovn.app.anyvblog.widget.CustomListView.OnClickFooterListener;
import com.ilovn.app.anyvblog.widget.CustomListView.OnRefreshListener;
import com.ilovn.app.anyvblog.widget.TitleFlowIndicator;
import com.ilovn.app.anyvblog.widget.ViewFlow;
import com.ilovn.open.oauth.model.Response;

public class MentionsActivity extends Activity implements OnClickListener {
	private static final String TAG = "MentionsActivity";
	final Activity activity = this;
	private CustomListView mentions_list_reply;
	private CustomListView mentions_list_comment;
	private List<DataAdapter> datas_reply;
	private List<DataAdapter> datas_comment;
	private HomeTimeLineAdapter adapter_reply;
	private HomeTimeLineAdapter adapter_comment;
	private static final int TYPE_REPLY = 0X001;
	private static final int TYPE_COMMENT = 0X002;
	private AsyncTask<Void, Void, Response> asyncTask1;
	private AsyncTask<Void, Void, Response> asyncTask2;
	private SendData sendData;
	private Handler handler;
	// 初始进入时的进度条
	private FrameLayout loading;
	// 类别，区分请求最新或者获取更多
	private int type;
	private UserAdapter user;
	private int count = 20;// 初始一个请求获取的条目数

	private ViewFlow viewFlow;

	private boolean finish1;
	private boolean finish2;

	private int cur_reply;
	private int cur_comment;

	private LayoutAnimationController animationController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mentions);
		ActivityStack.getInstance().addActivity(activity);
		initViews();
		type = 0;
		cur_comment = 0;
		cur_reply = 0;
		user = Config.currentUser;
		finish1 = false;
		finish2 = false;
		showProgress();
		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case MyHandler.Handler_GetData:
					if (msg.arg2 == 0) {
						if (msg.arg1 == TYPE_REPLY) {
							try {
								Response response = asyncTask1.get();
								datas_reply = parseData(response);
								fillData(datas_reply, TYPE_REPLY);
							} catch (InterruptedException e) {
								e.printStackTrace();
							} catch (ExecutionException e) {
								e.printStackTrace();
							}
						} else {
							try {
								Response response = asyncTask2.get();
								datas_comment = parseData(response);
								fillData(datas_comment, TYPE_COMMENT);
							} catch (InterruptedException e) {
								e.printStackTrace();
							} catch (ExecutionException e) {
								e.printStackTrace();
							}
						}
					} else {
						if (msg.arg1 == TYPE_REPLY) {
							try {
								Response response = asyncTask1.get();
								List<DataAdapter> datas_reply_temp = parseData(response);
								if (datas_reply_temp != null
										&& datas_reply_temp.size() <= 0) {
									noDataFooter(TYPE_REPLY);
								} else {
									resetFooter(TYPE_REPLY);
									if (user.getSp() == Constants.SP_SINA
											|| user.getSp() == Constants.SP_SOHU) {
										datas_reply_temp.remove(0);
										if (datas_reply_temp.size() <= 0) {
											noDataFooter(TYPE_REPLY);
										}
									}
									datas_reply.addAll(datas_reply_temp);
									adapter_reply.notifyDataSetChanged();
								}
								finish1 = true;
							} catch (InterruptedException e) {
								e.printStackTrace();
							} catch (ExecutionException e) {
								e.printStackTrace();
							}
						} else {
							try {
								Response response = asyncTask2.get();
								List<DataAdapter> datas_comment_temp = parseData(response);
								if (datas_comment_temp != null
										&& datas_comment_temp.size() <= 0) {
									noDataFooter(TYPE_COMMENT);
								} else {
									resetFooter(TYPE_COMMENT);
									if (user.getSp() == Constants.SP_SINA
											|| user.getSp() == Constants.SP_SOHU) {
										datas_comment_temp.remove(0);
										if (datas_comment_temp.size() <= 0) {
											noDataFooter(TYPE_COMMENT);
										}
									}
									datas_comment.addAll(datas_comment_temp);
									adapter_comment.notifyDataSetChanged();
								}
								finish2 = true;
							} catch (InterruptedException e) {
								e.printStackTrace();
							} catch (ExecutionException e) {
								e.printStackTrace();
							}
						}
						hiddenProgress();
					}
					break;
				}
			}
		};
		getData(TYPE_REPLY, 0);
		getData(TYPE_COMMENT, 0);
	}

	/**
	 * 初始化views，并设置所需监听器
	 */
	private void initViews() {
		viewFlow = (ViewFlow) findViewById(R.id.viewflow);

		MentionsTwoViewAdapter adapter = new MentionsTwoViewAdapter(activity);
		viewFlow.setAdapter(adapter);

		TitleFlowIndicator indicator = (TitleFlowIndicator) findViewById(R.id.viewflowindic);
		indicator.setTitleProvider(adapter);

		viewFlow.setFlowIndicator(indicator);

		loading = (FrameLayout) findViewById(R.id.loading);

		mentions_list_reply = (CustomListView) findViewById(R.id.mentions_list_reply);
		mentions_list_comment = (CustomListView) findViewById(R.id.mentions_list_comment);
		
		mentions_list_comment.setOnItemClickListener(new MyListener(TYPE_COMMENT));
		mentions_list_reply.setOnItemClickListener(new MyListener(TYPE_REPLY));

		animationController = new LayoutAnimationController(
				AnimationUtils.loadAnimation(activity, R.anim.alpha), 0.1f);
		animationController.setOrder(LayoutAnimationController.ORDER_NORMAL);

		findViewById(R.id.back_m_reply_top).setOnClickListener(this);
		findViewById(R.id.back_m_comment_top).setOnClickListener(this);

		mentions_list_reply.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				finish1 = false;
				cur_reply = 0;
				getData(TYPE_REPLY, 0);
			}
		});

		mentions_list_comment.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				finish2 = false;
				cur_comment = 0;
				getData(TYPE_COMMENT, 0);
			}
		});

		mentions_list_comment
				.setOnClickFooterListener(new OnClickFooterListener() {

					@Override
					public void onClick() {
						finish2 = false;
						getData(TYPE_COMMENT, ++cur_comment);
					}
				});

		mentions_list_reply
				.setOnClickFooterListener(new OnClickFooterListener() {

					@Override
					public void onClick() {
						finish1 = false;
						getData(TYPE_REPLY, ++cur_reply);

					}
				});

	}

	/**
	 * 获取数据
	 * 
	 * @param mType
	 *            提及/评论
	 * @param pageType
	 *            第一页/更多
	 */
	private void getData(int mType, int pageType) {
		showProgress();
		boolean more;
		type = TYPE_REPLY;// 该参数仅用于除腾讯以外服务商用于识别调用类别
		if (pageType == 0) {
			more = false;
			switch (user.getSp()) {
			case Constants.SP_TENCENT:
				switch (mType) {
				case TYPE_REPLY:
					sendData = new SendMentionsTimeLine4Tencent(count, 0x3A);
					type = TYPE_REPLY;
					break;
				case TYPE_COMMENT:
					sendData = new SendMentionsTimeLine4Tencent(count, 0x40);
					type = TYPE_COMMENT;
					break;
				}
				break;
			case Constants.SP_SINA:
				sendData = new SendMentionsTimeLine4Sina(count);
				switch (mType) {
				case TYPE_REPLY:
					type = TYPE_REPLY;
					break;
				case TYPE_COMMENT:
					type = TYPE_COMMENT;
					break;
				}
				break;
			case Constants.SP_NETEASE:
				sendData = new SendMentionsTimeLine4Netease(count);
				switch (mType) {
				case TYPE_REPLY:
					type = TYPE_REPLY;
					break;
				case TYPE_COMMENT:
					type = TYPE_COMMENT;
					break;
				}
				break;
			case Constants.SP_SOHU:
				sendData = new SendTimeLineData4Sohu(count);
				switch (mType) {
				case TYPE_REPLY:
					type = TYPE_REPLY;
					break;
				case TYPE_COMMENT:
					type = TYPE_COMMENT;
					break;
				default:
					break;
				}
				break;
			}
		} else {
			// 加载更多
			more = true;
			switch (user.getSp()) {
			case Constants.SP_TENCENT:
				switch (mType) {
				case TYPE_REPLY:
					sendData = new SendMentionsTimeLine4Tencent(1,
							Long.parseLong(getLastTime(datas_reply)),
							getLastId(datas_reply), count, 0x3A);
					type = TYPE_REPLY;
					break;
				case TYPE_COMMENT:
					sendData = new SendMentionsTimeLine4Tencent(1,
							Long.parseLong(getLastTime(datas_comment)),
							getLastId(datas_comment), count, 0x40);
					type = TYPE_COMMENT;
					break;
				}
				break;
			case Constants.SP_SINA:
				switch (mType) {
				case TYPE_REPLY:
					type = TYPE_REPLY;
					sendData = new SendMentionsTimeLine4Sina(
							getLastTime(datas_reply), count);
					break;
				case TYPE_COMMENT:
					type = TYPE_COMMENT;
					sendData = new SendMentionsTimeLine4Sina(
							getLastTime(datas_comment), count);
					break;
				}
				break;
			case Constants.SP_NETEASE:
				switch (mType) {
				case TYPE_REPLY:
					type = TYPE_REPLY;
					sendData = new SendMentionsTimeLine4Netease(
							getLastTime(datas_reply), count);
					break;
				case TYPE_COMMENT:
					type = TYPE_COMMENT;
					sendData = new SendMentionsTimeLine4Netease(
							getLastTime(datas_comment), count);
					break;
				}
				break;
			case Constants.SP_SOHU:
				switch (mType) {
				case TYPE_REPLY:
					type = TYPE_REPLY;
					sendData = new SendTimeLineData4Sohu(count,
							getLastTime(datas_reply));
					break;
				case TYPE_COMMENT:
					type = TYPE_COMMENT;
					sendData = new SendTimeLineData4Sohu(count,
							getLastTime(datas_comment));
					break;
				}
				break;
			}
		}
		if (type == TYPE_REPLY) {
			asyncTask1 = new GetMentionsTimeLineAsyncTask(activity, user,
					sendData, type, handler, more).execute();
		} else {
			asyncTask2 = new GetMentionsTimeLineAsyncTask(activity, user,
					sendData, type, handler, more).execute();
		}
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

	/**
	 * 根据返回的数据，解析得到数据
	 * 
	 * @param response
	 * @return
	 */
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

	/**
	 * 处理数据的显示
	 * 
	 * @param datas
	 * @param mType
	 */
	private void fillData(List<DataAdapter> datas, int mType) {
		if (mType == TYPE_REPLY) {// 提及
			adapter_reply = new HomeTimeLineAdapter(activity, datas_reply,
					R.layout.list_item);
			mentions_list_reply.setAdapter(adapter_reply);
			mentions_list_reply.setLayoutAnimation(animationController);
			resetFooter(TYPE_REPLY);
			mentions_list_reply.onRefreshComplete();
			finish1 = true;
		} else {// 评论
			adapter_comment = new HomeTimeLineAdapter(activity, datas_comment,
					R.layout.list_item);
			mentions_list_comment.setAdapter(adapter_comment);
			mentions_list_comment.setLayoutAnimation(animationController);
			resetFooter(TYPE_COMMENT);
			mentions_list_comment.onRefreshComplete();
			finish2 = true;
		}
		hiddenProgress();
	}

	/**
	 * 获取最后一个原始时间点
	 * 
	 * @return
	 */
	private String getLastTime(List<DataAdapter> datas) {
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
	private String getLastId(List<DataAdapter> datas) {
		if (datas == null || datas.size() <= 0) {
			return null;
		}
		return datas.get(datas.size() - 1).getStatus_id();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back_m_reply_top:
			// 回到顶部
			mentions_list_reply.setSelection(1);
			break;
		case R.id.back_m_comment_top:
			// 回到顶部
			mentions_list_comment.setSelection(1);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onResume() {
		Config.debug(TAG, "on resume");
		if (!user.getUser_id().equals(Config.currentUser.getUser_id())) {
			user = Config.currentUser;
			finish1 = false;
			finish2 = false;
			getData(TYPE_REPLY, 0);
			getData(TYPE_COMMENT, 0);
		}
		super.onResume();
	}

	/**
	 * 没有数据，改变footer的提示信息
	 */
	private void noDataFooter(int mType) {
		if (mType == TYPE_REPLY) {
			if (mentions_list_reply != null) {
				mentions_list_reply
						.onClickComplete(R.string.footer_no_more_data);
			}
		} else {
			if (mentions_list_comment != null) {
				mentions_list_comment
						.onClickComplete(R.string.footer_no_more_data);
			}
		}

	}

	/**
	 * 重置footer
	 */
	private void resetFooter(int mType) {
		if (mType == TYPE_REPLY) {
			if (mentions_list_reply != null) {
				mentions_list_reply
						.onClickComplete(R.string.footer_normal_tips);
			}
		} else {
			if (mentions_list_comment != null) {
				mentions_list_comment
						.onClickComplete(R.string.footer_normal_tips);
			}
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
		Config.debug(TAG, "fi->" + finish1 + " f2->" + finish2);
		if (loading != null && finish1 == true && finish2 == true) {
			loading.setVisibility(View.GONE);
		}
	}
	private class MyListener implements OnItemClickListener {
		private int mType;
		public MyListener(int mType) {
			this.mType = mType;
		}
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (mType == TYPE_COMMENT && Config.currentUser.getSp() == Constants.SP_SINA) {
				return;
			}
			Intent intent = new Intent(activity, ShowOneDataActivity.class);
			if (mType == TYPE_REPLY) {
				if (position == ListView.INVALID_POSITION || position > datas_reply.size()) {
					return;
				}
				intent.putExtra("id", datas_reply.get(position - 1).getStatus_id());
			} else {
				if (position == ListView.INVALID_POSITION || position > datas_comment.size()) {
					return;
				}
				intent.putExtra("id", datas_comment.get(position - 1).getStatus_id());
			}
			activity.startActivity(intent);
			
		}
	}
}
