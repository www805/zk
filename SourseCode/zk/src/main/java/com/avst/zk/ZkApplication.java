package com.avst.zk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableEurekaServer
@EnableFeignClients //服务调用
@SpringBootApplication
@EnableScheduling //开启定时任务
public class ZkApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZkApplication.class, args);
    }

}
