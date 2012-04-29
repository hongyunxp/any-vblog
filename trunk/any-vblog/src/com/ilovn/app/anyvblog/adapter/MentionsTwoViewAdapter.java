package com.ilovn.app.anyvblog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ilovn.app.anyvblog.R;
import com.ilovn.app.anyvblog.widget.TitleProvider;

public class MentionsTwoViewAdapter extends BaseAdapter implements
		TitleProvider {
	private static final int VIEW1 = 0;
	private static final int VIEW2 = 1;
	private static final int VIEW_MAX_COUNT = VIEW2 + 1;
	private final String[] names = { "提及", "评论" };
	private LayoutInflater mInflater;

	public MentionsTwoViewAdapter(Context context) {
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getItemViewType(int position) {
		return position;
	}

	@Override
	public int getViewTypeCount() {
		return VIEW_MAX_COUNT;
	}

	@Override
	public int getCount() {
		return 2;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int view = getItemViewType(position);
		if (convertView == null) {
			switch (view) {
			case VIEW1:
				convertView = mInflater.inflate(R.layout.mentions_view_reply, null);
				break;
			case VIEW2:
				convertView = mInflater.inflate(R.layout.mentions_view_comment, null);
				break;
			}
		}
		return convertView;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.taptwo.android.widget.TitleProvider#getTitle(int)
	 */
	public String getTitle(int position) {
		return names[position];
	}

}
