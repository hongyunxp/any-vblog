package com.debugi.app.anyvblog.asynctask;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.debugi.app.anyvblog.db.DataHelper;
import com.debugi.app.anyvblog.model.DataAdapter;
import com.debugi.app.anyvblog.utils.MyHandler;

public class GetHomeTimeLineFromDBAsyncTask extends
		AsyncTask<Integer, Void, List<DataAdapter>> {
	private Context context;
	private DataHelper dataHelper;
	private Handler handler;
	public GetHomeTimeLineFromDBAsyncTask(Context context, Handler handler) {
		this.context = context;
		this.handler = handler;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected List<DataAdapter> doInBackground(Integer... params) {

		init();
		return dataHelper.loadHomeDatas();
	}

	@Override
	protected void onPostExecute(List<DataAdapter> result) {
		Message msg = new Message();
		msg.what = MyHandler.Handler_GetDataFromDB;
		handler.sendMessage(msg);
		if (dataHelper != null) {
			dataHelper.close();
		}
	}

	private void init() {
		dataHelper = new DataHelper(context);
	}
}
