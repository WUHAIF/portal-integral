package com.galaxy.portal.integral.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author: wuhaifeng
 * @DateTime: 2021/4/2 9:34
 * @Description: 部门临时
 */

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="部门对象", description="部门对象")
public class SysUnit {

    /**部门编码*/
    @ApiModelProperty(value = "操作人id",required = true)
    private String unitCode;

    /**部门名称*/
    @ApiModelProperty(value = "操作人id",required = true)
    private String unitName;
}
