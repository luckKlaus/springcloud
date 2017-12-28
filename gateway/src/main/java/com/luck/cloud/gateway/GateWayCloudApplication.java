package com.luck.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by luck on 2017/09/28.
 */
@EnableZuulProxy
@SpringBootApplication
@EnableAutoConfiguration
@EntityScan("com.gimi.cloud.uxq.cloud.entity")
@ComponentScan({"com.luck.cloud.gateway","com.luck.cloud.spring.mybatis"})
public class GateWayCloudApplication {
    public static void main(String[] args) {
        SpringApplication.run(GateWayCloudApplication.class, args);
    }


}
