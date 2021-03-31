package com.galaxy.portal.integral.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.galaxy.portal.common.vo.Result;
import com.galaxy.portal.integral.entity.IntegralDataType;
import com.galaxy.portal.integral.service.IIntegralDataTypeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
* @Description: 数据等级定义表
* @Author: jeecg-boot
* @Date:   2021-03-29
* @Version: V1.0
*/
@Api(tags="数据等级定义表")
@RestController
@RequestMapping("/integral/integralDataType")
@Slf4j
public class IntegralDataTypeController {
   @Autowired
   private IIntegralDataTypeService integralDataTypeService;

   /**
    * 分页列表查询
    *
    * @param integralDataType
    * @param pageNo
    * @param pageSize
    * @param req
    * @return
    */
   @ApiOperation(value="数据等级定义表-分页列表查询", notes="数据等级定义表-分页列表查询")
   @GetMapping(value = "/list")
   public Result<?> queryPageList(IntegralDataType integralDataType,
                                  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                  HttpServletRequest req) {
       Page<IntegralDataType> page = new Page<IntegralDataType>(pageNo, pageSize);
       QueryWrapper<IntegralDataType> queryWrapper = new QueryWrapper<>();
       queryWrapper.setEntity(integralDataType).orderByDesc("create_time");
       IPage<IntegralDataType> pageList = integralDataTypeService.page(page, queryWrapper);
       return Result.OK(pageList);
   }

   /**
    *   添加
    *
    * @param integralDataType
    * @return
    */
   @ApiOperation(value="数据等级定义表-添加", notes="数据等级定义表-添加")
   @PostMapping(value = "/add")
   public Result<?> add(@RequestBody IntegralDataType integralDataType) {
       integralDataTypeService.save(integralDataType);
       return Result.OK("添加成功！");
   }

   /**
    *  编辑
    *
    * @param integralDataType
    * @return
    */
   @ApiOperation(value="数据等级定义表-编辑", notes="数据等级定义表-编辑")
   @PutMapping(value = "/edit")
   public Result<?> edit(@RequestBody IntegralDataType integralDataType) {
       integralDataTypeService.updateById(integralDataType);
       return Result.OK("编辑成功!");
   }

   /**
    *   通过id删除
    *
    * @param id
    * @return
    */
   @ApiOperation(value="数据等级定义表-通过id删除", notes="数据等级定义表-通过id删除")
   @DeleteMapping(value = "/delete")
   public Result<?> delete(@RequestParam(name="id",required=true) String id) {
       integralDataTypeService.removeById(id);
       return Result.OK("删除成功!");
   }

   /**
    *  批量删除
    *
    * @param ids
    * @return
    */
   @ApiOperation(value="数据等级定义表-批量删除", notes="数据等级定义表-批量删除")
   @DeleteMapping(value = "/deleteBatch")
   public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
       this.integralDataTypeService.removeByIds(Arrays.asList(ids.split(",")));
       return Result.OK("批量删除成功!");
   }

   /**
    * 通过id查询
    *
    * @param id
    * @return
    */

   @ApiOperation(value="数据等级定义表-通过id查询", notes="数据等级定义表-通过id查询")
   @GetMapping(value = "/queryById")
   public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
       IntegralDataType integralDataType = integralDataTypeService.getById(id);
       if(integralDataType==null) {
           return Result.error("未找到对应数据");
       }
       return Result.OK(integralDataType);
   }

}
