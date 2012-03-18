package com.debugi.app.anyvblog.model;

import java.util.Map;

/**
 * 该接口用于声明用于将用户数据转换到UserAdapter的方法
 * @author Administrator
 *
 */
public interface User {
	public UserAdapter conver2User();
	public Map<String, Object> conver();
}
