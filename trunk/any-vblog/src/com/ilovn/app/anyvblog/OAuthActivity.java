package com.ilovn.app.anyvblog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ilovn.app.anyvblog.R;
import com.ilovn.app.anyvblog.adapter.AccountAdapter;
import com.ilovn.app.anyvblog.adapter.OAuthSpAdapter;
import com.ilovn.app.anyvblog.application.ActivityStack;
import com.ilovn.app.anyvblog.asynctask.SendAsyncTask;
import com.ilovn.app.anyvblog.db.DataHelper;
import com.ilovn.app.anyvblog.exception.NormalException;
import com.ilovn.app.anyvblog.exception.ResponseException;
import com.ilovn.app.anyvblog.model.SendData;
import com.ilovn.app.anyvblog.model.UserAdapter;
import com.ilovn.app.anyvblog.model.netease.SendGet_UserInfo4Netease;
import com.ilovn.app.anyvblog.model.netease.UserInfo4Netease;
import com.ilovn.app.anyvblog.model.sina.SendGet_UserInfo4Sina;
import com.ilovn.app.anyvblog.model.sina.UserInfo4Sina;
import com.ilovn.app.anyvblog.model.sohu.SendGet_UserInfo4Sohu;
import com.ilovn.app.anyvblog.model.sohu.UserInfo4Sohu;
import com.ilovn.app.anyvblog.model.tencent.GetUserInfo4Tencent;
import com.ilovn.app.anyvblog.model.tencent.SendGet_UserInfo4Tencent;
import com.ilovn.app.anyvblog.oauth.OauthModel4Netease;
import com.ilovn.app.anyvblog.oauth.OauthModel4Sina;
import com.ilovn.app.anyvblog.oauth.OauthModel4Sohu;
import com.ilovn.app.anyvblog.oauth.OauthModel4Tencent;
import com.ilovn.app.anyvblog.oauth.RetrieveAccessTokenTask;
import com.ilovn.app.anyvblog.utils.Config;
import com.ilovn.app.anyvblog.utils.Constants;
import com.ilovn.app.anyvblog.utils.ImageUtils;
import com.ilovn.app.anyvblog.utils.MyHandler;
import com.ilovn.open.oauth.OAuthServiceProvider;
import com.ilovn.open.oauth.model.HttpType;
import com.ilovn.open.oauth.model.Response;
import com.ilovn.open.oauth.model.Token;

