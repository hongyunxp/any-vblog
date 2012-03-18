package com.debugi.app.anyvblog.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.debugi.app.anyvblog.model.SendData;
import com.debugi.app.anyvblog.model.UserAdapter;
import com.debugi.app.anyvblog.oauth.OauthModel4Netease;
import com.debugi.app.anyvblog.oauth.OauthModel4Sina;
import com.debugi.app.anyvblog.oauth.OauthModel4Sohu;
import com.debugi.app.anyvblog.oauth.OauthModel4Tencent;
import com.debugi.app.anyvblog.utils.Config;
import com.debugi.app.anyvblog.utils.Constants;
import com.debugi.app.anyvblog.utils.MyHandler;
import com.debugi.app.anyvblog.utils.NetworkUtils;
import com.debugi.open.oauth.OAuthServiceProvider;
import com.debugi.open.oauth.model.HttpType;
import com.debugi.open.oauth.model.OAuthRequest;
import com.debugi.open.oauth.model.Response;
import com.debugi.open.oauth.model.Token;

public class GetMentionsTimeLineAsyncTask extends
		AsyncTask<Void, Void, Response> {
	private static final String TAG = "GetMentionsTimeLineAsyncTask";
	private UserAdapter user;
	private Context context;
	private OAuthServiceProvider serviceProvider;
	private Token accessToken;
	private SendData sendData;
	private OAuthRequest request;
	private HttpType httpType;
	private Response response;
	private int type; // 该字段针对除腾讯微博以外的服务商,=0为获取提及列表 =1为获取评论列表
	private Handler handler;

	// private DataHelper dataHelper; //数据库操作对象

	public GetMentionsTimeLineAsyncTask(Context context, UserAdapter user, SendData sendData, int type, Handler handler) {
		this.context = context;
		this.user = user;
		this.sendData = sendData;
		this.type = type;
		this.handler = handler;
		accessToken = this.user.getAccessToken();
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected Response doInBackground(Void... params) {
		init();
		request.addQuerystringParameters(sendData.conver());
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
	protected void onPostExecute(Response response) {
		Message message = new Message();
		message.what = MyHandler.Handler_GetData;
		handler.sendMessage(message);
	}

	private void init() {
		httpType = HttpType.GET;
		switch (user.getSp()) {
		case Constants.SP_TENCENT:
			serviceProvider = new OauthModel4Tencent().getServiceProvider();
			request = new OAuthRequest(httpType,
					Constants.Tencent.MENTIONS_TIMELINE);
			break;
		case Constants.SP_SINA:
			serviceProvider = new OauthModel4Sina().getServiceProvider();
			if (type == 0) {
				request = new OAuthRequest(httpType,
						Constants.Sina.MENTIONS_TIMELINE);
			} else if (type == 1) {
				request = new OAuthRequest(httpType,
						Constants.Sina.COMMENTS_TIMELINE);
			}
			break;
		case Constants.SP_NETEASE:
			serviceProvider = new OauthModel4Netease().getServiceProvider();
			if (type == 0) {
				request = new OAuthRequest(httpType,
						Constants.NetEase.MENTIONS_TIMELINE);
			} else if (type == 1) {
				request = new OAuthRequest(httpType,
						Constants.NetEase.COMMENTS_TIMELINE);
			}
			break;
		case Constants.SP_SOHU:
			serviceProvider = new OauthModel4Sohu().getServiceProvider();
			if (type == 0) {
				request = new OAuthRequest(httpType,
						Constants.Sohu.MENTIONS_TIMELINE);
			} else if (type == 1) {
				request = new OAuthRequest(httpType,
						Constants.Sohu.COMMENTS_TIMELINE);
			}
			break;

		default:
			Log.i(TAG, "sp error");
			break;
		}

	}
}
