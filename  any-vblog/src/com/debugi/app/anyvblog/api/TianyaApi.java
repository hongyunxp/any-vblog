package com.debugi.app.anyvblog.api;

import com.debugi.open.oauth.builder.api.DefaultApi10a;
import com.debugi.open.oauth.model.Token;

public class TianyaApi extends DefaultApi10a {
	
	private static final String REQUEST_TOKEN_URL = "http://open.tianya.cn/oauth/request_token.php";
	private static final String ACCESS_TOKEN_URL = "http://open.tianya.cn/oauth/access_token.php";
	private static final String AUTHORIZE_URL = "http://open.tianya.cn/oauth/authorize.php?oauth_token=%s";
	
	@Override
	public String getRequestTokenEndpoint() 
	{
		return REQUEST_TOKEN_URL;
	}

	@Override
	public String getAccessTokenEndpoint() 
	{
		return ACCESS_TOKEN_URL;
	}

	@Override
	public String getAuthorizationUrl(Token requestToken) 
	{
		return String.format(AUTHORIZE_URL, requestToken.getToken());
	}

}
