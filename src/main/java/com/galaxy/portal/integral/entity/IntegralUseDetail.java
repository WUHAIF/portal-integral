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
 * @Description: 积分使用明细表
 * @Author: wuhaifeng
 * @Date:   2021-03-29
 * @Version: V1.0
 */
@Data
@TableName("integral_use_detail")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="integral_use_detail对象", description="积分使用明细表")
public class IntegralUseDetail implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private String id;
	/**部门编码*/
    @ApiModelProperty(value = "部门编码")
    private String unitCode;
	/**部门名称*/
    @ApiModelProperty(value = "部门名称")
    private String unitName;
	/**用户id*/
    @ApiModelProperty(value = "用户id")
    private String userId;
	/**用户名称*/
    @ApiModelProperty(value = "用户名称")
    private String userName;
	/**积分类型；0：新增积分；1：消费积分*/
    @ApiModelProperty(value = "积分类型；0：下发积分；2：扣除积分；3：消费积分；4：收入积分")
    private Integer integralType;
	/**操作时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "操作时间",hidden = true)
    private Date operTime;
	/**操作积分分值*/
    @ApiModelProperty(value = "操作积分分值")
    private Integer operIntegralNum;
	/**部门剩余积分*/
    @ApiModelProperty(value = "部门剩余积分",hidden = true)
    private Integer remainIntegralNum;
	/**操作详细描述*/
    @ApiModelProperty(value = "操作详细描述")
    private String operDatail;
}
