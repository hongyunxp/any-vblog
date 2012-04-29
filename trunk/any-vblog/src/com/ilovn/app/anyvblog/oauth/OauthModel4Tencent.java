package com.ilovn.app.anyvblog.oauth;

import com.ilovn.app.anyvblog.api.TencentApi;
import com.ilovn.app.anyvblog.utils.Constants;
import com.ilovn.open.oauth.OAuthServiceProvider;
import com.ilovn.open.oauth.builder.ServiceBuilder;
import com.ilovn.open.oauth.model.SignatureType;
import com.ilovn.open.oauth.model.Token;

public class OauthModel4Tencent implements OauthModel {

	@Override
	public OAuthServiceProvider getServiceProvider() {
		// TODO Auto-generated method stub
		OAuthServiceProvider serviceProvider = new ServiceBuilder()
				.provider(TencentApi.class)
				.apiKey(Constants.Tencent.CONSUMER_KEY)
				.apiSecret(Constants.Tencent.CONSUMER_SECRET)
				.callback(Constants.CALL_BACK_URL)
				.signatureType(SignatureType.QueryString).build();
		return serviceProvider;
	}

	@Override
	public Token getAccessToken() {
		// TODO Auto-generated method stub
		return null;
	}
}
