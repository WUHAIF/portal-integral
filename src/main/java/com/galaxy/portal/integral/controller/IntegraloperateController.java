package com.galaxy.portal.integral.controller;

import com.galaxy.portal.common.vo.Result;
import com.galaxy.portal.integral.entity.IntegralOperVO;
import com.galaxy.portal.integral.service.IIntegraloperateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: wuhaifeng
 * @DateTime: 2021/4/1 21:12
 * @Description: 积分对外操作类
 */
@Api(tags="外部操作积分")
@RestController
@Slf4j
@RequestMapping("/integraloper")
public class IntegraloperateController {

    @Resource
    private IIntegraloperateService integraloperateService;
    /**
     * 积分分发/扣除--管理员操作
     *
     * @return
     */
    @ApiOperation(value="积分分发/扣除--管理员操作", notes="积分分发/扣除--管理员操作")
    @PutMapping("/integralIncreaseOrDeduct")
    public Result<?> IntegralIncreaseOrDeductBatch(@RequestBody IntegralOperVO integralOperVO) {

        List<String> errorList = integraloperateService.integralIncreaseOrDeductBatch(integralOperVO);

        return Result.OK("操作成功");
    }

    /**
     * 用户消费积分
     *
     * @return
     */
    @ApiOperation(value="用户消费积分", notes="用户消费积分")
    @PutMapping("/integralConsume")
    public Result<?> integralConsume(@RequestBody IntegralOperVO integralOperVO) {

        boolean flag = integraloperateService.integralConsume(integralOperVO);
        return Result.OK("操作成功");
    }
}
