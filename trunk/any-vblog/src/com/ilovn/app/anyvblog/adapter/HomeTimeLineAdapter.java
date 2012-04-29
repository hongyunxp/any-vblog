package com.ilovn.app.anyvblog.adapter;

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

import com.ilovn.app.anyvblog.R;
import com.ilovn.app.anyvblog.listener.HandlerListener;
import com.ilovn.app.anyvblog.model.DataAdapter;
import com.ilovn.app.anyvblog.utils.Config;
import com.ilovn.app.anyvblog.utils.Constants;
import com.ilovn.app.anyvblog.utils.TextUtils;
import com.ilovn.app.anyvblog.widget.AsyncImageView;
/**
 * 主页时间线适配器
 * @author zhaoyong
 *
 */
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
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(resource, parent, false);
			holder.head = (AsyncImageView) convertView.findViewById(R.id.head);
			holder.head.setImageResource(R.drawable.head_default);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.time = (TextView) convertView.findViewById(R.id.time);
			holder.status = (TextView) convertView.findViewById(R.id.status);
			holder.status_image = (AsyncImageView) convertView.findViewById(R.id.status_image);

			holder.source = (LinearLayout) convertView.findViewById(R.id.source);
			holder.source_name = (TextView) convertView.findViewById(R.id.source_name);
			holder.source_time = (TextView) convertView.findViewById(R.id.source_time);
			holder.source_status = (TextView) convertView.findViewById(R.id.source_status);
			holder.source_from = (TextView) convertView.findViewById(R.id.source_from);
			holder.source_image = (AsyncImageView) convertView.findViewById(R.id.source_image);
			holder.source_forward_count = (TextView) convertView
					.findViewById(R.id.source_forward_count);
			holder.source_comment_count = (TextView) convertView
					.findViewById(R.id.source_comment_count);
			holder.from = (TextView) convertView.findViewById(R.id.from);
			holder.forward_count = (TextView) convertView.findViewById(R.id.forward_count);
			holder.comment_count = (TextView) convertView.findViewById(R.id.comment_count);
			holder.isvip = (ImageView) convertView.findViewById(R.id.vip);
			holder.source_isvip = (ImageView) convertView.findViewById(R.id.source_vip);
			holder.type = (TextView) convertView.findViewById(R.id.type);
			holder.source_c_f = (LinearLayout) convertView.findViewById(R.id.source_c_f);
			holder.c_f = (LinearLayout) convertView.findViewById(R.id.c_f);
			holder.have_location = (ImageView) convertView.findViewById(R.id.have_location);
			holder.have_pic = (ImageView) convertView.findViewById(R.id.have_pic);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		// 设置图文模式提示图标
		if (!Config.showPic
				&& (data.getImage_s() != null || data.getSource_image_s() != null)) {
			holder.have_pic.setVisibility(View.VISIBLE);
		} else {
			holder.have_pic.setVisibility(View.GONE);
		}

		if (!data.isSource()) {
			holder.source.setVisibility(View.GONE);
		} else {
			holder.source.setVisibility(View.VISIBLE);
			if (data.isSource_isvip()) {
				holder.source_isvip.setVisibility(View.VISIBLE);
			} else {
				holder.source_isvip.setVisibility(View.GONE);
			}
			holder.source_name.setText(data.getSource_nick());
			holder.source_time.setText(data.getSource_time());

			holder.source_status.setText(TextUtils.parse(data.getSource_status(),
					context));
			holder.source_from.setText(data.getSource_from());
			if (data.getSource_comment_count() > 0
					|| data.getSource_forward_count() > 0) {
				holder.source_c_f.setVisibility(View.VISIBLE);
				holder.source_comment_count.setText(data.getSource_comment_count()
						+ "");
				holder.source_forward_count.setText(data.getSource_forward_count()
						+ "");
			} else {
				holder.source_c_f.setVisibility(View.GONE);
			}
			holder.source_image.setImageBitmap(null);
			if (Config.showPic && data.getSource_image_s() != null
					&& URLUtil.isNetworkUrl(data.getSource_image_s())) {
				holder.source_image.setVisibility(View.VISIBLE);
				holder.source_image.setImageDrawable(null);
				holder.source_image.asyncLoadBitmapFromUrl(data.getSource_image_s(),
						Config.cachePath + data.getSource_image_s().substring(data.getSource_image_s().length() - 12).replace("/", "_") + ".jpg");
			} else {
				holder.source_image.setVisibility(View.GONE);
			}
		}
		holder.head.setImageBitmap(null);
		if (Config.showPic && data.getHead_url() != null
				&& URLUtil.isNetworkUrl(data.getHead_url())) {
			holder.head.setVisibility(View.VISIBLE);
			holder.head.setImageResource(R.drawable.head_default);
			holder.head.asyncLoadBitmapFromUrl(data.getHead_url(), Config.cachePath
					+ "head_" + data.getUser_id() + ".jpg");
		} else {
			if (!Config.showPic) {
				holder.head.setVisibility(View.GONE);
			} else {
				holder.head.setVisibility(View.VISIBLE);
				holder.head.setImageResource(R.drawable.head_default); 
			}
		}
		holder.status_image.setImageBitmap(null);
		if (Config.showPic && data.getImage_s() != null
				&& URLUtil.isNetworkUrl(data.getImage_s())) {
			holder.status_image.setVisibility(View.VISIBLE);
			holder.status_image.setImageDrawable(null);
			holder.status_image.asyncLoadBitmapFromUrl(data.getImage_s(),
					Config.cachePath + data.getImage_s().substring(data.getImage_s().length() - 12).replace("/", "_") + ".jpg");
		} else {
			holder.status_image.setVisibility(View.GONE);
		}
		if (data.getLat() != 0 && data.getLng() != 0) {
			holder.have_location.setVisibility(View.VISIBLE);
		} else {
			holder.have_location.setVisibility(View.GONE);
		}
		holder.name.setText(data.getNick());
		holder.time.setText(data.getTime());
		holder.status.setText(TextUtils.parse(data.getStatus(), context));
		holder.from.setText(data.getFrom());
		// 转播及点评数
		if (data.getComment_count() > 0 || data.getForward_count() > 0) {
			holder.c_f.setVisibility(View.VISIBLE);
			holder.comment_count.setText(data.getComment_count() + "");
			holder.forward_count.setText(data.getForward_count() + "");
		} else {
			holder.c_f.setVisibility(View.GONE);
		}
		if (data.isIsvip()) {
			holder.isvip.setVisibility(View.VISIBLE);
		} else {
			holder.isvip.setVisibility(View.GONE);
		}
		int isFav = 0;
		if (data.isIsfav()) {
			isFav = 1;
		} else {
			isFav = 0;
		}
		switch (data.getType()) {
		case Constants.TYPE_MENTIONS:
			holder.type.setText("提及");
			break;
		case Constants.TYPE_FORWARD:
			holder.type.setText("转播");
			break;
		case Constants.TYPE_COMMENT:
			holder.type.setText("评论");
			break;
		case Constants.TYPE_REPLY:
			holder.type.setText("回复");
			break;
		case Constants.TYPE_NULL_REPLY:
			holder.type.setText("空回复");
			break;
		case Constants.TYPE_PRIVATE:
			holder.type.setText("私信");
			break;
		case Constants.TYPE_SELF:
			holder.type.setText("原创");
			break;
		default:
			break;
		}
		quickbar = (LinearLayout) convertView.findViewById(R.id.quickbar);

		// Config.debug(TAG, "selectItem=" + getSelectedItem() + "  position=" +
		// position);
		if (getSelectedItem() == position + 1) {
			addQuickBar(data.getStatus_id(), data.getUser_id());
		} else {
			hideQuickBar();
		}
		return convertView;
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
	/**
	 * 用于提升性能
	 * @author zhaoyong
	 *
	 */
	private static class ViewHolder {
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
	}
}
