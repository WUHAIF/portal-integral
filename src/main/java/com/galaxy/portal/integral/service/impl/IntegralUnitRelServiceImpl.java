package com.galaxy.portal.integral.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.galaxy.portal.integral.entity.IntegralUnitRel;
import com.galaxy.portal.integral.entity.IntegralUserRel;
import com.galaxy.portal.integral.mapper.IntegralUnitRelMapper;
import com.galaxy.portal.integral.service.IIntegralUnitRelService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 积分-部门关系表
 * @Author: wuhaifeng
 * @Date:   2021-03-29
 * @Version: V1.0
 */
@Service
public class IntegralUnitRelServiceImpl extends ServiceImpl<IntegralUnitRelMapper, IntegralUnitRel> implements IIntegralUnitRelService {


    @Override
    public IntegralUnitRel selectOneByUnit(String uniteCode) {
        IntegralUnitRel integralUnitRel = new IntegralUnitRel();
        integralUnitRel.setUnitCode(uniteCode);
        QueryWrapper<IntegralUnitRel> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(integralUnitRel);
        queryWrapper.last(" limit 1");
        IntegralUnitRel resultRel = this.baseMapper.selectOne(queryWrapper);
        return resultRel;

    }
}
