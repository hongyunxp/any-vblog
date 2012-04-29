package com.ilovn.app.anyvblog.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ilovn.app.anyvblog.R;
import com.ilovn.app.anyvblog.model.UserAdapter;
import com.ilovn.app.anyvblog.utils.Config;
import com.ilovn.app.anyvblog.widget.AsyncImageView;

public class UsersAdapter extends BaseAdapter {
	private Context context;
	private List<UserAdapter> data;
	private int resource;
	private LayoutInflater inflater;

	public UsersAdapter(Context context,
			List<UserAdapter> data, int resource) {
		this.context = context;
		this.data = data;
		this.resource = resource;
		this.inflater = LayoutInflater.from(this.context);
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
		UserAdapter userAdapter = data.get(position);
		View view;
		AsyncImageView head;
		TextView name;
		TextView nick;
		ImageView isvip;
		if (convertView == null) {
			view = inflater.inflate(resource, parent, false);
		} else {
			view = convertView;
		}
		name = (TextView) view.findViewById(R.id.user_name);
		isvip = (ImageView) view.findViewById(R.id.isvip);
		nick = (TextView) view.findViewById(R.id.user_nick);
		head = (AsyncImageView) view.findViewById(R.id.head);

		name.setText(userAdapter.getName());
		nick.setText(userAdapter.getNick());
		if (userAdapter.isIsvip()) {
			isvip.setVisibility(View.VISIBLE);
		} else {
			isvip.setVisibility(View.GONE);
		}

		if (Config.showPic && userAdapter.getHead_url() != null
				&& URLUtil.isNetworkUrl(userAdapter.getHead_url())) {
			head.setVisibility(View.VISIBLE);
			head.setImageResource(R.drawable.head_default);
			head.asyncLoadBitmapFromUrl(userAdapter.getHead_url(), Config.cachePath
					+ "head_" + userAdapter.getUser_id() + ".jpg");
		} else {
			if (!Config.showPic) {
				head.setVisibility(View.GONE);
			} else {
				head.setVisibility(View.VISIBLE);
				head.setImageResource(R.drawable.head_default); 
			}
		}
		view.setTag(userAdapter.getUser_id());
		return view;
	}
}
