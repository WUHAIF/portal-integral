package com.galaxy.portal.integral.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.galaxy.portal.integral.entity.IntegralUserRel;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 积分-用户关系表
 * @Author: wuhaifeng
 * @Date:   2021-03-29
 * @Version: V1.0
 */
public interface IIntegralUserRelService extends IService<IntegralUserRel> {

    IPage<IntegralUserRel> queryPageListWithUser(IntegralUserRel integralUserRel, Integer pageNo, Integer pageSize);

    IntegralUserRel selectOneByUser(String userId);
}
