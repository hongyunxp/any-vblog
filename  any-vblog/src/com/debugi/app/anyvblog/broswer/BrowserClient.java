package com.debugi.app.anyvblog.broswer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.debugi.app.anyvblog.OAuthActivity;
import com.debugi.app.anyvblog.utils.Constants;
import com.debugi.open.oauth.model.OAuthConstants;

public class BrowserClient extends WebViewClient {
	private Activity activity;
	private WebView webView;

	public BrowserClient(Activity activity, WebView webView) {
		super();
		this.activity = activity;
		this.webView = webView;
	}

	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		String verifier = Uri.parse(url).getQueryParameter(
				OAuthConstants.VERIFIER);
		if (verifier != null && !"".equals(verifier)) {
			Intent intent = new Intent(activity, OAuthActivity.class);
			intent.putExtra("url", url);
			activity.startActivity(intent);
			activity.finish();
			return true;
		}
		if (url.startsWith(Constants.CALL_BACK_URL)) {
			Intent intent = new Intent(activity, OAuthActivity.class);
			intent.putExtra("url", url);
			activity.startActivity(intent);
			activity.finish();
			return true;
		}
		return false;
	}

	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		activity.setProgressBarVisibility(true);
		if (url.startsWith(Constants.CALL_BACK_URL)) {
			Intent intent = new Intent(activity, OAuthActivity.class);
			intent.putExtra("url", url);
			activity.startActivity(intent);
		}

		super.onPageStarted(view, url, favicon);
	}

	@Override
	public void onPageFinished(WebView view, String url) {
		Log.i("BrowserClent", "page load finish");
		webView.loadUrl("javascript:window.javaMethod.getHtml(document.getElementsByTagName('span')[0].innerHTML)");
		webView.loadUrl("javascript:window.javaMethod.getHtml(document.getElementById('verifier').innerHTML)");
		activity.setProgressBarVisibility(false);
		super.onPageFinished(view, url);
	}

	@Override
	public void onReceivedError(WebView view, int errorCode,
			String description, String failingUrl) {
		Toast.makeText(activity,
				"错误代码:" + errorCode + " " + description + " " + failingUrl,
				Toast.LENGTH_LONG).show();
		super.onReceivedError(view, errorCode, description, failingUrl);
	}
}
