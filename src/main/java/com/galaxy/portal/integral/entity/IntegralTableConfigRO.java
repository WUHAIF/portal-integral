package com.galaxy.portal.integral.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 资源-积分配置表
 * @Author: jeecg-boot
 * @Date:   2021-03-29
 * @Version: V1.0
 */
@Data
public class IntegralTableConfigRO implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
    private String id;
	/**表id*/
    private String tableId;
	/**表名称*/
    private String tableName;
	/**表类型；0：公共资源表；1：个人资源表*/
    private Integer tableType;
    /**当前表上传用户ID*/
    private String uploadUser;
    /**当前表上传用户名称*/
    private String uploadUserName;
    /**表所属部门*/
    private String belongToUnit;
	/**更新日期*/
    private Date updateTime;
	/**更新用户*/
    private String updateUser;
	/**数据类型*/
    private Integer dataTypeId;
	/**上架；0：下架；1：上架*/
    private Integer putOnSale;
	/**搜索所需积分值*/
    private Integer searchIntegral;
    /**是否可以编辑*/
    private boolean allowEdit;
    /**积分类型*/
    private String integralDataType;
    /**积分指定上限*/
    private Integer integralTopLimit;
    /**积分指定下限*/
    private Integer integralLowerLimit;

}
