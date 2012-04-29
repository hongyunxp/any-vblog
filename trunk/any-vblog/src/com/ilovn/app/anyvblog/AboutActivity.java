package com.ilovn.app.anyvblog;

import com.ilovn.app.anyvblog.R;
import com.ilovn.app.anyvblog.application.ActivityStack;
import com.ilovn.app.anyvblog.exception.NormalException;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AboutActivity extends Activity implements OnClickListener {
	@SuppressWarnings("unused")
	private static final String TAG = "AboutActivity";
	final Activity activity = this;
	private TextView version;
	private Button btn_back;
	private Button btn_home;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		ActivityStack.getInstance().addActivity(activity);
		initViews();
		try {
			version.setText("当前版本:" + getPackageManager().getPackageInfo(getPackageName(), 64).versionName);
		} catch (NameNotFoundException e) {
			throw new NormalException(e.getMessage());
		}
	}

	private void initViews() {
		version = (TextView) findViewById(R.id.version);
		btn_home = (Button) findViewById(R.id.btn_home);
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		btn_home.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			activity.finish();
			break;
		case R.id.btn_home:
			Intent intent = new Intent(activity, MainActivity.class);
			activity.startActivity(intent);
			activity.finish();
			break;

		default:
			break;
		}
	}
}