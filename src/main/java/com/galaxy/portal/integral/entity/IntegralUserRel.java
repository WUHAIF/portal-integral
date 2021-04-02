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
 * @Description: 积分-用户关系表
 * @Author: wuhaifeng
 * @Date:   2021-03-29
 * @Version: V1.0
 */
@Data
@TableName("integral_user_rel")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="integral_user_rel对象", description="积分-用户关系表")
public class IntegralUserRel implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private String id;
	/**用户id*/
    @ApiModelProperty(value = "用户id")
    private String userId;
	/**用户名称*/
    @ApiModelProperty(value = "用户名称")
    private String userName;
	/**允许使用积分*/
    @ApiModelProperty(value = "允许使用积分")
    private String allowUseIntegral;
	/**已使用的积分*/
    @ApiModelProperty(value = "已使用的积分")
    private String usedIntegral;
}
