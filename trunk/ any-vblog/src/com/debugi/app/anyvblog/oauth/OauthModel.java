package com.debugi.app.anyvblog.oauth;

import com.debugi.open.oauth.OAuthServiceProvider;
import com.debugi.open.oauth.model.Token;

public interface OauthModel {
	public OAuthServiceProvider getServiceProvider();
	public Token getAccessToken();
}
