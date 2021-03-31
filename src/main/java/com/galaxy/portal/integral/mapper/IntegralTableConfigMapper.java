package com.galaxy.portal.integral.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.galaxy.portal.integral.entity.IntegralTableConfig;
import com.galaxy.portal.integral.entity.IntegralTableConfigRO;
import com.galaxy.portal.integral.entity.IntegralTableConfigVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 资源-积分配置表
 * @Author: jeecg-boot
 * @Date:   2021-03-29
 * @Version: V1.0
 */
@Repository
public interface IntegralTableConfigMapper extends BaseMapper<IntegralTableConfig> {

    /**
     * 自定义sql分页
     * @param page
     * @param queryWrapper
     * 看这里看这里，如果自定义的方法中需要用到wrapper查询条件，需要这样写
     * @return
     */
    IPage<IntegralTableConfigRO> selectListByUser(IPage<IntegralTableConfigRO> page, @Param("info") IntegralTableConfigVO integralTableConfigVO);
}
