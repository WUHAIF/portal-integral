package com.galaxy.portal.integral.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.galaxy.portal.common.vo.Result;
import com.galaxy.portal.integral.entity.IntegralTableConfig;
import com.galaxy.portal.integral.entity.IntegralTableConfigRO;
import com.galaxy.portal.integral.entity.IntegralTableConfigVO;
import com.galaxy.portal.integral.service.IIntegralTableConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
* @Description: 资源-积分配置表
* @Author: jeecg-boot
* @Date:   2021-03-29
* @Version: V1.0
*/
@Api(tags="资源-积分配置表")
@RestController
@RequestMapping("/integral/integralTableConfig")
@Slf4j
public class IntegralTableConfigController {
   @Autowired
   private IIntegralTableConfigService integralTableConfigService;

   /**
    * 分页列表查询
    *
    * @param integralTableConfig
    * @param pageNo
    * @param pageSize
    * @param req
    * @return
    */
   @ApiOperation(value="资源-积分配置表-分页列表查询", notes="资源-积分配置表-分页列表查询")
   @GetMapping(value = "/list")
   public Result<?> queryPageList(IntegralTableConfig integralTableConfig,
                                  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                  HttpServletRequest req) {
       QueryWrapper<IntegralTableConfig> queryWrapper = new QueryWrapper<>();
       queryWrapper.setEntity(integralTableConfig);
       Page<IntegralTableConfig> page = new Page<IntegralTableConfig>(pageNo, pageSize);
       IPage<IntegralTableConfig> pageList = integralTableConfigService.page(page, queryWrapper);
       return Result.OK(pageList);
   }

    /**
     * 分页列表查询
     *
     * @param integralTableConfigVO
     * @param pageNo
     * @param pageSize
     * @return
     */
    @ApiOperation(value="资源-积分配置表-分页列表查询", notes="资源-积分配置表-分页列表查询")
    @GetMapping(value = "/listByUser")
    public Result<?> selectIntegraTableConfigInfoByUser(IntegralTableConfigVO integralTableConfigVO,
                                                        @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                                        @RequestParam(name="pageSize", defaultValue="10") Integer pageSize) throws Exception {
        IPage<IntegralTableConfigRO> page = new Page<>(pageNo, pageSize);
        IPage<IntegralTableConfigRO> pageList = integralTableConfigService.selectIntegraTableConfigInfoByUser(page, integralTableConfigVO);
        return Result.OK(pageList);
    }

   /**
    *   添加
    *
    * @param integralTableConfig
    * @return
    */
   @ApiOperation(value="资源-积分配置表-添加", notes="资源-积分配置表-添加")
   @PostMapping(value = "/add")
   public Result<?> add(@RequestBody IntegralTableConfig integralTableConfig) {
       integralTableConfigService.save(integralTableConfig);
       return Result.OK("添加成功！");
   }

   /**
    *  编辑
    *
    * @param integralTableConfig
    * @return
    */
   @ApiOperation(value="资源-积分配置表-编辑", notes="资源-积分配置表-编辑")
   @PutMapping(value = "/edit")
   public Result<?> edit(@RequestBody IntegralTableConfig integralTableConfig) {
       integralTableConfigService.updateById(integralTableConfig);
       return Result.OK("编辑成功!");
   }

   /**
    *   通过id删除
    *
    * @param id
    * @return
    */
   @ApiOperation(value="资源-积分配置表-通过id删除", notes="资源-积分配置表-通过id删除")
   @DeleteMapping(value = "/delete")
   public Result<?> delete(@RequestParam(name="id",required=true) String id) {
       integralTableConfigService.removeById(id);
       return Result.OK("删除成功!");
   }

   /**
    *  批量删除
    *
    * @param ids
    * @return
    */
   @ApiOperation(value="资源-积分配置表-批量删除", notes="资源-积分配置表-批量删除")
   @DeleteMapping(value = "/deleteBatch")
   public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
       this.integralTableConfigService.removeByIds(Arrays.asList(ids.split(",")));
       return Result.OK("批量删除成功!");
   }

   /**
    * 通过id查询
    *
    * @param id
    * @return
    */
   @ApiOperation(value="资源-积分配置表-通过id查询", notes="资源-积分配置表-通过id查询")
   @GetMapping(value = "/queryById")
   public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
       IntegralTableConfig integralTableConfig = integralTableConfigService.getById(id);
       if(integralTableConfig==null) {
           return Result.error("未找到对应数据");
       }
       return Result.OK(integralTableConfig);
   }
}
