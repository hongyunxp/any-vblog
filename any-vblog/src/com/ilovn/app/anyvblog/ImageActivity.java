package com.ilovn.app.anyvblog;

import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.ilovn.app.anyvblog.application.ActivityStack;
import com.ilovn.app.anyvblog.image.AsyncImageLoader;
import com.ilovn.app.anyvblog.utils.Config;
import com.ilovn.app.anyvblog.utils.ImageUtils;
import com.ilovn.app.anyvblog.utils.MyHandler;
import com.ilovn.app.anyvblog.widget.zoom.DynamicZoomControl;
import com.ilovn.app.anyvblog.widget.zoom.ImageZoomView;
import com.ilovn.app.anyvblog.widget.zoom.LongPressZoomListener;

public class ImageActivity extends Activity implements OnClickListener {
	private final Activity activity = this;
	private Button btn_back, btn_save;
	/** Constant used as menu item id for resetting zoom state */
	private static final int MENU_ID_RESET = 0;
	/** Image zoom view */
	private ImageZoomView mZoomView;
	/** Zoom control */
	private DynamicZoomControl mZoomControl;
	/** Decoded bitmap image */
	private Bitmap mBitmap;
	/** On touch listener for zoom view */
	private LongPressZoomListener mZoomListener;
	private ZoomControls mZoomControls;
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.image);
		ActivityStack.getInstance().addActivity(activity);
		initViews();
		String url = getIntent().getExtras().getString("url");
		mZoomControl = new DynamicZoomControl();
		mZoomListener = new LongPressZoomListener(activity);
		mZoomListener.setZoomControl(mZoomControl);
		mZoomView.setZoomState(mZoomControl.getZoomState());
		Drawable drawable = AsyncImageLoader.loadImageFromUrl(url);
		mBitmap = ((BitmapDrawable) drawable).getBitmap();
		mZoomView.setImage(mBitmap);
		mZoomView.setOnTouchListener(mZoomListener);
		mZoomControl.setAspectQuotient(mZoomView.getAspectQuotient());

		resetZoomState();

		handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				if (msg.what == MyHandler.Handler_Saved) {
					Config.toast(activity, "图片保存成功!", Toast.LENGTH_SHORT);
				}
			};
		};
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		mBitmap.recycle();
		mZoomView.setOnTouchListener(null);
		mZoomControl.getZoomState().deleteObservers();
	}

	private void initViews() {
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_save = (Button) findViewById(R.id.btn_save);
		mZoomView = (ImageZoomView) findViewById(R.id.pic);
		mZoomControls = (ZoomControls) findViewById(R.id.zoomCtrl);
		btn_back.setOnClickListener(this);
		btn_save.setOnClickListener(this);

		mZoomControls.setOnZoomInClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				float z = mZoomControl.getZoomState().getZoom() + 0.25f;
				mZoomControl.getZoomState().setZoom(z);
				mZoomControl.getZoomState().notifyObservers();
			}
		});
		mZoomControls.setOnZoomOutClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				float z = mZoomControl.getZoomState().getZoom() - 0.25f;
				mZoomControl.getZoomState().setZoom(z);
				mZoomControl.getZoomState().notifyObservers();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, MENU_ID_RESET, 2, "Reset");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_ID_RESET:
			resetZoomState();
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * Reset zoom state and notify observers
	 */
	private void resetZoomState() {
		mZoomControl.getZoomState().setPanX(0.5f);
		mZoomControl.getZoomState().setPanY(0.5f);
		mZoomControl.getZoomState().setZoom(1f);
		mZoomControl.getZoomState().notifyObservers();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			activity.finish();
			break;
		case R.id.btn_save:
			new Thread() {
				@Override
				public void run() {
					try {
						ImageUtils.saveImage(mBitmap, "pic_" + System.currentTimeMillis());
					} catch (IOException e) {
						e.printStackTrace();
					}
					Message message = new Message();
					message.what = MyHandler.Handler_Saved;
					handler.sendMessage(message);
				}
			}.start();
			break;
		default:
			break;
		}

	}
}
