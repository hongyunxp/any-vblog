package com.debugi.app.anyvblog.model;

import com.debugi.open.oauth.model.Parameters;
/**
 * 定义一个接口，作为数据转换
 * @author Administrator
 *
 */
public interface SendData {
	/**
	 * 将成员转换为对应的请求参数
	 * @return
	 */
	public Parameters conver();
}
