package com.galaxy.portal.integral.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.galaxy.portal.integral.entity.IntegralUserRel;
import com.galaxy.portal.integral.mapper.IntegralUserRelMapper;
import com.galaxy.portal.integral.service.IIntegralUserRelService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 积分-用户关系表
 * @Author: wuhaifeng
 * @Date:   2021-03-29
 * @Version: V1.0
 */
@Service
public class IntegralUserRelServiceImpl extends ServiceImpl<IntegralUserRelMapper, IntegralUserRel> implements IIntegralUserRelService {

    @Override
    public IPage<IntegralUserRel> queryPageListWithUser(IntegralUserRel integralUserRel, Integer pageNo, Integer pageSize) {
        IPage<IntegralUserRel> page = new Page<IntegralUserRel>(pageNo, pageSize);
        page = this.baseMapper.queryPageListWithUser(page,integralUserRel);
        return page;
    }

    @Override
    public IntegralUserRel selectOneByUser(String userId) {
        IntegralUserRel integralUserRel = new IntegralUserRel();
        integralUserRel.setUserId(userId);
        QueryWrapper<IntegralUserRel> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(integralUserRel);
        IntegralUserRel resultRel = this.baseMapper.selectOne(queryWrapper);
        return resultRel;
    }
}
