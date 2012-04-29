package com.ilovn.app.anyvblog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.ilovn.app.anyvblog.R;
import com.ilovn.app.anyvblog.adapter.FaceAdapter;
import com.ilovn.app.anyvblog.application.ActivityStack;
import com.ilovn.app.anyvblog.asynctask.SendDataAsyncTask;
import com.ilovn.app.anyvblog.db.DataHelper;
import com.ilovn.app.anyvblog.location.LocationModel;
import com.ilovn.app.anyvblog.location.LocationService;
import com.ilovn.app.anyvblog.model.SendData;
import com.ilovn.app.anyvblog.model.UserAdapter;
import com.ilovn.app.anyvblog.model.netease.SendOneData4Netease;
import com.ilovn.app.anyvblog.model.sina.SendOneData4Sina;
import com.ilovn.app.anyvblog.model.sohu.SendOneData4Sohu;
import com.ilovn.app.anyvblog.model.tencent.SendOneData4Tencent;
import com.ilovn.app.anyvblog.utils.Config;
import com.ilovn.app.anyvblog.utils.Constants;
import com.ilovn.app.anyvblog.utils.Face;
import com.ilovn.app.anyvblog.utils.MyHandler;

public class WriteActivity extends Activity implements OnClickListener {
	private static final String TAG = "WriteActivity";
	final Activity activity = this;
	private Button write_cancel;
	private Button write_send;
	private EditText write_status;
	private TextView write_location_tips;
	private TextView write_status_count;
	private Button write_pic;
	private Button write_at;
	private Button write_topic;
	private Button write_emo;
	private Button write_location;
	private Button write_account;
	private TextView write_account_send_count;
	final int DIALOG_ID = 0x001;
	private UserAdapter currentUser;
	private boolean[] checkStatus;
	private String[] names;
	private int accountCount = 1;
	private List<UserAdapter> users_send;
	private List<UserAdapter> users;
	private int count;
	private String status;
	private Handler handler;
	private boolean located;
	private LocationModel locationModel;
	private GridView grid_emotions;
	private FrameLayout emotions;
	private boolean frame_isShow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.write);
		ActivityStack.getInstance().addActivity(activity);
		initViews();
		frame_isShow = false;

		currentUser = Config.currentUser;
		write_account_send_count.setText("已选" + accountCount + "个账户");
		users_send = new ArrayList<UserAdapter>();
		users_send.add(currentUser);
		handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case MyHandler.Handler_Location:
					locationModel = (LocationModel) msg.obj;
					if (locationModel != null) {
						write_location_tips.setText("已定位");
						located = true;
						locationTips();
						Config.debug(TAG, locationModel.getLocation());
						//Config.debug(TAG, LocationService.getAddress(locationModel.getLocation()));
					}
					break;

				default:
					break;
				}
			};
		};

		// 字数统计
		write_status.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				System.out.println("afterTextChanged-->" + s.length());
				count = 140 - s.length();
				if (count >= 0) {
					write_status_count.setTextColor(Color.GREEN);
				} else {
					write_status_count.setTextColor(Color.RED);
				}
				write_status_count.setText(count + "");
			}
		});
		
		checkIntent(getIntent());
		
		List<Map<String, Object>> faceData = new ArrayList<Map<String,Object>>();
		
		for (int i = 0; i < Face.getAll().length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("image", Face.getAll()[i]);
			faceData.add(map);
			map = null;
		}
		
		FaceAdapter faceAdapter = new FaceAdapter(activity, Face.getAll(), R.layout.face_item);
		grid_emotions.setAdapter(faceAdapter);
	}

	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		switch (id) {
		case DIALOG_ID:
			Builder builder = new AlertDialog.Builder(activity);
			builder.setTitle("选择需要发布信息的账户");

			DataHelper dataHelper = new DataHelper(activity);
			users = dataHelper.loadUsers();
			dataHelper.close();
			checkStatus = new boolean[users.size()];
			names = new String[users.size()];
			for (int i = 0; i < users.size(); i++) {
				String name = "";
				if (users.get(i).getNick() == null) {
					switch (users.get(i).getSp()) {
					case Constants.SP_TENCENT:
						name = "腾讯微博";
						break;
					case Constants.SP_SINA:
						name = "新浪微博";
						break;
					case Constants.SP_NETEASE:
						name = "网易微博";
						break;
					case Constants.SP_SOHU:
						name = "搜狐微博";
						break;

					default:
						break;
					}
				} else {
					switch (users.get(i).getSp()) {
					case Constants.SP_TENCENT:
						name = "腾讯";
						break;
					case Constants.SP_SINA:
						name = "新浪";
						break;
					case Constants.SP_NETEASE:
						name = "网易";
						break;
					case Constants.SP_SOHU:
						name = "搜狐";
						break;

					default:
						break;
					}
					name = users.get(i).getNick() + " - " + name;
				}
				names[i] = name;

				if (currentUser.getSp() == users.get(i).getSp()
						&& users.get(i).getUser_id()
								.equals(currentUser.getUser_id())) {
					checkStatus[i] = true;
				} else {
					checkStatus[i] = false;
				}
			}
			builder.setMultiChoiceItems(names, checkStatus,
					new OnMultiChoiceClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which,
								boolean isChecked) {
							checkStatus[which] = isChecked;
						}
					});
			// 选择好后更新
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							accountCount = 0;
							users_send.clear();
							for (int i = 0; i < checkStatus.length; i++) {
								if (checkStatus[i]) {
									accountCount++;
									users_send.add(users.get(i));
								}
							}
							// update count
							write_account_send_count.setText("已选"
									+ accountCount + "个账户");
						}
					});
			// 不允许使用返回键取消
			builder.setCancelable(false);

			return builder.create();

		default:
			break;
		}
		return null;
	}

	/**
	 * 初始化
	 */
	private void initViews() {
		write_at = (Button) findViewById(R.id.write_at);
		write_cancel = (Button) findViewById(R.id.write_cancel);
		write_emo = (Button) findViewById(R.id.write_emo);
		write_location = (Button) findViewById(R.id.write_location);
		write_location_tips = (TextView) findViewById(R.id.write_location_tips);
		write_pic = (Button) findViewById(R.id.write_pic);
		write_send = (Button) findViewById(R.id.write_send);
		write_status = (EditText) findViewById(R.id.write_status);
		write_status_count = (TextView) findViewById(R.id.write_status_count);
		write_topic = (Button) findViewById(R.id.write_topic);
		write_account = (Button) findViewById(R.id.write_account);
		write_account_send_count = (TextView) findViewById(R.id.write_account_send_count);
		
		grid_emotions = (GridView) findViewById(R.id.grid_emotions);
		emotions = (FrameLayout) findViewById(R.id.emotions);
		// 注册监听器
		write_send.setOnClickListener(this);
		write_account.setOnClickListener(this);
		write_cancel.setOnClickListener(this);
		write_location.setOnClickListener(this);
		write_location_tips.setOnClickListener(this);
		grid_emotions.setOnItemSelectedListener(new MyGridListener());
		grid_emotions.setOnItemClickListener(new MyGridListener());
		write_emo.setOnClickListener(this);
		write_at.setOnClickListener(this);
		write_topic.setOnClickListener(this);
		write_pic.setOnClickListener(this);
		
		
	}

	/**
	 * 发送前数据检查
	 * 
	 * @return
	 */
	private boolean preSendCheck() {
		boolean flag = false;
		status = write_status.getText().toString();
		if (users_send.size() <= 0) {
			Log.i(TAG, "未选择需要发布到的账户，至少需要一个！");
			Config.toast(activity, "", Toast.LENGTH_SHORT);
			return flag;
		} else if (count >= 140) {
			Log.i(TAG, "别心急，你还没有输入内容！");
			Config.toast(activity, "别心急，你还没有输入内容！", Toast.LENGTH_SHORT);
			return flag;
		} else if (status == null || "".equals(status)) {
			Log.i(TAG, "别心急，你还没有输入内容！");
			Config.toast(activity, "别心急，你还没有输入内容！", Toast.LENGTH_SHORT);
			return flag;
		} else {
			flag = true;
		}
		return flag;
	}

	/**
	 * 根据用户和数据，转换得到用于发送的数据
	 * 
	 * @param user
	 * @param status
	 * @return
	 */
	private SendData conver(UserAdapter user, String status) {
		SendData sendData = null;
		switch (user.getSp()) {
		case Constants.SP_TENCENT:
			if (located) {
				sendData = new SendOneData4Tencent(status, (float)locationModel.getLongitude(), (float)locationModel.getLatitude());
			} else {
				sendData = new SendOneData4Tencent(status);
			}
			break;
		case Constants.SP_SINA:
			if (located) {
				sendData = new SendOneData4Sina(status, (float)locationModel.getLatitude(), (float)locationModel.getLongitude());
			} else {
				sendData = new SendOneData4Sina(status);
			}
			
			break;
		case Constants.SP_NETEASE:
			if (located) {
				sendData = new SendOneData4Netease(status, (float)locationModel.getLatitude(), (float)locationModel.getLongitude());
			} else {
				sendData = new SendOneData4Netease(status);
			}
			
			break;
		case Constants.SP_SOHU:
			sendData = new SendOneData4Sohu(status);
			break;

		default:
			break;
		}
		return sendData;
	}
	
	private void locationTips() {
		write_location_tips.setClickable(true);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.write_cancel:// 取消按钮
			activity.finish();
			break;
		case R.id.write_send:
			boolean isChecked = preSendCheck();
			if (!isChecked) {
				return;
			} else {
				status = write_status.getText().toString();
				System.out.println("status==>" + status);
				if (count < 0) {
					status = status.substring(0, 139);
					Log.i(TAG, "内容被截断");
					Config.toast(activity, "内容被截断", Toast.LENGTH_SHORT);
				}
				// 遍历当前需要发送的账户列表，启动异步任务发送数据
				for (UserAdapter user : users_send) {
					SendData sendData = conver(user, status);
					new SendDataAsyncTask(activity, user, sendData).execute();
				}
				// 关闭
				activity.finish();
			}
			break;
		case R.id.write_account://账户选择
			showDialog(DIALOG_ID);
			if (frame_isShow) {
				//隐藏
				hideEmotions();
			}
			break;
		case R.id.write_location:
			write_location_tips.setText("正在定位…");
			new Thread() {
				public void run() {
					Looper.prepare();
					new LocationService(activity, handler).getLocation();
				};
			}.start();
			if (frame_isShow) {
				//隐藏
				hideEmotions();
			}
			break;
		case R.id.write_location_tips:
			Config.debug(TAG, "click location tips");
			if (located) {
				Config.debug(TAG, "click location tips");
			}
			break;
		case R.id.write_emo:
			if (!frame_isShow) {
				showEmotions();
			} else {
				hideEmotions();
			}
			break;
		case R.id.write_topic:
			if (frame_isShow) {
				hideEmotions();
			}
			write_status.append("##");
			write_status.setSelection(write_status.getText().toString().length() - 1);
			break;
		case R.id.write_pic:
			Config.toast(activity, "暂不支持该功能", Toast.LENGTH_SHORT);
			break;
		case R.id.write_at:
			Config.toast(activity, "暂不支持该功能", Toast.LENGTH_SHORT);
			break;
		default:
			break;
		}
	}
	/**
	 * 显示表情选择框
	 */
	private void showEmotions() {
		if (emotions != null) {
			emotions.setVisibility(View.VISIBLE);
			frame_isShow = true;
		}
	}
	/**
	 * 隐藏表情框
	 */
	private void hideEmotions() {
		if (emotions != null) {
			emotions.setVisibility(View.GONE);
			frame_isShow = false;
		}
	}
	/**
	 * 
	 * @param intent
	 */
	private void checkIntent(Intent intent) {
		Config.debug(TAG, intent.getExtras());
		if (intent.getExtras() != null && intent.getExtras().containsKey("at")) {
			write_status.setText(intent.getExtras().getString("at"));
		}
	}
	private class MyGridListener implements OnItemSelectedListener, OnItemClickListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			Config.debug(TAG, "on select position=" + position + " id=" + id);
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			String faceName = Face.getName(Integer.parseInt(view.getTag().toString()));
			Config.debug(TAG, "you click " + faceName);
			write_status.append("/" + faceName);
			hideEmotions();
		}
		
	}
}
