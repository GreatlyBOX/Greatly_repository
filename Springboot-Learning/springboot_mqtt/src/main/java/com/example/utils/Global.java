/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.example.utils;

/**
 * @author 启哲
 * @知乎专栏 Spring Boot学习教程 全局配置类
 * https://zhuanlan.zhihu.com/c_152148543
 * @创建时间 2018/1/8
 */
public class Global {
	/**
	 * 当前对象实例
	 */
	private static Global global = new Global();
	
	private static PropertiesLoader properties = new PropertiesLoader("/application.yml");

	/**
	 * 200：成功
	 * 500：失败
	 * 400：非空
	 */
	public static final String SUCCEED_CODE="200";
	public static final String ERROR_CODE="500";
	public static final String NONEMPTY_CODE="400";
	public static final String ALARM_SEEKHELP = "SeekHelp";
	public static final String LOW_BATTERY = "LowBattery";
	/**
	 * 页面获取常量
	 * @see ${fns:getConst('YES')}
	 */
	public static Object getConst(String field) {
		try {
			return Global.class.getField(field).get(null);
		} catch (Exception e) {
			// 异常代表无配置，这里什么也不做
		}
		return null;
	}

	public static String getConfig(String key){
		return properties.getProperty(key);
	}

	
}
