package com.xws.xysz;

import com.xws.xysz.interceptor.AppInterceptor;
import com.xws.xysz.interceptor.ChannelInterceptor;
import com.xws.xysz.interceptor.SessionInterceptor;
import com.xws.xysz.interceptor.TokenInterceptor;
import com.xws.xysz.thymeleaf.dialect.MyDialect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * Date: 2018-11-05
 * Time: 12:43
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Bean
    public AppInterceptor appInterceptor(){
        return new AppInterceptor();
    }


    @Bean
    public ChannelInterceptor channelInterceptor(){
        return new ChannelInterceptor();
    }


    //增加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //session拦截器
        registry.addInterceptor(new SessionInterceptor())
                .addPathPatterns("/**");

        //重复提交拦截器
        registry.addInterceptor(new TokenInterceptor())
                .addPathPatterns("/**");

        //APP拦截器
        registry.addInterceptor(appInterceptor())
                .addPathPatterns("/app/**");

        //渠道商统计拦截器
        registry.addInterceptor(channelInterceptor())
                .addPathPatterns("/invite/**");

        super.addInterceptors(registry);
    }




    /**
     * 设置静态资源
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/templates/");
    }

    @Bean
    @ConditionalOnMissingBean
    public MyDialect baseDialect() {
        //增加Thymeleaf的方言，支持一些自定义的模板表达式
        return new MyDialect();
    }
}
