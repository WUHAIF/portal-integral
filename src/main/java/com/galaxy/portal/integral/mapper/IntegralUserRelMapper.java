package com.galaxy.portal.integral.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.galaxy.portal.integral.entity.IntegralUserRel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @Description: 积分-用户关系表
 * @Author: wuhaifeng
 * @Date:   2021-03-29
 * @Version: V1.0
 */
public interface IntegralUserRelMapper extends BaseMapper<IntegralUserRel> {

    IPage<IntegralUserRel> queryPageListWithUser(IPage<IntegralUserRel> page, @Param("userRel")  IntegralUserRel integralUserRel);
}
