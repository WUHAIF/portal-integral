package com.galaxy.portal.integral.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.galaxy.portal.integral.entity.IntegralUnitRel;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 积分-部门关系表
 * @Author: wuhaifeng
 * @Date:   2021-03-29
 * @Version: V1.0
 */
public interface IIntegralUnitRelService extends IService<IntegralUnitRel> {


    IntegralUnitRel selectOneByUnit(String uniteCode);
}
