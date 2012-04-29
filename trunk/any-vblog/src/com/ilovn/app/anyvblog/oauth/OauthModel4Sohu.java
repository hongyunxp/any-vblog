package com.ilovn.app.anyvblog.oauth;

import com.ilovn.app.anyvblog.api.SohuApi;
import com.ilovn.app.anyvblog.utils.Constants;
import com.ilovn.open.oauth.OAuthServiceProvider;
import com.ilovn.open.oauth.builder.ServiceBuilder;
import com.ilovn.open.oauth.model.SignatureType;
import com.ilovn.open.oauth.model.Token;

public class OauthModel4Sohu implements OauthModel {

	@Override
	public OAuthServiceProvider getServiceProvider() {
		// TODO Auto-generated method stub
		OAuthServiceProvider serviceProvider = new ServiceBuilder()
				.provider(SohuApi.class).apiKey(Constants.Sohu.CONSUMER_KEY)
				.apiSecret(Constants.Sohu.CONSUMER_SECRET)
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
