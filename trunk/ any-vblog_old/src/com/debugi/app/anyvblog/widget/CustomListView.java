package com.debugi.app.anyvblog.widget;

import java.util.Date;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.debugi.app.anyvblog.R;
/**
 * 自定义的ListView<br />
 * 包含下拉刷新操作和底部点击查看更多<br />
 * 该组件参照网友下拉刷新修改添加
 * @author zhaoyong1990@gmail.com
 *
 */
public class CustomListView extends ListView implements OnScrollListener, OnClickListener {
	//下拉刷新的几个参数
	private static final int RELEASE_TO_REFRESH = 0;
	private static final int PULL_TO_REFRESH = 1;
	private static final int REFRESHING = 2;
	private static final int DONE = 3;

	private LayoutInflater inflater;
	//headerView
	private LinearLayout headerView;
	private TextView head_tips;
	private TextView head_last_update;
	private ImageView head_image;
	private ProgressBar head_progressbar;
	private Animation animation;
	private Animation reverseAnimation;

	private boolean isRecored;

	private int headContentHeight;
	private int headContentWidth;

	private int startY;
	private int firstItemIndex;

	private int state;

	private boolean isBack;

	private OnRefreshListener onRefreshListener;
	private static final String TAG = "CustomListView";
	
	private LinearLayout footerView;
	private LinearLayout footer_loading;
	private LinearLayout footer_normal;
	private TextView footer_normal_tips;
	private TextView footer_loading_tips;
	
	private OnClickFooterListener onClickFooterListener;

	public CustomListView(Context context) {
		super(context);
		init(context);
	}
	

