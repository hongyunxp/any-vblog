package com.debugi.app.anyvblog.update;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.widget.Toast;

import com.debugi.app.anyvblog.UpdateDialogActivity;
import com.debugi.app.anyvblog.utils.Config;
import com.debugi.app.anyvblog.utils.NetworkUtils;
import com.debugi.open.oauth.model.HttpType;
import com.debugi.open.oauth.model.Request;
import com.debugi.open.oauth.model.Response;
import com.google.gson.Gson;

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
						Toast.makeText(context, "您当前使用的是最新版本!", Toast.LENGTH_SHORT).show();
					}
				}
			}
		}
	}
}
