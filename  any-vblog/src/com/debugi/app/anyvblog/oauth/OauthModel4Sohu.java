package com.debugi.app.anyvblog.oauth;

import com.debugi.app.anyvblog.api.SohuApi;
import com.debugi.app.anyvblog.utils.Constants;
import com.debugi.open.oauth.OAuthServiceProvider;
import com.debugi.open.oauth.builder.ServiceBuilder;
import com.debugi.open.oauth.model.SignatureType;
import com.debugi.open.oauth.model.Token;

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
