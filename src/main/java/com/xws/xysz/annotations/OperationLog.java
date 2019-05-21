package com.xws.xysz.annotations;

import java.lang.annotation.*;

/**
 *
 */
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLog {

    /**
     * @Description描述操作意义,比如申报通过或者不通过等
     */
    String desc() default "";

    /**
     * @Description描述操作方法的参数意义
     * 需要跟参数传入顺序一致,队列取
     * 自动过滤掉request,response,model(spring)
     * 自动过滤掉的不占位
     * 主动过滤使用 "none"
     * 数组内有几个元素，则记录几个参数值(不包括自动和主动过滤掉的)
     */
    String[] arguDesc() default {};
}
