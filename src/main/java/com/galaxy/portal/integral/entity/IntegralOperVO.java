package com.galaxy.portal.integral.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.galaxy.portal.common.system.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.util.List;

/**
 * @Author: wuhaifeng
 * @DateTime: 2021/4/2 9:26
 * @Description: 积分操作对象
 * @version v1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="积分操作对象", description="外部对积分进行操作的类")
public class IntegralOperVO {

    /**操作人id*/
    @ApiModelProperty(value = "操作人id",required = true)
    private String operUserId;
    /**操作人姓名*/
    @ApiModelProperty(value = "操作人姓名",required = true)
    private String operUserName;
    /**操作人部门编码*/
    @ApiModelProperty(value = "操作人部门编码",required = true)
    private String oPerUserUnitCode;
    /**操作人部门名称*/
    @ApiModelProperty(value = "操作人部门名称",required = true)
    private String oPerUserUnitName;
    /**被操作部门集合*/
    @ApiModelProperty(value = "被操作部门集合",required = true)
    List<String> sysUnitList;
    /**被操作用户集合，预留字段*/
    @ApiModelProperty(value = "被操作用户集合")
    private List<String> sysUserList;
    /**积分类型；0：下发积分；1：扣除积分；2：消费积分；3：收入积分*/
    @ApiModelProperty(value = "积分类型；0：下发积分；1：扣除积分；2：消费积分；3：收入积分",required = true)
    private Integer integralType;
    /**操作积分分值，下发和收入为正数，扣除和消费为负数*/
    @ApiModelProperty(value = "操作积分分值，下发和收入为正数，扣除和消费为负数",required = true)
    private Integer operIntegralNum;
    /**操作详细描述*/
    @ApiModelProperty(value = "操作详细描述",required = true)
    private String operDatail;
    /**消费的资源(比如查询表)*/
    @ApiModelProperty(value = "消费的资源(比如查询表),消费时候必须选择")
    private String consumeResource;
}
