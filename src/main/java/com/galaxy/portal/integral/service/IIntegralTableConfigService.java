package com.galaxy.portal.integral.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.galaxy.portal.common.vo.Result;
import com.galaxy.portal.integral.entity.IntegralTableConfig;
import com.galaxy.portal.integral.entity.IntegralTableConfigRO;
import com.galaxy.portal.integral.entity.IntegralTableConfigVO;

import java.util.List;
import java.util.Map;

/**
 * @Description: 资源-积分配置表
 * @Author: jeecg-boot
 * @Date:   2021-03-29
 * @Version: V1.0
 */
public interface IIntegralTableConfigService extends IService<IntegralTableConfig> {

    IPage<IntegralTableConfigRO> selectIntegraTableConfigInfoByUser(IPage<IntegralTableConfigRO> page, IntegralTableConfigVO integralTableConfigVO) throws Exception;

    Result editBatch(Map<String,String> parmasMap);
}
