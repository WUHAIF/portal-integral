package com.galaxy.portal.integral.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.galaxy.portal.common.system.SysUser;
import com.galaxy.portal.integral.entity.IntegralTableConfig;
import com.galaxy.portal.integral.entity.IntegralTableConfigRO;
import com.galaxy.portal.integral.entity.IntegralTableConfigVO;
import com.galaxy.portal.integral.mapper.IntegralTableConfigMapper;
import com.galaxy.portal.integral.service.IIntegralTableConfigService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 资源-积分配置表
 * @Author: jeecg-boot
 * @Date:   2021-03-29
 * @Version: V1.0
 */
@Service
public class IntegralTableConfigServiceImpl extends ServiceImpl<IntegralTableConfigMapper, IntegralTableConfig> implements IIntegralTableConfigService {
//    @Override
//    public String selectIntegraTableConfigInfoByUser(String name) {
//        return baseMapper.selectIntegraTableConfigInfoByUser(name);
//    }


    @Override
    public IPage<IntegralTableConfigRO> selectIntegraTableConfigInfoByUser(IPage<IntegralTableConfigRO> page, IntegralTableConfigVO integralTableConfigVO) throws Exception {
        //是否是省厅用户
        SysUser sysUser = integralTableConfigVO.getSysUser();
        if(sysUser == null){
            throw new Exception("用户不存在");
        }

        // todo 这个地方的省厅校验需要修改
        String unitCode = sysUser.getUnitCode();
        if(!"2".equals(unitCode)) {//省厅用户可以查看全部上传表信息,非省厅用户只能查看自己直属部门上传表信息
            integralTableConfigVO.setBelongToUnit(integralTableConfigVO.getSysUser().getUnitCode());
        }
        page = baseMapper.selectListByUser(page,integralTableConfigVO);
        /*

        IntegralTableConfig integralTableConfig = new IntegralTableConfig();
        BeanUtils.copyProperties(integralTableConfigVO, integralTableConfig);
        QueryWrapper<IntegralTableConfig> queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(integralTableConfig);
        IPage<IntegralTableConfigRO> integralTableConfigPage = baseMapper.selectListByUser(page, queryWrapper);
        List<IntegralTableConfigRO> recordList = integralTableConfigPage.getRecords().stream().map(record -> {
            //只能编辑直属部门的数据
            if (record.getBelongToUnit().equals(unitCode)) {
                record.setAllowEdit(true);
            } else {
                record.setAllowEdit(false);
            }
            return record;
        }).collect(Collectors.toList());
        integralTableConfigPage.setRecords(recordList);
        return new Page<>();
        page.setRecords(list);*/

        return page;
    }
}
