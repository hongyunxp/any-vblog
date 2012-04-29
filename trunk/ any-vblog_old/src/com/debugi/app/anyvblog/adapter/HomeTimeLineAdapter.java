package com.debugi.app.anyvblog.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.URLUtil;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.debugi.app.anyvblog.R;
import com.debugi.app.anyvblog.listener.HandlerListener;
import com.debugi.app.anyvblog.model.DataAdapter;
import com.debugi.app.anyvblog.utils.Config;
import com.debugi.app.anyvblog.utils.Constants;
import com.debugi.app.anyvblog.utils.TextUtils;
import com.debugi.app.anyvblog.widget.AsyncImageView;

public class HomeTimeLineAdapter extends BaseAdapter {
	@SuppressWarnings("unused")
	private static final String TAG = "HomeTimeLineAdapter";
	private Context context;
	private List<DataAdapter> datas;
	private int resource;
	private LayoutInflater inflater;
	private int selectedItem;
	private LinearLayout quickbar;

	public HomeTimeLineAdapter(Context context, List<DataAdapter> datas,
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
		DataAdapter data = datas.get(position);
		View view;
		AsyncImageView head;
		TextView name;
		TextView time;
		TextView status;
		AsyncImageView status_image;
		AsyncImageView source_image;
		LinearLayout source;
		LinearLayout source_c_f;
		LinearLayout c_f;
		TextView source_name;
		TextView source_time;
		TextView source_status;
		TextView source_from;
		TextView source_forward_count;
		TextView source_comment_count;
		TextView from;
		TextView forward_count;
		TextView comment_count;
		ImageView isvip;
		ImageView source_isvip;
		TextView type;
		ImageView have_location;
		ImageView have_pic;
		if (convertView == null) {
			view = inflater.inflate(resource, parent, false);
		} else {
			view = convertView;
		}
		head = (AsyncImageView) view.findViewById(R.id.head);
		head.setImageResource(R.drawable.head_default);
		name = (TextView) view.findViewById(R.id.name);
		time = (TextView) view.findViewById(R.id.time);
		status = (TextView) view.findViewById(R.id.status);
		status_image = (AsyncImageView) view.findViewById(R.id.status_image);

		source = (LinearLayout) view.findViewById(R.id.source);
		source_name = (TextView) view.findViewById(R.id.source_name);
		source_time = (TextView) view.findViewById(R.id.source_time);
		source_status = (TextView) view.findViewById(R.id.source_status);
		source_from = (TextView) view.findViewById(R.id.source_from);
		source_image = (AsyncImageView) view.findViewById(R.id.source_image);
		source_forward_count = (TextView) view
				.findViewById(R.id.source_forward_count);
		source_comment_count = (TextView) view
				.findViewById(R.id.source_comment_count);
		from = (TextView) view.findViewById(R.id.from);
		forward_count = (TextView) view.findViewById(R.id.forward_count);
		comment_count = (TextView) view.findViewById(R.id.comment_count);
		isvip = (ImageView) view.findViewById(R.id.vip);
		source_isvip = (ImageView) view.findViewById(R.id.source_vip);
		type = (TextView) view.findViewById(R.id.type);
		source_c_f = (LinearLayout) view.findViewById(R.id.source_c_f);
		c_f = (LinearLayout) view.findViewById(R.id.c_f);
		have_location = (ImageView) view.findViewById(R.id.have_location);
		have_pic = (ImageView) view.findViewById(R.id.have_pic);
		// 设置图文模式提示图标
		if (!Config.showPic
				&& (data.getImage_s() != null || data.getSource_image_s() != null)) {
			have_pic.setVisibility(View.VISIBLE);
		} else {
			have_pic.setVisibility(View.GONE);
		}

		if (!data.isSource()) {
			source.setVisibility(View.GONE);
		} else {
			source.setVisibility(View.VISIBLE);
			if (data.isSource_isvip()) {
				source_isvip.setVisibility(View.VISIBLE);
			} else {
				source_isvip.setVisibility(View.GONE);
			}
			source_name.setText(data.getSource_nick());
			source_time.setText(data.getSource_time());

			source_status.setText(TextUtils.parse(data.getSource_status(),
					context));
			source_from.setText(data.getSource_from());
			if (data.getSource_comment_count() > 0
					|| data.getSource_forward_count() > 0) {
				source_c_f.setVisibility(View.VISIBLE);
				source_comment_count.setText(data.getSource_comment_count()
						+ "");
				source_forward_count.setText(data.getSource_forward_count()
						+ "");
			} else {
				source_c_f.setVisibility(View.GONE);
			}
			if (Config.showPic && data.getSource_image_s() != null
					&& URLUtil.isNetworkUrl(data.getSource_image_s())) {
				source_image.setVisibility(View.VISIBLE);
				source_image.setImageDrawable(null);
				source_image.asyncLoadBitmapFromUrl(data.getSource_image_s(),
						Config.cachePath + "s_" + data.getSource_status_id()
								+ ".jpg");
			} else {
				source_image.setVisibility(View.GONE);
			}
		}
		if (Config.showPic && data.getHead_url() != null
				&& URLUtil.isNetworkUrl(data.getHead_url())) {
			head.setVisibility(View.VISIBLE);
			head.setImageResource(R.drawable.head_default);
			head.asyncLoadBitmapFromUrl(data.getHead_url(), Config.cachePath
					+ "head_" + data.getUser_id() + ".jpg");
		} else {
			if (!Config.showPic) {
				head.setVisibility(View.GONE);
			} else {
				head.setVisibility(View.VISIBLE);
				head.setImageResource(R.drawable.head_default); 
			}
		}
		if (Config.showPic && data.getImage_s() != null
				&& URLUtil.isNetworkUrl(data.getImage_s())) {
			status_image.setVisibility(View.VISIBLE);
			status_image.setImageDrawable(null);
			status_image.asyncLoadBitmapFromUrl(data.getImage_s(),
					Config.cachePath + "s_" + data.getStatus_id() + ".jpg");
		} else {
			status_image.setVisibility(View.GONE);
		}
		if (data.getLat() != 0 && data.getLng() != 0) {
			have_location.setVisibility(View.VISIBLE);
		} else {
			have_location.setVisibility(View.GONE);
		}
		name.setText(data.getNick());
		time.setText(data.getTime());
		status.setText(TextUtils.parse(data.getStatus(), context));
		from.setText(data.getFrom());
		// 转播及点评数
		if (data.getComment_count() > 0 || data.getForward_count() > 0) {
			c_f.setVisibility(View.VISIBLE);
			comment_count.setText(data.getComment_count() + "");
			forward_count.setText(data.getForward_count() + "");
		} else {
			c_f.setVisibility(View.GONE);
		}
		if (data.isIsvip()) {
			isvip.setVisibility(View.VISIBLE);
		} else {
			isvip.setVisibility(View.GONE);
		}
		int isFav = 0;
		if (data.isIsfav()) {
			isFav = 1;
		} else {
			isFav = 0;
		}
		view.setTag(data.getStatus_id() + "|" + isFav);
		switch (data.getType()) {
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
		quickbar = (LinearLayout) view.findViewById(R.id.quickbar);

		// Config.debug(TAG, "selectItem=" + getSelectedItem() + "  position=" +
		// position);
		if (getSelectedItem() == position + 1) {
			addQuickBar(data.getStatus_id(), data.getUser_id());
		} else {
			hideQuickBar();
		}
		return view;
	}

	public void setSelectedItem(int selectedItem) {
		this.selectedItem = selectedItem;
	}

	public int getSelectedItem() {
		return selectedItem;
	}

	public void hideQuickBar() {
		if (quickbar != null) {
			quickbar.setVisibility(View.GONE);
		}
	}

	public void addQuickBar(String id, String user_id) {
		if (quickbar != null) {
			quickbar.setVisibility(View.VISIBLE);
			Animation animation = AnimationUtils.loadAnimation(context,
					R.anim.quickbar_in);
			quickbar.startAnimation(animation);
			HandlerListener listener = new HandlerListener(context, id,
					quickbar, user_id);
			quickbar.findViewById(R.id.quickbar_forward).setOnClickListener(
					listener);
			quickbar.findViewById(R.id.quickbar_comment).setOnClickListener(
					listener);
			quickbar.findViewById(R.id.quickbar_fav).setOnClickListener(
					listener);
			quickbar.findViewById(R.id.quickbar_profile).setOnClickListener(
					listener);
			quickbar.findViewById(R.id.quickbar_cancel).setOnClickListener(
					listener);
		}
	}

	public void addDatas(List<DataAdapter> addData) {
		datas.addAll(addData);
		System.out.println(datas.size());
	}
}
