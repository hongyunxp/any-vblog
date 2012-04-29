package com.ilovn.app.anyvblog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.ilovn.app.anyvblog.R;

public class FaceAdapter extends BaseAdapter {

	private Context context;
	private int[] data;
	private int resource;
	private LayoutInflater inflater;

	public FaceAdapter(Context context,
			int[] data, int resource) {
		this.context = context;
		this.data = data;
		this.resource = resource;
		this.inflater = LayoutInflater.from(this.context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data == null ? 0 : data.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data == null ? null : data[position];
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
		int id = data[position];
		View view;
		ImageView face;
		if (convertView == null) {
			view = inflater.inflate(resource, parent, false);
		} else {
			view = convertView;
		}
		face = (ImageView) view.findViewById(R.id.face);
		face.setImageResource(id);
		view.setTag(id);
		return view;
	}
}