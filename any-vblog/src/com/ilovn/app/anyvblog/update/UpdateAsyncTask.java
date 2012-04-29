package com.ilovn.app.anyvblog.update;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ilovn.app.anyvblog.UpdateDialogActivity;
import com.ilovn.app.anyvblog.utils.Config;
import com.ilovn.app.anyvblog.utils.NetworkUtils;
import com.ilovn.open.oauth.model.HttpType;
import com.ilovn.open.oauth.model.Request;
import com.ilovn.open.oauth.model.Response;

public class UpdateAsyncTask extends AsyncTask<Void, Void, Response> {
	private static final String TAG = "UpdateAsyncTask";
	private Context context; // 上下文对象
	private Request request; // 请求
	private Response response; // 响应
	private int p;

	public UpdateAsyncTask(Context context, int p) {
		this.context = context;
		this.p = p;
	}

	@Override
	protected Response doInBackground(Void... params) {
		request = new Request(HttpType.GET, Config.UPDATE_CHECK_URL);
		request.setCharset("GBK");
		if (NetworkUtils.checkEnable(context)) {
			Config.debug(TAG, "start send request");
			try {
				response = request.send();
			} catch (Exception e) {
				Config.debug(TAG, "network exception!");
			}
		} else {
			Config.toast(context, "当前网络不可用，请检查网络连接", Toast.LENGTH_SHORT);
			return null;
		}
		return response;
	}

	@Override
	protected void onPostExecute(Response result) {
		if (result != null && result.getCode() == 200) {
			Gson gson = new Gson();
			Config.debug(TAG, result.getBody());
			Update info = gson.fromJson(result.getBody(), Update.class);
			if (info != null) {
				String curVersion = "";
				try {
					curVersion = context.getPackageManager().getPackageInfo(
							context.getPackageName(), 64).versionName;
				} catch (NameNotFoundException e) {
				}
				if (!curVersion.equals(info.getVersion())) {
					Intent intent = new Intent(context,
							UpdateDialogActivity.class);
					intent.putExtra("info", info);
					context.startActivity(intent);
				} else {
					if (!Config.autoCheckUpdate || p != 0) {
						Config.toast(context, "您当前使用的是最新版本!",
								Toast.LENGTH_SHORT);
					}
				}
			}
		}
	}
}
