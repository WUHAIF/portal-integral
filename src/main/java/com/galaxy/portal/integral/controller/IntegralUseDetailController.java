package com.galaxy.portal.integral.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.galaxy.portal.common.vo.Result;
import com.galaxy.portal.integral.entity.IntegralUseDetail;
import com.galaxy.portal.integral.service.IIntegralUseDetailService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
* @Description: 积分使用明细表
* @Author: wuhaifeng
* @Date:   2021-03-29
* @Version: V1.0
*/
@Api(tags="积分使用明细表")
@RestController
@RequestMapping("/integral/integralUseDetail")
@Slf4j
public class IntegralUseDetailController{
   @Autowired
   private IIntegralUseDetailService integralUseDetailService;

   /**
    * 分页列表查询
    *
    * @param integralUseDetail
    * @param pageNo
    * @param pageSize
    * @param req
    * @return
    */
   @ApiOperation(value="积分使用明细表-分页列表查询", notes="积分使用明细表-分页列表查询")
   @GetMapping(value = "/list")
   public Result<?> queryPageList(IntegralUseDetail integralUseDetail,
                                  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                  HttpServletRequest req) {
       QueryWrapper<IntegralUseDetail> queryWrapper = new QueryWrapper<>();
       queryWrapper.setEntity(integralUseDetail);
       Page<IntegralUseDetail> page = new Page<IntegralUseDetail>(pageNo, pageSize);
       IPage<IntegralUseDetail> pageList = integralUseDetailService.page(page, queryWrapper);
       return Result.OK(pageList);
   }

   /**
    *   添加
    *
    * @param integralUseDetail
    * @return
    */
   @ApiOperation(value="积分使用明细表-添加", notes="积分使用明细表-添加")
   @PostMapping(value = "/add")
   public Result<?> add(@RequestBody IntegralUseDetail integralUseDetail) {
       integralUseDetailService.save(integralUseDetail);
       return Result.OK("添加成功！");
   }

   /**
    *  编辑
    *
    * @param integralUseDetail
    * @return
    */
   @ApiOperation(value="积分使用明细表-编辑", notes="积分使用明细表-编辑")
   @PutMapping(value = "/edit")
   public Result<?> edit(@RequestBody IntegralUseDetail integralUseDetail) {
       integralUseDetailService.updateById(integralUseDetail);
       return Result.OK("编辑成功!");
   }

   /**
    *   通过id删除
    *
    * @param id
    * @return
    */
   @ApiOperation(value="积分使用明细表-通过id删除", notes="积分使用明细表-通过id删除")
   @DeleteMapping(value = "/delete")
   public Result<?> delete(@RequestParam(name="id",required=true) String id) {
       integralUseDetailService.removeById(id);
       return Result.OK("删除成功!");
   }

   /**
    *  批量删除
    *
    * @param ids
    * @return
    */
   @ApiOperation(value="积分使用明细表-批量删除", notes="积分使用明细表-批量删除")
   @DeleteMapping(value = "/deleteBatch")
   public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
       this.integralUseDetailService.removeByIds(Arrays.asList(ids.split(",")));
       return Result.OK("批量删除成功!");
   }

   /**
    * 通过id查询
    *
    * @param id
    * @return
    */
   @ApiOperation(value="积分使用明细表-通过id查询", notes="积分使用明细表-通过id查询")
   @GetMapping(value = "/queryById")
   public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
       IntegralUseDetail integralUseDetail = integralUseDetailService.getById(id);
       if(integralUseDetail==null) {
           return Result.error("未找到对应数据");
       }
       return Result.OK(integralUseDetail);
   }


}
