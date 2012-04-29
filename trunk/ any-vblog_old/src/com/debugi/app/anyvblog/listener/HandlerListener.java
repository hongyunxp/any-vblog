package com.debugi.app.anyvblog.listener;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import com.debugi.app.anyvblog.CommentActivity;
import com.debugi.app.anyvblog.ForwardActivity;
import com.debugi.app.anyvblog.R;
import com.debugi.app.anyvblog.UserInfoActivity;
import com.debugi.app.anyvblog.asynctask.SendFavAsyncTask;
import com.debugi.app.anyvblog.model.SendData;
import com.debugi.app.anyvblog.model.netease.SendFav4Netease;
import com.debugi.app.anyvblog.model.sina.SendFav4Sina;
import com.debugi.app.anyvblog.model.sohu.SendFav4Sohu;
import com.debugi.app.anyvblog.model.tencent.SendFav4Tencent;
import com.debugi.app.anyvblog.utils.Config;
import com.debugi.app.anyvblog.utils.Constants;

public class HandlerListener implements OnClickListener {
	private static final String TAG = "HandlerListener";
	private Context context;
	private String id;
	private View quickbar;
	private String user_id;

	public HandlerListener(Context context, String id) {
		this.context = context;
		this.id = id;
	}

	public HandlerListener(Context context, String id, View quickbar) {
		this(context, id);
		this.quickbar = quickbar;
	}
	

	public HandlerListener(Context context, String id, View quickbar,
			String user_id) {
		this(context, id, quickbar);
		this.user_id = user_id;
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.quickbar_forward:
			Config.debug(TAG, "onclick on forward " + id);
			intent = new Intent(context, ForwardActivity.class);
			intent.putExtra("id", id);
			context.startActivity(intent);
			hide();
			break;
		case R.id.quickbar_comment:
			Config.debug(TAG, "onclick on comment " + id);
			intent = new Intent(context, CommentActivity.class);
			intent.putExtra("id", id);
			intent.putExtra("type", Constants.COMMENT_TYPE_COMMENT);
			context.startActivity(intent);
			hide();
			break;
		case R.id.quickbar_fav:
			Config.debug(TAG, "onclick on fav " + id);
			SendData sendData = null;
			switch (Config.currentUser.getSp()) {
			case Constants.SP_TENCENT:
				sendData = new SendFav4Tencent(id);
				break;
			case Constants.SP_SINA:
				sendData = new SendFav4Sina(id);
				break;
			case Constants.SP_NETEASE:
				sendData = new SendFav4Netease(id);
				break;
			case Constants.SP_SOHU:
				sendData = new SendFav4Sohu(id);
				break;
			default:
				break;
			}
			new SendFavAsyncTask(context, Config.currentUser, sendData,
					id, Constants.FAV_TYPE_ADD).execute();
			hide();
			break;
		case R.id.quickbar_profile:
			Config.debug(TAG, "onclick on profile " + user_id);
			intent = new Intent(context, UserInfoActivity.class);
			intent.putExtra("user_id", user_id);
			context.startActivity(intent);
			hide();
			break;
		case R.id.quickbar_cancel:
			Config.debug(TAG, "onclick on cancel " + id);
			hide();
			break;
		default:
			break;
		}

	}
	private void hide() {
		if (quickbar != null) {
			quickbar.setVisibility(View.GONE);
		}
	}

}
