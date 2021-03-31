package com.galaxy.portal.integral.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.galaxy.portal.integral.entity.IntegralUnitRel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @Description: 积分-部门关系表
 * @Author: jeecg-boot
 * @Date:   2021-03-29
 * @Version: V1.0
 */
public interface IntegralUnitRelMapper extends BaseMapper<IntegralUnitRel> {

}
