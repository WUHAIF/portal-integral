package com.galaxy.portal.integral.service.impl;


import com.galaxy.portal.common.constant.CommonConstant;
import com.galaxy.portal.common.constant.enums.ApiException;
import com.galaxy.portal.common.constant.enums.ApiResultEnum;
import com.galaxy.portal.common.customize.annotation.IsTryAgain;
import com.galaxy.portal.common.customize.aspect.TryAgainException;
import com.galaxy.portal.common.system.SysUser;
import com.galaxy.portal.integral.entity.*;
import com.galaxy.portal.integral.service.*;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: wuhaifeng
 * @DateTime: 2021/4/1 22:40
 * @Description: TODO
 */
@Service
public class IntegraloperateServiceImpl implements IIntegraloperateService {

    @Resource
    private IIntegralTableConfigService integralTableConfigService;
    @Resource
    private IIntegralUnitRelService integralUnitRelService;
    @Resource
    private IIntegralUserRelService integralUserRelService;
    @Resource
    private IIntegralUseDetailService iIntegralUseDetailService;



    @Override
    public List<String> integralIncreaseOrDeductBatch(IntegralOperVO integralOperVO) {
        //todo 需要验证不同积分操作类型下的的积分分值，下发：积分值>0;扣除：积分值<0;使用：积分值<0

        //获取本次要查询的部门-积分对象
        List<IntegralUnitRel> integralUnitRels = integralUnitRelService.listByIds(integralOperVO.getSysUnitList());
        if(integralUnitRels.size() <= 0){
            throw new ApiException(ApiResultEnum.UNIT_NOT_SELECT_OR_NOT_FOUND);
        }
        List<String> erroUnitList = new ArrayList<>();
        integralUnitRels.stream().forEach(integralUnitRel -> {
            //批量对每一个对象进行操作
            Integer integralType = integralOperVO.getIntegralType();
            Integer operIntegralNum = integralOperVO.getOperIntegralNum();
            SysUser operUser = new SysUser();
            operUser.setId(integralOperVO.getOperUserId());
            operUser.setUserName(integralOperVO.getOperUserName());
            String operDetail = integralOperVO.getOperDatail();
            try {
                integralIncreaseOrDeduct(integralUnitRel,operUser,integralType,operIntegralNum,operDetail);
            } catch (Exception e) {
                e.printStackTrace();
                erroUnitList.add(integralUnitRel.getUnitName());
            }


        });
        return erroUnitList;
    }

    @Override
    @IsTryAgain
    public boolean integralConsume(IntegralOperVO integralOperVO) {
        //判断部门积分是否充足
        IntegralUnitRel integralUnitConsume = integralUnitRelService.selectOneByUnit(integralOperVO.getOPerUserUnitCode());
        if(integralOperVO.getOperIntegralNum() > integralUnitConsume.getCurrentIntegral()){
            throw new ApiException(ApiResultEnum.INTEGRAL_UNIT_NOT_ENOUGH);
        }
        //判断个人积分是否充足
        IntegralUserRel integralUserRel = integralUserRelService.selectOneByUser(integralOperVO.getOperUserId());
        if(integralOperVO.getOperIntegralNum() > integralUnitConsume.getCurrentIntegral()){
            throw new ApiException(ApiResultEnum.INTEGRAL_USER_NOT_ENOUGH);
        }

        //1、将个人可用积分减掉对应分值，新增个人已使用分值
        integralUserRel.setAllowUseIntegral(integralUserRel.getAllowUseIntegral() + integralOperVO.getOperIntegralNum());
        integralUserRel.setUsedIntegral(integralUserRel.getUsedIntegral() + Math.abs(integralOperVO.getOperIntegralNum()));


        //2、将部门积分减掉对应分值，更新部门已经使用分值
        integralUnitConsume.setCurrentIntegral(integralUnitConsume.getCurrentIntegral() + integralOperVO.getOperIntegralNum());
        integralUnitConsume.setUsedIntegral(integralUnitConsume.getUsedIntegral() + Math.abs(integralOperVO.getOperIntegralNum()));
        //3、将资源所属部门的积分制加上相应的分值
        IntegralTableConfig integralTableConfig = integralTableConfigService.selectOneByResource(integralOperVO.getConsumeResource());
        IntegralUnitRel integralUnitGain = integralUnitRelService.selectOneByUnit(integralTableConfig.getBelongToUnit());
        integralUnitGain.setCurrentIntegral(integralUnitGain.getCurrentIntegral() + Math.abs(integralOperVO.getOperIntegralNum()));

        //插入本次的日志明细
        addConsumeOperDetailInfo(integralOperVO,integralUnitConsume,integralUnitGain);

        //更新个人可以使用积分
        this.integralUserRelService.updateById(integralUserRel);
        //更新双方部门积分
        List<IntegralUnitRel> integralUnitRels = new ArrayList<>();
        integralUnitRels.add(integralUnitConsume);
        integralUnitRels.add(integralUnitGain);
        if(!this.integralUnitRelService.updateBatchById(integralUnitRels)){
            throw new TryAgainException(ApiResultEnum.ERROR_TRY_AGAIN);
        }
        return true;
    }

