package com.galaxy.portal.monitor.appconfig;


public class TomcatConfigInfo {

	/**
	 * jmx的url//配置在tomcat中的
	 */
	private String jmxURL;

	/**
	 * jmx.remote.credentials
	 * { "monitorRole", "wuhaifeng" } 用户名，密码
	 */
	private String[] credentials;

	/**
	 * threadObjName :格式：Catalina:type=ThreadPool,name="http-nio-端口号"
	 * 例子：Catalina:type=ThreadPool,name="http-nio-8080" tomcat的端口号
	 */
	private String threadObjName;

	public String getJmxURL() {
		return jmxURL;
	}
	public void setJmxURL(String jmxURL) {
		this.jmxURL = jmxURL;
	}
	public String[] getCredentials() {
		return credentials;
	}
	public void setCredentials(String[] credentials) {
		this.credentials = credentials;
	}
	public String getThreadObjName() {
		return threadObjName;
	}
	public void setThreadObjName(String threadObjName) {
		this.threadObjName = threadObjName;
	}
}
