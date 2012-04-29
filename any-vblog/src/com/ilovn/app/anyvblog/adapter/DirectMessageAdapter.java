package com.ilovn.app.anyvblog.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ilovn.app.anyvblog.R;
import com.ilovn.app.anyvblog.utils.Constants;

public class DirectMessageAdapter extends BaseAdapter {
	private Context context;
	private List<? extends Map<String, Object>> data;
	private int resource;
	private LayoutInflater inflater;
	private int type;
	
	public DirectMessageAdapter(Context context,
			List<? extends Map<String, Object>> data, int resource, int type) {
		this.context = context;
		this.data = data;
		this.resource = resource;
		this.inflater = LayoutInflater.from(this.context);
		this.type = type;
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
		TextView time;
		TextView status;
		ImageView isvip;
		TextView type;
		ImageView head;
		if (convertView == null) {
			view = inflater.inflate(resource, parent, false);
		} else {
			view = convertView;
		}
		name = (TextView) view.findViewById(R.id.name);
		time = (TextView) view.findViewById(R.id.time);
		status = (TextView) view.findViewById(R.id.status);
		isvip = (ImageView) view.findViewById(R.id.vip);
		type = (TextView) view.findViewById(R.id.type);
		head = (ImageView) view.findViewById(R.id.head);
		
		if (this.type == Constants.DIRECTMESSAGE_RECV) {
			name.setText(map.get("nick").toString());
			if (!"true".equals(map.get("isvip").toString())) {
				isvip.setVisibility(View.GONE);
			} else {
				isvip.setVisibility(View.VISIBLE);
			}
		} else if (this.type == Constants.DIRECTMESSAGE_SENT) {
			name.setText(map.get("tonick").toString());
			if (!"true".equals(map.get("toisvip").toString())) {
				isvip.setVisibility(View.GONE);
			} else {
				isvip.setVisibility(View.VISIBLE);
			}
		}
		
		time.setText(map.get("time").toString());
		//status.setText(map.get("status").toString());
		status.setText(Html.fromHtml(map.get("status").toString()));
		//head.setImageBitmap((Bitmap) map.get("head"));
		switch (Integer.parseInt(map.get("type").toString())) {
		case Constants.TYPE_MENTIONS:
			type.setText("提及");
			break;
		case Constants.TYPE_FORWARD:
			type.setText("转播");
			break;
		case Constants.TYPE_COMMENT:
			type.setText("评论");
			break;
		case Constants.TYPE_REPLY:
			type.setText("回复");
			break;
		case Constants.TYPE_NULL_REPLY:
			type.setText("空回复");
			break;
		case Constants.TYPE_PRIVATE:
			type.setText("私信");
			break;
		case Constants.TYPE_SELF:
			type.setText("原创");
			break;
		default:
			break;
		}
		return view;
	}

}
