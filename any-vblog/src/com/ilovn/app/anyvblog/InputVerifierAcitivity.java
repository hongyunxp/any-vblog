package com.ilovn.app.anyvblog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.ilovn.app.anyvblog.R;
import com.ilovn.app.anyvblog.application.ActivityStack;
import com.ilovn.app.anyvblog.utils.Constants;
import com.ilovn.open.oauth.model.OAuthConstants;

public class InputVerifierAcitivity extends Activity {
	private Button submit;
	private EditText verifier;
	private final Activity activity = this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inputverifier);
		ActivityStack.getInstance().addActivity(activity);
		initViews();
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				submitVerifier();
			}
		});
	}
	private void initViews() {
		submit = (Button) findViewById(R.id.submit);
		verifier = (EditText) findViewById(R.id.verifier);
	}
	private void submitVerifier() {
		String data = verifier.getText().toString();
		if (data != null && !"".equals(data)) {
			String url = Constants.CALL_BACK_URL + "?" + OAuthConstants.VERIFIER + "=" + data;
			Intent intent = new Intent(activity, OAuthActivity.class);
			intent.putExtra("url", url);
			activity.startActivity(intent);
			activity.finish();
		}
	}
}
