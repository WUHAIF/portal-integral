package com.galaxy.portal.integral.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.galaxy.portal.common.system.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 资源-积分配置表
 * @Author: wuhaifeng
 * @Date:   2021-03-29
 * @Version: V1.0
 */
@Data
//@TableName("integral_table_config")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="integral_table_config对象", description="资源-积分配置表")
public class IntegralTableConfigVO implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
//	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private String id;
	/**表id*/
    @ApiModelProperty(value = "表id")
    private String tableId;
	/**表名称*/
    @ApiModelProperty(value = "表名称")
    private String tableName;
	/**表类型；0：公共资源表；1：个人资源表*/
    @ApiModelProperty(value = "表类型；0：公共资源表；1：个人资源表")
    private Integer tableType;
    /**当前表上传用户ID*/
    @ApiModelProperty(value = "当前表上传用户ID")
    private String uploadUser;
    /**当前表上传用户名称*/
    @ApiModelProperty(value = "当前表上传用户名称")
    private String uploadUserName;
    /**表所属部门*/
    @ApiModelProperty(value = "表所属部门")
    private String belongToUnit;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**更新用户*/
    @ApiModelProperty(value = "更新用户")
    private String updateUser;
	/**数据类型*/
    @ApiModelProperty(value = "数据类型")
    private Integer dataTypeId;
	/**上架；0：下架；1：上架*/
    @ApiModelProperty(value = "上架；0：下架；1：上架")
    private Integer putOnSale;
	/**搜索所需积分值*/
    @ApiModelProperty(value = "搜索所需积分值")
    private Integer searchIntegral;
    /**数据类型*/
    @ApiModelProperty(value = "数据类型")
    private String integral_data_type;
    /**最大设置积分*/
    @ApiModelProperty(value = "最大设置积分")
    private Integer integral_top_limit;
    /**最小设置积分*/
    @ApiModelProperty(value = "最小设置积分")
    private Integer integral_lower_limit;
    /**当前登录用户*/
    @ApiModelProperty(value = "当前登录用户")
    private SysUser sysUser;


}
