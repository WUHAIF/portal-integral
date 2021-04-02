package com.galaxy.portal.common.constant;

public interface CommonConstant {

	/**
	 * 正常状态
	 */
	public static final Integer STATUS_NORMAL = 0;

	/**
	 * 禁用状态
	 */
	public static final Integer STATUS_DISABLE = -1;

	/**
	 * 删除标志
	 */
	public static final Integer DEL_FLAG_1 = 1;

	/**
	 * 未删除
	 */
	public static final Integer DEL_FLAG_0 = 0;

	/**
	 * 系统日志类型： 登录
	 */
	public static final int LOG_TYPE_1 = 1;

	/**
	 * 系统日志类型： 操作
	 */
	public static final int LOG_TYPE_2 = 2;

	/**
	 * 操作日志类型： 查询
	 */
	public static final int OPERATE_TYPE_1 = 1;

	/**
	 * 操作日志类型： 添加
	 */
	public static final int OPERATE_TYPE_2 = 2;

	/**
	 * 操作日志类型： 更新
	 */
	public static final int OPERATE_TYPE_3 = 3;

	/**
	 * 积分操作类型： 下发
	 */
	public static final int INTEGEAL_ADD = 0;

	/**
	 * 积分操作类型： 扣除
	 */
	public static final int INTEGEAL_DEDUCT = 1;

	/**
	 * 积分操作类型： 使用
	 */
	public static final int INTEGEAL_USED = 2;

	/**
	 * 积分操作类型： 使用
	 */
	public static final int INTEGEAL_GAIN = 3;

	/** {@code 500 Server Error} (HTTP/1.0 - RFC 1945) */
    public static final Integer SC_INTERNAL_SERVER_ERROR_500 = 500;
    /** {@code 200 OK} (HTTP/1.0 - RFC 1945) */
    public static final Integer SC_OK_200 = 200;

    /**访问权限认证未通过 510*/
    public static final Integer SC_JEECG_NO_AUTHZ=510;





}
