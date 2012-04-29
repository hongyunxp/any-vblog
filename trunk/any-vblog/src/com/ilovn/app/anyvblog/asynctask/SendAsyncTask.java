package com.ilovn.app.anyvblog.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.ilovn.app.anyvblog.model.SendData;
import com.ilovn.app.anyvblog.model.UserAdapter;
import com.ilovn.app.anyvblog.oauth.OauthModel4Netease;
import com.ilovn.app.anyvblog.oauth.OauthModel4Sina;
import com.ilovn.app.anyvblog.oauth.OauthModel4Sohu;
import com.ilovn.app.anyvblog.oauth.OauthModel4Tencent;
import com.ilovn.app.anyvblog.utils.Config;
import com.ilovn.app.anyvblog.utils.Constants;
import com.ilovn.app.anyvblog.utils.MyHandler;
import com.ilovn.app.anyvblog.utils.NetworkUtils;
import com.ilovn.open.oauth.OAuthServiceProvider;
import com.ilovn.open.oauth.model.HttpType;
import com.ilovn.open.oauth.model.OAuthRequest;
import com.ilovn.open.oauth.model.Response;
import com.ilovn.open.oauth.model.Token;

public class SendAsyncTask extends AsyncTask<Void, Void, Response> {
	private static final String TAG = "SendAsyncTask";
	private OAuthServiceProvider serviceProvider;
	private Token accessToken;
	private SendData sendData; // 需要发送的数据
	private Context context; // 上下文对象
	private OAuthRequest request; // 请求
	private Response response; // 响应
	private String url; // 请求URL
	private HttpType httpType; // 请求类型（POST/GET）
	private UserAdapter currentUser;
	private Handler handler;

	/**
	 * 方法用与还不能从数据库获取token&token_secret的时候，自行提供Token对象发送请求
	 * 
	 * @param context
	 * @param sendData
	 * @param url
	 * @param httpType
	 * @param sp
	 * @param accessToken
	 */
	public SendAsyncTask(Context context, SendData sendData, String url,
			HttpType httpType, int sp, Token accessToken, Handler handler) {
		UserAdapter adapter = new UserAdapter();
		adapter.setSp(sp);
		this.context = context;
		this.sendData = sendData;
		this.url = url;
		this.httpType = httpType;
		this.accessToken = accessToken;
		this.currentUser = adapter;
		this.handler = handler;
	}

	public SendAsyncTask(Context context, SendData sendData, String url,
			HttpType httpType, UserAdapter currentUser, Handler handler) {
		this.setContext(context);
		this.sendData = sendData;
		this.url = url;
		this.httpType = httpType;
		this.currentUser = currentUser;
		this.handler = handler;
		this.accessToken = this.currentUser.getAccessToken();
	}

	public void init() {
		switch (currentUser.getSp()) {
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
			Config.debug(TAG, "sp error");
			break;
		}
		request = new OAuthRequest(httpType, url);
	}

	@Override
	protected Response doInBackground(Void... params) {
		init();
		if (sendData != null) {
			Config.debug(TAG, "add parameters");
			if (httpType.equals(HttpType.POST)) {
				request.addBodyParameters(sendData.conver());
			} else {
				if (sendData.conver() != null && !"".equals(sendData.conver())) {
					request.addQuerystringParameters(sendData.conver());
				}
			}
		}
		Config.debug(TAG, "start sign the request");
		serviceProvider.signRequest(accessToken, request);
		if (NetworkUtils.checkEnable(context)) {
			Config.debug(TAG, "start send request");
			response = request.send();
		} else {
			Config.toast(context, "当前网络不可用，请检查网络连接", Toast.LENGTH_SHORT);
			return null;
		}
		return response;
	}

	@Override
	protected void onPostExecute(Response result) {
		Message message = new Message();
		message.what = MyHandler.Handler_GetData;
		handler.sendMessage(message);
		super.onPostExecute(result);
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public Context getContext() {
		return context;
	}

}
