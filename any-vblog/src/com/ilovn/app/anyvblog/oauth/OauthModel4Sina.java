package com.ilovn.app.anyvblog.oauth;

import com.ilovn.app.anyvblog.api.SinaApi;
import com.ilovn.app.anyvblog.utils.Constants;
import com.ilovn.open.oauth.OAuthServiceProvider;
import com.ilovn.open.oauth.builder.ServiceBuilder;
import com.ilovn.open.oauth.model.SignatureType;
import com.ilovn.open.oauth.model.Token;

public class OauthModel4Sina implements OauthModel {

	@Override
	public OAuthServiceProvider getServiceProvider() {
		// TODO Auto-generated method stub
		OAuthServiceProvider serviceProvider = new ServiceBuilder()
				.provider(SinaApi.class).apiKey(Constants.Sina.CONSUMER_KEY)
				.apiSecret(Constants.Sina.CONSUMER_SECRET)
				.callback(Constants.CALL_BACK_URL)
				.signatureType(SignatureType.Header).build();
		return serviceProvider;
	}

	@Override
	public Token getAccessToken() {
		// TODO Auto-generated method stub
		return null;
	}

}
