package com.ilovn.app.anyvblog.widget;

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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ilovn.app.anyvblog.R;

public class CustomListView extends ListView implements
		OnScrollListener, OnClickListener {

	private static final int TAP_TO_REFRESH = 1;
	private static final int PULL_TO_REFRESH = 2;
	private static final int RELEASE_TO_REFRESH = 3;
	private static final int REFRESHING = 4;

	private static final String TAG = "CustomListView";

	private OnRefreshListener mOnRefreshListener;
	private OnClickFooterListener onClickFooterListener;

	/**
	 * Listener that will receive notifications every time the list scrolls.
	 */
	private OnScrollListener mOnScrollListener;
	private LayoutInflater mInflater;

	private LinearLayout headerView;
	private TextView head_tips;
	private ImageView head_image;
	private ProgressBar head_progressbar;
	private TextView head_last_update;

	private int mCurrentScrollState;
	private int mRefreshState;

	private Animation mFlipAnimation;
	private Animation mReverseFlipAnimation;

	private int mRefreshViewHeight;
	private int mRefreshOriginalTopPadding;
	private int mLastMotionY;

	private boolean mBounceHack;

	private LinearLayout footerView;
	private LinearLayout footer_loading;
	private LinearLayout footer_normal;
	private TextView footer_normal_tips;
	private TextView footer_loading_tips;

	public CustomListView(Context context) {
		super(context);
		init(context);
	}

	public CustomListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public CustomListView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		// Load all of the animations we need in code rather than through XML
		mFlipAnimation = AnimationUtils
				.loadAnimation(context, R.anim.animation);
		mReverseFlipAnimation = AnimationUtils.loadAnimation(context,
				R.anim.reverseanimation);

		mInflater = LayoutInflater.from(context);

		headerView = (LinearLayout) mInflater.inflate(
				R.layout.listview_header, this, false);
		head_tips = (TextView) headerView
				.findViewById(R.id.head_tips);
		head_image = (ImageView) headerView
				.findViewById(R.id.head_image);
		head_progressbar = (ProgressBar) headerView
				.findViewById(R.id.head_progressbar);
		head_last_update = (TextView) headerView
				.findViewById(R.id.head_last_update);

		head_image.setMinimumHeight(50);
		headerView.setOnClickListener(new OnClickRefreshListener());
		

		mRefreshState = TAP_TO_REFRESH;
		
		footerView = (LinearLayout) mInflater.inflate(R.layout.listview_footer,
				null);
		footer_loading = (LinearLayout) footerView
				.findViewById(R.id.footer_loading);
		footer_loading_tips = (TextView) footerView
				.findViewById(R.id.footer_loading_tips);
		footer_normal = (LinearLayout) footerView
				.findViewById(R.id.footer_normal);
		footer_normal_tips = (TextView) footerView
				.findViewById(R.id.footer_normal_tips);
		footer_normal.setOnClickListener(this);
		addFooterView(footerView);

		addHeaderView(headerView);

		super.setOnScrollListener(this);

		measureView(headerView);
		mRefreshViewHeight = headerView.getMeasuredHeight();
		mRefreshOriginalTopPadding = headerView.getPaddingTop();
		headerView.setPadding(0, -1 * mRefreshViewHeight, 0, 0);
	}

	@Override
	protected void onAttachedToWindow() {
		setSelection(1);
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		super.setAdapter(adapter);
		setSelection(1);
	}

	/**
	 * Set the listener that will receive notifications every time the list
	 * scrolls.
	 * 
	 * @param l
	 *            The scroll listener.
	 */
	@Override
	public void setOnScrollListener(AbsListView.OnScrollListener l) {
		mOnScrollListener = l;
	}

	/**
	 * Register a callback to be invoked when this list should be refreshed.
	 * 
	 * @param onRefreshListener
	 *            The callback to run.
	 */
	public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
		mOnRefreshListener = onRefreshListener;
	}

	/**
	 * Set a text to represent when the list was last updated.
	 * 
	 * @param lastUpdated
	 *            Last updated at.
	 */
	public void setLastUpdated(CharSequence lastUpdated) {
		if (lastUpdated != null) {
			head_last_update.setVisibility(View.VISIBLE);
			head_last_update.setText(lastUpdated);
		} else {
			head_last_update.setVisibility(View.GONE);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		final int y = (int) event.getY();
		mBounceHack = false;

		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:
			if (!isVerticalScrollBarEnabled()) {
				setVerticalScrollBarEnabled(true);
			}
			if (getFirstVisiblePosition() == 0 && mRefreshState != REFRESHING) {
				if ((headerView.getBottom() >= mRefreshViewHeight || headerView
						.getTop() >= 0) && mRefreshState == RELEASE_TO_REFRESH) {
					// Initiate the refresh
					mRefreshState = REFRESHING;
					prepareForRefresh();
					onRefresh();
				} else if (headerView.getBottom() < mRefreshViewHeight
						|| headerView.getTop() <= 0) {
					// Abort refresh and scroll down below the refresh view
					resetHeader();
					setSelection(1);
				}
			}
			break;
		case MotionEvent.ACTION_DOWN:
			mLastMotionY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			applyHeaderPadding(event);
			break;
		}
		return super.onTouchEvent(event);
	}

	private void applyHeaderPadding(MotionEvent ev) {
		// getHistorySize has been available since API 1
		int pointerCount = ev.getHistorySize();

		for (int p = 0; p < pointerCount; p++) {
			if (mRefreshState == RELEASE_TO_REFRESH) {
				if (isVerticalFadingEdgeEnabled()) {
					setVerticalScrollBarEnabled(false);
				}

				int historicalY = (int) ev.getHistoricalY(p);

				// Calculate the padding to apply, we divide by 1.7 to
				// simulate a more resistant effect during pull.
				int topPadding = (int) (((historicalY - mLastMotionY) - mRefreshViewHeight) / 1.7);

				headerView.setPadding(headerView.getPaddingLeft(),
						topPadding, headerView.getPaddingRight(),
						headerView.getPaddingBottom());
			}
		}
	}

	/**
	 * Sets the header padding back to original size.
	 */
	private void resetHeaderPadding() {
//		headerView.setPadding(0, -1 * mRefreshViewHeight, 0, 0);
		headerView.setPadding(headerView.getPaddingLeft(),
				mRefreshOriginalTopPadding, headerView.getPaddingRight(),
				headerView.getPaddingBottom());
	}

	/**
	 * Resets the header to the original state.
	 */
	private void resetHeader() {
		if (mRefreshState != TAP_TO_REFRESH) {
			mRefreshState = TAP_TO_REFRESH;

			resetHeaderPadding();

			// Set refresh view text to the pull label
			head_tips.setText(R.string.header_click_update);
			// Replace refresh drawable with arrow drawable
			head_image
					.setImageResource(R.drawable.ic_pulltorefresh_arrow);
			// Clear the full rotation animation
			head_image.clearAnimation();
			// Hide progress bar and arrow.
			head_image.setVisibility(View.GONE);
			head_progressbar.setVisibility(View.GONE);
		}
	}

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
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// When the refresh view is completely visible, change the text to say
		// "Release to refresh..." and flip the arrow drawable.
		if (mCurrentScrollState == SCROLL_STATE_TOUCH_SCROLL
				&& mRefreshState != REFRESHING) {
			if (firstVisibleItem == 0) {
				head_image.setVisibility(View.VISIBLE);
				if ((headerView.getBottom() >= mRefreshViewHeight + 20 || headerView
						.getTop() >= 0) && mRefreshState != RELEASE_TO_REFRESH) {
					head_tips
							.setText(R.string.header_release_to_refresh);
					head_image.clearAnimation();
					head_image.startAnimation(mFlipAnimation);
					mRefreshState = RELEASE_TO_REFRESH;
				} else if (headerView.getBottom() < mRefreshViewHeight + 20
						&& mRefreshState != PULL_TO_REFRESH) {
					head_tips
							.setText(R.string.header_pull_to_refresh);
					if (mRefreshState != TAP_TO_REFRESH) {
						head_image.clearAnimation();
						head_image.startAnimation(mReverseFlipAnimation);
					}
					mRefreshState = PULL_TO_REFRESH;
				}
			} else {
				head_image.setVisibility(View.GONE);
				resetHeader();
			}
		} else if (mCurrentScrollState == SCROLL_STATE_FLING
				&& firstVisibleItem == 0 && mRefreshState != REFRESHING) {
			setSelection(1);
			mBounceHack = true;
		} else if (mBounceHack && mCurrentScrollState == SCROLL_STATE_FLING) {
			setSelection(1);
		}

		if (mOnScrollListener != null) {
			mOnScrollListener.onScroll(view, firstVisibleItem,
					visibleItemCount, totalItemCount);
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		mCurrentScrollState = scrollState;

		if (mCurrentScrollState == SCROLL_STATE_IDLE) {
			mBounceHack = false;
		}

		if (mOnScrollListener != null) {
			mOnScrollListener.onScrollStateChanged(view, scrollState);
		}
	}

	public void prepareForRefresh() {
		resetHeaderPadding();

		head_image.setVisibility(View.GONE);
		// We need this hack, otherwise it will keep the previous drawable.
		head_image.setImageDrawable(null);
		head_progressbar.setVisibility(View.VISIBLE);

		// Set refresh view text to the refreshing label
		head_tips.setText(R.string.header_refreshing);

		mRefreshState = REFRESHING;
	}

	public void onRefresh() {
		Log.d(TAG, "onRefresh");

		if (mOnRefreshListener != null) {
			mOnRefreshListener.onRefresh();
		}
	}

	/**
	 * Resets the list to a normal state after a refresh.
	 * 
	 * @param lastUpdated
	 *            Last updated at.
	 */
	public void onRefreshComplete(CharSequence lastUpdated) {
		setLastUpdated(lastUpdated);
//		onRefreshComplete();
		resetHeader();

		// If refresh view is visible when loading completes, scroll down to
		// the next item.
		if (headerView.getBottom() > 0) {
			invalidateViews();
			setSelection(1);
		}
	}

	/**
	 * Resets the list to a normal state after a refresh.
	 */
	public void onRefreshComplete() {
		Log.d(TAG, "onRefreshComplete");
		setLastUpdated("最近更新:" + new Date().toLocaleString());
		resetHeader();

		// If refresh view is visible when loading completes, scroll down to
		// the next item.
		if (headerView.getBottom() > 0) {
			invalidateViews();
			setSelection(1);
		}
	}
	
	public void onNoMoreData() {
		headerView.setPadding(0, -1 * mRefreshViewHeight, 0, 0);
	}

	/**
	 * Invoked when the refresh view is clicked on. This is mainly used when
	 * there's only a few items in the list and it's not possible to drag the
	 * list.
	 */
	private class OnClickRefreshListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (mRefreshState != REFRESHING) {
				prepareForRefresh();
				onRefresh();
			}
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

	public void onClickComplete(int resource) {
		footer_normal_tips.setText(resource);
		onClickComplete();
	}

	public void onClickComplete(String tips) {
		footer_normal_tips.setText(tips);
		onClickComplete();
	}

	/**
	 * 点击事件完成
	 */
	public void onClickComplete() {
		footer_loading.setVisibility(View.GONE);
		footer_normal.setVisibility(View.VISIBLE);
	}

	public void setOnClickFooterListener(
			OnClickFooterListener onClickFooterListener) {
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
	 * Interface definition for a callback to be invoked when list should be
	 * refreshed.
	 */
	public interface OnRefreshListener {
		/**
		 * Called when the list should be refreshed.
		 * <p>
		 * A call to {@link CustomListView #onRefreshComplete()} is
		 * expected to indicate that the refresh has completed.
		 */
		public void onRefresh();
	}

	/**
	 * 定义一个接口<br />
	 * 内部的方法是用于点击footer时的动作
	 * 
	 * @author zhaoyong1990@gmail.com
	 * 
	 */
	public interface OnClickFooterListener {
		public void onClick();
	}
}
