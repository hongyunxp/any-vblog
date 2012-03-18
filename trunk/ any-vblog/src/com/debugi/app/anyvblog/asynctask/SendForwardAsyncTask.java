package com.debugi.app.anyvblog.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.debugi.app.anyvblog.exception.ResponseException;
import com.debugi.app.anyvblog.model.SendData;
import com.debugi.app.anyvblog.model.UserAdapter;
import com.debugi.app.anyvblog.model.netease.GetForward4Netease;
import com.debugi.app.anyvblog.model.netease.GetSendOneData4Netease;
import com.debugi.app.anyvblog.model.sina.GetSendOneData4Sina;
import com.debugi.app.anyvblog.model.sohu.GetSendOneData4Sohu;
import com.debugi.app.anyvblog.model.tencent.GetSendOne4Tencent;
import com.debugi.app.anyvblog.oauth.OauthModel4Netease;
import com.debugi.app.anyvblog.oauth.OauthModel4Sina;
import com.debugi.app.anyvblog.oauth.OauthModel4Sohu;
import com.debugi.app.anyvblog.oauth.OauthModel4Tencent;
import com.debugi.app.anyvblog.utils.Config;
import com.debugi.app.anyvblog.utils.Constants;
import com.debugi.app.anyvblog.utils.NetworkUtils;
import com.debugi.open.oauth.OAuthServiceProvider;
import com.debugi.open.oauth.model.HttpType;
import com.debugi.open.oauth.model.OAuthRequest;
import com.debugi.open.oauth.model.Response;
import com.debugi.open.oauth.model.Token;
import com.google.gson.Gson;

public class SendForwardAsyncTask extends AsyncTask<Void, Void, Response> {
	private static final String TAG = "SendForwardAsyncTask";
	private Context context;
	private OAuthServiceProvider serviceProvider;
	private Token accessToken;
	private UserAdapter user;
	private SendData sendData;
	private OAuthRequest request;
	private Response response;
	private int sp;
	private int type;
	private String id;

	public SendForwardAsyncTask(Context context, UserAdapter user,
			SendData sendData, int type, String id) {
		this.context = context;
		this.user = user;
		this.sendData = sendData;
		this.sp = user.getSp();
		this.type = type;
		this.id = id;
	}

	@Override
	protected Response doInBackground(Void... params) {
		preSend();
		Config.debug(TAG, "type=" + type);
		request.addBodyParameters(sendData.conver());
		Config.debug(TAG, request.getUrl());
		serviceProvider.signRequest(accessToken, request);
		if (NetworkUtils.checkEnable(context)) {
			Config.debug(TAG, "start send request");
			response = request.send();
		} else {
			Toast.makeText(context, "当前网络不可用，请检查网络连接", Toast.LENGTH_SHORT)
					.show();
			return null;
		}
		return response;
	}

	@Override
	protected void onPostExecute(Response result) {
		Gson gson = new Gson();
		if (response == null || response.getCode() != 200) {
			if (response == null) {
				return;
			}
			String tip = new ResponseException(response, user.getSp())
					.getMessage();
			Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
			return;
		}
		try {
			Config.debug(TAG, response.getCode() + response.getBody());
			switch (sp) {
			case Constants.SP_TENCENT:
				GetSendOne4Tencent one4Tencent = gson.fromJson(
						result.getBody(), GetSendOne4Tencent.class);
				if (one4Tencent.getRet() == 0) {
					Config.debug(TAG, "发送到 " + user.getNick() + "<腾讯> 成功!");
					Toast.makeText(context,
							"发送到 " + user.getNick() + "<腾讯> 成功!",
							Toast.LENGTH_SHORT).show();
				}
				break;
			case Constants.SP_SINA:
				GetSendOneData4Sina data4Sina = gson.fromJson(result.getBody(),
						GetSendOneData4Sina.class);
				if (data4Sina.getId() != null && !"".equals(data4Sina.getId())) {
					Config.debug(TAG, "发送到 " + user.getNick() + "<新浪> 成功!");
					Toast.makeText(context,
							"发送到 " + user.getNick() + "<新浪> 成功!",
							Toast.LENGTH_SHORT).show();
				}
				break;
			case Constants.SP_NETEASE:
				if (type == Constants.FORWARD_TYPE_F) {
					GetForward4Netease forward4Netease = gson.fromJson(
							result.getBody(), GetForward4Netease.class);
					if (forward4Netease.getRetweeted_status() != null
							&& !"".equals(forward4Netease.getRetweeted_status())) {
						Config.debug(TAG, "发送到 " + user.getNick() + "<网易> 成功!");
						Toast.makeText(context,
								"发送到 " + user.getNick() + "<网易> 成功!",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					GetSendOneData4Netease data4Netease = gson.fromJson(
							result.getBody(), GetSendOneData4Netease.class);
					if (data4Netease.getId() != null
							&& !"".equals(data4Netease.getId())) {
						Config.debug(TAG, "发送到 " + user.getNick() + "<网易> 成功!");
						Toast.makeText(context,
								"发送到 " + user.getNick() + "<网易> 成功!",
								Toast.LENGTH_SHORT).show();
					}
				}
				break;
			case Constants.SP_SOHU:
				GetSendOneData4Sohu data4Sohu = gson.fromJson(result.getBody(),
						GetSendOneData4Sohu.class);
				if (data4Sohu.getId() != null && !"".equals(data4Sohu.getId())) {
					Config.debug(TAG, "发送到 " + user.getNick() + "<搜狐> 成功!");
					Toast.makeText(context,
							"发送到 " + user.getNick() + "<搜狐> 成功!",
							Toast.LENGTH_SHORT).show();
				}
				break;

			default:
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 发送前准备
	 */
	private void preSend() {
		accessToken = user.getAccessToken();
		Config.debug(TAG, accessToken);
		switch (sp) {
		case Constants.SP_TENCENT:
			serviceProvider = new OauthModel4Tencent().getServiceProvider();
			if (type == Constants.FORWARD_TYPE_F) {
				request = new OAuthRequest(HttpType.POST,
						Constants.Tencent.FORWARD);
			} else {
				request = new OAuthRequest(HttpType.POST,
						Constants.Tencent.SEND_ONE);
			}
			break;
		case Constants.SP_SINA:
			serviceProvider = new OauthModel4Sina().getServiceProvider();
			request = new OAuthRequest(HttpType.POST, Constants.Sina.SEND_ONE);
			break;
		case Constants.SP_NETEASE:
			serviceProvider = new OauthModel4Netease().getServiceProvider();
			if (type == Constants.FORWARD_TYPE_F) {
				request = new OAuthRequest(HttpType.POST, String.format(
						Constants.NetEase.FORWARD, id));
			} else {
				request = new OAuthRequest(HttpType.POST,
						Constants.NetEase.SEND_ONE);
			}
			break;
		case Constants.SP_SOHU:
			serviceProvider = new OauthModel4Sohu().getServiceProvider();
			if (type == Constants.FORWARD_TYPE_F) {
				request = new OAuthRequest(HttpType.POST, String.format(
						Constants.Sohu.FORWARD, id));
			} else {
				request = new OAuthRequest(HttpType.POST,
						Constants.Sohu.SEND_ONE);
			}
			break;

		default:
			break;
		}
	}

}
