package com.debugi.app.anyvblog.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.debugi.app.anyvblog.R;

public class OAuthSpAdapter extends BaseAdapter {

	private Context context;
	private List<? extends Map<String, Object>> data;
	private int resource;
	private LayoutInflater inflater;

	public OAuthSpAdapter(Context context,
			List<? extends Map<String, Object>> data, int resource) {
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
		Map<String, Object> map = data.get(position);
		View view;
		ImageView icon;
		TextView name;
		if (convertView == null) {
			view = inflater.inflate(resource, parent, false);
		} else {
			view = convertView;
		}
		icon = (ImageView) view.findViewById(R.id.oauth_sp_icon);
		icon.setImageResource(Integer.parseInt(map.get("icon").toString()));
		name = (TextView) view.findViewById(R.id.oauth_sp_name);
		name.setText(map.get("name").toString());

		return view;
	}

}