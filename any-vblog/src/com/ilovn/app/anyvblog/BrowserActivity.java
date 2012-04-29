package com.ilovn.app.anyvblog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.ilovn.app.anyvblog.R;
import com.ilovn.app.anyvblog.application.ActivityStack;
import com.ilovn.app.anyvblog.broswer.BrowserClient;
import com.ilovn.app.anyvblog.utils.Constants;
import com.ilovn.open.oauth.model.OAuthConstants;

public class BrowserActivity extends Activity {
	private WebView webView;
	private final Activity activity = this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		setProgressBarVisibility(true);
		setContentView(R.layout.browser);
		ActivityStack.getInstance().addActivity(activity);
		Intent intent = getIntent();
		
		
		webView = (WebView) findViewById(R.id.webView);
		
		
		webView.getSettings().setJavaScriptEnabled(true);
		//设置双击缩放页面
		webView.getSettings().setUseWideViewPort(true);
		
		webView.getSettings().setSupportZoom(true);
		
		webView.addJavascriptInterface(new JavaScriptInterface(), "javaMethod");
		
		//webView.getSettings().setLoadWithOverviewMode(true);
		String url = intent.getExtras().getString("url");
		if(URLUtil.isNetworkUrl(url)) {
			webView.loadUrl(url);
		}
		webView.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				activity.setProgress(newProgress * 1000);
			}
			
		});
		
		webView.setWebViewClient(new BrowserClient(activity, webView));
	}
	@Override
	protected void onNewIntent(Intent intent) {
//		String url = intent.getExtras().getString("url");
//		System.out.println(url);
//		if(URLUtil.isNetworkUrl(url)) {
//			webView.loadUrl(url);
//		}
		super.onNewIntent(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(GROUP, ITEM_EXIT, 1, "完成(授权成功方能点击)");
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (item.getItemId() == ITEM_EXIT) {
			Intent intent = new Intent(activity, InputVerifierAcitivity.class);
			activity.startActivity(intent);
			activity.finish();
			return true;
		} else {
			return false;
		}
	}
	
	class JavaScriptInterface {
		public void getHtml(String html) {
			try {
				new Integer(html);
				if (html != null && !"".equals(html) && !html.startsWith("<a")) {
					Intent intent = new Intent(activity, OAuthActivity.class);
					intent.putExtra("url", Constants.CALL_BACK_URL + "?" + OAuthConstants.VERIFIER + "=" + html);
					activity.startActivity(intent);
					activity.finish();
				}
			} catch (Exception e) {
			}
		}
	}
	
	
	private static final int GROUP = 1;
	private static final int ITEM_EXIT = 0;
	
	
}
