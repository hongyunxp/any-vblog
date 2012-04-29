package com.ilovn.app.anyvblog;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.ilovn.app.anyvblog.R;
import com.ilovn.app.anyvblog.application.ActivityStack;
import com.ilovn.app.anyvblog.update.Update;
/**
 * 检查更新的提示窗口
 * @author zhaoyong
 *
 */
public class UpdateDialogActivity extends Activity implements OnClickListener {
	final Activity activity = this;
	private TextView curVersion;
	private TextView newVersion;
	private TextView fileSize;
	private TextView desc;
	private Button btn_update;
	private Button btn_cancel;
	private Update info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update_dialog);
		ActivityStack.getInstance().addActivity(activity);
		initViews();
		checkIntent(getIntent());
		fillData();
	}

	private void fillData() {
		if (info != null) {
			try {
				curVersion.setText("当前版本号:" + getPackageManager().getPackageInfo(activity.getPackageName(), 64).versionName);
			} catch (NameNotFoundException e) {
			}
			newVersion .setText("最新版本号:" + info.getVersion());
			fileSize.setText("文件大小:" + info.getSize());
			desc.setText(info.getDesc());
		}
		
	}

	private void checkIntent(Intent intent) {
		if (intent.getExtras().containsKey("info")) {
			info = (Update) intent.getExtras().getSerializable("info");
		}
	}

	private void initViews() {
		curVersion = (TextView) findViewById(R.id.curVersion);
		newVersion = (TextView) findViewById(R.id.newVersion);
		desc = (TextView) findViewById(R.id.desc);
		fileSize = (TextView) findViewById(R.id.fileSize);
		btn_update = (Button) findViewById(R.id.btn_update);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(this);
		btn_update.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_cancel:
			activity.finish();
			break;
		case R.id.btn_update:
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(info.getUrl()));
			activity.startActivity(intent);
			activity.finish();
			break;
		default:
			break;
		}

	}
}
