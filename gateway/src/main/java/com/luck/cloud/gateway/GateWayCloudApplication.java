package com.luck.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by luck on 2017/09/28.
 * 该过滤器覆盖范围为整个微服务.
 */
@EnableZuulProxy
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan({"com.luck.cloud.gateway"})
public class GateWayCloudApplication {
    public static void main(String[] args) {
        SpringApplication.run(GateWayCloudApplication.class, args);
    }


}
