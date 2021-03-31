package com.galaxy.portal.integral.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.galaxy.portal.common.vo.Result;
import com.galaxy.portal.integral.entity.IntegralTableConfig;
import com.galaxy.portal.integral.entity.IntegralUnitRel;
import com.galaxy.portal.integral.mapper.IntegralUnitRelMapper;
import com.galaxy.portal.integral.service.IIntegralUnitRelService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 积分-部门关系表
 * @Author: jeecg-boot
 * @Date:   2021-03-29
 * @Version: V1.0
 */
@Service
public class IntegralUnitRelServiceImpl extends ServiceImpl<IntegralUnitRelMapper, IntegralUnitRel> implements IIntegralUnitRelService {

}
