package com.galaxy.portal.integral.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 数据等级定义表
 * @Author: jeecg-boot
 * @Date:   2021-03-29
 * @Version: V1.0
 */
@Data
@TableName("integral_data_type")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="integral_data_type对象", description="数据等级定义表")
public class IntegralDataType implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private String id;
	/**数据类型*/
    @ApiModelProperty(value = "数据类型")
    private String integralDataType;
	/**创建用户*/
    @ApiModelProperty(value = "创建用户")
    private String createUser;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新日期")
    private Date createTime;
	/**更新用户*/
    @ApiModelProperty(value = "更新用户")
    private String updateUser;
	/**更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新时间")
    private Date updataTime;
	/**积分设置上限*/
    @ApiModelProperty(value = "积分设置上限")
    private Integer integralTopLimit;
	/**积分设置下限*/
    @ApiModelProperty(value = "积分设置下限")
    private Integer integralLowerLimit;
}
