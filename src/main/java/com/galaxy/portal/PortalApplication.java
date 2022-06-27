package com.galaxy.portal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableEurekaClient
@MapperScan("com.galaxy.portal.*.mapper") //扫描dao
//@EnableDiscoveryClient
//@EnableFeignClients
//@EnableHystrix
//@EnableCircuitBreaker
public class PortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(PortalApplication.class, args);
    }

}
