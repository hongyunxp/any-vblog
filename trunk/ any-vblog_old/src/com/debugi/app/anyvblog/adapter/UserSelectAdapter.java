package com.debugi.app.anyvblog.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.debugi.app.anyvblog.R;

public class UserSelectAdapter extends BaseAdapter implements Filterable {
	private Context context;
	private List<? extends Map<String, Object>> data;
	private int resource;
	private LayoutInflater inflater;

	public UserSelectAdapter(Context context,
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
		TextView name;
		TextView nick;
		ImageView isvip;
		ImageView head;
		if (convertView == null) {
			view = inflater.inflate(resource, parent, false);
		} else {
			view = convertView;
		}
		name = (TextView) view.findViewById(R.id.userselect_name);
		isvip = (ImageView) view.findViewById(R.id.isvip);
		nick = (TextView) view.findViewById(R.id.userselect_nick);
		head = (ImageView) view.findViewById(R.id.head);

		name.setText(map.get("name").toString());
		nick.setText(map.get("nick").toString());
		if (!"true".equals(map.get("isvip").toString())) {
			isvip.setVisibility(View.GONE);
		} else {
			isvip.setVisibility(View.VISIBLE);
		}

		// head.setImageBitmap((Bitmap) map.get("head"));
		
		return view;
	}

	@Override
	public Filter getFilter() {
		// TODO Auto-generated method stub
		return null;
	}

}
