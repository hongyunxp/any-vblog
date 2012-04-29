package com.ilovn.app.anyvblog.model;

import java.util.Map;
/**
 * 接口用于定义主页时间线返回的数据微博内容及参数，需要实现用于数据到DataAdapter以及自行转换到map
 * @author Administrator
 *
 */
public interface Data {
	/**
	 * 转换到数据适配
	 * @return
	 */
	public DataAdapter conver2Data();
	/**
	 * 转换到map
	 * @return
	 */
	public Map<String, Object> conver();
}
