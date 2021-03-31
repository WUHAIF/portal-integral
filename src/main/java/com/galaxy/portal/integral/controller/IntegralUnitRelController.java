package com.galaxy.portal.integral.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.galaxy.portal.common.vo.Result;
import com.galaxy.portal.integral.entity.IntegralUnitRel;
import com.galaxy.portal.integral.service.IIntegralUnitRelService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
* @Description: 积分-部门关系表
* @Author: jeecg-boot
* @Date:   2021-03-29
* @Version: V1.0
*/
@Api(tags="积分-部门关系表")
@RestController
@RequestMapping("/integral/integralUnitRel")
@Slf4j
public class IntegralUnitRelController {
   @Autowired
   private IIntegralUnitRelService integralUnitRelService;

   /**
    * 分页列表查询
    *
    * @param integralUnitRel
    * @param pageNo
    * @param pageSize
    * @param req
    * @return
    */
   @ApiOperation(value="积分-部门关系表-分页列表查询", notes="积分-部门关系表-分页列表查询")
   @GetMapping(value = "/list")
   public Result<?> queryPageList(IntegralUnitRel integralUnitRel,
                                  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                  HttpServletRequest req) {
       QueryWrapper<IntegralUnitRel> queryWrapper = new QueryWrapper<>();
       queryWrapper.setEntity(integralUnitRel);
       Page<IntegralUnitRel> page = new Page<IntegralUnitRel>(pageNo, pageSize);
       IPage<IntegralUnitRel> pageList = integralUnitRelService.page(page, queryWrapper);
       return Result.OK(pageList);
   }

    /**
     * 分页列表查询:根据部门表左外连接生成记录
     *
     * @param integralUnitRel
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @ApiOperation(value="积分-部门关系表-分页列表查询", notes="积分-部门关系表-分页列表查询")
    @GetMapping(value = "/listWithAllUser")
    public Result<?> queryPageListWithAllUser(IntegralUnitRel integralUnitRel,
                                   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<IntegralUnitRel> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(integralUnitRel);
        Page<IntegralUnitRel> page = new Page<IntegralUnitRel>(pageNo, pageSize);
        IPage<IntegralUnitRel> pageList = integralUnitRelService.page(page, queryWrapper);
        return Result.OK(pageList);
    }
   /**
    *   添加
    *
    * @param integralUnitRel
    * @return
    */
   @ApiOperation(value="积分-部门关系表-添加", notes="积分-部门关系表-添加")
   @PostMapping(value = "/add")
   public Result<?> add(@RequestBody IntegralUnitRel integralUnitRel) {
       integralUnitRelService.save(integralUnitRel);
       return Result.OK("添加成功！");
   }

   /**
    *  编辑
    *
    * @param integralUnitRel
    * @return
    */
   @ApiOperation(value="积分-部门关系表-编辑", notes="积分-部门关系表-编辑")
   @PutMapping(value = "/edit")
   public Result<?> edit(@RequestBody IntegralUnitRel integralUnitRel) {
       integralUnitRelService.updateById(integralUnitRel);
       return Result.OK("编辑成功!");
   }

   /**
    *   通过id删除
    *
    * @param id
    * @return
    */
   @ApiOperation(value="积分-部门关系表-通过id删除", notes="积分-部门关系表-通过id删除")
   @DeleteMapping(value = "/delete")
   public Result<?> delete(@RequestParam(name="id",required=true) String id) {
       integralUnitRelService.removeById(id);
       return Result.OK("删除成功!");
   }

   /**
    *  批量删除
    *
    * @param ids
    * @return
    */
   @ApiOperation(value="积分-部门关系表-批量删除", notes="积分-部门关系表-批量删除")
   @DeleteMapping(value = "/deleteBatch")
   public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
       this.integralUnitRelService.removeByIds(Arrays.asList(ids.split(",")));
       return Result.OK("批量删除成功!");
   }

   /**
    * 通过id查询
    *
    * @param id
    * @return
    */
   @ApiOperation(value="积分-部门关系表-通过id查询", notes="积分-部门关系表-通过id查询")
   @GetMapping(value = "/queryById")
   public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
       IntegralUnitRel integralUnitRel = integralUnitRelService.getById(id);
       if(integralUnitRel==null) {
           return Result.error("未找到对应数据");
       }
       return Result.OK(integralUnitRel);
   }




}
