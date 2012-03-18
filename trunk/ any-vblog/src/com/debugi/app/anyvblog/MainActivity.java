package com.debugi.app.anyvblog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.debugi.app.anyvblog.asynctask.SendAsyncTask;
import com.debugi.app.anyvblog.exception.NormalException;
import com.debugi.app.anyvblog.exception.ResponseException;
import com.debugi.app.anyvblog.model.SendData;
import com.debugi.app.anyvblog.model.UnReadAdapter;
import com.debugi.app.anyvblog.model.netease.UnReadData4Netease;
import com.debugi.app.anyvblog.model.sina.SendUnReadData4Sina;
import com.debugi.app.anyvblog.model.sina.UnReadData4Sina;
import com.debugi.app.anyvblog.model.sohu.UnReadData4Sohu;
import com.debugi.app.anyvblog.model.tencent.GetUnRead4Tencent;
import com.debugi.app.anyvblog.model.tencent.SendUnRead4Tencent;
import com.debugi.app.anyvblog.update.UpdateAsyncTask;
import com.debugi.app.anyvblog.utils.Config;
import com.debugi.app.anyvblog.utils.Constants;
import com.debugi.app.anyvblog.utils.MyHandler;
import com.debugi.open.oauth.model.HttpType;
import com.debugi.open.oauth.model.Response;
import com.google.gson.Gson;

