package com.galaxy.portal.integral.service;

import com.galaxy.portal.common.customize.annotation.IsTryAgain;
import com.galaxy.portal.integral.entity.IntegralOperVO;

import java.util.List;

/**
 * @Author: wuhaifeng
 * @DateTime: 2021/7/3 16:05
 * @Description: TODO
 */
public interface IIntegraloperateService {
    List<String> integralIncreaseOrDeductBatch(IntegralOperVO integralOperVO);

    @IsTryAgain
    boolean integralConsume(IntegralOperVO integralOperVO);
}
