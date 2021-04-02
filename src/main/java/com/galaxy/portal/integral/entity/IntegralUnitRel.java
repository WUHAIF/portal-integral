package com.galaxy.portal.integral.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;
import com.galaxy.portal.common.system.SysUser;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 积分-部门关系表
 * @Author: wuhaifeng
 * @Date:   2021-03-29
 * @Version: V1.0
 */
@Data
@TableName("integral_unit_rel")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="integral_unit_rel对象", description="积分-部门关系表")
public class IntegralUnitRel implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private String id;
	/**部门id*/
    @ApiModelProperty(value = "部门Code",required = true)
    private String unitCode;
	/**部门名称*/
    @ApiModelProperty(value = "部门名称",required = true)
    private String unitName;
	/**当前部门积分*/
    @ApiModelProperty(value = "当前部门积分")
    private Integer currentIntegral;
	/**已使用积分*/
    @ApiModelProperty(value = "已使用积分")
    private Integer usedIntegral;
    /**乐观锁版本*/
    @Version
    @ApiModelProperty(value = "乐观锁版本",hidden = true)
    private Integer version;
}
