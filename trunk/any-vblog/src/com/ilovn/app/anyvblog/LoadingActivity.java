package com.ilovn.app.anyvblog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.ilovn.app.anyvblog.R;
import com.ilovn.app.anyvblog.application.ActivityStack;
import com.ilovn.app.anyvblog.db.DataHelper;
import com.ilovn.app.anyvblog.model.UserAdapter;
import com.ilovn.app.anyvblog.utils.Config;
import com.ilovn.app.anyvblog.utils.NetworkUtils;
/**
 * 启动界面，做一些初始化工作，如检查网络、调试模式、默认账户等
 * @author zhaoyong
 *
 */
public class LoadingActivity extends Activity {
	private static final String TAG = "LogoActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
		ActivityStack.getInstance().addActivity(this);
		//开启调试，程序将输出调试信息
		Config.debug = false;
		Config.showPic = Config.isShowPic(LoadingActivity.this);
		Config.autoCheckUpdate = Config.isAutoCheckUpdate(LoadingActivity.this);
		final Intent intent;
		DataHelper helper = new DataHelper(this);
		if (!helper.haveAccount()) {
			intent = new Intent(LoadingActivity.this, OAuthActivity.class);
		} else {
			intent = new Intent(LoadingActivity.this, MainActivity.class);
		}
		if (NetworkUtils.checkEnable(LoadingActivity.this)) {
			for (String ip : NetworkUtils.getIpAddress()) {
				Config.debug(TAG, "enable network->" + ip);
			}
		} else {
			Config.debug(TAG, "network is not enable");
			Config.toast(LoadingActivity.this, "程序需要网络支持，请连接网络", Toast.LENGTH_SHORT);
		}
		//程序初始化,获取默认账户
		Config.currentUser = UserAdapter.getDefaultUser(this);
		if (Config.currentUser == null) {
			if (helper.loadUsers().size() > 0) {
				Config.currentUser = helper.loadUsers().get(0);
			}
		}
		helper.close();
		Config.cachePath = Config.getCachePath(LoadingActivity.this);
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				
				startActivity(intent);
				finish();
			}
		}, 2 * 1000);
	}
}