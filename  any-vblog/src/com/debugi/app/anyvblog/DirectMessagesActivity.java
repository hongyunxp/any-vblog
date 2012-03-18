package com.debugi.app.anyvblog;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.Toast;

import com.debugi.app.anyvblog.asynctask.GetDirectMessagesAsyncTask;
import com.debugi.app.anyvblog.model.SendData;
import com.debugi.app.anyvblog.model.UserAdapter;
import com.debugi.app.anyvblog.model.netease.SendDirectMessages4Netease;
import com.debugi.app.anyvblog.model.sohu.SendDirectMessages4Sohu;
import com.debugi.app.anyvblog.model.tencent.SendDirectMessages4Tencent;
import com.debugi.app.anyvblog.utils.Config;
import com.debugi.app.anyvblog.utils.Constants;
import com.debugi.app.anyvblog.utils.MyHandler;
import com.debugi.app.anyvblog.widget.CustomListView;

public class DirectMessagesActivity extends Activity implements
		OnCheckedChangeListener {
	private static final String TAG = "DirectMessagesActivity";
	private RadioButton directmessages_type_recv;
	private RadioButton directmessages_type_sent;
	private CustomListView directmessages_list;
	private SendData sendData;
	final Activity activity = this;
	private Button directmessages_edit;
	private int count;
	private int type;
	private int curBtn;
	private UserAdapter curUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.directmessages);
		initViews();
		curUser = Config.currentUser;
		curBtn = R.id.directmessages_type_recv;
		type = Constants.DIRECTMESSAGE_RECV;
		count = 20;
		sendRequest();
		directmessages_edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent intent = new Intent(activity, UserSelectActivity.class);
//				activity.startActivity(intent);
				Toast.makeText(activity, "暂不支持写私信，请关注后续版本", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void initViews() {
		directmessages_type_recv = (RadioButton) findViewById(R.id.directmessages_type_recv);
		directmessages_type_sent = (RadioButton) findViewById(R.id.directmessages_type_sent);
		directmessages_list = (CustomListView) findViewById(R.id.directmessages_list);
		directmessages_edit = (Button) findViewById(R.id.directmessages_edit);
		directmessages_type_recv.setOnCheckedChangeListener(this);
		directmessages_type_sent.setOnCheckedChangeListener(this);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			type = Constants.DIRECTMESSAGE_RECV; // 1=收件箱 2=发件箱
			switch (buttonView.getId()) {
			case R.id.directmessages_type_recv:
				type = Constants.DIRECTMESSAGE_RECV;
				break;
			case R.id.directmessages_type_sent:
				type = Constants.DIRECTMESSAGE_SENT;
				break;
			default:
				break;
			}
			curBtn = buttonView.getId();
			sendRequest();
		}
	}
	private void sendRequest() {
		switch (Config.currentUser.getSp()) {
		case Constants.SP_TENCENT:
			sendData = new SendDirectMessages4Tencent(count);
			break;
		case Constants.SP_SINA:
			Toast.makeText(activity, "新浪微博私信接口已关闭,请关注后续版本",
					Toast.LENGTH_SHORT).show();
			Config.debug(TAG, "新浪微博私信接口已关闭,请关注后续版本");
			break;
		case Constants.SP_NETEASE:
			sendData = new SendDirectMessages4Netease(count);
			break;
		case Constants.SP_SOHU:
			sendData = new SendDirectMessages4Sohu(count);
			break;
		default:
			break;
		}
		if (Config.currentUser.getSp() != Constants.SP_SINA) {
			new GetDirectMessagesAsyncTask(activity, directmessages_list,
					Config.currentUser, sendData, type).execute();
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Config.debug(TAG, "click back catch on directmsg");
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
		if (!curUser.getUser_id().equals(Config.currentUser.getUser_id())) {
			curUser = Config.currentUser;
			sendRequest();
		}
		super.onResume();
	}
}
