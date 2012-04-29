package com.ilovn.app.anyvblog.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ilovn.app.anyvblog.R;
import com.ilovn.app.anyvblog.utils.Constants;

public class HomeAccountSpAdapter extends BaseAdapter {

	private Context context;
	private List<? extends Map<String, Object>> data;
	private int resource;
	private LayoutInflater inflater;

	public HomeAccountSpAdapter(Context context,
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
		if (map.get("icon") == null) {
			switch (Integer.parseInt(map.get("sp").toString())) {
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
			icon.setImageBitmap((Bitmap)map.get("icon"));
		}
		
		name = (TextView) view.findViewById(R.id.oauth_sp_name);
		if (map.get("name") == null) {
			switch (Integer.parseInt(map.get("sp").toString())) {
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
			name.setText(map.get("name").toString());
		}
		return view;
	}

}