package com.galaxy.portal.integral.service;

import com.galaxy.portal.integral.entity.IntegralOperVO;

import java.util.List;

/**
 * @Author: wuhaifeng
 * @DateTime: 2021/4/1 22:39
 * @Description: 积分操作对外接口
 */
public interface IIntegraloperateService {
    List<String> integralIncreaseOrDeductBatch(IntegralOperVO integralUnitRelVO);

    boolean integralConsume(IntegralOperVO integralOperVO);
}
