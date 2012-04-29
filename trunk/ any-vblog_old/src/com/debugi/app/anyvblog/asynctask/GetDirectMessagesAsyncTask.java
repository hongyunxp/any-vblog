package com.debugi.app.anyvblog.asynctask;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.debugi.app.anyvblog.R;
import com.debugi.app.anyvblog.adapter.DirectMessageAdapter;
import com.debugi.app.anyvblog.exception.NormalException;
import com.debugi.app.anyvblog.exception.ResponseException;
import com.debugi.app.anyvblog.model.DataAdapter;
import com.debugi.app.anyvblog.model.SendData;
import com.debugi.app.anyvblog.model.UserAdapter;
import com.debugi.app.anyvblog.model.netease.Status4Netease;
import com.debugi.app.anyvblog.model.sina.Status4Sina;
import com.debugi.app.anyvblog.model.sohu.Status4Sohu;
import com.debugi.app.anyvblog.model.tencent.Data4Tencent;
import com.debugi.app.anyvblog.model.tencent.GetTimeLine4Tencent;
import com.debugi.app.anyvblog.oauth.OauthModel4Netease;
import com.debugi.app.anyvblog.oauth.OauthModel4Sina;
import com.debugi.app.anyvblog.oauth.OauthModel4Sohu;
import com.debugi.app.anyvblog.oauth.OauthModel4Tencent;
import com.debugi.app.anyvblog.utils.Config;
import com.debugi.app.anyvblog.utils.Constants;
import com.debugi.app.anyvblog.utils.NetworkUtils;
import com.debugi.app.anyvblog.widget.CustomListView;
import com.debugi.open.oauth.OAuthServiceProvider;
import com.debugi.open.oauth.model.HttpType;
import com.debugi.open.oauth.model.OAuthRequest;
import com.debugi.open.oauth.model.Response;
import com.debugi.open.oauth.model.Token;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GetDirectMessagesAsyncTask extends
		AsyncTask<Integer, Void, List<DataAdapter>> {
	private static final String TAG = "GetDirectMessagesAsyncTask";
	private UserAdapter user;
	private Context context;
	private OAuthServiceProvider serviceProvider;
	private Token accessToken;
	private SendData sendData;
	private OAuthRequest request;
	private HttpType httpType;
	private Response response;
	private GetTimeLine4Tencent data4Tencent;
	private CustomListView listview;
	private int type; // 1=收件箱 2=发件箱

	// private DataHelper dataHelper; //数据库操作对象

	public GetDirectMessagesAsyncTask(Context context, CustomListView listview,
			UserAdapter user, SendData sendData, int type) {
		this.context = context;
		this.listview = listview;
		this.user = user;
		this.sendData = sendData;
		this.type = type;
		// dataHelper = new DataHelper(context);
		// accessToken = dataHelper.getToken(user);
		// dataHelper.close();
		accessToken = this.user.getAccessToken();
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected List<DataAdapter> doInBackground(Integer... params) {
		List<DataAdapter> datas = new ArrayList<DataAdapter>();
		init();
		request.addQuerystringParameters(sendData.conver());
		serviceProvider.signRequest(accessToken, request);
		if (NetworkUtils.checkEnable(context)) {
			Config.debug(TAG, "start send request");
			response = request.send();
		} else {
			Toast.makeText(context, "当前网络不可用，请检查网络连接", Toast.LENGTH_SHORT)
					.show();
			return datas;
		}
		if (response == null || response.getCode() != 200) {
			if (response == null) {
				return datas;
			}
			String tip = new ResponseException(response, user.getSp())
					.getMessage();
			Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
			return datas;
		}
		try {
			Config.debug(TAG, response.getBody());
			Gson gson = new Gson();
			Type myType = null;
			switch (user.getSp()) {
			case Constants.SP_TENCENT:
				data4Tencent = gson.fromJson(response.getBody(),
						GetTimeLine4Tencent.class);
				// 若返回值ret为0，则解析到适配
				if (data4Tencent.getRet() == 0) {
					for (Data4Tencent data : data4Tencent.getData().getInfo()) {
						datas.add(data.conver2Data());
					}
				}
				break;
			case Constants.SP_SINA:
				myType = new TypeToken<List<Status4Sina>>() {
				}.getType();
				List<Status4Sina> datas4Sina = gson.fromJson(
						response.getBody(), myType);
				for (Status4Sina data : datas4Sina) {
					datas.add(data.conver2Data());
				}
				break;
			case Constants.SP_NETEASE:
				myType = new TypeToken<List<Status4Netease>>() {
				}.getType();
				List<Status4Netease> status4Neteases = gson.fromJson(
						response.getBody(), myType);
				for (Status4Netease status4Netease : status4Neteases) {
					datas.add(status4Netease.conver2Data());
				}
				break;
			case Constants.SP_SOHU:
				myType = new TypeToken<List<Status4Sohu>>() {
				}.getType();
				List<Status4Sohu> status4Sohus = gson.fromJson(
						response.getBody(), myType);
				for (Status4Sohu status4Sohu : status4Sohus) {
					datas.add(status4Sohu.conver2Data());
				}
				break;
			default:
				break;
			}
		} catch (Exception e) {
			throw new NormalException(e.getMessage());
		}

		return datas;
	}

	@Override
	protected void onPostExecute(List<DataAdapter> result) {
		List<Map<String, Object>> cdata = new ArrayList<Map<String, Object>>();
		// 将适配中的数据装配到list
		for (DataAdapter data : result) {
			cdata.add(data.conver());
		}
		if (cdata.size() <= 0) {
			Toast.makeText(context, "没有新的数据", Toast.LENGTH_SHORT).show();
			Config.debug(TAG, "没有新的数据");
		}
		// 创建adapter
		DirectMessageAdapter adapter = null;
		if (type == Constants.DIRECTMESSAGE_RECV) {
			adapter = new DirectMessageAdapter(context, cdata,
					R.layout.directmessage_recv_item, type);
		} else if (type == Constants.DIRECTMESSAGE_SENT) {
			adapter = new DirectMessageAdapter(context, cdata,
					R.layout.directmessage_sent_item, type);
		}

		// 对listview设置adapter
		listview.setAdapter(adapter);
		listview.onRefreshComplete();
		// listview的显示动画效果
		LayoutAnimationController animationController = new LayoutAnimationController(
				AnimationUtils.loadAnimation(context, R.anim.alpha), 0.1f);
		animationController.setOrder(LayoutAnimationController.ORDER_NORMAL);
		listview.setLayoutAnimation(animationController);
		// 将数据存到数据库
		// DataHelper dataHelper = new DataHelper(context);
		// dataHelper.saveHomeDatas(result);
		// 关闭数据库连接
		// dataHelper.close();

	}

	private void init() {
		httpType = HttpType.GET;
		switch (user.getSp()) {
		case Constants.SP_TENCENT:
			serviceProvider = new OauthModel4Tencent().getServiceProvider();
			if (type == Constants.DIRECTMESSAGE_RECV) {
				request = new OAuthRequest(httpType,
						Constants.Tencent.DIRECT_MESSAGES_RECV);
			} else if (type == Constants.DIRECTMESSAGE_SENT) {
				request = new OAuthRequest(httpType,
						Constants.Tencent.DIRECT_MESSAGES_SENT);
			}
			break;
		case Constants.SP_SINA:
			serviceProvider = new OauthModel4Sina().getServiceProvider();
			if (type == Constants.DIRECTMESSAGE_RECV) {
				request = new OAuthRequest(httpType,
						Constants.Sina.MENTIONS_TIMELINE);
			} else if (type == Constants.DIRECTMESSAGE_SENT) {
				request = new OAuthRequest(httpType,
						Constants.Sina.COMMENTS_TIMELINE);
			}
			break;
		case Constants.SP_NETEASE:
			serviceProvider = new OauthModel4Netease().getServiceProvider();
			if (type == Constants.DIRECTMESSAGE_RECV) {
				request = new OAuthRequest(httpType,
						Constants.NetEase.DIRECT_MESSAGES_RECV);
			} else if (type == Constants.DIRECTMESSAGE_SENT) {
				request = new OAuthRequest(httpType,
						Constants.NetEase.DIRECT_MESSAGES_SENT);
			}
			break;
		case Constants.SP_SOHU:
			serviceProvider = new OauthModel4Sohu().getServiceProvider();
			if (type == Constants.DIRECTMESSAGE_RECV) {
				request = new OAuthRequest(httpType,
						Constants.Sohu.DIRECT_MESSAGES_RECV);
			} else if (type == Constants.DIRECTMESSAGE_SENT) {
				request = new OAuthRequest(httpType,
						Constants.Sohu.DIRECT_MESSAGES_SENT);
			}
			break;

		default:
			Log.i(TAG, "sp error");
			break;
		}

	}
}
