package com.ilovn.app.anyvblog.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ilovn.app.anyvblog.R;

public class PublicGridAdapter extends BaseAdapter {
	@SuppressWarnings("unused")
	private static final String TAG = "PublicGridAdapter";
	private Context context;
	private List<Map<String, Object>> datas;
	private int resource;
	private LayoutInflater inflater;

	public PublicGridAdapter(Context context, List<Map<String, Object>> datas,
			int resource) {
		this.context = context;
		this.datas = datas;
		this.resource = resource;
		this.inflater = LayoutInflater.from(this.context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datas == null ? 0 : datas.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return datas == null ? null : datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return datas == null ? -1 : position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (datas == null) {
			return null;
		}
		Map<String, Object> data = datas.get(position);
		View view;
		ImageView topic_pic;
		TextView topic_name;
		if (convertView == null) {
			view = inflater.inflate(resource, parent, false);
		} else {
			view = convertView;
		}
		topic_pic = (ImageView) view.findViewById(R.id.topic_pic);
		topic_name = (TextView) view.findViewById(R.id.topic_name);
		topic_pic.setImageResource((Integer) data.get("pic"));
		topic_name.setText(data.get("name").toString());
		return view;
	}
}
