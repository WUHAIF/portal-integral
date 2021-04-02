package com.galaxy.portal.integral.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.galaxy.portal.common.vo.Result;
import com.galaxy.portal.integral.entity.IntegralUserRel;
import com.galaxy.portal.integral.service.IIntegralUserRelService;

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
* @Description: 积分-用户关系表
* @Author: wuhaifeng
* @Date:   2021-03-29
* @Version: V1.0
*/
@Api(tags="积分-用户关系表")
@RestController
@RequestMapping("/integral/integralUserRel")
@Slf4j
public class IntegralUserRelController{
   @Autowired
   private IIntegralUserRelService integralUserRelService;

   /**
    * 分页列表查询
    *
    * @param integralUserRel
    * @param pageNo
    * @param pageSize
    * @param req
    * @return
    */
   @ApiOperation(value="积分-用户关系表-分页列表查询", notes="积分-用户关系表-分页列表查询")
   @GetMapping(value = "/list")
   public Result<?> queryPageList(IntegralUserRel integralUserRel,
                                  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                  HttpServletRequest req) {
       QueryWrapper<IntegralUserRel> queryWrapper = new QueryWrapper<>();
       queryWrapper.setEntity(integralUserRel);
       Page<IntegralUserRel> page = new Page<IntegralUserRel>(pageNo, pageSize);
       IPage<IntegralUserRel> pageList = integralUserRelService.page(page, queryWrapper);
       return Result.OK(pageList);
   }

    /**
     * 分页列表查询；根据用户表左外连接生成列表记录
     *
     * @param integralUserRel
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @ApiOperation(value="积分-用户关系表-分页列表查询", notes="积分-用户关系表-分页列表查询")
    @GetMapping(value = "/queryPageListWithAllUser")
    public Result<?> queryPageListWithUser(IntegralUserRel integralUserRel,
                                   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                   HttpServletRequest req) {


        IPage<IntegralUserRel> pageList = integralUserRelService.queryPageListWithUser(integralUserRel, pageNo,pageSize);
        return Result.OK(pageList);
    }

    /**
    *   添加
    *
    * @param integralUserRel
    * @return
    */
   @ApiOperation(value="积分-用户关系表-添加", notes="积分-用户关系表-添加")
   @PostMapping(value = "/add")
   public Result<?> add(@RequestBody IntegralUserRel integralUserRel) {
       integralUserRelService.save(integralUserRel);
       return Result.OK("添加成功！");
   }

   /**
    *  编辑
    *
    * @param integralUserRel
    * @return
    */
   @ApiOperation(value="积分-用户关系表-编辑", notes="积分-用户关系表-编辑")
   @PutMapping(value = "/edit")
   public Result<?> edit(@RequestBody IntegralUserRel integralUserRel) {
       integralUserRelService.updateById(integralUserRel);
       return Result.OK("编辑成功!");
   }

    /**
     *  新增或者保存
     *
     * @param integralUserRel
     * @return
     */
    @ApiOperation(value="积分-用户关系表-新增或者保存", notes="积分-用户关系表-新增或者保存")
    @PutMapping(value = "/saveOrUpdate")
    public Result<?> saveOrUpdate(@RequestBody IntegralUserRel integralUserRel) {
        integralUserRelService.saveOrUpdate(integralUserRel);
        return Result.OK("新增或者保存成功!");
    }

   /**
    *   通过id删除
    *
    * @param id
    * @return
    */
   @ApiOperation(value="积分-用户关系表-通过id删除", notes="积分-用户关系表-通过id删除")
   @DeleteMapping(value = "/delete")
   public Result<?> delete(@RequestParam(name="id",required=true) String id) {
       integralUserRelService.removeById(id);
       return Result.OK("删除成功!");
   }

   /**
    *  批量删除
    *
    * @param ids
    * @return
    */
   @ApiOperation(value="积分-用户关系表-批量删除", notes="积分-用户关系表-批量删除")
   @DeleteMapping(value = "/deleteBatch")
   public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
       this.integralUserRelService.removeByIds(Arrays.asList(ids.split(",")));
       return Result.OK("批量删除成功!");
   }

   /**
    * 通过id查询
    *
    * @param id
    * @return
    */
   @ApiOperation(value="积分-用户关系表-通过id查询", notes="积分-用户关系表-通过id查询")
   @GetMapping(value = "/queryById")
   public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
       IntegralUserRel integralUserRel = integralUserRelService.getById(id);
       if(integralUserRel==null) {
           return Result.error("未找到对应数据");
       }
       return Result.OK(integralUserRel);
   }

    /**
     * 通过用户查询
     *
     * @param id
     * @return
     */
    @ApiOperation(value="积分-用户关系表-通过用户查询", notes="积分-用户关系表-通过用户查询")
    @GetMapping(value = "/selectOneByUser")
    public Result<?> selectOneByUser(@RequestParam(name="userId",required=true) String userId) {
        IntegralUserRel integralUserRel = integralUserRelService.selectOneByUser(userId);
        if(integralUserRel==null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(integralUserRel);
    }
}
