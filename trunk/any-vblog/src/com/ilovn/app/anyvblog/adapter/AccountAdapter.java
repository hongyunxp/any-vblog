package com.ilovn.app.anyvblog.adapter;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ilovn.app.anyvblog.R;
import com.ilovn.app.anyvblog.db.DataHelper;
import com.ilovn.app.anyvblog.model.UserAdapter;
import com.ilovn.app.anyvblog.utils.Constants;

public class AccountAdapter extends BaseAdapter {

	private Context context;
	private List<UserAdapter> data;
	private int resource;
	private LayoutInflater inflater;
	private AlertDialog.Builder builder;

	public AccountAdapter(Context context,
			List<UserAdapter> data, int resource) {
		this.context = context;
		this.data = data;
		this.resource = resource;
		this.inflater = LayoutInflater.from(this.context);
		builder = new AlertDialog.Builder(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data == null ? 0 : data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data == null ? null : data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return data == null ? -1 : position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (data == null) {
			return null;
		}
		UserAdapter user = data.get(position);
		View view;
		ImageView icon;
		TextView name;
		Button del;
		if (convertView == null) {
			view = inflater.inflate(resource, parent, false);
		} else {
			view = convertView;
		}
		icon = (ImageView) view.findViewById(R.id.account_head);
		if (user.getHead() == null) {
			switch (user.getSp()) {
			case Constants.SP_TENCENT:
				icon.setImageResource(R.drawable.tencent_icon_32);
				break;
			case Constants.SP_SINA:
				icon.setImageResource(R.drawable.sina_icon_32);
				break;
			case Constants.SP_NETEASE:
				icon.setImageResource(R.drawable.netease_icon_32);
				break;
			case Constants.SP_SOHU:
				icon.setImageResource(R.drawable.sohu_icon_32);
				break;

			default:
				break;
			}
		} else {
			icon.setImageBitmap(user.getHead());
		}
		
		name = (TextView) view.findViewById(R.id.account_name);
		if (user.getNick() == null) {
			switch (user.getSp()) {
			case Constants.SP_TENCENT:
				name.setText("腾讯微博");
				break;
			case Constants.SP_SINA:
				name.setText("新浪微博");
				break;
			case Constants.SP_NETEASE:
				name.setText("网易微博");
				break;
			case Constants.SP_SOHU:
				name.setText("搜狐微博");
				break;

			default:
				break;
			}
		} else {
			String text = user.getNick();
			switch (user.getSp()) {
			case Constants.SP_TENCENT:
				text += "<腾讯>";
				break;
			case Constants.SP_SINA:
				text += "<新浪>";
				break;
			case Constants.SP_NETEASE:
				text += "<网易>";
				break;
			case Constants.SP_SOHU:
				text += "<搜狐>";
				break;
			default:
				break;
			}
			name.setText(text);
		}
		
		del = (Button) view.findViewById(R.id.account_del);
		del.setTag(user.getUser_id());
		del.setOnClickListener(new AccountListener(context, user, this));
		return view;
	}
	
	private class AccountListener implements OnClickListener {
		private Context context;
		private DataHelper dataHelper;
		private UserAdapter userAdapter;
		private AccountAdapter accountAdapter;
		public AccountListener(Context context, UserAdapter userAdapter, AccountAdapter accountAdapter) {
			this.context = context;
			this.userAdapter = userAdapter;
			this.accountAdapter = accountAdapter;
		}

		@Override
		public void onClick(View v) {
			dataHelper = new DataHelper(this.context);
			builder.setMessage("确定删除\"" + userAdapter.getNick() + "\"?").setCancelable(true)
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (userAdapter != null) {
						if (dataHelper.deleteAccount(userAdapter) > 0) {
							data.remove(userAdapter);
							accountAdapter.notifyDataSetChanged();
						}
						if (dataHelper != null) {
							dataHelper.close();
						}
					}
				}
			})
			.setNegativeButton("取消", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
			AlertDialog dialog = builder.create();
			dialog.show();
		}
	}
}