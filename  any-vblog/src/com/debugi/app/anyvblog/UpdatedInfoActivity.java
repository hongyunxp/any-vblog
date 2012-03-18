package com.debugi.app.anyvblog;

import android.app.Activity;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
/**
 * 显示当前版本的信息
 * @author zhaoyong
 *
 */
public class UpdatedInfoActivity extends Activity implements OnClickListener {
	final Activity activity = this;
	private TextView curVersion;
	private Button btn_cancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.updated_info);
		initViews();
		try {
			curVersion.setText("当前版本号:" + getPackageManager().getPackageInfo(activity.getPackageName(), 64).versionName);
		} catch (NameNotFoundException e) {
		}
	}

	private void initViews() {
		curVersion = (TextView) findViewById(R.id.curVersion);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_cancel:
			activity.finish();
			break;
		default:
			break;
		}
	}
}
