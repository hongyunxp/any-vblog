package com.ilovn.app.anyvblog.oauth;

import com.ilovn.open.oauth.OAuthServiceProvider;
import com.ilovn.open.oauth.model.Token;
/**
 * OAuth认证的模型<br />
 * 包含两个方法：
 * 	<li>获取OAuthServiceProvider</li>
 * 	<li>获取授权的Token</li>
 * 
 * @author zhaoyong
 *
 */
public interface OauthModel {
	public OAuthServiceProvider getServiceProvider();
	public Token getAccessToken();
}
