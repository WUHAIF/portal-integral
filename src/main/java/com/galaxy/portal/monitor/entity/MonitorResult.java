package com.galaxy.portal.monitor.entity;
import java.util.List;
import java.util.Map;

/**
 * 监控信息返回结果
 * {
 *     "msg": "请求成功",
 *     "resultdataset": [{
 *             "isAlived": 0,
 *             "itemName": "",
 *             "memoryUnit": "MB",
 *             "publishDate": "20210425",
 *             "rowCount": 0,
 *             "serviceCode": "应用服务内存占用大小",
 *             "serviceName": "应用服务",
 *             "unid": "6cd477a78c9f4bba90d7a64b4377b848",
 *             "usedMemory": 257.0291
 *         }],
 *     "returncode": "1"
 * }
 */
public class MonitorResult {

	private String msg;

	private String returncode;

	private List<Map<String,Object>> resultdataset;

	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getReturncode() {
		return returncode;
	}
	public void setReturncode(String returncode) {
		this.returncode = returncode;
	}
	public List<Map<String, Object>> getResultdataset() {
		return resultdataset;
	}
	public void setResultdataset(List<Map<String, Object>> resultdataset) {
		this.resultdataset = resultdataset;
	}
}