public class MainActivity extends TabActivity implements
		OnCheckedChangeListener {

	private static final String TAG = "MainActivity";
	final Activity activity = this;
	private TabHost host;
	private Intent tab_home;
	private Intent tab_mentions;
	private Intent tab_private;
	private Intent tab_public;
	private Intent tab_more;

	private SendData sendData;
	private String url;
	private Handler handler;
	private AsyncTask<Void, Void, Response> asyncTask = null;
	private FrameLayout unread_bar;
	private TextView unread_home;
	private TextView unread_mentions;
	private TextView unread_directmessage;
	private TextView unread_followers;

	private AlertDialog.Builder builder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabhost);

		// ~~~~~~~~~~~~ 初始化
		this.tab_home = new Intent(this, HomeActivity.class);
		this.tab_mentions = new Intent(this, MentionsActivity.class);
		this.tab_private = new Intent(this, DirectMessagesActivity.class);
		this.tab_more = new Intent(this, InfoActivity.class);
		this.tab_public = new Intent(this, PublicActivity.class);
		initRadios();

		initViews();

		handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
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
						Config.debug(TAG,
								response.getCode() + " " + response.getBody());
						showData(parseData(response));
					} catch (Exception e) {
						throw new NormalException(e.getMessage());
					}
					break;
				case MyHandler.Handler_Clear_Count_Home:
					if (unread_home != null) {
						unread_home.setVisibility(View.GONE);
					}
					break;
				case MyHandler.Handler_Clear_Count_Mentions:
					if (unread_mentions != null) {
						unread_mentions.setVisibility(View.GONE);
					}
					break;
				case MyHandler.Handler_Clear_Count_Directmessages:
					if (unread_directmessage != null) {
						unread_directmessage.setVisibility(View.GONE);
					}
					break;
				case MyHandler.Handler_Clear_Count_Followers:
					if (unread_followers != null) {
						unread_followers.setVisibility(View.GONE);
					}
					break;
				case MyHandler.Handler_Get_UnRead_Count:
					// sendRequest();
					break;
				case MyHandler.Handler_Clear_Count_All:
					if (unread_bar != null) {
						unread_bar.setVisibility(View.GONE);
					}
					break;
				case MyHandler.Handler_Back:
					if (builder == null) {
						return;
					}
					AlertDialog dialog = builder.create();
					dialog.show();
					break;
				default:
					break;
				}
			};
		};
		builder = new AlertDialog.Builder(activity);

		MyHandler.handler = handler;
		if (Config.currentUser == null) {
			Config.debug(TAG, "user is null");
		} else {
			// sendRequest();
		}
		setupIntent();
		builder.setMessage("确定要退出程序?").setCancelable(true)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						saveDefaultAccount();
						activity.finish();
						System.exit(0);
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		if (Config.autoCheckUpdate) {
			checkUpdate(0);
		}
		if (!Config.isAlreadySeeNews(activity)) {
			Intent intent = new Intent(activity, UpdatedInfoActivity.class);
			activity.startActivity(intent);
			Config.alreadySeeNews(activity);
		}
	}

	private void checkUpdate(int p) {
		new UpdateAsyncTask(activity, p).execute();

	}

	private void initViews() {
		unread_bar = (FrameLayout) findViewById(R.id.unread_bar);
		unread_home = (TextView) findViewById(R.id.unread_home);
		unread_mentions = (TextView) findViewById(R.id.unread_mentions);
		unread_directmessage = (TextView) findViewById(R.id.unread_directmessage);
		unread_followers = (TextView) findViewById(R.id.unread_followers);
	}

	/**
	 * 初始化底部按钮
	 */
	private void initRadios() {
		((RadioButton) findViewById(R.id.radio_btn_home))
				.setOnCheckedChangeListener(this);
		((RadioButton) findViewById(R.id.radio_btn_mentions))
				.setOnCheckedChangeListener(this);
		((RadioButton) findViewById(R.id.radio_btn_private))
				.setOnCheckedChangeListener(this);
		((RadioButton) findViewById(R.id.radio_btn_public))
				.setOnCheckedChangeListener(this);
		((RadioButton) findViewById(R.id.radio_btn_more))
				.setOnCheckedChangeListener(this);
	}

	/**
	 * 切换模块
	 */
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			switch (buttonView.getId()) {
			case R.id.radio_btn_home:
				Config.debug(TAG, "on click home");
				this.host.setCurrentTabByTag("tab_home");
				this.unread_home.setVisibility(View.GONE);
				break;
			case R.id.radio_btn_mentions:
				this.host.setCurrentTabByTag("tab_mentions");
				this.unread_mentions.setVisibility(View.GONE);
				break;
			case R.id.radio_btn_private:
				this.host.setCurrentTabByTag("tab_private");
				this.unread_directmessage.setVisibility(View.GONE);
				break;
			case R.id.radio_btn_public:
				this.host.setCurrentTabByTag("tab_public");
				break;
			case R.id.radio_btn_more:
				this.host.setCurrentTabByTag("tab_more");
				this.unread_followers.setVisibility(View.GONE);
				break;
			}
		}
	}

	private void setupIntent() {
		this.host = getTabHost();
		TabHost localTabHost = this.host;

		localTabHost.addTab(buildTabSpec("tab_home", R.string.main_home,
				R.drawable.tab_home_normal, this.tab_home));

		localTabHost.addTab(buildTabSpec("tab_mentions",
				R.string.main_mentions, R.drawable.tab_mentions_normal,
				this.tab_mentions));

		localTabHost.addTab(buildTabSpec("tab_private", R.string.main_private,
				R.drawable.tab_private_normal, this.tab_private));

		localTabHost.addTab(buildTabSpec("tab_public", R.string.main_public,
				R.drawable.tab_public_normal, this.tab_public));

		localTabHost.addTab(buildTabSpec("tab_more", R.string.main_more,
				R.drawable.tab_more_normal, this.tab_more));

	}

	private TabHost.TabSpec buildTabSpec(String tag, int resLabel, int resIcon,
			final Intent content) {
		return this.host
				.newTabSpec(tag)
				.setIndicator(getString(resLabel),
						getResources().getDrawable(resIcon))
				.setContent(content);
	}

	private void showData(UnReadAdapter adapter) {
		if (adapter.sum() == 0) {
			Config.debug(TAG, "no data for show");
			unread_bar.setVisibility(View.GONE);
		} else {
			unread_bar.setVisibility(View.VISIBLE);
			if (adapter.getMentions() > 0) {
				unread_mentions.setVisibility(View.VISIBLE);
				unread_mentions.setText(adapter.getMentions()
						+ adapter.getComments() + "");
			} else {
				unread_mentions.setVisibility(View.GONE);
			}
			if (adapter.getStatus() > 0) {
				unread_home.setVisibility(View.VISIBLE);
				unread_home.setText(adapter.getStatus() + "");
			} else {
				unread_home.setVisibility(View.GONE);
			}
			if (adapter.getDirectmessages() > 0) {
				unread_directmessage.setVisibility(View.VISIBLE);
				unread_directmessage.setText(adapter.getDirectmessages() + "");
			} else {
				unread_directmessage.setVisibility(View.GONE);
			}
			if (adapter.getFollowers() > 0) {
				unread_followers.setVisibility(View.VISIBLE);
				unread_followers.setText(adapter.getFollowers() + "");
			} else {
				unread_followers.setVisibility(View.GONE);
			}
		}
	}

	/**
	 * 解析数据
	 * 
	 * @param response
	 * @return
	 */
	private UnReadAdapter parseData(Response response) {
		if (response == null) {
			Config.debug(TAG, "严重错误");
			return null;
		}
		UnReadAdapter adapter = null;
		if (response.getCode() != 200) {
			Config.debug(TAG, response.getCode() + " " + response.getBody());
		} else {

			Gson gson = new Gson();
			switch (Config.currentUser.getSp()) {
			case Constants.SP_TENCENT:
				GetUnRead4Tencent unRead4Tencent = gson.fromJson(
						response.getBody(), GetUnRead4Tencent.class);
				if (unRead4Tencent.getRet() == 0) {
					adapter = unRead4Tencent.getData().conver();
				}
				break;
			case Constants.SP_SINA:
				UnReadData4Sina unReadData4Sina = gson.fromJson(
						response.getBody(), UnReadData4Sina.class);
				if (unReadData4Sina != null) {
					adapter = unReadData4Sina.conver();
				}
				break;
			case Constants.SP_NETEASE:
				UnReadData4Netease unReadData4Netease = gson.fromJson(
						response.getBody(), UnReadData4Netease.class);
				if (unReadData4Netease != null) {
					adapter = unReadData4Netease.conver();
				}
				break;
			case Constants.SP_SOHU:
				UnReadData4Sohu unReadData4Sohu = gson.fromJson(
						response.getBody(), UnReadData4Sohu.class);
				if (unReadData4Sohu != null) {
					adapter = unReadData4Sohu.conver();
				}
				break;

			default:
				break;
			}
		}
		return adapter;
	}

	/**
	 * 装配数据
	 */
	private void conver() {
		switch (Config.currentUser.getSp()) {
		case Constants.SP_TENCENT:
			sendData = new SendUnRead4Tencent(1, 5);
			url = Constants.Tencent.UNREAD;
			break;
		case Constants.SP_SINA:
			sendData = new SendUnReadData4Sina(1);
			url = Constants.Sina.UNREAD;
			break;
		case Constants.SP_NETEASE:
			sendData = null;
			url = Constants.NetEase.UNREAD;
			break;
		case Constants.SP_SOHU:
			sendData = null;
			url = Constants.Sohu.UNREAD;
			break;
		default:
			break;
		}
	}

	private void sendRequest() {
		conver();
		asyncTask = new SendAsyncTask(activity, sendData, url, HttpType.GET,
				Config.currentUser, handler).execute();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Config.debug(TAG, "click back catch on main");
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu_Group_1, Menu_Item_About, 3, "关于程序");
		menu.add(Menu_Group_1, Menu_Item_Oauth, 2, "授权管理");
		menu.add(Menu_Group_1, Menu_Item_Exit, 1, "退出程序");
		menu.add(Menu_Group_2, Menu_item_Setting, 1, "程序设置");
		menu.add(Menu_Group_2, Menu_item_Update, 2, "检查更新");
		menu.add(Menu_Group_2, Menu_item_Updated, 3, "版本说明");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = null;
		switch (item.getItemId()) {
		case Menu_Item_About:
			intent = new Intent(activity, AboutActivity.class);
			activity.startActivity(intent);
			break;
		case Menu_Item_Oauth:
			intent = new Intent(activity, OAuthActivity.class);
			activity.startActivity(intent);
			break;
		case Menu_Item_Exit:
			AlertDialog dialog = builder.create();
			dialog.show();
			break;
		case Menu_item_Setting:
			intent = new Intent(activity, SettingActivity.class);
			activity.startActivity(intent);
			break;
		case Menu_item_Update:
			Toast.makeText(activity, "正在检查更新……", Toast.LENGTH_SHORT).show();
			checkUpdate(1);
			break;
		case Menu_item_Updated:
			Intent intent2 = new Intent(activity, UpdatedInfoActivity.class);
			activity.startActivity(intent2);
			Config.alreadySeeNews(activity);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 保存默认账户
	 */
	private void saveDefaultAccount() {
		Log.i(TAG, "save current user at main...");
		SharedPreferences sharedPreferences = PreferenceManager
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

	private static final int Menu_Group_1 = 1;
	private static final int Menu_Group_2 = 2;
	private static final int Menu_Item_About = 0x11;
	private static final int Menu_Item_Oauth = 0x12;
	private static final int Menu_Item_Exit = 0x13;
	private static final int Menu_item_Setting = 0x21;
	private static final int Menu_item_Update = 0x22;
	private static final int Menu_item_Updated = 0x23;
}