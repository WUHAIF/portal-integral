package com.galaxy.portal.config.mybatis;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author: wuhaifeng
 * @DateTime: 2021/3/30 18:25
 * @Description: MyBatisPlusConfig插件配置类
 */
@EnableTransactionManagement
@Configuration
public class MyBatisPlusConfig {
    /**
     * mybatisplus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }

    /**
     *   乐观锁
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }
}
