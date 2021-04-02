package com.galaxy.portal.integral.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.galaxy.portal.common.system.SysUser;
import com.galaxy.portal.common.vo.Result;
import com.galaxy.portal.integral.entity.IntegralTableConfig;
import com.galaxy.portal.integral.entity.IntegralTableConfigRO;
import com.galaxy.portal.integral.entity.IntegralTableConfigVO;
import com.galaxy.portal.integral.entity.IntegralUnitRel;
import com.galaxy.portal.integral.mapper.IntegralTableConfigMapper;
import com.galaxy.portal.integral.service.IIntegralTableConfigService;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description: 资源-积分配置表
 * @Author: wuhaifeng
 * @Date:   2021-03-29
 * @Version: V1.0
 */
@Service
public class IntegralTableConfigServiceImpl extends ServiceImpl<IntegralTableConfigMapper, IntegralTableConfig> implements IIntegralTableConfigService {


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
        List<IntegralTableConfigRO> recordList = page.getRecords().stream().map(record -> {
            //只能编辑直属部门的数据
            if (record.getBelongToUnit().equals(unitCode)) {
                record.setAllowEdit(true);
            } else {
                record.setAllowEdit(false);
            }
            return record;
        }).collect(Collectors.toList());
        page.setRecords(recordList);
        return page;
    }


    @Override
    public Result<?> editBatch(Map<String,String> paramsMap) {

        //过滤定价不符合规范的对象(每个用户只能更新自己部门下的资源配置)

        //将符合定价的要求对象插入数据库
        String[] ids = paramsMap.get("ids").split(",");
        if(ids.length <= 0){
            return Result.error("请至少选择一条记录更新");
        }

        // 1.批量上/下架;2.批量设置数据等级；3：批量设置积分
        if(paramsMap.get("dataTypeId") == null && paramsMap.get("putOnSale") == null && paramsMap.get("searchIntegral") == null){
            return Result.error("请选择一个要更新的类型");
        }

        //参数值不能设置负数，后期校验
        List<IntegralTableConfig> integralTableConfigList = Stream.of(ids).map(id ->{
            IntegralTableConfig integralTableConfig = new IntegralTableConfig();
            integralTableConfig.setId(id);
            if(paramsMap.get("putOnSale") != null){
                integralTableConfig.setPutOnSale(Integer.parseInt(paramsMap.get("putOnSale")));
            }
            if(paramsMap.get("dataTypeId") != null){
                integralTableConfig.setDataTypeId(Integer.parseInt(paramsMap.get("dataTypeId")));
            }
            if(paramsMap.get("searchIntegral") != null){
                integralTableConfig.setSearchIntegral(Integer.parseInt(paramsMap.get("searchIntegral")));
            }
            return integralTableConfig;
        }).filter(integralTableConfig -> {
            //过滤不是当前用户所属的或者分值设置超标的记录
            /*QueryWrapper<IntegralTableConfig> queryWrapper = new QueryWrapper<>();
            queryWrapper.setEntity(integralTableConfig);
            IntegralTableConfig temInfo = this.baseMapper.selectOne(queryWrapper);
            if(!temInfo.getBelongToUnit().equals(paramsMap.get("unit_code"))){
                return false;
            }*/
            return true;
        }).collect(Collectors.toList());
        boolean b = this.updateBatchById(integralTableConfigList);
        if(b){
            return Result.error("批量更新成功");
        }else{
            return Result.error("批量更新失败");
        }

    }

    @Override
    public IntegralTableConfig selectOneByResource(String resourceName) {
        IntegralTableConfig integralTableConfig = new IntegralTableConfig();
        integralTableConfig.setTableName(resourceName);
        QueryWrapper<IntegralTableConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(integralTableConfig).last(" limit 1");
        IntegralTableConfig resultConfig = this.baseMapper.selectOne(queryWrapper);
        return resultConfig;
    }

}
