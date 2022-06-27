package com.galaxy.portal.monitor.impl;
import java.lang.management.MemoryUsage;
import java.text.SimpleDateFormat;
import java.util.*;

import com.galaxy.portal.monitor.AppMonitor;
import com.galaxy.portal.monitor.appconfig.TomcatConfigInfo;
import com.google.gson.Gson;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import lombok.extern.slf4j.Slf4j;

/**
 * Tomcat监控;获取Tomcat启动情况和内存使用情况
 */
@Slf4j
public class TomcatMonitorImpl implements AppMonitor {

	private static Gson gson = new Gson();

	private TomcatConfigInfo appInfo;

	public TomcatMonitorImpl(TomcatConfigInfo appInfo) {
		this.appInfo = appInfo;
	}

	@Override
	public Map<String, Object> getMonitorInfo() {

		HashMap<String, Object> tomcatMess = new HashMap<>();
		try {
			//jmx的url//配置在tomcat中的
			String jmxURL = appInfo.getJmxURL();
			JMXServiceURL serviceURL = new JMXServiceURL(jmxURL);

			Map map = new HashMap();
			String[] credentials = appInfo.getCredentials();
			map.put("jmx.remote.credentials", credentials);
			JMXConnector connector = JMXConnectorFactory.connect(serviceURL, map);
			MBeanServerConnection mbsc = connector.getMBeanServerConnection();

			// ------------------------- system ----------------------
			ObjectName runtimeObjName = new ObjectName("java.lang:type=Runtime");
			Date starttime = new Date((Long) mbsc.getAttribute(runtimeObjName, "StartTime"));
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//tomcat的启动时间
			tomcatMess.put("tcStarttime", df.format(starttime));

			Long timespan = (Long) mbsc.getAttribute(runtimeObjName, "Uptime");
			//tomcat的运行时长
			tomcatMess.put("tcRuntime", TomcatMonitor.formatTimeSpan(timespan));
			// ------------------------ JVM -------------------------
			// 堆使用率
			ObjectName heapObjName = new ObjectName("java.lang:type=Memory");
			MemoryUsage heapMemoryUsage = MemoryUsage.from(
					(CompositeDataSupport) mbsc.getAttribute(heapObjName, "HeapMemoryUsage"));
			long maxMemory = heapMemoryUsage.getMax();// 堆最大
			long commitMemory = heapMemoryUsage.getCommitted();// 堆当前分配
			long usedMemory = heapMemoryUsage.getUsed();

			tomcatMess.put("maxMemory", maxMemory);//最大堆内存
			tomcatMess.put("commitMemory", commitMemory);//当前分配堆内存
			tomcatMess.put("usedMemory", usedMemory);//当前使用堆内存

			//非堆内存使用量
			MemoryUsage nonheapMemoryUsage = MemoryUsage.from(
					(CompositeDataSupport) mbsc.getAttribute(heapObjName, "NonHeapMemoryUsage"));
			long noncommitMemory = nonheapMemoryUsage.getCommitted();
			long nonusedMemory = nonheapMemoryUsage.getUsed();
			tomcatMess.put("noncommitMemory", noncommitMemory);//非堆内存
			tomcatMess.put("nonusedMemory", nonusedMemory);//非堆内存使用量

            /*ObjectName permObjName = new ObjectName(
                    "java.lang:type=MemoryPool,name=\"Perm Gen\"");
            MemoryUsage permGenUsage = MemoryUsage
                    .from((CompositeDataSupport) mbsc.getAttribute(permObjName,
                            "Usage"));
            long committed = permGenUsage.getCommitted();// 持久堆大小
            long used = heapMemoryUsage.getUsed();//
            System.out.println("perm gen:" + (double) used * 100 / committed
                    + "%");// 持久堆使用率*/

			// -------------------- Session ---------------
			ObjectName managerObjName = new ObjectName("Catalina:type=Manager,*");
			Set<ObjectName> s = mbsc.queryNames(managerObjName, null);
			for (ObjectName obj : s) {

				System.out.println("应用名:" + obj.getKeyProperty("path"));
				ObjectName objname = new ObjectName(obj.getCanonicalName());
				System.out.println("最大会话数:" + mbsc.getAttribute(objname, "maxActiveSessions"));
				System.out.println("会话数:" + mbsc.getAttribute(objname, "activeSessions"));
				System.out.println("活动会话数:" + mbsc.getAttribute(objname, "sessionCounter"));
			}

			// ----------------- Thread Pool ----------------
			ObjectName threadpoolObjName = new ObjectName("Catalina:type=ThreadPool,*");
			Set<ObjectName> s2 = mbsc.queryNames(threadpoolObjName, null);
			for (ObjectName obj : s2) {
				//端口
				tomcatMess.put("tomcatPort", obj.getKeyProperty("name"));
				ObjectName objname = new ObjectName(obj.getCanonicalName());
				//最大线程数
				System.out.println("最大线程数:" + mbsc.getAttribute(objname, "maxThreads"));
				//当前线程数
				System.out.println("当前线程数:" + mbsc.getAttribute(objname, "currentThreadCount"));
				//繁忙线程数
				System.out.println("繁忙线程数:" + mbsc.getAttribute(objname, "currentThreadsBusy"));
			}
			tomcatMess.put("tomcatAlive",true);
			log.info("Tomcat使用情况：{}", gson.toJson(tomcatMess));
		} catch (Exception e) {
			e.printStackTrace();
			tomcatMess.put("tomcatAlive",false);
		}
		return tomcatMess;
	}

	public static String formatTimeSpan(long span) {
		long minseconds = span % 1000;

		span = span / 1000;
		long seconds = span % 60;

		span = span / 60;
		long mins = span % 60;

		span = span / 60;
		long hours = span % 24;

		span = span / 24;
		long days = span;
		return (new Formatter()).format("%1$d天 %2$02d:%3$02d:%4$02d.%5$03d", days, hours, mins, seconds, minseconds)
				.toString();
	}

	public TomcatConfigInfo getAppInfo() {
		return appInfo;
	}
	public void setAppInfo(TomcatConfigInfo appInfo) {
		this.appInfo = appInfo;
	}
}
