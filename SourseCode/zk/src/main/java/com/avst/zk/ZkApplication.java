package com.avst.zk;

import com.avst.zk.common.util.properties.PropertiesListener;
import com.avst.zk.common.util.properties.PropertiesListenerConfig;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@EnableEurekaServer
@EnableFeignClients //服务调用
@SpringBootApplication
@EnableScheduling //开启定时任务
public class ZkApplication  {

    public static class CustomGenerator implements BeanNameGenerator {

        @Override
        public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
            return definition.getBeanClassName();
        }
    }

    @RequestMapping("/listener")
    public Map<String, Object> listener() {
        Map<String, Object> map = new HashMap<>();
        map.putAll(PropertiesListenerConfig.getAllProperty());
        return map;
    }


    public static void main(String[] args) {
//        SpringApplication.run(ZkApplication.class, args);

        SpringApplication application=new SpringApplication(ZkApplication.class);
        application.setBeanNameGenerator(new CustomGenerator());
        application.addListeners(new PropertiesListener("application.properties"));
        application.run(args);

//        new SpringApplicationBuilder(ZkApplication.class)
//                .beanNameGenerator(new CustomGenerator())
//                .run(args);
    }

}
