package com.ilovn.app.anyvblog;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.ilovn.app.anyvblog.asynctask.SendCommentAsyncTask;
import com.ilovn.app.anyvblog.db.DataHelper;
import com.ilovn.app.anyvblog.model.DataAdapter;
import com.ilovn.app.anyvblog.model.SendData;
import com.ilovn.app.anyvblog.model.UserAdapter;
import com.ilovn.app.anyvblog.model.netease.SendComment4Netease;
import com.ilovn.app.anyvblog.model.sina.SendComment4Sina;
import com.ilovn.app.anyvblog.model.sohu.SendComment4Sohu;
import com.ilovn.app.anyvblog.model.tencent.SendComment4Tencent;
import com.ilovn.app.anyvblog.utils.Config;
import com.ilovn.app.anyvblog.utils.Constants;
import com.ilovn.app.anyvblog.utils.Face;

/**
 * 转播，须传入转播的ID
 * 
 * @author Administrator
 * 
 */
public class CommentActivity extends Activity implements OnClickListener {
	private static final String TAG = "CommentActivity";
	final Activity activity = this;
	private Button write_cancel;
	private Button write_send;
	private EditText write_status;
	private TextView write_status_count;
	private Button write_at;
	private Button write_topic;
	private Button write_emo;
	final int DIALOG_ID = 0x002;
	private UserAdapter currentUser;
	private int count;
	private String status;
	private String id;
	private DataHelper dataHelper;
	private DataAdapter sourceData;
	private int type;
	private String cid;
	private GridView grid_emotions;
	private FrameLayout emotions;
	private boolean frame_isShow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comment);
		ActivityStack.getInstance().addActivity(activity);
		initViews();

		currentUser = Config.currentUser;
		id = getId(getIntent());
		type = getType(getIntent());
		if (getIntent().getExtras().containsKey("cid")) {
			cid = getIntent().getExtras().getString("cid");
		}
		if (getIntent().getExtras().containsKey("status")) {
			write_status.setText(getIntent().getExtras().getString("status"));
		}

		dataHelper = new DataHelper(activity);
		sourceData = dataHelper.loadOneDataById(id);
		dataHelper.close();
		Config.debug(TAG, "取出的数据" + sourceData);
		if (sourceData.isSource()) {
			write_status.setText("||@" + sourceData.getName() + " :" + sourceData.getStatus());
		}
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
				count = 140 - s.length();
				if (count >= 0) {
					write_status_count.setTextColor(Color.GREEN);
				} else {
					write_status_count.setTextColor(Color.RED);
				}
				write_status_count.setText(count + "");
			}
		});
		FaceAdapter faceAdapter = new FaceAdapter(activity, Face.getAll(),
				R.layout.face_item);
		grid_emotions.setAdapter(faceAdapter);
	}

	private String getId(Intent intent) {
		return intent.getExtras().getString("id");
	}

	private int getType(Intent intent) {
		return intent.getExtras().getInt("type");
	}

	/**
	 * 初始化
	 */
	private void initViews() {
		write_at = (Button) findViewById(R.id.write_at);
		write_cancel = (Button) findViewById(R.id.write_cancel);
		write_emo = (Button) findViewById(R.id.write_emo);
		write_send = (Button) findViewById(R.id.write_send);
		write_status = (EditText) findViewById(R.id.write_status);
		write_status_count = (TextView) findViewById(R.id.write_status_count);
		write_topic = (Button) findViewById(R.id.write_topic);
		grid_emotions = (GridView) findViewById(R.id.grid_emotions);
		emotions = (FrameLayout) findViewById(R.id.emotions);
		grid_emotions.setOnItemSelectedListener(new MyGridListener());
		grid_emotions.setOnItemClickListener(new MyGridListener());
		write_emo.setOnClickListener(this);
		write_at.setOnClickListener(this);
		write_topic.setOnClickListener(this);
		write_cancel.setOnClickListener(this);
		write_send.setOnClickListener(this);
	}

	/**
	 * 发送前数据检查
	 * 
	 * @return
	 */
	private boolean preSendCheck() {
		boolean flag = false;
		status = write_status.getText().toString();
		if (count >= 140) {
			// 空转播理由，则内容设置为“转发微博”
			status = "转发微博";
		} else if (status == null || "".equals(status)) {
			Config.debug(TAG, "别心急，你还没有输入内容！");
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
			sendData = new SendComment4Tencent(status, id);
			break;
		case Constants.SP_SINA:
			if (type == Constants.COMMENT_TYPE_COMMENT) {
				sendData = new SendComment4Sina(status, id);
			} else {
				sendData = new SendComment4Sina(status, cid, id);
			}
			break;
		case Constants.SP_NETEASE:
			sendData = new SendComment4Netease(id, status);
			break;
		case Constants.SP_SOHU:
			if (type == Constants.COMMENT_TYPE_COMMENT) {
				sendData = new SendComment4Sohu(id, status);
			} else {
				sendData = new SendComment4Sohu(id, status, cid);
			}
			break;

		default:
			break;
		}
		return sendData;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
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
				if (count < 0) {
					status = status.substring(0, 139);
					Config.debug(TAG, "内容被截断");
					Config.toast(activity, "内容被截断", Toast.LENGTH_SHORT);
				}
				// 启动异步任务发送数据
				new SendCommentAsyncTask(activity, currentUser, conver(
						currentUser, status), type).execute();
				// 关闭
				activity.finish();
			}
			break;
		case R.id.write_account:// 账户选择
			showDialog(DIALOG_ID);
			if (frame_isShow) {
				// 隐藏
				hideEmotions();
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
