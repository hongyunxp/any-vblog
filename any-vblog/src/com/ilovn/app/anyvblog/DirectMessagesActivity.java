package com.ilovn.app.anyvblog;

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

import com.ilovn.app.anyvblog.R;
import com.ilovn.app.anyvblog.application.ActivityStack;
import com.ilovn.app.anyvblog.asynctask.GetDirectMessagesAsyncTask;
import com.ilovn.app.anyvblog.model.SendData;
import com.ilovn.app.anyvblog.model.UserAdapter;
import com.ilovn.app.anyvblog.model.netease.SendDirectMessages4Netease;
import com.ilovn.app.anyvblog.model.sohu.SendDirectMessages4Sohu;
import com.ilovn.app.anyvblog.model.tencent.SendDirectMessages4Tencent;
import com.ilovn.app.anyvblog.utils.Config;
import com.ilovn.app.anyvblog.utils.Constants;
import com.ilovn.app.anyvblog.utils.MyHandler;
import com.ilovn.app.anyvblog.widget.CustomListView;

public class DirectMessagesActivity extends Activity {
	private static final String TAG = "DirectMessagesActivity";
	private static final int TYPE_RECV = 0x001;
	private static final int TYPE_SENT = 0x002;
	private CustomListView directmessages_list;
	private SendData sendData;
	final Activity activity = this;
	private Button directmessages_edit;
	private int count;
	private int type;
	private UserAdapter curUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.directmessages);
		ActivityStack.getInstance().addActivity(activity);
		initViews();
		curUser = Config.currentUser;
		count = 20;
		sendRequest();
		directmessages_edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Intent intent = new Intent(activity,
				// UserSelectActivity.class);
				// activity.startActivity(intent);
				Config.toast(activity, "暂不支持写私信，请关注后续版本", Toast.LENGTH_SHORT);
			}
		});
	}

	private void initViews() {
		directmessages_list = (CustomListView) findViewById(R.id.directmessages_list);
		directmessages_edit = (Button) findViewById(R.id.directmessages_edit);
	}

	private void sendRequest() {
		switch (Config.currentUser.getSp()) {
		case Constants.SP_TENCENT:
			sendData = new SendDirectMessages4Tencent(count);
			break;
		case Constants.SP_SINA:
			Config.toast(activity, "新浪微博私信接口已关闭,请关注后续版本", Toast.LENGTH_SHORT);
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