    /**
     * 积分只针对部门或者个人，所有积分明细的主体就是明细或者个人
     * @param integralUnitRelOld
     * @param integralType
     * @param operIntegralNum
     * @return
     */
    @IsTryAgain
    public boolean integralIncreaseOrDeduct(IntegralUnitRel integralUnitRelOld,SysUser operUser,  Integer integralType, Integer operIntegralNum ,String operDetail) {
        //todo 需要验证不同积分操作类型下的的积分分值，下发/收入：积分值>0;扣除/使用：积分值<0;

        //新建一个待更新的对象，将前台的对象设置的该对象
        IntegralUnitRel integralUnitRelNew = new IntegralUnitRel();
        BeanUtils.copyProperties(integralUnitRelOld, integralUnitRelNew);
        //设置当前的查询的对象的版本,更新积分的分值
        if(integralUnitRelOld.getCurrentIntegral() + operIntegralNum < 0){
            throw new TryAgainException(ApiResultEnum.INTEGRAL_UNIT_NOT_ENOUGH);
        }
        integralUnitRelNew.setCurrentIntegral(integralUnitRelOld.getCurrentIntegral() + operIntegralNum);
        //-----更新部门积分，同时记录操作详情---//
        //更新部门积分对象
        try {
            //插入详细的操作日志信息
            addOperDetailInfo(integralUnitRelNew,operUser,integralType,operIntegralNum, operDetail );

            //积分操作放在最后一步，防止异常导致已经更新的被回滚
            if (!integralUnitRelService.updateById(integralUnitRelNew)) {
                throw new TryAgainException(ApiResultEnum.ERROR_TRY_AGAIN);
            }

        } catch (TryAgainException e) {
            throw new TryAgainException(ApiResultEnum.ERROR_TRY_AGAIN);
        } catch (Exception e) {
            throw new ApiException(ApiResultEnum.INTEGRAL_OPERATE_DETAIL);
        }

        return true;
    }


    /**
     * 部门下发/扣除积分时候插入日志
     * @param integralUnitRel
     * @param operUser
     * @param integralType
     * @param operIntegralNum
     * @param operDetail
     */
    private void addOperDetailInfo(IntegralUnitRel integralUnitRel, SysUser operUser, Integer integralType, Integer operIntegralNum, String operDetail) {
        IntegralUseDetail integralUseDetail = new IntegralUseDetail();
        integralUseDetail.setUnitCode(integralUnitRel.getUnitCode());
        integralUseDetail.setUnitName(integralUnitRel.getUnitName());
        integralUseDetail.setIntegralType(integralType);
        integralUseDetail.setOperTime(new Date());
        integralUseDetail.setOperIntegralNum(operIntegralNum);
        integralUseDetail.setRemainIntegralNum(integralUnitRel.getCurrentIntegral());
        integralUseDetail.setOperDatail(operDetail + ";操作人员：" +operUser.getUserName());
        iIntegralUseDetailService.save(integralUseDetail);
    }

    /**
     * 个人消费积分明细日志
     * @param integralOperVO
     * @param integralUnitConsume
     * @param integralUnitGain
     */
    private void addConsumeOperDetailInfo(IntegralOperVO integralOperVO,IntegralUnitRel integralUnitConsume,IntegralUnitRel integralUnitGain) {
        IntegralUseDetail integralUseDetail = new IntegralUseDetail();
        Date date = new Date();
        //设置个人消费明细
        integralUseDetail.setUnitCode(integralOperVO.getOPerUserUnitCode());
        integralUseDetail.setUnitName(integralOperVO.getOPerUserUnitName());
        integralUseDetail.setIntegralType(integralOperVO.getIntegralType());
        integralUseDetail.setOperTime(date);
        integralUseDetail.setOperIntegralNum(integralOperVO.getOperIntegralNum());
        integralUseDetail.setRemainIntegralNum(integralUnitConsume.getCurrentIntegral());
        integralUseDetail.setOperDatail(integralOperVO.getOperUserName() + "查询资源\"" + integralOperVO.getConsumeResource() + "\" 消费了"  + Math.abs(integralOperVO.getOperIntegralNum()) +  "个积分" );
        iIntegralUseDetailService.save(integralUseDetail);

        //对应部门获得积分明细
        integralUseDetail.setUnitCode(integralUnitGain.getUnitCode());
        integralUseDetail.setUnitName(integralUnitGain.getUnitName());
        integralUseDetail.setIntegralType(CommonConstant.INTEGEAL_GAIN);
        integralUseDetail.setOperTime(date);
        integralUseDetail.setOperIntegralNum(integralOperVO.getOperIntegralNum());
        integralUseDetail.setRemainIntegralNum(integralUnitGain.getCurrentIntegral());
        integralUseDetail.setOperDatail(integralOperVO.getOperUserName() + "查询资源\"" + integralOperVO.getConsumeResource() + "\" 消费了"  + Math.abs(integralOperVO.getOperIntegralNum()) +  "个积分" );
        iIntegralUseDetailService.save(integralUseDetail);
    }
}
