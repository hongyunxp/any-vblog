package com.debugi.app.anyvblog;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.debugi.app.anyvblog.adapter.HomeTimeLineAdapter;
import com.debugi.app.anyvblog.adapter.PublicGridAdapter;
import com.debugi.app.anyvblog.adapter.UsersAdapter;
import com.debugi.app.anyvblog.asynctask.SendNoUpdateAsyncTask;
import com.debugi.app.anyvblog.exception.NormalException;
import com.debugi.app.anyvblog.exception.ResponseException;
import com.debugi.app.anyvblog.model.DataAdapter;
import com.debugi.app.anyvblog.model.SendData;
import com.debugi.app.anyvblog.model.UserAdapter;
import com.debugi.app.anyvblog.model.netease.GetSearchStatus4Netease;
import com.debugi.app.anyvblog.model.netease.GetSearchUser4Netease;
import com.debugi.app.anyvblog.model.netease.SendSearch4Netease;
import com.debugi.app.anyvblog.model.netease.Status4Netease;
import com.debugi.app.anyvblog.model.netease.UserInfo4Netease;
import com.debugi.app.anyvblog.model.sohu.GetSearchStatus4Sohu;
import com.debugi.app.anyvblog.model.sohu.SendSearchStatus4Sohu;
import com.debugi.app.anyvblog.model.sohu.SendSearchUser4Sohu;
import com.debugi.app.anyvblog.model.sohu.Status4Sohu;
import com.debugi.app.anyvblog.model.sohu.UserInfo4Sohu;
import com.debugi.app.anyvblog.model.tencent.Data4Tencent;
import com.debugi.app.anyvblog.model.tencent.GetTimeLine4Tencent;
import com.debugi.app.anyvblog.model.tencent.GetUserInfos4Tencent;
import com.debugi.app.anyvblog.model.tencent.SendSearchStatus4Tencent;
import com.debugi.app.anyvblog.model.tencent.SendSearchUser4Tencent;
import com.debugi.app.anyvblog.model.tencent.UserInfo4Tencent;
import com.debugi.app.anyvblog.utils.Config;
import com.debugi.app.anyvblog.utils.Constants;
import com.debugi.app.anyvblog.utils.MyHandler;
import com.debugi.open.oauth.model.HttpType;
import com.debugi.open.oauth.model.Response;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class PublicActivity extends Activity implements OnGestureListener,
		OnItemClickListener, OnTouchListener, OnCheckedChangeListener,
		OnClickListener {
	private static final String TAG = "PublicActivity";
	final Activity activity = this;
	final int FLIPPER_DISTANCE = 100;
	private GestureDetector gestureDetector;
	private ViewFlipper flipper;
	private PublicGridAdapter adapter;
	private EditText search_key;
	private Button search_submit;
	private RadioButton search_status;
	private RadioButton search_user;
	private ListView search_list;
	private Animation animation_in;
	private Animation animation_out;
	private int curBtn;
	private UserAdapter curUser;
	private int curPage;
	private RadioGroup search_types;
	// 是否载入完毕
	private boolean loaded;
	// 初始进入时的进度条
	private FrameLayout progressBar;
	// listview footer
	private LinearLayout footer;
	private ProgressBar footer_progressbar;
	private TextView footer_text;
	private Handler handler;
	private SendData sendData;
	private String url;
	private AsyncTask<Void, Void, Response> asyncTask;
	@SuppressWarnings("rawtypes")
	private List datas;
	private BaseAdapter baseAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_public);
		gestureDetector = new GestureDetector(this);
		animation_in = AnimationUtils
				.loadAnimation(activity, R.anim.flipper_in);
		animation_out = AnimationUtils.loadAnimation(activity,
				R.anim.flipper_out);
		initViews();
		loaded = false;
		curPage = 1;

		// if (!loaded) {
		// progressBar.setVisibility(View.VISIBLE);
		// } else {
		// progressBar.setVisibility(View.GONE);
		// }

		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pic", R.drawable.public_timeline);
		map.put("name", "广播大厅");
		datas.add(map);

		adapter = new PublicGridAdapter(activity, datas, R.layout.grid_item);
		GridView gridView = (GridView) findViewById(R.id.grid_public);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(this);
		gridView.setOnTouchListener(this);
		flipper.setInAnimation(animation_in);
		flipper.setOutAnimation(animation_out);

		curBtn = R.id.search_status;
		curUser = Config.currentUser;
		handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case MyHandler.Handler_GetData_Without_Update:
					try {
						Response response = asyncTask.get();
						if (response == null || response.getCode() != 200) {
							if (response == null) {
								return;
							}
							String tip = new ResponseException(response,
									curUser.getSp()).getMessage();
							Toast.makeText(activity, tip, Toast.LENGTH_SHORT)
									.show();
							return;
						}
						Config.debug(TAG, response.getBody());
						loaded = true;
						List<?> datas = parseData(response);
						if (datas != null && datas.size() <= 0) {
							noDataFooter();
						}
						fillData(datas);
						search_list.setVisibility(View.VISIBLE);
						resetFooter();
						if (progressBar != null) {
							progressBar.setVisibility(View.GONE);
						}
					} catch (Exception e) {
						 throw new NormalException(e.getMessage());
					}
					break;

				default:
					break;
				}
			}
		};

	}

	/**
	 * 填充数据
	 * 
	 * @param datas
	 */
	private void fillData(List<?> datas) {
		if (datas.size() <= 0) {
			progressBar.setVisibility(View.GONE);
			Toast.makeText(activity, "没有找到数据", Toast.LENGTH_SHORT).show();
		}
		if (this.datas == null) {
			if (curBtn == R.id.search_status) {
				this.datas = new ArrayList<DataAdapter>();
			} else {
				this.datas = new ArrayList<UserAdapter>();
			}
		}
		if (curPage != 1) {
			this.datas.addAll(datas);
			adapter.notifyDataSetChanged();
		} else {
			this.datas = datas;
			if (curBtn == R.id.search_status) {
				baseAdapter = new HomeTimeLineAdapter(activity,
						(List<DataAdapter>) this.datas, R.layout.list_item);
				search_list.setAdapter(baseAdapter);
			} else {
				baseAdapter = new UsersAdapter(activity,
						(List<UserAdapter>) this.datas, R.layout.users_item);
				search_list.setAdapter(baseAdapter);
			}
		}

	}

	/**
	 * 解析返回的数据
	 * 
	 * @param response
	 * @return
	 */
	private List<?> parseData(Response response) {
		List<DataAdapter> datas = new ArrayList<DataAdapter>();
		List<UserAdapter> users = new ArrayList<UserAdapter>();
		try {
			Gson gson = new Gson();
			if (curBtn == R.id.search_status) {
				switch (curUser.getSp()) {
				case Constants.SP_TENCENT:
					GetTimeLine4Tencent tencent = gson.fromJson(
							response.getBody(), GetTimeLine4Tencent.class);
					if (tencent != null && tencent.getRet() == 0) {
						for (Data4Tencent data : tencent.getData().getInfo()) {
							datas.add(data.conver2Data());
						}
					}
					break;
				case Constants.SP_SINA:

					break;
				case Constants.SP_NETEASE:
					GetSearchStatus4Netease searchStatus4Netease = gson
							.fromJson(response.getBody(),
									GetSearchStatus4Netease.class);
					if (searchStatus4Netease != null) {
						for (Status4Netease status4Netease : searchStatus4Netease
								.getResults()) {
							datas.add(status4Netease.conver2Data());
						}
					}

					break;
				case Constants.SP_SOHU:
					GetSearchStatus4Sohu searchStatus4Sohu = gson.fromJson(
							response.getBody(), GetSearchStatus4Sohu.class);
					if (searchStatus4Sohu != null) {
						for (Status4Sohu sohu : searchStatus4Sohu.getStatuses()) {
							datas.add(sohu.conver2Data());
						}
					}
					break;
				default:
					break;
				}

				return datas;
			} else {
				switch (curUser.getSp()) {
				case Constants.SP_TENCENT:
					GetUserInfos4Tencent infos4Tencent = gson.fromJson(
							response.getBody(), GetUserInfos4Tencent.class);
					if (infos4Tencent != null && infos4Tencent.getRet() == 0) {
						for (UserInfo4Tencent data : infos4Tencent.getData()
								.getInfo()) {
							users.add(data.conver2User());
						}
					}
					break;
				case Constants.SP_SINA:

					break;
				case Constants.SP_NETEASE:
					GetSearchUser4Netease searchUser4Netease = gson.fromJson(
							response.getBody(), GetSearchUser4Netease.class);
					if (searchUser4Netease != null) {
						for (UserInfo4Netease userInfo4Netease : searchUser4Netease
								.getResults()) {
							users.add(userInfo4Netease.conver2User());
						}
					}
					break;
				case Constants.SP_SOHU:
					Type typeSohu = new TypeToken<List<UserInfo4Sohu>>() {
					}.getType();
					List<UserInfo4Sohu> userInfo4Sohus = gson.fromJson(
							response.getBody(), typeSohu);
					for (UserInfo4Sohu userInfo4Sohu : userInfo4Sohus) {
						users.add(userInfo4Sohu.conver2User());
					}
					break;
				default:
					break;
				}
				return users;
			}
		} catch (Exception e) {
			throw new NormalException(e.getMessage());
		} finally {
			if (curBtn == R.id.search_status) {
				return datas;
			} else {
				return users;
			}
		}
	}

	private void initViews() {
		flipper = (ViewFlipper) findViewById(R.id.flipper_public);
		search_key = (EditText) findViewById(R.id.search_key);
		search_submit = (Button) findViewById(R.id.search_submit);
		search_list = (ListView) findViewById(R.id.search_list);
		search_types = (RadioGroup) findViewById(R.id.search_types);
		progressBar = (FrameLayout) findViewById(R.id.progressbar);
		search_status = (RadioButton) findViewById(R.id.search_status);
		search_user = (RadioButton) findViewById(R.id.search_user);
		// back_top = (Button) findViewById(R.id.back_top);
		// back_top.setOnClickListener(this);
		flipper.setOnTouchListener(this);
		search_list.setOnTouchListener(this);
		search_submit.setOnClickListener(this);
		search_status.setOnCheckedChangeListener(this);
		search_user.setOnCheckedChangeListener(this);
		search_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String sid = view.getTag().toString();
				Intent intent;
				if (curBtn == R.id.search_status) {
					intent = new Intent(activity, ShowOneDataActivity.class);
					intent.putExtra("id",
							sid.substring(0, (sid.lastIndexOf("|"))));
					activity.startActivity(intent);
				} else {
					intent = new Intent(activity, UserInfoActivity.class);
					intent.putExtra("user_id", sid);
					activity.startActivity(intent);
				}

			}
		});

		LayoutInflater inflater = LayoutInflater.from(activity);
		View view = inflater.inflate(R.layout.footer, null);
		footer_progressbar = (ProgressBar) view
				.findViewById(R.id.footer_progressbar);
		footer_text = (TextView) view.findViewById(R.id.foot_text);
		footer = (LinearLayout) view.findViewById(R.id.footer);
		search_list.addFooterView(footer);
		footer.setOnClickListener(this);
		resetFooter();

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// 将触碰时间交由GestureDetector处理
		return this.gestureDetector.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		Config.debug(TAG,
				"X=" + velocityX + " Y=" + velocityY + " e1.x=" + e1.getX()
						+ " e2.x=" + e2.getX());
		if (e1.getX() - e2.getX() > FLIPPER_DISTANCE) {
			flipper.showNext();
			return true;
		}
		if (e1.getX() - e2.getX() < -(FLIPPER_DISTANCE)) {
			flipper.showPrevious();
			return true;
		}
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Config.debug(TAG, "view=" + view.getId() + " position=" + position
				+ " id=" + id);
		Toast.makeText(activity, "暂不支持该特性，请关注后续版本", Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return this.gestureDetector.onTouchEvent(event);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			curBtn = buttonView.getId();
			curPage = 1;
			sendRequest();
		}

	}

	@Override
	protected void onResume() {
		if (search_types.getCheckedRadioButtonId() != curBtn
				|| !curUser.getUser_id()
						.equals(Config.currentUser.getUser_id())) {
			Config.debug(TAG, "update data");
			curBtn = search_types.getCheckedRadioButtonId();
			curUser = Config.currentUser;
			curPage = 1;
			sendRequest();
		}
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.footer:
			// 请求更多数据
			Config.debug(TAG, "on click footer");
			clickedFooter();
			curPage++;
			sendRequest();
			break;
		case R.id.back_top:
			// 回到顶部
			search_list.setSelection(1);
			break;
		case R.id.search_submit:
			curPage = 1;
			String key = search_key.getText().toString().trim();
			if (key.length() <= 0) {
				Config.debug(TAG, "没有输入关键字");
				Toast.makeText(activity, "别急，你还没输入搜索的关键字", Toast.LENGTH_SHORT)
						.show();
				return;
			}
			sendRequest();
			break;
		default:
			break;
		}

	}

	/**
	 * 没有数据，改变footer的提示信息
	 */
	private void noDataFooter() {
		if (footer != null && footer_progressbar != null && footer_text != null) {
			footer_progressbar.setVisibility(View.GONE);
			footer_text.setText("已拉取完毕!");
		}
	}

	/**
	 * 点击footer后
	 */
	private void clickedFooter() {
		if (footer != null && footer_progressbar != null && footer_text != null) {
			footer_progressbar.setVisibility(View.VISIBLE);
			footer_text.setText("正在载入中……");
		}
	}

	/**
	 * 重置footer
	 */
	private void resetFooter() {
		if (footer != null && footer_progressbar != null && footer_text != null) {
			footer_progressbar.setVisibility(View.GONE);
			footer_text.setText("查看更多");
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Config.debug(TAG, "click back catch on public");
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

	private SendData createSendData() {
		SendData sendData = null;
		String keyword = search_key.getText().toString().trim();
		if (keyword.length() <= 0) {
			keyword = "微博";
		}
		int count = 20;
		switch (curUser.getSp()) {
		case Constants.SP_TENCENT:
			if (curBtn == R.id.search_status) {
				sendData = new SendSearchStatus4Tencent(keyword, count, curPage);
				url = Constants.Tencent.SEARCH_STATUS;
			} else {
				Config.debug(TAG, "search user");
				sendData = new SendSearchUser4Tencent(keyword, count, curPage);
				url = Constants.Tencent.SEARCH_USER;
			}
			break;
		case Constants.SP_SINA:
			break;
		case Constants.SP_NETEASE:
			sendData = new SendSearch4Netease(keyword, curPage, count);
			if (curBtn == R.id.search_status) {
				url = Constants.NetEase.SEARCH_STATUS;
			} else {
				url = Constants.NetEase.SEARCH_USER;
			}
			break;
		case Constants.SP_SOHU:
			if (curBtn == R.id.search_status) {
				sendData = new SendSearchStatus4Sohu(keyword, curPage, count);
				url = Constants.Sohu.SEARCH_STATUS;
			} else {
				sendData = new SendSearchUser4Sohu(keyword, curPage, count);
				url = Constants.Sohu.SEARCH_USER;
			}
			break;

		default:
			break;
		}
		return sendData;
	}

	private void sendRequest() {
		if (curUser.getSp() == Constants.SP_SINA) {
			Config.debug(TAG, "版本还不支持新浪的搜索");
			Toast.makeText(activity, "版本还不支持新浪的搜索", Toast.LENGTH_SHORT).show();
			return;
		}
		sendData = createSendData();
		asyncTask = new SendNoUpdateAsyncTask(activity, sendData, url,
				HttpType.GET, curUser, handler).execute();
		if (progressBar != null && curPage == 1) {
			progressBar.setVisibility(View.VISIBLE);
		}
		loaded = false;
	}
}