	public CustomListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}


	public CustomListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}


	private void init(Context context) {
		inflater = LayoutInflater.from(context);
		headerView = (LinearLayout) inflater.inflate(R.layout.listview_header,
				null);
		head_image = (ImageView) headerView.findViewById(R.id.head_image);
		head_last_update = (TextView) headerView.findViewById(R.id.head_last_update);
		head_progressbar = (ProgressBar) headerView.findViewById(R.id.head_progressbar);
		head_tips = (TextView) headerView.findViewById(R.id.head_tips);
		
		footerView = (LinearLayout) inflater.inflate(R.layout.listview_footer, null);
		footer_loading = (LinearLayout) footerView.findViewById(R.id.footer_loading);
		footer_loading_tips = (TextView) footerView.findViewById(R.id.footer_loading_tips);
		footer_normal = (LinearLayout) footerView.findViewById(R.id.footer_normal);
		footer_normal_tips = (TextView) footerView.findViewById(R.id.footer_normal_tips);
		
		footer_normal.setOnClickListener(this);

		measureView(headerView);

		headContentHeight = headerView.getMeasuredHeight();
		headContentWidth = headerView.getMeasuredWidth();

		headerView.setPadding(0, -1 * headContentHeight, 0, 0);
		headerView.invalidate();
		Log.v(TAG, "width:" + headContentWidth + " height:" + headContentHeight);

		addHeaderView(headerView);
		setOnScrollListener(this);
		// 动画
		animation = AnimationUtils.loadAnimation(context, R.anim.animation);
		reverseAnimation = AnimationUtils.loadAnimation(context, R.anim.reverseanimation);
		
		addFooterView(footerView);
	}

	// 当状态改变时候，调用该方法，以更新界面
	private void changeHeaderViewByState() {
		switch (state) {
		case RELEASE_TO_REFRESH:
			head_image.setVisibility(View.VISIBLE);
			head_progressbar.setVisibility(View.GONE);
			head_tips.setVisibility(View.VISIBLE);
			head_last_update.setVisibility(View.VISIBLE);

			head_image.clearAnimation();
			head_image.startAnimation(animation);

			head_tips.setText(R.string.header_release_to_refresh);

			Log.v(TAG, "当前状态，松开刷新");
			break;
		case PULL_TO_REFRESH:
			head_progressbar.setVisibility(View.GONE);
			head_tips.setVisibility(View.VISIBLE);
			head_last_update.setVisibility(View.VISIBLE);
			head_image.clearAnimation();
			head_image.setVisibility(View.VISIBLE);
			// 是由RELEASE_To_REFRESH状态转变来的
			if (isBack) {
				isBack = false;
				head_image.clearAnimation();
				head_image.startAnimation(reverseAnimation);

				head_tips.setText(R.string.header_pull_to_refresh);
			} else {
				head_tips.setText(R.string.header_pull_to_refresh);
			}
			Log.v(TAG, "当前状态，下拉刷新");
			break;

		case REFRESHING:

			headerView.setPadding(0, 0, 0, 0);
			headerView.invalidate();

			head_progressbar.setVisibility(View.VISIBLE);
			head_image.clearAnimation();
			head_image.setVisibility(View.GONE);
			head_tips.setText(R.string.header_refreshing);
			head_last_update.setVisibility(View.VISIBLE);

			Log.v(TAG, "当前状态,正在刷新...");
			break;
		case DONE:
			headerView.setPadding(0, -1 * headContentHeight, 0, 0);
			headerView.invalidate();

			head_progressbar.setVisibility(View.GONE);
			head_image.clearAnimation();
			head_image.setImageResource(R.drawable.ic_pulltorefresh_arrow);
			head_tips.setText(R.string.header_pull_to_refresh);
			head_last_update.setVisibility(View.VISIBLE);

			Log.v(TAG, "当前状态，done");
			break;
		}
	}
	/**
	 *
	 * @param child
	 */
	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}

		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		firstItemIndex = firstVisibleItem;

	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (firstItemIndex == 0 || !isRecored) {
				startY = (int) event.getY();
				isRecored = true;
				Log.i(TAG, "recored position on down");
			}

			break;
		case MotionEvent.ACTION_UP:
			if (state != REFRESHING) {
				if (state == DONE) {
					// do nothing
				}
				if (state == PULL_TO_REFRESH) {
					state = DONE;
					changeHeaderViewByState();
					Log.v(TAG, "由下拉刷新状态，到done状态");
				}
				if (state == RELEASE_TO_REFRESH) {
					state = REFRESHING;
					changeHeaderViewByState();
					onRefresh();
					Log.v(TAG, "由松开刷新状态，到done状态");
				}
			}
			isBack = false;
			isRecored = false;
			break;
		case MotionEvent.ACTION_MOVE:
			int tempY = (int) event.getY();
			if (!isRecored && firstItemIndex == 0) {
				Log.v(TAG, "在move时候记录下位置");
				isRecored = true;
				startY = tempY;
			}
			if (state != REFRESHING && isRecored) {
				// 可以松手去刷新了
				if (state == RELEASE_TO_REFRESH) {
					// 往上推了，推到了屏幕足够掩盖head的程度，但是还没有推到全部掩盖的地步
					if ((tempY - startY < headContentHeight)
							&& (tempY - startY) > 0) {
						state = PULL_TO_REFRESH;
						changeHeaderViewByState();

						Log.v(TAG, "由松开刷新状态转变到下拉刷新状态");
					}
					// 一下子推到顶了
					else if (tempY - startY <= 0) {
						state = DONE;
						changeHeaderViewByState();

						Log.v(TAG, "由松开刷新状态转变到done状态");
					}
					// 往下拉了，或者还没有上推到屏幕顶部掩盖head的地步
					else {
						// 不用进行特别的操作，只用更新paddingTop的值就行了
					}
				}
				// 还没有到达显示松开刷新的时候,DONE或者是PULL_TO_REFRESH状态
				if (state == PULL_TO_REFRESH) {
					// 下拉到可以进入RELEASE_TO_REFRESH的状态
					if (tempY - startY >= headContentHeight) {
						state = RELEASE_TO_REFRESH;
						isBack = true;
						changeHeaderViewByState();

						Log.v(TAG, "由done或者下拉刷新状态转变到松开刷新");
					}
					// 上推到顶了
					else if (tempY - startY <= 0) {
						state = DONE;
						changeHeaderViewByState();

						Log.v(TAG, "由DOne或者下拉刷新状态转变到done状态");
					}
				}

				// done状态下
				if (state == DONE) {
					if (tempY - startY > 0) {
						state = PULL_TO_REFRESH;
						changeHeaderViewByState();
					}
				}

				// 更新headView的size
				if (state == PULL_TO_REFRESH) {
					headerView.setPadding(0, -1 * headContentHeight
							+ (tempY - startY), 0, 0);
					headerView.invalidate();
				}

				// 更新headView的paddingTop
				if (state == RELEASE_TO_REFRESH) {
					headerView.setPadding(0,
							tempY - startY - headContentHeight, 0, 0);
					headerView.invalidate();
				}
			}
			break;
		}
		return super.onTouchEvent(event);
	}
	/**
	 * 更新完成
	 */
	public void onRefreshComplete() {
		state = DONE;
		head_last_update.setText("最近更新:" + new Date().toLocaleString());
		changeHeaderViewByState();
	}
	/**
	 * 更新完成
	 */
	public void onRefreshComplete(String tips) {
		state = DONE;
		head_last_update.setText(tips);
		changeHeaderViewByState();
	}
	/**
	 * 刷新操作
	 */
	private void onRefresh() {
		if (onRefreshListener != null) {
			onRefreshListener.onRefresh();
		}
	}
	/**
	 * 点击事件
	 */
	private void onClick() {
		if (onClickFooterListener != null) {
			onClickLoading();
			onClickFooterListener.onClick();
		}
	}
	/**
	 * 点击事件完成
	 */
	public void onClickComplete() {
		footer_loading.setVisibility(View.GONE);
		footer_normal.setVisibility(View.VISIBLE);
	}
	public void onClickComplete(int resource) {
		footer_normal_tips.setText(resource);
		onClickComplete();
	}
	public void onClickComplete(String tips) {
		footer_normal_tips.setText(tips);
		onClickComplete();
	}
	/**
	 * 点击时间执行中
	 */
	private void onClickLoading() {
		footer_normal.setVisibility(View.GONE);
		footer_loading.setVisibility(View.VISIBLE);
		footer_loading_tips.setText(R.string.footer_loading_tips);
	}
	@SuppressWarnings("unused")
	private void onClickLoading(String tips) {
		onClickLoading();
		footer_loading_tips.setText(tips);
	}
	public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
		this.onRefreshListener = onRefreshListener;
	}

	public void setOnClickFooterListener(OnClickFooterListener onClickFooterListener) {
		this.onClickFooterListener = onClickFooterListener;
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.footer_normal:
			onClick();
			break;

		default:
			break;
		}
		
	}
	/**
	 * 定义一个接口<br />
	 * 内部只有一个方法，用于刷新事件的动作
	 * @author zhaoyong1990@gmail.com
	 *
	 */
	public interface OnRefreshListener {
		public void onRefresh();
	}
	/**
	 * 定义一个接口<br />
	 * 内部的方法是用于点击footer时的动作
	 * @author zhaoyong1990@gmail.com
	 *
	 */
	public interface OnClickFooterListener {
		public void onClick();
	}
}