public class OAuthActivity extends Activity implements OnClickListener {
	private static final String TAG = "OAuthActivity";
	private final Activity activity = this;
	private OAuthServiceProvider serviceProvider;
	private int sp;
	private Token token;
	private Spinner spinner;
	private List<Map<String, Object>> data;
	private Button startOauth;
	private Intent intent;
	private Handler handler;
	private AsyncTask<Void, Void, Response> result;
	private Response response;
	private SendData sendData;
	private UserAdapter adapter;
	private DataHelper dataHelper;
	private Gson gson;
	private Token accessToken;
	private ListView account_list;
	private Button btn_back;
	private Button btn_home;
	private List<UserAdapter> users;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.oauth);
		ActivityStack.getInstance().addActivity(activity);
		account_list = (ListView) findViewById(R.id.account_list);
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_home = (Button) findViewById(R.id.btn_home);
		btn_back.setOnClickListener(this);
		btn_home.setOnClickListener(this);
		handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case MyHandler.Handler_GetData:
					if (result == null) {
						Config.debug(TAG, "result is null");
						return;
					}
					try {
						response = result.get();
						if (response == null || response.getCode() != 200) {
							if (response == null) {
								return;
							}
							String tip = new ResponseException(response,
									sp).getMessage();
							Config.toast(activity, "程序出现异常" + tip, Toast.LENGTH_SHORT);
							return;
						}
						parseData();
						saveData();
					} catch (Exception e) {
						throw new NormalException(e.getMessage());
					}
					break;

				default:
					break;
				}
			};
		};
		spinner = (Spinner) findViewById(R.id.oauth_sp);
		startOauth = (Button) findViewById(R.id.startOauth);
		startOauth.setOnClickListener(this);

		Map<String, Object> data1 = new HashMap<String, Object>();
		data1.put("icon", R.drawable.tencent_icon_32);
		data1.put("name", "腾讯微博");
		data1.put("sp", Constants.SP_TENCENT);
		Map<String, Object> data2 = new HashMap<String, Object>();
		data2.put("icon", R.drawable.sina_icon_32);
		data2.put("name", "新浪微博");
		data2.put("sp", Constants.SP_SINA);
		Map<String, Object> data3 = new HashMap<String, Object>();
		data3.put("icon", R.drawable.netease_icon_32);
		data3.put("name", "网易微博");
		data3.put("sp", Constants.SP_NETEASE);
		Map<String, Object> data4 = new HashMap<String, Object>();
		data4.put("icon", R.drawable.sohu_icon_32);
		data4.put("name", "搜狐微博");
		data4.put("sp", Constants.SP_SOHU);
		data = new ArrayList<Map<String, Object>>();
		data.add(data1);
		data.add(data2);
		data.add(data3);
		data.add(data4);

		spinner.setAdapter(new OAuthSpAdapter(activity, data,
				R.layout.oauth_item));

		intent = new Intent(activity, BrowserActivity.class);

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				sp = Integer.parseInt(data.get(position).get("sp").toString());

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}

		});
		fillData();
	}

	/**
	 * 当授权后，回调该Activity 判断授权是否成功， 启动获取accessToken的异步任务类完成授权认证
	 */
	@Override
	protected void onNewIntent(Intent intent) {
		fillData();
		if (intent.getExtras() == null
				|| !intent.getExtras().containsKey("url")) {
			return;
		}
		String url = intent.getExtras().getString("url");
		if (url != null && sp > 0) {
			AsyncTask<Uri, Void, Token> asyncTask = new RetrieveAccessTokenTask(
					serviceProvider, token).execute(Uri.parse(url));
			if (asyncTask == null) {
				Config.debug(TAG, "RetrieveAccessTokenTask is null");
				return;
			}
			try {
				accessToken = asyncTask.get();
			} catch (Exception e) {
				throw new NormalException(e.getMessage());
			}
		}

		Config.debug(TAG, "intent token =" + accessToken);
		if (accessToken != null) {
			switch (sp) {
			case Constants.SP_TENCENT:
				sendData = new SendGet_UserInfo4Tencent();
				getUserInfo(Constants.Tencent.GET_USERINFO, sp);
				break;
			case Constants.SP_SINA:
				// 获取授权成功后返回的user_id
				String user_id = accessToken.getRawResponse();
				user_id = user_id.substring(user_id.lastIndexOf("=") + 1);
				sendData = new SendGet_UserInfo4Sina(user_id);
				getUserInfo(Constants.Sina.GET_USERINFO, sp);
				break;
			case Constants.SP_NETEASE:
				sendData = new SendGet_UserInfo4Netease(null);
				getUserInfo(Constants.NetEase.GET_USERINFO, sp);
				break;
			case Constants.SP_SOHU:
				sendData = new SendGet_UserInfo4Sohu();
				getUserInfo(Constants.Sohu.GET_USERINFO, sp);
				break;

			default:
				break;
			}
		} else {
			Config.debug(TAG, "accessToken is null");
		}
		Log.i(TAG, "get token&secret");
		// 完成后关闭本Activity
		// finish();

	}

	/**
	 * 网络获取用户信息
	 * 
	 * @param url
	 * @param sp
	 */
	private void getUserInfo(String url, int sp) {
		UserAdapter temp = new UserAdapter();
		temp.setSp(sp);
		temp.setToken(accessToken.getToken());
		temp.setToken_secret(accessToken.getToken_secret());
		Config.debug(TAG, temp);
		result = new SendAsyncTask(activity, sendData, url, HttpType.GET, temp,
				handler).execute();
	}

	/**
	 * 解析数据
	 */
	private void parseData() {
		Config.debug(TAG, "parse data start!");
		dataHelper = new DataHelper(activity);
		try {
			gson = new Gson();
			switch (sp) {
			case Constants.SP_TENCENT:
				if (response.getCode() == 200) {
					Config.debug(TAG, response.getBody());
					GetUserInfo4Tencent info4Tencent = gson.fromJson(
							response.getBody(), GetUserInfo4Tencent.class);
					if (info4Tencent.getRet() == 0) {
						adapter = info4Tencent.getData().conver2User();
					}
				} else {
					Config.debug(TAG,
							response.getCode() + " " + response.getBody());
				}
				break;
			case Constants.SP_SINA:
				if (response.getCode() == 200) {

					UserInfo4Sina userInfo4Sina = gson.fromJson(
							response.getBody(), UserInfo4Sina.class);
					adapter = userInfo4Sina.conver2User();
				} else {
					Config.debug(TAG,
							response.getCode() + " " + response.getBody());
				}
				break;
			case Constants.SP_NETEASE:
				if (response.getCode() == 200) {
					UserInfo4Netease info4Netease = gson.fromJson(
							response.getBody(), UserInfo4Netease.class);
					adapter = info4Netease.conver2User();
				} else {
					Config.debug(TAG,
							response.getCode() + " " + response.getBody());
				}
				break;
			case Constants.SP_SOHU:
				if (response.getCode() == 200) {
					UserInfo4Sohu info4Sohu = gson.fromJson(response.getBody(),
							UserInfo4Sohu.class);
					adapter = info4Sohu.conver2User();
				} else {
					Config.debug(TAG,
							response.getCode() + " " + response.getBody());
				}
				break;
			default:
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		Config.debug(TAG, "parse data finish");
	}

	/**
	 * 保存数据
	 */
	private void saveData() {
		if (accessToken == null) {
			return;
		}
		adapter.setToken(accessToken.getToken());
		adapter.setToken_secret(accessToken.getToken_secret());
		if (adapter.getHead_url() != null
				&& URLUtil.isNetworkUrl(adapter.getHead_url())) {
			Bitmap head = ImageUtils.loadImageFromUrl(adapter.getHead_url());
			adapter.setHead(head);
			Config.debug(TAG, "get user head");
		}
		long _id = dataHelper.saveAccount(adapter);
		Config.debug(TAG, "_id" + _id);
		Config.currentUser = adapter;
		dataHelper.close();
		fillData();
	}

	/**
	 * 装配已授权账户
	 */
	private void fillData() {
		dataHelper = new DataHelper(activity);
		users = dataHelper.loadUsers();
		dataHelper.close();
		AccountAdapter accountAdapter = new AccountAdapter(activity, users,
				R.layout.accounts_item);
		account_list.setAdapter(accountAdapter);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.startOauth:
			Config.toast(activity, "你将要前往服务商授权页面,此过程持续时间和网络相关,请耐心等待", Toast.LENGTH_LONG);
			String url;
			switch (sp) {
			case Constants.SP_TENCENT:
				serviceProvider = new OauthModel4Tencent().getServiceProvider();
				break;
			case Constants.SP_SINA:
				serviceProvider = new OauthModel4Sina().getServiceProvider();
				break;
			case Constants.SP_NETEASE:
				serviceProvider = new OauthModel4Netease().getServiceProvider();
				break;
			case Constants.SP_SOHU:
				serviceProvider = new OauthModel4Sohu().getServiceProvider();
				break;
			default:
				break;
			}
			token = serviceProvider.getRequestToken();
			url = serviceProvider.getAuthorizationUrl(token);
			intent.putExtra("url", url);
			activity.startActivity(intent);
			break;
		case R.id.btn_back:
			activity.finish();
			break;
		case R.id.btn_home:
			if (users == null || users.size() <= 0) {
				Config.toast(activity, "还没有授权账户,请授权或退出", Toast.LENGTH_SHORT);
				break;
			}
			Config.isNeedFresh = true;
			intent = new Intent(activity, MainActivity.class);
			intent.putExtra("fresh", true);
			activity.startActivity(intent);
			activity.finish();
			break;
		default:
			break;
		}

	}
}
