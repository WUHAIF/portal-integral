package com.galaxy.portal.monitor;

import com.galaxy.portal.monitor.appconfig.TomcatConfigInfo;
import com.galaxy.portal.monitor.impl.TomcatMonitorImpl;
public class AppMonitorFactory {

	public static AppMonitor createAppMonitor(String appName){

		if("tomcat".equals(appName)){
			//获取配置数据
			TomcatConfigInfo tomcatConfigInfo = new TomcatConfigInfo();
			tomcatConfigInfo.setJmxURL("service:jmx:rmi:///jndi/rmi://127.0.0.1:8999/jmxrmi");
			String[] credentials = new String[] { "monitorRole", "wuhaifeng" };
			tomcatConfigInfo.setCredentials(credentials);
			tomcatConfigInfo.setThreadObjName("Catalina:type=ThreadPool,name=\"http-nio-8080\"");
			TomcatMonitorImpl tomcatMonitor = new TomcatMonitorImpl(tomcatConfigInfo);
			return tomcatMonitor;

		}else {
			return null;
		}
	}
}
