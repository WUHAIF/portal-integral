package com.galaxy.portal.monitor.impl;
import java.lang.management.MemoryUsage;
import java.text.SimpleDateFormat;
import java.util.*;

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

@Slf4j
public class TomcatMonitor {


	private static Gson gson = new Gson();

	/**
	 * main方法
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			//jmx的url//配置在tomcat中的
			String jmxURL = "service:jmx:rmi:///jndi/rmi://127.0.0.1:8999/jmxrmi";
			HashMap<String, Object> tomcatMess = new HashMap<>();
			JMXServiceURL serviceURL = new JMXServiceURL(jmxURL);

			Map map = new HashMap();
			String[] credentials = new String[] { "monitorRole", "wuhaifeng" };
			map.put("jmx.remote.credentials", credentials);
			JMXConnector connector = JMXConnectorFactory.connect(serviceURL, map);
			MBeanServerConnection mbsc = connector.getMBeanServerConnection();

			// 端口最好是动态取得
			ObjectName threadObjName = new ObjectName(
					"Catalina:type=ThreadPool,name=\"http-nio-8080\"");//tomcat配置的端口
			MBeanInfo mbInfo = mbsc.getMBeanInfo(threadObjName);

			String attrName = "currentThreadCount";// tomcat的线程数对应的属性值
			MBeanAttributeInfo[] mbAttributes = mbInfo.getAttributes();
          /*  System.out.println("currentThreadCount:"
                    + mbsc.getAttribute(threadObjName, attrName));
            // heap
            for (int j = 0; j < mbsc.getDomains().length; j++) {
                System.out.println("###########" + mbsc.getDomains()[j]);
            }*/

			Set MBeanset = mbsc.queryMBeans(null, null);
			//MBean的数量
			Iterator MBeansetIterator = MBeanset.iterator();
			//寻求(查看所有命令)
           /* while (MBeansetIterator.hasNext()) {
                ObjectInstance objectInstance = (ObjectInstance) MBeansetIterator
                        .next();
                ObjectName objectName = objectInstance.getObjectName();
                String canonicalName = objectName.getCanonicalName();
                System.out.println("canonicalName : " + canonicalName);
                if (canonicalName
                        .equals("Catalina:host=localhost,type=Cluster")) {
                    // Get details of cluster MBeans
                    System.out.println("Cluster MBeans Details:");
                    System.out
                            .println("=========================================");
                    // getMBeansDetails(canonicalName);
                    String canonicalKeyPropList = objectName
                            .getCanonicalKeyPropertyListString();
                }
            }*/
			// ------------------------- system ----------------------
			ObjectName runtimeObjName = new ObjectName("java.lang:type=Runtime");
            /*System.out.println("厂商:"
                    + (String) mbsc.getAttribute(runtimeObjName, "VmVendor"));
            System.out.println("程序:"
                    + (String) mbsc.getAttribute(runtimeObjName, "VmName"));
            System.out.println("版本:"
                    + (String) mbsc.getAttribute(runtimeObjName, "VmVersion"));*/
			Date starttime = new Date((Long) mbsc.getAttribute(runtimeObjName,
					"StartTime"));
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//tomcat的启动时间
			tomcatMess.put("tcStarttime",df.format(starttime));

			Long timespan = (Long) mbsc.getAttribute(runtimeObjName, "Uptime");
			//tomcat的运行时长
			tomcatMess.put("tcRuntime",TomcatMonitor.formatTimeSpan(timespan));
			// ------------------------ JVM -------------------------
			// 堆使用率
			ObjectName heapObjName = new ObjectName("java.lang:type=Memory");
			MemoryUsage heapMemoryUsage = MemoryUsage
					.from((CompositeDataSupport) mbsc.getAttribute(heapObjName,
							"HeapMemoryUsage"));
			long maxMemory = heapMemoryUsage.getMax();// 堆最大
			long commitMemory = heapMemoryUsage.getCommitted();// 堆当前分配
			long usedMemory = heapMemoryUsage.getUsed();

			tomcatMess.put("maxMemory",maxMemory);//最大堆内存
			tomcatMess.put("commitMemory",commitMemory);//当前分配堆内存
			tomcatMess.put("usedMemory",usedMemory);//当前使用堆内存

			//非堆内存使用量
			MemoryUsage nonheapMemoryUsage = MemoryUsage
					.from((CompositeDataSupport) mbsc.getAttribute(heapObjName,
							"NonHeapMemoryUsage"));
			long noncommitMemory = nonheapMemoryUsage.getCommitted();
			long nonusedMemory = nonheapMemoryUsage.getUsed();
			tomcatMess.put("noncommitMemory",noncommitMemory);//非堆内存
			tomcatMess.put("nonusedMemory",nonusedMemory);//非堆内存使用量

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
			ObjectName managerObjName = new ObjectName(
					"Catalina:type=Manager,*");
			Set<ObjectName> s = mbsc.queryNames(managerObjName, null);
			for (ObjectName obj : s) {



				System.out.println("应用名:" + obj.getKeyProperty("path"));
				ObjectName objname = new ObjectName(obj.getCanonicalName());
				System.out.println("最大会话数:"
						+ mbsc.getAttribute(objname, "maxActiveSessions"));
				System.out.println("会话数:"
						+ mbsc.getAttribute(objname, "activeSessions"));
				System.out.println("活动会话数:"
						+ mbsc.getAttribute(objname, "sessionCounter"));
			}

			// ----------------- Thread Pool ----------------
			ObjectName threadpoolObjName = new ObjectName(
					"Catalina:type=ThreadPool,*");
			Set<ObjectName> s2 = mbsc.queryNames(threadpoolObjName, null);
			for (ObjectName obj : s2) {
				//端口
				tomcatMess.put("tomcatPort",obj.getKeyProperty("name"));
				ObjectName objname = new ObjectName(obj.getCanonicalName());
				//最大线程数
				System.out.println("最大线程数:"
						+ mbsc.getAttribute(objname, "maxThreads"));
				//当前线程数
				System.out.println("当前线程数:"
						+ mbsc.getAttribute(objname, "currentThreadCount"));
				//繁忙线程数
				System.out.println("繁忙线程数:"
						+ mbsc.getAttribute(objname, "currentThreadsBusy"));
			}

			log.info("Tomcat使用情况：{}",gson.toJson(tomcatMess));
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		return (new Formatter()).format("%1$d天 %2$02d:%3$02d:%4$02d.%5$03d",
				days, hours, mins, seconds, minseconds).toString();
	}
}
