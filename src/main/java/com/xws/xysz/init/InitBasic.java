package com.xws.xysz.init;

import com.xws.xysz.config.SystemConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;

/**
 * 基础变量初始化
 * JokerYG
 * Date: 2019-01-07
 * Time: 15:58
 */
@Component
public class InitBasic implements CommandLineRunner {
    @Autowired
    ServletContext servletContext;

    @Override
    public void run(String... args) {
        System.out.println("--- [基础变量] 开始初始化 ---");
        servletContext.setAttribute("imgSite", SystemConfig.IMG_SITE);//图片空间地址
        servletContext.setAttribute("site", SystemConfig.SITE);//网站地址
        System.out.println("--- [基础变量] 初始化完毕 ---");
    }
}
