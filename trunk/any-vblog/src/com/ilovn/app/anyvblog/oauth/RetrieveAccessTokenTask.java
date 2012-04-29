package com.ilovn.app.anyvblog.oauth;

import android.net.Uri;
import android.os.AsyncTask;

import com.ilovn.open.oauth.OAuthServiceProvider;
import com.ilovn.open.oauth.model.OAuthConstants;
import com.ilovn.open.oauth.model.Token;
import com.ilovn.open.oauth.model.Verifier;

public class RetrieveAccessTokenTask extends AsyncTask<Uri, Void, Token> {
	private OAuthServiceProvider serviceProvider;
	private Token token;


	public RetrieveAccessTokenTask(OAuthServiceProvider serviceProvider, Token token) {
		this.serviceProvider = serviceProvider;
		this.token = token;
	}

	@Override
	protected Token doInBackground(Uri... params) {
		Uri uri = params[0];
		String v = uri.getQueryParameter(OAuthConstants.VERIFIER);
		Verifier verifier;
		if (v == null) {
			verifier = new Verifier("");
		} else {
			verifier = new Verifier(v);
		}
		Token accessToken = serviceProvider.getAccessToken(token, verifier);
		return accessToken;
	}
}
