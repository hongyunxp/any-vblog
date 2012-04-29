package com.debugi.app.anyvblog.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.debugi.app.anyvblog.model.SendData;
import com.debugi.app.anyvblog.model.UserAdapter;
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

public class SendFavAsyncTask extends AsyncTask<Void, Void, Response> {
	private static final String TAG = "SendFavAsyncTask";
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

	public SendFavAsyncTask(Context context, UserAdapter user,
			SendData sendData, String id, int type) {
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
			Toast.makeText(context, "当前网络不可用，请检查网络连接", Toast.LENGTH_SHORT).show();
			return null;
		}
		return response;
	}

	@Override
	protected void onPostExecute(Response result) {
		Gson gson = new Gson();
		if (result.getCode() == 200) {
			Config.debug(TAG, response.getCode() + response.getBody());
			switch (sp) {
			case Constants.SP_TENCENT:
				GetSendOne4Tencent one4Tencent = gson.fromJson(
						result.getBody(), GetSendOne4Tencent.class);
				if (one4Tencent.getRet() == 0) {
					if (type == Constants.FAV_TYPE_ADD) {
						Config.debug(TAG, "添加收藏成功!");
						Toast.makeText(context, "添加收藏成功!", Toast.LENGTH_SHORT)
								.show();
					} else {
						Config.debug(TAG, "取消收藏成功!");
						Toast.makeText(context, "取消收藏成功!", Toast.LENGTH_SHORT)
								.show();
					}
				}
				break;
			case Constants.SP_SINA:
				GetSendOneData4Sina data4Sina = gson.fromJson(result.getBody(),
						GetSendOneData4Sina.class);
				if (data4Sina.getId() != null && !"".equals(data4Sina.getId())) {
					if (type == Constants.FAV_TYPE_ADD) {
						Config.debug(TAG, "添加收藏成功!");
						Toast.makeText(context, "添加收藏成功!", Toast.LENGTH_SHORT)
								.show();
					} else {
						Config.debug(TAG, "取消收藏成功!");
						Toast.makeText(context, "取消收藏成功!", Toast.LENGTH_SHORT)
								.show();
					}
				}
				break;
			case Constants.SP_NETEASE:
				GetSendOneData4Netease data4Netease = gson.fromJson(
						result.getBody(), GetSendOneData4Netease.class);
				if (data4Netease.getId() != null
						&& !"".equals(data4Netease.getId())) {
					if (type == Constants.FAV_TYPE_ADD) {
						Config.debug(TAG, "添加收藏成功!");
						Toast.makeText(context, "添加收藏成功!", Toast.LENGTH_SHORT)
								.show();
					} else {
						Config.debug(TAG, "取消收藏成功!");
						Toast.makeText(context, "取消收藏成功!", Toast.LENGTH_SHORT)
								.show();
					}
				}
				break;
			case Constants.SP_SOHU:
				GetSendOneData4Sohu data4Sohu = gson.fromJson(result.getBody(),
						GetSendOneData4Sohu.class);
				if (data4Sohu.getId() != null && !"".equals(data4Sohu.getId())) {
					if (type == Constants.FAV_TYPE_ADD) {
						Config.debug(TAG, "添加收藏成功!");
						Toast.makeText(context, "添加收藏成功!", Toast.LENGTH_SHORT)
								.show();
					} else {
						Config.debug(TAG, "取消收藏成功!");
						Toast.makeText(context, "取消收藏成功!", Toast.LENGTH_SHORT)
								.show();
					}
				}
				break;

			default:
				break;
			}
		} else {
			Config.debug(TAG, result.getCode() + "  " + result.getBody());
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
			if (type == Constants.FAV_TYPE_ADD) {
				request = new OAuthRequest(HttpType.POST,
						Constants.Tencent.FAV_ADD);
			} else {
				request = new OAuthRequest(HttpType.POST,
						Constants.Tencent.FAV_DEL);
			}
			break;
		case Constants.SP_SINA:
			serviceProvider = new OauthModel4Sina().getServiceProvider();
			if (type == Constants.FAV_TYPE_ADD) {
				request = new OAuthRequest(HttpType.POST,
						Constants.Sina.FAV_ADD);
			} else {
				request = new OAuthRequest(HttpType.POST,
						String.format(Constants.Sina.FAV_DEL, id));
			}
			break;
		case Constants.SP_NETEASE:
			serviceProvider = new OauthModel4Netease().getServiceProvider();
			if (type == Constants.FAV_TYPE_ADD) {
				request = new OAuthRequest(HttpType.POST,
						String.format(Constants.NetEase.FAV_ADD, id));
			} else {
				request = new OAuthRequest(HttpType.POST,
						String.format(Constants.NetEase.FAV_DEL, id));
			}
			break;
		case Constants.SP_SOHU:
			serviceProvider = new OauthModel4Sohu().getServiceProvider();
			if (type == Constants.FAV_TYPE_ADD) {
				request = new OAuthRequest(HttpType.POST,
						String.format(Constants.Sohu.FAV_ADD, id));
			} else {
				request = new OAuthRequest(HttpType.POST,
						String.format(Constants.Sohu.FAV_DEL, id));
			}
			break;

		default:
			break;
		}
	}

}
