package com.xws.xysz.init;


import com.xws.xysz.enums.StateEnum;
import com.xws.xysz.util.EnumUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;

/**
 * 枚举初始化
 * rwt
 * Date: 2019-05-22
 * Time: 10:28
 */
@Component
public class InitEnum implements CommandLineRunner {

    @Autowired
    ServletContext servletContext;

    @Override
    public void run(String... args) {
        System.out.println("--- [枚举] 开始初始化 ---");
        servletContext.setAttribute("basicState", EnumUtil.enumToMap(StateEnum.Basic.values()));
        servletContext.setAttribute("infoState",EnumUtil.enumToMap(StateEnum.Info.values()));
        System.out.println("--- [枚举] 初始化完毕 ---");
    }
}
