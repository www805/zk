package com.avst.zk.web.interceptor;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration          //使用注解 实现拦截
public class InterceptorConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //登录拦截的管理器
        InterceptorRegistration registration = registry.addInterceptor(new ManagerInterceptor());     //拦截的对象会进入这个类中进行判断
        registration.addPathPatterns("/");
//        registration.addPathPatterns("/zk/**");
        registration.addPathPatterns("/sweb/**");
        registration.addPathPatterns("/main/**");
        registration.excludePathPatterns("/gotologin", "/logining", "/error", "/static/**", "/logout","/main/getNavList");       //添加不拦截路径

        //权限拦截的管理器

    }

    /**
     * 跨域支持
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH")
                .maxAge(3600 * 24);
    }


}
